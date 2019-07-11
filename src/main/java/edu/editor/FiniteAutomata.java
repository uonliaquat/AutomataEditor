package edu.editor;


public class FiniteAutomata<T>{
    private Set<T> states, finalStates;
    private Set<String> alphabets;
    private T[][] transition;
    private T initialState;

    public FiniteAutomata(){

    }
    public FiniteAutomata(int noOfStates, int noOfSymbols){
        states = new BitSet<T>(ENUM.STRING);
        alphabets = new BitSet<String>(ENUM.STRING);
        initialState = null;
        transition = (T[][]) new Object[noOfStates][noOfSymbols];
        finalStates = new BitSet<>(ENUM.STRING);

    }

    public FiniteAutomata(Set<T> states, Set<String> alphabets, T initialState, T[][] transitions, Set<T> finalStates){
        this.states = states;
        this.alphabets = new BitSet<String>(ENUM.STRING);
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

    public FiniteAutomata<T> setAlphabets(Set<String> alphabets) {
        this.alphabets = alphabets;
        return this;
    }

    public Set<String> getAlphabets() {
        return alphabets;
    }

    public FiniteAutomata<T> setStates(Set<T> states) {
        this.states = states;
        return this;

    }

    public edu.editor.Set<T> getStates() {
        return states;
    }

    public FiniteAutomata<T> setTransition(T[][] transition) {
        this.transition = transition;
        return this;
    }

    public T[][] getTransition() {
        return transition;
    }
}