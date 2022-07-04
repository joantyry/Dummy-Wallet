package com.example.dummywallet;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class MainActivity extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button KeyGenButton = (Button) findViewById(R.id.KeyGen);
        Button SignButton = (Button) findViewById(R.id.Sign);
        //Button CheckResults = (Button) findViewById(R.id.CheckResults);

        File Hash_file = new File(getFilesDir(), "Hash.json");
        File SecretShare_file = new File(getFilesDir(), "SecretShare.json");
        File InitSecret_file = new File(getFilesDir(), "InitSecret.json");



    /*    int WalletSecret_size = Integer.parseInt(String.valueOf(WalletSecret_file.length()));

        int SecretShare_size = Integer.parseInt(String.valueOf(SecretShare_file.length()));

        int [] file_size = {WalletSecret_size, SecretShare_size};
*/
       //KeyGenButton.setText(Arrays.toString(file_size));
        //BigInteger p = BigInteger.probablePrime(1024, new Random());
       /* //BigInteger  p = BigInteger.valueOf(23);
        BigInteger p_1 = p.subtract(BigInteger.ONE);
        BigInteger i;


        BigInteger g = null;
        ArrayList<BigInteger> array = new ArrayList<BigInteger>();
        ArrayList<BigInteger> gen = new ArrayList<BigInteger>();
        ArrayList<BigInteger> com = new ArrayList<BigInteger>();

        BigInteger pass = BigInteger.valueOf(Long.parseLong("3542660321"));
        BigInteger zop = BigInteger.valueOf(Long.parseLong("3542660"));
        BigInteger re = zop.modPow(zop,pass);*/

       /* for (i = BigInteger.valueOf(1); i.compareTo(p_1) <= 0; i = i.add(BigInteger.ONE)) {

            array.add(i);

            for (BigInteger j = BigInteger.valueOf(1); j.compareTo(p_1) <= 0; j = j.add(BigInteger.ONE)) {

                gen.add(i.modPow(j,p));


                if(Arrays.equals(array.toArray(), gen.toArray())){
                    com.add(i);

                    Arrays.sort(com.toArray());
                }

            *//*BigInteger k = i.modPow(p_1, p);


            if (k.compareTo(BigInteger.ONE)==0) {

                g = i;

            }*//*

            }
        }
*/
       // SignButton.setText(p.toString());
        //KeyGenButton.setText(Arrays.toString(com.toArray()));
        //GenerateSecret
        //String Secret = "14";


        //Write S1 to a file after receiving intent


        //Process received intents
        Intent intent = getIntent();

        String package_name = this.getReferrer().getHost();

        Bundle sentMessage = intent.getExtras();


        if (sentMessage != null) {

            if (package_name.equals("com.example.newapp")) {

                String name = sentMessage.getString("Name");


                if (name.equals("Setup")) {


                    //Read S1 from intent
                    String S1 = sentMessage.getString("S1");

                    //Write S1 to file
                    WriteToFile objWrite1 = new WriteToFile();
                    objWrite1.WriteToFile("Setup", "S1", S1, SecretShare_file);

                    //Delete secret file
                    Boolean bool = InitSecret_file.delete();

                    long endTime = System.nanoTime();


                    //Read times from intent for experiment purposes
                    String StartTime = sentMessage.getString("StartTime");
                    String sharex_Time = sentMessage.getString("sharex_Time");
                    String sharePr_Time = sentMessage.getString("sharePr_Time");
                    String Ec_Time = sentMessage.getString("Ec_Time");

                    long totalSetupTime = endTime - Long.parseLong(StartTime) ;

                    SetupActivity(String.valueOf(totalSetupTime),sharex_Time, sharePr_Time, Ec_Time, bool );


                } else {

                    long hashTime_Recon = System.nanoTime();

                    //Read reconstructed secret
                    String Secret_recon = sentMessage.getString("SecretRecon");

                    //Compare the hash of init secret to recon secret.
                    SecretVerification objVerify = new SecretVerification();
                    Boolean bool = objVerify.SecretVerification(Secret_recon, Hash_file);

                    long totalHashTime_Recon = System.nanoTime()- hashTime_Recon;

                    long End_Recon = System.nanoTime();

                    //Read times from intent for experiment purposes
                    String StartTime_Recon = sentMessage.getString("StartTime");
                    String SecretTime_Recon = sentMessage.getString("SecretTime");
                    String BVTauTime_Recon = sentMessage.getString("BVTauTime");


                    long totalReconTime = End_Recon - Long.parseLong(StartTime_Recon) ;

                    openSecretReconResultsActivity(bool,String.valueOf(totalReconTime), SecretTime_Recon, BVTauTime_Recon, String.valueOf(totalHashTime_Recon ) );

                }


            }

        }


        KeyGenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Read q from string resources
                BigInteger q = new BigInteger(getApplication().getResources().getString(R.string.q));

                //Gen secret and Store in File
                SecretGen objSecret = new SecretGen();
                String Secret = objSecret.SecretGen(InitSecret_file, q);

                //Compute Secret hash and store in file
                StoreSecretHash objstore = new StoreSecretHash();
                objstore.StoreSecretHash(Secret, Hash_file);

                //Send Secret to NewApp(Gawa App)
                KeyGenButtonActivity(Secret);
            }
        });


        SignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Read share S1 from file
                JSONParser parser = new JSONParser();
                org.json.simple.JSONObject object = null;
                try {
                    object = (JSONObject) parser.parse(new FileReader(SecretShare_file.getAbsoluteFile()));
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }

                String S1 = (String) object.get("S1");

                //Send S1 to New App (Gawa App)
                SignButtonActivity(S1);


            }
        });



    }





    public void KeyGenButtonActivity(String Secret)  {

        //Send Secret to NewApp (Gawa App)
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.newapp");
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("Name", "InitSetup");
        intent.putExtra("Secret", Secret);
        intent.setType("text/plain");
        startActivity(intent);
    }



    public void SignButtonActivity(String S1){
        //send S1 to NewApp(Gawa App)

        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.newapp");
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("Name", "Recon");
        intent.putExtra("S1", S1);
        intent.setType("text/plain");
        startActivity(intent);
    }



    public void SetupActivity(String TotalSetupTime, String sharex_Time, String sharePr_Time, String Ec_Time, Boolean bool ){
        //Times for experiment purposes

        Intent intent = new Intent(this, SetupActivity.class);

        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("SetupTime", TotalSetupTime);
        //intent.putExtra("EndTime", EndTime);
        intent.putExtra("sharex_Time", sharex_Time);
        intent.putExtra("sharePr_Time", sharePr_Time);
        intent.putExtra("Ec_Time", Ec_Time);
        intent.putExtra("Boolean", bool);

        intent.setType("text/plain");

        startActivity(intent);
    }

    public void openSecretReconResultsActivity(Boolean bool, String ReconTime, String SecretTime_Recon, String BVTauTime_Recon, String totalHashTime_Recon  ){

        //Times for experiment purposes
        Intent intent = new Intent(this, SecretReconResultsActivity.class);

        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("Boolean", bool.toString());
        intent.putExtra("SecretTime", SecretTime_Recon);
        intent.putExtra("BVTauTime", BVTauTime_Recon);
        intent.putExtra("HashTime", totalHashTime_Recon );
        intent.putExtra("ReconTime", ReconTime );
        intent.setType("text/plain");

        startActivity(intent);
    }




}