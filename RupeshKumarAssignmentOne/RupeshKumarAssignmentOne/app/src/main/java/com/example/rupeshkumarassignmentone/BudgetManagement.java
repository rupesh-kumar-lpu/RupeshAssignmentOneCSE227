package com.example.rupeshkumarassignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BudgetManagement extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_management);

        sharedPreferences= getSharedPreferences("MySharedPref", MODE_PRIVATE);
        sh= getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        if(!(Integer.toString((sh.getInt("budget", 0)))).equals("0")){
            Intent intent=new Intent(BudgetManagement.this,HomeBudgetActivity.class);
            startActivity(intent);
            finish();
        }
        ((Button)findViewById(R.id.buttonnn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText usernameEditText = (EditText) findViewById(R.id.editText);
                String Val = usernameEditText.getText().toString();
                if (Val.matches("")) {
                    Toast.makeText(BudgetManagement.this, "Enter some value please!", Toast.LENGTH_SHORT).show();

                } else {
                    // Creating an Editor object
// to edit(write to the file)
                    SharedPreferences.Editor myEdit
                            = sharedPreferences.edit();

// Storing the key and its value
// as the data fetched from edittext

                    myEdit.putInt("budget", Integer.parseInt(((EditText) findViewById(R.id.editText)).getText().toString()));

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                    myEdit.commit();
                    Intent intent = new Intent(BudgetManagement.this, HomeBudgetActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }
}
