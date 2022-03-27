package com.example.dummywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.math.BigInteger;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Button button = (Button) findViewById(R.id.WalletSetup);

        //Receive intent
        Intent intent = getIntent();

        String SetupTime = getIntent().getStringExtra("SetupTime");
        //String EndTime = getIntent().getStringExtra("EndTime");
        String sharex_Time = getIntent().getStringExtra("sharex_Time");
        String sharePr_Time = getIntent().getStringExtra("sharePr_Time");
        String Ec_Time = getIntent().getStringExtra("Ec_Time");
        String bool = getIntent().getStringExtra("Boolean");

        //BigInteger TotalSetupTime = BigInteger.valueOf(Long.parseLong(EndTime)).subtract(BigInteger.valueOf(Long.parseLong(StartTime))) ;

        button.setText("TotalSetupTime = " + SetupTime +"\n"+ "sharex_Time = " + sharex_Time +"\n"+ "sharePr_Time =" + sharePr_Time +"\n"+ "Ec_Time =" + Ec_Time +"\n"+ "SecretFile Deleted:" + bool) ;
    }
}