package com.f_ahmad.daily_lab4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ExpensesListActivity extends AppCompatActivity {

    SQLiteDatabase dbKinds;
    ExpensesMainActivity.KindsDB dbKind;
    String strName,strYear,strDate,strGuardian,strPhone,strAddress,strTotal,strOverall="",totRec;
    String tot="";
    //float tot=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);
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

        Runnable run = new Runnable() {
            @Override
            public void run() {
                dbKinds = openOrCreateDatabase("dbMyExpense",MODE_PRIVATE,null);
                Cursor resultSet = dbKind.getReadableDatabase().rawQuery("select * from kind;",null);

                if(resultSet.moveToFirst())
                {
                    do {
                        strName = resultSet.getString(resultSet.getColumnIndex("name"));
                        strYear = resultSet.getString(resultSet.getColumnIndex("year"));
                        strDate = resultSet.getString(resultSet.getColumnIndex("date"));
                        strGuardian = resultSet.getString(resultSet.getColumnIndex("guardian"));
                        strPhone = resultSet.getString(resultSet.getColumnIndex("phone"));
                        strAddress = resultSet.getString(resultSet.getColumnIndex("address"));
                        strTotal = resultSet.getString(resultSet.getColumnIndex("total"));
                        strOverall += strName + " | " + strYear + " | " + strDate + " | " + strGuardian + " | " + strPhone + " | " + strAddress + " | " + strTotal + "\n";
                        tot += tot.valueOf(parseFloat(strTotal)).toString();
                    }while(resultSet.moveToNext());
                }



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView txtExp = (TextView)findViewById(R.id.textView);
                        txtExp.setText(strOverall);

                        TextView txtExp2 = (TextView)findViewById(R.id.tot);
                        txtExp2.setText(tot);



                    }
                });

            }
        };
        Thread t = new Thread(run);
        t.start();
    }

    public static class WebServiceCall{
        JSONObject jsnObj;
        String strUrl="";

        public WebServiceCall()
        {
            jsnObj = null;
            strUrl="http://localhost/webServiceJSON/globalWebService.php";
        }

        public String fnGetURL()
        {
            return strUrl;
        }

        public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params)
        {
            InputStream is = null;
            String json = "";
            JSONObject jO = null;

            try{
                if(method == "POST")
                {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                }else if (method == "GET")
                {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    String paramStr = URLEncodedUtils.format(params,"utf-8");
                    url += "?" + paramStr;
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
                jO = new JSONObject(json);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return jO;
        }

    }

}
