package edu.editor;

import java.util.List;

public interface Set<T> {


    boolean insert(T state);
    boolean remove(T state);
    Set union(Set s);
    Set intersection(Set s);
    boolean isEqual(Set s);
    boolean isEmpty();
    boolean contains(T state);
    void clear();
    int getSize();
    long getSet();
    BitSet.ENUM getType();
//    ENUM getType();
    List<T> getElements();



}
