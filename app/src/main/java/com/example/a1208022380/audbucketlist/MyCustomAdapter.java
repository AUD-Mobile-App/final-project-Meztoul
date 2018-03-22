package com.example.a1208022380.audbucketlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 1208022380 on 3/10/2018.
 */

public class MyCustomAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    ArrayList<MyItem> mylist = new ArrayList<>();
    ArrayList <MyItem> mOriginalValues;

    public MyCustomAdapter(ArrayList<MyItem> itemArray, Context mContext){
        super();
        this.mContext=mContext;
        this.mOriginalValues =itemArray;
        this.mylist=itemArray;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void onItemSelected (int position){

    }
    public class ViewHolder{
        public TextView nametext;
        public TextView contenttext;
        public CheckBox tick;
    }
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {




            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults(); // Holds the

                if(constraint != null && constraint.length()>0) {
                    constraint= constraint.toString().toUpperCase();
                    ArrayList<MyItem> filteredItems = new ArrayList<>();

                    for (int i=0; i<mOriginalValues.size();i++){
                        if(mOriginalValues.get(i).getTitle().toUpperCase().contains(constraint)){
                            filteredItems.add(mOriginalValues.get(i));
                        }
                }

                    // set the Filtered result to return
                    results.count = filteredItems.size();
                    results.values = filteredItems;

                }else{
                    results.count =mOriginalValues.size();
                    results.values=mOriginalValues;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {

                mylist = (ArrayList<MyItem>) results.values; // has

                notifyDataSetChanged();
            }
        };


        return filter;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder view = null;
        LayoutInflater inflator = ((Activity) mContext).getLayoutInflater();
        if(view ==null){
            view = new ViewHolder();
            convertView=inflator.inflate(R.layout.myadapter,null);
            view.nametext= convertView.findViewById(R.id.adapterText1);
            view.contenttext= convertView.findViewById(R.id.adapterText2);
            view.tick=convertView.findViewById(R.id.adapterCHeck);
            final TextView n = view.nametext;
            view.tick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (int) buttonView.getTag();
                    mylist.get(getPosition).setChecked(buttonView.isChecked());
                    if (isChecked){
                        n.setPaintFlags(n.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }else{
                        n.setPaintFlags(0);
                    }
                }
            });

            convertView.setTag(view);
        }else{
            view = (ViewHolder) convertView.getTag();
        }
        view.tick.setTag(position);
        view.nametext.setText(""+ mylist.get(position).getTitle());
        view.contenttext.setText(""+mylist.get(position).getContent());
        view.tick.setChecked(mylist.get(position).isChecked());
        return convertView;
    }
}
