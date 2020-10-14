package Search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InvertedIndex {
    private Map<String, PostingList> table = new HashMap<>();

    public void add(Document doc) {
        String[] tokens = doc.getBody().split("\\s+");
        Set<String> distinctTokens = new HashSet<>();
        for (String token : tokens) {
            distinctTokens.add(token);
        }
        for (String token : distinctTokens) {
            table.putIfAbsent(token, new PostingList());
            table.get(token).add(doc.getDocId());
        }

        for (PostingList list : table.values()) {
            list.sort();
        }
    }

    public PostingList get(String token) {
        return table.get(token);
    }
}
