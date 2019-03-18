package com.kim.cheolho.lock.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kim.cheolho.lock.R;
import com.kim.cheolho.lock.dto.ManageUserDTO;

import java.util.ArrayList;

public class MyKeyUserManagementAdapter extends BaseAdapter {

    Context context;
    ArrayList<ManageUserDTO> manageUserDTOS;

    public MyKeyUserManagementAdapter(Context context, ArrayList<ManageUserDTO> manageUserDTOS) {
        this.context = context;
        this.manageUserDTOS = manageUserDTOS;

    }

    @Override
    public int getCount() {
        return manageUserDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return manageUserDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        TextView tv_user_man_keyName = view.findViewById(R.id.txv_user_man_keyName);
        TextView tv_user_man_borrowNum = view.findViewById(R.id.txv_user_man_borrowNum);
        ImageView img_user_man_thumb = view.findViewById(R.id.img_user_man_thumb);
        ImageView img_user_man_menu = view.findViewById(R.id.img_user_man_menu);

        ManageUserDTO manageUserDTO = (ManageUserDTO) getItem(position);

        tv_user_man_keyName.setText(manageUserDTO.getUser_user_id());
        // TODO 수정할 것, 이미지 뷰 그림 바꾸기
        String string = manageUserDTO.getAccess_number() + manageUserDTO.getExpire_date();
        tv_user_man_borrowNum.setText(string);


        // TODO: 클릭시 뭔가 다른 것이 필요??
        img_user_man_menu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                return false;
            }
        });


        return view;
    }
}
