package aero.delco;

import android.view.View;
import android.widget.TextView;

public class ViewHolder {

	private View mRow;
	
	private TextView number = null;
	private TextView content = null;
	
	
	public ViewHolder(View row) {
		mRow = row;
	}
	
	public TextView getNumber() {
		if (null == number) {
			number = (TextView) mRow.findViewById(R.id.tvCodeNumber);
		}
		return number;
	}		
	
	public TextView getContent() {
		if (null == content) {
			content = (TextView) mRow.findViewById(R.id.tvCodeContent);
		}
		return content;
	}	
}
