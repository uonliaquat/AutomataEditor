package edu.editor;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class GraphicalViewModel {

    public final static double STATE_RADIUS = 35;
    private List<HBox> stateList = new ArrayList<>();

    public HBox drawState(final double x, final double y, final String name){
        Circle circle = new Circle(0,0,STATE_RADIUS);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);

        Text text = new Text(name);
        text.setTextAlignment(TextAlignment.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(circle, text);

        HBox hBox = new HBox();
        hBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(stackPane);

        hBox.setLayoutX(x - STATE_RADIUS);
        hBox.setLayoutY(y - STATE_RADIUS);
        stateList.add(hBox);
        return hBox;
    }

    public List<HBox> getStateList() {
        return stateList;
    }
}
