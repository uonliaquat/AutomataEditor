package edu.editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MinimizationRootController implements Initializable {

    @FXML
    private Pane graphicalViewPane, definitionViewPane;

    @FXML
    private TabPane tabPane;

    private FXMLLoader graphicalPaneLoader, definitionPaneLoader;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pane graphicalPane = null;
        Pane definitionPane = null;
        try {
            graphicalPaneLoader = new FXMLLoader(getClass().getResource("/edu.editor/graphicalView.fxml"));
            definitionPaneLoader = new FXMLLoader(getClass().getResource("/edu.editor/definitionView.fxml"));
            graphicalPane = graphicalPaneLoader.load();
            definitionPane = definitionPaneLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphicalViewPane.getChildren().add(graphicalPane);
        definitionViewPane.getChildren().add(definitionPane);

    }

    public int getTabPaneSelectedIndex() {
        return tabPane.getSelectionModel().getSelectedIndex();
    }

    public GraphicalViewController getGraphicalViewController(){
        return graphicalPaneLoader.getController();
    }

    public DefinitionViewController getDefinitionViewController(){
        return definitionPaneLoader.getController();
    }

}
