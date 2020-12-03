package Search;

import java.io.Serializable;
import java.util.*;

public class PostingList implements Serializable {

    private LinkedHashMap<Integer, ArrayList<Integer>> plist = new LinkedHashMap<>();

    PostingList() {
    }

    /*
    public int idsSize() {
        return getIds().size();
    }

    public List<List<Integer>> getPositions() {
        return positions;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public List<Integer> getPositionsById(int id) {
        if (getPositions().size() <= id)
            getPositions().add(new ArrayList<>());

        List<Integer> positions = getPositions().get(getIds().indexOf(id));
        if (positions == null)
            positions = new ArrayList<>();
        return positions;
    }

    public void addPosition(int id, int pos) {
        getPositionsById(id).add(pos);
    }

    public void addId(int id) {
        if (getIds().indexOf(id) == -1)
            getIds().add(id);
    }*/
    /*
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

    public void add(int id) {
        docIds.add(id);
    }*/
    /*
    public int size() {
        return plist.size();
    }

    public void addPair(Pair p) {
        this.getPlist().add(p);
    }

    // adds position to the related ids position list
    // if there is not any positions for related ids list
    // then adds the position to the list
    public void addPositionById(int id, int pos) {
        List<Integer> positions = this.getPositions(id);
        if (positions != null)
            positions.add(pos);
        else {
            List<Integer> poss = new ArrayList<>();
            poss.add(pos);
            this.addPair(new Pair(id, poss));
        }
    }

    public List<Pair> getPlist() {
        return plist;
    }

    public List<Integer> getDocIds() {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < plist.size(); i++) {
            ret.add(plist.get(i).getId());
        }
        return ret;
    }

    public List<Integer> getPositions(int id) {
        List<Pair> lis = getPlist();
        for (int i = 0; i < lis.size(); i++) {
            Pair pair = lis.get(i);
            if (pair.getId() == id)
                return pair.getPositions();
        }
        return null;
    }

    public int getIdByIndex(int index) {
        return this.getPlist().get(index).getId();
    }*/
/*
    public void sort() {
        //sort plist by id
        HashMap<Integer,ArrayList<Integer>> res = new HashMap<>();
        TreeMap<Integer, ArrayList<Integer>> sorted = new TreeMap<>(this.getPlist());
        Set<Integer> ids = sorted.keySet();
        for(int i = 0; i< ids.size(); i++){
            res.putIfAbsent()
        }

//        Collections.sort(getPlist(), Comparator.comparing(p));
    }
*/
    public void addId(int id) {
        plist.putIfAbsent(id, new ArrayList<>());
    }


    public void addPosition(int id, int pos) {
        plist.get(id).add(pos);
    }

    public HashMap<Integer, ArrayList<Integer>> getPlist() {
        return plist;
    }
/*
    public PostingList near(PostingList other, int k) {
        PostingList result = new PostingList();

        Set<Integer> ids1 = getPlist().keySet();
        Set<Integer> otherIds1 = other.getPlist().keySet();

        Collection<ArrayList<Integer>> positions = getPlist().values();
        Collection<ArrayList<Integer>> otherpostions = other.getPlist().values();

        Integer[] ids = (Integer[]) ids1.toArray();
        Integer[] otherids = (Integer[]) otherIds1.toArray();

        ArrayList<Integer>[] poss = (ArrayList<Integer>[]) positions.toArray();
        ArrayList<Integer>[] otherposs = (ArrayList<Integer>[]) otherpostions.toArray();

        int i = 0, j = 0;
        while (i < ids.length && j < otherids.length) {
            if (ids[i] == otherids[j]) {
                ArrayList<Integer> poss1 = poss[i];
                ArrayList<Integer> otherposs1 = otherposs[j];
                int ii = 0, jj = 0;
                while (ii < poss1.size()){
                    while (jj<otherposs1.size()){
                        if(Math.abs(poss1.get(ii)-otherposs1.get(jj)) <= k){
                            result.addId(ids[i]);
                            result.addPosition(ids[i],poss1.get(ii));
                            result.addPosition(ids[i],otherposs1.get(jj));
                        }else
                    }
                }
            } else if (ids[i] < otherids[j]) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }
*/

    public PostingList and(PostingList other) {
        PostingList result = new PostingList();
        Set<Integer> ids = plist.keySet();
        Set<Integer> otherIds = other.plist.keySet();
        Iterator<Integer> it = ids.iterator();
        Iterator<Integer> otherIt = otherIds.iterator();
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();

        while (otherIt.hasNext())
            list1.add(otherIt.next());
        while (it.hasNext())
            list.add(it.next());

        int i = 0, j = 0;
        while (i < list.size() && j < list1.size()) {
            int a = list.get(i);
            int b = list1.get(j);
            if (a == b) {
                result.addId(a);
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
        Set<Integer> ids = getPlist().keySet();
        Set<Integer> otherIds = other.getPlist().keySet();
        Iterator<Integer> it = ids.iterator();
        Iterator<Integer> otherIt = otherIds.iterator();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        while (otherIt.hasNext())
            list2.add(otherIt.next());
        while (it.hasNext())
            list1.add(it.next());

        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            int a = list1.get(i);
            int b = list2.get(j);
            if (a == b) {
                result.addId(a);
                i++;
                j++;
            } else if (a < b) {
                result.addId(a);
                i++;
            } else {
                result.addId(b);
                j++;
            }
        }
        while (i < list1.size()) {
            result.addId(list1.get(i));
            i++;
        }
        while (j < list2.size()) {
            result.addId(list2.get(j));
            j++;
        }
        return result;
    }

    public PostingList not(int lastId) {
        Set<Integer> ids = getPlist().keySet();
        Iterator<Integer> it = ids.iterator();
        PostingList result = new PostingList();
        List<Integer> list = new ArrayList<>();
        while (it.hasNext())
            list.add(it.next());

        int i = 0, j = 0;
        while (i < lastId + 1 && j < list.size()) {
            int a = i;
            int b = list.get(j);
            if (a < b) {
                result.addId(a);
                i++;
            } else if (a == b) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        return result;
    }
}
