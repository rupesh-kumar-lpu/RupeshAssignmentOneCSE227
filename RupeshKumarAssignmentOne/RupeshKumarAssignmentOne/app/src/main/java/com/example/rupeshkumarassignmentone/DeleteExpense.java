package com.example.rupeshkumarassignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteExpense extends AppCompatActivity {
    MyBudgetDataBase myDatabase;
    SharedPreferences sharedprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_expense);
        myDatabase=new MyBudgetDataBase(this);

        sharedprefs= getSharedPreferences("MySharedPreff", MODE_PRIVATE);

        ((Button)findViewById(R.id.button7)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText usernameEditText = (EditText) findViewById(R.id.editText5);
                String Namee = usernameEditText.getText().toString();

                if (Namee.matches("")) {
                    Toast.makeText(DeleteExpense.this, "Enter some value please!", Toast.LENGTH_SHORT).show();

                } else {

                    if ((myDatabase.checkEmailAlready(((EditText) findViewById(R.id.editText5)).getText().toString()))) {

                        Cursor curs=myDatabase.getalldatarow(((EditText) findViewById(R.id.editText5)).getText().toString());
                        if (curs.moveToFirst()) // data?{
                        {
                            final Integer id = curs.getInt(curs.getColumnIndex("Id"));
                            myDatabase.clearall(id);

                            SharedPreferences.Editor myEdit
                                    = sharedprefs.edit();

                            // Storing the key and its value
                            // as the data fetched from edittext
                            int getprev = sharedprefs.getInt("consumed", 0);

                            int totalnow=getprev-(curs.getInt(curs.getColumnIndex("Value")));



                            myEdit.putInt("consumed", totalnow);

                            // Once the changes have been made,
                            // we need to commit to apply those changes made,
                            // otherwise, it will throw an error
                            myEdit.commit();

                            Toast.makeText(DeleteExpense.this,"Deleted!",Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(DeleteExpense.this, "Saving Failed! Type do not exists!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
