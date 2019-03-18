package com.kim.cheolho.lock;

import android.os.AsyncTask;

public abstract class AndroidAsyncTask extends AsyncTask<Void, Void, String> {
    private String url;
    private String data;

    public AndroidAsyncTask(String url, String data) {
        this.url = url;
        this.data = data;
    }


    @Override
    protected String doInBackground(Void... voids) {

        String result; // 요청 결과를 저장하는 변수
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, data); // 해당 URL로부터 결과물을 얻어옴

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        inOnPostExecute(s);

    }

    public abstract void inOnPostExecute(String returnedJson);

}
