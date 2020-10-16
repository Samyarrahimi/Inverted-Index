package Search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PostingList implements Serializable {
    private List<Integer> docIds = new ArrayList<>();

    PostingList() {
    }

    PostingList(int... ids) {
        Arrays.sort(ids);
        for (int id : ids) {
            docIds.add(id);
        }
    }

    public void insert(int n) {
        List<Integer> list = this.getDocIds();

        int left = 0, right = list.size() - 1;

        //if list contains n or n is invalid
        if (list.contains(n) || n <= 0)
            return;

        //if list is empty or if n is higher than all numbers in the list
        if (list.size() == 0 || n > list.get(right)) {
            list.add(n);
            return;
        } else if (n < list.get(0)) {
            list.add(0, n);
            return;
        }

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (right - left + 1 == 2 && list.get(right) > n && list.get(left) < n) {
                //add n to the right(index number) and shift every item after that
                list.add(right, n);
                return;
            } else if (list.get(mid) > n)
                right = mid;
            else
                left = mid;
        }
    }

    public int size() {
        return docIds.size();
    }

    public void sort() {
        Collections.sort(docIds);
    }

    public List<Integer> getDocIds() {
        return docIds;
    }

    public PostingList and(PostingList other) {
        PostingList result = new PostingList();
        int i = 0, j = 0;
        while (i < size() && j < other.size()) {
            int a = docIds.get(i);
            int b = other.docIds.get(j);
            if (a == b) {
                result.insert(a);
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
        while (i < size() && j < other.size()) {
            int a = docIds.get(i);
            int b = other.docIds.get(j);
            if (a == b) {
                result.insert(a);
                i++;
                j++;
            } else if (a < b) {
                result.insert(a);
                i++;
            } else {
                result.insert(b);
                j++;
            }
        }
        while (i < size()) {
            result.insert(docIds.get(i));
            i++;
        }
        while (j < other.size()) {
            result.insert(other.docIds.get(j));
            j++;
        }
        return result;
    }

    public PostingList not(int lastId) {
        int[] all = new int[lastId + 1];
        for (int i = 0; i < all.length; i++) {
            all[i] = i;
        }
        List<Integer> docIds = getDocIds();
        PostingList result = new PostingList();
        int i = 0, j = 0;
        while (i < all.length && j < docIds.size()) {
            int a = all[i];
            int b = docIds.get(j);
            if (a < b) {
                result.insert(a);
                i++;
            } else if (a == b) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        while (i < all.length) {
            result.insert(i++);
        }
        return result;
    }

    @Override
    public String toString() {
        return "" + docIds;
    }
}
