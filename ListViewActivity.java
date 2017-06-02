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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.f_ahmad.daily_lab4.ExpensesMainActivity;
import com.f_ahmad.daily_lab4.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_list_view);
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
        lv = (ListView)findViewById(R.id.listView);
        aL = new ArrayList<HashMap<String, String>>();

        Runnable run4 = new Runnable() {
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
                                ListViewActivity.this, aL, R.layout.list_view_info,
                                new String[] {strId, strName, strYear, strDate},
                                new int[] {R.id.textId, R.id.textName, R.id.textYear, R.id.textDate}
                        );

                        lv.setAdapter(adapter);

                        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TextView Tid = (TextView)view.findViewById(R.id.textId);
                                TextView Tname = (TextView)view.findViewById(R.id.textName);
                                TextView Ty = (TextView)view.findViewById(R.id.textYear);
                                TextView Td = (TextView)view.findViewById(R.id.textDate);

                                String Sid = Tid.getText().toString();
                                String Sname = Tname.getText().toString();
                                String Syear = Ty.getText().toString();
                                String Sdate = Td.getText().toString();

                                Intent iU = new Intent(getApplicationContext(),UpdateActivity.class);
                                iU.putExtra("name",Sname);
                                iU.putExtra("year",Syear);
                                iU.putExtra("date",Sdate);
                                iU.putExtra("id",Sid);

                                startActivity(iU);

                            }
                        });*/
                    }
                });

            }
        };

        Thread t4 = new Thread(run4);
        t4.start();

    }



}
