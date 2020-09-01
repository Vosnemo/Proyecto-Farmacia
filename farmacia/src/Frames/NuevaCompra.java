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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;

import seguridad.Conexion;
import seguridad.Usuario;

/**
 *
 * @author GuillePC
 */
public class NuevaCompra extends javax.swing.JFrame {
    private static Usuario user;//variable global del usuario logeado

    /**
     * Creates new form Lotes
     */
    
   
     public NuevaCompra(Usuario user) {
        this.user=user;//Se asigna el usuaario que hizo login
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/índice.png")).getImage());
         
        initComponents();      
        this.setLocationRelativeTo(null);
        
        
        LlenarCmbMedi();
        LlenarcmbProve(); 

        jtxtCosto.setEnabled(false);
        jtxtExistencia.setEnabled(false);
        jDateFechaCadu.setEnabled(false);
      
        btnInsertar.setVisible(false);
        btnTerminarCom.setVisible(false);
        btnCancelarCo.setVisible(false);
        btnModificar.setVisible(false);
        
        cmbMedicamentoID.setEnabled(false);
        
    
    }
    
//    public NuevaCompra() {
//        initComponents();
//       
//        this.setLocationRelativeTo(null);
//        LlenarCmbMedi();
//        LlenarcmbProve(); 
//        //ObtenerDate();
//             
//       
//        jtxtCosto.setEnabled(false);
//        jtxtExistencia.setEnabled(false);
//        jDateFechaCadu.setEnabled(false);
//        btnInsertar.setEnabled(false);
//        btnModificar.setVisible(false);
//        cmbMedicamentoID.setEnabled(false);
//        
//    
//    }
   
    private java.sql.Date ObtenerDate(String FechaString) 
    { 
         
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); // Se Crea El formato
            java.util.Date fechaD = new java.util.Date(); // Variable De tipo Date
   
            fechaD = formatoFecha.parse(FechaString); // Se le asigna el valor STRING a la variable de tipo Date
            java.sql.Date sqlDate = new java.sql.Date(fechaD.getTime()); // Conversion Explicita a sqlDate
            //System.out.println(sqlDate);96.+
            
            
            
                    
                    return sqlDate;
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);

        }
        return null;

            
    }
    
     private int Validar()
    {
       
        if ( jtxtCosto.getText().length()==0) 
        {
          JOptionPane.showMessageDialog(null, "Llenar Todos Los campos", "Error", JOptionPane.ERROR_MESSAGE);
          return 1;
        }
        else if (jtxtExistencia.getText().length()==0) 
        {
          JOptionPane.showMessageDialog(null, "Llenar Todos Los campos", "Error", JOptionPane.ERROR_MESSAGE);
          return 1;

        }
        else if (jDateFechaCadu.getDate()==null) {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    
   private void LlenarcmbProve()
    {
        try{
            Connection cn=Conexion.conectar();
            String sql="";
            
            
            sql="select * from proveedor ";
            
            String []datos=new String [4];
        
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                while(rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);                    
                    
                    cmbProveedor.addItem(datos[0]+".  "+datos[1]);
                    
                }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error"+ex);
        }

    }
   
   
   private void LlenarCmbMedi()
    {
        try{
            Connection cn=Conexion.conectar();
            
            String sql="";         
            
            sql="select * from Medicamento where medicamento.Habilitado = 1";
            
            String []datos=new String [4];
           
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                while(rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    
                    cmbMedicamentoID.addItem(datos[0]+".  "+datos[1]);
                }
                
        }
        
        catch(SQLException ex)
        {
          JOptionPane.showMessageDialog(null, "Error"+ex);
        }

    }
    
   
     void mostrardatos(String valor)
    {
        try{
            Connection cn=Conexion.conectar();          
            DefaultTableModel modelo=new DefaultTableModel()
            {
                @Override
                public boolean isCellEditable(int fil, int col) {
                    return col==8;
                }
            
            };
            
            modelo.addColumn("No Lote");
            modelo.addColumn("Medicamento");
            modelo.addColumn("Existencia");
            modelo.addColumn("Costo");
            modelo.addColumn("Fecha De Caducidad");
            modelo.addColumn("Fecha De Ingreso");
            modelo.addColumn("Proveedor");

            
            
            
            jTable1.setModel(modelo);
            String sql="";
            
            if (valor.equals(""))
            {
                sql="Select lot.IdLotes, med.Nombre, lot.Existencia, lot.Costo, lot.FechaCaducidad, lot.FechaIngreso, pro.Nombre from lote lot inner join medicamento med on lot.Medicamento_idMedicamento = med.idMedicamento left join proveedor pro on lot.Proveedor_idproveedor = pro.idproveedor ";
            }
            else
            {
              sql="Select lot.IdLotes, med.Nombre, lot.Existencia, lot.Costo, lot.FechaCaducidad, lot.FechaIngreso, pro.Nombre from lote lot inner join medicamento med on lot.Medicamento_idMedicamento = med.idMedicamento left join proveedor pro on lot.Proveedor_idproveedor = pro.idproveedor where (lot.Compra_idCompra ='"+valor+"')";
  
            }
            
            
            String []datos=new String [8];
          
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                
                while(rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    datos[4]=rs.getString(5);
                    datos[5]=rs.getString(6);
                    datos[6]=rs.getString(7);
                    
                    modelo.addRow(datos);
                    
                }
                
                jTable1.setModel(modelo);
           
        }
        
        catch(SQLException ex)
        {
          JOptionPane.showMessageDialog(null, "Error "+ex);
        }
    }
    
     
     
 private void insertarCompra()
 {
        try {
            Connection cn=Conexion.conectar();
            java.util.Date d = new java.util.Date(); 
            java.sql.Date date = new java.sql.Date(d.getTime());
            PreparedStatement pst=cn.prepareStatement("INSERT INTO Compra(Usuario_idUsuario,Fecha) VALUES(?,?)");
                  
                      pst.setInt(1,user.getUserId());
                      pst.setDate(2,date);

                      int a=pst.executeUpdate();
                      if(a>0)
                      {
                         // JOptionPane.showMessageDialog(null,"Compra Generada");
                        
                      }

                      else
                      {
                          JOptionPane.showMessageDialog(null,"Error al Generar Compra");
                      }
           

           
       
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" +ex);
           
        }
 
 }
       
 private int obtenerUltimaCom()
 {
     try{
            Connection cn=Conexion.conectar();
            
                String sql="Select * from compra";
                
                String idCo="";  
                int iD;
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                
                while(rs.next())
                {
                    idCo=rs.getString(1);             
                  
                }
                iD=Integer.parseInt(idCo);
                return iD;
     }
        catch(SQLException ex)
        {
          JOptionPane.showMessageDialog(null, "Error "+ex);
          return 0;
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
        jMenuComprar = new javax.swing.JMenuItem();
        jmenuModificar = new javax.swing.JMenuItem();
        jmenuEliminar = new javax.swing.JMenuItem();
        jLabel2 = new javax.swing.JLabel();
        jtxtExistencia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtCosto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbMedicamentoID = new javax.swing.JComboBox<>();
        btnInsertar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cmbProveedor = new javax.swing.JComboBox<>();
        btnNuevaCompra = new javax.swing.JButton();
        btnTerminarCom = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        jLabelMenu = new javax.swing.JLabel();
        btnCancelarCo = new javax.swing.JButton();
        jDateFechaCadu = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        jMenuComprar.setText("Comprar");
        jMenuComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuComprarActionPerformed(evt);
            }
        });
        jpopOpciones.add(jMenuComprar);

        jmenuModificar.setText("Modificar");
        jmenuModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuModificarActionPerformed(evt);
            }
        });
        jpopOpciones.add(jmenuModificar);

        jmenuEliminar.setText("Eliminar");
        jmenuEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuEliminarActionPerformed(evt);
            }
        });
        jpopOpciones.add(jmenuEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NUEVA COMPRA");
        setMinimumSize(new java.awt.Dimension(1440, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Existencia");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(1190, 110, 60, 17);

        jtxtExistencia.setEnabled(false);
        getContentPane().add(jtxtExistencia);
        jtxtExistencia.setBounds(1190, 130, 118, 22);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Costo");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(1190, 170, 36, 17);

        jtxtCosto.setEnabled(false);
        getContentPane().add(jtxtCosto);
        jtxtCosto.setBounds(1190, 190, 118, 22);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fecha De Caducidad ");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(1190, 230, 130, 17);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Medicamento");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(1190, 60, 76, 16);

        cmbMedicamentoID.setEnabled(false);
        getContentPane().add(cmbMedicamentoID);
        cmbMedicamentoID.setBounds(1190, 80, 118, 22);

        btnInsertar.setBackground(new java.awt.Color(255, 255, 255));
        btnInsertar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/AñadirBlanco.png"))); // NOI18N
        btnInsertar.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Añadir</h2>\n\t</div>\n</body>\n</html>");
        btnInsertar.setBorderPainted(false);
        btnInsertar.setContentAreaFilled(false);
        btnInsertar.setEnabled(false);
        btnInsertar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/AñadirNegro.png"))); // NOI18N
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertar);
        btnInsertar.setBounds(1190, 350, 50, 40);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicamento", "Existencia", "Costo", "Fecha De Caducidad", "Fecha De Ingreso", "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setComponentPopupMenu(jpopOpciones);
        jTable1.setFocusable(false);
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 10, 1160, 508);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LapizBlanco.png"))); // NOI18N
        btnModificar.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Modificar</h2>\n\t</div>\n</body>\n</html>");
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setEnabled(false);
        btnModificar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LapizNegro.png"))); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar);
        btnModificar.setBounds(1240, 340, 60, 60);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Proveedor");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(1190, 290, 90, 20);

        cmbProveedor.setEnabled(false);
        getContentPane().add(cmbProveedor);
        cmbProveedor.setBounds(1190, 320, 118, 22);

        btnNuevaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/NuevaCompraBlanco.png"))); // NOI18N
        btnNuevaCompra.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: ##003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Nueva Compra</h2>\n\t</div>\n</body>\n</html>");
        btnNuevaCompra.setBorderPainted(false);
        btnNuevaCompra.setContentAreaFilled(false);
        btnNuevaCompra.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/NuevaCompraNegro.png"))); // NOI18N
        btnNuevaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaCompraActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevaCompra);
        btnNuevaCompra.setBounds(1180, 10, 50, 50);

        btnTerminarCom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ComprarBlanco.png"))); // NOI18N
        btnTerminarCom.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Comprar</h2>\n\t</div>\n</body>\n</html>");
        btnTerminarCom.setBorderPainted(false);
        btnTerminarCom.setContentAreaFilled(false);
        btnTerminarCom.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ComprarNegro.png"))); // NOI18N
        btnTerminarCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarComActionPerformed(evt);
            }
        });
        getContentPane().add(btnTerminarCom);
        btnTerminarCom.setBounds(1190, 450, 60, 60);

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
        btnMenu.setBounds(1350, 530, 73, 49);

        jLabelMenu.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabelMenu.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMenu.setText("Menú");
        getContentPane().add(jLabelMenu);
        jLabelMenu.setBounds(1360, 590, 60, 17);

        btnCancelarCo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CancelarCarritoBlanco.png"))); // NOI18N
        btnCancelarCo.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Cancelar</h2>\n\t</div>\n</body>\n</html>");
        btnCancelarCo.setBorderPainted(false);
        btnCancelarCo.setContentAreaFilled(false);
        btnCancelarCo.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CancelarCarritoNegro.png"))); // NOI18N
        btnCancelarCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCoActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelarCo);
        btnCancelarCo.setBounds(1190, 400, 60, 50);
        getContentPane().add(jDateFechaCadu);
        jDateFechaCadu.setBounds(1190, 260, 160, 22);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/wpblue.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1510, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        try{                       
            int idCompra=0;
              
            
            Connection cn=Conexion.conectar();
            java.util.Date d = new java.util.Date();
            java.sql.Date date = new java.sql.Date(d.getTime());
            
           
              if(obtenerUltimaCom()<=0)
                 {                 
                   JOptionPane.showMessageDialog(null, "Debe Generar una Compra", "Error", JOptionPane.ERROR_MESSAGE);                 
                 }
                   else
                   {
                         if (Validar()==0) 
                          {
                               String FechaLarga=jDateFechaCadu.getDate().toLocaleString();
                               String FechaFinal =FechaLarga.substring(0, 10);  
                            idCompra=obtenerUltimaCom();
                            PreparedStatement pst=cn.prepareStatement("INSERT INTO Lote(Medicamento_idMedicamento,Existencia,Costo,FechaCaducidad,FechaIngreso,Proveedor_idproveedor,Compra_idCompra) VALUES(?,?,?,?,?,?,?)");

                             pst.setInt(1,Integer.parseInt(cmbMedicamentoID.getSelectedItem().toString().substring(0, 1) ));
                             pst.setInt(2,Integer.parseInt(jtxtExistencia.getText()));
                             pst.setDouble(3,Double.parseDouble(jtxtCosto.getText()));
                            // pst.setString(4,jtxtFechaCadu.getText());
                             pst.setDate(4,ObtenerDate(FechaFinal));
                             pst.setDate(5,date);
                             pst.setInt(6,Integer.parseInt(cmbProveedor.getSelectedItem().toString().substring(0, 1) ));
                             pst.setInt(7,idCompra);


                             int a=pst.executeUpdate();
                             if(a>0)
                             {                                   
                                 //JOptionPane.showMessageDialog(null,"Producto Agregado");
                                 mostrardatos(String.valueOf(idCompra));

                                 jtxtExistencia.setText(null);
                                 jDateFechaCadu.setDate(new Date());
                                 jtxtCosto.setText(null);                    

                             }

                             else
                             {
                                 JOptionPane.showMessageDialog(null,"Error al agregar");
                             }
  
                           
                       }// FIN if validar
                            else
                            {
                             JOptionPane.showMessageDialog(null, "Llenar Todos Los Campos", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                      
                   } // FIn else para ver si hay compra 
            

            
        }catch(SQLException ex)
        {
          JOptionPane.showMessageDialog(null, "Error"+ex);
        }
    }//GEN-LAST:event_btnInsertarActionPerformed
   
    private void jMenuComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuComprarActionPerformed
        cmbMedicamentoID.setEnabled(true);
        btnInsertar.setVisible(true);
        btnInsertar.setEnabled(true);
        btnModificar.setVisible(false);

        jtxtCosto.setEnabled(true);
        jtxtExistencia.setEnabled(true);
        jDateFechaCadu.setEnabled(true);

        btnInsertar.setEnabled(true);

        jtxtCosto.setText("");
        jtxtExistencia.setText("");
        jDateFechaCadu.setDate(new Date());

    }//GEN-LAST:event_jMenuComprarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        try{                                             
                Connection cn=Conexion.conectar();
                 String FechaLarga=jDateFechaCadu.getDate().toLocaleString();
                 String FechaFinal =FechaLarga.substring(0, 10);    
                
                 java.util.Date d = new java.util.Date();
                java.sql.Date date = new java.sql.Date(d.getTime());
                
                int idCompra=obtenerUltimaCom();
                int fila = jTable1.getSelectedRow();
                String idmod="";
               
                  if(fila==-1)
                     {
                        JOptionPane.showMessageDialog(null, "Seleccione una casilla", "Error", JOptionPane.ERROR_MESSAGE);
                     }
                    else
                     {
                        idmod = jTable1.getValueAt(fila,0).toString();
                     }

                     try
                     {
                         if (Validar()==0)
                        {
                             
                            PreparedStatement pst=cn.prepareStatement("UPDATE Lote SET Medicamento_idMedicamento='" +Integer.toString(cmbMedicamentoID.getSelectedIndex()+1) +  "',Existencia='"+jtxtExistencia.getText()+ "',Costo='"+jtxtCosto.getText()+"',FechaCaducidad='"+ObtenerDate(FechaFinal)+  "',FechaIngreso='"+date+   "',Proveedor_idProveedor='"+Integer.toString(cmbProveedor.getSelectedIndex()+1)+     "',Compra_idCompra='"+idCompra+     " 'Where idLotes= "+idmod);
                            pst.executeUpdate();
                            mostrardatos(String.valueOf(idCompra));   

                            JOptionPane.showMessageDialog(null, "Modificado");

                            btnModificar.setVisible(false);
                            cmbMedicamentoID.setSelectedItem(0);
                            cmbProveedor.setSelectedItem(0);

                            jtxtExistencia.setText(null);
                            jtxtCosto.setText(null);
                            jDateFechaCadu.setDate(new Date());
                            
                         }// FIN IF validar 
                     
                         else
                         {
                             JOptionPane.showMessageDialog(null, "Llenar Todos Los Campos", "Error", JOptionPane.ERROR_MESSAGE);
                         }
                     }

                    catch(SQLException e)
                    {
                        //JOptionPane.showMessageDialog(null, "Error"+e);
                    }

                    
                    
                        
            
        }catch(SQLException ex)
        {
          JOptionPane.showMessageDialog(null, "Error"+ex);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnNuevaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaCompraActionPerformed

       jTable1.setEnabled(true);
       cmbMedicamentoID.setEnabled(true);
       cmbProveedor.setEnabled(true);
       
       jtxtExistencia.setEnabled(true);
       jtxtCosto.setEnabled(true);
       jDateFechaCadu.setEnabled(true);  
       btnInsertar.setEnabled(true);
       btnInsertar.setVisible(true);
       btnTerminarCom.setVisible(true);
       
       btnCancelarCo.setVisible(true);
       insertarCompra();
       obtenerUltimaCom();
       btnNuevaCompra.setEnabled(false);
       
    }//GEN-LAST:event_btnNuevaCompraActionPerformed

    private void jmenuModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuModificarActionPerformed
        btnInsertar.setVisible(false);
        
       
        int fila=jTable1.getSelectedRow();
        
        int k =  JOptionPane.showConfirmDialog(null, "Modificar compra ?", "Modificar", JOptionPane.YES_NO_OPTION);
               
       if(k==JOptionPane.YES_OPTION)
        {
                if (fila==-1) 
                {
                   JOptionPane.showMessageDialog(null, "Seleccione una casilla", "Error", JOptionPane.ERROR_MESSAGE);
                }
               else{
                    
                   jtxtExistencia.setEnabled(true);
                   jtxtCosto.setEnabled(true);
                   cmbMedicamentoID.setEnabled(true);
                   btnModificar.setVisible(true);
                   btnModificar.setEnabled(true);

                   //--------------------------------------------
                   cmbMedicamentoID.setSelectedItem(jTable1.getValueAt(fila,1));
                   jtxtExistencia.setText(jTable1.getValueAt(fila,2).toString());
                   jtxtCosto.setText(jTable1.getValueAt(fila,3).toString());
                   jDateFechaCadu.setDate(new Date());
                   cmbMedicamentoID.setSelectedItem(jTable1.getValueAt(fila,3));

               }

       }// fin if si o no 
       
    }//GEN-LAST:event_jmenuModificarActionPerformed

    private void jmenuEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuEliminarActionPerformed

        try{

            Connection cn=Conexion.conectar();
            int fila=jTable1.getSelectedRow();
            String cod="";
            
            int k =  JOptionPane.showConfirmDialog(null, "Eliminar compra?", "Eliminar", JOptionPane.YES_NO_OPTION);
               
             if(k==JOptionPane.YES_OPTION)
             {
                    if(fila==-1)
                    {
                        JOptionPane.showMessageDialog(null, "Seleccione una casilla", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        cod=jTable1.getValueAt(fila,0).toString();
                    }


                        PreparedStatement pst=cn.prepareStatement("DELETE FROM Lote WHERE idLotes='"+cod+"'");
                        pst.executeUpdate();
                        mostrardatos(String.valueOf(cod));   

              }

        }
        catch (SQLException ex)
        {
           JOptionPane.showMessageDialog(null, "Eror"+ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jmenuEliminarActionPerformed

    private void btnTerminarComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarComActionPerformed
        
              
        if (jTable1.getRowCount()==0) {
        JOptionPane.showMessageDialog(null, "La compra Debe tener productos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        else
        {
                  jtxtCosto.setEnabled(false);
                  jtxtExistencia.setEnabled(false);
                  jDateFechaCadu.setEnabled(false);

                  jTable1.setEnabled(false);

                   cmbMedicamentoID.setEnabled(false);
                   cmbMedicamentoID.setSelectedIndex(0);

                   cmbProveedor.setEnabled(false);
                   cmbProveedor.setSelectedIndex(0);



                   btnInsertar.setEnabled(false);
                   btnModificar.setEnabled(false);
                   jTable1.setEnabled(false);

                   JOptionPane.showMessageDialog(null,"Compra Terminada");
                   btnNuevaCompra.setEnabled(true);
          }
        
       
        
        
        DefaultTableModel modelo=new DefaultTableModel();
            modelo.addColumn("No Lote");
            modelo.addColumn("Medicamento");
            modelo.addColumn("Existencia");
            modelo.addColumn("Costo");
            modelo.addColumn("Fecha De Caducidad");
            modelo.addColumn("Fecha De Ingreso");
            modelo.addColumn("Proveedor");

            
            
            
            jTable1.setModel(modelo);
        
        

        
        
    }//GEN-LAST:event_btnTerminarComActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        Menu otro = new Menu();
        otro.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnCancelarCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCoActionPerformed
               
        
        try {
            Connection cn=Conexion.conectar();  
                        
          int x = jTable1.getRowCount()-1;
          
            if (x==-1) {
              int Ultim = obtenerUltimaCom(); // Se obtiene la compra que se genero
              PreparedStatement pst=cn.prepareStatement("DELETE FROM Compra WHERE idCompra='"+Ultim+"'");// Se borra la compra
              pst.executeUpdate(); // Manda la instruccion
              JOptionPane.showMessageDialog(null, "Compra Cancelada");              
            }
            else{            
                    while (x >=0)
                   {
                       String cod=jTable1.getValueAt(x,0).toString(); // Se usa el valor 0,0 porque es el ID
                       PreparedStatement pst=cn.prepareStatement("DELETE FROM Lote WHERE idLotes='"+cod+"'"); // Se borra con el ID
                       pst.executeUpdate();//Manda la instruccion
                       x--;             
                   }
                    int Ultim = obtenerUltimaCom(); // Se obtiene la compra que se genero
                     PreparedStatement pst=cn.prepareStatement("DELETE FROM Compra WHERE idCompra='"+Ultim+"'");// Se borra la compra
                     pst.executeUpdate(); // Manda la instruccion
                     mostrardatos(String.valueOf(Ultim)); // Muestra los datos (tiene que estar vacio para confirmar que si borro)
                     JOptionPane.showMessageDialog(null, "Compra Cancelada");

            }

        
       jTable1.setEnabled(false);
       cmbMedicamentoID.setEnabled(false);
       cmbProveedor.setEnabled(false);
       
       jtxtExistencia.setEnabled(false);
       jtxtExistencia.setText(null);
       
       jtxtCosto.setEnabled(false);
       jtxtCosto.setText(null);
       
       jDateFechaCadu.setEnabled(false);
       
       btnInsertar.setVisible(false);
       btnModificar.setVisible(false);

       btnTerminarCom.setVisible(false);
       
       btnCancelarCo.setVisible(false);

       btnNuevaCompra.setEnabled(true);
        
       
        
        } 
        catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Eror"+ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btnCancelarCoActionPerformed

     
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
            java.util.logging.Logger.getLogger(NuevaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new NuevaCompra(user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarCo;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaCompra;
    private javax.swing.JButton btnTerminarCom;
    private javax.swing.JComboBox<String> cmbMedicamentoID;
    private javax.swing.JComboBox<String> cmbProveedor;
    private com.toedter.calendar.JDateChooser jDateFechaCadu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JMenuItem jMenuComprar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem jmenuEliminar;
    private javax.swing.JMenuItem jmenuModificar;
    private javax.swing.JPopupMenu jpopOpciones;
    private javax.swing.JTextField jtxtCosto;
    private javax.swing.JTextField jtxtExistencia;
    // End of variables declaration//GEN-END:variables
}
