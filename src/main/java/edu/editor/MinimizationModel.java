package edu.editor;

import java.util.*;

public class MinimizationModel {
    private FiniteAutomata<String> dfa;

    public MinimizationModel(FiniteAutomata<String> dfa) {
        this.dfa = dfa;
    }

    private Set<String> EliminateUnreachableStates() {
        Stack<String> stack = new Stack<>();
        stack.push(dfa.getInitialState());
        Set<String> result = new BitSet<String>(ENUM.STRING);
        while (!stack.isEmpty()) {
            String state = stack.pop();
            result.insert(state);
            dfa.getAlphabets().getElements();
            for(String alphabet : dfa.getAlphabets().getElements()){
                String nextState = dfa.getTransition()[getStateIndex(state)][getSymbolIndex(alphabet)];
                if(!stack.contains(nextState) && !result.contains(nextState)){
                    stack.push(nextState);
                }
            }
        }
        return result;
    }


//        Stack<String> stack = new Stack<>();
//        Set<String> result = new BitSet<String>(ENUM.STRING);
//        result.insert(dfa.getInitialState());
//        stack.push(dfa.getInitialState());
//        while (!stack.isEmpty()) {
//            String state = stack.pop();
//            result.insert(state);
//            for (String symbol : dfa.getAlphabets().getSet()) {
//                String s = dfa.getTransition()[getStateIndex(state)][getSymbolIndex(symbol)];
//                if (!stack.contains(s) && !result.contains(s)) {
//                    stack.push(s);
//                }
//            }
//        }
//        return result;

 //   }

    private Set<String> ReduceTrapStates(Set<String> set) {
        int trapStates = 0;
        boolean check = false;
        Set<String> result = new BitSet<String>(ENUM.STRING);

        int stateIndex = 0;
        for (String i : set.getElements()) {
            int count = 0;
            for (int j = 0; j < dfa.getAlphabets().getSize(); j++) {
                if (i.equals(dfa.getTransition()[stateIndex][j])) {
                    count++;
                }
            }
            if (count == dfa.getAlphabets().getSize()) {
                trapStates++;
                check = true;
            }
            if (!check || trapStates == 1) {
                result.insert(i);
            }
            check = false;
            stateIndex++;
        }
        return result;

    }

    private Set<Set<String>> ZeroEquivalence(Set<String> set) {
        Set<String> finalStates = new BitSet<String>(ENUM.STRING);
        Set<String> nonFinalStates = new BitSet<String>(ENUM.STRING);
        for (String i : set.getElements()) {
            boolean check = false;
            for (String j : dfa.getFinalStates().getElements()) {
                if (i.equals(j)) {
                    check = true;
                }
            }
            if (!check) {
                nonFinalStates.insert(i);
            } else {
                finalStates.insert(i);
            }
        }
        Set<Set<String>> result = new BitSet<Set<String>>(ENUM.SET_STRING);
        if (nonFinalStates.getSize() > 0) {
            result.insert(nonFinalStates);
        }
        if (finalStates.getSize() > 0) {
            result.insert(finalStates);
        }
        return result;
    }

    public FiniteAutomata<Set<String>> minimize() {
        //get states
        Set<Set<String>> states = getStates(ZeroEquivalence(ReduceTrapStates(EliminateUnreachableStates())));

        //get alphabets
        Set<String> symbols = dfa.getAlphabets();

        //get initial state
        Set<String> initial_state = getInitialState(states);

        //get final states
        Set<Set<String>> final_states = getFinalStates(states);

        //get Transitions
        Set<String>[][] transitions = getTransition(states);

        return new FiniteAutomata<Set<String>>()
                .setStates(states)
                .setAlphabets(symbols)
                .setInitialState(initial_state)
                .setFinalStates(final_states)
                .setTransition(transitions);

    }

    private Set<Set<String>> getStates(Set<Set<String>> sets) {
        Set<Set<String>> result = new BitSet<Set<String>>(ENUM.SET_STRING);
        Set<Set<String>> temp = null;
        for (Set<String> i : sets.getElements()) {
            temp = new BitSet<Set<String>>(ENUM.SET_STRING);
            if (i.getSize() > 1) {
                Set<String> current_set = new BitSet<String>(ENUM.STRING);
                current_set.insert(i.getElements().iterator().next());
                temp.insert(current_set);
                for (String element : i.getElements()) {
                    if (!current_set.contains(element)) {
                        boolean isEqual = true;
                        for (Set<String> l : temp.getElements()) {
                            isEqual = true;
                            for (String symbol : dfa.getAlphabets().getElements()) {
                                if (!isEquivalent(sets, dfa.getTransition()[getStateIndex(element)][getSymbolIndex(symbol)],
                                        dfa.getTransition()[getStateIndex(l.getElements().iterator().next())][getSymbolIndex(symbol)])) {
                                    isEqual = false;
                                    break;
                                }
                            }
                            if (isEqual) {
                                l.insert(element);
                                break;
                            }
                        }
                        if (!isEqual) {
                            Set<String> newSet = new BitSet<String>(ENUM.STRING);
                            newSet.insert(element);
                            temp.insert(newSet);
                        }
                    }
                }
            } else {
                temp.insert(i);
            }
            for (Set<String> t : temp.getElements()) {
                result.insert(t);
            }
        }
        if (!result.isEqual(sets)) {
            result = getStates(result);
        }
        return result;

    }


    private Set<String>[][] getTransition(Set<Set<String>> set) {
        int i = 0, j = 0;
        Set<String>[][] result = new Set[set.getSize()][dfa.getAlphabets().getSize()];
        for (Set<String> s : set.getElements()) {
            String itr = s.getElements().iterator().next();
            for (String symbol : dfa.getAlphabets().getElements()) {
                String st = dfa.getTransition()[getStateIndex(itr)][getSymbolIndex(symbol)];
                for (Set<String> k : set.getElements()) {
                    if (k.contains(st)) {
                        result[i][j] = k;
                        break;
                    }
                }
                j++;
            }
            i++;
            j = 0;
        }
        return result;
    }

    private Set<Set<String>> getFinalStates(Set<Set<String>> set) {
        Set<Set<String>> result = new BitSet<Set<String>>(ENUM.SET_STRING);
        for (Set<String> i : set.getElements()) {
            for (String f : dfa.getFinalStates().getElements()) {
                if (i.contains(f)) {
                    result.insert(i);
                    break;
                }
            }
        }

        return result;
    }

    private Set<String> getInitialState(Set<Set<String>> set) {
        for (Set<String> s : set.getElements()) {
            if (s.contains(dfa.getInitialState())) {
                return s;
            }
        }
        return null;
    }

    private boolean isEquivalent(Set<Set<String>> sets, String x, String y) {
        for (Set<String> i : sets.getElements()) {
            if (i.contains(x) && i.contains(y)) {
                return true;
            }
        }
        return false;
    }

    private int getStateIndex(String state) {
        int index = 0;
        for (String i : dfa.getStates().getElements()) {
            if (i.equals(state)) {
                return index;
            }
            index++;
        }
        return -1;
    }
//
    public int getSymbolIndex(String state) {
        int index = 0;
        for (String i : dfa.getAlphabets().getElements()) {
            if (i.equals(state)) {
                return index;
            }
            index++;
        }
        return -1;
    }

//    private boolean isKthBitSet(long set, long n)
//    {
//        long i = 1;
//        if (((i << n) & set) != 0)
//            return true;
//        return false;
//    }
}