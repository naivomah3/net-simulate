/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.view;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author RANAIVOSON
 */
public class MainFxmlView extends Application{
   
    public static Stage stagePublic;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/mg/entraide/fxml/MainFxml.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/mg/entraide/css/gridPaneScreenStyle.css").toExternalForm());
        
        stagePublic = stage;
        stagePublic.setScene(scene);
        stagePublic.setResizable(true);
        stagePublic.setTitle("Zanatany Network Simulator");
        stagePublic.getIcons().add(new Image(this.getClass().getResource("/mg/entraide/images/icon-zns-maxi.png").toString()));
        stagePublic.sizeToScene();
        stagePublic.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
