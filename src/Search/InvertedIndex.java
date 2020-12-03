package Search;

import java.util.*;

public class InvertedIndex {

    private Map<String, PostingList> table = new HashMap<>();

    public void add(Document doc) {
        int id = doc.getDocId();
        String[] tokens = doc.tokenize();
        doc.normalize(tokens);

        //Set<String> distinctTokens = new HashSet<>();
        //for (String token : tokens)
        //  distinctTokens.add(token);

        System.out.println(id);
        int i = 0;
        for (String token : tokens) {
            table.putIfAbsent(token, new PostingList());
            table.get(token).addId(id);
            table.get(token).addPosition(id, i++);
            //table.get(token).sort();
        }
/*
        int i = 0;
        for (String token : tokens) {
            table.putIfAbsent(token, new PostingList());
            table.get(token).addPositionById(id, i);
        }


        int i = 0;
        for (String token : tokens) {
            PostingList ret = table.get(token);
            if(ret != null){
                table.get(token).addPositionById(id, i);
            }else{
                List<Integer> poss = new ArrayList<>();
                poss.add(i);
                PostingList postingList = new PostingList();
                postingList.addPair(new Pair(id, poss));
                table.put(token, postingList);
            }
            i++;
        }*/
    }

    /*
        public void add(String[] body, int Docid) {
            Set<String> distinctTokens = new HashSet<>();

            for (String token : body)
                distinctTokens.add(token);

            for (String token : distinctTokens) {
                table.putIfAbsent(token, new PostingList());
                table.get(token).add(Docid);
            }
        }
    */
    public Map<String, PostingList> getTable() {
        return table;
    }
}
