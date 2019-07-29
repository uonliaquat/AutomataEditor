package edu.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class DefinitionViewController implements Initializable {

    @FXML
    private TableView dfaTransitionTable, minimizedDfaTransitionTable;

    @FXML
    private Button setTransitionsBtn;

    @FXML
    private TextField states_field, alphabets_field, initialState_field, finalStates_field;

    @FXML
    private Text minimizedStates, minimizedAlphabets, minimizedInitialState, minimizedFinalStates;

    private MinimizationModel model;


    private Map<Integer, List<Integer>> transitionData;
    private String[] states_arr, alphabets_arr, finalStates_arr;
    private int initial_state;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTransitionsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getValidText(states_field.getText(), alphabets_field.getText(), initialState_field.getText(), finalStates_field.getText());
                generateDFATransitionTable();
                setTransitionsBtn.setDisable(true);
            }
        });
    }

    private void getValidText(String states, String symbols, String initialState, String finalStates){
        states_arr = states.split("[{},]");
        alphabets_arr = symbols.split("[{},]");
        initial_state = Integer.parseInt(initialState);
        finalStates_arr = finalStates.split("[{},]");
        transitionData = new HashMap<>();
    }

    private void generateDFATransitionTable(){
        dfaTransitionTable.setEditable(true);

        for(int i = 0; i < alphabets_arr.length; i++){
            TableColumn c= new TableColumn<>(alphabets_arr[i]);
            c.setMinWidth(100);
            if(i == 0) {
                c.setCellValueFactory(new PropertyValueFactory<>("name"));
            }
            c.setCellFactory(TextFieldTableCell.forTableColumn());
            dfaTransitionTable.getColumns().add(c);
            c.setCellFactory(TextFieldTableCell.<Column>forTableColumn());
            c.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Column, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<Column, String> event) {
                            ((Column) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow())
                            ).setName(event.getNewValue());
                        }
                    }
            );
        }


        for(int i = 1; i < states_arr.length; i++){
            Column c = new Column(states_arr[i]);
            dfaTransitionTable.getItems().add(c);
        }

    }

    private void getTableData() {
        int  x = 0, y = 0;
        boolean check = false;
        for (Node r : dfaTransitionTable.lookupAll(".table-row-cell")) {
            int key = 0;
            List list = new ArrayList();
            for (Node c : r.lookupAll(".table-cell")) {
                TableCell<?, ?> tc = (TableCell<?, ?>) c;
                if(tc.getText() == null){
                    return;
                }
                if(check) {
                    list.add(Integer.parseInt(tc.getText()));
                    y++;
                }
                else{
                    key = Integer.parseInt(tc.getText());
                }
                check = true;
            }
            transitionData.put(key, list);
            check = false;
            x++;
            y = 0;
        }
    }

    public void MinimizeDFA(){
        getTableData();

        Set<Integer> states = new BitSet<>(BitSet.ENUM.INTEGER);
        for(int i = 1; i < states_arr.length; i++){
            states.insert(Integer.parseInt(states_arr[i]));
        }

        Set<Integer> alphabets = new BitSet<>(BitSet.ENUM.INTEGER);
        for(int i = 1; i < alphabets_arr.length; i++){
            alphabets.insert(Integer.parseInt(alphabets_arr[i]));
        }


        Set<Integer> finalStates = new BitSet<>(BitSet.ENUM.INTEGER);
        for(int i = 1; i < finalStates_arr.length; i++){
            finalStates.insert(Integer.parseInt(finalStates_arr[i]));
        }


        FiniteAutomata<Integer> dfa = new FiniteAutomata()
                .setStates(states)
                .setAlphabets(alphabets)
                .setInitialState(initial_state)
                .setTransition(transitionData)
                .setFinalStates(finalStates);

        model = new MinimizationModel(dfa);
        FiniteAutomata<Set> minimizedDFA =  model.minimize();
        UpdateTable(minimizedDFA);

    }

    private void UpdateTable(FiniteAutomata<Set> minimizedDFA){
        String states = "[", final_states = "[";
        for(int i = 0; i < minimizedDFA.getStates().getSize(); i++){
            states = states + minimizedDFA.getStates().getElements().get(i).getElements().toString();
        }
        for(int i = 0; i < minimizedDFA.getFinalStates().getSize(); i++){
            final_states = final_states + minimizedDFA.getFinalStates().getElements().get(i).getElements().toString();
        }
        states = states + "]";
        final_states = final_states + "]";
        minimizedStates.setText(states);
        minimizedAlphabets.setText(minimizedDFA.getAlphabets().getElements().toString());
        minimizedInitialState.setText(minimizedDFA.getInitialState().getElements().toString());
        minimizedFinalStates.setText(final_states);
        generateMinimisedDFATransitionTable(minimizedDFA);
    }

    private void generateMinimisedDFATransitionTable(FiniteAutomata<Set> minimizedDFA){

        for(int i = -1; i < minimizedDFA.getAlphabets().getSize(); i++){
            TableColumn c;
            if(i == -1){
                c= new TableColumn<>("");
            }
            else {
                c = new TableColumn<>(minimizedDFA.getAlphabets().getElements().get(i).toString());
            }
            c.setMinWidth(100);
            if(i > 0) {
                c.setCellValueFactory(new PropertyValueFactory<>(Integer.toString(i)));
            }
            c.setCellFactory(TextFieldTableCell.forTableColumn());
            minimizedDfaTransitionTable.getColumns().add(c);
//            c.setCellFactory(TextFieldTableCell.<Column>forTableColumn());
//            c.setOnEditCommit(
//                    new EventHandler<TableColumn.CellEditEvent<Column, String>>() {
//                        @Override
//                        public void handle(TableColumn.CellEditEvent<Column, String> event) {
//                            ((Column) event.getTableView().getItems().get(
//                                    event.getTablePosition().getRow())
//                            ).setName(event.getNewValue());
//                        }
//                    }
//            );
        }


//        for(int i = 0; i < minimizedDFA.getStates().getSize(); i++){
//            Column c = new Column(minimizedDFA.getStates().getElements().get(i).getElements().toString());
//            minimizedDfaTransitionTable.getItems().add(c);
//        }

        for (Map.Entry<Set, List<Set>> entry : minimizedDFA.getTransition().entrySet()) {
           List<Set> list = entry.getValue();
           for(int i = 0; i < list.size(); i++){
               Column c = new Column(list.get(i).toString());
               minimizedDfaTransitionTable.getItems().add(c);
           }
        }
    }

    public class Column {
        private String name;

        public Column(){

        }
        public Column(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
