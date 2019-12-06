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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mg.entraide.object.InterfaceObject;
import mg.entraide.tools.IpCheckerTools;

/**
 * FXML Controller class
 *
 * @author RANAIVOSON
 */
public class PingDialogFxmlController implements Initializable {

    @FXML
    private TextField ipAddressField = new TextField();

    private Stage dialogStage;
    private String ipAddress;
    private boolean isValid = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handlePing() {
        if (isInputConfigValid()) {
            ipAddress = ipAddressField.getText().trim();
            setIsValid(true);
            dialogStage.close();
        }

    }

    private boolean isInputConfigValid() {
        String errorMessage = "";
        //Check IP field
        if (!ipAddressField.getText().trim().equalsIgnoreCase("")) {
            if (!IpCheckerTools.isValidIP(ipAddressField.getText().trim(), false)) {
                errorMessage += "Invalid IP address \n";
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Ping");
                alert.setHeaderText("Please type a valid IP");
                alert.setContentText(errorMessage);
                alert.showAndWait();

            } else {
                String messagePing = "";
                for (InterfaceObject interfaceIterator : MainFxmlController.listInterfaceObjectMainPanel) {
                    if (interfaceIterator.getIpAddressObj().getIpAddress() != null) {
//                        System.out.println("interfaceIterator.getIpAddressObj().getIpAddress() : " + interfaceIterator.getIpAddressObj().getIpAddress());
//                        System.out.println("interfaceIterator.getInterfaceStatus() : " + interfaceIterator.getInterfaceStatus());
                        if (interfaceIterator.getIpAddressObj().getIpAddress().equalsIgnoreCase(ipAddressField.getText().trim()) && interfaceIterator.getInterfaceStatus() == 2) {
                            messagePing = "Ping OK";
                        }
                    }

                }
                if (messagePing.length() != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ping to " + ipAddressField.getText().trim());
                    alert.setHeaderText(null);
                    alert.setContentText(messagePing);
                    alert.showAndWait();
                    System.out.println("Ping OK : " + ipAddressField.getText().trim());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ping to " + ipAddressField.getText().trim());
                    alert.setHeaderText(null);
                    alert.setContentText("Request timed out");
                    alert.showAndWait();
                    System.out.println("Request timed out: " + ipAddressField.getText().trim());
                }
            }
        } else {
            dialogStage.close();
            return false;
        }

        return false;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
