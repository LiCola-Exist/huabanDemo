package licola.demo.com.huabandemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import licola.demo.com.huabandemo.API.ViewInject;
import licola.demo.com.huabandemo.Util.Logger;
import retrofit.Call;

/**
 * Created by LiYi on 2015/11/4 0004 14:59.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG =getTAG();

    protected abstract  int getLayoutId();

    protected abstract  String getTAG();

    protected Call mCall;

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext=this;
        Logger.d(TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall!=null){
            mCall.cancel();
            Logger.d("mCall cancel");
        }else {
            Logger.d("mCall not cancel");
        }
        Logger.d(TAG);
    }


}
