package com.daman.farmify.Diseases;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daman.farmify.R;
import com.daman.farmify.Util;

public class DiseaseListContainer extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    TextView textView,textTitle;
    String [] diseaseList;
    ArrayAdapter<String> arrayAdapter;
    Button homeRemedies,expertHelp;
    RelativeLayout relativeLayout;
    PopupWindow popupWindow;
    int position;
    public void initList(){
        Intent rcv = getIntent();
        diseaseList=rcv.getStringArrayExtra("array");
        position=rcv.getIntExtra("position",0);
        Log.i("Position", String.valueOf(position));
        textView= (TextView) findViewById(R.id.textView);
        //diseaseList= new String[]{"Chronic pain", "Joint Pain", "Headache"};
        listView= (ListView) findViewById(R.id.listview);
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.disease_list_item,R.id.textView,diseaseList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_list_container);
        relativeLayout= (RelativeLayout) findViewById(R.id.relative_vanish);
        initList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int diseasePos=position;
        if (position == 0) {
            String selectedItem = listView.getItemAtPosition(position).toString();
            showPrompt(selectedItem,diseasePos);
        } else if (position == 1) {
            String selectedItem = listView.getItemAtPosition(position).toString();
            showPrompt(selectedItem,diseasePos);
        } else if (position == 2) {
            String selectedItem = listView.getItemAtPosition(position).toString();
            showPrompt(selectedItem,diseasePos);
        }
    }
    public void showPrompt(final String item, final int diseasePos){

        AlertDialog.Builder builder=new AlertDialog.Builder(DiseaseListContainer.this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        homeRemedies= (Button) view.findViewById(R.id.button_homeremedie);
        expertHelp= (Button) view.findViewById(R.id.button_experthelp);
        textTitle= (TextView) view.findViewById(R.id.text_title);
        builder.setView(view);
        final AlertDialog dialog= builder.create();
        textTitle.setText(item);
        dialog.getWindow().getAttributes().windowAnimations=R.style.animationdialog;
        dialog.show();
        homeRemedies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Position onclick", String.valueOf(diseasePos));
                if(position==0 && diseasePos==0){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_CHRONIC_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                } if(position==0 && diseasePos==1){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_JOINT_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }if(position==0 && diseasePos==2){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_HEADACHE_PHP);

                    startActivity(intent);
                    dialog.dismiss();
                }if(position==1 && diseasePos==0){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_SKINIRRITATION_PHP);

                    startActivity(intent);
                    dialog.dismiss();
                }if(position==1 && diseasePos==1){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_SKINRASHES_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }if(position==2 && diseasePos==0){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_THROATINFECTION_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }if(position==2 && diseasePos==1){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_BODYTEMPERATURE_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }if(position==2 && diseasePos==2){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_STUFFYNOSE_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }
                if(position==3 && diseasePos==0){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_FEELINGSAD_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                } if(position==3 && diseasePos==1){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_FEELINGANXIOUS_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                } if(position==3&& diseasePos==2){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_OVERSLEEPING_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }if(position==4&& diseasePos==0){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_EYEIRRITATION_PHP);

                    startActivity(intent);
                    dialog.dismiss();
                }if(position==4 && diseasePos==1){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_EYEREDNESS_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }if(position==5 && diseasePos==0){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_INSOMNIA_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }if(position==5 && diseasePos==1){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_TALKINGSLEEP_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }if(position==5 && diseasePos==2){
                    Intent intent=new Intent(DiseaseListContainer.this,Remedies.class);
                    intent.putExtra("disease",item);
                    intent.putExtra("url", Util.RETRIEVE_NIGHTSWEATS_PHP);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
    }
}
