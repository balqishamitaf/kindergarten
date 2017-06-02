package com.f_ahmad.kindergarten;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.f_ahmad.daily_lab4.ExpensesMainActivity;
import com.f_ahmad.daily_lab4.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewOnlyActivity extends AppCompatActivity {

    private static final String strId = ExpensesMainActivity.KindsDB.colId;
    private static final String strName = ExpensesMainActivity.KindsDB.colName;
    private static final String strYear = ExpensesMainActivity.KindsDB.colYear;
    private static final String strDate = ExpensesMainActivity.KindsDB.colDate;

    ExpensesMainActivity.KindsDB dbKind;
    ListView lv;
    ArrayList<HashMap<String, String>> aL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_only);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dbKind = new ExpensesMainActivity.KindsDB(getApplicationContext());
        lv = (ListView)findViewById(R.id.lV);
        aL = new ArrayList<HashMap<String, String>>();

        Runnable run5 = new Runnable() {
            @Override
            public void run() {
                String strSql = "select id,name,year,date from "+ ExpensesMainActivity.KindsDB.tblName;
                Cursor cur = dbKind.getReadableDatabase().rawQuery(strSql,null);

                while(cur.moveToNext())
                {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(strId, cur.getString(cur.getColumnIndex(ExpensesMainActivity.KindsDB.colId)));
                    map.put(strName, cur.getString(cur.getColumnIndex(ExpensesMainActivity.KindsDB.colName)));
                    map.put(strYear, cur.getString(cur.getColumnIndex(ExpensesMainActivity.KindsDB.colYear)));
                    map.put(strDate, cur.getString(cur.getColumnIndex(ExpensesMainActivity.KindsDB.colDate)));

                    aL.add(map);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListAdapter adapter = new SimpleAdapter(
                                ViewOnlyActivity.this, aL, R.layout.list_view_info,
                                new String[] {strId, strName, strYear, strDate},
                                new int[] {R.id.textId, R.id.textName, R.id.textYear, R.id.textDate}
                        );

                        lv.setAdapter(adapter);


                    }
                });

            }
        };

        Thread t5 = new Thread(run5);
        t5.start();

    }

}
