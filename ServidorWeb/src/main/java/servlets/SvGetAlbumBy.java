/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import controladores.Fabrica;
import controladores.iSistema;
import datatypes.DataAlbum;
import datatypes.DataTema;
import excepciones.AlbumNoExisteException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Album;
import logica.Tema;

@WebServlet(name = "SvGetAlbumBy", urlPatterns = {"/SvGetAlbumBy"})
@MultipartConfig
public class SvGetAlbumBy extends HttpServlet {
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

            String action = request.getParameter("action");
            String value = request.getParameter("value");
            System.out.println("accion:" + action);
            System.out.println("valor: "+value);
            ObjectMapper mapper = new ObjectMapper();
    try {
        if ("Genero".equals(action)) {
            DataAlbum[] albumsGen = sys.getAlbumsByGenero(value);
            String albumsGenJson = mapper.writeValueAsString(albumsGen);
            response.getWriter().write(albumsGenJson);
            
            
        } else if ("Artista".equals(action)) {
            DataAlbum[] albumsArt = sys.getAlbumsByArtista(value);
            String albumsArtJson = mapper.writeValueAsString(albumsArt);
            response.getWriter().write(albumsArtJson);
            
        }
            return; 
        } catch (AlbumNoExisteException ex) {
            Logger.getLogger(SvGetAlbumBy.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
