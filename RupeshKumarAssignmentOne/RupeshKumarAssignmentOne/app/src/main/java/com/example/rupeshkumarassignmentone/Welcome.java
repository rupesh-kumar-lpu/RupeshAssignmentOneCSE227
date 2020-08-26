package com.example.rupeshkumarassignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        auth=FirebaseAuth.getInstance();

        final FirebaseUser user=auth.getCurrentUser();



        ((ImageButton)findViewById(R.id.imageButtonref)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.getCurrentUser().reload();
                if(user.isEmailVerified()){
                    ((TextView)findViewById(R.id.emailveri)).setText("Email Status : Verified");
                    ((TextView)findViewById(R.id.emailveri)).setOnClickListener(null);
                    ((ImageButton)findViewById(R.id.imageButtonref)).setVisibility(View.GONE);
                }
            }
        });

        ((Button)findViewById(R.id.button8)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,BudgetManagement.class));
            }
        });


        if(user.isEmailVerified()){
            ((TextView)findViewById(R.id.emailveri)).setText("Your Email is verified");
            ((TextView)findViewById(R.id.emailveri)).setOnClickListener(null);
            ((ImageButton)findViewById(R.id.imageButtonref)).setVisibility(View.GONE);

        }else{
            ((TextView)findViewById(R.id.emailveri)).setText("Your Email is not verified.. Click to verify");
            ((ImageButton)findViewById(R.id.imageButtonref)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.emailveri)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(Welcome.this,"Verification Email Sent",Toast.LENGTH_LONG).show();

                        }
                    });

                }
            });
        }


    }
}
