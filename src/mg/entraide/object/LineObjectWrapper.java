/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.object;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**********************
 * @author RANAIVOSON *
 **********************/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "line")
public class LineObjectWrapper {
    public String hostNameBegin;
    public String hostNameEnd;
    public String interfaceNameBegin;
    public String interfaceNameEnd;
    public Double startX;
    public Double startY;
    public Double endX;
    public Double endY;
    
    
    public String getHostNameBegin() {
        return hostNameBegin;
    }

    public void setHostNameBegin(String hostNameBegin) {
        this.hostNameBegin = hostNameBegin;
    }

    public String getHostNameEnd() {
        return hostNameEnd;
    }

    public void setHostNameEnd(String hostNameEnd) {
        this.hostNameEnd = hostNameEnd;
    }

    public Double getStartX() {
        return startX;
    }

    public void setStartX(Double startX) {
        this.startX = startX;
    }

    public Double getStartY() {
        return startY;
    }

    public void setStartY(Double startY) {
        this.startY = startY;
    }

    public Double getEndX() {
        return endX;
    }

    public void setEndX(Double endX) {
        this.endX = endX;
    }

    public Double getEndY() {
        return endY;
    }

    public void setEndY(Double endY) {
        this.endY = endY;
    }

    public String getInterfaceNameBegin() {
        return interfaceNameBegin;
    }

    public void setInterfaceNameBegin(String interfaceNameBegin) {
        this.interfaceNameBegin = interfaceNameBegin;
    }

    public String getInterfaceNameEnd() {
        return interfaceNameEnd;
    }

    public void setInterfaceNameEnd(String interfaceNameEnd) {
        this.interfaceNameEnd = interfaceNameEnd;
    }
    
    
    
    
    
}
