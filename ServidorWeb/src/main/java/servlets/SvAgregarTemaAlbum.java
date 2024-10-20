
package servlets;

import controladores.Fabrica;
import controladores.iSistema;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "SvAgregarTemaAlbum", urlPatterns = {"/SvAgregarTemaAlbum"})
@MultipartConfig
public class SvAgregarTemaAlbum extends HttpServlet {
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

    String nombreT = request.getParameter("nombreT");
    String duracion = request.getParameter("duracion");
    String posStr = request.getParameter("pos"); 
    String dirW = request.getParameter("direccionWeb");
    String archivo = request.getParameter("archivo");
    String album = request.getParameter("nombreA");
    HttpSession session = request.getSession();

    Set<String> temas = (Set<String>) session.getAttribute("temas");
        if (temas == null) {
            temas = new HashSet<>(); // Si no existe, crear una nueva
        }
        
    Set<String> temasPos = (Set<String>) session.getAttribute("temasPos");
        if (temasPos == null) {
            temasPos = new HashSet<>(); // Si no existe, crear una nueva
        }
    
    String action = request.getParameter("action");
    if ("".equals(posStr)) {
           response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La posicion es requerida.");
            return; 
        }
    
    try {
        if ("checkTema".equals(action) || "checkTemaPos".equals(action)) {
            String value = request.getParameter("value");
            boolean exists = false;

            if ("checkTema".equals(action)) {
                if (temas.contains(value)) {
                    exists = true;
                } else {
                    temas.add(nombreT); // Solo se agrega si no existe
                }
            } else if ("checkTemaPos".equals(action)) {
               
                if (temasPos.contains(posStr)) {
                    exists = true;
                } else {
                    temasPos.add(posStr); // Solo se agrega si no existe
                }
            }

            response.getWriter().write(exists ? "exists" : "available");
            return; 
        }

        Integer pos = Integer.valueOf(posStr);
        
        
        String nickname = session.getAttribute("nickname").toString();
        // Verificar si el álbum existe
        if (!sys.verificaAlbum(album, nickname)) {
            sys.altaTema(nombreT, duracion, pos, dirW, archivo);
            session.setAttribute("temas", temas);
            session.setAttribute("temasPos", temasPos);
            response.setStatus(HttpServletResponse.SC_OK); // Cambiar a OK al éxito
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    } catch (NumberFormatException e) {
        // Manejo de la excepción si hay un error al convertir a número
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error en los datos proporcionados.");
    } catch (Exception ex) {
        // Manejo de otras excepciones
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor.");
    }
}

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
