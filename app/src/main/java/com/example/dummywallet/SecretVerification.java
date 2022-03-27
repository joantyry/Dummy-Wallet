package com.example.dummywallet;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecretVerification {

    public boolean SecretVerification(String Secret_Recon, File WalletSecret_file){

        //Read from file the original secret hash and compare the two
        //1.Compute the hash of  Secret_recon

        MessageDigest SecretRecon_hash = null;
        try {
            SecretRecon_hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        SecretRecon_hash.update(Secret_Recon.getBytes(StandardCharsets.UTF_8));

        byte[] byteData = SecretRecon_hash.digest();

        StringBuffer secretRecon_hexString = new StringBuffer();

        for (int i = 0;i< byteData.length;i++) {


            secretRecon_hexString.append(String.format("%02x", 0xff & byteData[i]));

        }


        //2.Read Secret_hash from file



        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(new FileReader(WalletSecret_file.getAbsoluteFile()));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }



        String Secret_hash = null;
        Secret_hash = (String) object.get("Secret_hash");


       //3.Compare the two hashes
        if(Secret_hash.equals(secretRecon_hexString.toString())){

        return true;

        }

        else{

            return false;
        }

        //String[] arr ={Secret_hash, secretRecon_hexString.toString()};
       // return  arr;

       // return Secret_Recon;
    }
}
