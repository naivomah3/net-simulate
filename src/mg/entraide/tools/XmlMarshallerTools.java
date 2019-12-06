/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import mg.entraide.object.HostObjectWrappers;

/**
 *
 * @author RANAIVOSON
 */
public class XmlMarshallerTools {

//    MainFxmlController mainFxmlController = new MainFxmlController();

    //method to marshal XML file
    public static void marshalingXml(HostObjectWrappers hostObjectWrappersParam, File selectedFile) throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(HostObjectWrappers.class);
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(selectedFile));  
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(hostObjectWrappersParam, writer);
        writer.close();
    }
    
    //methode to unmarshall XML file 
    public static HostObjectWrappers unmarshallerXml (File selectedFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(HostObjectWrappers.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (HostObjectWrappers) jaxbUnmarshaller.unmarshal(selectedFile);
    }
}
