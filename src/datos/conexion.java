package datos;

import java.sql.*;
import java.io.*;
import java.util.Properties;

/**
 *
 * @author apys
 */
public class conexion {

    public static Connection con;
    
    public static Connection Conexion()  {
        String cnn = "";
        Properties propiedades = new Properties();
        InputStream entrada = null;
        try {

            entrada = new FileInputStream("propiedades.properties");
            propiedades.load(entrada);
            cnn = propiedades.getProperty("cnn");
            } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://" + cnn;

            //"//LEAL\\SQLEXPRESS:0; databaseName=LOTENAL;user=sa;password=manager;"
            con = DriverManager.getConnection(connectionUrl);
            //System.err.println("Conexion exitosa!"); 

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        }
        return con;
    }



    public static ResultSet execquery(String query) throws SQLException {
        Conexion();
        Statement statement = con.createStatement();
        con.close();
        return statement.executeQuery(query);

    }

    public static boolean execnonquery(String query) throws SQLException {
        try {
            Conexion();           
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
/**
 * 
 * @param sp formato = "{call nombre_sp(?,?,?)}  ?-> uno por cada parametro que tengamos en el stored
 * @param valores arreglo de valor para los parametros del stored, en el oreden que esten en el stored
 * @param tipoValor Arreglo con los el tipo de dato del parametro "s" = string, "i" = integer
 * @param err Retorna mensaje de error si acaso entra en el catch{}
 * @return false= error, true = ok
 * @throws SQLException 
 */
    public static boolean exec_sp(String sp, Object[]valores, String[] tipoValor, String err) throws SQLException {
        try {
            Conexion();
            int cont = 1;
            CallableStatement cmd = con.prepareCall(sp);
            for (int i = 0; i < valores.length; i++) {
                if ("s".equals(tipoValor[i].toLowerCase())) {
                    cmd.setString(cont, (String) valores[i]);
                    cont++;
                } else if ("i".equals(tipoValor[i].toLowerCase())) {
                    cmd.setInt(cont, (int)valores[i]);
                    cont++;
                }
            }
            cmd.execute();
            con.close();
            return true;
        } catch (SQLException e) {
            err = ("Error al ejecutar sp" + e.toString());
            return false;
        }

    }

    /**
     *
     * @param sp Formato = "{call nombre_sp(?,?,?)}" ? <- Uno por cada parametro
     * que tengamos en el stored
     * @param valores Arreglo de valor para los parametros del stored, en el
     * oreden que esten en el stored
     * @param tipoValor Arreglo con los el tipo de dato del parametro "s" = string, "i" = integer
     * @param err Retorna mensaje de error si acaso entra en el catch{}
     * @return false= error, true = ok
     * @throws SQLException
     */
    public static ResultSet exp_sp(String sp, Object[]valores, String[] tipoValor, String err) throws SQLException {
        ResultSet rs = null;
        Conexion();
         int cont = 1;
        CallableStatement cmd = con.prepareCall("{" + sp + "}");
          for (int i = 0; i < valores.length; i++) {
                if ("s".equals(tipoValor[i].toLowerCase())) {
                    cmd.setString(cont, (String) valores[i]);
                    cont++;
                } else if ("i".equals(tipoValor[i].toLowerCase())) {
                    cmd.setInt(cont, (int)valores[i]);
                    cont++;
                }
            }
        try{
        rs = cmd.executeQuery();
        }catch(SQLException e){
        err = ("Error al ejecutar sp " + e.toString());
        }
        con.close();
        return rs;
    }
}
