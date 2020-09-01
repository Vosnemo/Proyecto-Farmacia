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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Clase encargada del manejo de la seguridad de la base de datos
 * @author Aliciarroyave
 */
public class Login {
    Connection conexionDB;
    
    //consturctor genreal
    public Login() {
        
    }
    
    /**
     * metodo que hace logion
     * @param nombreUsuario
     * @param clave
     * @return 1 si se realiza el logeo con exito, 0 si la contraseña es incorrecta, -1 si el usuario no existe
     */
    public int login(String nombreUsuario, String clave){
        int res=0;
        try {
            conexionDB = Conexion.conectar();//se conecta al la base de datos
            String consulta = "SELECT login(\""+nombreUsuario+"\",\""+clave+"\");";//se utiliza el metodo almacenado en la base de datos para hacer login
                        
            PreparedStatement pre = conexionDB.prepareStatement(consulta);//se prepara la consulta
            ResultSet resultado = pre.executeQuery();//se ejecuata la consulta
            if(resultado.next()){//se verifica que retorne algo
                res = resultado.getInt(1);// se obtiene le resueltado
            }
            conexionDB.close();//se cierra la conexion
            return res;//se retorna el resultado
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    /**
     * clase que obtiene la informacion del usaurio 
     * @param userName
     * @return un objeto de la clase usuario, que contiene la infromacion del nivel de acceso del usaurio
     */
    public Usuario getUsuarioInfo(String userName){
        Usuario usuario = null;
        
        try {
            conexionDB = Conexion.conectar();//se conecta a la base de datos
            String consulta = "SELECT acc.Codigo, acc.Nombre, us.idUsuario FROM usuario us INNER JOIN accesos acc ON us.Accesos_id = acc.id WHERE us.Nombre=\""+userName+"\";";//se consulta la info de acceso
            PreparedStatement pre = conexionDB.prepareStatement(consulta);//se prepara la consulta
            ResultSet resultado = pre.executeQuery();//se ejecuta la consulta
            if(resultado.next()){//se verifica el reusltado
                usuario = new Usuario(resultado.getInt(1), resultado.getInt(3),userName, resultado.getString(2));//se crea un objeto de la case Usuario con la info consultada a la base
            }
            conexionDB.close();//se cierra la conexion
            return usuario;//retorna el usuario
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usuario;
    }
    
    
}
