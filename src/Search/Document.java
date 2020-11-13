package Search;

import java.util.concurrent.atomic.AtomicInteger;

public class Document {
    private final String name, body;
    private final int docId;
    private static AtomicInteger lastId = new AtomicInteger(-1);

    public Document(String name, String body) {
        this.name = name;
        this.body = body;
        docId = lastId.incrementAndGet();
    }

    public String[] tokenize() {

        String body = this.getBody().toLowerCase();

        // replace !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ with space
        body = body.replace("!", " ");
        body = body.replace("\"", " ");
        body = body.replace("#", " ");
        body = body.replace("$", " ");
        body = body.replace("%", " ");
        body = body.replace("&", " ");
        body = body.replace("'", " ");
        body = body.replace("(", " ");
        body = body.replace(")", " ");
        body = body.replace("*", " ");
        body = body.replace("+", " ");
        body = body.replace(",", " ");
        body = body.replace("-", " ");
        body = body.replace(".", " ");
        body = body.replace("/", " ");
        body = body.replace(":", " ");
        body = body.replace(";", " ");
        body = body.replace("<", " ");
        body = body.replace("=", " ");
        body = body.replace(">", " ");
        body = body.replace("?", " ");
        body = body.replace("@", " ");
        body = body.replace("[", " ");
        body = body.replace("]", " ");
        body = body.replace("\\", " ");
        body = body.replace("^", " ");
        body = body.replace("_", " ");
        body = body.replace("{", " ");
        body = body.replace("}", " ");
        body = body.replace("|", " ");
        body = body.replace("~", " ");
        body = body.replace("،", " ");

        return body.split("\\s+");
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", docId=" + docId +
                '}';
    }

    public int getDocId() {
        return docId;
    }

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public static AtomicInteger getLastId() {
        return lastId;
    }
}
