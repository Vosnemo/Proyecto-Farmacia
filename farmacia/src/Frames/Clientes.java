/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import java.awt.Toolkit;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import seguridad.Conexion;
import seguridad.Usuario;

/**
 *
 * @author GuillePC
 */
public class Clientes extends javax.swing.JFrame {
        private static Usuario user;//variable global del usuario logeado

    /**
     * Creates new form Medicamento
     */
    public Clientes(Usuario user) {
        this.user=user;//Se asigna el usuaario que hizo login
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/índice.png")).getImage());
         
        initComponents();      
        this.setLocationRelativeTo(null);
        
         mostrardatos("");  
         btnNuevo.setVisible(true);
         btnCancelar.setVisible(false);
        
        jtxtNombre.setEnabled(false);
        jtxtApellido.setEnabled(false);
        jtxtNIT.setEnabled(false);
        jtxtDireccion.setEnabled(false);
        
        
       
        btnInsertar.setEnabled(false);
        btnInsertar.setVisible(false);
        btnModificar.setVisible(false);
        

    
    }
    
    
      private int Validar()
    {
       
        if ( jtxtNombre.getText().length()==0) 
        {
          JOptionPane.showMessageDialog(null, "Llenar Todos Los campos", "Error", JOptionPane.ERROR_MESSAGE);
          return 1;
        }
        else if (jtxtApellido.getText().length()==0) 
        {
          JOptionPane.showMessageDialog(null, "Llenar Todos Los campos", "Error", JOptionPane.ERROR_MESSAGE);
          return 1;
        }
        else if (jtxtNIT.getText().length()==0) 
        {
          JOptionPane.showMessageDialog(null, "Llenar Todos Los campos", "Error", JOptionPane.ERROR_MESSAGE);
           return 1;
        }  
        else if (jtxtDireccion.getText().length()==0) 
        {
          JOptionPane.showMessageDialog(null, "Llenar Todos Los campos", "Error", JOptionPane.ERROR_MESSAGE);
           return 1;
        }  
    
        else
        {
            return 0;
        }
    }
    
    
 private  void mostrardatos(String valor)
 {
        try{
            Connection cn=Conexion.conectar();
            
            DefaultTableModel modelo=new DefaultTableModel()
            {
                @Override
                public boolean isCellEditable(int fil, int col) {
                    return col==7;
                }
            
            };
            
            modelo.addColumn("ID Cliente");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("NIT");
            modelo.addColumn("Direccion");

            
            
            jTable1.setModel(modelo);
            String sql="";
            
            if (valor.equals(""))
            {
                sql="select * from Cliente";
            }
        
            
            else
            {
                sql="SELECT * FROM Cliente WHERE (idCliente='"+valor+"'  OR Nombre='"+valor+"' OR Apellido='"+valor+"' )";
            }
            
            String []datos=new String [6];
            
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    datos[4]=rs.getString(5);
                 
                                        
                    modelo.addRow(datos);
                }
                           
              
           
        }catch(SQLException ex){
           
          JOptionPane.showMessageDialog(null, "Error" +ex);
        }
    }
           
 private int VerExist(String Id)
    {
        try{
            Connection cn=Conexion.conectar();
            String sql="";          
            
            sql="select lote.existencia from lote where (lote.Medicamento_idMedicamento ='"+Id+"' )";
            
            int existencia =0;
            
            try{
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {
                    existencia=Integer.parseInt(rs.getString(1));
                }
                
            }
            catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error" +ex);
            }
            return existencia;
        }
        
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error" +ex);
            return 100;
        }
    }
 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpopOpciones = new javax.swing.JPopupMenu();
        jmenuModificar = new javax.swing.JMenuItem();
        jMenuInsertar = new javax.swing.JMenuItem();
        btnInsertar = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jtxtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtNIT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtDireccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtxtApellido = new javax.swing.JTextField();
        jtxtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        jLabelMenu = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jmenuModificar.setText("Modificar");
        jmenuModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuModificarActionPerformed(evt);
            }
        });
        jpopOpciones.add(jmenuModificar);

        jMenuInsertar.setText("Insertar");
        jMenuInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuInsertarActionPerformed(evt);
            }
        });
        jpopOpciones.add(jMenuInsertar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CLIENTES");
        setMinimumSize(new java.awt.Dimension(1440, 650));
        setName("Clientes"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);

        btnInsertar.setBackground(new java.awt.Color(255, 255, 255));
        btnInsertar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/AñadirBlanco.png"))); // NOI18N
        btnInsertar.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Añadir\n</h2>\n\t</div>\n</body>\n</html>");
        btnInsertar.setBorderPainted(false);
        btnInsertar.setContentAreaFilled(false);
        btnInsertar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/AñadirNegro.png"))); // NOI18N
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertar);
        btnInsertar.setBounds(10, 380, 50, 40);

        btnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ActualizarBlanco.png"))); // NOI18N
        btnMostrar.setBorderPainted(false);
        btnMostrar.setContentAreaFilled(false);
        btnMostrar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ActualizarNegro.png"))); // NOI18N
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnMostrar);
        btnMostrar.setBounds(1260, 450, 50, 41);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setComponentPopupMenu(jpopOpciones);
        jTable1.setFocusable(false);
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(190, 10, 1067, 508);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 80, 49, 17);
        getContentPane().add(jtxtNombre);
        jtxtNombre.setBounds(10, 110, 161, 22);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NIT");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 200, 30, 17);
        getContentPane().add(jtxtNIT);
        jtxtNIT.setBounds(10, 220, 161, 22);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Direccion");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 250, 70, 17);
        getContentPane().add(jtxtDireccion);
        jtxtDireccion.setBounds(10, 270, 161, 70);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Apellido");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(10, 140, 45, 17);
        getContentPane().add(jtxtApellido);
        jtxtApellido.setBounds(10, 170, 161, 22);
        getContentPane().add(jtxtBuscar);
        jtxtBuscar.setBounds(1290, 20, 89, 30);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/BuscarBlanco.png"))); // NOI18N
        btnBuscar.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Buscar</h2>\n\t</div>\n</body>\n</html>");
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar);
        btnBuscar.setBounds(1310, 50, 50, 40);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LapizBlanco.png"))); // NOI18N
        btnModificar.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Editar</h2>\n\t</div>\n</body>\n</html>");
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LapizNegro.png"))); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar);
        btnModificar.setBounds(100, 370, 60, 60);

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cerrar Sesion.png"))); // NOI18N
        btnMenu.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Menu</h2>\n\t</div>\n</body>\n</html>");
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cerrar Sesion Select.png"))); // NOI18N
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        getContentPane().add(btnMenu);
        btnMenu.setBounds(1340, 520, 73, 49);

        jLabelMenu.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabelMenu.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMenu.setText("Menú");
        getContentPane().add(jLabelMenu);
        jLabelMenu.setBounds(1350, 570, 60, 17);

        btnNuevo.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/AñadirBlanco.png"))); // NOI18N
        btnNuevo.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Añadir</h2>\n\t</div>\n</body>\n</html>");
        btnNuevo.setBorderPainted(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/AñadirNegro.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo);
        btnNuevo.setBounds(130, 20, 50, 40);

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/XBlanca.png"))); // NOI18N
        btnCancelar.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Cancelar\n</h2>\n\t</div>\n</body>\n</html>");
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/XNegra.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar);
        btnCancelar.setBounds(50, 370, 60, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/wpblue.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1450, 630);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        

            try{ Connection cn=Conexion.conectar();
            {
                  if(Validar()==0)
                  {
                      PreparedStatement pst=cn.prepareStatement("INSERT INTO Cliente(Nombre,Apellido,NIT,Direccion) VALUES(?,?,?,?)");
                
                      pst.setString(1,jtxtNombre.getText());
                      pst.setString(2,jtxtApellido.getText());
                      pst.setString(3,jtxtNIT.getText());
                      pst.setString(4,jtxtDireccion.getText());

                      int a=pst.executeUpdate();
                      if(a>0)
                      {
                          JOptionPane.showMessageDialog(null,"Registro exitoso");
                          mostrardatos("");

                          jtxtNombre.setText(null);
                          jtxtApellido.setText(null);
                          jtxtNIT.setText(null);                        
                          jtxtDireccion.setText(null);



                      }

                      else
                      {
                          JOptionPane.showMessageDialog(null,"Error al agregar");
                      }
                  
                  } // fin del if validar
                   else
                       {
                             JOptionPane.showMessageDialog(null, "Llenar Todos Los Campos", "Error", JOptionPane.ERROR_MESSAGE);
                       }


             
                 } // fin del tray conexion
                  

            
            } // FIN DEL TRY 
            
            
            
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }









        
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        mostrardatos("");
        
        btnNuevo.setVisible(true);
        //radHabili.setSelected(true);
        btnInsertar.setVisible(false);     
        btnModificar.setVisible(false);
        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtNIT.setText("");
        jtxtDireccion.setText("");
        jtxtBuscar.setText("");
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        mostrardatos(jtxtBuscar.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
             
        try{                                            
            Connection cn=Conexion.conectar();
            
            int fila=jTable1.getSelectedRow();
            String idC="";
            if(fila==-1)
            {
                JOptionPane.showMessageDialog(null, "Seleccione una casilla", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                idC=jTable1.getValueAt(fila,0).toString();
            }
            
            
            try{
                if (Validar()==0) 
                {
                        PreparedStatement pst=cn.prepareStatement("UPDATE Cliente SET idCliente='"+idC+"',Nombre='"+jtxtNombre.getText()+"',Apellido='"+jtxtApellido.getText()+"',NIT='"+jtxtNIT.getText()+"',Direccion='"+jtxtDireccion.getText()+ "' WHERE idCliente='"+idC+"' ");
                       pst.executeUpdate();

                       mostrardatos("");
                       jtxtNombre.setText(null);
                       jtxtApellido.setText(null);
                       jtxtNIT.setText(null);
                       jtxtDireccion.setText(null);
                       
                       btnModificar.setVisible(false);
                       btnCancelar.setVisible(false);
                       btnNuevo.setVisible(true);
                    
                       JOptionPane.showMessageDialog(null, "Registro Modificado Con Exito");
                } // FIn del if Validar
                
                else
                  {
                   JOptionPane.showMessageDialog(null, "Llenar Todos Los Campos", "Error", JOptionPane.ERROR_MESSAGE);
                  }
                
                
               
                
                
            } //TRY CATCH de actualizacion //TRY CATCH de actualizacion
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, "Error"+e);
            }
            
        }
        catch(SQLException ex)
        {
         JOptionPane.showMessageDialog(null, "Error"+ex); // TRY CATCH conexion
        }
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void jMenuInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuInsertarActionPerformed
      
       btnCancelar.setVisible(true);
        btnNuevo.setVisible(false);
        btnInsertar.setVisible(true);
        btnInsertar.setEnabled(true);
        btnModificar.setVisible(false);
         
        jtxtNombre.setEnabled(true);
        jtxtApellido.setEnabled(true);
        jtxtNIT.setEnabled(true);
        jtxtDireccion.setEnabled(true);
        
        btnInsertar.setEnabled(true);
        
        

        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtNIT.setText("");
        jtxtDireccion.setText("");

       
    }//GEN-LAST:event_jMenuInsertarActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        Menu otro = new Menu();
        otro.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        
        btnNuevo.setVisible(false);
        btnCancelar.setVisible(true);
        
        btnInsertar.setVisible(true);
        btnInsertar.setEnabled(true);
        btnModificar.setVisible(false);        
         
        jtxtNombre.setEnabled(true);
        jtxtApellido.setEnabled(true);
        jtxtNIT.setEnabled(true);
        jtxtDireccion.setEnabled(true);
    
        btnInsertar.setEnabled(true);
        
        

        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtNIT.setText("");
        jtxtDireccion.setText("");

        
        
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       
        btnNuevo.setVisible(true);
        btnCancelar.setVisible(false);
        
        btnInsertar.setVisible(false);
        btnModificar.setVisible(false);        
         
        jtxtNombre.setEnabled(false);
        jtxtApellido.setEnabled(false);
        jtxtNIT.setEnabled(false);
        jtxtDireccion.setEnabled(false);


        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtNIT.setText("");
        jtxtDireccion.setText("");

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jmenuModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuModificarActionPerformed

        
        btnInsertar.setVisible(false);
        btnNuevo.setVisible(false);

            jtxtNombre.setEnabled(true);
            jtxtApellido.setEnabled(true);
            jtxtNIT.setEnabled(true);
            jtxtDireccion.setEnabled(true);
        

        int fila=jTable1.getSelectedRow();
        if (fila==-1) {
            JOptionPane.showMessageDialog(null, "Seleccione una casilla", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{

            btnModificar.setVisible(true);
            btnCancelar.setVisible(true);

            //--------------------------------------------

            jtxtNombre.setText(jTable1.getValueAt(fila,1).toString());
            jtxtApellido.setText(jTable1.getValueAt(fila,2).toString());
            jtxtNIT.setText(jTable1.getValueAt(fila,3).toString());
            jtxtDireccion.setText(jTable1.getValueAt(fila,4).toString());
            

        }

    }//GEN-LAST:event_jmenuModificarActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clientes(user).setVisible(true);
            }
        });
    }
    
     


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JMenuItem jMenuInsertar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem jmenuModificar;
    private javax.swing.JPopupMenu jpopOpciones;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtBuscar;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNIT;
    private javax.swing.JTextField jtxtNombre;
    // End of variables declaration//GEN-END:variables
}
