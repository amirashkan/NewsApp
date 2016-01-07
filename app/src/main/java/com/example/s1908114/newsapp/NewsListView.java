package com.example.s1908114.newsapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class NewsListView extends ListActivity {
    SQLiteDatabase database = null;
    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_view);
        queryDataFromDatabase();
    }
    public void queryDataFromDatabase() {
        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
        }
        List<String> list_values = new ArrayList<String>();
        try {
            database = dbHelper.getDataBase();
            dbCursor = database.rawQuery("SELECT headline FROM NewsTable;",
                    null);
            dbCursor.moveToFirst();
            int index = dbCursor.getColumnIndex("headline");
            while (!dbCursor.isAfterLast()) {
                String record = dbCursor.getString(index);
                list_values.add(record);
                dbCursor.moveToNext();
            }
        } finally {
            if (database != null) {
                dbHelper.close();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.news_list_view_item, list_values);
        setListAdapter(adapter);
    }
    public void onClickAddDataRecord(View view) {
    }
    public void onClickUpdateList(View view) {
    }
}
