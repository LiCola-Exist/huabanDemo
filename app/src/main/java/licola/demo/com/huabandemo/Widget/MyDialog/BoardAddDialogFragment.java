package licola.demo.com.huabandemo.Widget.MyDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import butterknife.ButterKnife;
import licola.demo.com.huabandemo.API.Dialog.OnAddDialogInteractionListener;
import licola.demo.com.huabandemo.Base.BaseDialogFragment;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;

/**
 * Created by LiCola on  2016/05/30  18:07
 */

public class BoardAddDialogFragment extends BaseDialogFragment {


    EditText mEditTextBoardName;
    EditText mEditTextBoardDescribe;
    Spinner mSpinnerBoardTitle;


    private Context mContext;

    private String mStringBoardName;

    private String mStringBoardType;

    private String[] titleList;

    private boolean isChange = false;//输入值是否有变化 默认false

    OnAddDialogInteractionListener mListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    //依赖注入 因为对话框可能由其他Fragment调用 就不在使用onAttach获取实现
    public void setListener(OnAddDialogInteractionListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static BoardAddDialogFragment create() {
//        Bundle bundle = new Bundle();

        BoardAddDialogFragment fragment = new BoardAddDialogFragment();
//        fragment.setArguments(bundle);

        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Logger.d(TAG);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_title_add));
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View dialogView = factory.inflate(R.layout.dialog_board_edit, null);
        initView(dialogView);//初始化View
        setData();//设置数据
        builder.setView(dialogView);
        //消极操作 不需要返回
        builder.setNegativeButton(R.string.dialog_negative, null);

        //积极操作 回调用户输入
        builder.setPositiveButton(R.string.dialog_add_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d();
                //如果检测到值有变化才有回调
                if (DataChange()) {
                    String boardName=mEditTextBoardName.getText().toString();
                    if (TextUtils.isEmpty(boardName)){
                        boardName=mEditTextBoardName.getHint().toString();
                    }
                    mListener.onDialogPositiveClick(boardName, mEditTextBoardDescribe.getText().toString(), mStringBoardType);
                }
            }
        });

        return builder.create();
    }

    private boolean DataChange() {
        //Spinner 控件会影响 所以先取内部变量
        boolean isChange = this.isChange;
        if (isChange) {
            return true;
        }

        String  input;
        input=mEditTextBoardName.getText().toString();
        if ((!TextUtils.isEmpty(input)) && (!input.equals(mStringBoardName))) {
            return true;
        }
        return false;
    }

    private void setData() {
        //画板名称 设置默认值

        mEditTextBoardName.setHint(R.string.text_is_default);
        mStringBoardName=mEditTextBoardName.getHint().toString();

        //画板类型 定义在本地资源中
        titleList = getResources().getStringArray(R.array.title_array_all);
        String[] typeList = getResources().getStringArray(R.array.type_array_all);
        int selectPosition = 0;//默认选中第一项

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

    private void initView(View dialogView) {
        mEditTextBoardName = ButterKnife.findById(dialogView, R.id.edit_board_name);
        mEditTextBoardDescribe = ButterKnife.findById(dialogView, R.id.edit_board_describe);
        mSpinnerBoardTitle = ButterKnife.findById(dialogView, R.id.spinner_title);

    }

}
