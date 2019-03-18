package com.kim.cheolho.lock.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kim.cheolho.lock.AndroidAsyncTask;
import com.kim.cheolho.lock.MyKeyManagement;
import com.kim.cheolho.lock.R;
import com.kim.cheolho.lock.dto.DoorLockDTO;

import java.util.ArrayList;

public class MyKeyListAdapter extends BaseAdapter {

    Context applicationContext;
    int list_item;
    ArrayList<DoorLockDTO> myKeys;
    private LayoutInflater inflater;
    Gson gson;

    public MyKeyListAdapter(Context applicationContext, int list_item, ArrayList<DoorLockDTO> myKeys ) {
        this.applicationContext = applicationContext;
        this.list_item = list_item;
        this.myKeys = myKeys;
        inflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myKeys.size();
    }

    @Override
    public Object getItem(int position) {
        return myKeys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(list_item, parent, false);
        }

        ImageView img_permission = convertView.findViewById(R.id.img_permission);
        ImageView img_menu = convertView.findViewById(R.id.img_menu);
        TextView txv_key_name = convertView.findViewById(R.id.txv_key_name);
        TextView txv_key_manager = convertView.findViewById(R.id.txv_key_manager);
        TextView txv_remain = convertView.findViewById(R.id.txv_remain);
        TextView txv_remain_tail = convertView.findViewById(R.id.txv_remain_tail);

        DoorLockDTO myKey = (DoorLockDTO) getItem(position);
        // todo 일단해봄... activity에서 일괄적으로 바꿔주는게 나을지도...
        img_permission.setImageResource(convertView.getResources().getIdentifier("permission" + (myKey.getPermission()), "drawable", applicationContext.getPackageName()));
        img_menu.setImageResource(R.drawable.menu);
        txv_key_name.setText(myKey.getKeyName());
        txv_key_manager.setText(myKey.getKeyManager());
        txv_remain.setText(myKey.getKeyRemain());
        if (myKey.isTime()) {
            txv_remain_tail.setText("분 남음");
        } else {
            txv_remain_tail.setText("회 남음");
        }

        return convertView;
    }
}
