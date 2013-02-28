package ru.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import ru.ejb.PropertyFacadeLocal;
import ru.entities.Property;


public class DownloadServlet extends HttpServlet {

    @EJB
    PropertyFacadeLocal propsWorker;
    
    private final static Logger LOGGER = Logger.getLogger(DownloadServlet.class.getCanonicalName());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Content-Disposition", "attachment; filename=Object.xml");
        ServletOutputStream stream = response.getOutputStream();
        
        Property props = propsWorker.find(Long.valueOf(request.getParameter("id")));

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Property.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(props, stream);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
                    new Object[]{e.getMessage()});
        } finally {
            stream.close();
        }        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
