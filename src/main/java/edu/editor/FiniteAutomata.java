package edu.editor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiniteAutomata<T>{
    private Set<T> states, finalStates;
    private Set<Integer> alphabets;
    private Map<T, List<T>> transition;
    private T initialState;

    public FiniteAutomata(){

    }
    public FiniteAutomata(int noOfStates, int noOfSymbols){
        states = new BitSet<>(BitSet.ENUM.INTEGER);
        alphabets = new BitSet<>(BitSet.ENUM.INTEGER);
        initialState = null;
        transition = new HashMap<>();
        finalStates = new BitSet<>(BitSet.ENUM.INTEGER);

    }

    public FiniteAutomata(Set<T> states, Set<String> alphabets, T initialState, Map<T, List<T>> transitions, Set<T> finalStates){
        this.states = states;
        this.alphabets = new BitSet<>(BitSet.ENUM.INTEGER);
        this.initialState = initialState;
        this.transition = transitions;
        this.finalStates = finalStates;
    }

    public FiniteAutomata<T> setInitialState(T initialState){
        this.initialState = initialState;
        return this;
    }

    public T getInitialState() {
        return initialState;
    }

    public FiniteAutomata<T> setFinalStates(Set<T> finalStates){
        this.finalStates = finalStates;
        return this;
    }

    public edu.editor.Set<T> getFinalStates() {
        return finalStates;
    }

    public FiniteAutomata<T> setAlphabets(Set<Integer> alphabets) {
        this.alphabets = alphabets;
        return this;
    }

    public Set<Integer> getAlphabets() {
        return alphabets;
    }

    public FiniteAutomata<T> setStates(Set<T> states) {
        this.states = states;
        return this;

    }

    public edu.editor.Set<T> getStates() {
        return states;
    }

    public FiniteAutomata<T> setTransition(Map<T, List<T>> transition) {
        this.transition = transition;
        return this;
    }

    public Map<T, List<T>> getTransition() {
        return transition;
    }

}
