package kotleni.b0mb3r.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dm.bomber.R;

public class CountryCodeAdapter extends BaseAdapter {
    private final String[] countryCodes;
    private final LayoutInflater inflater;
    private final Activity context;

    public CountryCodeAdapter(Activity context,String[] countryCodes) {
        this.countryCodes = countryCodes;

        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return countryCodes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_countrycode, parent, false);

            view.setTag(holder = new ViewHolder(view));
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //.icon.setImageResource(flags[index]);
        ((TextView)holder.rootView.findViewById(R.id.code)).setText(String.format("+%s", countryCodes[index]));

        return view;
    }

    private static class ViewHolder {
        View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
        }
    }
}