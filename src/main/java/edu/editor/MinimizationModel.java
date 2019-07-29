package edu.editor;

import java.util.*;

public class MinimizationModel {
    private FiniteAutomata<Integer> dfa;

    public MinimizationModel(FiniteAutomata dfa) {
        this.dfa = dfa;
    }

    private Set<Integer> EliminateUnreachableStates() {
        Stack<Integer> stack = new Stack<>();
        stack.push(dfa.getInitialState());
        Set<Integer> result = new BitSet<>(BitSet.ENUM.INTEGER);
        while (!stack.isEmpty()) {
            int state = stack.pop();
            result.insert(state);
            List<Integer> list = dfa.getTransition().get(state);
            for(int i = 0; i < list.size(); i++){
                int nextState = list.get(i);
                if(!stack.contains(nextState) && !result.contains(nextState)){
                    stack.push(nextState);
                }
            }
        }
        return result;
    }

    private Set<Integer> ReduceTrapStates(Set<Integer> set) {
        int trapStates = 0;
        boolean check = false;
        Set<Integer> result = new BitSet<>(BitSet.ENUM.INTEGER);

        int stateIndex = 0;
        for (int i : set.getElements()) {
            List<Integer> list = dfa.getTransition().get(i);
            int count = 0;
            for(int j = 0; j < list.size(); j++){
                if(i == list.get(j)){
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

    private Set<Set> ZeroEquivalence(Set<Integer> set) {
        Set<Integer> finalStates = new BitSet<>(BitSet.ENUM.INTEGER);
        Set<Integer> nonFinalStates = new BitSet<>(BitSet.ENUM.INTEGER);
        for (int i : set.getElements()) {
            boolean check = false;
            for (Object j : dfa.getFinalStates().getElements()) {
                if (j.equals(i)) {
                    check = true;
                }
            }
            if (!check) {
                nonFinalStates.insert(i);
            } else {
                finalStates.insert(i);
            }
        }
        Set<Set> result = new BitSet<>(BitSet.ENUM.SET_INTEGER);
        if (nonFinalStates.getSize() > 0) {
            result.insert(nonFinalStates);
        }
        if (finalStates.getSize() > 0) {
            result.insert(finalStates);
        }
        return result;
    }

    public FiniteAutomata<Set> minimize() {
        //get states
        Set<Set> states = getStates(ZeroEquivalence(ReduceTrapStates(EliminateUnreachableStates())));

        //get alphabets
        Set<Integer> symbols = dfa.getAlphabets();

        //get initial state
        Set<Integer> initial_state = getInitialState(states);

        //get final states
        Set<Set> final_states = getFinalStates(states);

        //get Transitions
        Map<Set, List<Set>> transitions = getTransition(states);



        return new FiniteAutomata<Set>()
                .setStates(states)
                .setAlphabets(symbols)
                .setInitialState(initial_state)
                .setFinalStates(final_states)
                .setTransition(transitions);



    }

    private Set<Set> getStates(Set<Set> sets) {
        Set<Set> result = new BitSet<>(BitSet.ENUM.SET_INTEGER);
        Set<Set<Integer>> temp = null;
        for (Set<Integer> i : sets.getElements()) {
            temp = new BitSet<>(BitSet.ENUM.SET_INTEGER);
            if (i.getSize() > 1) {
                Set<Integer> current_set = new BitSet<>(BitSet.ENUM.INTEGER);
                current_set.insert(i.getElements().iterator().next());
                temp.insert(current_set);
                for (int element : i.getElements()) {
                    List<Integer> list1 = dfa.getTransition().get(element);
                    if (!current_set.contains(element)) {
                        boolean isEqual = true;
                        for (Set<Integer> l : temp.getElements()) {
                            List<Integer> list2 = dfa.getTransition().get(l.getElements().iterator().next());
                            isEqual = true;

                            for(int k = 0; k < list1.size(); k++){
                                if (!isEquivalent(sets, list1.get(k), list2.get(k))) {
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
                            Set<Integer> newSet = new BitSet<>(BitSet.ENUM.INTEGER);
                            newSet.insert(element);
                            temp.insert(newSet);
                        }
                    }
                }
            } else {
                temp.insert(i);
            }
            for (Set<Integer> t : temp.getElements()) {
                result.insert(t);
            }
        }
        if (!result.isEqual(sets)) {
            result = getStates(result);
        }

        return result;

    }


    private  Map<Set, List<Set>>  getTransition(Set<Set> set) {
        Map<Set, List<Set>> result = new HashMap<>();
        for (Set<Integer> s : set.getElements()) {
            int itr = s.getElements().iterator().next();
            List<Integer> list = dfa.getTransition().get(itr);
            List<Set> lst = new ArrayList<>();
            for(int m = 0; m < list.size(); m++){
                int st = list.get(m);
                for (Set k : set.getElements()) {
                    if (k.contains(st)) {
                        lst.add(k);
                        break;
                    }
                }
            }
            result.put(s, lst);
        }
        return result;
    }

    private Set<Set> getFinalStates(Set<Set> set) {
        Set<Set> result = new BitSet<>(BitSet.ENUM.SET_INTEGER);
        for (Set<Integer> i : set.getElements()) {
            for (Object f : dfa.getFinalStates().getElements()) {
                if (i.contains((int)f)) {
                    result.insert(i);
                    break;
                }
            }
        }

        return result;
    }

    private Set<Integer> getInitialState(Set<Set> set) {
        for (Set s : set.getElements()) {
            if (s.contains(dfa.getInitialState())) {
                return s;
            }
        }
        return null;
    }

    private boolean isEquivalent(Set<Set> sets, int x, int y) {
        for (Set<Integer> i : sets.getElements()) {
            if (i.contains(x) && i.contains(y)) {
                return true;
            }
        }
        return false;
    }

    private int getStateIndex(int state) {
        int index = 0;
        for (Object i : dfa.getStates().getElements()) {
            if (i.equals(state)) {
                return index;
            }
            index++;
        }
        return -1;
    }
    //
    public int getSymbolIndex(int state) {
        int index = 0;
        for (int i : dfa.getAlphabets().getElements()) {
            if (i== state) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private boolean isKthBitSet(long set, long n)
    {
        long i = 1;
        if (((i << n) & set) != 0)
            return true;
        return false;
    }
}
