package licola.demo.com.huabandemo.Base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;

import licola.demo.com.huabandemo.Util.Logger;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LiCola on  2016/05/06  14:38
 * 预留的所有DialogFragment抽象父类
 */
public abstract class BaseDialogFragment extends AppCompatDialogFragment {
    protected String TAG = getTAG();

    protected abstract String getTAG();

    //联网的授权字段 几乎所有的Fragment子类都有联网功能 故父类提供变量
    protected String mAuthorization;

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (s == null) {
            return;
        }

        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " @" + Integer.toHexString(hashCode());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Logger.d(TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG);
    }

    public void throwRuntimeException(Context context) {
        throw new RuntimeException(context.toString()
                + " must implement OnDialogInteractionListener");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG);
        if (this.mCompositeSubscription != null) {

            this.mCompositeSubscription.unsubscribe();
        }
        HuaBanApplication.getRefwatcher(getActivity()).watch(this);
    }
}
