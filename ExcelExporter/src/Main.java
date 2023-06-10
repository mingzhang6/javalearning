import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.ExportUtil;
import javafx.scene.shape.Path;
import model.customer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Hello world!");

        String str = readString(".\\customer.json");
        Gson gson = new GsonBuilder().setLenient().create();

        List<customer> customers = gson.fromJson(str, new TypeToken<List<customer>>() {
        }.getType());

        (new ExportUtil<customer>()).exportDate(customer.class, customers, "Customer.xlsx");
    }

    /**
     * 从文件中读取字符出串
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readString(String fileName) throws IOException {

//        FileInputStream fileInputStream = new FileInputStream(".\\customer.json");
//        // ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//
//        StringBuilder stringBuilder = new StringBuilder();
//        byte[] buffer = new byte[1];
//
//        while(fileInputStream.read(buffer) != -1){
//            String str = new String(buffer);
//            stringBuilder.append(str);
//            buffer = new byte[1];
//        }
//        fileInputStream.close();
//        return stringBuilder.toString();
        byte[] bytes = Files.readAllBytes(Paths.get("customer.json"));
        String str = new String(bytes);
        return str;
    }
}

