package edu.editor;

import java.util.Iterator;
import java.util.List;

public interface Set<T> {


    Boolean insert(T state);
    Boolean remove(T state);
    Set union(Set s);
    Set intersection(Set s);
    boolean isEqual(Set s);
    Boolean isEmpty();
    Boolean contains(T state);
    void clear();
    int getSize();
    void setSize(int size);
    int getLength();
    long getSet();
    ENUM getType();
    List<Object> getElements();



}
