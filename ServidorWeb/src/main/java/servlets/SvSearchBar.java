
package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import controladores.Fabrica;
import controladores.iSistema;
import datatypes.DataAlbum;
import datatypes.DataLista;
import datatypes.DataTema;
import datatypes.DataUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "SvSearchBar", urlPatterns = {"/SvSearchBar"})
@MultipartConfig
public class SvSearchBar extends HttpServlet {
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
        String action = request.getParameter("action");
        String value = request.getParameter("value");
        try {
            if("checkSearch".equals(action)){
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                    List<DataTema> DTTemas = sys.getDataTemasAjax(value);
                    List<DataLista> DTListasP = sys.getDataListasReproduccionAjax(value, "part");
                    //List<DataLista> DTListasPPublicas new ArrayList<>();
                    List<DataLista> DTListasD = sys.getDataListasReproduccionAjax(value, "def");
                    List<DataUsuario> DTArtista = sys.getDataUsuariosAjax(value, "art");
                    List<DataUsuario> DTCliente = sys.getDataUsuariosAjax(value, "cli");
                    List<DataAlbum> DTAlbums = sys.getDataAlbumsAjax(value);
                    
//                    for(DataLista dtLista : DTListasP){
//                        if(dtLista.esPublica()){
//                            DTListasPPublicas.add(dtLista);
//                        }
//                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put("temas", DTTemas);
                    data.put("listasParticulares", DTListasP);
                    data.put("listasPorDefecto", DTListasD);
                    data.put("artistas", DTArtista);
                    data.put("clientes", DTCliente);
                    data.put("albums", DTAlbums);
                    System.out.println("Datos a enviar: " + data.toString());
                    String jsonResponse = mapper.writeValueAsString(data);

                    response.setContentType("application/json");
                    response.getWriter().write(jsonResponse);

                }
        } catch (Exception e) {
            e.printStackTrace(); 
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
        }
        
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
