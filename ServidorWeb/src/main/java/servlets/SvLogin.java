/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.Sistema;
import controladores.iSistema;
import controladores.Fabrica;
import datatypes.DataUsuario;

import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Artista;
import logica.Cliente;
import logica.Usuario;


/**
 *
 * @author dokgo
 */
@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {
    
    @EJB
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
        //processRequest(request, response);
        
        String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    response.setContentType("text/plain"); // texto plano
    response.setCharacterEncoding("UTF-8");
    
    try {
        Usuario usuario = null;
        
        if (sys.existeMail(username)) {
            System.out.println("Buscando usuario por correo: " + username);
            usuario = sys.obtenerUsuarioMail(username);
        } else {
            System.out.println("Buscando usuario por nickname: " + username);
            usuario = sys.obtenerUsuario(username);
        }
        
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getNickname());
            if (verificarPassword(password, usuario.getPassword())) {
                HttpSession session = request.getSession();
                
                DataUsuario dataUsuario;
                if(usuario instanceof Artista){
                    Artista artista = (Artista) usuario;
                    dataUsuario = new DataUsuario(
                        artista.getNickname(),
                        artista.getNombre(),
                        artista.getApellido(),
                        artista.getEmail(),
                        artista.getFechaNac(),
                        artista.getImagenPerfil(),
                        artista.getBiografia(),
                        artista.getSitioWeb(),
                        artista.getTipo()
                    );
                }else{
                    Cliente cliente = (Cliente) usuario;
                    dataUsuario = new DataUsuario(
                        cliente.getNickname(),
                        cliente.getNombre(),
                        cliente.getApellido(),
                        cliente.getEmail(),
                        cliente.getFechaNac(),
                        cliente.getImagenPerfil(),
                        cliente.getTipo()
                    ); 
                }
     
                
                session.setAttribute("dataUsuario", dataUsuario);
                session.setAttribute("nickname", usuario.getNickname());
                session.setAttribute("sistema", sys);
                
              
              response.getWriter().write("success");
                
                
            } else {
                
            }
            
        } else {
            System.out.println("entra al else de si el usuario es null");


        }
    } catch (Exception e) {
        System.out.println("Excepción: " + e.getMessage());
        e.printStackTrace();
    }
}

    private boolean verificarPassword(String inputPassword, String storedPassword) {
        boolean ismatch;
        ismatch =  inputPassword.equals(storedPassword);
        System.out.println("Verificando contraseña: " + inputPassword + " == " + storedPassword + " ? " + ismatch);
        return ismatch;
    }
        
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
