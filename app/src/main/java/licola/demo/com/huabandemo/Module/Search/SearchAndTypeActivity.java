package licola.demo.com.huabandemo.Module.Search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import licola.demo.com.huabandemo.API.HttpsAPI.SearchAPI;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.Module.Type.TypeActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Module.SearchResult.SearchResultActivity;
import licola.demo.com.huabandemo.Util.CompatUtils;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.Widget.FlowLayout;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * 搜索和全部分类模块
 */
public class SearchAndTypeActivity extends BaseActivity {

    @BindView(R.id.actv_search)
    AutoCompleteTextView mACTVSearch;
    @BindView(R.id.scrollview_search)
    ScrollView mScrollViewSearch;
    @BindView(R.id.flow_reference)
    FlowLayout mFlowReference;//推荐的父控件 内容动态填充
    @BindView(R.id.flow_history)
    FlowLayout mFlowHistory;//搜索历史父控件 内容动态填充
    @BindView(R.id.ibtn_clear_history)
    ImageButton mIBtnClearHistory;

    @BindString(R.string.hint_not_history)
    String mStringNotHistory;
//    @BindViewDrawable(R.drawable.bg_tv_text_selector)
//    Drawable mTextDrawable;

    final int mItemLineNumber = 3;//每行的个数
    final int mItemMargin = 1;
    final int mItemTVMargin = 10;
    int mItemWidth;//子控件的宽度


    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mListHttpHint = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SearchAndTypeActivity.class);
//        ActivityCompat.startActivity();
        activity.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFlowHistory(mFlowHistory);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        mIBtnClearHistory.setImageDrawable(CompatUtils.getTintListDrawable(mContext, R.drawable.ic_close_black_24dp, R.color.tint_list_grey));
        initFlowReference(mFlowReference);

        initHintAdapter();
        initHintHttp();

        mACTVSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.d(mListHttpHint.get(position));
                SearchResultActivity.launch(SearchAndTypeActivity.this, mListHttpHint.get(position));
            }
        });

        RxTextView.editorActions(mACTVSearch, integer -> integer==EditorInfo.IME_ACTION_SEARCH).throttleFirst(500,TimeUnit.MILLISECONDS)
                .subscribe(integer -> {initActionSearch();});

        initClearHistory();//点击按钮 清除历史记录的操作
    }

    private void initHintHttp() {
        //// TODO: 2016/4/20 0020 目前存在bug okhttp的取消产生线程中断异常 目前在convert做异常捕捉
        //调用onError后不再监听输入 由于bug导致
        RxTextView.textChanges(mACTVSearch)//观察mACTVSearch的输入变化
                .observeOn(Schedulers.io())
                .filter(charSequence -> {
                    return charSequence.length() > 0;//过滤空输入
                })
                //debounce 函数 过滤掉由Observable发射的速率过快的数据
                .debounce(300, TimeUnit.MILLISECONDS)
                //switchMap函数 每当源Observable发射一个新的数据项（Observable）时，
                //它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个。
                .switchMap(new Func1<CharSequence, Observable<SearchHintBean>>() {
                    @Override
                    public Observable<SearchHintBean> call(CharSequence charSequence) {
                        return RetrofitClient.createService(SearchAPI.class)
                                .httpsSearHintBean(mAuthorization, charSequence.toString());
                    }
                })
                .map(SearchHintBean::getResult)
                .filter(strings -> (strings != null) && (strings.size() > 0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    mListHttpHint.clear();
                    mListHttpHint.addAll(strings);
                    mAdapter.notifyDataSetChanged();
                }, throwable -> {
                    Logger.d(throwable.toString());
                });
    }


    private void initHintAdapter() {

        mAdapter = new SearHintAdapter(mContext,
                android.R.layout.simple_spinner_dropdown_item, mListHttpHint);

        mACTVSearch.setAdapter(mAdapter);

    }

    private void initActionSearch() {
        if (mACTVSearch.getText().length() > 0) {
            SearchResultActivity.launch(SearchAndTypeActivity.this, mACTVSearch.getText().toString());
        }
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
                        SPUtils.remove(mContext, Constant.HISTORYTEXT);
                        addChildTextTips(mFlowHistory, mStringNotHistory);
                    }
                });
    }

    private void initFlowHistory(FlowLayout mFlowHistory) {
        mFlowHistory.removeAllViews();
//        String mTextList[] = getResources().getStringArray(R.array.title_array_all);//显示的文字
        Set<String> mTextList = (HashSet<String>) SPUtils.get(mContext, Constant.HISTORYTEXT, new HashSet<>());
        if (!mTextList.isEmpty()) {
            for (String mTextString : mTextList) {
                Logger.d(mTextString);
                addChildText(mFlowHistory, mTextString);
            }
        } else {
            addChildTextTips(mFlowHistory, mStringNotHistory);
        }
    }

    private void initFlowReference(FlowLayout mFlowReference) {
        String mTextList[] = getResources().getStringArray(R.array.title_array_all);//显示的文字
        String mTypeList[] = getResources().getStringArray(R.array.type_array_all);//查询的关键字
//        int[] ColorResId=getResources().getIntArray(R.array.type_array_color);
//        int[] drawableResIdList=new int[]{R};
        mItemWidth = Utils.getScreenWidth(mContext) / mItemLineNumber - mItemMargin * 2;//每个子控件宽为屏幕的等分
//        mItemWidth= Utils.getScreenWidth(mContext)/mItemLineNumber;
        //根据内容动态填充
        for (int i = 0, size = mTextList.length; i < size; i++) {
            addChildButton(mFlowReference, mTextList[i], mTypeList[i], R.drawable.ic_loyalty_white_24dp);
        }

    }

    private void addChildTextTips(FlowLayout group, String mTextString) {
        TextView tvChild = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(mItemTVMargin, mItemTVMargin, mItemTVMargin, mItemTVMargin);
        tvChild.setText(mTextString);
        tvChild.setLayoutParams(layoutParams);
        tvChild.setGravity(Gravity.CENTER_HORIZONTAL);
        group.addView(tvChild);
    }

    private void addChildText(FlowLayout group, String mTextString) {
        TextView tvChild = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_textview_history, group, false);
//        final TextView tvChild = new TextView(mContext);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.setMargins(mItemTVMargin, mItemTVMargin, mItemTVMargin, mItemTVMargin);
        tvChild.setText(mTextString);
//        tvChild.setTextColor(Color.WHITE);
//        Drawable drawable=getResources().getDrawable(R.drawable.bg_text_history);
//        tvChild.setBackground(drawable);
//        tvChild.setLayoutParams(layoutParams);
//        tvChild.setGravity(Gravity.CENTER_HORIZONTAL);

        tvChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.launch(SearchAndTypeActivity.this, ((TextView) v).getText().toString());
            }
        });

        group.addView(tvChild);
    }

    private void addChildButton(FlowLayout group, String text, String type, int ResId) {
        Button btnChild = new Button(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(mItemMargin, mItemMargin, mItemMargin, mItemMargin);
        btnChild.setCompoundDrawablesWithIntrinsicBounds(
                null,
                CompatUtils.getTintListDrawable(mContext, ResId, R.color.tint_list_pink),
                null,
                null);
        btnChild.setText(text);
        btnChild.setBackgroundColor(Color.WHITE);
        btnChild.setTag(type);
//        btnChild.setBackgroundColor(ResId);
        btnChild.setLayoutParams(layoutParams);
        btnChild.setGravity(Gravity.CENTER);

        btnChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                TypeActivity.launch(SearchAndTypeActivity.this, textView.getText().toString(), textView.getTag().toString());
            }
        });

        group.addView(btnChild);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

//        SearchView searchView = (SearchView) MenuItemCompat
//                .getActionView(menu.findItem(R.id.action_search));
//        initSearchView(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d("menu id=" + item.getItemId());
        switch (item.getItemId()) {
            case R.id.action_search:
                initActionSearch();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
