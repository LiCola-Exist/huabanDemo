package licola.demo.com.huabandemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.bean.SearchHintBean;
import licola.demo.com.huabandemo.httpUtils.RetrofitGsonRx;
import licola.demo.com.huabandemo.view.FlowLayout;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.scrollview_search)
    ScrollView mScrollViewSearch;
    @Bind(R.id.flow_reference)
    FlowLayout mFlowReference;//推荐的父控件 内容动态填充
    @Bind(R.id.flow_history)
    FlowLayout mFlowHistory;//推荐的父控件 内容动态填充
    @Bind(R.id.ibtn_clear_history)
    ImageButton mIBtnClearHistory;
    @Bind(R.id.listview_search)
    ListView mListViewSearch;//用于展示搜索关键字网络提示

    final int mItemLineNumber = 4;//每行的个数
    final int mItemMargin = 1;
    final int mItemTVMargin = 10;
    int mItemWidth;//子控件的宽度


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
//        ActivityCompat.startActivity();
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIBtnClearHistory.setImageDrawable(Utils.getTintCompatDrawable(mContext, R.drawable.ic_close_white_24dp, R.color.tint_list_grey));
        initFlowReference(mFlowReference);
        initFlowHistory(mFlowHistory);

        initClearHistory();//点击按钮 清除历史记录的操作
        initSearHint();
    }

    private void initSearHint() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            arrayList.add("36140137" + i + "@qq.com");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_dropdown_item, arrayList);
        mListViewSearch.setAdapter(adapter);

    }

    private void initClearHistory() {
        RxView.clicks(mIBtnClearHistory)
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Logger.d();
                        mFlowHistory.removeAllViews();
                        addChildTextTips(mFlowHistory,"没有纪录");
                    }
                });
    }

    private void initFlowHistory(FlowLayout mFlowHistory) {
        mFlowHistory.removeAllViews();
        String mTextList[] = getResources().getStringArray(R.array.title_array_all);//显示的文字
        for (String mTextString :
                mTextList) {
            addChildText(mFlowHistory, mTextString);
        }
    }

    private void initFlowReference(FlowLayout mFlowReference) {
        String mTextList[] = getResources().getStringArray(R.array.title_array_all);//显示的文字
        String mTypeList[] = getResources().getStringArray(R.array.type_array_all);//查询的关键字
        mItemWidth = Utils.getScreenWidth(mContext) / mItemLineNumber - mItemMargin * 2;//每个子控件宽为屏幕的等分
//        mItemWidth= Utils.getScreenWidth(mContext)/mItemLineNumber;
        //根据内容动态填充
        for (int i = 0, size = mTextList.length; i < size; i++) {
            addChildButton(mFlowReference, mTextList[i], mTypeList[i]);
        }

    }

    private void addChildTextTips(FlowLayout group,String mTextString) {
        TextView tvChild=new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        tvChild.setText(mTextString);
        tvChild.setLayoutParams(layoutParams);
        tvChild.setGravity(Gravity.CENTER_HORIZONTAL);
        group.addView(tvChild);
    }

    private void addChildText(FlowLayout group, String mTextString) {
        final TextView tvChild=new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(mItemTVMargin, mItemTVMargin, mItemTVMargin, mItemTVMargin);
        tvChild.setText(mTextString);
        tvChild.setTextColor(Color.WHITE);
        tvChild.setBackgroundResource(R.drawable.bg_text_history);
        tvChild.setLayoutParams(layoutParams);
        tvChild.setGravity(Gravity.CENTER_HORIZONTAL);

        tvChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultActivity.launch(SearchActivity.this,tvChild.getText().toString());
            }
        });

        group.addView(tvChild);
    }

    private void addChildButton(FlowLayout group, String text, String type) {
        Button btnChild = new Button(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(mItemMargin, mItemMargin, mItemMargin, mItemMargin);
        btnChild.setCompoundDrawablesWithIntrinsicBounds(
                null,
                Utils.getTintCompatDrawable(mContext, R.drawable.ic_toys_white_48dp, R.color.tint_list_pink),
                null,
                null);
        btnChild.setText(text);
        btnChild.setTag(type);
        btnChild.setBackgroundColor(Color.WHITE);
        btnChild.setLayoutParams(layoutParams);
        btnChild.setGravity(Gravity.CENTER_HORIZONTAL);

        btnChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                Logger.d(textView.getTag() + " ");
                Logger.d(textView.getText().toString());
            }
        });

        group.addView(btnChild);
    }

    private Observable<SearchHintBean> getSearHit(String key) {
        return RetrofitGsonRx.service.httpSearHintBean(key);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));

//        initSearchView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /**
             * Called when the user submits the query. This could be due to a key press on the
             * keyboard or due to pressing a submit button.
             * The listener can override the standard behavior by returning true
             * to indicate that it has handled the submit request. Otherwise return false to
             * let the SearchView handle the submission by launching any associated intent.
             *
             * @param query the query text that is to be submitted
             *
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                Logger.d(query);
                return false;
            }

            /**
             * Called when the query text is changed by the user.
             *
             * @param newText the new content of the query text field.
             *
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                Logger.d(newText);
                return true;
            }
        });
        return true;
    }

    private void initSearchView(final SearchView searchView) {
        RxSearchView.queryTextChanges(searchView)
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return !charSequence.equals("");
                    }
                })
                .observeOn(Schedulers.io())
                //debounce 函数 过滤掉由Observable发射的速率过快的数据
                .debounce(500, TimeUnit.MILLISECONDS)
                //switchMap函数 每当源Observable发射一个新的数据项（Observable）时，
                //它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个。
                .switchMap(new Func1<CharSequence, Observable<SearchHintBean>>() {
                    @Override
                    public Observable<SearchHintBean> call(CharSequence charSequence) {
                        return getSearHit(charSequence.toString());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchHintBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(SearchHintBean searchHintBean) {
                        Logger.d(searchHintBean.getResult().toString());
//                        mScrollViewSearch.setVisibility(View.GONE);
//                        mListViewSearch.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search:
//                break;
//        }

        Logger.d(item.getItemId()+" "+item.getTitle());

        return true;
    }


}
