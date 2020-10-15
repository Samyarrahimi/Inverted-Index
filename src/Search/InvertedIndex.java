package Search;

import java.io.*;
import java.util.*;

public class InvertedIndex {
    private Map<String, PostingList> table = new HashMap<>();

    public void add(Document doc) {
        String[] allTokens = doc.getBody().split("\\s+");
        Set<String> distinctTokens = new HashSet<>();

        for (String token : allTokens)
            distinctTokens.add(token);

        for (String token : distinctTokens) {
            table.putIfAbsent(token, new PostingList());
            table.get(token).insert(doc.getDocId());
        }
        /*
        we don't need to sort the postinglist values because we insert them in the correct order
        for (PostingList list : table.values()) {
            list.sort();
        }*/
    }

    public Map<String, PostingList> getTable() {
        return table;
    }
}
