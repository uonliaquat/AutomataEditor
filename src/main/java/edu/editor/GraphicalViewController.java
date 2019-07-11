package edu.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GraphicalViewController implements Initializable {

    @FXML
    private Group group;
    @FXML
    private Button selectBtn, stateBtn, transitionBtn;
    private GraphicalViewModel model;
    public static String drawingStatus;
    private int stateNo;
    private List<Draggable.Nature> draggableStates = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new GraphicalViewModel();

        //set status initially drawing status to STATE
        drawingStatus = Constants.STATE;

        stateNo = 0;

    }

    @FXML
    private void processButtons(ActionEvent actionEvent){
        removeDragging();
        if(actionEvent.getSource().equals(stateBtn)){
            drawingStatus = Constants.STATE;
        }
        else if(actionEvent.getSource().equals(selectBtn)){
            //make all states draggable
            for (int i = 0; i < model.getStateList().size(); i++) {
                Draggable.Nature nature = new Draggable.Nature(model.getStateList().get(i));
                draggableStates.add(nature);
            }
            drawingStatus = Constants.DRAGGING;
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent){
        switch (drawingStatus){
            case Constants.STATE:
                HBox hBox = model.drawState(mouseEvent.getX(), mouseEvent.getY(), "q" + stateNo);
                Context_Menu.setContextMenu(hBox, this);
                group.getChildren().add(hBox);
                stateNo++;
                break;
        }
    }

    private void removeDragging() {
        for (int i = 0; i < draggableStates.size(); i++) {
            draggableStates.get(i).removeDraggedNode(model.getStateList().get(i));
        }
        draggableStates.clear();
    }

    public GraphicalViewModel getModel() {
        return model;
    }

    public Group getGroup() {
        return group;
    }
}
