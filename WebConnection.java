package com.example.fm24mhz;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class WebConnection {
    public String request(String _url, ContentValues _params) {
        HttpURLConnection urlConnection = null;
        StringBuffer sbParams = new StringBuffer();

        // 1. StringBuffer에 파라미터 연결
        if (_params == null) sbParams.append("");
        else {
            boolean isAnd = false;
            String key;
            String value;

            for(Map.Entry<String, Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                if (isAnd) sbParams.append("&");
                sbParams.append(key).append("=").append(value);

                if (!isAnd) {
                    if (_params.size() >= 2) isAnd = true;
                }
            }
        }

        // 2. HttpURLConnection을 통해 web의 데이터를 가져온다.
        try {
            URL url = new URL(_url);
            urlConnection = (HttpURLConnection) url.openConnection();

            // 2.1 urlConnection 설정.
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            urlConnection.setConnectTimeout(2 * 1000);

            // 2.2 parameter 전달 및 데이터 읽어오기.
            String strParams = sbParams.toString();
            OutputStream os = urlConnection.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));
            os.flush();
            os.close();

            // 2.3 연결 요청 확인.
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) return null;

            // 2.4 읽어온 결과 리턴.
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line;
            String result = "";

            while( (line = reader.readLine()) != null) result += line;

            return result;
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return null;
    }
}
