/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.object;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RANAIVOSON
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "host")
public class HostObjectWrapper {
    
    private String hostName = "none";
    
    @XmlElement(name = "interface")
    private List<InterfaceObjects> interfaceObjects = new ArrayList<>();

    public List<InterfaceObjects> getInterfaceObjects() {
        return interfaceObjects;
    }
    
    public void setInterfaceObjects(List<InterfaceObjects> interfaceObjects) {
        this.interfaceObjects = interfaceObjects;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    
    
    
}
