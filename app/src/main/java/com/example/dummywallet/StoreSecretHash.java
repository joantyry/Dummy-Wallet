package com.example.dummywallet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StoreSecretHash {

    String Secret;
    File WalletSecret_file;
    public void StoreSecretHash(String Secret, File Hash_file){


        //write hash of the secret to a file
        //1.Compute the hash

        MessageDigest Secret_hash = null;
        try {
            Secret_hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Secret_hash.update(Secret.getBytes(StandardCharsets.UTF_8));

        byte[] byteData = Secret_hash.digest();

        StringBuffer secret_hexString = new StringBuffer();

        for (int i = 0;i< byteData.length;i++) {


            secret_hexString.append(String.format("%02x", 0xff & byteData[i]));

        }

        //2.Write the hash to a file

       WriteToFile objWrite = new WriteToFile();
        objWrite. WriteToFile("Secret_hash", "Secret_hash", secret_hexString.toString(), Hash_file);

        /*JSONObject item = new JSONObject();

        try {
            item.put("Name", "Secret_hash");
            item.put("Secret_hash", secret_hexString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileWriter file = null;
        try {
            file = new FileWriter(WalletSecret_file);
            file.write(item.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}
