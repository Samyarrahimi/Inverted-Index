package Search;

import java.util.*;

public class SearchClass {
    public static void main(String[] args) {
        /*
        Scanner sc = new Scanner(System.in);
        System.out.println("How Many Documents(Doc ids) do you have in your input file?");
        int lastId = sc.nextInt();
        System.out.println("Please Enter from where do you want to read your InvertedIndex : ");
        System.out.println("example -> E:\\invertedIndex.txt");
        String readPath = sc.next();
        HashMap<String, PostingList> table = MyFileHandler.ReadFromFile(readPath);// read hashMap from file

        Scanner in = new Scanner(System.in);
        System.out.println("Enter quit when you want to end the program.");
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
        */
        System.out.println("Enter your XML file path:");
        Scanner in = new Scanner(System.in);
        //String path = in.next();
        long start = System.currentTimeMillis();
        ArrayList<Document> documents = MyFileHandler.ReadFromXML("C:\\Users\\Admin\\Desktop\\simple.xml");
        System.out.println("read from file :\t" + (System.currentTimeMillis() - start) + " seconds");

        String[][] tokens = new String[documents.size()][];
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < tokens.length; i++)
            tokens[i] = documents.get(i).tokenize();
        long finish1 = System.currentTimeMillis();
        System.out.println("tokenization :\t" + (finish1 - start1) + " seconds");


        long start2 = System.currentTimeMillis();
        normalize(tokens);
        long finish2 = System.currentTimeMillis();
        System.out.println("normalization :\t" + (finish2 - start2) + " seconds");


        InvertedIndex index = new InvertedIndex();
        DocumentStore documentStore = new DocumentStore();
        long start3 = System.currentTimeMillis();
        for (int i = 0; i < tokens.length; i++) {
            index.add(tokens[i], i);
            documentStore.add(documents.get(i));
        }
        long finish3 = System.currentTimeMillis();
        System.out.println("indexing :\t" + (finish3 - start3) + " seconds");


        System.out.println();
        System.out.println("table has this amount of tokens : ");
        System.out.println(index.getTable().size());
        System.out.println("total number of documents : ");
        System.out.println(Document.getLastId());
        System.out.println();

        HashMap<String, PostingList> table = (HashMap<String, PostingList>) index.getTable();

        System.out.println("Enter quit when you want to end the program.");

        int doNot = 0;
        int doAnd = 0;//0 : and  1 : or
        int totalDocNum = Document.getLastId().get();
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
                    doAnd = 1;
                } else if (word.equals("or")) {
                    doAnd = 0;
                } else if (word.equals("not")) {
                    doNot = 1 - doNot;
                } else {
                    PostingList list = table.getOrDefault(word, new PostingList());
                    if (doNot == 1) {
                        list = list.not(totalDocNum);
                        doNot = 0;
                    }
                    if (doAnd == 1) {
                        result = result.and(list);
                        doAnd = 0;
                    } else if (doAnd == 0) {
                        result = result.or(list);
                    }
                }
            }
            System.out.println("Result is : ");
            List<Integer> docIds = result.getDocIds();
            int limit = 500 < docIds.size() ? 500 : docIds.size();
            for (int i = 0; i < limit; i++) {
                System.out.print(docIds.get(i) + "\t");
                if (i % 25 == 0 && i != 0)
                    System.out.println();
            }
            System.out.println();
            System.out.println();

        }

        /*
        while (true) {
            System.out.println("Enter your query : ");
            String query = in.nextLine();
            if (query.equals("quit"))
                return;

            int totalDocNum = Document.getLastId().get();
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
                        PostingList list = table.getOrDefault(word2, new PostingList()).not(totalDocNum);
                        //System.out.println(word1 + " " + word2 + " " + list.toString());
                        result = result.and(list);
                    } else {
                        PostingList list = table.getOrDefault(word1, new PostingList());
                        //System.out.println(word1 + " " + list.toString());
                        result = result.and(list);
                    }
                } else if (word.equals("or")) {
                    String word1 = stack.pop();
                    if (word1.equals("not")) {
                        String word2 = stack.pop();
                        PostingList list = table.getOrDefault(word2, new PostingList()).not(totalDocNum);
                        //System.out.println(word1 + " " + word2 + " " + list.toString());
                        result = result.or(list);
                    } else {
                        PostingList list = table.getOrDefault(word1, new PostingList());
                        //System.out.println(word1 + " " + list.toString());
                        result = result.or(list);
                    }
                } else if (word.equals("not")) {
                    String word1 = stack.pop();
                    if (word1.equals("not")) {
                        String word2 = stack.pop();
                        PostingList list = table.getOrDefault(word2, new PostingList());
                        //System.out.println(word + " " + word1 + " " + word2 + " " + list.toString());
                        if(result.getDocIds().size()==0)
                            result = result.or(list);
                        else
                            result = result.and(list);
                    } else {
                        PostingList list = table.getOrDefault(word1, new PostingList()).not(totalDocNum);
                        //System.out.println(word + " " + word1 + list.toString());
                        if(result.getDocIds().size()==0)
                            result = result.or(list);
                        else
                            result = result.and(list);
                    }
                } else {
                    List<Integer> wordIds = table.getOrDefault(word, new PostingList()).getDocIds();
                    //System.out.println(word + " " + wordIds.toString());
                    for (int i = 0; i < wordIds.size(); i++) {
                        result.add(wordIds.get(i));
                    }
                }
            }

            System.out.println("Result is : ");
            List<Integer> docIds = result.getDocIds();
            int limit = 1000 < docIds.size() ? 1000 : docIds.size();
            for (int i = 0; i < limit; i++) {
                System.out.print(docIds.get(i)+"\t");
                if (i % 25 == 0 && i != 0)
                    System.out.println();
            }
            System.out.println();
            System.out.println();
        }*/

    }

    private static void print(String[][] tokens, int start, int finish) {
        for (int i = start; i < finish; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                System.out.println(tokens[i][j]);
            }
            System.out.println();
        }
    }

    private static void normalize(String[][] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                tokens[i][j] = tokens[i][j].replaceAll("[ىﻱئ]", "ی");
                tokens[i][j] = tokens[i][j].replaceAll("[َُِ]", "");// a e o
                tokens[i][j] = tokens[i][j].replace("ة", "ه");
                tokens[i][j] = tokens[i][j].replace("ك", "ک");
                tokens[i][j] = tokens[i][j].replace("ؤ", "و");
                tokens[i][j] = tokens[i][j].replace("ٔ", "");// hamze
            }
        }
    }
}
