package Search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PostingList {
    private List<Integer> docIds = new ArrayList<>();

    PostingList() {
    }

    PostingList(int... ids) {
        Arrays.sort(ids);
        for (int id : ids) {
            docIds.add(id);
        }
    }

    public List<Integer> getDocIds() {
        return docIds;
    }

    public void add(int id) {
        docIds.add(id);
    }

    public void sort() {
        Collections.sort(docIds);
    }

    public int size() {
        return docIds.size();
    }

    @Override
    public String toString() {
        return "PostingList{" +
                "docIds=" + docIds +
                '}';
    }

    public PostingList and(PostingList other) {
        PostingList result = new PostingList();
        int i = 0, j = 0;
        while (i < size() && j < other.size()) {
            int a = docIds.get(i);
            int b = other.docIds.get(j);
            if (a == b) {
                result.add(a);
                i++;
                j++;
            } else if (a < b) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }

    public PostingList or(PostingList other) {
        PostingList result = new PostingList();
        int i = 0, j = 0;
        while (i < size() && j < size()) {
            int a = docIds.get(i);
            int b = other.docIds.get(j);
            if (a == b) {
                result.add(a);
                i++;
                j++;
            } else if (a < b) {
                result.add(a);
                i++;
            } else {
                result.add(b);
                j++;
            }
        }
        while (i < size()) {
            result.add(docIds.get(i));
            i++;
        }
        while (j < other.size()) {
            result.add(other.docIds.get(j));
            j++;
        }
        return result;
    }

    public PostingList not() {
        int[] all = new int[Document.getLastId().incrementAndGet()];
        for (int i = 0; i < all.length; i++) {
            all[i] = i;
        }
        List<Integer> docIds = getDocIds();
        PostingList result = new PostingList();
        int i = 1, j = 0;
        while (i < all.length && j < docIds.size()) {
            int a = all[i];
            int b = docIds.get(j);
            if (a < b) {
                result.add(a);
                i++;
            } else if (a == b) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        while (i < all.length) {
            result.add(i++);
        }
        return result;
    }
}
