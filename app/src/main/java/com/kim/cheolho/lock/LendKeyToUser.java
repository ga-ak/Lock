package com.kim.cheolho.lock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LendKeyToUser extends AppCompatActivity {

    String user_list;
    Button btn_lend_date_choose, btn_cancel, btn_confirm;
    Spinner spin_lend_doorlock_member;
    EditText edt_lend_doorlock_count;
    int mYear, mMonth, mDay;
    Gson gson;
    ArrayAdapter spinnerAdapter;
    String doorlock_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_key_to_user);

        Intent intent = getIntent();
        doorlock_name = intent.getStringExtra("doorlock_name");
//        user_list = intent.getStringExtra("user_list");
//
//        gson = new Gson();
//        JsonParser jsonParser = new JsonParser();
//        JsonElement jsonElement = jsonParser.parse(user_list).getAsJsonObject();


        btn_lend_date_choose = findViewById(R.id.btn_lend_date_choose);
        btn_confirm = findViewById(R.id.btn_lend_confirm);
        btn_cancel = findViewById(R.id.btn_lend_doorlock_cancel);
        spin_lend_doorlock_member = findViewById(R.id.spin_lend_doorlock_member);
        edt_lend_doorlock_count = findViewById(R.id.edt_lend_doorlock_count);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //사용자가 입력한 값을 가져온뒤
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth + 1;

                String expire_date = mYear + "-" + mMonth + "-" + mDay;
                btn_lend_date_choose.setText(expire_date);
                btn_lend_date_choose.setBackgroundColor(Color.WHITE);
            }

        };

        btn_lend_date_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(LendKeyToUser.this, mDateSetListener, mYear, mMonth, mDay).show();

            }
        });


        ArrayList<String> memlist = new ArrayList<>();
        memlist.add("회원을 선택하세요");
        memlist.add("dd");
        memlist.add("dd");
        memlist.add("dd");

        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, memlist);

        spin_lend_doorlock_member.setAdapter(spinnerAdapter);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReadyForMyKeyUserManagement();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 통신
                String url = StaticValues.url + "/lend_key";

                String user_id = spin_lend_doorlock_member.getSelectedItem().toString();
                Log.v("debug", user_id);
                String access_number = "";
                if (!edt_lend_doorlock_count.getText().toString().equals("")) {
                    access_number = edt_lend_doorlock_count.getText().toString();
                } else {
                    access_number = 0 + "";
                }

                String expire_date = "";
                if (btn_lend_date_choose.getText().toString().equals("날짜선택하기")) {
                    expire_date = "0";
                } else {
                    expire_date = btn_lend_date_choose.getText().toString();
                }

                gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_id", user_id);
                jsonObject.addProperty("doorlock_name", doorlock_name);
                jsonObject.addProperty("access_number", access_number);
                jsonObject.addProperty("expire_date", expire_date);

                String data = gson.toJson(jsonObject);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        /// TODO: 통신

                        getReadyForMyKeyUserManagement();

                    }
                };

                androidAsyncTask.execute();
            }
        });

    }

    private void getReadyForMyKeyUserManagement() {

        String user_id = StaticValues.login_id;

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
                intent1.putExtra("ToMyKeyUserManagement1", returnedJson);
                intent1.putExtra("doorlock_name", doorlock_name);
                startActivity(intent1);
            }

        };

        androidAsyncTask.execute();
    }


}
