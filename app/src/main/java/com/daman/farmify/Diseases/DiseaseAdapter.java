package com.daman.farmify.Diseases;

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
import com.daman.farmify.profile.ListBean;

import java.util.ArrayList;

/**
 * Created by Daman on 18-05-2017.
 */

public class DiseaseAdapter extends ArrayAdapter<DiseaseBean> {
    int resource;
    Context context;
    ArrayList<DiseaseBean> al;
    public DiseaseAdapter(Context context, int resource, ArrayList<DiseaseBean> objects) {
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

        TextView title= (TextView) view.findViewById(R.id.text_title);
        TextView content= (TextView) view.findViewById(R.id.text_content);
        DiseaseBean db = al.get(position);

        title.setText(db.getTitle());
        content.setText(db.getContent());
        return view;
    }
}