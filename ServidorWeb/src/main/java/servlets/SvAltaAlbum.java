
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controladores.iSistema;
import controladores.Fabrica;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import rutaP.rutaProyecto;




@WebServlet(name = "SvAltaAlbum", urlPatterns = {"/SvAltaAlbum"})
@MultipartConfig
public class SvAltaAlbum extends HttpServlet {
iSistema sys = new Fabrica().getSistema();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    
    }

  @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    processRequest(request, response);
    
    HttpSession session = request.getSession();
    String genero = request.getParameter("nombreG");
    String album = request.getParameter("nombreA");
    String nickname = session.getAttribute("nickname").toString();
    String anioStr = request.getParameter("anio");

    // Manejo de errores para la obtención del año
    
        if ("".equals(anioStr)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La posicion es requerida.");
            return; 
        }
        
    String action = request.getParameter("action");
    // Verificación del nombre del álbum
    if ("checkAlbumName".equals(action)) {
        String value = request.getParameter("value");
        try {
            boolean exists = sys.verificaAlbum(value, nickname);
            response.getWriter().write(exists ? "exists" : "available");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor.");
        }
        return;
    }
    Integer anio = Integer.valueOf(anioStr); 


    // Carga de la imagen
    String uploadPath = null;
    String filePath = null;
    String rutaimg = null;
    Part filePart = request.getPart("imagen");

    if (filePart != null && filePart.getSize() > 0) {
        uploadPath = rutaProyecto.getRuta() + "imagenes_album";   

        String contentType = filePart.getContentType();
        String[] contentParts = contentType.split("/");
        String imageFormat = contentParts[1];
        String fileName = album + "." + imageFormat;
        filePath = uploadPath + File.separator + fileName;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        rutaimg = "imagenes_albumes/" + fileName;

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar la imagen.");
            return;
        }
    } else {
        System.out.println("No se ha subido ninguna imagen o el archivo está vacío");
    }
    
    // ALTA ALBUM
    try {
        boolean exist = sys.verificaAlbum(album, nickname);
        if (!exist) {
            sys.cargarDatosAlbum(album, nickname, anio, rutaimg);
            sys.altaAlbum();
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El álbum ya existe.");
        }
    } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al crear el álbum.");
    }
    
    session.removeAttribute("temas");
    session.removeAttribute("temasPos");
    session.removeAttribute("generos");
}
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
