/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmira
 */
public class ManejoUsuarios {
    Connection conexionDB;

    public ManejoUsuarios() {
    }
    
    public ArrayList<Usuario> getUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            conexionDB = Conexion.conectar();//se conecta a la base de datos
            String consulta = "SELECT us.idUsuario, us.Nombre, acc.Codigo, acc.Nombre FROM usuario us INNER JOIN accesos acc ON us.Accesos_id = acc.id";//se consulta la info de acceso
            PreparedStatement pre = conexionDB.prepareStatement(consulta);//se prepara la consulta
            ResultSet resultado = pre.executeQuery();//se ejecuta la consulta
            while(resultado.next()){
                usuarios.add(new Usuario(resultado.getInt(3), resultado.getInt(1), resultado.getString(2), resultado.getString(4)));
            }
            conexionDB.close();
            return usuarios;
        } catch (SQLException ex) {
            Logger.getLogger(ManejoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }
    
    public ArrayList<Acceso> getAcceso(){
        ArrayList<Acceso> accesos = new ArrayList<>();
        try {
            conexionDB = Conexion.conectar();//se conecta a la base de datos
            String consulta = "SELECT id, Codigo, Nombre FROM accesos";//se consulta la info de acceso
            PreparedStatement pre = conexionDB.prepareStatement(consulta);//se prepara la consulta
            ResultSet resultado = pre.executeQuery();//se ejecuta la consulta
            while(resultado.next()){
                accesos.add(new Acceso(resultado.getInt(1), resultado.getInt(2), resultado.getString(3)));
            }
            conexionDB.close();
            return accesos;
        } catch (SQLException ex) {
            Logger.getLogger(ManejoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accesos;
    }
    
    public boolean crearUsuario(String userName, String calve, int acceso){
        try {
            conexionDB = Conexion.conectar();//se conecta a la base de datos
            String consulta = "SELECT crearUsuario(\""+userName+"\",\""+calve+"\","+acceso+");";//se consulta la info de acceso
            PreparedStatement pre = conexionDB.prepareStatement(consulta);//se prepara la consulta
            ResultSet resultado = pre.executeQuery();//se ejecuta la consulta
            int creacion = 0;
            if(resultado.next()){
                creacion=resultado.getInt(1);
            }
            conexionDB.close();
            if (creacion==1) {
                return true;
            }else return false;
        } catch (SQLException ex) {
            Logger.getLogger(ManejoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public int restaurarConstraseña(String userName, String clave, int adminId){
        int result = 0;
        
        try {
            conexionDB = Conexion.conectar();//se conecta a la base de datos
            String consulta = "select restaurarClave(\""+userName+"\", \""+clave+"\", "+adminId+");";//se consulta la info de acceso
            PreparedStatement pre = conexionDB.prepareStatement(consulta);//se prepara la consulta
            ResultSet resultado = pre.executeQuery();//se ejecuta la consulta
            result = 0;
            if(resultado.next()){
                result=resultado.getInt(1);
            }
            conexionDB.close();

            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ManejoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean cambiarConstraseña(Usuario user, String claveAnterior, String claveNueva){
        int result = 0;
        
        Login log = new Login();
        result = log.login(user.getUserName(), claveAnterior);
        
        if(result!=1){
            return false;
        }else{
             try {
                conexionDB = Conexion.conectar();//se conecta a la base de datos
                String consulta = "UPDATE usuario SET contraseña = md5(\""+claveNueva+"\") WHERE idUsuario = "+user.getUserId()+";";//se consulta la info de acceso
                PreparedStatement pre = conexionDB.prepareStatement(consulta);//se prepara la consulta
                result = pre.executeUpdate();//se ejecuta la consulta
                if(result>0){
                    return true;
                }
                conexionDB.close();

                return false;
            } catch (SQLException ex) {
                Logger.getLogger(ManejoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    public boolean modificarAcceso(Usuario user, int Acceso){
        
        try {
           conexionDB = Conexion.conectar();//se conecta a la base de datos
           String consulta = "UPDATE usuario SET Accesos_id ="+Acceso+" WHERE idUsuario = "+user.getUserId()+";";//se consulta la info de acceso
           PreparedStatement pre = conexionDB.prepareStatement(consulta);//se prepara la consulta
           int result = pre.executeUpdate();//se ejecuta la consulta
           if(result>0){
               return true;
           }
           conexionDB.close();

           return false;
       } catch (SQLException ex) {
           Logger.getLogger(ManejoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        return false;
    }
    
}
