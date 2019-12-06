/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mg.entraide.controller.PingDialogFxmlController;

/**
 *
 * @author RANAIVOSON
 */
public class PingDialogFxmlView {
    
    

    public PingDialogFxmlView() {
    }
    
    public String showConsoleDialog (String dialogTitle) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mg/entraide/fxml/PingDialogFxml.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle(dialogTitle);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(MainFxmlView.stagePublic);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        PingDialogFxmlController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.getDialogStage().getIcons().add(new Image(this.getClass().getResource("/mg/entraide/images/icon-zns-maxi.png").toString()));
        dialogStage.showAndWait();
        if (controller.isIsValid()) {
            return controller.getIpAddress();
        }
        return null;
        
    }
    
}
