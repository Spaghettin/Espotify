/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacionGUI;
import controladores.Fabrica;
import controladores.iSistema;
import datatypes.DataAlbum;
import datatypes.DataUsuario;
import excepciones.UsuarioNoExisteException;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.ButtonGroup;
//import javax.persistence.criteria.Path;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;




public class AltaAlbum extends javax.swing.JInternalFrame {

    String nombre;
    Integer fechaCreacion;
    String imagenAlbum;
    String nickname;
    Set<String> setGeneros = new HashSet<>();
    Set<String> setTemas = new HashSet<>();
    Set<Integer> setPosiciones = new HashSet<>();
    String archivo;
    
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Géneros");
    DefaultTreeModel treeModel = new DefaultTreeModel(root);
    iSistema sys = new Fabrica().getSistema();
    private DefaultTableModel tableModel;
    String tablegen;
    
    
    public AltaAlbum() {
        
        initComponents();
        jComboBox_nickname.setSelectedItem(null);   
        jPanel1.setVisible(false);
        
        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(rb_archivo);
        grupoOpciones.add(rb_direccionWeb);
        jButton_archivo.setVisible(false);
        jText_url.setVisible(false);
        
        String[] columnNames = {"Nombre", "Duración", "Posición", "Álbum", "Género"};
        tableModel = new DefaultTableModel(columnNames, 0);
        jTable_temAG.setModel(tableModel);
        jTable_temAG.setVisible(false);
        
        sys.cargarGenerosSys();
        jTree_generos = new JTree(treeModel);
        Load();
        jTree_generos.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
                
            TreePath path = jTree_generos.getPathForLocation(evt.getX(), evt.getY());

            if (path != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                String selectedGenre = selectedNode.getUserObject().toString();
                txtF_gen.setText(selectedGenre);
                tablegen = selectedGenre;
            }else {
                txtF_gen.setText("  Esperando genero");
            }
        }});
        
    txt_nombre.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        nombre = txt_nombre.getText();
    }
});
    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonGroup = new javax.swing.ButtonGroup();
        btn_salir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel_nombre = new javax.swing.JLabel();
        jLabel_anio = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_anio = new javax.swing.JTextField();
        btn_img = new javax.swing.JButton();
        jLabel_imagen = new javax.swing.JLabel();
        label_nombre_tema = new javax.swing.JLabel();
        label_duracion_tema = new javax.swing.JLabel();
        label_orden_tema = new javax.swing.JLabel();
        btn_tema = new javax.swing.JButton();
        txt_nombre_tema = new javax.swing.JTextField();
        txt_orden_tema = new javax.swing.JTextField();
        txt_duracion_tema = new javax.swing.JTextField();
        jScrollPane = new javax.swing.JScrollPane();
        jTree_generos = new javax.swing.JTree();
        btn_gen = new javax.swing.JButton();
        txtF_gen = new javax.swing.JTextField();
        jText_url = new javax.swing.JTextField();
        rb_archivo = new javax.swing.JRadioButton();
        rb_direccionWeb = new javax.swing.JRadioButton();
        jButton_archivo = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_temAG = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel_nickname = new javax.swing.JLabel();
        jComboBox_nickname = new javax.swing.JComboBox<>();
        btn_alta = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_salir.setText("Salir");
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        getContentPane().add(btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(558, 500, 98, -1));

        jLabel_nombre.setText("Nombre album:");

        jLabel_anio.setText("Año de creación:");

        txt_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreActionPerformed(evt);
            }
        });

        btn_img.setText("Seleccionar Imagen");
        btn_img.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imgActionPerformed(evt);
            }
        });

        label_nombre_tema.setText("Nombre");

        label_duracion_tema.setText("Duración");

        label_orden_tema.setText("Posición");

        btn_tema.setText("Agregar Tema");
        btn_tema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_temaActionPerformed(evt);
            }
        });

        txt_nombre_tema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombre_temaActionPerformed(evt);
            }
        });

        txt_orden_tema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_orden_temaActionPerformed(evt);
            }
        });

        txt_duracion_tema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_duracion_temaActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree_generos.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane.setViewportView(jTree_generos);

        btn_gen.setText("Agregar");
        btn_gen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_genActionPerformed(evt);
            }
        });

        txtF_gen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_genActionPerformed(evt);
            }
        });

        jText_url.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jText_urlActionPerformed(evt);
            }
        });

        rb_archivo.setText("Archivo");
        rb_archivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_archivoActionPerformed(evt);
            }
        });

        rb_direccionWeb.setText("Direccion Web");
        rb_direccionWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_direccionWebActionPerformed(evt);
            }
        });

        jButton_archivo.setText("Cargar Archivo");
        jButton_archivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_archivoActionPerformed(evt);
            }
        });

        jTable_temAG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable_temAG);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_img)
                                .addGap(25, 25, 25)
                                .addComponent(jLabel_imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtF_gen)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_gen, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel_nombre)
                                        .addGap(93, 93, 93)
                                        .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel_anio)
                                        .addGap(88, 88, 88)
                                        .addComponent(txt_anio, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(label_nombre_tema))
                                    .addComponent(txt_nombre_tema, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(label_duracion_tema))
                                    .addComponent(txt_duracion_tema, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_orden_tema, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(label_orden_tema)
                                        .addGap(8, 8, 8)))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rb_direccionWeb)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rb_archivo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton_archivo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_tema, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jText_url, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel_nombre))
                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel_anio))
                    .addComponent(txt_anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(btn_img))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel_imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(txtF_gen, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_gen, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(label_nombre_tema)
                            .addGap(6, 6, 6)
                            .addComponent(txt_nombre_tema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(label_duracion_tema)
                                .addComponent(label_orden_tema))
                            .addGap(6, 6, 6)
                            .addComponent(txt_duracion_tema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txt_orden_tema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_archivo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rb_archivo))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jText_url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rb_direccionWeb))
                        .addGap(12, 12, 12)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_tema, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, -1, -1));

        jLabel_nickname.setText("Ingrese Nickname a consultar:");

        try{
            DataUsuario[] dataArtistas = sys.getArtistas();
            String[] nicksA = new String[dataArtistas.length];
            for(int i = 0; i < dataArtistas.length; i++){
                nicksA[i] = dataArtistas[i].getNick();
            }
            jComboBox_nickname.setModel(new javax.swing.DefaultComboBoxModel<>(nicksA));

            jComboBox_nickname.setToolTipText("");
        }catch(UsuarioNoExisteException e){}
        jComboBox_nickname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_nicknameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 465, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel_nickname)
                    .addGap(18, 18, 18)
                    .addComponent(jComboBox_nickname, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel_nickname)
                        .addComponent(jComboBox_nickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(13, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 0, -1, -1));

        btn_alta.setText("Alta Album");
        btn_alta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_altaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_alta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, -1, -1));

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Load(){

        HashSet<String> generos = sys.getGeneros();
        String padre = "Genero";
        for (String nombreGenero : generos){
            padre = sys.getPadre(nombreGenero);
            if(!padre.equals("Genero")){
               // Buscar el nodo del género principal
                DefaultMutableTreeNode nodoPrincipal = buscarNodo(root, padre);
                if (nodoPrincipal != null) {
                    nodoPrincipal.add(new DefaultMutableTreeNode(nombreGenero));
                } else {
                    nodoPrincipal = new DefaultMutableTreeNode(padre);
                    root.add(nodoPrincipal);
                    nodoPrincipal.add(new DefaultMutableTreeNode(nombreGenero));
                }
            }else{
                root.add(new DefaultMutableTreeNode(nombreGenero));
            } 
        }

        treeModel.reload();

        for (int i = 0; i < jTree_generos.getRowCount(); i++) {
            jTree_generos.expandRow(i);
        }
        JScrollPane scrollPane = new JScrollPane(jTree_generos);
        jPanel1.add(scrollPane);
        jScrollPane.setViewportView(jTree_generos);
        
    }

    private DefaultMutableTreeNode buscarNodo(DefaultMutableTreeNode root, String name) {
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
            if (node.getUserObject().toString().equals(name)) {
                return node;
            }
        }
        return null;
    }
    
    private void jComboBox_nicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_nicknameActionPerformed
        if(jComboBox_nickname.getSelectedItem() == null){
            return;
        }
        jPanel1.setVisible(true);
        nickname = jComboBox_nickname.getSelectedItem().toString();
        jTable_temAG.setVisible(true);
        //sys.altaAlbum(nickname);
        btn_salir.setVisible(false);
        
    }//GEN-LAST:event_jComboBox_nicknameActionPerformed

    private void txt_duracion_temaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_duracion_temaActionPerformed
        Integer duracion = Integer.valueOf(txt_duracion_tema.getText());
    }//GEN-LAST:event_txt_duracion_temaActionPerformed

    private void txt_orden_temaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_orden_temaActionPerformed
        Integer pos = Integer.valueOf(txt_orden_tema.getText());
    }//GEN-LAST:event_txt_orden_temaActionPerformed

    private void txt_nombre_temaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombre_temaActionPerformed
        String nombreT = txt_nombre_tema.getText();
    }//GEN-LAST:event_txt_nombre_temaActionPerformed

    private void btn_temaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_temaActionPerformed
    try{
        nickname = jComboBox_nickname.getSelectedItem().toString();
        nombre = txt_nombre.getText();
        String nombreT = txt_nombre_tema.getText();
        String duracion = txt_duracion_tema.getText();
        Integer pos = Integer.valueOf(txt_orden_tema.getText());
        String direccionWeb = jText_url.getText();
        

        if (setTemas.contains(nombreT)){
                JOptionPane.showMessageDialog(this, "Tema ya registrado, seleccione otro.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
            if (nombre == ""){
                JOptionPane.showMessageDialog(this, "ERROR: tiene que ingresar un nombre para el tema.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        //verifica la posicion
        if (setPosiciones.contains(pos)) {
            JOptionPane.showMessageDialog(this, "ERROR: Ya existe un tema con la posición " + pos + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //
        if (rb_direccionWeb.isSelected()) {
            direccionWeb = jText_url.getText();
            if (direccionWeb.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: Debe ingresar una dirección web.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (rb_archivo.isSelected()) {
            jText_url = null;
            if (archivo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: Debe cargar un archivo de audio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //
        
        setPosiciones.add(pos);
        setTemas.add(nombreT);
        
        sys.altaTema(nombreT, duracion, pos, direccionWeb, archivo);
        JOptionPane.showMessageDialog(this, "Tema registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        Object[] rowData = {nombreT, duracion, pos, nombre, tablegen};
        tableModel.addRow(rowData);
        
        txt_nombre_tema.setText("");
        txt_orden_tema.setText("");
        txt_duracion_tema.setText("");
    } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: El campo de posición tiene que ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: Ocurrió un problema inesperado con el tema. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
    
    }//GEN-LAST:event_btn_temaActionPerformed

    private void btn_imgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imgActionPerformed
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filtrado = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
        jFileChooser.setFileFilter(filtrado);

        int respuesta = jFileChooser.showOpenDialog(this);

        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File imagenSeleccionada = jFileChooser.getSelectedFile();
            imagenAlbum = imagenSeleccionada.getName();

            try {
                Path destino = Paths.get("src/main/resources/imagesA/" + imagenAlbum);

                if (!Files.exists(destino)) {
                    Files.copy(imagenSeleccionada.toPath(), destino);
                }

                // Mostrar la imagen en el JLabel
                Image mImagen = new ImageIcon(destino.toString()).getImage();
                ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(jLabel_imagen.getWidth(), jLabel_imagen.getHeight(), Image.SCALE_SMOOTH));
                jLabel_imagen.setIcon(mIcono);
                jLabel_imagen.setVisible(true);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "ERROR: No se pudo copiar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_imgActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed

        txt_anio.setText("");
        txt_nombre.setText("");
        Iterator<String> itGeneros = setGeneros.iterator();
            while (itGeneros.hasNext()) {
                itGeneros.next();
                itGeneros.remove(); //Elimina el elemento actual
            }
            
            Iterator<String> itTemas = setTemas.iterator();
            while (itTemas.hasNext()) {
                itTemas.next();
                itTemas.remove(); //Elimina el elemento actual
            }
        btn_img.setSelected(false);

        jLabel_nombre.setVisible(false);
        jLabel_anio.setVisible(false);

        this.setVisible(false);

    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void txt_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreActionPerformed
        nombre = txt_nombre.getText();
    }//GEN-LAST:event_txt_nombreActionPerformed

    private void btn_altaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_altaActionPerformed
        LocalDate fechaActual = LocalDate.now();
        int añoActual = fechaActual.getYear();
        try {
            // Validación de campos obligatorios
            if (txt_nombre.getText().isEmpty() || txt_anio.getText().isEmpty()) {
                throw new IllegalArgumentException("ERROR: faltan campos por completar.");
            }
            if (!(Integer.valueOf(txt_anio.getText()) < añoActual && Integer.valueOf(txt_anio.getText()) > 1850)){
                JOptionPane.showMessageDialog(this, "ERROR: El año ingresado tiene que estar en el rango de 1850-" + añoActual + ".", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } 
            
            fechaCreacion = Integer.valueOf(txt_anio.getText());         
            nombre = txt_nombre.getText();
            //imagenAlbum = btn_img.getText();

            
            if (setGeneros.isEmpty()){
                JOptionPane.showMessageDialog(this, "ERROR: Tiene que ingresar al menos un género.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (setTemas.isEmpty()){
                JOptionPane.showMessageDialog(this, "ERROR: Tiene que ingresar al menos un tema.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean existe = sys.verificaAlbum(nombre, nickname);
            if (existe) {
                JOptionPane.showMessageDialog(this, "ERROR: El álbum con el nombre '" + nombre + "' ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            sys.cargarDatosAlbum(nombre, nickname, fechaCreacion, imagenAlbum); //Se cargan los datos de los txt en el album generico
            sys.altaAlbum(); //Se crea el album objetivo, se copian los datos de generico y se linkea con el artista seleccionado
            //sys.actualizoAlbum();
            
            sys.getAlbumGenerico().limpiarAlbum(); //No hace falta comentarlo, pero limpia el generico xd
            JOptionPane.showMessageDialog(this, "Álbum registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            txt_nombre.setText("");
            txt_anio.setText("");
            
            Iterator<String> it = setGeneros.iterator();
            while (it.hasNext()) {
                String nombreGenero = it.next();
                if (sys.getAlbumEspecificoArt(nombre, nickname) != null){
//                  sys.addAlbumGenero(nombreGenero, sys.getAlbumEspecifico(nombre, nickname));
                    it.remove();
                }
            }
            
            Iterator<String> itTemas = setTemas.iterator();
            while (itTemas.hasNext()) {
                itTemas.next();
                itTemas.remove(); //Elimina el elemento actual
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: fecha inválida. Use el formato YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: Ocurrió un problema inesperado. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_altaActionPerformed

    private void btn_genActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_genActionPerformed
         TreePath selectedPath = jTree_generos.getSelectionPath();
            
        if (selectedPath != null) {
            
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

                if (selectedNode == root){
                    JOptionPane.showMessageDialog(this, "No puedes seleccionar \"Generos\".", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }    
                String selectedGenre = selectedNode.getUserObject().toString();  

                if (setGeneros.contains(selectedGenre)){
                    JOptionPane.showMessageDialog(this, "Género ya registrado, seleccione otro.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                setGeneros.add(selectedGenre);

                nickname = jComboBox_nickname.getSelectedItem().toString();
                if (selectedGenre != null){
                    sys.addGeneroAlbum(selectedGenre);
                }
                
                
                JOptionPane.showMessageDialog(this, "Género registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtF_gen.setText("");
                jTree_generos.updateUI();
            } else {
            txtF_gen.setText("  Esperando genero.");
            
        }
    }//GEN-LAST:event_btn_genActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void jText_urlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jText_urlActionPerformed
        
    }//GEN-LAST:event_jText_urlActionPerformed

    private void rb_archivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_archivoActionPerformed
        jText_url.setVisible(false);
        jButton_archivo.setVisible(true);
    }//GEN-LAST:event_rb_archivoActionPerformed

    private void rb_direccionWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_direccionWebActionPerformed
        jText_url.setVisible(true);
        jButton_archivo.setVisible(false);
    }//GEN-LAST:event_rb_direccionWebActionPerformed

    private void jButton_archivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_archivoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo de audio");
        int respuesta = fileChooser.showOpenDialog(null);

        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            archivo = archivoSeleccionado.getName();

            try {
                Path destino = Paths.get("src/main/resources/audio/" + archivo);

                if (!Files.exists(destino)) {
                    Files.copy(archivoSeleccionado.toPath(), destino);
                } 
                
            }catch (IOException e) {
                JOptionPane.showMessageDialog(this, "ERROR: No se pudo copiar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    }//GEN-LAST:event_jButton_archivoActionPerformed

    private void txtF_genActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_genActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_genActionPerformed

        
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup ButtonGroup;
    private javax.swing.JButton btn_alta;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_gen;
    private javax.swing.JButton btn_img;
    private javax.swing.JButton btn_salir;
    private javax.swing.JButton btn_tema;
    private javax.swing.JButton jButton_archivo;
    private javax.swing.JComboBox<String> jComboBox_nickname;
    private javax.swing.JLabel jLabel_anio;
    private javax.swing.JLabel jLabel_imagen;
    private javax.swing.JLabel jLabel_nickname;
    private javax.swing.JLabel jLabel_nombre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable_temAG;
    private javax.swing.JTextField jText_url;
    private javax.swing.JTree jTree_generos;
    private javax.swing.JLabel label_duracion_tema;
    private javax.swing.JLabel label_nombre_tema;
    private javax.swing.JLabel label_orden_tema;
    private javax.swing.JRadioButton rb_archivo;
    private javax.swing.JRadioButton rb_direccionWeb;
    private javax.swing.JTextField txtF_gen;
    private javax.swing.JTextField txt_anio;
    private javax.swing.JTextField txt_duracion_tema;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_nombre_tema;
    private javax.swing.JTextField txt_orden_tema;
    // End of variables declaration//GEN-END:variables
}
