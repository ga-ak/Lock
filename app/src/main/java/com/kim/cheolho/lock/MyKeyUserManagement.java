package com.kim.cheolho.lock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kim.cheolho.lock.adapter.MyKeyUserManagementAdapter;
import com.kim.cheolho.lock.dto.ManageUserDTO;
import com.kim.cheolho.lock.dto.ManageUserListDTO;

import java.util.ArrayList;

public class MyKeyUserManagement extends AppCompatActivity {
    Gson gson;
    ListView listView;
    String doorlock_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_user_management);

        listView = findViewById(R.id.lv_user_management);

        Intent intent = getIntent();
        String json = intent.getStringExtra("ToMyKeyUserManagement");
        doorlock_name = intent.getStringExtra("doorlock_name");

        gson = new Gson();
        JsonParser jsonParser1 = new JsonParser();
        JsonElement jsonElement1 = jsonParser1.parse(json).getAsJsonObject();

        ManageUserListDTO manageUserListDTO = gson.fromJson(jsonElement1, ManageUserListDTO.class);

        ArrayList<ManageUserDTO> manageUserDTOS = manageUserListDTO.getManageUserDTOS();

        MyKeyUserManagementAdapter myKeyUserManagementAdapter = new MyKeyUserManagementAdapter(getApplicationContext(), manageUserDTOS, doorlock_name);

        listView.setAdapter(myKeyUserManagementAdapter);

        // TODO: 해당 회원 클릭시 회원의 키를 제어할 수 있는 창이 또 뜸
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
