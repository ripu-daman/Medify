package com.daman.farmify.Medication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daman.farmify.Diseases.DiseaseBean;
import com.daman.farmify.Diseases.DiseaseListContainer;
import com.daman.farmify.Diseases.Remedies;
import com.daman.farmify.R;
import com.daman.farmify.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MedicineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    boolean medicine;
    View view;
    String diseaseName;
    ListView listView;
    TextView textView,popupText;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    MedicineListBean medicineListBean;
    ArrayList<MedicineListBean> arrayList;
    int id=0;
    String name,myname;
    Button addMedicine,submit;
    EditText medi1,medi2,medi3,medi4;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public MedicineFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicineFragment newInstance(String param1, String param2) {
        MedicineFragment fragment = new MedicineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view=inflater.inflate(R.layout.fragment_medicine, container, false);
        SharedPreferences sharedPreferences = MedicineFragment.this.getActivity().getSharedPreferences("MedicineFlag", Context.MODE_PRIVATE);
        medicine = sharedPreferences.getBoolean("medicine",false);
        diseaseName=sharedPreferences.getString("diseaseName","");
        //Toast.makeText(MedicineFragment.this.getActivity(), "Value of flag: "+medicine, Toast.LENGTH_SHORT).show();
        //Toast.makeText(MedicineFragment.this.getActivity(), "Name of disease: "+diseaseName, Toast.LENGTH_SHORT).show();
        listView= (ListView) view.findViewById(R.id.medicine_list);
        textView= (TextView) view.findViewById(R.id.text_header);
        addMedicine= (Button) view.findViewById(R.id.add_medi);
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
        requestQueue= Volley.newRequestQueue(MedicineFragment.this.getActivity());
        progressDialog = new ProgressDialog(MedicineFragment.this.getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        initList();
        return view;
    }
    void initList(){
        if(medicine&&diseaseName.equalsIgnoreCase("chronic pain")){
            textView.setText(diseaseName);
            myname="chronic_pain";
            retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("joint pain")){
            textView.setText(diseaseName);
            myname="joint_pain";
            retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("headache")){
            textView.setText(diseaseName);
            myname="headache";
            retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("skin irritation")){
            textView.setText(diseaseName);
            myname="skin_irritation";
            retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("skin rashes")){
            textView.setText(diseaseName);
            myname="skin_rashes";
            retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("throat infection")){
            textView.setText(diseaseName);
            myname="throat_infection";
            retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("high body temperature")){
            textView.setText(diseaseName);
            myname="body_temperature";
            retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("stuffy nose")){
            textView.setText(diseaseName);
            myname="stuffy_nose";
            retrieveMedicine();
        } if(medicine&&diseaseName.equalsIgnoreCase("feeling sad")){
                textView.setText(diseaseName);
                myname="feeling_sad";
                retrieveMedicine();
        } if(medicine&&diseaseName.equalsIgnoreCase("feeling anxious or empty")){
                textView.setText(diseaseName);
                myname="feeling_anxious";
                retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("over/under sleeping")){
                textView.setText(diseaseName);
                myname="over_sleeping";
                retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("irritation in the eye")){
                textView.setText(diseaseName);
                myname="eye_irritation";
                retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("eye redness")){
                textView.setText(diseaseName);
                myname="eye_redness";
                retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("insomnia")){
                textView.setText(diseaseName);
                myname="insomnia";
                retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("talking in sleep")){
                textView.setText(diseaseName);
                myname="talking_sleep";
                retrieveMedicine();
        }if(medicine&&diseaseName.equalsIgnoreCase("night sweats")){
                textView.setText(diseaseName);
                myname="night_sweats";
                retrieveMedicine();
        }else{
//            Toast.makeText(MedicineFragment.this.getActivity(), "you need to add medicine in order to view them!",
//                    Toast.LENGTH_LONG).show();
        }
    }
    void retrieveMedicine(){
        arrayList=new ArrayList<>();
        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.GET,Util.RETRIEVE_MEDICINE_PHP, new Response.Listener<String>() {
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
                            name = jObj.getString(myname);
                            medicineListBean = new MedicineListBean(id,name);
                            arrayList.add(medicineListBean);
                            Log.i("userobj",medicineListBean.toString());
                        }
                        setAdapter();
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(MedicineFragment.this.getActivity(),"Some  Exception!",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(MedicineFragment.this.getActivity(),"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MedicineFragment.this.getActivity(),"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }
    void setAdapter(){
        BindDictionary<MedicineListBean> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.text_number, new StringExtractor<MedicineListBean>() {
            @Override
            public String getStringValue(MedicineListBean item, int position) {

                return ""+item.getId();
            }
        });
        dictionary.addStringField(R.id.text_medicine_title, new StringExtractor<MedicineListBean>() {
            @Override
            public String getStringValue(MedicineListBean item, int position) {

                return item.getMedicineName();
            }
        });
        FunDapter funDapter=new FunDapter(MedicineFragment.this.getActivity(),arrayList,R.layout.medicine_listitem,dictionary);
        listView.setAdapter(funDapter);
    }
    void showPopup(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MedicineFragment.this.getActivity());
        View v = MedicineFragment.this.getActivity(). getLayoutInflater().inflate(R.layout.add_medi_popup,null);
        popupText= (TextView) v.findViewById(R.id.text_title);
        medi1= (EditText) v.findViewById(R.id.input_medi1);
        medi2= (EditText) v.findViewById(R.id.input_medi2);
        medi3= (EditText) v.findViewById(R.id.input_medi3);
        medi4= (EditText) v.findViewById(R.id.input_medi4);
        submit= (Button) v.findViewById(R.id.btn_submit);
        builder.setView(v);
        final AlertDialog dialog= builder.create();
        dialog.getWindow().getAttributes().windowAnimations=R.style.animationdialog;
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                sharedPreferences = MedicineFragment.this.getActivity().getSharedPreferences("CustomMedicine", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("medi1",medi1.getText().toString());
                editor.putString("medi2",medi2.getText().toString());
                editor.putString("medi3",medi3.getText().toString());
                editor.putString("medi4",medi4.getText().toString());
                editor.putBoolean("signal",true);
                editor.commit();
                Toast.makeText(MedicineFragment.this.getActivity(),"Your medicines have been added to your profile",Toast.LENGTH_LONG).show();
                dialog.dismiss();
                }
            }
        });
    }
    public boolean validate() {
        boolean valid = true;

        String medicin1 = medi1.getText().toString();
        String medicin2 = medi2.getText().toString();
        String medicin3 = medi3.getText().toString();
        String medicin4 = medi4.getText().toString();
        if (medicin1.isEmpty()&&medicin2.isEmpty()&&medicin3.isEmpty()&&medicin4.isEmpty()&&medicin1.isEmpty()) {
            medi1.setError("Please enter the field");
            medi2.setError("Please enter the field");
            medi3.setError("Please enter the field");
            medi4.setError("Please enter the field");

            valid = false;
        }

        return valid;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
