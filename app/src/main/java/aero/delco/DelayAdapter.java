package aero.delco;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// class adapting the listview for keyword search

public class DelayAdapter extends ArrayAdapter<DelayCode> {

	private Context c;

	public DelayAdapter(Context context, int textViewResourceId,
			ArrayList<DelayCode> codes) {
		super(context, textViewResourceId, codes);
		c = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		TextView number = null;
		TextView content = null;

		DelayCode delayCode = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(c).inflate(R.layout.child_row,
					null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		number = viewHolder.getNumber();
		number.setText(delayCode.getNumber());
		content = viewHolder.getContent();
		content.setText(Html.fromHtml(delayCode.getContent()));

		Utils.setupFontSize(c, number);
		Utils.setupFontSize(c, content);

		return convertView;
	}
}