/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.Fabrica;
import controladores.Sistema;
import controladores.iSistema;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import rutaP.rutaProyecto;




/**
 *
 * @author dokgo
 */

@WebServlet(name = "SvRegistroUsuario", urlPatterns = {"/SvRegistroUsuario"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class SvRegistroUsuario extends HttpServlet {

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
        
        if ("checkNickname".equals(action) || "checkEmail".equals(action)) {
            String value = request.getParameter("value");
            try {
                boolean exists = false;
                if ("checkNickname".equals(action)) {
                    exists = sys.existeUsuario(value);
                } else if ("checkEmail".equals(action)) {
                    exists = sys.existeMail(value);
                }

                response.getWriter().write(exists ? "exists" : "available");
            } catch (Exception ex) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor.");
            }
            return;
        }
        
        
        System.out.println("llega a la verificacion de mail y nick");
        String email = request.getParameter("email");
        String nick = request.getParameter("nickname");
        System.out.println(nick + email);
        try{
            if(sys.existeMail(email)){
                response.sendError(HttpServletResponse.SC_CONFLICT, "email");
                return;
            }
            if(sys.existeUsuario(nick)){
                response.sendError(HttpServletResponse.SC_CONFLICT, "nick");
                return;
            }
        }catch(Exception ex){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor.");
        }
        
        System.out.println("Pasa la verificacion de mail y nick");
        
        String tipo = request.getParameter("tipo_usuario");
        String password = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String fechaStr = request.getParameter("fecha");
        
        String biografia = null, sitioWeb = null;
        
        System.out.println("llega  ala fecha");
        LocalDate fecha = LocalDate.parse(fechaStr);
        switch (tipo){
            case "cliente":
                tipo = "cliente";
                break;
            case "artista":
                tipo = "artista";
                biografia = request.getParameter("biografia");
                sitioWeb = request.getParameter("sitioWeb");
                break;
            default:
                response.sendError(400, "tipo incorrecto de usuario");
                break;
        }
        
        System.out.println("Pasa la fecha");
        
        String confPassword = request.getParameter("conf_password");
        if(!password.equals(confPassword)){
            response.sendRedirect("registro.jsp?err=password_mismatch");
            return;
        }
        
        
        System.out.println("Lllega a la imagen");
        
        String uploadPath = null;
        String filePath = null;
        String rutaimg = null;
        Part filePart = request.getPart("imagen");
        if (filePart != null && filePart.getSize() > 0) {
            uploadPath = rutaProyecto.getRuta() + "imagenes_perfil";   
            //uploadPath = getServletContext().getRealPath("") + File.separator + "imagenes_perfil";

            String fileName = nick;

            String contentType = filePart.getContentType();
            String[] contentParts = contentType.split("/");
            String imageFormat = contentParts[1];
            System.out.println("entra al if de la imagen:" + filePart);

            fileName = fileName + "." + imageFormat;

            filePath = uploadPath + File.separator + fileName;

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            rutaimg = "imagenes_perfil/" + nick + "." + imageFormat;
            System.out.println("Llega al try de la imagen: " + fileName + filePath);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("paso el try de la imagen");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se ha subido ninguna imagen o el archivo esta vacio");
        }
        
        
        
        System.out.println("Pasa la imagen y estoy afuera del try de altaperfil");
        
        try {
            System.out.println("AltaPerfil iniciado");
            sys.altaPerfil(nick, nombre, apellido, password, email, fecha, rutaimg, biografia, sitioWeb, tipo);
            System.out.println("AltaPerfil finalizado");
            
        } catch (Exception ex) {
            Logger.getLogger(SvRegistroUsuario.class.getName()).log(Level.SEVERE, null, ex);

            return;
        }
        response.setStatus(202);
        response.sendRedirect("index.jsp");
        System.out.println("correcto");
    }
    
    

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
