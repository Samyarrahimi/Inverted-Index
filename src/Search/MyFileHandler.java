package Search;

import java.io.*;
import java.util.HashMap;

public class MyFileHandler {

    public HashMap<String, PostingList> ReadFromFile(String path) {
        HashMap<String, PostingList> table = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            objectInputStream = new ObjectInputStream(fileInputStream);
            table = (HashMap<String, PostingList>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        } catch (IOException e) {
            System.out.println("Exception in reading from file!");
        } catch (ClassNotFoundException e) {
            System.out.println("Exception in reading class not found!");
        } finally {
            try {
                fileInputStream.close();
                objectInputStream.close();
            } catch (IOException e) {
                System.out.println("Exception in closing file objects(Reading)");
            }
        }
        return table;
    }


    public void WriteToFile(InvertedIndex index, String path) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(index.getTable());
            System.out.println("File created!");
        } catch (IOException e) {
            System.out.println("Exception in writing to file!");
        } finally {
            try {
                fileOutputStream.close();
                objectOutputStream.close();
            } catch (IOException e) {
                System.out.println("Exception in closing file objects(Writing)");
            }
        }
    }
}
