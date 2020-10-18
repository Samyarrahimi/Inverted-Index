package Search;

import java.util.*;

public class SearchClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyFileHandler myFileHandler = new MyFileHandler();
        System.out.println("How Many Documents(Doc ids) do you have in your input file?");
        int lastId = sc.nextInt();
        System.out.println("Please Enter from where do you want to read your InvertedIndex : ");
        System.out.println("example -> E:\\invertedIndex.txt");
        String readPath = sc.next();
        HashMap<String, PostingList> table = myFileHandler.ReadFromFile(readPath);// read hashMap from file

        Scanner in = new Scanner(System.in);
        System.out.println("Enter quit to finish the program.");
        while (true) {
            System.out.println("Enter your query : ");
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
}
