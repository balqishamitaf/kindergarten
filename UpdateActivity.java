package com.f_ahmad.kindergarten;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.f_ahmad.daily_lab4.ExpensesMainActivity;
import com.f_ahmad.daily_lab4.R;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editName,editGuardian,editYear,editAddress,editPhone,editDate,editTotal;
    Button upd,del;

    private long _id;

    ExpensesMainActivity.KindsDB dbKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
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

        editName = (EditText)findViewById(R.id.editName);
        editGuardian = (EditText)findViewById(R.id.editGuardian);
        editYear = (EditText)findViewById(R.id.editYear);
        editAddress = (EditText)findViewById(R.id.editAddress);
        editPhone = (EditText)findViewById(R.id.editPhone);
        editDate = (EditText)findViewById(R.id.editDate);
        editTotal = (EditText)findViewById(R.id.editTotal);

        upd = (Button)findViewById(R.id.upd);
        del = (Button)findViewById(R.id.del);

        Intent newI = getIntent();
        String id = newI.getStringExtra("id");
        String name = newI.getStringExtra("name");
        String guardian = newI.getStringExtra("guardian");
        String year = newI.getStringExtra("year");
        String address = newI.getStringExtra("address");
        String phone = newI.getStringExtra("phone");
        String date = newI.getStringExtra("date");
        String total = newI.getStringExtra("total");

        _id = Long.parseLong(id);

        editName.setText(name);
        editGuardian.setText(guardian);
        editYear.setText(year);
        editAddress.setText(address);
        editPhone.setText(phone);
        editDate.setText(date);
        editTotal.setText(total);

        upd.setOnClickListener(this);
        del.setOnClickListener(this);

    }

    public void onClick(View vw)
    {
        switch(vw.getId()){
            case R.id.upd:
                String name = editName.getText().toString();
                String guardian = editGuardian.getText().toString();
                String year = editYear.getText().toString();
                String address = editAddress.getText().toString();
                String phone = editPhone.getText().toString();
                String date = editDate.getText().toString();
                String total = editTotal.getText().toString();

                //dbKind.upd(_id,name,guardian,year,address,phone,date,total);
                this.goBack();
                break;

            case R.id.del:
                //dbKind.delete(_id);
                this.goBack();
                break;

        }
    }

    public void goBack(){
        Intent iH = new Intent(getApplicationContext(),ListViewActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(iH);
    }

}
