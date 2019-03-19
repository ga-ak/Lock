package com.kim.cheolho.lock;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.kim.cheolho.lock.dto.LoginStatusDTO;

public class Login extends AppCompatActivity {

    EditText edt_login_id, edt_login_pw;
    Button btn_login_signup, btn_login_login;
    Gson gson;
    String[] inputDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initialize();

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = StaticValues.url + "/login";

                final String user_id = edt_login_id.getText().toString();
                final String user_pw = edt_login_pw.getText().toString();

                gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_id", user_id);
                jsonObject.addProperty("user_pw", user_pw);

                String data = gson.toJson(jsonObject);
                Log.v("json", data);

                AndroidAsyncTask androidAsyncTask = new AndroidAsyncTask(url, data) {
                    @Override
                    public void inOnPostExecute(String returnedJson) {

                        Gson gson = new Gson();
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(returnedJson).getAsJsonObject();
                        LoginStatusDTO loginStatusDTO = gson.fromJson(jsonElement, LoginStatusDTO.class);
                        if (loginStatusDTO.isLoged()) {
                            StaticValues.login_id = user_id;
                            StaticValues.login_pw = user_pw;
                            Intent intent = new Intent(getApplicationContext(), MyKeyWallet.class);
                            intent.putExtra("toMyKeyWallet", returnedJson);
                            startActivity(intent);
                            finish();
                        } else {
                            StaticValues.login_id = null;
                            Toast.makeText(Login.this, "로그인 실패...", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                };

                androidAsyncTask.execute();

            }
        });


        btn_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivityForResult(intent, 1000);
            }
        });

    }

    private void initialize () {

        edt_login_id = findViewById(R.id.edt_login_id);
        edt_login_pw = findViewById(R.id.edt_login_pw);
        btn_login_login = findViewById(R.id.btn_login_login);
        btn_login_signup = findViewById(R.id.btn_login_signup);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
