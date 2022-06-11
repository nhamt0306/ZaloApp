package hcmute.edu.vn.nhom01.zaloapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// class MemoryData dùng để đọc và ghi dữ liệu vào các file txt trong thiết bị
public class MemoryData {

    // Lưu dữ liệu số điện thoại người dùng vào file datata.txt
    public static void saveData(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("datata.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lưu dữ liệu id của cuộc hội thoại vào file có dạng "chatid".txt
    public static void saveLastMsgTS(String data, String chatId, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(chatId + ".txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lưu dữ liệu tên người dùng vào file nameee.txt
    public static void saveName(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("nameee.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lấy dữ liệu số điện thoại người dùng từ file datata.txt
    public static String getData(Context context) {
        String data = "";
        try {
            FileInputStream fileInputStream = context.openFileInput("datata.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Lấy dữ liệu tên người dùng từ file nameee.txt
    public static String getName(Context context) {
        String data = "";
        try {
            FileInputStream fileInputStream = context.openFileInput("nameee.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Lấy dữ liệu id của cuộc hội thoại từ file có dạng "chatid".txt
    public static String getLastMsgTs(Context context, String chatId) {
        String data = "0";
        try {
            FileInputStream fileInputStream = context.openFileInput(chatId + ".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
