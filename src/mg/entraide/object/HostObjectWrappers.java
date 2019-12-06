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
@XmlRootElement(name = "hosts")
public class HostObjectWrappers {
    
    @XmlElement(name = "host")
    private List<HostObjectWrapper> hostObjectWrappers = new ArrayList<>();
    
    @XmlElement(name = "line")
    private List<LineObjectWrapper> lineObjectWrappers = new ArrayList<>();

    public List<HostObjectWrapper> getHostObjectWrappers() {
        return hostObjectWrappers;
    }

    public void setHostObjectWrappers(List<HostObjectWrapper> hostObjectWrappers) {
        this.hostObjectWrappers = hostObjectWrappers;
    }

    public List<LineObjectWrapper> getLineObjectWrappers() {
        return lineObjectWrappers;
    }

    public void setLineObjectWrappers(List<LineObjectWrapper> lineObjectWrappers) {
        this.lineObjectWrappers = lineObjectWrappers;
    }
    
    
    
}
