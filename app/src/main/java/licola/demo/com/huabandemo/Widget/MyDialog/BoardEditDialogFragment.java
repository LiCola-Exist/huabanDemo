package licola.demo.com.huabandemo.Widget.MyDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.ButterKnife;
import licola.demo.com.huabandemo.API.Dialog.OnEditDialogInteractionListener;
import licola.demo.com.huabandemo.Base.BaseDialogFragment;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;

/**
 * Created by LiCola on  2016/05/06  14:40
 * 画板的修改对话框
 * 原则上 不在对话框中做网络操作 因为对话框容易被取消 网络操作不稳定
 * 把网络操作回调给调用者
 */
public class BoardEditDialogFragment extends BaseDialogFragment {

    //bundle key
    private static final String KEYBOARDID = "keyBoardId";
    private static final String KEYBOARDNAME = "keyBoardName";
    private static final String KEYDESCRIBE = "keyDescribe";
    private static final String KEYBOARDTYPE = "keyBoardType";

    //UI
    EditText mEditTextBoardName;
    EditText mEditTextBoardDescribe;
    Spinner mSpinnerBoardTitle;


    private Context mContext;

    //外部传入的值
    private String mStringBoardId;
    private String mStringBoardName;
    private String mStringDescribe;
    private String mStringBoardType;

    //画板类型 通过context 获取本地资源
    private String[] titleList;

    private boolean isChange = false;//输入值是否有变化 默认false

    OnEditDialogInteractionListener mListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    //依赖注入 因为对话框可能由其他Fragment调用 就不在使用onAttach获取实现
    public void setListener(OnEditDialogInteractionListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static BoardEditDialogFragment create(String boardId, String name, String describe, String boardTitle) {
        Bundle bundle = new Bundle();
        bundle.putString(KEYBOARDID, boardId);
        bundle.putString(KEYBOARDNAME, name);
        bundle.putString(KEYDESCRIBE, describe);
        bundle.putString(KEYBOARDTYPE, boardTitle);
        BoardEditDialogFragment fragment = new BoardEditDialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mStringBoardId = args.getString(KEYBOARDID);
            mStringBoardName = args.getString(KEYBOARDNAME);
            mStringDescribe = args.getString(KEYDESCRIBE);
            mStringBoardType = args.getString(KEYBOARDTYPE);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Logger.d(TAG);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_title_edit));
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View dialogView = factory.inflate(R.layout.dialog_board_edit, null);
        initView(dialogView);//初始化View
        setData();//设置值
        builder.setView(dialogView);
        //消极操作 不需要返回
        builder.setNegativeButton(R.string.dialog_negative, null);
        //中立操作 这里修改成删除操作传递回调用者 回调id name
        builder.setNeutralButton(R.string.dialog_delete_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d();
                mListener.onDialogNeutralClick(mStringBoardId,mStringBoardName);
            }
        });
        //积极操作 回调用户输入
        builder.setPositiveButton(R.string.dialog_edit_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d();
                //如果检测到值有变化才有回调
                if (DataChange()) {
                    mListener.onDialogPositiveClick(mStringBoardId,
                            mEditTextBoardName.getText().toString(), mEditTextBoardDescribe.getText().toString(), mStringBoardType);
                }
            }
        });

        return builder.create();
    }

    /**
     * 判定数据是否变化
     * true 值有变化 false 没有变化
     *
     * @return
     */
    private boolean DataChange() {
        //Spinner 控件会影响 所以先取内部变量
        boolean isChange = this.isChange;
        if (isChange) {
            return true;
        }
        //临时变量
        String input;
        input = mEditTextBoardName.getText().toString().trim();//取名称输入框值
        //判断 不为空 并且值有变化
        if ((!TextUtils.isEmpty(input)) && (!input.equals(mStringBoardName))) {
            return true;
        }

        input = mEditTextBoardDescribe.getText().toString().trim();//取描述输入框值
        if ((!TextUtils.isEmpty(input)) && (!input.equals(mStringDescribe))) {
            return true;
        }

        return false;
    }

    private void setData() {
        //画板名称
        if (!TextUtils.isEmpty(mStringBoardName)) {
            mEditTextBoardName.setText(mStringBoardName);
        } else {
            mEditTextBoardName.setText(R.string.text_is_default);
        }

        //画板描述 可以为空
        mEditTextBoardDescribe.setText(mStringDescribe);

        //画板类型 定义在本地资源中
        titleList = getResources().getStringArray(R.array.title_array_all);
        String[] typeList = getResources().getStringArray(R.array.type_array_all);
        int selectPosition = 0;//默认选中第一项

        if (mStringBoardType != null) {
            //遍历查找
            for (int i = 0, size = titleList.length; i < size; i++) {
                if (typeList[i].equals(mStringBoardType)) {
                    selectPosition = i;
                }
            }
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, titleList);
        mSpinnerBoardTitle.setAdapter(adapter);
        mSpinnerBoardTitle.setSelection(selectPosition);
        mSpinnerBoardTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Logger.d("position=" + position);
                //选中监听事件 产生变化 赋值
                String selected = typeList[position];
                if (!selected.equals(mStringBoardType)) {
                    mStringBoardType = typeList[position];
                    isChange = true;//有选择 就表示数据发生变化
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        mEditTextBoardName = ButterKnife.findById(dialogView, R.id.edit_board_name);
        mEditTextBoardDescribe = ButterKnife.findById(dialogView, R.id.edit_board_describe);
        mSpinnerBoardTitle = ButterKnife.findById(dialogView, R.id.spinner_title);

    }

}
