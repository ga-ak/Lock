package com.kim.cheolho.lock;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestHttpURLConnection {

    public String request(String _url, String _params) {

        // HttpURLConnextion 참조 변수.
        HttpURLConnection urlConn = null;
        // URL 뒤에 붙여서 보낼 파라미터.
        StringBuffer sbParams = new StringBuffer();

        /**
         *  2. HttpURLConnection을 통해 web의 데이터를 가져온다.
         **/

        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            // [2-1] url 설정

            urlConn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : POST
            urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accent-Charset 설정
            urlConn.setRequestProperty("content-type", "application/json"); // Header
            // [2-2] parameter 전달 및 데이터 읽어오기
            String strParams = _params; //sbParams에 정리한 파라미터들을 스트링으로 저장. 예)id=id1&pw=123;
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8")); // 출력 스트림에 출력
            os.flush(); // 출력 스트림을 비우고 버퍼링된 모든 출력 바이트를 강제 실행
            os.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제

            // [2-3] 연결 요청 확인
            // 실패시 null을 리턴하고 메서드 종료
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            // [2-4] 읽어온 결과물 리턴
            // 요청한 URL의 출력물을 BufferedReader로 받는다
            BufferedReader reader = new BufferedReader((new InputStreamReader(urlConn.getInputStream(), "UTF-8")));

            // 출력물의 라인과 그 함에 대한 변수.
            String line;
            String page = "";

            // 라인을 받아와 합친다
            while ((line = reader.readLine()) != null) {
                page += line;
            }

            return page;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }

        return null;
    }
}