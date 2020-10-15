package Search;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SearchClass {
    public static void main(String[] args) {
        List<String> texts = Arrays.asList("alice", "beowulf", "frankenstein", "pride", "yellow");
        InvertedIndex index = new InvertedIndex();
        DocumentStore store = new DocumentStore();

        for (String name : texts) {
            InputStream stream = null;
            String body = null;
            try {
                stream = SearchClass.class.getResource("/txts/" + name + ".txt").openStream();
                body = new String(stream.readAllBytes()).toLowerCase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Document document = new Document(name, body);
            store.add(document);
            index.add(document);
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter your query : ");
            String line = sc.nextLine().toLowerCase();
            if (line.equals("quit")) {
                return;
            }

            PostingList list = index.get(line);
            if (list != null) {
                for (Integer docId : list.getDocIds()) {
                    System.out.println(store.get(docId).toString());
                }
            } else {
                System.out.println("No file contains : " + line);
            }
            System.out.println();
        }

    }
}
