
package presentacionGUI;

import controladores.Fabrica;
import controladores.iSistema;
import excepciones.GeneroRepetidoException;
import excepciones.UsuarioRepetidoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import persistencia.iControladorPersistencia;

/**
 *
 * @author dokgo
 */
public class Principal extends javax.swing.JFrame {
    iSistema sys;
    iControladorPersistencia cpu;
    
    private boolean datosCargados = false;
    
    public Principal() {
        sys = new Fabrica().getSistema();
        //cpu = new Fabrica().getControladorPersistencia();
        initComponents();
        this.setTitle("Espotify");
        this.setSize(900, 800);        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuSistema = new javax.swing.JMenu();
        itemCargarDatos = new javax.swing.JMenuItem();
        menuRegistro = new javax.swing.JMenu();
        itemAltaPerfil = new javax.swing.JMenuItem();
        itemAltaGenero = new javax.swing.JMenuItem();
        itemAltaAlbum = new javax.swing.JMenuItem();
        itemCrearLista = new javax.swing.JMenuItem();
        itemAgregarTemaLista = new javax.swing.JMenuItem();
        itemQuitarTemaLista = new javax.swing.JMenuItem();
        itemPublicarLista = new javax.swing.JMenuItem();
        itemSeguirUsuario = new javax.swing.JMenuItem();
        itemDejarDeSeguirUsuario = new javax.swing.JMenuItem();
        itemGuardarTemaListaAlbum = new javax.swing.JMenuItem();
        itemEliminarTemaListaAlbum = new javax.swing.JMenuItem();
        menuConsultas = new javax.swing.JMenu();
        itemConsultaPerfilCliente = new javax.swing.JMenuItem();
        itemConsultaPerfilArtista = new javax.swing.JMenuItem();
        itemConsultaAlbum = new javax.swing.JMenuItem();
        itemConsultaListaReproduccion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(0, 549));

        jPanel1.setPreferredSize(new java.awt.Dimension(900, 800));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 678, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 524, Short.MAX_VALUE)
        );

        menuSistema.setText("Sistema");

        itemCargarDatos.setText("Cargar Datos de Prueba");
        itemCargarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCargarDatosActionPerformed(evt);
            }
        });
        menuSistema.add(itemCargarDatos);

        jMenuBar1.add(menuSistema);

        menuRegistro.setText("Registro");
        menuRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRegistroActionPerformed(evt);
            }
        });

        itemAltaPerfil.setText("Alta de Perfil (Cliente / Artista)");
        itemAltaPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAltaPerfilActionPerformed(evt);
            }
        });
        menuRegistro.add(itemAltaPerfil);

        itemAltaGenero.setText("Alta de Genero");
        itemAltaGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAltaGeneroActionPerformed(evt);
            }
        });
        menuRegistro.add(itemAltaGenero);

        itemAltaAlbum.setText("Alta de Album");
        itemAltaAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAltaAlbumActionPerformed(evt);
            }
        });
        menuRegistro.add(itemAltaAlbum);

        itemCrearLista.setText("Crear Lista de Reproduccion");
        itemCrearLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCrearListaActionPerformed(evt);
            }
        });
        menuRegistro.add(itemCrearLista);

        itemAgregarTemaLista.setText("Agregar Tema a Lista");
        itemAgregarTemaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAgregarTemaListaActionPerformed(evt);
            }
        });
        menuRegistro.add(itemAgregarTemaLista);

        itemQuitarTemaLista.setText("Quitar Tema de Lista");
        itemQuitarTemaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemQuitarTemaListaActionPerformed(evt);
            }
        });
        menuRegistro.add(itemQuitarTemaLista);

        itemPublicarLista.setText("Publicar Lista");
        itemPublicarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPublicarListaActionPerformed(evt);
            }
        });
        menuRegistro.add(itemPublicarLista);

        itemSeguirUsuario.setText("Seguir Usuario (Cliente / Artista)");
        itemSeguirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSeguirUsuarioActionPerformed(evt);
            }
        });
        menuRegistro.add(itemSeguirUsuario);

        itemDejarDeSeguirUsuario.setText("Dejar de Seguir a Usuario (Cliente / Artista)");
        itemDejarDeSeguirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDejarDeSeguirUsuarioActionPerformed(evt);
            }
        });
        menuRegistro.add(itemDejarDeSeguirUsuario);

        itemGuardarTemaListaAlbum.setText("Guardar Tema / Lista / Album");
        itemGuardarTemaListaAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarTemaListaAlbumActionPerformed(evt);
            }
        });
        menuRegistro.add(itemGuardarTemaListaAlbum);

        itemEliminarTemaListaAlbum.setText("Eliminar Tema / Lista Album");
        itemEliminarTemaListaAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEliminarTemaListaAlbumActionPerformed(evt);
            }
        });
        menuRegistro.add(itemEliminarTemaListaAlbum);

        jMenuBar1.add(menuRegistro);

        menuConsultas.setText("Consultas");

        itemConsultaPerfilCliente.setText("Consulta de Perfil de Cliente");
        itemConsultaPerfilCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemConsultaPerfilClienteActionPerformed(evt);
            }
        });
        menuConsultas.add(itemConsultaPerfilCliente);

        itemConsultaPerfilArtista.setText("Consulta de Perfil de Artista");
        itemConsultaPerfilArtista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemConsultaPerfilArtistaActionPerformed(evt);
            }
        });
        menuConsultas.add(itemConsultaPerfilArtista);

        itemConsultaAlbum.setText("Consulta de Album");
        itemConsultaAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemConsultaAlbumActionPerformed(evt);
            }
        });
        menuConsultas.add(itemConsultaAlbum);

        itemConsultaListaReproduccion.setText("Consulta de Lista de Reproduccion");
        itemConsultaListaReproduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemConsultaListaReproduccionActionPerformed(evt);
            }
        });
        menuConsultas.add(itemConsultaListaReproduccion);

        jMenuBar1.add(menuConsultas);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemConsultaPerfilClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultaPerfilClienteActionPerformed
        ConsultaPerfilCliente consultaCli = new ConsultaPerfilCliente();
        jPanel1.add(consultaCli);
        consultaCli.setVisible(true);
        try{
            consultaCli.setSize(802,730);
        }catch(Exception e){}
    }//GEN-LAST:event_itemConsultaPerfilClienteActionPerformed

    private void itemAltaAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAltaAlbumActionPerformed
        AltaAlbum registroA = new AltaAlbum();
        jPanel1.add(registroA);
        registroA.setVisible(true);
        registroA.setSize(691,590);
    }//GEN-LAST:event_itemAltaAlbumActionPerformed

    private void itemAltaPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAltaPerfilActionPerformed
        AltaPerfil registro = new AltaPerfil();
        jPanel1.add(registro);
        registro.setVisible(true);
    }//GEN-LAST:event_itemAltaPerfilActionPerformed

    private void menuRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRegistroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuRegistroActionPerformed

    private void itemConsultaPerfilArtistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultaPerfilArtistaActionPerformed
        ConsultaPerfilArtista consultaArt = new ConsultaPerfilArtista();
        jPanel1.add(consultaArt);
        consultaArt.setVisible(true);
        try{
        consultaArt.setSize(822,620);
        }catch(Exception e){}
    }//GEN-LAST:event_itemConsultaPerfilArtistaActionPerformed

    private void itemConsultaAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultaAlbumActionPerformed
        ConsultaAlbum consultaAlb = new ConsultaAlbum();
        jPanel1.add(consultaAlb);
        consultaAlb.setVisible(true);
        try{
            consultaAlb.setSize(802,510);
        }catch(Exception e){}
    }//GEN-LAST:event_itemConsultaAlbumActionPerformed

    private void itemAltaGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAltaGeneroActionPerformed
        AltaGenero altGen = new AltaGenero();
        jPanel1.add(altGen);
        altGen.setVisible(true);
        try{
            altGen.setMaximum(true);
        }catch(Exception e){}
    }//GEN-LAST:event_itemAltaGeneroActionPerformed

    private void itemCrearListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCrearListaActionPerformed
        CrearListaReproduccion clr = new CrearListaReproduccion();
        jPanel1.add(clr);
        clr.setVisible(true);
        try{
            clr.setSize(672, 300);
        }catch(Exception e){}
    }//GEN-LAST:event_itemCrearListaActionPerformed

    private void itemConsultaListaReproduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultaListaReproduccionActionPerformed
        ConsultaListaReproduccion clr = new ConsultaListaReproduccion();
        jPanel1.add(clr);
        clr.setVisible(true);
        try{
            clr.setMaximum(true);
        }catch(Exception e){}
    }//GEN-LAST:event_itemConsultaListaReproduccionActionPerformed

    private void itemSeguirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSeguirUsuarioActionPerformed
        SeguirUsuario clr = new SeguirUsuario();
        jPanel1.add(clr);
        clr.setVisible(true);
        try{
            clr.setSize(439,321);
        }catch(Exception e){}
    }//GEN-LAST:event_itemSeguirUsuarioActionPerformed

    private void itemDejarDeSeguirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemDejarDeSeguirUsuarioActionPerformed
        DejarDeSeguirUsuario clr = new DejarDeSeguirUsuario();
        jPanel1.add(clr);
        clr.setVisible(true);
        try{
            clr.setSize(607,319);
        }catch(Exception e){}
    }//GEN-LAST:event_itemDejarDeSeguirUsuarioActionPerformed

    private void itemGuardarTemaListaAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarTemaListaAlbumActionPerformed
       GuardarTemaListaAlbum clr = new GuardarTemaListaAlbum();
        //jPanel1.add(clr);
        clr.setVisible(true);
        clr.setLocationRelativeTo(null);
    }//GEN-LAST:event_itemGuardarTemaListaAlbumActionPerformed

    private void itemCargarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCargarDatosActionPerformed

        if (datosCargados) {
            JOptionPane.showMessageDialog(this, "Los datos ya han sido cargados previamente.");
            return;
        }
        try {
            sys.cargarDatos();
            
            datosCargados = true;
            System.out.println("Datos de prueba cargados correctamente.");
            JOptionPane.showMessageDialog(this, "Datos de prueba cargados correctamente.");
        } catch (GeneroRepetidoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UsuarioRepetidoException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Se intento cargar usuarios repetidos.");
        }     
    }//GEN-LAST:event_itemCargarDatosActionPerformed

    private void itemQuitarTemaListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemQuitarTemaListaActionPerformed
        QuitarTemaLista clr = new QuitarTemaLista();
        jPanel1.add(clr);
        clr.setVisible(true);
        try{
            clr.setMaximum(true);
        }catch(Exception e){}
    }//GEN-LAST:event_itemQuitarTemaListaActionPerformed

    private void itemEliminarTemaListaAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemEliminarTemaListaAlbumActionPerformed
        EliminarTemaListaAlbum ETMLA = new EliminarTemaListaAlbum();
        ETMLA.setVisible(true);
        ETMLA.setLocationRelativeTo(null);
    }//GEN-LAST:event_itemEliminarTemaListaAlbumActionPerformed

    private void itemAgregarTemaListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAgregarTemaListaActionPerformed
        AgregarTemaALista agregarTema = new AgregarTemaALista();
        agregarTema.setVisible(true);
        agregarTema.setLocationRelativeTo(null);
        agregarTema.setSize(800,700);
    }//GEN-LAST:event_itemAgregarTemaListaActionPerformed

    private void itemPublicarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPublicarListaActionPerformed
        PublicarLista publ = new PublicarLista();
        publ.setVisible(true);
        publ.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_itemPublicarListaActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemAgregarTemaLista;
    private javax.swing.JMenuItem itemAltaAlbum;
    private javax.swing.JMenuItem itemAltaGenero;
    private javax.swing.JMenuItem itemAltaPerfil;
    private javax.swing.JMenuItem itemCargarDatos;
    private javax.swing.JMenuItem itemConsultaAlbum;
    private javax.swing.JMenuItem itemConsultaListaReproduccion;
    private javax.swing.JMenuItem itemConsultaPerfilArtista;
    private javax.swing.JMenuItem itemConsultaPerfilCliente;
    private javax.swing.JMenuItem itemCrearLista;
    private javax.swing.JMenuItem itemDejarDeSeguirUsuario;
    private javax.swing.JMenuItem itemEliminarTemaListaAlbum;
    private javax.swing.JMenuItem itemGuardarTemaListaAlbum;
    private javax.swing.JMenuItem itemPublicarLista;
    private javax.swing.JMenuItem itemQuitarTemaLista;
    private javax.swing.JMenuItem itemSeguirUsuario;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenu menuRegistro;
    private javax.swing.JMenu menuSistema;
    // End of variables declaration//GEN-END:variables
}
