package com.kim.cheolho.lock;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
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

public class MyKeyManagement extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lsv_myKey_management;
    private MyKeyManagementListAdapter myKeyManagementListAdapter;
    private Button btn_add_doorlock;
    private ArrayList<ManageKeyDTO> manageKeyDTOS;
    AlertDialog customDialog;
    Gson gson;
    String doorlock_name, user_id;
    TextView tv_man_keyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_management);

        initialize();


        lsv_myKey_management.setOnItemClickListener(this);

        btn_add_doorlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(getApplicationContext(), AddDoorlock.class);

                startActivity(intent3);

                finish();
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
        } else if (intent.getStringExtra("toManagement3") != null) {
            json = intent.getStringExtra("toManagement3");
        } else if (intent.getStringExtra("toManagement4") != null) {
            json = intent.getStringExtra("toManagement4");
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

//                        Toast.makeText(getApplicationContext(),returnedJson,Toast.LENGTH_SHORT).show();

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


        @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        TextView tv_man_keyName = view.findViewById(R.id.txv_man_keyName);
        doorlock_name = tv_man_keyName.getText().toString();
        user_id = StaticValues.login_id;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //커스텀다이얼로그를 위한 레이아웃 엑셈엘 초기화
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(v);

        builder.setPositiveButton("유저 관리 목록으로 가기",dialogListenerToUserManagement);
        builder.setNegativeButton("원격으로 열기",dialogListenerRemoteControl);
        builder.setNeutralButton("취소", dialogListenerCancel);

        customDialog = builder.create();
        customDialog.show();

    }

    DialogInterface.OnClickListener dialogListenerToUserManagement = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int position) {

            gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("doorlock_name", doorlock_name);
            jsonObject.addProperty("user_id", user_id);

            String urlToManageUsers = StaticValues.url + "/manage/user";
            String data = gson.toJson(jsonObject);

            Log.v("debug", data);

            AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(urlToManageUsers, data) {
                @Override
                public void inOnPostExecute(String returnedJson) {

                    Log.v("debug", returnedJson);
                    Intent intent1 = new Intent(getApplicationContext(), MyKeyUserManagement.class);
                    intent1.putExtra("ToMyKeyUserManagement", returnedJson);
                    intent1.putExtra("doorlock_name", doorlock_name);
                    startActivity(intent1);
                }

            };

            androidAsyncTask.execute();

            dialog.dismiss();

        }
    };

    DialogInterface.OnClickListener dialogListenerRemoteControl = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int position) {

        }
    };

    DialogInterface.OnClickListener dialogListenerCancel = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int position) {

            dialog.dismiss();

        }
    };
}
