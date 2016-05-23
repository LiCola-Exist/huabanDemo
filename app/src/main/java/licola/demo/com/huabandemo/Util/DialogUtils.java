package licola.demo.com.huabandemo.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import licola.demo.com.huabandemo.R;

/**
 * Created by LiCola on  2016/05/10  18:25
 */
public class DialogUtils {
    public static void showDeleteDialog(Context mContext, String boardTitle, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.drawable.ic_report_problem_black_24dp);
        builder.setTitle(mContext.getString(R.string.dialog_title_delete));
        String message=mContext.getString(R.string.dialog_delete_message);
        builder.setMessage(String.format(message,boardTitle));
        builder.setPositiveButton(mContext.getString(R.string.dialog_delete_positive), listener);
        builder.setNegativeButton(mContext.getString(R.string.dialog_negative), null);
        builder.show();
    }
}
