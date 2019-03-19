package com.kim.cheolho.lock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LendKeyToUser extends AppCompatActivity {

    String user_list;
    Button btn_lend_date_choose;
    Spinner spin_lend_doorlock_member;
    EditText edt_lend_doorlock_count;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_key_to_user);

        Intent intent = getIntent();
        user_list = intent.getStringExtra("user_list");

        btn_lend_date_choose = findViewById(R.id.btn_lend_date_choose);
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

                new DatePickerDialog(getApplicationContext(), mDateSetListener, mYear, mMonth, mDay).show();

            }
        });

//        spin_lend_doorlock_member


    }

}
