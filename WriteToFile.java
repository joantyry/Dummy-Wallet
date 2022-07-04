package com.example.dummywallet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

    public void WriteToFile(String Name, String ValueName, String Value, File file_Name) {

        //Write  to file

        JSONObject item = new JSONObject();

        try {
            item.put("Name", Name);
            item.put(ValueName, Value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileWriter file = null;
        try {
            file = new FileWriter(file_Name);
            file.write(item.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
