package Search;

import java.util.concurrent.atomic.AtomicInteger;

public class Document {
    private final String name, body;
    private final int docId;
    private static final AtomicInteger lastId = new AtomicInteger(0);

    public Document(String name, String body) {
        this.name = name;
        this.body = body;
        docId = lastId.incrementAndGet();
    }

    public String getBody() {
        return body;
    }

    public int getDocId() {
        return docId;
    }

    public String getName() {
        return name;
    }

    public static AtomicInteger getLastId() {
        return lastId;
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", docId=" + docId +
                '}';
    }
}
