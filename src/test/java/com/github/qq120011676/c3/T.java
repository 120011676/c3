package com.github.qq120011676.c3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class T {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://xingzhengquhua.bmcx.com/510000000000__xingzhengquhua/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                System.out.println(content.toString());
            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
