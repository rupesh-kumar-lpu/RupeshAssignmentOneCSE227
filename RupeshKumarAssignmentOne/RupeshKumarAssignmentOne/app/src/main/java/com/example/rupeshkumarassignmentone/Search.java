package com.example.rupeshkumarassignmentone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Search extends AppCompatActivity {
    MyBudgetDataBase myDatabase;
    DateInputMask dateInputMask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        myDatabase=new MyBudgetDataBase(this);
        EditText dtedit=((EditText)findViewById(R.id.dted));
        dateInputMask=new DateInputMask(dtedit);


        ((Button)findViewById(R.id.srcheddt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor ress=myDatabase.getalldatarowfromdt(((EditText)findViewById(R.id.dted)).getText().toString());
                StringBuffer bfrr=new StringBuffer();
                if(ress.getCount()==0){
                    showMessage("Expence Data","Nothing Found");
                    return;
                }


                if(ress.getString(1)!=null) {
                    bfrr.append("Type : " + ress.getString(1) + "\n");
                }
                if(ress.getString(2)!=null) {
                    bfrr.append("Value : " + ress.getString(2) + "\n");
                }
                if(ress.getString(3)!=null) {
                    bfrr.append("Note : " + ress.getString(3) + "\n");
                }
                if(ress.getString(4)!=null) {
                    bfrr.append("Date : " + ress.getString(4) + "\n\n\n");
                }

                showMessage("Expence Data",bfrr.toString());

            }
        });





        ((Button)findViewById(R.id.searchbt2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res=myDatabase.getalldatarow(((EditText)findViewById(R.id.searched2)).getText().toString());
                StringBuffer bfr=new StringBuffer();
                if(res.getCount()==0){
                    showMessage("Expence Data","Nothing Found");
                    return;
                }


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

                showMessage("Expence Data",bfr.toString());
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

    public class DateInputMask implements TextWatcher {

        private String current = "";
        private String ddmmyyyy = "DDMMYYYY";
        private Calendar cal = Calendar.getInstance();
        private EditText input;

        public DateInputMask(EditText input) {
            this.input = input;
            this.input.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().equals(current)) {
                return;
            }

            String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
            String cleanC = current.replaceAll("[^\\d.]|\\.", "");

            int cl = clean.length();
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) {
                sel++;
            }
            //Fix for pressing delete next to a forward slash
            if (clean.equals(cleanC)) sel--;

            if (clean.length() < 8){
                clean = clean + ddmmyyyy.substring(clean.length());
            }else{
                //This part makes sure that when we finish entering numbers
                //the date is correct, fixing it otherwise
                int day  = Integer.parseInt(clean.substring(0,2));
                int mon  = Integer.parseInt(clean.substring(2,4));
                int year = Integer.parseInt(clean.substring(4,8));

                mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                cal.set(Calendar.MONTH, mon-1);
                year = (year<1900)?1900:(year>2100)?2100:year;
                cal.set(Calendar.YEAR, year);
                // ^ first set year for the line below to work correctly
                //with leap years - otherwise, date e.g. 29/02/2012
                //would be automatically corrected to 28/02/2012

                day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                clean = String.format("%02d%02d%02d",day, mon, year);
            }

            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                    clean.substring(2, 4),
                    clean.substring(4, 8));

            sel = sel < 0 ? 0 : sel;
            current = clean;
            input.setText(current);
            input.setSelection(sel < current.length() ? sel : current.length());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}

