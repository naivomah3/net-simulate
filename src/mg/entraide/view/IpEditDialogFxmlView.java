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
import mg.entraide.controller.IpEditDialogFxmlController;
import mg.entraide.object.IpAdressObject;
import static mg.entraide.view.MainFxmlView.stagePublic;

/**
 *
 * @author RANAIVOSON
 */
public class IpEditDialogFxmlView  {

    public IpEditDialogFxmlView() { }
    
    public IpAdressObject showIpEditDialog (IpAdressObject ipAdressObject, String dialogTitle) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mg/entraide/fxml/IpEditDialogFxml.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle(dialogTitle);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(MainFxmlView.stagePublic);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        IpEditDialogFxmlController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.getDialogStage().getIcons().add(new Image(this.getClass().getResource("/mg/entraide/images/icon-zns-maxi.png").toString()));
        controller.setIpConfigField(ipAdressObject);
        dialogStage.showAndWait();
        if (controller.isValid()) {
            return controller.getIpConfig();
        }
        return null;
    }
    
    
}
