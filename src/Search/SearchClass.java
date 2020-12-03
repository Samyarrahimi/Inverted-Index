package Search;

import java.util.*;

public class SearchClass {
    public static void main(String[] args) {
        System.out.println("Enter your XML file path:");
        Scanner in = new Scanner(System.in);
        //String path = in.next();
        long start = System.currentTimeMillis();
        ArrayList<Document> documents = MyFileHandler.ReadFromXML("C:\\Users\\Admin\\Desktop\\simple.xml");
        System.out.println("read from file :\t" + (System.currentTimeMillis() - start) + " seconds");
        System.out.println("indexing started!");

        InvertedIndex index = new InvertedIndex();
        DocumentStore documentStore = new DocumentStore();
        start = System.currentTimeMillis();
        for (int i = 0; i < documents.size(); i++) {
            index.add(documents.get(i));
            documentStore.add(documents.get(i));
        }
        System.out.println("indexing :\t" + (System.currentTimeMillis() - start) + " seconds");
        System.out.println();
        System.out.println("table has this amount of tokens : ");
        System.out.println(index.getTable().size());
        System.out.println("total number of documents : ");
        System.out.println(Document.getLastId());
        System.out.println();


        HashMap<String, PostingList> table = (HashMap<String, PostingList>) index.getTable();

        System.out.println("Enter quit when you want to end the program.");

        int totalDocNum = Document.getLastId().get();
        while (true) {
            int doNot = 0;
            int doAnd = 1;//1 : and  0 : or
            int firstTime = 1;
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
                    if (doAnd == 1 && firstTime == 0) {
                        result = result.and(list);
                        doAnd = 1;
                    } else if (doAnd == 0 || firstTime == 1) {
                        result = result.or(list);
                        doAnd = 1;
                        firstTime = 0;
                    }
                }
            }

            System.out.println("Result is : ");
            Set<Integer> docIds = result.getPlist().keySet();
            Iterator<Integer> it = docIds.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (i % 15 == 0) {
                    System.out.println();
                }
                if (i == 100)
                    break;
                System.out.print(it.next() + "\t");
                i++;
            }

            System.out.println();
            System.out.println();
        }
    }

    private static void print(String[][] tokens, int start, int finish) {
        for (int i = start; i < finish; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                System.out.println(tokens[i][j]);
            }
            System.out.println();
        }
    }
}
