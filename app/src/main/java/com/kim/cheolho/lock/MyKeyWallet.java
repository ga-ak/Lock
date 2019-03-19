package com.kim.cheolho.lock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kim.cheolho.lock.adapter.MyKeyListAdapter;
import com.kim.cheolho.lock.dto.DoorLockDTO;
import com.kim.cheolho.lock.dto.ManageKeyListDTO;
import com.kim.cheolho.lock.dto.MyKeyListDTO;

import java.util.ArrayList;

public class MyKeyWallet extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lsv_myKey;
    private MyKeyListAdapter myAdapter;
    Gson gson;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_wallet);

        Intent intent = getIntent();
        String json = null;
        if (intent.getStringExtra("toMyKeyWallet") != null) {
            json = intent.getStringExtra("toMyKeyWallet");
        } else if (intent.getStringExtra("ToMyKeyWallet2") != null) {
            json = intent.getStringExtra("ToMyKeyWallet2");
        } else if (intent.getStringExtra("ToMyKeyWallet3") != null) {
            json = intent.getStringExtra("ToMyKeyWallet3");
        }

        Log.v("debug", json);
        Gson gson = new Gson();
        MyKeyListDTO myKeyListDTO = gson.fromJson(json, MyKeyListDTO.class);
        ArrayList<DoorLockDTO> myKeyList = myKeyListDTO.getDoorLockDTOArrayList();

        myAdapter = new MyKeyListAdapter(getApplicationContext(), R.layout.list_my_key, myKeyList);

        lsv_myKey = findViewById(R.id.lsv_myKey);
        lsv_myKey.setAdapter(myAdapter);

        lsv_myKey.setOnItemClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                item.setCheckable(false);
                break;

            case R.id.menu2:

                String url = StaticValues.url + "/manage";

                gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_id", StaticValues.login_id);


                String data = gson.toJson(jsonObject);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        Intent intent2 = new Intent(getApplicationContext(), MyKeyManagement.class);

                        intent2.putExtra("toManagement", returnedJson);
                        Log.v("이거", returnedJson);

                        startActivity(intent2);

                        finish();

                    }

                };

                androidAsyncTask.execute();

                break;
        }
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyKeyWallet.this);

        builder.setTitle("연장신청하시겠습니까?");
        builder.setNegativeButton("OK", dialogListener);
        builder.setPositiveButton("NO", null);

        alertDialog = builder.create();
        alertDialog.show();

    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int position) {

            Toast.makeText(getApplicationContext(), "연장 신청이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }

    };
}
