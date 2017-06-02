package com.f_ahmad.kindergarten;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.f_ahmad.daily_lab4.ExpensesMainActivity;
import com.f_ahmad.daily_lab4.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    GraphView grp;
    LineGraphSeries<DataPoint> srs;
    ExpensesMainActivity.KindsDB dbKind;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
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

        grp = (GraphView)findViewById(R.id.graph);
        dbKind = new ExpensesMainActivity.KindsDB(this);
        sqLiteDatabase=dbKind.getWritableDatabase();

        srs=new LineGraphSeries<DataPoint>(getData());

        grp.addSeries(srs);

    }

    private DataPoint[] getData() {
        String[] column={"year","total"};
        Cursor cursor = sqLiteDatabase.query("kind",column,null,null,null,null,null);


        DataPoint[] dp=new DataPoint[cursor.getCount()];

        for(int i=0; i < cursor.getCount(); i++){
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getInt(2),cursor.getInt(7));
        }

        return dp;
    }

}
