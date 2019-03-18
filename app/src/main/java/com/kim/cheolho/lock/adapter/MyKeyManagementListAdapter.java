package com.kim.cheolho.lock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kim.cheolho.lock.R;
import com.kim.cheolho.lock.dto.ManageKeyDTO;

import java.util.ArrayList;

public class MyKeyManagementListAdapter extends BaseAdapter {
    Context applicationContext;
    int list_item;
    ArrayList<ManageKeyDTO> myKeyMans;
    private LayoutInflater inflater;

    public MyKeyManagementListAdapter(Context applicationContext, int list_item, ArrayList<ManageKeyDTO> myKeyMans) {
        this.applicationContext = applicationContext;
        this.list_item = list_item;
        this.myKeyMans = myKeyMans;
        inflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myKeyMans.size();
    }

    @Override
    public Object getItem(int position) {
        return myKeyMans.get(position);
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

        ImageView img_man_thumb = convertView.findViewById(R.id.img_man_thumb);
        ImageView img_man_menu = convertView.findViewById(R.id.img_man_menu);
        TextView txv_man_keyName = convertView.findViewById(R.id.txv_man_keyName);
        TextView txv_man_borrowNum = convertView.findViewById(R.id.txv_man_borrowNum);

        ManageKeyDTO myKeyManagement = (ManageKeyDTO) getItem(position);

        img_man_thumb.setImageResource(R.drawable.ic_launcher_background);
        img_man_menu.setImageResource(R.drawable.menu);
        txv_man_keyName.setText(myKeyManagement.getKeyName());
        txv_man_borrowNum.setText(myKeyManagement.getGivenNum()+"명 대여중");
        return convertView;
    }
}
