/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;
import java.sql.Connection;
/**
 *
 * @author Trevisan
 */
public class TesteConexão {
    
    public static void main (String args[]){
        Connection con = ConnectionFactory.getConnection();
        
        if (con != null) {
            System.out.println("Conectado com sucesso!");
            System.out.println("Um Commit Inesperado");
        }
    }
}
