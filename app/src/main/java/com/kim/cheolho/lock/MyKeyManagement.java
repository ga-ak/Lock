package com.kim.cheolho.lock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kim.cheolho.lock.adapter.MyKeyManagementListAdapter;
import com.kim.cheolho.lock.dto.ManageKeyDTO;
import com.kim.cheolho.lock.dto.ManageKeyListDTO;

import java.util.ArrayList;

public class MyKeyManagement extends AppCompatActivity {

    private ListView lsv_myKey_management;
    private MyKeyManagementListAdapter myKeyManagementListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_management);

        Intent intent = getIntent();
        String json = intent.getStringExtra("toManagement");
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json).getAsJsonObject();

        ArrayList<ManageKeyDTO> manageKeyDTOS = gson.fromJson(jsonElement, ManageKeyListDTO.class).getManageKeyDTOS();

        myKeyManagementListAdapter = new MyKeyManagementListAdapter(getApplicationContext(), R.layout.list_my_key_management, manageKeyDTOS);

        lsv_myKey_management = findViewById(R.id.lsv_myKey_management);
        lsv_myKey_management.setAdapter(myKeyManagementListAdapter);

    }
}
