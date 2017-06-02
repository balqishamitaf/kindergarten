package com.f_ahmad.daily_lab4;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.f_ahmad.kindergarten.GraphActivity;
import com.f_ahmad.kindergarten.ListViewActivity;
import com.f_ahmad.kindergarten.ViewOnlyActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpensesMainActivity extends AppCompatActivity {

    EditText editName, editYear, editDate, editGuardian, editPhone, editAddress, editTotal;

    ExpensesListActivity.WebServiceCall wsc = new ExpensesListActivity.WebServiceCall();
    String strDate, strMsg;
    KindsDB dbKinds;
    JSONObject jsnObj = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_main);
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

        dbKinds = new KindsDB(getApplicationContext());
        editName = (EditText)findViewById(R.id.editName);
        editYear = (EditText)findViewById(R.id.editYear);
        editDate = (EditText)findViewById(R.id.editDate);
        editGuardian = (EditText)findViewById(R.id.editGuardian);
        editPhone = (EditText)findViewById(R.id.editPhone);
        editAddress = (EditText)findViewById(R.id.editAddress);
        editTotal = (EditText)findViewById(R.id.editTotal);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("selectFn","fnGetDateTime"));

                try{
                    jsnObj = wsc.makeHttpRequest(wsc.fnGetURL(),"POST",params);
                    strDate = jsnObj.getString("currDate");
                    strMsg = "Retrieve success";
                }catch (Exception e){
                    strMsg = "Retrieve failed, using local date";
                    strDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fnDisplayToastMsg(strMsg);
                        editDate.setText(strDate);
                    }
                });

            }
        };

        Thread tD = new Thread(run);
        tD.start();

        final Button oneTime = (Button)findViewById(R.id.trig);
        oneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTotal = (EditText)findViewById(R.id.editTotal);

                String get1 = editTotal.getText().toString();
                Float conv1 = Float.parseFloat(get1);

                editTotal.setText(String.valueOf(conv1 + 300));

                Toast c = Toast.makeText(getApplicationContext(),"Successfully added !",Toast.LENGTH_SHORT);
                c.show();
                oneTime.setEnabled(false);
            }
        });

    }

    public void fnDisplayToastMsg(String strText)
    {
        Toast t = Toast.makeText(getApplicationContext(),strText,Toast.LENGTH_LONG);
        t.show();
    }

    public static class KindsDB extends SQLiteOpenHelper
    {
        public static final String dbName = "dbKind";
        public static final String tblName = "kind";
        public static final String colName = "name";
        public static final String colYear = "year";
        public static final String colDate = "date";
        public static final String colId = "id";
        public static final String colGuardian = "guardian";
        public static final String colAddress = "address";
        public static final String colPhone = "phone";
        public static final String colTotal = "total";

        private Context con;
        public KindsDB(Context context)
        {
            super(context,dbName,null,1);
            con = context;

        }



        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("create table if not exists kind(id varchar,name varchar,year varchar,date date,guardian varchar,phone varchar, address varchar, total varchar);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("drop table if exists kind");
            onCreate(db);
        }

        public Cursor getDataById(int id)
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from "+tblName+" where "+colId+" = "+id,null);

            return cur;
        }

        public void fnExecuteSql(String strSql, Context appContext)
        {
            try{
                SQLiteDatabase db = this.getReadableDatabase();
                db.execSQL(strSql);
            }catch (Exception e)
            {
                Log.d("Unable to run query !","Error !");
            }
        }

        public int fnTotalRow()
        {
            int intRow;
            SQLiteDatabase db = this.getReadableDatabase();
            intRow = (int) DatabaseUtils.queryNumEntries(db,tblName);
            return intRow;
        }

        /*public int upd(long _id, String name, String guardian, String year, String address, String phone, String date, String total){
            ContentValues cV = new ContentValues();
            cV.put(KindsDB.colName,name);
            cV.put(KindsDB.colGuardian,guardian);
            cV.put(KindsDB.colYear,year);
            cV.put(KindsDB.colAddress,address);
            cV.put(KindsDB.colPhone,phone);
            cV.put(KindsDB.colDate,date);
            cV.put(KindsDB.colTotal,total);

            SQLiteDatabase db = this.getWritableDatabase();

            int i = db.update(KindsDB.tblName,cV,KindsDB.colId + " = " + _id,null);
            return i;

        }

        public void delete(long _id){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(KindsDB.tblName,KindsDB.colId + " = " + _id,null);
        }*/

    }

    public void fnCalc(View vw)
    {
        /*editTotal = (EditText)findViewById(R.id.editTotal);

        String get1 = editTotal.getText().toString();
        Float conv1 = Float.parseFloat(get1);

        editTotal.setText(String.valueOf(conv1 + 300));

        Toast c = Toast.makeText(getApplicationContext(),"Successfully added !",Toast.LENGTH_SHORT);
        c.show();*/
    }

    public void fnSave(View vw)
    {
        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                String strName = editName.getText().toString();
                String strYear = editYear.getText().toString();
                String strDate = editDate.getText().toString();
                String strGuardian = editGuardian.getText().toString();
                String strPhone = editPhone.getText().toString();
                String strAddress = editAddress.getText().toString();
                String strTotal = editTotal.getText().toString();

                int intNewId = dbKinds.fnTotalRow() + 1;
                String strQry = "insert into kind values('"+intNewId+"','"+strName+"','"+strYear+"','"+strDate+"','"+strGuardian+"','"+strPhone+"','"+strAddress+"','"+strTotal+"');";

                dbKinds.fnExecuteSql(strQry,getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tS = Toast.makeText(getApplicationContext(),"Saved !",Toast.LENGTH_SHORT);
                        tS.show();
                    }
                });

            }
        };
        Thread tSv = new Thread(run2);
        tSv.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expenses_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exp_menu) {
            Intent it = new Intent(this,ExpensesListActivity.class);
            startActivity(it);
            return true;
        }

        else if(id == R.id.ListVw)
        {
            Intent it2 = new Intent(this, ListViewActivity.class);
            startActivityForResult(it2,0);
        }

        else if(id == R.id.vwOnly)
        {
            Intent it3 = new Intent(this, ViewOnlyActivity.class);
            startActivityForResult(it3,0);
        }

        else if(id == R.id.grpVw)
        {
            Intent it4 = new Intent(this, GraphActivity.class);
            startActivityForResult(it4,0);
        }

        return super.onOptionsItemSelected(item);
    }

}

