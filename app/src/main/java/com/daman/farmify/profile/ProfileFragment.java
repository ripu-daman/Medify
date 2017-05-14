package com.daman.farmify.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.daman.farmify.R;
import com.daman.farmify.UserBean;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements AdapterView.OnItemClickListener{
    UserBean bean;
    ListBean listBean;
    ListAdapter adapter;
    ArrayList<ListBean> list;
    ListView listView;
    String name,phone,password,dob,email;
       View view;
    public void initlist(){
        listView= (ListView) view.findViewById(R.id.listviewFragment);

        list= new ArrayList<>();
        ListBean basicinfo= new ListBean(R.drawable.basicinfo,"Basic Info","View and edit your basic info.");
        ListBean healthchart= new ListBean(R.drawable.healthchart,"Health Chart","View your health chart");


        list.add(basicinfo);
        list.add(healthchart);

        adapter = new ListAdapter(getContext(),R.layout.list_item,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(ProfileFragment.this);
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        view= inflater.inflate(R.layout.fragment_profile, container, false);
        initlist();
        return view;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            Intent intent=new Intent(getContext(),BasicInfo.class);

            SharedPreferences sharedPreferences =this.getActivity(). getSharedPreferences("signupsp", Context.MODE_PRIVATE);

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
            this.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
