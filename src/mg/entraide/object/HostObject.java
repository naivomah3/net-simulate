/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.object;

import java.util.ArrayList;
import java.util.List;
import mg.entraide.controller.HostObjectController;
import mg.entraide.controller.MainFxmlController;

/**
 *
 * @author RANAIVOSON
 */
public class HostObject {

    private MainFxmlController mainFxmlController = null;
    private HostObjectController hostObjectController = null;
    private String hostName;
    private Double interfacePositionX;
    private Double interfacePositionY;
    private List<InterfaceObject> interfaceObjects = new ArrayList<>();
    private InterfaceObject eth0 = null;
    private InterfaceObject eth1 = null;
    
    public HostObject (
            MainFxmlController mainFxml,
            String hostNameParam,
            Double interfacePositionXParam,
            Double interfacePositionYParam,
            InterfaceObject _eth0,
            InterfaceObject _eth1) {
        mainFxmlController = mainFxml;
        hostName = hostNameParam;
        interfacePositionX = interfacePositionXParam;
        interfacePositionY = interfacePositionYParam;
        eth0 = _eth0;
        eth1 = _eth1;
        
    }

    public HostObject(
            MainFxmlController mainFxml,
            String hostNameParam,
            Double interfacePositionXParam,
            Double interfacePositionYParam,
            List<InterfaceObject> interfaceObjParam) {
        hostName = hostNameParam;
        interfacePositionX = interfacePositionXParam;
        interfacePositionY = interfacePositionYParam;
        interfaceObjects = interfaceObjParam;

    }

    public HostObject(
            MainFxmlController mainFxml,
            String hostNameP,
            Double interfacePositionXP,
            Double interfacePositionYP) {
        this.mainFxmlController = mainFxml;
        this.hostName = hostNameP;
        this.interfacePositionX = interfacePositionXP;
        this.interfacePositionY = interfacePositionYP;
    }

    public void addImageToMainPanel(boolean isLoader) {
        if (!isLoader) {
            eth0 = new InterfaceObject(0, hostName, "eth0", interfacePositionX, interfacePositionY, new IpAdressObject());
            eth1 = new InterfaceObject(0, hostName, "eth1", interfacePositionX, interfacePositionY, new IpAdressObject());
        }
        
        interfaceObjects.add(eth0);
        interfaceObjects.add(eth1);
        mainFxmlController.listInterfaceObjectMainPanel.add(eth0);
        mainFxmlController.listInterfaceObjectMainPanel.add(eth1);
        hostObjectController = new HostObjectController(mainFxmlController);
        mainFxmlController.getPaneScreen().getChildren().add(hostObjectController.addImage(hostName, interfacePositionX, interfacePositionY, eth0, eth1));
    }

    public void addLineToMainPanel(String hostNameBeginP, String hostNameEndP, String interfaceNameBeginP, String interfaceNameEndP, Double startXP, Double startYP, Double endXP, Double endYP) {
        LineObject lineObj = hostObjectController.addLine(hostNameBeginP,hostNameEndP, interfaceNameBeginP, interfaceNameEndP, startXP, startYP, endXP, endYP); 
        mainFxmlController.getPaneScreen().getChildren().add(lineObj);
        mainFxmlController.listLineObjectsMainPanel.add(lineObj);
        lineObj.toBack();
    }
    /**
     * ********************
     * GETTERS AND SETTERS*
     * ********************
     */
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Double getInterfacePositionX() {
        return interfacePositionX;
    }

    public void setInterfacePositionX(Double interfacePositionX) {
        this.interfacePositionX = interfacePositionX;
    }

    public Double getInterfacePositionY() {
        return interfacePositionY;
    }

    public void setInterfacePositionY(Double interfacePositionY) {
        this.interfacePositionY = interfacePositionY;
    }

    public List<InterfaceObject> getInterfaceObj() {
        return interfaceObjects;
    }

    public void setInterfaceObj(List<InterfaceObject> interfaceObj) {
        this.interfaceObjects = interfaceObj;
    }

}
