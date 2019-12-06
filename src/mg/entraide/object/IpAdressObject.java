/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.object;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RANAIVOSON
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ipAddressObj")
public class IpAdressObject {
    private String ipAddress = null;
    private String ipMask = null;
    private String ipNetwork = null;
    private String ipGateway = null;
    
    public IpAdressObject() {
    }

    public IpAdressObject(String ipAddressParam, String ipMaskParam, String ipNetworkParam, String ipGatewayParam) {
        this.ipAddress = ipAddressParam;
        this.ipMask = ipMaskParam;
        this.ipNetwork = ipNetworkParam;
        this.ipGateway = ipGatewayParam;
    }

    
    public void setAddress (String ipAddressParam, String ipMaskParam, String ipNetworkParam, String ipGatewayParam) {
        this.setIpAddress(ipAddressParam);
        this.setIpMask(ipMaskParam);
        this.setIpNetwork(ipNetworkParam);
        this.setIpGateway(ipGatewayParam);
    }
    
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpMask() {
        return ipMask;
    }

    public void setIpMask(String ipMask) {
        this.ipMask = ipMask;
    }

    public String getIpNetwork() {
        return ipNetwork;
    }

    public void setIpNetwork(String ipNetwork) {
        this.ipNetwork = ipNetwork;
    }

    public String getIpGateway() {
        return ipGateway;
    }

    public void setIpGateway(String ipGateway) {
        this.ipGateway = ipGateway;
    }
    
    
    
            
}
