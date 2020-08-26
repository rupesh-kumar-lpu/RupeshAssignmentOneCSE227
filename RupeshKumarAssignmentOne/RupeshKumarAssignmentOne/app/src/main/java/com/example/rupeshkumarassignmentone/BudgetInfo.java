package com.example.rupeshkumarassignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BudgetInfo extends AppCompatActivity {

    SharedPreferences sharedprefs;
    int Consumed = 0;
    int Total = 0;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_info);
        sharedprefs= getSharedPreferences("MySharedPreff", MODE_PRIVATE);
        int consumed = sharedprefs.getInt("consumed", 0);

        sh= getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int total = sh.getInt("budget", 0);
        Toast.makeText(BudgetInfo.this,Integer.toString(sharedprefs.getInt("consumed", 0)),Toast.LENGTH_LONG).show();

        updateChart(total,consumed);
        ((TextView)findViewById(R.id.rmaing_bal)).setText("Remaining Balance : "+Integer.toString(total-consumed));
    }



    public void updateChart(int tt,int cs){
        // Update the text in a center of the chart:
        TextView numberOfCals = findViewById(R.id.number_of_calories);
        numberOfCals.setText(String.valueOf(cs) + " / " + tt);

        // Calculate the slice size and update the pie chart:
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);
        double d = (double) cs / (double) tt;
        int progress = (int) (d * 100);
        pieChart.setProgress(progress);

    }


}
