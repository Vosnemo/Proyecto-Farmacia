/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad;

/**
 *Clase de tipo usuario utilizado para el manejo de acceso en el programa
 * @author aliciarroyave
 */
public class Usuario {
    private int nivelAcceso, userId;//almacena el codigo de nivel de acceso
    private String UserName, tipoUser;//almacena nombre y tipo de usuario

    public Usuario(int nivelAcceso, int userId, String UserName, String tipoUser) {
        this.nivelAcceso = nivelAcceso;
        this.userId = userId;
        this.UserName = UserName;
        this.tipoUser = tipoUser;
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public String getUserName() {
        return UserName;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public int getUserId() {
        return userId;
    }
    
}
