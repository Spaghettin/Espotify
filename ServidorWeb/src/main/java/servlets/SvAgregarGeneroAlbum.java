
package servlets;

import controladores.Fabrica;
import controladores.iSistema;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "SvAgregarGeneroAlbum", urlPatterns = {"/SvAgregarGeneroAlbum"})
@MultipartConfig
public class SvAgregarGeneroAlbum extends HttpServlet {
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

        Set<String> generos = (Set<String>) session.getAttribute("generos");
        if (generos == null) {
            generos = new HashSet<>(); // Si no existe, crear una nueva
        }
        
        String genero = request.getParameter("nombreG");
        String album = request.getParameter("nombreA");
        String action = request.getParameter("action");

        if ("checkGenre".equals(action)) {
            String value = request.getParameter("value");
            try {
                boolean exists = false;
                    if(generos.contains(value)){
                        exists = true;
                    }else{
                        exists = false;
                        generos.add(genero);
                    }
                response.getWriter().write(exists ? "exists" : "available");
            } catch (Exception ex) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor.");
            }
            return;
        }
        
        String nickname = session.getAttribute("nickname").toString();

        if (!sys.verificaAlbum(album, nickname)){
            sys.addGeneroAlbum(genero);
            response.setStatus(202);
            session.setAttribute("generos", generos);
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
