package Search;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SearchClass {
    public static void main(String[] args) {
/*
        List<String> texts = Arrays.asList("alice", "beowulf", "frankenstein", "pride", "yellow");
        Document[] documents = InitializeDocuments(texts);
        InvertedIndex index = new InvertedIndex();
        DocumentStore store = new DocumentStore();
        for (int i = 0; i < documents.length; i++) {
            store.add(documents[i]);
            index.add(documents[i]);
        }

        Scanner sc = new Scanner(System.in);
        MyFileHandler myFileHandler = new MyFileHandler();
        System.out.println("Please Enter where do you want to save your InvertedIndex : ");
        System.out.println("example -> E:\\invertedIndex.txt");
        String writePath = sc.next();
        myFileHandler.WriteToFile(index, writePath);// write hashMap to file
*/
        Scanner sc = new Scanner(System.in);
        MyFileHandler myFileHandler = new MyFileHandler();
        System.out.println("How Many Documents(Doc ids) do you have in your input file?");
        int lastId = sc.nextInt();
        System.out.println("Please Enter from where do you want to read your InvertedIndex : ");
        System.out.println("example -> E:\\invertedIndex.txt");
        String readPath = sc.next();
        HashMap<String, PostingList> table = myFileHandler.ReadFromFile(readPath);// read hashMap from file

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter your query : ");
            String query = in.nextLine();
            if (query.equals("quit"))
                return;

            PostingList result = new PostingList();
            String[] str = query.toLowerCase().split("\\s+");
            Stack<String> stack = new Stack<>();
            for (int i = str.length - 1; i >= 0; i--)
                stack.push(str[i]);

            while (!stack.empty()) {
                String word = stack.pop();
                if (word.equals("and")) {
                    String word1 = stack.pop();
                    if (word1.equals("not")) {
                        String word2 = stack.pop();
                        PostingList list = table.getOrDefault(word2, new PostingList()).not(lastId);
                        System.out.println(word1 + " " + word2 + " " + list.toString());
                        result = result.and(list);
                    } else {
                        PostingList list = table.getOrDefault(word1, new PostingList());
                        System.out.println(word1 + " " + list.toString());
                        result = result.and(list);
                    }
                } else if (word.equals("or")) {
                    String word1 = stack.pop();
                    if (word1.equals("not")) {
                        String word2 = stack.pop();
                        PostingList list = table.getOrDefault(word2, new PostingList()).not(lastId);
                        System.out.println(word1 + " " + word2 + " " + list.toString());
                        result = result.or(list);
                    } else {
                        PostingList list = table.getOrDefault(word1, new PostingList());
                        System.out.println(word1 + " " + list.toString());
                        result = result.or(list);
                    }
                } else if (word.equals("not")) {
                    String word1 = stack.pop();
                    List<Integer> wordIdsNot = table.getOrDefault(word1, new PostingList()).not(lastId).getDocIds();
                    System.out.println(word + " " + word1 + " " + wordIdsNot.toString());
                    for (int i = 0; i < wordIdsNot.size(); i++) {
                        result.insert(wordIdsNot.get(i));
                    }
                } else {
                    List<Integer> wordIds = table.getOrDefault(word, new PostingList()).getDocIds();
                    System.out.println(word + " " + wordIds.toString());
                    for (int i = 0; i < wordIds.size(); i++) {
                        result.insert(wordIds.get(i));
                    }
                }
            }
            System.out.println("Result is : ");
            System.out.println(result.toString());
            System.out.println();
        }
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
