/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * Implements Table interface and acceses with credentials
 * 
 * @author Liam-Portatil
 */
public class dataBase {

    /**
     * Global parameters
     */
    private String db;
    private final String user = "root";
    private final String pass = "";
    private Connection conn;

    /**
     * Initializes data base
     * @param db 
     */
    public dataBase(String db){
        init(db);
        initDataBase();
    }
    
    private void init(String db){
        this.db = db;
    }
    
    /**
     * Gets connection and starts Driver
     */
    private void initDataBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("ERROR: Error with the driver");
        }
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + db,
                    user,
                    pass);
        } catch (SQLException ex) {
            System.err.println("ERROR: Error cargando database: " + db);
        }
    }
    
    /**
     * Connector getter
     * @return 
     */
    public Connection getConn(){
        return conn;
    }
}
