package licola.demo.com.huabandemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import licola.demo.com.huabandemo.Util.Logger;
import retrofit.Call;

/**
 * Created by LiCola on  2015/12/19  20:19
 */
public abstract class BaseFragment extends Fragment {
    protected Call mCall;

    protected String TAG =getTAG();

    protected abstract String getTAG();

    protected View mRootView;

    protected abstract  int getLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG);
        if (null==mRootView){
            mRootView =inflater.inflate(getLayoutId(),null);
        }
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(TAG);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG);

    }


}
