/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mg.entraide.object.HostObject;
import mg.entraide.object.InterfaceObject;
import mg.entraide.object.IpAdressObject;
import mg.entraide.object.LineObject;
import mg.entraide.view.IpEditDialogFxmlView;
import mg.entraide.view.PingDialogFxmlView;

/**
 *
 * @author RANAIVOSON
 */
public class HostObjectController {

    public VBox hostVBoxView = null;
    public DoubleProperty interfacePositionX;
    public DoubleProperty interfacePositionY;

    private double orgSceneX;
    private double orgSceneY;
    private double orgTranslateX;
    private double orgTranslateY;
    private MainFxmlController mainFxmlController;
    private String hostName;
    private ContextMenu contextMenuAddInterface;
    private ContextMenu contextMenuSettings;
    private InterfaceObject eth0 = null;
    private InterfaceObject eth1 = null;
    
    /**********************************
     * MenuItem on contextMenuSettings*
     **********************************/
    private MenuItem itemConfigIpEth0;
    private MenuItem itemConfigIpEth1;
    private MenuItem itemConsole;
    /*************************************
     *MenuItem on contextMenuAddInterface* 
     *************************************/
    private MenuItem itemEth0;
    private MenuItem itemEth1;
    
//    private List<InterfaceObject> listInterfaceHost = new ArrayList<>();
//    private List<LineObject> listLineObjects = new ArrayList<>();

    public HostObjectController() {
    }
    
    public HostObjectController(MainFxmlController mainFxml) {
        mainFxmlController = mainFxml;
    }

    public LineObject addLine(String hostNameBeginP, String hostNameEndP, String interfaceNameBeginP, String interfaceNameEndP, Double startXP, Double startYP, Double endXP, Double endYP) {
        LineObject drawLineObject;
        drawLineObject = new LineObject(
                new SimpleDoubleProperty(startXP),
                new SimpleDoubleProperty(startYP),
                new SimpleDoubleProperty(endXP),
                new SimpleDoubleProperty(endYP),
                hostNameBeginP,/*** hostNameBegin **/
                interfaceNameBeginP,/*** interfaceNameBegin **/
                hostNameEndP,/*** hostNameEnd **/
                interfaceNameEndP);
        
        return drawLineObject;
    }
    
    public VBox addImage(String imageName, double positionX, double positionY, InterfaceObject _eth0, InterfaceObject _eth1) {
        hostName = imageName;
        eth0 = _eth0;
        eth1 = _eth1;
        
        /**
         * Add image view
         */
        hostVBoxView = new VBox();
        Label labeHostName = new Label(hostName);
        labeHostName.setContentDisplay(ContentDisplay.CENTER);
        labeHostName.setFont(Font.font("Arial", 13));
//        System.out.println("labeHostName " + labeHostName.getText());
        Image img = null;
        if (imageName.contains("Host")) {
            img = new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/host.png"));
        } else {
            img = new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/router.png"));
        }
        
        hostVBoxView.setSpacing(5);
        hostVBoxView.getChildren().add((labeHostName));
        hostVBoxView.getChildren().add(new ImageView(img));
        hostVBoxView.setAlignment(Pos.CENTER);
        hostVBoxView.setTranslateX(positionX);
        hostVBoxView.setTranslateY(positionY);

        /**
         * Initialize wire position
         */
        interfacePositionX = new SimpleDoubleProperty(positionX);
        interfacePositionY = new SimpleDoubleProperty(positionY);
        
        /**
         * initialize items on contextMenuSettings
         */
        itemConfigIpEth0 = new MenuItem("set settings eth0");
        itemConfigIpEth0.setOnAction(itemConfigIpEth0Event);
        itemConfigIpEth1 = new MenuItem("set settings eth1");
        itemConfigIpEth1.setOnAction(ItemConfigIpEth1Event);
        itemConsole = new MenuItem("console");
        itemConsole.setOnAction(itemConsoleEvent);
        /**
         * Initialize les 2 interfaces and initialize items on contextMenuAddInterface
         */
        itemEth0 = new MenuItem("eth0");
        itemEth0.setOnAction(itemEth0Event);
        itemEth1 = new MenuItem("eth1");
        itemEth1.setOnAction(itemEth1Event);

        /**
         * Initialize all hostVBoxView
         */
        hostVBoxView.setOnMousePressed(hostVBoxViewMousePressedEvent);
        hostVBoxView.setOnMouseDragged(hostVBoxViewMouseDraggedEvent);
        hostVBoxView.setOnMouseReleased(hostVBoxViewMouseReleasedEvent);

        return hostVBoxView;
    }
    
    /*****************************
     * All events on hostVBoxView* 
     *****************************/
    EventHandler<MouseEvent> hostVBoxViewMousePressedEvent = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            if (t.getButton() == MouseButton.SECONDARY) {
                if (!mainFxmlController.isHostClicked() && !mainFxmlController.isRouterClicked() && !mainFxmlController.isWireClicked()) {
//                    System.out.println("MouseButton.SECONDARY");
                    contextMenuSettings = new ContextMenu();
                    contextMenuSettings.getItems().addAll(itemConfigIpEth0, itemConfigIpEth1, itemConsole);
                    contextMenuSettings.show(hostVBoxView, Side.RIGHT, -10, 10);
                } 
            }
            
            /**********************************************
             *mettre à jour la position de l'interfaceHost*
             **********************************************/
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            if (hostVBoxView != null) {
                orgTranslateX = hostVBoxView.getTranslateX();
                orgTranslateY = hostVBoxView.getTranslateY();
//                hostVBoxView.toFront();
                hostVBoxView.setEffect(new DropShadow());
            }

            /**
             * check wire clicked 
             * if(true) -> view menu context
             * else -> nothing
             */
            if (mainFxmlController.isWireClicked()) {
                contextMenuAddInterface = new ContextMenu();
                contextMenuAddInterface.getItems().addAll(itemEth0, itemEth1);
                contextMenuAddInterface.show(hostVBoxView, Side.RIGHT, -20, 40);
            }

        }
    };
    EventHandler<MouseEvent> hostVBoxViewMouseDraggedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            interfacePositionX = new SimpleDoubleProperty(t.getX() - 37.5);
            interfacePositionY = new SimpleDoubleProperty(t.getY() - 40.5);

            if (!mainFxmlController.isWireClicked()) {
                if (hostVBoxView != null) {
                    setOrgTranslateX(new SimpleDoubleProperty(orgTranslateX + offsetX));
                    setOrgTranslateY(new SimpleDoubleProperty(orgTranslateY + offsetY));
                    hostVBoxView.setTranslateX(getOrgTranslateX().floatValue());
                    hostVBoxView.setTranslateY(getOrgTranslateY().floatValue());
                    eth0.setPositionHostX(hostVBoxView.getTranslateX());
                    eth0.setPositionHostY(hostVBoxView.getTranslateY());
                    eth1.setPositionHostX(hostVBoxView.getTranslateX());
                    eth1.setPositionHostY(hostVBoxView.getTranslateY());

                    /**
                     * update wire position when imageView was moved
                     */
                    if (!mainFxmlController.listLineObjectsMainPanel.isEmpty()) {
                        for (LineObject lineObjectFromMainPanel : mainFxmlController.listLineObjectsMainPanel) {
                            if (lineObjectFromMainPanel.hostNameBegin.equalsIgnoreCase(hostName)) {
                                lineObjectFromMainPanel.updateStartXY(
                                        new SimpleDoubleProperty(hostVBoxView.getTranslateX() + 37.5),
                                        new SimpleDoubleProperty(hostVBoxView.getTranslateY() + 40.5));
//                                System.out.println("My update : " + lineObjectFromMainPanel.getStartX());
                            } else if (lineObjectFromMainPanel.hostNameEnd.equalsIgnoreCase(hostName)) {
                                lineObjectFromMainPanel.updateEndXY(
                                        new SimpleDoubleProperty(hostVBoxView.getTranslateX() + 37.5),
                                        new SimpleDoubleProperty(hostVBoxView.getTranslateY() + 40.5));
                            }

                        }
                    }

                }
            }

        }
    };
    EventHandler<MouseEvent> hostVBoxViewMouseReleasedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            hostVBoxView.setEffect(null);
        }
    };
    EventHandler<ActionEvent> itemEth0Event = new EventHandler<ActionEvent>() {
        
        public void handle(ActionEvent event) {
            if (mainFxmlController.isWireClicked()) {
                if (eth0.getInterfaceStatus() == 1 || eth0.getInterfaceStatus() == 2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Interface busy");
                    alert.setHeaderText(null);
                    alert.setContentText("eth0 was connected, please choose another interface");
                    alert.show();
                    mainFxmlController.setWireClicked(false);
                    mainFxmlController.updateInterfaceStatusToDisconnected();
                    
                } else {
//                    //#######TEST
//                    afficheListInterface(mainFxmlController.listInterfaceObjectMainPanel);
                    
                    if (mainFxmlController.getCompteInterfaceSelected() == 0) {
                        /**
                         * Check if my interface "eth0" status was "disconnected" ie interfaceIterator.getInterfaceStatus() == 0
                         * if(true) -> set status to "connecting" ie set value to 1
                         * else -> check if 1 or 2 ie "connecting" or "connected"
                         */
                        eth0.setInterfaceStatus(1);
                        mainFxmlController.setCompteInterfaceSelected(mainFxmlController.getCompteInterfaceSelected()+1);;
                    } else {
//                        //#######TEST
//                        afficheListInterface(mainFxmlController.listInterfaceObjectMainPanel);
                        
                        if (mainFxmlController.getCompteInterfaceSelected() == 1) {
//                            //#######TEST
//                            afficheListInterface(mainFxmlController.listInterfaceObjectMainPanel);
                            for (HostObject hostIterator : mainFxmlController.getListHostObjectMainPanel()) {
                                /**
                                 * Check if my interface "eth0" status was
                                 * "connecting" ie
                                 * interfaceIterator.getInterfaceStatus() == 1
                                 * if(true) -> set status to "connected" ie set
                                 * value to 2 else -> nothing
                                 */
                                for (InterfaceObject interfaceIterator : hostIterator.getInterfaceObj()) {
                                    if (interfaceIterator.getInterfaceStatus() == 1) {
                                        /**
                                         * Set status to 2 for the 2 interfaces
                                         */
                                        eth0.setInterfaceStatus(2);
                                        interfaceIterator.setInterfaceStatus(2);
                                         /**
                                         * create line to draw on MainPanel
                                         */
                                        LineObject drawLineObject;
                                        drawLineObject = new LineObject(
                                                new SimpleDoubleProperty(interfaceIterator.getPositionHostX() + 37.5),
                                                new SimpleDoubleProperty(interfaceIterator.getPositionHostY() + 40.5),
                                                new SimpleDoubleProperty(interfacePositionX.floatValue() + 37.5),
                                                new SimpleDoubleProperty(interfacePositionY.floatValue() + 40.5),
                                                interfaceIterator.getRouteurName(),/** hostNameBegin **/
                                                interfaceIterator.getInterfaceName(),/** interfaceNameBegin **/
                                                hostName,/** hostNameEnd **/
                                                eth0.getInterfaceName());/** interfaceNameEnd **/
//                                        System.out.println("-------- drawLine create --------");
//                                        System.out.println("hostNameBegin : " + interfaceIterator.getRouteurName());
//                                        System.out.println("hostNameEnd : " + hostName);
                                        /**
                                         * set interface position to Begin end End
                                         */
                                        interfaceIterator.setInterfacePosition("begin");
                                        eth0.setInterfacePosition("end");
                                        /**
                                         * add drawLineObject to the line list
                                         * MainPanel
                                         */
                                        mainFxmlController.listLineObjectsMainPanel.add(drawLineObject);
                                        /**
                                         * add drawLineObject to Main panel View
                                         */
                                        mainFxmlController.getPaneScreen().getChildren().add(drawLineObject);
                                        drawLineObject.toBack();

                                        mainFxmlController.setCompteInterfaceSelected(0);
                                        /**
                                         * set wire clicked to false
                                         */
                                        mainFxmlController.setWireClicked(false);
//                                    //#######TEST
//                                    afficheListInterface(mainFxmlController.listInterfaceObjectMainPanel);

                                    }
                                }
                            }
                        }
                    }
                }
                
                
            }

        }
    };
    EventHandler<ActionEvent> itemEth1Event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (mainFxmlController.isWireClicked()) {
                if (eth1.getInterfaceStatus() == 1 || eth1.getInterfaceStatus() == 2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Interface busy");
                    alert.setHeaderText(null);
                    alert.setContentText("eth1 was connected, please choose another interface");
                    alert.show();
                    mainFxmlController.setWireClicked(false);
                    mainFxmlController.updateInterfaceStatusToDisconnected();
                    
                } else {
//                    //#######TEST
//                    afficheListInterface(mainFxmlController.listInterfaceObjectMainPanel);
                    
                    if (mainFxmlController.getCompteInterfaceSelected() == 0) {
                        /**
                         * Check if my interface "eth0" status was "disconnected" ie interfaceIterator.getInterfaceStatus() == 0
                         * if(true) -> set status to "connecting" ie set value to 1
                         * else -> check if 1 or 2 ie "connecting" or "connected"
                         */
                        eth1.setInterfaceStatus(1);
                        mainFxmlController.setCompteInterfaceSelected(mainFxmlController.getCompteInterfaceSelected()+1);;
                    } else {
//                        //#######TEST
//                        afficheListInterface(mainFxmlController.listInterfaceObjectMainPanel);
                        
                        if (mainFxmlController.getCompteInterfaceSelected() == 1) {
//                            //#######TEST
//                            afficheListInterface(mainFxmlController.listInterfaceObjectMainPanel);
                            for (HostObject hostIterator : mainFxmlController.getListHostObjectMainPanel()) {
                                /**
                                 * Check if my interface "eth0" status was
                                 * "connecting" ie
                                 * interfaceIterator.getInterfaceStatus() == 1
                                 * if(true) -> set status to "connected" ie set
                                 * value to 2 else -> nothing
                                 */
                                for (InterfaceObject interfaceIterator : hostIterator.getInterfaceObj()) {
                                    if (interfaceIterator.getInterfaceStatus() == 1) {
                                        /**
                                         * Set status to 2 for the 2 interfaces
                                         */
                                        eth1.setInterfaceStatus(2);
                                        interfaceIterator.setInterfaceStatus(2);
 /**
                                         * create line to draw on MainPanel
                                         */
                                        LineObject drawLineObject;
                                        drawLineObject = new LineObject(
                                                new SimpleDoubleProperty(interfaceIterator.getPositionHostX() + 37.5),
                                                new SimpleDoubleProperty(interfaceIterator.getPositionHostY() + 40.5),
                                                new SimpleDoubleProperty(interfacePositionX.floatValue() + 37.5),
                                                new SimpleDoubleProperty(interfacePositionY.floatValue() + 40.5),
                                                interfaceIterator.getRouteurName(),/** routerNameBegin **/
                                                interfaceIterator.getInterfaceName(),/** interfaceNameBegin **/
                                                hostName,/** routerNameEnd **/
                                                eth1.getInterfaceName());/** interfaceNameEnd **/
                                        /**
                                         * set interface position to Begin end End
                                         */
                                        interfaceIterator.setInterfacePosition("begin");
                                        eth1.setInterfacePosition("end");
                                        /**
                                         * add drawLineObject to the line list MainPanel
                                         */
                                        mainFxmlController.listLineObjectsMainPanel.add(drawLineObject);
                                        /**
                                         * add drawLineObject to Main panel View
                                         */
                                        mainFxmlController.getPaneScreen().getChildren().add(drawLineObject);
                                        drawLineObject.toBack();

                                        mainFxmlController.setCompteInterfaceSelected(0);
                                        /**
                                         * set wire clicked to false
                                         */
                                        mainFxmlController.setWireClicked(false);
//                                    //#######TEST
//                                    afficheListInterface(mainFxmlController.listInterfaceObjectMainPanel);

                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    };
    EventHandler<ActionEvent> itemConfigIpEth0Event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                IpAdressObject ipAdress = new IpAdressObject();
                IpEditDialogFxmlView dialogIpAdressView = new IpEditDialogFxmlView();
                ipAdress = dialogIpAdressView.showIpEditDialog(eth0.getIpAddressObj(), "eth0 internet protocol setting");
                if (ipAdress != null) {
                    eth0.setIpAddressObj(ipAdress);
//                    System.out.println("ipAdress eth0 != null");
                } else {
//                    System.out.println("ipAdress eth0 == null");
                }
               
            } catch (IOException ex) {
                Logger.getLogger(HostObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    EventHandler<ActionEvent> ItemConfigIpEth1Event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                IpAdressObject ipAdress = new IpAdressObject();
                IpEditDialogFxmlView dialogIpAdressView = new IpEditDialogFxmlView();
                ipAdress = dialogIpAdressView.showIpEditDialog(eth1.getIpAddressObj(), "eth1 internet protocol setting");
                if (ipAdress != null) {
                    eth1.setIpAddressObj(ipAdress);
//                    System.out.println("ipAdress eth1 != null");
                } else {
//                    System.out.println("ipAdress eth1 == null");0
                }
            } catch (IOException ex) {
                Logger.getLogger(HostObject.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    };
    EventHandler<ActionEvent> itemConsoleEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String ipAddress;
                PingDialogFxmlView pingDialog = new PingDialogFxmlView();
                ipAddress = pingDialog.showConsoleDialog("Ping");
                String messagePing = "";
                if (ipAddress != null) {
                    
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(HostObject.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
    };
    
    /**
     * METHODES DE TEST
     * @return 
     */
    private void afficheListInterface(List<InterfaceObject> listInterface) {
        for (InterfaceObject interfaceIterator : listInterface) {
            System.out.println("\n---------------------------------");
            System.out.println("List interface : ");
            System.out.println("Routeur name : " + interfaceIterator.getRouteurName());
            System.out.println("Interface name : " + interfaceIterator.getInterfaceName());
            System.out.println("Interface status : " + interfaceIterator.getInterfaceStatus());
                    
        }
    }  
    private void afficheNewConfig (InterfaceObject interfaceObj) {
        System.out.println("\n---------------------------------");
            System.out.println("NEW CONFIG : ");
            System.out.println("Routeur name : " + interfaceObj.getRouteurName());
            System.out.println("Interface name : " + interfaceObj.getInterfaceName());
            System.out.println("Interface status : " + interfaceObj.getInterfaceStatus());
            System.out.println("IP Adress : " + interfaceObj.getIpAddressObj().getIpAddress());
            System.out.println("IP Mask : " + interfaceObj.getIpAddressObj().getIpMask());
            
            
    }

    public boolean addInterfaces() {
        return false;
    }

    public DoubleProperty getOrgTranslateX() {
        return interfacePositionX;
    }

    public void setOrgTranslateX(DoubleProperty positionX) {
        this.interfacePositionX = positionX;
    }

    public DoubleProperty getOrgTranslateY() {
        return interfacePositionY;
    }

    public void setOrgTranslateY(DoubleProperty positionY) {
        this.interfacePositionY = positionY;
    }
}
