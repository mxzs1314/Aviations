package com.example.administrator.aviation.model.updateversion;

import android.content.Context;

import com.example.administrator.aviation.http.HttpCommons;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取更新信息主要是读取服务器上的.txt文件内容(此类获取更新信息)
 */

public class GetUpdateInfo {
    private String path = "";
    private StringBuffer stringBuffer;
    private String line = "";
    private BufferedReader reader = null;
    private String info = "";
    private UpdateInfo updateInfo;

    public GetUpdateInfo(Context context) {

    }

    public UpdateInfo getUpdateInfo() throws Exception{
        path = HttpCommons.UPDATE_VERSION_URL + "/update.txt";
        stringBuffer = new StringBuffer();
        try {
            // 创建一个url对象
            URL url = new URL(path);

            // 通过url对象，创建一个HttpURLConnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // 通过HttpURLConnection对象得到InputStream
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        info = stringBuffer.toString();
        updateInfo = new UpdateInfo();
        updateInfo.setVersion(info.split("&")[1]);
        updateInfo.setDescription(info.split("&")[2]);
        updateInfo.setUrl(info.split("&")[3]);
        return updateInfo;
    }
}
