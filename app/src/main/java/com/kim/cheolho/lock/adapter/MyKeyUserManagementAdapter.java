package com.kim.cheolho.lock.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kim.cheolho.lock.AndroidAsyncTask;
import com.kim.cheolho.lock.MyKeyUserManagement;
import com.kim.cheolho.lock.R;
import com.kim.cheolho.lock.StaticValues;
import com.kim.cheolho.lock.dto.ManageUserDTO;

import java.util.ArrayList;

public class MyKeyUserManagementAdapter extends BaseAdapter {

    Context context;
    ArrayList<ManageUserDTO> manageUserDTOS;
    String doorlock_nameFromMyKeyManagement;
    Gson gson;
    AlertDialog alertDialog;
    private LayoutInflater inflater;
    int list_item;
    Context context1;

    public MyKeyUserManagementAdapter(Context context, int list_item, ArrayList<ManageUserDTO> manageUserDTOS, String doorlock_name, Context context1) {
        this.context = context;
        this.list_item = list_item;
        this.manageUserDTOS = manageUserDTOS;
        this.doorlock_nameFromMyKeyManagement = doorlock_name;
        this.context1 = context1;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


        if (view == null) {
            view = inflater.inflate(list_item, parent, false);
        }

        TextView tv_user_man_userId = view.findViewById(R.id.txv_user_man_userId);
        TextView tv_user_man_borrowNum = view.findViewById(R.id.txv_user_man_borrowNum);
        ImageView img_user_man_thumb = view.findViewById(R.id.img_user_man_thumb);
        //ImageView img_user_man_menu = view.findViewById(R.id.img_user_man_menu);
        Button btn_user_man_revoke = view.findViewById(R.id.btn_user_man_revoke);

        ManageUserDTO manageUserDTO = (ManageUserDTO) getItem(position);

        tv_user_man_userId.setText(manageUserDTO.getUser_user_id());
        // TODO 수정할 것, 이미지 뷰 그림 바꾸기
        String string = manageUserDTO.getAccess_number() + manageUserDTO.getExpire_date();
        tv_user_man_borrowNum.setText(string);

        final String user_idFromTv = tv_user_man_userId.getText().toString();

        // TODO: 원격으로 열기
        btn_user_man_revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context1);

                builder.setTitle("해당 유저로부터 키를 회수하시겠습니까?");
                builder.setNegativeButton("OK", dialogListener);
                builder.setPositiveButton("NO", null);

                alertDialog = builder.create();
                alertDialog.show();


//                String url = StaticValues.url + "/revoke";
//
//                String user_id = user_idFromTv;
//                String doorlock_name = doorlock_nameFromMyKeyManagement;
//
//                gson = new Gson();
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("user_id", user_id);
//                jsonObject.addProperty("doorlock_name", doorlock_name);
//
//                String data = gson.toJson(jsonObject);
//
//                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
//                    @Override
//                    public void inOnPostExecute(String returnedJson) {
//
//
//
//                    }
//                };

            }
        });

        // TODO: 클릭시 뭔가 다른 것이 필요??
//        img_user_man_menu.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//
//                return false;
//            }
//        });


        return view;
    }

    // TODO: 여기 더 넣으면 좋음
    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int position) {

            Toast.makeText(context.getApplicationContext(), "키가 회수되었습니다.", Toast.LENGTH_SHORT).show();
        }

    };
}
