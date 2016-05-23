package licola.demo.com.huabandemo.Module.Search;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiCola on  2016/03/21  0:41
 * 主要用在输入提示的adapter
 */
public class SearHintAdapter extends ArrayAdapter<String> {
    private static final String TAG = "SearHintAdapter";

    private Filter mFilter;
    private List<String> mObjects;


    public SearHintAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mObjects = objects;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new HintFilter();
        }
        return mFilter;
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     * 重写过滤类 自定义一个不会过滤任何数的Filter
     */
    private class HintFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {


            ArrayList<Object> suggestions = new ArrayList<Object>();
            for (String s : mObjects) {
                suggestions.add(s);
//                Logger.d(s);
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();
//            Logger.d("filterResults.count=" + filterResults.count);
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mObjects = (List<String>) results.values;
//            Logger.d("results.count=" + results.count);
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
