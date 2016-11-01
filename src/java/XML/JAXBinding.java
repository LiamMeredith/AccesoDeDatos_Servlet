/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import generated.Alumno;
import java.io.File;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Liam-Portatil
 */
class JAXBinding {
    
    /**
     * Method that creats objects based on a file and the xsd implemented by the
     * programmer with JAXB Binding
     *
     * @param f
     * @return
     */
    public Alumno xmlToObject(File f) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Alumno.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            //Do the job, return object
            Alumno p = (Alumno) jaxbUnmarshaller.unmarshal(f);
            return p;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Writes Array List to XML
     * @param pers
     * @param rf 
     */
    public String objectToXml(Alumno pers) {
        StringWriter sw = new StringWriter();
        try {
            
            JAXBContext jaxbContext = JAXBContext.newInstance(Alumno.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Optional
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Do the job
            jaxbMarshaller.marshal(pers, sw);
            //Optional: output pretty printed
            //jaxbMarshaller.marshal(cds, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}


