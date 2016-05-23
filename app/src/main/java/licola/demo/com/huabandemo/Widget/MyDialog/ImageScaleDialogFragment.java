package licola.demo.com.huabandemo.Widget.MyDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.CloseableImage;

import butterknife.ButterKnife;
import licola.demo.com.huabandemo.Base.BaseDialogFragment;
import licola.demo.com.huabandemo.R;

/**
 * Created by LiCola on  2016/05/22  15:59
 */

public class ImageScaleDialogFragment extends BaseDialogFragment {

    SubsamplingScaleImageView mImgScale;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static ImageScaleDialogFragment create() {
        Bundle bundle = new Bundle();

        ImageScaleDialogFragment fragment = new ImageScaleDialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView=inflater.inflate(R.layout.dialog_scale,container,false);
        mImgScale= ButterKnife.findById(dialogView,R.id.img_scale);
        mImgScale.setImage(ImageSource.asset("squirrel.jpg"));

        return dialogView;
    }
}
