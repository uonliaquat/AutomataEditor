package edu.editor;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private VBox drawer;
    @FXML
    private Button menu;
    @FXML
    private Button minimizeDFABtn, playBtn;
    @FXML
    private Pane pane;

    private Pane newLoadedPane = null;

    private FXMLLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       drawerAction();
       drawer.toFront();

       playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Access Minimization Root Controller
                MinimizationRootController minimizationRootController = loader.getController();
                if(minimizationRootController.getTabPaneSelectedIndex() == 0){
                    //graphical view
                }
                else{
                    //definition view
                    minimizationRootController.getDefinitionViewController().MinimizeDFA();
                }

            }
        });
    }

    @FXML
    private void processButtons(ActionEvent actionEvent) throws IOException {
       if(actionEvent.getSource().equals(minimizeDFABtn)){
           if(newLoadedPane == null) {
               loader = new FXMLLoader(getClass().getResource("/edu.editor/minimizationRoot.fxml"));
               newLoadedPane = loader.load();
               pane.getChildren().add(newLoadedPane);
           }
           //close navigation drawer
           TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
           closeNav.setToX(-(drawer.getWidth()));
           closeNav.play();
       }
    }

    private void drawerAction() {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), drawer);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
        menu.setOnAction((ActionEvent evt) -> {
            if (drawer.getTranslateX() != 0) {
                openNav.play();
            } else {
                closeNav.setToX(-(drawer.getWidth()));
                closeNav.play();
            }
        });
    }
}
