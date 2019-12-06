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
@XmlRootElement(name = "interfaces")
@XmlAccessorType(XmlAccessType.FIELD)
public class InterfaceObjects {
    
    @XmlElement(name = "interface")
    private List<InterfaceObject> interfaceObjects = new ArrayList<>();

    public List<InterfaceObject> getInterfaceObjects() {
        return interfaceObjects;
    }
    
    public void setInterfaceObjects(List<InterfaceObject> interfaceObjects) {
        this.interfaceObjects = interfaceObjects;
    }
   
    
}
