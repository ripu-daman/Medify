package com.daman.farmify.Diseases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daman.farmify.HomeActivity;
import com.daman.farmify.LoginActivity;
import com.daman.farmify.R;
import com.daman.farmify.UserBean;
import com.daman.farmify.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Remedies extends AppCompatActivity {
TextView textView;
    ArrayList<DiseaseBean> arrayList;
    ListView listView;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    DiseaseBean diseaseBean;
    int id=0;
    String name,title,info,url;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedies);
        listView= (ListView) findViewById(R.id.list);
        textView= (TextView) findViewById(R.id.text_header);
        Intent rcv = getIntent();
        name=rcv.getStringExtra("disease");
        url=rcv.getStringExtra("url");
       textView.setText(name);
        requestQueue= Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        if(isNetworkConected()){
            showList();
        }else{
               setContentView(R.layout.error);
            showSnackBar();
        }
    }
    void showList(){
        arrayList=new ArrayList<>();
        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                int i = 0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    i = jsonObject.getInt("success");
                    jsonObjectLog = new JSONObject(response);
                    jsonArray = jsonObjectLog.getJSONArray("user");

                }catch (Exception e){
                    e.printStackTrace();
                }

                if(i==1){
                    try {
                        for(int j=0;j<jsonArray.length();j++){
                            JSONObject jObj = jsonArray.getJSONObject(j);
                            id = jObj.getInt("id");
                            title = jObj.getString("title");
                            info = jObj.getString("info");
                            diseaseBean = new DiseaseBean(id,title,info);
                            arrayList.add(diseaseBean);
                            Log.i("userobj",diseaseBean.toString());
                        }
                      setAdapter();
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(Remedies.this,"Some  Exception!",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(Remedies.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Remedies.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
/*
        DiseaseBean db1=new DiseaseBean(0,"This is Title 1","This is content 1");
        DiseaseBean db2=new DiseaseBean(0,"This is Title 2","This is content 2");
        arrayList.add(db1);
        arrayList.add(db2);

        BindDictionary<DiseaseBean> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.text_title, new StringExtractor<DiseaseBean>() {
            @Override
            public String getStringValue(DiseaseBean item, int position) {

                return item.getTitle();
            }
        });
        dictionary.addStringField(R.id.text_content, new StringExtractor<DiseaseBean>() {
            @Override
            public String getStringValue(DiseaseBean item, int position) {

                return item.getContent();
            }
        });
        FunDapter funDapter=new FunDapter(this,arrayList,R.layout.remedie_list_item,dictionary);
        listView.setAdapter(funDapter);*/
    }
    void setAdapter(){
        BindDictionary<DiseaseBean> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.text_title, new StringExtractor<DiseaseBean>() {
            @Override
            public String getStringValue(DiseaseBean item, int position) {

                return item.getTitle();
            }
        });
        dictionary.addStringField(R.id.text_content, new StringExtractor<DiseaseBean>() {
            @Override
            public String getStringValue(DiseaseBean item, int position) {

                return item.getContent();
            }
        });
        FunDapter funDapter=new FunDapter(Remedies.this,arrayList,R.layout.remedie_list_item,dictionary);
        listView.setAdapter(funDapter);
    }
    boolean isNetworkConected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        return (networkInfo!=null && networkInfo.isConnected());

    }
    void showSnackBar(){
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.error_layout);
            /*Snackbar.make(linearLayout, "Connection Error", Snackbar.LENGTH_LONG)
                      .setAction("Action", null).show();*/
        Snackbar snack = Snackbar.make(linearLayout, "Connection Error", Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(Remedies.this, R.color.accent));
        tv.setBackgroundResource(R.color.red);
        if(tv!=null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        snack.show();
    }

}
