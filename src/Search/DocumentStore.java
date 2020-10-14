package Search;

import java.util.Collection;
import java.util.HashMap;

public class DocumentStore {
    private HashMap<Integer, Document> docs = new HashMap<>();

    public void add(Document doc) {
        docs.put(doc.getDocId(), doc);
    }

    public Collection<Document> getAll() {
        return docs.values();
    }

    public Document get(int id) {
        return docs.get(id);
    }

    @Override
    public String toString() {
        return "DocumentStore{" +
                "docs=" + docs +
                '}';
    }
}
