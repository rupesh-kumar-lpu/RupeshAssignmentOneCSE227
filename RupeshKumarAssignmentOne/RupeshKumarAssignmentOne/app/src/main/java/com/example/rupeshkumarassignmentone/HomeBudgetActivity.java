package com.example.rupeshkumarassignmentone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeBudgetActivity extends AppCompatActivity {
    SharedPreferences sh;
    SharedPreferences sharedPreferences;
    MyBudgetDataBase myBudgetDataBase;
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_budget);


        sharedPreferences= getSharedPreferences("MySharedPref", MODE_PRIVATE);
        sh= getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        a = sh.getInt("budget", 0);
        myBudgetDataBase=new MyBudgetDataBase(this);

        ((TextView)findViewById(R.id.textView)).setText(Integer.toString(a));



        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder adb = new AlertDialog.Builder(HomeBudgetActivity.this);

                adb.setTitle("You will lose all your expence data! Are you sure to reset?");
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        SharedPreferences.Editor myEdit
                                = sharedPreferences.edit();

// Storing the key and its value
// as the data fetched from edittext

                        myEdit.putInt("budget", Integer.parseInt("0"));

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myEdit.commit();

                        myBudgetDataBase.cleartable();

                        Intent intent2=new Intent(HomeBudgetActivity.this,MainActivity.class);
                        startActivity(intent2);
                        finish();
                        //Toast.makeText(HomeBudgetActivity.this,Integer.toString(a),Toast.LENGTH_LONG).show();

                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                adb.show();



            }
        });

        ((Button)findViewById(R.id.srcdt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentsr=new Intent(HomeBudgetActivity.this,Search.class);
                startActivity(intentsr);

            }
        });

        ((Button)findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3=new Intent(HomeBudgetActivity.this,ExpenseActivity.class);
                startActivity(intent3);

            }
        });

        ((Button)findViewById(R.id.button5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res =myBudgetDataBase.getalldata();
                StringBuffer bfr=new StringBuffer();
                if(res.getCount()==0){
                    showMessage("Expence Data","Nothing Found");
                    return;
                }
                while(res.moveToNext()){

                    if(res.getString(1)!=null) {
                        bfr.append("Type : " + res.getString(1) + "\n");
                    }
                    if(res.getString(2)!=null) {
                        bfr.append("Value : " + res.getString(2) + "\n");
                    }
                    if(res.getString(3)!=null) {
                        bfr.append("Note : " + res.getString(3) + "\n");
                    }
                    if(res.getString(4)!=null) {
                        bfr.append("Date : " + res.getString(4) + "\n\n\n");
                    }



                }
                showMessage("Expence Data",bfr.toString());

            }
        });

        ((Button)findViewById(R.id.button6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeBudgetActivity.this,DeleteExpense.class);
                startActivity(intent);
            }
        });
    }
    public void showMessage(String Title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(message);
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.bdgetitm:
                Intent intbudgett=new Intent(HomeBudgetActivity.this,BudgetInfo.class);
                startActivity(intbudgett);
                break;
            case R.id.abtitm:
                showMessage("About Project", getResources().getString(R.string.about_string)+"\n\n "+getResources().getString(R.string.Github_Link));
                break;
        }
        return true;
    }


}
