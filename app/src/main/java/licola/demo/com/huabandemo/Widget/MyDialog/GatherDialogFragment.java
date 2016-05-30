package licola.demo.com.huabandemo.Widget.MyDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.ButterKnife;
import licola.demo.com.huabandemo.API.Dialog.OnGatherDialogInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.OperateAPI;
import licola.demo.com.huabandemo.Base.BaseDialogFragment;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.Module.ImageDetail.GatherInfoBean;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/05/06  14:40
 */
public class GatherDialogFragment extends BaseDialogFragment {

    private static final String KEYAUTHORIZATION = "keyAuthorization";
    private static final String KEYVIAID = "keyViaId";
    private static final String KEYDESCRIBE = "keyDescribe";
    private static final String KEYBOARDTITLEARRAY = "keyBoardTitleArray";


    EditText mEditTextDescribe;
    TextView mTVGatherWarning;
    Spinner mSpinnerBoardTitle;


    private Context mContext;
    private String mViaId;
    private String mDescribeText;
    private String[] mBoardTitleArray;

    private int mSelectPosition=0;//默认的选中项

    OnGatherDialogInteractionListener mListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnGatherDialogInteractionListener){
            mListener= (OnGatherDialogInteractionListener) context;
        }else {
            throwRuntimeException(context);
        }

    }

    public static GatherDialogFragment create(String Authorization,String viaId, String describe, String[] boardTitleArray) {
        Bundle bundle = new Bundle();
        bundle.putString(KEYAUTHORIZATION,Authorization);
        bundle.putString(KEYVIAID, viaId);
        bundle.putString(KEYDESCRIBE, describe);
        bundle.putStringArray(KEYBOARDTITLEARRAY, boardTitleArray);
        GatherDialogFragment fragment = new GatherDialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mAuthorization=args.getString(KEYAUTHORIZATION);
            mViaId = args.getString(KEYVIAID);
            mDescribeText = args.getString(KEYDESCRIBE);
            mBoardTitleArray = args.getStringArray(KEYBOARDTITLEARRAY);
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Logger.d(TAG);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_title_gather));
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View dialogView = factory.inflate(R.layout.dialog_gather, null);
        initView(dialogView);
        builder.setView(dialogView);

        builder.setNegativeButton(R.string.dialog_negative,null);
        builder.setPositiveButton(R.string.dialog_gather_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d();
                //取出输入字符串 没有输入用hint文本作为默认值
                String input=mEditTextDescribe.getText().toString();
                if (TextUtils.isEmpty(input)){
                    input=mEditTextDescribe.getHint().toString();
                }
                mListener.onDialogPositiveClick(input,mSelectPosition);
            }
        });

        addSubscription(getGatherInfo());

        return builder.create();
    }


    //可能需要保存数据的回调 一般是按下Home键
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d();
    }

    //取消的回调
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Logger.d(dialog.toString());
    }

    private void initView(View dialogView) {
        mEditTextDescribe = ButterKnife.findById(dialogView, R.id.edit_describe);
        mTVGatherWarning = ButterKnife.findById(dialogView, R.id.tv_gather_warning);
        mSpinnerBoardTitle = ButterKnife.findById(dialogView, R.id.spinner_title);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, mBoardTitleArray);
        if (!TextUtils.isEmpty(mDescribeText)) {
            mEditTextDescribe.setHint(mDescribeText);
        }else {
            mEditTextDescribe.setHint(R.string.text_image_describe_null);
        }
        mSpinnerBoardTitle.setAdapter(adapter);
        mSpinnerBoardTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Logger.d("position="+position);
                mSelectPosition=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    public Subscription getGatherInfo() {
        return RetrofitClient.createService(OperateAPI.class)
                .httpsGatherInfo(mAuthorization, mViaId, Constant.OPERATECHECK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GatherInfoBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(GatherInfoBean gatherInfoBean) {
                        Logger.d("this pin exist =" + (gatherInfoBean.getExist_pin() != null));
                        if (gatherInfoBean.getExist_pin()!=null){
                            String formatWarning = getResources().getString(R.string.text_gather_warning);
                            mTVGatherWarning.setVisibility(View.VISIBLE);
                            mTVGatherWarning.setText(String.format(formatWarning, gatherInfoBean.getExist_pin().getBoard().getTitle()));
                        }


                    }
                });
    }
}
