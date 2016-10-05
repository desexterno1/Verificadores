/*
                                                                                                     * To change this license header, choose License Headers in Project Properties.
                                                                                                     * To change this template file, choose Tools | Templates
                                                                                                     * and open the template in the editor.
 */
package verificador.datos;

import java.sql.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author apys
 */
public class conexion {

    private static Connection con;
    protected static String cnn;
    public static Connection Conexion()  {
        Properties propiedades = new Properties();
        InputStream entrada = null;
        try {

            entrada = new FileInputStream("propiedades.properties");
            propiedades.load(entrada);
            cnn = propiedades.getProperty("cnn");
            System.out.println(cnn);
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



    public ResultSet execquery(String query) throws SQLException {
        Conexion();
        Statement statement = con.createStatement();
        return statement.executeQuery(query);

    }

    public boolean execnonquery(String query) throws SQLException {
        try {
            Conexion();
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean exec_sp(String sp, String[] parametros, String[] valores) throws SQLException {
        try {
            Conexion();
            CallableStatement cmd = con.prepareCall("{" + sp + "}");
            for (int i = 0; 0 < parametros.length - 1; i++) {
                cmd.setString(parametros[i], valores[i]);
            }
            cmd.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al ejecutar sp" + e.toString());
            return false;
        }

    }

    public ResultSet exp_sp(String sp, String[] parametros, String[] valores) throws SQLException {
        ResultSet rs;
        Conexion();
        CallableStatement cmd = con.prepareCall("{" + sp + "}");
        for (int i = 0; 0 < parametros.length - 1; i++) {
            cmd.setString(parametros[i], valores[i]);
        }
        rs = cmd.executeQuery();
        return rs;
    }
}
