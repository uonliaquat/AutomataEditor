package edu.editor;

import jdk.jfr.Unsigned;

import java.lang.reflect.Array;
import java.util.*;

public class SetMap {

    private  Map<Object, Integer> map = new HashMap<>();
    private  Map<Integer, Object> reverseMap = new HashMap<>();
    private ArrayList<ArrayList<Integer>> table = new ArrayList<>();
    private int[] randomNos = new int[64];

    SetMap(){
        GenerateTable();
    }
    public  int generateRandomNumber(Object key){
        if(map.containsKey(key)){
            return map.get(key);
        }
        Random rand = new Random();
        int random;
        do{
            random = rand.nextInt(BitSet.length);
        }
        while(map.containsValue(random));
        map.put(key, random);
        reverseMap.put(random, key);
        return random;
    }


    public Map<Object, Integer> getMap(){
        return map;
   }

    public Map<Integer, Object> getReverseMap(){
        return reverseMap;
    }


    public ArrayList<Integer> getValueFromTable(byte b){
        return table.get(b);
    }

    private void GenerateTable(){
        int temp = 1;
        int val= 1;
        ArrayList<Integer> arrayList;
        for(int j = 0; j < 255; j++) {
            arrayList = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                int t = temp & val;
                if (t != 0) {
                    arrayList.add(i);
                }
                val = val << 1;
            }
            temp++;
            val = 1;
            table.add(arrayList);
        }
    }

}
