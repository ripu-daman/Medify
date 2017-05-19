package com.daman.farmify.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daman.farmify.R;

import java.util.ArrayList;

/**
 * Created by Daman on 09-05-2017
 */

public class ListAdapter extends ArrayAdapter<ListBean> {
    int resource;
    Context context;
    ArrayList<ListBean> al;
    public ListAdapter(Context context, int resource, ArrayList<ListBean> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        al = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= null;
        view = LayoutInflater.from(context).inflate(resource,parent,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView name= (TextView) view.findViewById(R.id.text_title);
        TextView hint= (TextView) view.findViewById(R.id.text_hint);
        ListBean lb = al.get(position);
        imageView.setBackgroundResource(lb.getLogo());
        name.setText(lb.getName());
        hint.setText(lb.getHint());
        return view;
}
}

