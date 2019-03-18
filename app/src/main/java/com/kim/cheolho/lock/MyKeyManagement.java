package com.kim.cheolho.lock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_management);

        Intent intent = getIntent();
        String json = intent.getStringExtra("toManagement");
        gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json).getAsJsonObject();

        ArrayList<ManageKeyDTO> manageKeyDTOS = gson.fromJson(jsonElement, ManageKeyListDTO.class).getManageKeyDTOS();

        myKeyManagementListAdapter = new MyKeyManagementListAdapter(getApplicationContext(), R.layout.list_my_key_management, manageKeyDTOS);

        lsv_myKey_management = findViewById(R.id.lsv_myKey_management);
        lsv_myKey_management.setAdapter(myKeyManagementListAdapter);

        lsv_myKey_management.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tv_man_keyName = view.findViewById(R.id.txv_man_keyName);

                final String doorlock_name = tv_man_keyName.getText().toString();
                String user_id = StaticValues.login_id;

                gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("doorlock_name", doorlock_name);
                jsonObject.addProperty("user_id", user_id);


                String url = StaticValues.url + "/manage/user";
                String data = gson.toJson(jsonObject);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        Intent intent1 = new Intent(getApplicationContext(), MyKeyUserManagement.class);
                        intent1.putExtra("ToMyKeyUserManagement", returnedJson);
                        intent1.putExtra("doorlock_name", doorlock_name);
                        startActivity(intent1);
                        finish();

                    }

                };

                androidAsyncTask.execute();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:

                String url = StaticValues.url + "/manage";

                gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_id", StaticValues.login_id);


                String data = gson.toJson(jsonObject);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        Intent intent2 = new Intent(getApplicationContext(), MyKeyManagement.class);

                        intent2.putExtra("ToManagement", returnedJson);

                        startActivity(intent2);

                        finish();

                    }

                };

                androidAsyncTask.execute();

                break;

            case R.id.menu2:

                item.setCheckable(false);

                break;
        }
        return true;
    }
}
