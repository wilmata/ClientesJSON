package com.example.clientesjson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class WSJson {

    public static JSONObject getJson(String strUrl) throws IOException, JSONException {
        String jsonStr = null;

        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        // leer la respuesta
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        final int respuesta = conn.getResponseCode();
        if(respuesta == HttpURLConnection.HTTP_OK){
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line).append('\n');
            jsonStr = sb.toString();

            return  new JSONObject(jsonStr);
        }

        return null;
    }

    public static JSONObject sendJson(String strUrl, JSONObject jsonParam) throws IOException, JSONException {
        HttpURLConnection urlConn;

        DataOutputStream printout;
        DataInputStream input;
        URL url = new URL(strUrl);
        urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type", "application/json");
        urlConn.setRequestProperty("Accept", "application/json");
        urlConn.connect();

        // Envio los parámetros post.
        OutputStream os = urlConn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonParam.toString());
        writer.flush();
        writer.close();

        final int respuesta = urlConn.getResponseCode();
        StringBuilder result = new StringBuilder();

        if (respuesta == HttpURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = br.readLine()) != null) {
                result.append(line);
                //response+=line;
            }

            return new JSONObject(result.toString());
        }
        return  null;
    }
}
