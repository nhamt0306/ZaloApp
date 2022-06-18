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
            // Mở file datata.txt
            FileOutputStream fileOutputStream = context.openFileOutput("datata.txt", Context.MODE_PRIVATE);
            // Ghi đè thông tin data lên file
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lưu dữ liệu id của cuộc hội thoại vào file có dạng chatid.txt
    public static void saveLastMsgTS(String data, String chatId, Context context) {
        try {
            // Mở file chatId + .txt
            FileOutputStream fileOutputStream = context.openFileOutput(chatId + ".txt", Context.MODE_PRIVATE);
            // Ghi đè thông tin data lên file
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lưu dữ liệu tên người dùng vào file nameee.txt
    public static void saveName(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("nameee.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lấy dữ liệu số điện thoại người dùng từ file datata.txt
    public static String getData(Context context) {
        String data = "";
        try {
            // Mở file datata
            FileInputStream fileInputStream = context.openFileInput("datata.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            // Biến lưu thông tin trong file
            String line;
            // Đọc thông tin và lưu trên stringBuilder
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            // Lưu thông tin đọc đc trên data, trả về kết quả
            data = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Lấy dữ liệu tên người dùng từ file nameee.txt
    public static String getName(Context context) {
        String data = "";
        try {
            // Mở file nameee
            FileInputStream fileInputStream = context.openFileInput("nameee.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            // Biến lưu thông tin trong file
            String line;
            // Đọc thông tin và lưu trên stringBuilder
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            // Lưu thông tin đọc đc trên data, trả về kết quả
            data = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Lấy dữ liệu mốc thời gian của cuộc hội thoại từ file có dạng "chatid".txt
    public static String getLastMsgTs(Context context, String chatId) {
        String data = "0";
        try {
            // Mở file có tên chatID + .txt
            FileInputStream fileInputStream = context.openFileInput(chatId + ".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            // Biến lưu thông tin trong file
            String line;
            // Đọc thông tin và lưu trên stringBuilder
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            // Lưu thông tin đọc đc trên data, trả về kết quả
            data = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
