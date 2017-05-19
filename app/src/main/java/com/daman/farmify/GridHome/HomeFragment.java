package com.daman.farmify.GridHome;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.daman.farmify.Diseases.DiseaseListContainer;
import com.daman.farmify.HomeActivity;
import com.daman.farmify.R;
import com.daman.farmify.profile.ProfileFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener{
    GridView gridView;
    GridViewAdapter gridAdapter;
    ArrayList<ImageItem> data ;
    ImageItem item,item1,item2,item3,item4,item5;
    View view;
    String[] diseaseList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

         view= inflater.inflate(R.layout.fragment_home, container, false);

        initGrid();
        return view;}

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
       // Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
        if(position==0){
            Intent intent=new Intent(getContext(),DiseaseListContainer.class);
            diseaseList= new String[]{"Chronic pain", "Joint Pain", "Headache"};
            intent.putExtra("array",diseaseList);
            intent.putExtra("position",position);
            startActivity(intent);
                this.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }else if(position==1){
                diseaseList= new String[]{"Skin Irritation", "Skin Rashes"};
                Intent intent=new Intent(getContext(),DiseaseListContainer.class);
                intent.putExtra("array",diseaseList);
            intent.putExtra("position",position);
                startActivity(intent);
                this.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }else if(position==2){
                diseaseList= new String[]{"Throat Infection", "High Body Temperature","Stuffy Nose"};
                Intent intent=new Intent(getContext(),DiseaseListContainer.class);
                intent.putExtra("array",diseaseList);
            intent.putExtra("position",position);
                startActivity(intent);
                this.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }else if(position==3){
                diseaseList= new String[]{"Feeling Sad","Feeling Anxious Or Empty" ,"Over/Under Sleeping"};
               Intent intent=new Intent(getContext(),DiseaseListContainer.class);
                intent.putExtra("array",diseaseList);
            intent.putExtra("position",position);
                startActivity(intent);
                this.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }else if(position==4){
                diseaseList= new String[]{"Irritation In The Eye","Eye Redness"};
                Intent intent=new Intent(getContext(),DiseaseListContainer.class);
                intent.putExtra("array",diseaseList);
            intent.putExtra("position",position);
                startActivity(intent);
                this.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }else if(position==5){
                diseaseList= new String[]{"Insomnia", "Talking In Sleep","Night Sweats"};
                Intent intent=new Intent(getContext(),DiseaseListContainer.class);
                intent.putExtra("array",diseaseList);
            intent.putExtra("position",position);
                startActivity(intent);
                this.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

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
    public void initGrid(){
        gridView = (GridView)view.findViewById(R.id.gridView);
        data= new ArrayList<>();
        item=new ImageItem(R.drawable.pain,"Pain Management");
        item1=new ImageItem(R.drawable.allergies,"Skin Problems");
        item2=new ImageItem(R.drawable.sick,"Cold & Flu");
        item3=new ImageItem(R.drawable.depression,"Depression");
        item4=new ImageItem(R.drawable.eye,"Eye Care");
        item5=new ImageItem(R.drawable.tired,"Sleeping Disorder");
        data.add(item);
        data.add(item1);
        data.add(item2);
        data.add(item3);
        data.add(item4);
        data.add(item5);

        gridAdapter = new GridViewAdapter(HomeFragment.this.getContext(), R.layout.grid_item_layout, data);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(HomeFragment.this);
    }
}
