package com.kim.cheolho.lock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AddDoorlock extends AppCompatActivity {

    EditText edt_doorlock_name, edt_doorlcok_ip, edt_doorlock_pw;
    Button btn_confirm, btn_cancel;
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doorlock);

        edt_doorlock_name = findViewById(R.id.edit_add_doorlock_name);
        edt_doorlcok_ip = findViewById(R.id.edit_add_doorlock_ip);
        edt_doorlock_pw = findViewById(R.id.edit_add_doorlock_pw);
        btn_confirm = findViewById(R.id.btn_add_doorlock);
        btn_confirm = findViewById(R.id.btn_add_doorlock_confirm);
        btn_cancel = findViewById(R.id.btn_add_doorlock_cancel);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlAddDoorlock = StaticValues.url + "/add_doorlock";

                String doorlock_name = edt_doorlock_name.getText().toString();
                String doorlock_nfc = edt_doorlock_pw.getText().toString();
                String doorlock_ip = edt_doorlcok_ip.getText().toString();
                String user_id = StaticValues.login_id;

                gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("doorlock_name", doorlock_name);
                jsonObject.addProperty("doorlock_nfc", doorlock_nfc);
                jsonObject.addProperty("doorlock_ip", doorlock_ip);
                jsonObject.addProperty("user_id", user_id);

                String data = gson.toJson(jsonObject);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(urlAddDoorlock, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        gson = new Gson();
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(returnedJson).getAsJsonObject();
                        BooleanAnswerFromServer booleanAnswerFromServer = gson.fromJson(jsonElement, BooleanAnswerFromServer.class);

                        if (booleanAnswerFromServer.isConfirmed()) {

                            getReadyForToMyKeyManager();

                            Intent intent = new Intent(getApplicationContext(), MyKeyManagement.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddDoorlock.this, "도어락 추가 실패", Toast.LENGTH_SHORT).show();
                        }


                    }
                };

                androidAsyncTask.execute();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getReadyForToMyKeyManager();

            }
        });

    }

    private void getReadyForToMyKeyManager() {

        String url = StaticValues.url + "/manage";

        gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", StaticValues.login_id);


        String data = gson.toJson(jsonObject);

        AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
            @Override
            public void inOnPostExecute(String returnedJson) {

                Intent intent2 = new Intent(getApplicationContext(), MyKeyManagement.class);

                intent2.putExtra("toManagement3", returnedJson);
                Log.v("이거", returnedJson);

                startActivity(intent2);

                finish();

            }

        };

        androidAsyncTask.execute();

    }
}
