/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mg.entraide.object.InterfaceObject;
import mg.entraide.object.IpAdressObject;
import mg.entraide.tools.IpCheckerTools;
import static mg.entraide.view.MainFxmlView.stagePublic;

/**
 * FXML Controller class
 *
 * @author RANAIVOSON
 */
public class IpEditDialogFxmlController implements Initializable {
    @FXML
    private TextField ipAddressField = new TextField();
    @FXML
    private TextField maskField = new TextField();
    @FXML
    private TextField gatewayField = new TextField();
    
    
    private Stage dialogStage;
    private IpAdressObject ipAdressObject = new IpAdressObject();
    private boolean isValid = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
    @FXML
    private void handleOK () {
        if (isInputConfigValid()) {
            ipAdressObject.setIpAddress(ipAddressField.getText().trim());
            ipAdressObject.setIpMask(maskField.getText().trim());
            ipAdressObject.setIpNetwork(IpCheckerTools.getNetwork(ipAddressField.getText().trim(), maskField.getText().trim()));
            if (gatewayField.getText().trim().length() == 0) {
                ipAdressObject.setIpGateway(null);
            } else {
                ipAdressObject.setIpGateway(gatewayField.getText().trim());
            }
            isValid = true;
            dialogStage.close();
        } else {
            isValid = false;
        }
    }
    
    @FXML
    private void handleCancel () {
        dialogStage.close();
        
    }
    
    /**
     * Traitement des inputs
     * @return 
     */
    private boolean isInputConfigValid () {
        String errorMessage = "";
        //Check IP field
        if (!ipAddressField.getText().trim().equalsIgnoreCase("")) {
            if (!IpCheckerTools.isValidIP(ipAddressField.getText().trim(), false)) {
                errorMessage += "No valid IP \n";
            }
        } else {
            errorMessage += "No valid IP \n";
        }
        //Check mask field
        if (!maskField.getText().trim().equalsIgnoreCase("")) {
            if (!IpCheckerTools.isValidIP(maskField.getText().trim(), true)) {
                errorMessage += "No valid netmask \n";
            }
        } else {
            errorMessage += "No valid netmask \n";
        }
        //Check gateway field 
        if (!gatewayField.getText().trim().equalsIgnoreCase("")) {
            if (!IpCheckerTools.isValidIP(gatewayField.getText(), false)) {
                errorMessage += "No valid gateway ";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Settings error");
            alert.setHeaderText("IP settings invalid");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        return false;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public Stage getDialogStage () {
        return this.dialogStage;
    }
    public void setIpConfigField (IpAdressObject ipAdressObjectConfig) {
        if (ipAdressObjectConfig != null) {
            if (ipAdressObjectConfig.getIpAddress() != null) {
                ipAddressField.setText(ipAdressObjectConfig.getIpAddress());
            } else {
                ipAddressField.setText("");
            }
            if (ipAdressObjectConfig.getIpMask() != null) {
                maskField.setText(ipAdressObjectConfig.getIpMask());
            } else {
                maskField.setText("");
            }
            if (ipAdressObjectConfig.getIpGateway() != null) {
                gatewayField.setText(ipAdressObjectConfig.getIpGateway());
            } else {
                gatewayField.setText("");
            }
        }
        
    }
    public IpAdressObject getIpConfig () {
        return ipAdressObject;
    }  
    public boolean isValid() {
        return isValid;
    }
    public void isValid(boolean okClicked) {
        this.isValid = okClicked;
    }
    
    
    
    
}
