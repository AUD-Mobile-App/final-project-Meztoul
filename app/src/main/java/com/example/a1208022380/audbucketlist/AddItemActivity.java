package com.example.a1208022380.audbucketlist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.a1208022380.audbucketlist.LoginActivity.mDatabaseReference;
import static com.example.a1208022380.audbucketlist.LoginActivity.mFirebaseAuth;
import static com.example.a1208022380.audbucketlist.LoginActivity.mUser;
import static com.example.a1208022380.audbucketlist.MainActivity.UserID;
import static com.example.a1208022380.audbucketlist.MainActivity.mode;
import static com.example.a1208022380.audbucketlist.MainActivity.target;

public class AddItemActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText Title;
    private EditText Content;
    private EditText dateTxt;
    private Date date = new Date();
    private GoogleMap mMap;
    private Marker mMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

         Title = findViewById(R.id.editText);
         Content = findViewById(R.id.editText2);
         dateTxt = findViewById(R.id.editText3);

        Toolbar tool = findViewById(R.id.toolbar);

        setSupportActionBar(tool);

        SupportMapFragment mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        final Calendar calendar = Calendar.getInstance();
        final DateFormat format = DateFormat.getDateInstance();
        final DatePickerDialog.OnDateSetListener dp = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                date = calendar.getTime();
                dateTxt.setText(format.format(calendar.getTime()));
            }
        };

        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mode ==1){
                    calendar.setTime(target.getDate());
                }
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(AddItemActivity.this,dp,mYear,mMonth,mDay).show();

            }
        });

        if(mode ==1){
            date = target.getDate();
            dateTxt.setText(format.format(target.getDate()));
            Title.setText(target.getTitle());
            Content.setText(target.getContent());
        }else{
            dateTxt.setText(format.format(new Date()));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater MI = getMenuInflater();
        MI.inflate(R.menu.add_edit_menu,menu);


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            if(!Title.getText().toString().isEmpty() || !Content.getText().toString().isEmpty()) {
               // MainActivity.myitems.add(new MyItem(Title.getText().toString(),Content.getText().toString(),false));
                if(mUser != null){
                    MyItem item1 = new MyItem();
                    item1.setTitle(Title.getText().toString());
                    item1.setContent(Content.getText().toString());
                    item1.setDate(date);
                    item1.setChecked(false);

                    if (mMarker !=null){
                        item1.setLongitude(mMarker.getPosition().longitude);
                        item1.setLatitude(mMarker.getPosition().latitude);
                    }

                    if (mode == 1){
                        mDatabaseReference.child(UserID).child("Tasks").child(target.getPushKey()).setValue(item1);

                    }else{
                        mDatabaseReference.child(UserID).child("Tasks").push().setValue(item1);
                    }
                    this.finish();
                }


                return true;
            } else{
                Toast.makeText(AddItemActivity.this,"Title Or Content Are Empty!! You Should Put Content",Toast.LENGTH_LONG).show();
            }
        }else if(id == R.id.action_signout){
            mFirebaseAuth.signOut();
            startActivity(new Intent(AddItemActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mode ==1){
            LatLng pos = new LatLng(target.getLatitude(),target.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,10));
            mMarker = mMap.addMarker(new MarkerOptions().position(pos).title("the Place"));
        }else {
            LatLng Loc = new LatLng(25.2060,55.269554);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Loc,10));
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mMarker != null){
                    mMarker.remove();
                }
                mMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("the Place"));
            }
        });

    }
}
