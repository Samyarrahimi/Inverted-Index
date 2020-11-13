package Search;

import java.util.*;

public class InvertedIndex {

    private Map<String, PostingList> table = new HashMap<>();

    public void add(String[] body, int Docid) {
        Set<String> distinctTokens = new HashSet<>();

        for (String token : body)
            distinctTokens.add(token);

        for (String token : distinctTokens) {
            table.putIfAbsent(token, new PostingList());
            table.get(token).add(Docid);
        }
    }

    public Map<String, PostingList> getTable() {
        return table;
    }
}
