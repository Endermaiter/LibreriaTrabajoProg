package libreriatrabajoprog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Libreria {

    public static final String url = "jdbc:mysql://localhost:3306/trabajoprog";
    public static final String userName = "root";
    public static final String password = "marcosydavid1.";
    public static PreparedStatement ps = null;
    public static DefaultTableModel modelo;
    public static ResultSet rs;
    public static Libreria instancia;
    public static Connection con;

    public static Libreria getInstance(){
        if(instancia == null){
            instancia = new Libreria();
        }
        return instancia;
    }
    
    public static Connection establecerConexionBD() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public static void llenarTabla(JTable tabla) {

        try {

            //base de datos
            con = getInstance().establecerConexionBD();
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM reservas");
            rs = ps.executeQuery();

            //tabla
            modelo = (DefaultTableModel) tabla.getModel();

            int cantidadColl = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object rowDatos[] = new Object[cantidadColl];
                for (int i = 0; i < cantidadColl; i++) {
                    rowDatos[i] = rs.getString(i + 1);
                }
                modelo.addRow(rowDatos);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
}
