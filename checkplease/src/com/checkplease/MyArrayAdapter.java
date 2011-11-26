package com.checkplease;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<Item> {
	private final List<Item> list;
	private final Activity context;
	
	public MyArrayAdapter(Activity context, List<Item> list) {
		super(context, R.layout.rowlayout, list);
		this.context = context;
		this.list = list;
	}
	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		public ImageView imageView;
		public TextView nameTextView;
		public TextView priceTextView;
		protected CheckBox checkbox;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder will buffer the assess to the individual fields of the row
		// layout
		View view = null;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(R.layout.rowlayout, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) view.findViewById(R.id.label);
			viewHolder.priceTextView = (TextView) view.findViewById(R.id.price);
			viewHolder.imageView = (ImageView)view.findViewById(R.id.icon);
			viewHolder.checkbox = (CheckBox)view.findViewById(R.id.check);
			
			viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					Item element = (Item) viewHolder.checkbox
							.getTag();
					element.setSelected(buttonView.isChecked());
				
				}
			});
			
			view.setTag(viewHolder);
			viewHolder.checkbox.setTag(list.get(position));
		} 
		else {
				view = convertView;
				((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.nameTextView.setText(list.get(position).getName());
		holder.priceTextView.setText(list.get(position).getPrice().toString());
		holder.imageView.setImageBitmap(list.get(position).getFoodImage());
		holder.checkbox.setChecked(list.get(position).isSelected());
 	
		return view;
	}	

}
