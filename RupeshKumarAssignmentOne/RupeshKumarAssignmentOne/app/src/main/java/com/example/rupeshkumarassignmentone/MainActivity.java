package com.example.rupeshkumarassignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();


        ((TextView)findViewById(R.id.loginactitv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Login.class));

            }
        });

        ((Button)findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd= new ProgressDialog(MainActivity.this);
                pd.setTitle("Please Wait...");
                pd.setMessage("Registering...");
                pd.show();

                if(((EditText)findViewById(R.id.phonenumalt)).getText().toString().equals("")||((EditText)findViewById(R.id.phonenum)).getText().toString().equals("")||((EditText)findViewById(R.id.dateob)).getText().toString().equals("")||((EditText)findViewById(R.id.fullname)).getText().toString().equals("")||((EditText)findViewById(R.id.password)).getText().toString().equals("")||((EditText)findViewById(R.id.email)).getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"All Fields are required",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }else if(((EditText)findViewById(R.id.password)).getText().toString().length()<6){
                    Toast.makeText(MainActivity.this,"Password must have 6 characters",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }else if(((EditText)findViewById(R.id.phonenum)).getText().toString().length()!=10){
                    Toast.makeText(MainActivity.this,"Phone Number must be 10 digits long",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }else if(((EditText)findViewById(R.id.phonenumalt)).getText().toString().length()!=10){
                    Toast.makeText(MainActivity.this,"Alternate Phone Number must be 10 digits long",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }

                else{
                    register(((EditText)findViewById(R.id.phonenum)).getText().toString(),((EditText)findViewById(R.id.phonenumalt)).getText().toString(),((EditText)findViewById(R.id.dateob)).getText().toString(),((EditText)findViewById(R.id.fullname)).getText().toString(),((EditText)findViewById(R.id.email)).getText().toString(),((EditText)findViewById(R.id.password)).getText().toString());
                }
            }
        });




    }
    private void register(final String phone, final String alterphone,final String dob, final String fullnam, String email, String password){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("dateofbirth", dob);
                    hashMap.put("fullname", fullnam);
                    hashMap.put("phone", phone);
                    hashMap.put("alterphone", alterphone);
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                Intent intent = new Intent(MainActivity.this, Welcome.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                        }
                    });

                } else {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "You can't register with this email and password!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}

