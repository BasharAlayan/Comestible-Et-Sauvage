package com.example.bashar.comestibleetsauvage;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Synch_plante extends AppCompatActivity {

    //    Activity_ajouter_plante ajouter_plante;
    DataBase_Local dataBase_global;
    private ListView mlistView;
    private static final String TAG="Synch_plante";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synch_plante);
        mlistView=(ListView) findViewById(R.id.listview);
        dataBase_global=new DataBase_Local(this);
        listview_DB();


    }

    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private void listview_DB(){
        Log.d(TAG,"Displaying data in the ListView");

        //recuperer les données et inserer dans la liste
        Cursor data=dataBase_global.getData();
        ArrayList<String> listdata=new ArrayList<>();
        while (data.moveToNext()){
            listdata.add(data.getString(1));
            listdata.add(data.getString(2));
            listdata.add(data.getString(3));
            listdata.add(data.getString(4));

        }
        ListAdapter adapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listdata);
        mlistView.setAdapter(adapter);


    }

}