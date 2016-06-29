package com.klouddata.dynamicview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.entitytypes_data.Revision;

import java.util.ArrayList;
import java.util.List;

public class ComponentACVAdapter extends ArrayAdapter<String> {

	private final int textViewResourceID;
	private Context context;
	private ArrayList<String> list;
	private List<String> list1;

	public ComponentACVAdapter(Context context, int textViewResourceId,
							   ArrayList<String> objects) {
		super(context, textViewResourceId, objects);

		textViewResourceID = textViewResourceId;

		this.context = context;
		this.list = objects;

		list1 = (ArrayList<String>) list.clone();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView tv;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item, null);
		}

		tv = (TextView) convertView.findViewById(R.id.tv_list);
		tv.setText(list.get(position));
		return convertView;
	}


	private Filter mFilter = new Filter() {

		@Override
		public CharSequence convertResultToString(Object resultValue) {

			Revision bean = (Revision) resultValue;

			return "" + bean.getREV();
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			clear();

			if (results != null && results.count > 0) {

				addAll((ArrayList<String>) results.values);

			} else {
				list1.size();
				addAll(list1);
			}
			notifyDataSetChanged();
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			FilterResults filterResults = new FilterResults();
			ArrayList<String> suggestionList;
			if (constraint != null) {
				suggestionList = new ArrayList<String>();
				for (String bean : list1) {
					if (bean.toLowerCase()
							.contains(constraint)) {
						suggestionList.add(bean);
					}
				}
				filterResults.values = suggestionList;
				filterResults.count = suggestionList.size();
			}

			return filterResults;
		}
	};

	@Override
	public Filter getFilter() {
		return mFilter;
	}

}
