package com.daman.farmify.Medication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daman.farmify.R;

import java.util.ArrayList;

public class UserMedicineActivity extends AppCompatActivity {
    ListView listView;
    Button clearList;
    String medi1,medi2,medi3,medi4;
    ArrayList<String> data;
    ArrayAdapter<String> arrayAdapter;
    TextView headerText;
    boolean signal;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_medicine);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView= (ListView) findViewById(R.id.custom_medicine_list);
        clearList= (Button) findViewById(R.id.clear_medi);
        headerText= (TextView) findViewById(R.id.header);
         sharedPreferences =getSharedPreferences("CustomMedicine", Context.MODE_PRIVATE);
        medi1=sharedPreferences.getString("medi1","");
        medi2=sharedPreferences.getString("medi2","");
        medi3=sharedPreferences.getString("medi3","");
        medi4=sharedPreferences.getString("medi4","");
        signal=sharedPreferences.getBoolean("signal",false);
        if(signal){
            initList();
            clearList.setVisibility(View.VISIBLE);
        }
        clearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserMedicineActivity.this);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor = sharedPreferences.edit();
                        editor.putString("medi1","");
                        editor.putString("medi2","");
                        editor.putString("medi3","");
                        editor.putString("medi4","");
                        editor.putBoolean("signal",false);
                        editor.commit();
                        data.remove(medi1);
                        data.remove(medi2);
                        data.remove(medi3);
                        data.remove(medi4);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                builder.setMessage("Are you sure you want to clear the list?");
                 alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public void initList(){
        headerText.setText("Your Custom Medicine List");
        headerText.setBackgroundResource(R.color.green);
        data= new ArrayList<String>();
        data.add(medi1);
        data.add(medi2);
        data.add(medi3);
        data.add(medi4);
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.user_medicine_list_item,R.id.textView,data);
        listView.setAdapter(arrayAdapter);

    }
}
