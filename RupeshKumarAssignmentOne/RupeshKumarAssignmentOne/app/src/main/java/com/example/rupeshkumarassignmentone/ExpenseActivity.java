package com.example.rupeshkumarassignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity {
    MyBudgetDataBase myDatabase;
    EditText dtedit;
    SharedPreferences sharedprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        sharedprefs= getSharedPreferences("MySharedPreff", MODE_PRIVATE);
        myDatabase=new MyBudgetDataBase(this);

        dtedit=(EditText)findViewById(R.id.dateeds);

        DateInputMask dateInputMask=new DateInputMask(dtedit);

        ((Button)findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText usernameEditText = (EditText) findViewById(R.id.editText2);
                String Namee = usernameEditText.getText().toString();
                EditText editText22 = (EditText) findViewById(R.id.editText3);
                String ed2 = editText22.getText().toString();
                EditText editText222 = (EditText) findViewById(R.id.editText4);
                String ed3 = editText222.getText().toString();

                if (ed3.matches("")) {
                    Toast.makeText(ExpenseActivity.this, "Enter some value please!", Toast.LENGTH_SHORT).show();

                } else {
                    if (ed2.matches("")) {
                        Toast.makeText(ExpenseActivity.this, "Enter some value please!", Toast.LENGTH_SHORT).show();

                    } else {
                        if (Namee.matches("")) {
                            Toast.makeText(ExpenseActivity.this, "Enter some value please!", Toast.LENGTH_SHORT).show();

                        } else {

                            if (!(myDatabase.checkEmailAlready(((EditText) findViewById(R.id.editText2)).getText().toString()))) {
                                long id = myDatabase.insertRegisterDataInTable(((EditText) findViewById(R.id.editText2)).getText().toString(), ((EditText) findViewById(R.id.editText3)).getText().toString(), ((EditText) findViewById(R.id.editText4)).getText().toString(),dtedit.getText().toString());
                                SharedPreferences.Editor myEdit
                                        = sharedprefs.edit();

                                // Storing the key and its value
                                // as the data fetched from edittext
                                int getprev = sharedprefs.getInt("consumed", 0);

                                int totalnow=Integer.parseInt(((EditText)findViewById(R.id.editText3)).getText().toString())+getprev;



                                myEdit.putInt("consumed", totalnow);

                                // Once the changes have been made,
                                // we need to commit to apply those changes made,
                                // otherwise, it will throw an error
                                myEdit.commit();
                                Toast.makeText(ExpenseActivity.this,Integer.toString(sharedprefs.getInt("consumed", 0)),Toast.LENGTH_LONG).show();


                                //Toast.makeText(ExpenceActivity.this, "Expence Added Sucessfully!", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ExpenseActivity.this, "Saving Failed! Type alredy exists!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            }
        });
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

