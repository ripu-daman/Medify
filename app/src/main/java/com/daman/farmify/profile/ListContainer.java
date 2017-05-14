package com.daman.farmify.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daman.farmify.R;
import com.daman.farmify.UserBean;
import com.daman.farmify.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListContainer extends AppCompatActivity implements AdapterView.OnItemClickListener{

//    UNUSED ACTIVITY- THE CONTROL HAS BEEN SHIFTED TO THE PROFILE FRAGMENT
    UserBean bean;
    ListBean listBean;
    ListAdapter adapter;
    ArrayList<ListBean> list;
    ListView listView;
    String name,phone,password,dob,email;
    RequestQueue requestQueue;

    ProgressDialog progressDialog;
    public void initlist(){
        listView= (ListView) findViewById(R.id.listview);

        list= new ArrayList<>();
        ListBean basicinfo= new ListBean(R.drawable.basicinfo,"Basic Info","View and edit your basic info.");
        ListBean healthchart= new ListBean(R.drawable.healthchart,"Health Chart","View your health chart");

        list.add(basicinfo);
        list.add(healthchart);

        adapter = new ListAdapter(this,R.layout.list_item,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_container);
        initlist();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            Intent intent=new Intent(getApplicationContext(),BasicInfo.class);

            SharedPreferences sharedPreferences = getSharedPreferences("signupsp", Context.MODE_PRIVATE);
            email=sharedPreferences.getString("email","");
            name=sharedPreferences.getString("name","");
            phone=sharedPreferences.getString("phone","");
            password=sharedPreferences.getString("password","");
            dob=sharedPreferences.getString("dob","");
            Log.i("Email",email);
            Log.i("name",name);
            Log.i("phone",phone);
            Log.i("password",password);
            intent.putExtra("name",name);
            intent.putExtra("phone",phone);
            intent.putExtra("password",password);
            intent.putExtra("dob",dob);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
