package licola.demo.com.huabandemo.Widget;

import android.content.Context;

import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;

/**
 * Created by LiCola on  2016/04/18  21:57
 */
public class MyLikeActionProvider extends ActionProvider {

    /** Context for accessing resources. */
    private final Context mContext;
    private ImageButton mImageButton;

    /**
     * Creates a new instance. ActionProvider classes should always implement a
     * constructor that takes a single Context parameter for inflating from menu XML.
     *
     * @param context Context for accessing resources.
     */
    public MyLikeActionProvider(Context context) {
        super(context);
        this.mContext=context;
        Logger.d();
    }

    @Override
    public View onCreateActionView() {
        return null;
    }



    @Override
    public View onCreateActionView(MenuItem forItem) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view=layoutInflater.inflate(R.layout.view_action_bar_like,null);
        mImageButton = (ImageButton) view.findViewById(R.id.action_bar_image);
        // Attach a click listener for launching the system settings.
//        mImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logger.d(forItem.getTitle()+" "+forItem.isChecked());
//            }
//        });
        return view;
    }
}
