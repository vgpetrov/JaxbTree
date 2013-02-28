package ru.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import ru.ejb.PropertyFacadeLocal;
import ru.entities.Property;

@MultipartConfig
public class UploadServlet extends HttpServlet {
    
    @EJB
    PropertyFacadeLocal propsWorker;
    
    private final static Logger LOGGER = Logger.getLogger(UploadServlet.class.getCanonicalName());
    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
    private Property parent;
    
    private void parentFind(Property properties) {
        if (properties.getProperty() != null) {
            parent = properties;
            for(Property prop : properties.getProperty()) {                
                prop.setParentPproperty(parent);
                parentFind(prop);
            }
        } else {
            properties.setParentPproperty(parent);
        }
    }
  
    protected void processRequest(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setContentType("text/plain;charset=UTF-8");

        String path = "/tmp";
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        PrintWriter writer = response.getWriter();

        BufferedReader br = null;
    
        try {
            out = new FileOutputStream(new File(path + File.separator + fileName));
            filecontent = filePart.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
                    new Object[]{fileName, path});

            br = new BufferedReader(new FileReader(path + File.separator + fileName));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                writer.println(sCurrentLine);
            }

            try {
                File file = new File(path + File.separator + fileName);
                JAXBContext jaxbContext = JAXBContext.newInstance(Property.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                Property properties = (Property) jaxbUnmarshaller.unmarshal(file);
                
                parentFind(properties);
                propsWorker.create(properties);

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Propblems in JAXB unmarshalling. Error: {0}", 
                    new Object[]{e.getMessage()});
            }

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
                    new Object[]{e.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
