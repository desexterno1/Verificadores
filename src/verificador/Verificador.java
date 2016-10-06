/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verificador;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentacion.pantallaPrecio;

/**
 *
 * @author apys
 */
public class Verificador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        
        //ejmplooo!!!!!!
//        Object[] valores = {"Emmanuel",20,"mexico"};
//        String[] tipo = {"s","i","s"};
//        String error ="";
//        
//     try{
//        
//           if (datos.conexion.exec_sp("{call procinsertarusuario(?,?,?)}", valores,tipo, error)){
//           System.out.println("Resgistro guardado");
//           }
//        } catch (SQLException ex) {
//            Logger.getLogger(pantallaPrecio.class.getName()).log(Level.SEVERE, null, ex);
//        }
//                                            
        
        
        
        new presentacion.pantallaPrecio().setVisible(true);
        
      
        
    }
    
}
