package com.example.dummywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Arrays;

public class SecretReconResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_recon_results);

        Button button = (Button) findViewById(R.id.Results);

        //Receive intent
        Intent intent = getIntent();
        //Boolean bool = Boolean.valueOf(intent.getStringExtra("Boolean"));
        String bool = getIntent().getStringExtra("Boolean");

        String SecretTime = getIntent().getStringExtra("SecretTime");
        String BVTauTime = getIntent().getStringExtra("BVTauTime");
        String HashTime = getIntent().getStringExtra("HashTime");
        String ReconTime = getIntent().getStringExtra("ReconTime");

       // button.setText(bool);

      //if(bool.contentEquals(bool[1])){
           // button.setText("Success!");


       // }
        if (bool.equals("true")){
           // button.setText("ReconTime = "+ ReconTime + "\n" +"SecretTime = " + SecretTime +"\n"+ "BVTauTime = " +BVTauTime +"\n"+ "HashTime = " +HashTime +"\n"+"Status:"+"Success!") ;
            button.setText("Status: Success!");

        }
        else {
            button.setText("Status: Failed!");
        }

     /*  if (bool.equals(true))
            button.setText("Success!");
        }

        else{
            button.setText("Failed!");
        }*/
    }
}