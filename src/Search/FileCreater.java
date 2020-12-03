package Search;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileCreater {
    public static void main(String[] args) {

        List<String> texts = Arrays.asList("alice", "beowulf", "frankenstein", "pride", "yellow");
        Document[] documents = InitializeDocuments(texts);
        InvertedIndex index = new InvertedIndex();
        DocumentStore store = new DocumentStore();
        for (int i = 0; i < documents.length; i++) {
            store.add(documents[i]);
            index.add(documents[i]);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter where do you want to save your InvertedIndex : ");
        System.out.println("example -> E:\\invertedIndex.txt");
        String writePath = sc.next();
        MyFileHandler.WriteToFile(index, writePath);// write hashMap to file

    }

    private static Document[] InitializeDocuments(List<String> texts) {
        Document[] documents = new Document[texts.size()];
        int i = 0;
        for (String name : texts) {
            InputStream stream = null;
            String body = null;
            try {
                stream = SearchClass.class.getResource("/txts/" + name + ".txt").openStream();
                body = new String(stream.readAllBytes()).toLowerCase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            documents[i++] = new Document(name, body);
        }
        return documents;
    }
}
