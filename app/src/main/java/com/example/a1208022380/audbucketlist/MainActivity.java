package com.example.a1208022380.audbucketlist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView List;
   public static ArrayList<MyItem> myitems = new ArrayList<>();
    MyCustomAdapter adapter = new MyCustomAdapter(myitems,MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        List = findViewById(R.id.List);
        myitems.add(new MyItem("Get Something For Mother's day","Love your mom everyday of the year",true));
        myitems.add(new MyItem("Learn Cooking","learn How to make Brownies",false));
        myitems.add(new MyItem("Complete Assignment","Complete the Mobile Apps Assignment",false));
        myitems.add(new MyItem("Go to the Gym","Do 30 Minutes Cardio",false));
        myitems.add(new MyItem("Get Something for Mohamed","get the Game he always talks About",false));

        FloatingActionButton FAB = findViewById(R.id.FAB);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddItemActivity.class));
            }
        });



        List.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

       return super.onCreateOptionsMenu(menu);
    }
    }

