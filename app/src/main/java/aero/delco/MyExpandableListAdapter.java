package aero.delco;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> groups;

	private ArrayList<ArrayList<DelayCode>> children;

	private Context context;
	
	private int fontSize;

	public MyExpandableListAdapter(Context context, List<String> groups,
			List<ArrayList<DelayCode>> children) {
		this.context = context;
		this.groups = (ArrayList<String>) groups;
		this.children = (ArrayList<ArrayList<DelayCode>>) children;
		
		fontSize = Utils.getSharedFontSize(context);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public DelayCode getChild(int groupPosition, int childPosition) {
		return children.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		DelayCode child = (DelayCode) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.child_row, null);
		}

		TextView codeNumber = (TextView) convertView
				.findViewById(R.id.tvCodeNumber);
		TextView codeContent = (TextView) convertView
				.findViewById(R.id.tvCodeContent);

		codeNumber.setText(child.getNumber());
		codeContent.setText(Html.fromHtml(child.getContent()));
		setupFontSize(codeNumber);
		setupFontSize(codeContent);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return children.get(groupPosition).size();
	}

	@Override
	public String getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		String group = (String) getGroup(groupPosition);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.group_row, null);
		}

		TextView tvGroup = (TextView) convertView
				.findViewById(R.id.tvGroupName);

		tvGroup.setText(group);
		setupFontSizeTitle(tvGroup);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	private void setupFontSize(TextView tv) {

		switch (fontSize) {
		case Utils.FONT_SIZE_SMALL:
			tv.setTextAppearance(context, R.style.fontTextSmall);
			break;

		case Utils.FONT_SIZE_MEDIUM:
			tv.setTextAppearance(context, R.style.fontTextMedium);
			break;

		case Utils.FONT_SIZE_LARGE:
			tv.setTextAppearance(context, R.style.fontTextLarge);
			break;

		default:
			tv.setTextAppearance(context, R.style.fontTextMedium);
			break;
		}
	}

	private void setupFontSizeTitle(TextView tv) {

		switch (fontSize) {
		case Utils.FONT_SIZE_SMALL:
			tv.setTextAppearance(context, R.style.fontTitleSmall);
			break;

		case Utils.FONT_SIZE_MEDIUM:
			tv.setTextAppearance(context, R.style.fontTitleMedium);
			break;

		case Utils.FONT_SIZE_LARGE:
			tv.setTextAppearance(context, R.style.fontTitleLarge);
			break;

		default:
			tv.setTextAppearance(context, R.style.fontTitleMedium);
			break;
		}
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
}