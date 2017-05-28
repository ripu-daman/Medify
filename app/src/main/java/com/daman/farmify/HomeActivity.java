package com.daman.farmify;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daman.farmify.Diseases.DiseaseListContainer;
import com.daman.farmify.GridHome.GridViewAdapter;
import com.daman.farmify.GridHome.HomeFragment;
import com.daman.farmify.GridHome.ImageItem;
import com.daman.farmify.Medication.MedicineFragment;
import com.daman.farmify.profile.ListContainer;
import com.daman.farmify.profile.ProfileFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,AdapterView.OnItemClickListener,
        HomeFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,MedicineFragment.OnFragmentInteractionListener,LocationListener{

    TextView texthome;
    TextView textname;
    SharedPreferences sharedPreferences;
    String name,diseaseName;
    GridView gridView;
    GridViewAdapter gridAdapter;
    ArrayList<ImageItem> data ;
    ImageItem item,item1,item2,item3,item4,item5;
    RatingBar ratingBar;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    double latitude,longitude;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Location...");
        progressDialog.setCancelable(false);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nv = navigationView.getHeaderView(0);
        texthome= (TextView) findViewById(R.id.text_home);
        textname= (TextView)nv. findViewById(R.id.text_user);
        sharedPreferences = getSharedPreferences("signupsp", Context.MODE_PRIVATE);
        name=sharedPreferences.getString("name","");
        textname.setText(name);
       // RelativeLayout relativeLayout= (RelativeLayout)nv.findViewById(R.id.content_home);
        //initGrid();
//        gridView.setOnItemClickListener(this);
        HomeFragment homeFragment =new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_home, homeFragment, homeFragment.getTag()).commit();

    }



    public void clickHandler(View v){
        int id = v.getId();
        if(id==R.id.text_home){
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            //textname.setText("");
        }
    }
    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferencesLogin = getSharedPreferences("loginSp", Context.MODE_PRIVATE);
                SharedPreferences preferencesSignUp = getSharedPreferences("signupsp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorLogin=preferencesLogin.edit();
                SharedPreferences.Editor editorSignup=preferencesSignUp.edit();
                editorLogin.putBoolean("loggedin",false);
                editorLogin.putString("email","");
                editorLogin.commit();
                editorSignup.putInt("id",0);
                editorSignup.putBoolean("signup",false);
                editorSignup.putInt("id",0);
                editorSignup.putString("name","");
                editorSignup.putString("address","");
                editorSignup.putString("email","");
                editorSignup.putString("phone","");
                editorSignup.putString("password","");
                editorSignup.putString("state","");
                editorSignup.putString("dob","");
                editorSignup.commit();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aboutus) {
            popup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
           /* Intent intent=new Intent(getApplicationContext(),ListContainer.class);
            startActivity(intent);
             overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
           ProfileFragment profileFragment=new ProfileFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_home, profileFragment, profileFragment.getTag()).commit();

        } else if (id == R.id.nav_medication) {
            MedicineFragment medicineFragment=new MedicineFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_home, medicineFragment, medicineFragment.getTag()).commit();

        } else if (id == R.id.nav_localhealth) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Please Grant Permissions from Settings",Toast.LENGTH_LONG).show();
            }else{
                intent=new Intent(getApplicationContext(),MapsActivity.class);
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50, 10, HomeActivity.this);
                progressDialog.show();
            }

        } else if (id == R.id.nav_settings) {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
            View view = getLayoutInflater().inflate(R.layout.contact_us,null);
            Button ok = (Button) view.findViewById(R.id.btn_ok);
            final TextView email_link=(TextView)view.findViewById(R.id.textmail_link);
            builder.setView(view);
            final AlertDialog dialog= builder.create();
            dialog.getWindow().getAttributes().windowAnimations=R.style.animationdialog;
            dialog.show();
            dialog.setCancelable(false);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    void popup(){
        AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
        View view = getLayoutInflater().inflate(R.layout.activity_about_us,null);
        Button ok = (Button) view.findViewById(R.id.btn_ok);
        ratingBar= (RatingBar)view. findViewById(R.id.ratingBar);
        builder.setView(view);
        final AlertDialog dialog= builder.create();
        dialog.getWindow().getAttributes().windowAnimations=R.style.animationdialog;
        dialog.show();
        dialog.setCancelable(false);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
       ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               Toast.makeText(HomeActivity.this,"Thank You For "+rating+" Stars",Toast.LENGTH_LONG).show();
           }
       });
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        progressDialog.dismiss();
        startActivity(intent);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
