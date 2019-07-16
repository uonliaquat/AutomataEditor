package edu.editor;

import java.nio.ByteBuffer;
import java.util.*;

public class BitSet<T> implements Set<T> {

    private long set;
    public static int length = 64;
    private int size;
    private static Map<ENUM, SetMap> map = new HashMap<>();
    private SetMap setMap;
    private ENUM type;
    public BitSet(ENUM e){
        set = 0;
        size = 0;
        type = e;
        checkType(e);
    }

    @Override
    public Boolean insert(T state) {
        int rand;
        if(type == ENUM.SET_STRING || type == ENUM.SET_CHAR || type == ENUM.SET_INTEGER){
            rand = setMap.generateRandomNumber(((BitSet)state).getSet());
        }
        else{
            rand = setMap.generateRandomNumber(state);
        }
        long i = 1;
        if(((i << rand) | set) != set){
            set = set | (i << rand);
            size++;
            return true;
        }
        return false;
    }


    @Override
    public Boolean remove(T state) {
        int rand;
        if(type == ENUM.SET_STRING || type == ENUM.SET_CHAR || type == ENUM.INTEGER){
            rand = setMap.generateRandomNumber(((BitSet)state).getSet());
        }
        else{
            rand = setMap.generateRandomNumber(state);
        }
        long i = 1;
        if((~(i << rand) & set) != 0){
            set = set & i;
            size--;
            return true;
        }
        return false;
    }



    @Override
    public Set union(Set s) {
        if(s.getType().equals(type)) {
            BitSet union_set = new BitSet(type);
            union_set.setSet(set | s.getSet());
            union_set.setSize(Long.bitCount(union_set.getSet()));
            Set set = union_set;
            return set;
        }
        return null;
    }

    @Override
    public Set intersection(Set s) {
        if(s.getType().equals(type)) {
            BitSet intersection_set = new BitSet(type);
            intersection_set.setSet(set & s.getSet());
            intersection_set.setSize(Long.bitCount(intersection_set.getSet()));
            Set set = intersection_set;
            return set;
        }
        return null;
    }

    @Override
    public boolean isEqual(Set s) {
        if(set == s.getSet() && s.getType().equals(type) && s.getSize() == size)
            return true;
        return false;
    }


    @Override
    public Boolean isEmpty() {
        if(set == 0)
            return true;
        return false;
    }

    @Override
    public Boolean contains(T state) {
        int rand = setMap.generateRandomNumber(state);
        long l = 1;
        if(((l << rand) & set) > 0){
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        set = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getLength() {
        return length;
    }


    @Override
    public long getSet() {
        return set;
    }


    private void setSet(long set) {
        this.set = set;
    }


    @Override
    public ENUM getType(){
        return type;
    }


    @Override
    public List<T> getElements() {
        if(this.type.equals(ENUM.STRING)){

        }
        else{

        }

        byte[] bit_subset = ByteBuffer.allocate(4).putLong(set).array();
        List<Integer> list = new ArrayList<>();
        if(this.type.equals(ENUM.STRING)){
            for(int i = 0; i < 8; i++) {
                ArrayList<Integer> indexes = setMap.getValueFromTable(bit_subset[i]);
                for (Integer j : indexes) {
                    list.add(j);
                }
            }

            List<Object> arrayList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                arrayList.add(setMap.getReverseMap().get(list.get(i)));
            }
            return  arrayList;

        }
        else{
            for(int i = 0; i < 8; i++) {
                ArrayList<Integer> indexes = setMap.getValueFromTable(bit_subset[i]);
                for (Integer j : indexes) {
                    list.add(j);
                }
            }
        }

        List<T> list = new ArrayList<>();
        byte[] bit_subset = ByteBuffer.allocate(4).putLong(set).array();
        for(int i = 0; i < 8; i++){
            ArrayList<T> indexes = setMap.getValueFromTable(bit_subset[i]);
            for(T j : indexes){
                list.add(j);
            }
        }
        return list;
    }


    private void checkType(ENUM e){
        if(e == ENUM.INTEGER){
            if(map.containsKey(ENUM.INTEGER)){
                setMap = map.get(ENUM.INTEGER);
            }
            else{
                SetMap m = new SetMap();
                map.put(ENUM.INTEGER, m);
                setMap = m;
            }
              }
        else if(e == ENUM.STRING){
            if(map.containsKey(ENUM.STRING)){
                setMap = map.get(ENUM.STRING);
            }
            else{
                SetMap m = new SetMap();
                map.put(ENUM.STRING, m);
                setMap = m;
            }
        }
        else if(e == ENUM.CHAR){
            if(map.containsKey(ENUM.CHAR)){
                setMap = map.get(ENUM.CHAR);
            }
            else{
                SetMap m = new SetMap();
                map.put(ENUM.CHAR, m);
                setMap = m;
            }
        }
        else if(e == ENUM.SET_INTEGER){
            if(map.containsKey(ENUM.SET_INTEGER)){
                setMap = map.get(ENUM.SET_INTEGER);
            }
            else{
                SetMap m = new SetMap();
                map.put(ENUM.SET_INTEGER, m);
                setMap = m;
            }
        }
        else if(e == ENUM.SET_STRING){
            if(map.containsKey(ENUM.SET_STRING)){
                setMap = map.get(ENUM.SET_STRING);
            }
            else{
                SetMap m = new SetMap();
                map.put(ENUM.SET_STRING, m);
                setMap = m;
            }
        }
        else if(e == ENUM.SET_CHAR){
            if(map.containsKey(ENUM.SET_CHAR)){
                setMap = map.get(ENUM.SET_CHAR);
            }
            else{
                SetMap m = new SetMap();
                map.put(ENUM.SET_CHAR, m);
                setMap = m;
            }
        }
    }

    long bitCount(long i){
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        return i & 0x3f;

    }


}



enum ENUM{
    INTEGER, STRING, CHAR, SET_INTEGER, SET_STRING, SET_CHAR
}

