package com.example.a1208022380.audbucketlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {

    EditText Title;
    EditText Content;
    ArrayList<MyItem> AddedItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

         Title = findViewById(R.id.editText);
         Content = findViewById(R.id.editText2);

        Toolbar tool = findViewById(R.id.toolbar);

        setSupportActionBar(tool);


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
                MainActivity.myitems.add(new MyItem(Title.getText().toString(),Content.getText().toString(),false));
                this.finish();

                return true;
            } else{
                Toast.makeText(AddItemActivity.this,"Title Or Content Are Empty!! You Should Put Content",Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
