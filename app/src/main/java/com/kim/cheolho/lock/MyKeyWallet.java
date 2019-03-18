package com.kim.cheolho.lock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kim.cheolho.lock.adapter.MyKeyListAdapter;
import com.kim.cheolho.lock.dto.DoorLockDTO;
import com.kim.cheolho.lock.dto.MyKeyListDTO;

import java.util.ArrayList;

public class MyKeyWallet extends AppCompatActivity {

    private ListView lsv_myKey;
    private MyKeyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_wallet);

        Intent intent = getIntent();
        String json = intent.getStringExtra("toMyKeyWallet");
        Gson gson = new Gson();
        MyKeyListDTO myKeyListDTO = gson.fromJson(json, MyKeyListDTO.class);
        ArrayList<DoorLockDTO> myKeyList = myKeyListDTO.getDoorLockDTOArrayList();

        myAdapter = new MyKeyListAdapter(getApplicationContext(), R.layout.list_my_key, myKeyList);

        lsv_myKey = findViewById(R.id.lsv_myKey);
        lsv_myKey.setAdapter(myAdapter);



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
                Intent intent2 = new Intent(getApplicationContext(), MyKeyManagement.class);
                startActivity(intent2);
                break;
        }
        return true;
    }

}
