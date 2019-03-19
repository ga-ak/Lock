package com.kim.cheolho.lock;

import android.content.Context;
import android.content.Intent;
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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kim.cheolho.lock.adapter.MyKeyUserManagementAdapter;
import com.kim.cheolho.lock.dto.ManageUserDTO;
import com.kim.cheolho.lock.dto.ManageUserListDTO;

import java.util.ArrayList;

public class MyKeyUserManagement extends AppCompatActivity {
    Gson gson;
    ListView listView;
    String doorlock_name;
    Button btn_give_key_to_user;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_user_management);
        context = this;

        listView = findViewById(R.id.lv_user_management);
        btn_give_key_to_user = findViewById(R.id.btn_give_key_to_user);

        Intent intent = getIntent();
        String json = null;
        if (intent.getStringExtra("ToMyKeyUserManagement") != null) {
            json = intent.getStringExtra("ToMyKeyUserManagement");
        } if (intent.getStringExtra("ToMyKeyUserManagement1") != null) {
            json = intent.getStringExtra("ToMyKeyUserManagement1");
        }

        doorlock_name = intent.getStringExtra("doorlock_name");

        gson = new Gson();
        JsonParser jsonParser1 = new JsonParser();
        JsonElement jsonElement1 = jsonParser1.parse(json).getAsJsonObject();

        ManageUserListDTO manageUserListDTO = gson.fromJson(jsonElement1, ManageUserListDTO.class);

        ArrayList<ManageUserDTO> manageUserDTOS = manageUserListDTO.getManageUserDTOS();

        MyKeyUserManagementAdapter myKeyUserManagementAdapter = new MyKeyUserManagementAdapter( getApplicationContext(), R.layout.list_my_key_user_management, manageUserDTOS, doorlock_name, context);

        listView.setAdapter(myKeyUserManagementAdapter);

        // TODO: 해당 회원 클릭시 회원의 키를 제어할 수 있는 창이 또 뜸
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String user_id = StaticValues.login_id;
                String urlToLendKeyToUser = StaticValues.url + "/user_ids";

                gson = new Gson();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("user_id", user_id);
                String data = gson.toJson(jsonObject1);

                AndroidAsyncTask androidAsyncTask1 = new AndroidAsyncTask(urlToLendKeyToUser, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        Intent intentToAddDoorlock = new Intent(getApplicationContext(), LendKeyToUser.class);
                        intentToAddDoorlock.putExtra("user_list", returnedJson);
                        startActivity(intentToAddDoorlock);

                    }
                };

            }
        });

        btn_give_key_to_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 통신
                String url = StaticValues.url + "/user_list";
                String user_id = StaticValues.login_id;

                gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_id", user_id);
                String data = gson.toJson(jsonObject);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        Intent intent = new Intent(getApplicationContext(), LendKeyToUser.class);
//                        intent.putExtra("user_list", returnedJson);
                        intent.putExtra("doorlock_name", doorlock_name);
                        startActivity(intent);

                        finish();

                    }
                };

                androidAsyncTask.execute();

            }
        });

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

                String url1 = StaticValues.url + "/login";

                gson = new Gson();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("user_id", StaticValues.login_id);
                jsonObject1.addProperty("user_pw", StaticValues.login_pw);

                String data = gson.toJson(jsonObject1);

                AndroidAsyncTask androidAsyncTask1 = new AndroidAsyncTask(url1, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

//                        Toast.makeText(getApplicationContext(),returnedJson,Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(getApplicationContext(), MyKeyWallet.class);
//
                        intent2.putExtra("ToMyKeyWallet3", returnedJson);
//
                        startActivity(intent2);
//
                        finish();

                    }

                };

                androidAsyncTask1.execute();

                break;

            case R.id.menu2:

                String url2 = StaticValues.url + "/manage";

                gson = new Gson();
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("user_id", StaticValues.login_id);


                String data2 = gson.toJson(jsonObject2);

                AndroidAsyncTask androidAsyncTask2 = new AndroidAsyncTask(url2, data2) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        Intent intent2 = new Intent(getApplicationContext(), MyKeyManagement.class);

                        intent2.putExtra("toManagement4", returnedJson);
                        Log.v("이거", returnedJson);

                        startActivity(intent2);

                        finish();

                    }

                };

                androidAsyncTask2.execute();

                break;
        }
        return true;
    }

}
