package edu.editor;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Context_Menu {
    public static void setContextMenu(HBox state, GraphicalViewController controller) {
        javafx.scene.control.ContextMenu contextMenu = new javafx.scene.control.ContextMenu();
        javafx.scene.control.MenuItem initialState = new javafx.scene.control.MenuItem("Start state");
        javafx.scene.control.MenuItem finalState = new javafx.scene.control.MenuItem("Final state");
        javafx.scene.control.MenuItem self_loop = new javafx.scene.control.MenuItem("Self loop");

        initialState.setOnAction(e -> {
            checkIfStartStartAlreadyExists(controller.getModel());
            Arrow line = new Arrow();
            line.setStartX(0);
            line.setStartY(GraphicalViewModel.STATE_RADIUS);
            line.setEndX(-GraphicalViewModel.STATE_RADIUS * 2);
            line.setEndY(GraphicalViewModel.STATE_RADIUS);
            state.getChildren().add(line);

            state.setLayoutX(state.getLayoutX() - GraphicalViewModel.STATE_RADIUS * 2);
        });

        finalState.setOnAction(e -> {
            Circle circle = new Circle(0, 0, GraphicalViewModel.STATE_RADIUS - 10, Color.TRANSPARENT);
            circle.setStroke(Color.BROWN);
            circle.setStrokeWidth(2);
            StackPane stackPane = (StackPane) state.getChildren().get(0);
            stackPane.getChildren().add(circle);

        });

        self_loop.setOnAction(e -> {
            Group transition = null;
            if(transition == null){
                selfLoop(state);
            }
            //setAlphabet(transition, state, state);
            //stateInfo.add(new STATE_INFO(state, state, transition));
        });


        contextMenu.getItems().addAll(initialState, finalState, self_loop);
        state.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    GraphicalViewController.drawingStatus = Constants.CONTEXT_MENU;
                    contextMenu.show(state.getChildren().get(0), event.getSceneX() + 100, event.getScreenY());
                }
            }
        });
    }

    private static void checkIfStartStartAlreadyExists(GraphicalViewModel model) {
        for (int i = 0; i < model.getStateList().size(); i++) {
            if (model.getStateList().get(i).getChildren().size() > 1) {
                model.getStateList().get(i).getChildren().remove(model.getStateList().get(i).getChildren().get(1));
                model.getStateList().get(i).setLayoutX(model.getStateList().get(i).getLayoutX() + GraphicalViewModel.STATE_RADIUS * 2);
                break;
            }


        }
    }

//    public static Group checkDoubleTransition(HBox start_state, HBox end_state) {
//        for (STATE_INFO i : stateInfo)
//            if (start_state == i.start_state && end_state == i.end_state) return i.transition;
//        return null;
//    }

    private static void selfLoop(HBox state){
        StackPane stackPane = (StackPane) state.getChildren().get(0);
        Circle _state = (Circle) stackPane.getChildren().get(0);

        Group group = new Group();
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        ArcTo arcTo  = new ArcTo();
        Circle head = new Circle();

        head.setFill(Color.BLACK);
        head.setRadius(7);
        head.setCenterX(_state.getCenterX() - GraphicalViewModel.STATE_RADIUS/2);
        head.setCenterY(_state.getCenterY() - GraphicalViewModel.STATE_RADIUS);

        path.setStroke(Color.BROWN);
        path.setStrokeWidth(1.5);

        moveTo.setX(_state.getCenterX() + GraphicalViewModel.STATE_RADIUS /2);
        moveTo.setY(_state.getCenterY() - GraphicalViewModel.STATE_RADIUS);

        arcTo.setX(_state.getCenterX() - GraphicalViewModel.STATE_RADIUS/2);
        arcTo.setY(_state.getCenterY() - GraphicalViewModel.STATE_RADIUS /2);
        arcTo.setRadiusX(10);
        arcTo.setRadiusY(30);


        path.getElements().add(moveTo);
        path.getElements().add(arcTo);
        group.getChildren().add(path);
        group.getChildren().add(head);


        stackPane.getChildren().addAll(group);
    }
}
