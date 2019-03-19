package com.kim.cheolho.lock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button btn_add_doorlock;
    private ArrayList<ManageKeyDTO> manageKeyDTOS;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_management);

        initialize();


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


                String urlToManageUsers = StaticValues.url + "/manage/user";
                String data = gson.toJson(jsonObject);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(urlToManageUsers, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        // TODO: 이것 때문에 에러날지도?
                        Intent intent1 = new Intent(getApplicationContext(), MyKeyUserManagement.class);
                        intent1.putExtra("ToMyKeyUserManagement", returnedJson);
                        intent1.putExtra("doorlock_name", doorlock_name);
                        startActivity(intent1);
                        finish();

                    }

                };

                androidAsyncTask.execute();

                btn_add_doorlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent3 = new Intent(getApplicationContext(), AddDoorlock.class);

                        startActivity(intent3);

                        finish();
                    }
                });

            }
        });
    }


    private void initialize() {

        Intent intent = getIntent();
        String json = null;
        if (intent.getStringExtra("toManagement") != null) {
            json = intent.getStringExtra("toManagement");
        } else if (intent.getStringExtra("toManagement2") != null) {
            json = intent.getStringExtra("toManagement2");
        }

        gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json).getAsJsonObject();

        manageKeyDTOS = gson.fromJson(jsonElement, ManageKeyListDTO.class).getManageKeyDTOS();

        myKeyManagementListAdapter = new MyKeyManagementListAdapter(getApplicationContext(), R.layout.list_my_key_management, manageKeyDTOS);

        lsv_myKey_management = findViewById(R.id.lsv_myKey_management);
        btn_add_doorlock = findViewById(R.id.btn_add_doorlock);

        lsv_myKey_management.setAdapter(myKeyManagementListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:

                String url = StaticValues.url + "/login";

                gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_id", StaticValues.login_id);
                jsonObject.addProperty("user_pw", StaticValues.login_pw);

                String data = gson.toJson(jsonObject);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        Toast.makeText(getApplicationContext(),returnedJson,Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(getApplicationContext(), MyKeyWallet.class);
//
                        intent2.putExtra("ToMyKeyWallet2", returnedJson);
//
                        startActivity(intent2);
//
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
