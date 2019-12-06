/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.object;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author RANAIVOSON
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "interface")
public class InterfaceObject {
    private String interfaceName = "none";
    private String interfacePosition = "none";
    private int interfaceStatus = 0;/*0:disconnected/ 1:waiting/ 2:connected */
    private double positionHostX;
    private double positionHostY;
    public String routeurName; 
    
    
    @XmlElement(name = "ipAddress", type = IpAdressObject.class)
    private IpAdressObject ipAddressObj;
    
    public InterfaceObject() {
    }
    
    public InterfaceObject(int interfaceStatusParam, String routeurNameParam, String interfaceNameParam, double positionHostXParam, double positionHostYParam, IpAdressObject ipAddressParam) {
        interfaceName = interfaceNameParam;
        interfaceStatus = interfaceStatusParam;
        positionHostX = positionHostXParam;
        positionHostY = positionHostYParam;
        routeurName = routeurNameParam;
        ipAddressObj = ipAddressParam;
    }

    public String getInterfacePosition() {
        return interfacePosition;
    }

    public void setInterfacePosition(String interfacePosition) {
        this.interfacePosition = interfacePosition;
    }

    
    public IpAdressObject getIpAddressObj() {
        return ipAddressObj;
    }

    public void setIpAddressObj(IpAdressObject ipAddressObj) {
        this.ipAddressObj = ipAddressObj;
    }


    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public double getPositionHostX() {
        return positionHostX;
    }

    public void setPositionHostX(double positionHostX) {
        this.positionHostX = positionHostX;
    }

    public double getPositionHostY() {
        return positionHostY;
    }

    public void setPositionHostY(double positionHostY) {
        this.positionHostY = positionHostY;
    }

    public String getRouteurName() {
        return routeurName;
    }

    public void setRouteurName(String routeurName) {
        this.routeurName = routeurName;
    }

    public int getInterfaceStatus() {
        return interfaceStatus;
    }

    public void setInterfaceStatus(int interfaceStatus) {
        this.interfaceStatus = interfaceStatus;
    }
    
    @Override
    public boolean equals(Object object){
        InterfaceObject host = (InterfaceObject) object;
        return this.interfaceName.equals(host.getInterfaceName());
    }
    
    
    
}
