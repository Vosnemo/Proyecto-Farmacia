/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import seguridad.Conexion;
import seguridad.Usuario;

/**
 *
 * @author GuillePC
 */
public class Ventas extends javax.swing.JFrame {
  private static Usuario user;//variable global del usuario logeado


    /**
     * Creates new form Inventario
     */
    public Ventas(Usuario user) {
        this.user=user;//Se asigna el usuaario que hizo login
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/índice.png")).getImage());
         
        initComponents();      
        this.setLocationRelativeTo(null);

        mostrardatos("");
    }
    
    
       void mostrardatos(String valor)
 {

        try {
            Connection cn=Conexion.conectar();
            
            DefaultTableModel modelo=new DefaultTableModel();
            
            modelo.addColumn("Id Venta");
            modelo.addColumn("Cantidad Medicamentos ");
            modelo.addColumn("Total");

            
           
            
            jTable1.setModel(modelo);
            String sql="";
            
            if (valor.equals(""))
            {
               sql=" select ven.idVenta , ped.Cantidad_Medi, ven.Total  from pedido ped  inner join venta ven on ped.Venta_idVenta = ven.idVenta  where (ven.Anulado =0 )group by ven.idVenta ";
            }
            else
            {
                sql=" select med.Nombre, ped.Cantidad_Medi from pedido ped  inner join lote lot on ped.Lote_idLotes= lot.idLotes inner join medicamento med on lot.Medicamento_idMedicamento = med.idMedicamento inner join venta ven on ped.Venta_idVenta = ven.idVenta  where (ven.idVenta = "+valor+"  ) ";
            }    
            
            String []datos=new String [9];
            
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);


                modelo.addRow(datos);
                
                jTable1.setModel(modelo);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);

        }
            
          
    }
       
        void mostrardatoVent(String valor)
 {

        try {
            Connection cn=Conexion.conectar();
            
            DefaultTableModel modelo=new DefaultTableModel();
            
            modelo.addColumn("Medicamento");
            modelo.addColumn("Cantidad Vendida ");
         
           
            
            jTable1.setModel(modelo);
            String sql="";
            
            if (valor.equals(""))
            {
              //sql=" select med.Nombre, ped.Cantidad_Medi from pedido ped  inner join lote lot on ped.Lote_idLotes= lot.idLotes inner join medicamento med on lot.Medicamento_idMedicamento = med.idMedicamento inner join venta ven on ped.Venta_idVenta = ven.idVenta  where (ven.idVenta = "+valor+"  ) ";
            }
            else
            {
             sql=" select med.Nombre, ped.Cantidad_Medi from pedido ped  inner join lote lot on ped.Lote_idLotes= lot.idLotes inner join medicamento med on lot.Medicamento_idMedicamento = med.idMedicamento inner join venta ven on ped.Venta_idVenta = ven.idVenta  where (ven.idVenta = "+valor+"  ) ";
            }    
            
            String []datos=new String [9];
            
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next())
            {
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);

                modelo.addRow(datos);
                
                jTable1.setModel(modelo);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);

        }
            
          
    }
       
       
 private void Actualiz(String idVenta)
 {
    try {
            Connection cn=Conexion.conectar();

                  try{
                        
                        String sql="";                
                        sql = ("Select pedido.Lote_idLotes, pedido.Cantidad_Medi from pedido  Where( pedido.Venta_idVenta  = '"+idVenta+"' ) ");
                       
                        String []datos=new String [5];
            
                            Statement st=cn.createStatement();
                            ResultSet rs=st.executeQuery(sql);
                            
                            while(rs.next())
                            {
                                datos[0]=rs.getString(1);                              
                                datos[1]=rs.getString(2);
                                
                                PreparedStatement pst2=cn.prepareStatement("UPDATE  Lote  set lote.Existencia = (Lote.Existencia + '"+datos[1]+"' ) where (lote.idLotes = '"+datos[0]+"' ) ");
                                pst2.executeUpdate();                               
                            }
                                 PreparedStatement pst=cn.prepareStatement("UPDATE  Venta  set Venta.Anulado = 1 where venta.idVenta = ('"+idVenta+"' ) ");
                                 pst.executeUpdate();
                      
                       JOptionPane.showMessageDialog(null, "Venta Anulada");
                       mostrardatos("");


                    }
                    catch (SQLException e){
                        JOptionPane.showMessageDialog(null, "Error "+e, "Error", JOptionPane.ERROR_MESSAGE);

                    }

              

        } catch (SQLException ex) { // TRY CATCH conexion
            JOptionPane.showMessageDialog(null, "Error" + ex);

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
        jmenuAnular = new javax.swing.JMenuItem();
        jmenuVerVent = new javax.swing.JMenuItem();
        jMenuTodVentas = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jtxtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnMenu = new javax.swing.JButton();
        jLabelMenu = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jmenuAnular.setText("Anular");
        jmenuAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuAnularActionPerformed(evt);
            }
        });
        jpopOpciones.add(jmenuAnular);

        jmenuVerVent.setText("Ver Venta");
        jmenuVerVent.setToolTipText("");
        jmenuVerVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuVerVentActionPerformed(evt);
            }
        });
        jpopOpciones.add(jmenuVerVent);

        jMenuTodVentas.setText("Toda Las Ventas");
        jMenuTodVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTodVentasActionPerformed(evt);
            }
        });
        jpopOpciones.add(jMenuTodVentas);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VENTAS");
        setFocusTraversalPolicyProvider(true);
        setMinimumSize(new java.awt.Dimension(1440, 650));
        setResizable(false);
        setSize(new java.awt.Dimension(1440, 650));
        getContentPane().setLayout(null);

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
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 70, 1260, 510);

        jtxtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(jtxtBuscar);
        jtxtBuscar.setBounds(20, 30, 91, 22);

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
        btnBuscar.setBounds(120, 10, 50, 50);

        btnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ActualizarBlanco.png"))); // NOI18N
        btnMostrar.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Actualizar</h2>\n\t</div>\n</body>\n</html>");
        btnMostrar.setBorderPainted(false);
        btnMostrar.setContentAreaFilled(false);
        btnMostrar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ActualizarNegro.png"))); // NOI18N
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnMostrar);
        btnMostrar.setBounds(1220, 20, 50, 41);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Buscar");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 10, 47, 16);

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cerrar Sesion.png"))); // NOI18N
        btnMenu.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Menu</h2>\n\t</div>\n</body>\n</html>");
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CerrarSesionNegro.png"))); // NOI18N
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        getContentPane().add(btnMenu);
        btnMenu.setBounds(1320, 520, 73, 49);

        jLabelMenu.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabelMenu.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMenu.setText("Menú");
        getContentPane().add(jLabelMenu);
        jLabelMenu.setBounds(1330, 580, 60, 17);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/wpblue.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1490, 670);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
  
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        mostrardatos(jtxtBuscar.getText());        
     
        jtxtBuscar.setText(null);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jtxtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtBuscarActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        mostrardatos("");
        jtxtBuscar.setText("");
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        Menu otro = new Menu();
        otro.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void jmenuAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuAnularActionPerformed
        
         int fila=jTable1.getSelectedRow();


            String idVenta="";
            if(fila==-1)
            {
                JOptionPane.showMessageDialog(null, "Seleccione una casilla", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                idVenta=jTable1.getValueAt(fila,0).toString();
            }

          
                int k =  JOptionPane.showConfirmDialog(null, "Anular Venta?", "Anular", JOptionPane.YES_NO_OPTION);

                if(k==JOptionPane.YES_OPTION)
                {
                   Actualiz(idVenta);
                }
     
    }//GEN-LAST:event_jmenuAnularActionPerformed

    private void jmenuVerVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuVerVentActionPerformed
        int fila = jTable1.getSelectedRow();
        String noVent=jTable1.getValueAt(fila,0).toString();
        mostrardatoVent(noVent);
        jmenuVerVent.setVisible(false);
        jMenuTodVentas.setVisible(true);
        
        jtxtBuscar.setEnabled(false);
        btnBuscar.setEnabled(false);




    }//GEN-LAST:event_jmenuVerVentActionPerformed

    private void jMenuTodVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTodVentasActionPerformed
        mostrardatos("");
        jmenuVerVent.setVisible(true);
        jMenuTodVentas.setVisible(false);

        jtxtBuscar.setEnabled(true);
        btnBuscar.setEnabled(true);
    }//GEN-LAST:event_jMenuTodVentasActionPerformed

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
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Ventas(user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JMenuItem jMenuTodVentas;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem jmenuAnular;
    private javax.swing.JMenuItem jmenuVerVent;
    private javax.swing.JPopupMenu jpopOpciones;
    private javax.swing.JTextField jtxtBuscar;
    // End of variables declaration//GEN-END:variables
}
