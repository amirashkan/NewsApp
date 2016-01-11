package com.example.s1908114.newsapp;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import parallaxscrollexample.SingleParallaxScrollView;

public class NewsListView extends Fragment {
    SQLiteDatabase database = null;
    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this.getActivity());
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_news_list_view,container,false);
         ListView listv = (ListView) rootView.findViewById(R.id.lisst);
         ListView listv2 = (ListView) rootView.findViewById(R.id.lisst1);
         queryDataFromDatabase(listv,listv2);
         return rootView;

    }




    public void queryDataFromDatabase(final ListView  listv,final ListView  listv2) {


        List<String> list_headline = new ArrayList<String>();
        List<String> list_text = new ArrayList<String>();
        try {

            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery("SELECT headline , maintext,dates FROM NewsTable "
                    + "where NewsTable.category=? or NewsTable.category=?" +
                    " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?;",
                    SideBar.MenuFilter);
            dbCursor.moveToFirst();
            int index = dbCursor.getColumnIndex("headline");
             while (!dbCursor.isAfterLast()) {
                 List<String> item = new ArrayList<String>();

                 String record = dbCursor.getString(index);
                String recordtext = dbCursor.getString(1);


                 list_headline.add(record);
                 list_text.add(recordtext);
                dbCursor.moveToNext();
            }
        } finally {
            if (database != null) {
                dbHelper.close();
            }
        }
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_item, list_headline);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_text, list_text);
        listv2.setAdapter(adapter2);

        listv.setAdapter(adapter);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), SingleParallaxScrollView.class);
                intent.putExtra("headline",String.valueOf(listv.getItemAtPosition((int) (long)id)));
                intent.putExtra("text",String.valueOf(listv2.getItemAtPosition((int) (long)id)));

                startActivity(intent);
               // intent.putExtra("text", "SecondKeyValue");
                Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();

            }
        });
    }

     public void onClickAddDataRecord(View view) {
    }
    public void onClickUpdateList(View view) {
    }
}
