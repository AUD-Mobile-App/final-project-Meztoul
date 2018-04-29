package com.example.a1208022380.audbucketlist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {


    ListView List;
   public static ArrayList<MyItem> myitems = new ArrayList<>();
    MyCustomAdapter adapter = new MyCustomAdapter(myitems,MainActivity.this);
    public static int mode = 0;
    public static MyItem target;
    public static String UserID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        List = findViewById(R.id.List);
        List.setAdapter(adapter);

        List.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);



        FloatingActionButton FAB = findViewById(R.id.FAB);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode =0;
                startActivity(new Intent(MainActivity.this,AddItemActivity.class));
            }
        });

        UserID = LoginActivity.mFirebaseAuth.getCurrentUser().getUid();

        LoginActivity.mDatabaseReference.child(UserID).child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myitems.clear();
                for (DataSnapshot Snap : dataSnapshot.getChildren()){
                    MyItem t = Snap.getValue(MyItem.class);
                    t.setPushKey(Snap.getKey());
                    myitems.add(t);
                }

                Collections.sort(myitems, new Comparator<MyItem>() {
                    @Override
                    public int compare(MyItem myItem, MyItem t1) {
                        if (myItem.getDate()== null || t1.getDate() == null){
                            return 0;
                        }else {
                            return myItem.getDate().compareTo(t1.getDate());
                        }
                    }
                });
                for(int i = 0; i<myitems.size();i++){
                    for (int j = myitems.size()-1;j >=0;j--){
                        if (myitems.get(j).isChecked()){
                            myitems.add(myitems.get(j));
                            myitems.remove(j);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mode =1;
                target = myitems.get(position);
                startActivity(new Intent(MainActivity.this,AddItemActivity.class));

            }
        });






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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_signout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

