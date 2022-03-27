package com.example.dummywallet;

import java.io.File;
import java.math.BigInteger;
import java.util.Random;

public class SecretGen {

    public String SecretGen(File InitSecret_file, BigInteger q)  {
        //Generate secret and store it in a file

        Random randNum = new Random();
        int len = 14;
        BigInteger secret = new BigInteger(len, randNum);


        secret = secret .mod(q);



        WriteToFile objWrite1 = new WriteToFile();

        objWrite1.WriteToFile("InitSecret", "Secret", secret.toString(), InitSecret_file);

        return secret.toString();

    }

}
