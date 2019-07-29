package edu.editor;

import java.nio.ByteBuffer;
import java.util.*;

public class BitSet<T> implements Set<T> {

    private long set;
    private final int length = 64;
    private int size;
    private Map<Long, Integer> map;
    private Map<Integer, T> reverseMap;
    private static Map<ENUM, Map> setTypeMap = new HashMap<>();
    private static Map<ENUM, Map> setTypeReverseMap = new HashMap<>();
    private ENUM type;

    private static int[] setPositions = null;

    BitSet(ENUM e) {
        set = 0;
        size = 0;
        if(setPositions == null) {
            setPositions = new int[length];
            for(int i = 0; i < length; i++){
                setPositions[i] = i;
            }
        }

        setType(e);

    }

    @Override
    public boolean insert(T state) {
        long i = 1;
        int sp = getStatePos(state);
        if(((i << sp) | set) != set){
            set = ((i << sp) | set);
            size++;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(T state) {
        long i = 1;
        long key;
        if(state.equals(ENUM.SET_INTEGER)){
            BitSet bitSet = (BitSet) state;
            key = bitSet.getSet();
        }
        else{
            int val = (int) state;
            key = val;
        }
        if(map.containsKey(key)){
            set = (i << map.get(key)) & set;
            size--;

            //put back the position of set into setPositions
            int t = map.get(key);
            setPositions[length - size - 1] = t;
            map.remove(key);
            reverseMap.remove(t);
            return true;
        }
        return false;
    }

    @Override
    public Set union(Set s) {
        if(type == s.getType()) {
            BitSet union_set = new BitSet(type);
            union_set.setSet(set | s.getSet());
            union_set.setSize(Long.bitCount(union_set.getSet()));
            union_set.setType(type);
            Set set = union_set;
            return set;
        }
        return null;
    }

    @Override
    public Set intersection(Set s) {
        if(type == s.getType()) {
            BitSet intersection_set = new BitSet(type);
            intersection_set.setSet(set & s.getSet());
            intersection_set.setSize(Long.bitCount(intersection_set.getSet()));
            intersection_set.setType(s.getType());
            Set set = intersection_set;
            return set;
        }
        return null;
    }

    @Override
    public boolean isEqual(Set s) {
        if(set == s.getSet()){
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if(set == 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(T state) {
        long key;
        if(state.equals(ENUM.SET_INTEGER)){
            BitSet bitSet = (BitSet) state;
            key = bitSet.getSet();
        }
        else{
            int val = (int) state;
            key = val;
        }
        if(map.containsKey(key)){
            int sp = map.get(key);
            long l = 1;
            if(((l << sp) & set) != 0){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void clear() {
        set = 0;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }


    public int getLength() {
        return length;
    }

    @Override
    public long getSet() {
        return set;
    }

    @Override
    public ENUM getType() {
        return type;
    }

    @Override
    public List<T> getElements() {
        List<T> elements = new ArrayList<>();
        for(long i = 0; i < length; i++){
            long j = 1;
            if(((j << i) & set) != 0) {
                elements.add(reverseMap.get((int)i));
            }
        }
        return elements;
    }

    private void setSet(long set) {
        this.set = set;
    }


    private void setSize(int size) {
        this.size = size;
    }

    private int getStatePos(T state) {
        long key;
        if(type.equals(ENUM.SET_INTEGER)){
            BitSet bitSet = (BitSet) state;
            key = bitSet.getSet();
        }
        else {
            int val  = (int) state;
            key = val;
        }

        if (!map.containsKey(key)) {
            Random random = new Random();
            int setPosition = random.nextInt(length - size);

            //replace last element of setPositions with setPosition
            int ret = setPositions[setPosition];
            setPositions[setPosition] = setPositions[length - size - 1];
            map.put(key, ret);
            reverseMap.put(ret, state);
            return ret;
        }
        return map.get(key);
    }

    private void setType(BitSet.ENUM e){
        if(e == BitSet.ENUM.INTEGER) {
            if (setTypeMap.containsKey(BitSet.ENUM.INTEGER)) {
                map = setTypeMap.get(BitSet.ENUM.INTEGER);
                reverseMap = setTypeReverseMap.get(BitSet.ENUM.INTEGER);
            } else {
                Map<Long, Integer> m = new HashMap<>();
                Map<Integer, T> r = new HashMap<>();
                setTypeMap.put(BitSet.ENUM.INTEGER, m);
                setTypeReverseMap.put(BitSet.ENUM.INTEGER, r);
                map = m;
                reverseMap = r;
            }
            type = ENUM.INTEGER;
        }
        else if(e == BitSet.ENUM.SET_INTEGER){
            if(setTypeMap.containsKey(BitSet.ENUM.SET_INTEGER)){
                map = setTypeMap.get(BitSet.ENUM.SET_INTEGER);
                reverseMap = setTypeReverseMap.get(BitSet.ENUM.SET_INTEGER);
            }
            else{
                Map<Long, Integer> m = new HashMap<>();
                Map<Integer, T> r = new HashMap<>();
                setTypeMap.put(BitSet.ENUM.SET_INTEGER, m);
                setTypeReverseMap.put(BitSet.ENUM.SET_INTEGER, r);
                map = m;
                reverseMap = r;
            }
            type = ENUM.SET_INTEGER;
        }
    }

    enum ENUM{
        INTEGER, SET_INTEGER
    }
}
