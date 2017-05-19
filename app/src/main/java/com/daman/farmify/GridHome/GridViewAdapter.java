package com.daman.farmify.GridHome;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daman.farmify.R;
import com.daman.farmify.profile.ListBean;

import java.util.ArrayList;

/**
 * Created by Daman on 11-05-2017.
 */

public class GridViewAdapter extends ArrayAdapter<ImageItem> {
     Context context;
     int resource;
     ArrayList<ImageItem> data ;

    public GridViewAdapter(Context context, int resource, ArrayList<ImageItem> data) {
        super(context, resource, data);
        this.resource = resource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= null;
        view = LayoutInflater.from(context).inflate(resource,parent,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView name= (TextView) view.findViewById(R.id.text);
        //TextView hint= (TextView) view.findViewById(R.id.text_hint);
        ImageItem item = data.get(position);
        imageView.setBackgroundResource(item.getImage());
        name.setText(item.getTitle());

        return view;
}
}
