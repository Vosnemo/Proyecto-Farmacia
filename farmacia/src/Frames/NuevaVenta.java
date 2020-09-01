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
import java.util.Vector;
import javax.swing.ImageIcon;

import seguridad.Conexion;
import seguridad.Usuario;

/**
 *
 * @author GuillePC
 */
public class NuevaVenta extends javax.swing.JFrame {
    private static Usuario user;//variable global del usuario logeado

    /**
     * Creates new form Lotes
     */
    
   
     public NuevaVenta(Usuario user) {
         
        this.user=user;//Se asigna el usuaario que hizo login
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/índice.png")).getImage());
         
        initComponents();      
        this.setLocationRelativeTo(null);
        
        
        LlenarCmbMedi();
        LlenarcmbCliente(); 

        jtxtCantidad.setEnabled(false);
      
        btnInsertar.setVisible(false);
        btnTerminarVent.setVisible(false);
        btnCancelarCo.setVisible(false);
        btnElimProduc.setVisible(false);
        
        cmbMedicamentoID.setEnabled(false);
   
        
    
    }
   
     DefaultTableModel modelo=new DefaultTableModel()
            {
                @Override
                public boolean isCellEditable(int fil, int col) {
                    return col==8;
                }
            
            };
     Object[] ObjectTabla = new Object[99999];
     
     
        
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
            int UltimVe=obtenerUltimaVent();
            
            modelo.addColumn("Medicamento");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Subtotal");


            
            
            jTable1.setModel(modelo);
            String sql="";
            
            if (valor.equals(""))
            {         
             sql=" select med.Nombre, ped.Cantidad_Medi, (ped.Cantidad_Medi * med.PrecioInt) as subtotal from pedido ped inner join lote lot on ped.Lote_idLotes = lot.idLotes inner join medicamento med on lot.Medicamento_idMedicamento = med.idMedicamento  where (ped.Venta_idVenta = '"+UltimVe+"' ) ";
            }
        
            String []datos=new String [6];
            
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
               
                                        
                    modelo.addRow(datos);
                }
                           
              
           
        }catch(SQLException ex){
           
          JOptionPane.showMessageDialog(null, "Error" +ex);
        }
    }
 
     
private int Validar()
    {
       
        if ( jtxtCantidad.getText().length()==0) 
        {
          JOptionPane.showMessageDialog(null, "Ingrese una cantidad", "Error", JOptionPane.ERROR_MESSAGE);
          return 1;
        }
        else if (radExt.isSelected()==false && radInter.isSelected()==false ) 
        {
          JOptionPane.showMessageDialog(null, "Seleccionar un precio", "Error", JOptionPane.ERROR_MESSAGE);
          return 1;

        }

        else
        {
            return 0;
        }
    }
    
   private void EliminarVenta( String idMedi, int Cant)
   {
   
        try {
            int idventa=obtenerUltimaVent();
            idMedi=idMedi.substring(0, 1);

            
            Connection cn=Conexion.conectar();
            String sql ="";
            sql = " select lot.idLotes,  ped.Cantidad_Medi, med.idMedicamento, ped.idPedido from pedido ped inner join lote lot on ped.Lote_idLotes =  lot.idLotes inner join medicamento med on lot.Medicamento_idMedicamento = med.idMedicamento where (ped.Cantidad_Medi ="+Cant+" and med.idMedicamento ="+idMedi+" and ped.Venta_idVenta ="+idventa+") ";
            String []datos=new String [6];
          
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                
                while(rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);                 
                    
                }
              
              PreparedStatement pst=cn.prepareStatement("UPDATE  Lote  set lote.Existencia = (Lote.Existencia + '"+datos[1]+"' ) where (lote.idLotes = '"+datos[0]+"' ) ");
              pst.executeUpdate();
              
              PreparedStatement pst2=cn.prepareStatement("UPDATE  Pedido  set Pedido.Cantidad_Medi = '0'  where (pedido.idPedido = '"+datos[3]+"' ) ");
              pst2.executeUpdate();
            
            
        } // Fin try
        catch (SQLException ex) 
        {
         JOptionPane.showMessageDialog(null, "Error"+ex);
        } // Fin Catch

   
   
   
   }
    
   private void LlenarcmbCliente()
    {
        try{
            Connection cn=Conexion.conectar();
            String sql="";
            
            
            sql="select * from Cliente ";
            
            String []datos=new String [4];
        
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                while(rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);                    
                    
                    cmbClientes.addItem(datos[0]+".  "+datos[1]);
                    
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
            
            sql="select med.idMedicamento, med.Nombre from lote lot inner join medicamento med on lot.Medicamento_idMedicamento = med.idMedicamento where lot.Existencia>0 group by med.idMedicamento;";

            String []datos=new String [4];
           
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                while(rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    
                    cmbMedicamentoID.addItem(datos[0]+".  "+datos[1]);
                }
                
        }
        
        catch(SQLException ex)
        {
          JOptionPane.showMessageDialog(null, "Error"+ex);
        }

    }
    
      
 private void InsertarPedido (String valor, int CantVenta )
 {
        try {
            int IdVenta= obtenerUltimaVent();
            
            
            int CantNuev;
            Connection cn=Conexion.conectar();
            String sql ="Select lote.idLotes, lote.existencia from lote where (lote.Medicamento_idMedicamento = '"+valor+"' and lote.Existencia >0 ) order by lote.FechaCaducidad ASC ;";
               
            String []datos=new String [3];
          
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
             while((CantVenta>0)&&(rs.next()))
               {  
                   
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                                
                    if (CantVenta>Integer.parseInt( datos[1] )) 
                    {
                        CantVenta=CantVenta-Integer.parseInt( datos[1] );                

                     PreparedStatement pst=cn.prepareStatement("INSERT INTO Pedido(Lote_idLotes,Cantidad_Medi,Venta_idVenta) VALUES(?,?,?)");

                    
                      pst.setInt(1,Integer.parseInt(datos[0]));
                      pst.setInt(2,Integer.parseInt(datos[1]));
                      pst.setInt(3,IdVenta);

                      PreparedStatement pst2=cn.prepareStatement("UPDATE  Lote  set lote.Existencia = '0' where (lote.idLotes = '"+datos[0]+"' ) ");
                      pst2.executeUpdate();
                      

                      int a=pst.executeUpdate();
                      if(a>0)
                      {
                        // JOptionPane.showMessageDialog(null,"Venta Generada");

                      }

                      else
                      {
                          JOptionPane.showMessageDialog(null,"Error");
                      }
                      
                        
                    }
                    else if(CantVenta<=Integer.parseInt( datos[1] ) &&(CantVenta>0))
                    {
                        CantNuev=(Integer.parseInt(datos[1])-CantVenta);
                        
                     PreparedStatement pst=cn.prepareStatement("INSERT INTO Pedido(Lote_idLotes,Cantidad_Medi,Venta_idVenta) VALUES(?,?,?)");

                    
                      pst.setInt(1,Integer.parseInt(datos[0]));
                      pst.setInt(2,CantVenta);
                      pst.setInt(3,IdVenta);
                    
                      PreparedStatement pst2=cn.prepareStatement("UPDATE  Lote  set lote.Existencia = "+CantNuev+" where (lote.idLotes = '"+datos[0]+"' ) ");
                      pst2.executeUpdate();
                      

                      int a=pst.executeUpdate();
                      if(a>0)
                      {
                        // JOptionPane.showMessageDialog(null,"Venta Generada"); 
                      }

                      else
                      {
                          JOptionPane.showMessageDialog(null,"Error");
                      }   
                        
                    break;
                    }// FIn Else NO ES SUFICIENTE
                    
                                    
               }           
            
            
        } 
        
        catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Error "+ex);

        }

 
 }
     
 private void insertarVenta()
 {
        try {
            Connection cn=Conexion.conectar();
            java.util.Date d = new java.util.Date(); 
            java.sql.Date date = new java.sql.Date(d.getTime());
            PreparedStatement pst=cn.prepareStatement("INSERT INTO Venta(Usuario_idUsuario,Cliente_idCliente,Fecha,Total,Anulado) VALUES(?,?,?,?,?)");
                  
                      //pst.setInt(1,user.getUserId());
                      pst.setInt(1,2);
                      pst.setInt(2,Integer.parseInt(cmbClientes.getSelectedItem().toString().substring(0, 1) ));
                      pst.setDate(3,date);
                      pst.setInt(4,1);
                      pst.setBoolean(5, false);


                      int a=pst.executeUpdate();
                      if(a>0)
                      {
                        // JOptionPane.showMessageDialog(null,"Venta Generada");
                        
                      }

                      else
                      {
                          JOptionPane.showMessageDialog(null,"Error al Generar Venta");
                      }
           

           
       
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" +ex);
           
        }
 
 }
       
 private int obtenerUltimaVent()
 {
     try{
            Connection cn=Conexion.conectar();
            
                String sql="Select * from Venta";
                
                String idVent="";  
                int iD;
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                
                while(rs.next())
                {
                    idVent=rs.getString(1);             
                  
                }
                iD=Integer.parseInt(idVent);
                return iD;
     }
        catch(SQLException ex)
        {
          JOptionPane.showMessageDialog(null, "Error "+ex);
          return 0;
        }
     
                
 }
 
 private void actualizarventa()
 {
        try {
            Connection cn=Conexion.conectar();
        int idCliente=0;
        idCliente=Integer.parseInt( cmbClientes.getSelectedItem().toString().substring(0, 1));
        double tot = SumarSubtotal();
        int idVent=obtenerUltimaVent();//---------------------------------------------------------------------------------ACAAAA---CORREGIR EL USUARIO
        PreparedStatement pst=cn.prepareStatement("UPDATE Venta SET Usuario_idUsuario='"+user.getUserId()+"',Cliente_idCliente='"+ idCliente +"',Total='"+tot+"',Anulado='"+0+"'  WHERE idVenta='"+idVent+"' ");
        pst.executeUpdate();
        } 
        catch (SQLException ex) 
        {
          JOptionPane.showMessageDialog(null, "Error "+ex);
        }

 }
 
  
private String ObtenerPrecio(String valor, int preci)
{
        try {
            Connection cn=Conexion.conectar();
             String sql="";
            
             if (preci ==1)
             {
                 sql="select Medicamento.PrecioInt from Medicamento WHERE (idMedicamento='"+valor+"')";
             }
                
             else
             {
                sql="select Medicamento.PrecioExt from Medicamento WHERE (idMedicamento='"+valor+"')";
          
             }
             
             
            // String []datos=new String [4];
            String datos = "";
        
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                while(rs.next())
                {
                    datos=rs.getString(1);                          
                }
        
                return datos;
        
        }// Fin TRY 
        
        
        
        catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error"+ex);
             return "";
        }



}

private String ObtenerCantMax (String valor )
{

     try 
     {
     Connection cn=Conexion.conectar();      
      
      String sql="select sum(lot.Existencia) as existencias from medicamento med inner join lote lot on med.idMedicamento = lot.Medicamento_idMedicamento WHERE (med.idMedicamento='"+valor+"')";
                
                String CantMax="";  
                Statement st=cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                
                
                while(rs.next())
                {
                    CantMax=rs.getString(1);           
                }                   

     return CantMax;
     
     }
     catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error"+ex);
      return "";

     }
            

}

private double SumarSubtotal()
{
    int filas = jTable1.getRowCount()-1;
    double Total=0;
    double Tot=0;
    
    
    while(filas>=0)
    {
        double sub = Double.parseDouble( jTable1.getValueAt(filas, 2).toString() ) ;
        Total=Total+sub;
        filas--;
    }
    jtxtTotal.setText(String.valueOf(Total));
    return Total;
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
        jmenuEliminar = new javax.swing.JMenuItem();
        buttonGroup = new javax.swing.ButtonGroup();
        jtxtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnNuevaVenta = new javax.swing.JButton();
        btnTerminarVent = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        jLabelMenu = new javax.swing.JLabel();
        btnCancelarCo = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cmbClientes = new javax.swing.JComboBox<>();
        btnInsertar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jtxtTotal = new javax.swing.JTextField();
        radInter = new javax.swing.JRadioButton();
        radExt = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        cmbMedicamentoID = new javax.swing.JComboBox<>();
        jtxtCantMax = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnElimProduc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jmenuEliminar.setText("Eliminar");
        jmenuEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuEliminarActionPerformed(evt);
            }
        });
        jpopOpciones.add(jmenuEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NUEVA VENTA");
        setMinimumSize(new java.awt.Dimension(1440, 650));
        setName("Nueva Venta"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);
        getContentPane().add(jtxtCantidad);
        jtxtCantidad.setBounds(1150, 230, 50, 30);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Medicamento");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(1140, 150, 76, 16);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicamento", "Cantidad", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setComponentPopupMenu(jpopOpciones);
        jTable1.setFocusable(false);
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 10, 1120, 508);

        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/NuevaCompraBlanco.png"))); // NOI18N
        btnNuevaVenta.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: ##003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Nueva Venta</h2>\n\t</div>\n</body>\n</html>");
        btnNuevaVenta.setBorderPainted(false);
        btnNuevaVenta.setContentAreaFilled(false);
        btnNuevaVenta.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/NuevaCompraNegro.png"))); // NOI18N
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevaVenta);
        btnNuevaVenta.setBounds(1140, 10, 50, 50);

        btnTerminarVent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ComprarBlanco.png"))); // NOI18N
        btnTerminarVent.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Vender\n</h2>\n\t</div>\n</body>\n</html>");
        btnTerminarVent.setBorderPainted(false);
        btnTerminarVent.setContentAreaFilled(false);
        btnTerminarVent.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ComprarNegro.png"))); // NOI18N
        btnTerminarVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarVentActionPerformed(evt);
            }
        });
        getContentPane().add(btnTerminarVent);
        btnTerminarVent.setBounds(1150, 410, 60, 60);

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
        btnCancelarCo.setBounds(1200, 410, 60, 50);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cliente");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(1140, 70, 76, 16);

        cmbClientes.setEnabled(false);
        getContentPane().add(cmbClientes);
        cmbClientes.setBounds(1140, 90, 118, 22);

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
        btnInsertar.setBounds(1160, 320, 50, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(1040, 530, 30, 17);

        jtxtTotal.setEnabled(false);
        getContentPane().add(jtxtTotal);
        jtxtTotal.setBounds(1090, 530, 40, 30);

        buttonGroup.add(radInter);
        radInter.setForeground(new java.awt.Color(255, 255, 255));
        radInter.setText("Interior");
        radInter.setContentAreaFilled(false);
        radInter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radInterActionPerformed(evt);
            }
        });
        getContentPane().add(radInter);
        radInter.setBounds(1300, 70, 107, 25);

        buttonGroup.add(radExt);
        radExt.setForeground(new java.awt.Color(255, 255, 255));
        radExt.setText("Exterior");
        radExt.setContentAreaFilled(false);
        radExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radExtActionPerformed(evt);
            }
        });
        getContentPane().add(radExt);
        radExt.setBounds(1300, 100, 120, 25);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cantidad");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(1150, 210, 54, 17);

        cmbMedicamentoID.setEnabled(false);
        cmbMedicamentoID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMedicamentoIDActionPerformed(evt);
            }
        });
        getContentPane().add(cmbMedicamentoID);
        cmbMedicamentoID.setBounds(1140, 170, 120, 22);

        jtxtCantMax.setEnabled(false);
        getContentPane().add(jtxtCantMax);
        jtxtCantMax.setBounds(1370, 230, 40, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cantidad Maxima");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(1320, 210, 120, 17);

        btnElimProduc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/XBlanca.png"))); // NOI18N
        btnElimProduc.setToolTipText("<html>\n<head>\n\t<style>\n\t\t #contenido{ \n\t\tbackground: #003333;  /*Se le da un color de fondo*/\n\t\tcolor: white;\t\t  /*Color a la letra*/\n\t\t}\n\t</style>\n</head>\n<body>\n\t<div id=contenido>\n\t\t<h2>Eliminar Producto\n</h2>\n\t</div>\n</body>\n</html>");
        btnElimProduc.setBorderPainted(false);
        btnElimProduc.setContentAreaFilled(false);
        btnElimProduc.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/XNegra.png"))); // NOI18N
        btnElimProduc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElimProducActionPerformed(evt);
            }
        });
        getContentPane().add(btnElimProduc);
        btnElimProduc.setBounds(1210, 310, 60, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/wpblue.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1460, 690);

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
            
      
       
       jTable1.setEnabled(true);
       cmbMedicamentoID.setEnabled(true);
       cmbClientes.setEnabled(true);

       
       jtxtCantidad.setEnabled(true);
 
       btnInsertar.setEnabled(true);
       btnInsertar.setVisible(true);
       btnTerminarVent.setVisible(true);       
       btnCancelarCo.setVisible(true);
       btnElimProduc.setVisible(true);
       
       radExt.setEnabled(true);
       radInter.setEnabled(true);
       
       insertarVenta();
       btnNuevaVenta.setEnabled(false);
       
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void jmenuEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuEliminarActionPerformed
        
                
         int fila=jTable1.getSelectedRow();


            String idMedi="";
            int cantMedi=0;
            if(fila==-1)
            {
                JOptionPane.showMessageDialog(null, "Seleccione una casilla", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                idMedi=jTable1.getValueAt(fila,0).toString();
                cantMedi=  Integer.parseInt(jTable1.getValueAt(fila,1).toString() );
                
                int k =  JOptionPane.showConfirmDialog(null, "Eliminar producto?", "Eliminar", JOptionPane.YES_NO_OPTION);

                if(k==JOptionPane.YES_OPTION)
                {
                    
                    EliminarVenta(idMedi, cantMedi);
                    
                    int x = jTable1.getRowCount()-1;
                    
                    modelo.removeRow(fila);
                    SumarSubtotal();
                    
                }
     
                
                
            }

          
        
    }//GEN-LAST:event_jmenuEliminarActionPerformed

    private void btnTerminarVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarVentActionPerformed
        
        actualizarventa();
        int x=jTable1.getRowCount()-1; // Se obtiene el numero de filas -1 porque empieza en 0
     
        if (x==-1) {
        JOptionPane.showMessageDialog(null, "La Venta Debe tener productos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        else
        {
                  jtxtCantidad.setEnabled(false);
                  jTable1.setEnabled(false);

                   cmbMedicamentoID.setEnabled(false);
                   cmbMedicamentoID.setSelectedIndex(0);

                   btnInsertar.setEnabled(false);
                   
                   btnCancelarCo.setVisible(false);
                   btnElimProduc.setVisible(false);
                   btnTerminarVent.setVisible(false);
                   btnInsertar.setVisible(false);
                   
                   jTable1.setEnabled(false);
                  
                   System.out.println("1 "+x);
                   while (x>=0) 
                   {                
                     
                       modelo.removeRow(x);
                       x--;
                   }
                   
                   jtxtTotal.setText(null);
                   JOptionPane.showMessageDialog(null,"Venta Terminada");
                   btnNuevaVenta.setEnabled(true);
          }
        
       
        
    }//GEN-LAST:event_btnTerminarVentActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        Menu otro = new Menu();
        otro.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnCancelarCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCoActionPerformed
               
        
        try {
            Connection cn=Conexion.conectar();  
                        
            int x=jTable1.getRowCount()-1; // Se obtiene el numero de filas -1 porque empieza en 0
            int x2=x;
            int Ultim = obtenerUltimaVent(); // Se obtiene la venta que se genero
            
            int k =  JOptionPane.showConfirmDialog(null, "Cancelar Venta?", "Cancelar", JOptionPane.YES_NO_OPTION);

                if(k==JOptionPane.YES_OPTION)
                {
                
                        
                        if (x==-1) {
                          PreparedStatement pst=cn.prepareStatement("DELETE FROM Venta WHERE idVenta='"+Ultim+"'");// Se borra la compra
                          pst.executeUpdate(); // Manda la instruccion
                          JOptionPane.showMessageDialog(null, "Venta Cancelada");              
                        }
                        else{       

                            while  (x2>=0)
                            {
                                  String idMedi=jTable1.getValueAt(x2,0).toString();
                                  int cantMedi =  Integer.parseInt(jTable1.getValueAt(x2,1).toString());
                                  EliminarVenta(idMedi, cantMedi);
                                  x2--;

                            }
                                while (x >=0)
                               {

                                   PreparedStatement pst=cn.prepareStatement("DELETE FROM Pedido WHERE Venta_idVenta='"+Ultim+"'"); // Se borra con el ID
                                   pst.executeUpdate();//Manda la instruccion
                                   modelo.removeRow(x);
                                   x--;      
                               }

                                 PreparedStatement pst=cn.prepareStatement("DELETE FROM Venta WHERE idVenta='"+Ultim+"'");// Se borra la compra
                                 pst.executeUpdate(); // Manda la instruccion
                             jtxtTotal.setText(null);
                             JOptionPane.showMessageDialog(null, "Venta Cancelada");

                        }


                   jTable1.setEnabled(false);
                   cmbMedicamentoID.setEnabled(false);
                //   cmbProveedor.setEnabled(false);

                   jtxtCantidad.setEnabled(false);
                   jtxtCantidad.setText(null);

                   btnInsertar.setVisible(false);

                   btnTerminarVent.setVisible(false);

                   btnCancelarCo.setVisible(false);
                   btnElimProduc.setVisible(false);

                   btnNuevaVenta.setEnabled(true);


                    
                    
                    
                    
                    
                    
                    
                }
            
        
        
        
        
        
        } 
        catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Eror"+ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btnCancelarCoActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed

       
        double precio;
      

                
         
         int cantMax= Integer.parseInt( ObtenerCantMax(cmbMedicamentoID.getSelectedItem().toString() )  );
         jtxtCantMax.setText(String.valueOf(cantMax));
         
       
        if (Validar()==0) 
        {
         cmbClientes.setEnabled(false);
         radExt.setEnabled(false);
         radInter.setEnabled(false);
            
               if (Integer.parseInt(jtxtCantidad.getText()) > cantMax) 
                   {
                     JOptionPane.showMessageDialog(null, "No hay existencias suficientes para esta venta ", "Error", JOptionPane.ERROR_MESSAGE);        
                   } 
               
                   else 
                   {

                              if (radInter.isSelected()) 
                                 {
                                   precio = Double.parseDouble ( ObtenerPrecio(cmbMedicamentoID.getSelectedItem().toString().substring(0, 1), 1));
                                 }
                                 else
                                 {
                                     precio = Double.parseDouble ( ObtenerPrecio(cmbMedicamentoID.getSelectedItem().toString().substring(0, 1), 0));
                                 }


                                 if (jTable1.getRowCount()-1==-1) 
                                 {
                                      modelo =new DefaultTableModel();
                                       modelo.addColumn("Medicamento"); // se envian los titulos de la tabla
                                       modelo.addColumn("Cantidad");    
                                       modelo.addColumn("Subtotal");
                                       jTable1.setModel(modelo); // se  le envia el modelo


                                      ObjectTabla[0]=cmbMedicamentoID.getSelectedItem().toString(); // Se agrega el medicamento
                                      ObjectTabla[1]= jtxtCantidad.getText(); // Se agrega la cantidad
                                      ObjectTabla[2]= (Double.parseDouble( jtxtCantidad.getText())*precio);// Se agrega el subtotal
                                      modelo.addRow(ObjectTabla); // se agrega toda la fila
                                     jTable1.setModel(modelo); // se envia el modelo 
                                 }

                                else
                                     {
                                      ObjectTabla[0]=cmbMedicamentoID.getSelectedItem().toString(); // Se agrega el medicamento
                                      ObjectTabla[1]= jtxtCantidad.getText(); // Se agrega la cantidad
                                      ObjectTabla[2]= (Integer.parseInt( jtxtCantidad.getText())*precio); // Se obtiene el precio de venta y se multiplica por la cantidad
                                      modelo.addRow(ObjectTabla); // se agrega toda la fila
                                      jTable1.setModel(modelo); // se envia el modelo 
                                     }
                             SumarSubtotal();


                                InsertarPedido(cmbMedicamentoID.getSelectedItem().toString().substring(0, 1), Integer.parseInt( jtxtCantidad.getText() ) );
                              //  CantMax= Integer.parseInt( ObtenerCantMax(cmbMedicamentoID.getSelectedItem().toString().substring(0, 1)) );
                              //  jtxtCantMax.setText( ObtenerCantMax(cmbMedicamentoID.getSelectedItem().toString().substring(0, 1)) );

                                 jtxtCantidad.setText("");
                                 cantMax= Integer.parseInt( ObtenerCantMax(cmbMedicamentoID.getSelectedItem().toString() )  );
                                 jtxtCantMax.setText(String.valueOf(cantMax));



                   }
            
            
            
            
            
        }// FIn IF VALIDAR
       
              

    }//GEN-LAST:event_btnInsertarActionPerformed

    private void radInterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radInterActionPerformed


    }//GEN-LAST:event_radInterActionPerformed

    private void radExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radExtActionPerformed


    }//GEN-LAST:event_radExtActionPerformed

    private void cmbMedicamentoIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMedicamentoIDActionPerformed
        
  
        
    }//GEN-LAST:event_cmbMedicamentoIDActionPerformed

    private void btnElimProducActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElimProducActionPerformed
        
                
         int fila=jTable1.getSelectedRow();


            String idMedi="";
            int cantMedi=0;
            if(fila==-1)
            {
                JOptionPane.showMessageDialog(null, "Seleccione una casilla", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                idMedi=jTable1.getValueAt(fila,0).toString();
                cantMedi=  Integer.parseInt(jTable1.getValueAt(fila,1).toString() );
                
                int k =  JOptionPane.showConfirmDialog(null, "Eliminar producto?", "Eliminar", JOptionPane.YES_NO_OPTION);

                if(k==JOptionPane.YES_OPTION)
                {
                    
                    EliminarVenta(idMedi, cantMedi);
                    
                    int x = jTable1.getRowCount()-1;
                    
                    modelo.removeRow(fila);
                    SumarSubtotal();
                                        
                }
                
                
            }

          
                
  
    }//GEN-LAST:event_btnElimProducActionPerformed

     
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
            java.util.logging.Logger.getLogger(NuevaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new NuevaVenta(user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarCo;
    private javax.swing.JButton btnElimProduc;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnTerminarVent;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JComboBox<String> cmbClientes;
    private javax.swing.JComboBox<String> cmbMedicamentoID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem jmenuEliminar;
    private javax.swing.JPopupMenu jpopOpciones;
    private javax.swing.JTextField jtxtCantMax;
    private javax.swing.JTextField jtxtCantidad;
    private javax.swing.JTextField jtxtTotal;
    private javax.swing.JRadioButton radExt;
    private javax.swing.JRadioButton radInter;
    // End of variables declaration//GEN-END:variables
}
