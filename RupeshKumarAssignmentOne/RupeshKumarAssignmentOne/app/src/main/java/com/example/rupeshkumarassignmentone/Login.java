package com.example.rupeshkumarassignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();

        ((TextView)findViewById(R.id.registeractitv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));

            }
        });

        ((Button)findViewById(R.id.loginbt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd=new ProgressDialog(Login.this);
                pd.setTitle("Please Wait");
                pd.setMessage("Trying to login...");
                pd.show();

                if(((EditText)findViewById(R.id.passwordlg)).getText().toString().equals("")||((EditText)findViewById(R.id.emaillg)).getText().toString().equals("")){

                    Toast.makeText(Login.this,"Fiels can't be empty",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }else{

                    auth.signInWithEmailAndPassword(((EditText)findViewById(R.id.emaillg)).getText().toString(),((EditText)findViewById(R.id.passwordlg)).getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        pd.dismiss();
                                        Intent intet=new Intent(Login.this,Welcome.class);
                                        intet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intet);
                                        finish();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                        pd.dismiss();
                                    }
                                });

                            }else{
                                pd.dismiss();
                                Toast.makeText(Login.this,"Auth Failed!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }


            }
        });


    }
}
