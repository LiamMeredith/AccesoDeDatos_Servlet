/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Liam-Portatil
 */
public class tableAlumnos implements Table {

    /**
     * Global parameters
     */
    private Connection conn;
    private Statement t;

    /**
     * Constructor that initializes data base
     */
    public tableAlumnos() {
        init();
        try {
            getSelect();
        } catch (SQLException ex) {
            Logger.getLogger(tableAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        conn = new dataBase("colegio").getConn();
        try {
            t = conn.createStatement();
        } catch (SQLException ex) {
            System.err.println("Error interno statement de asignaturas");
        }
    }

    /**
     * Returns query by ID
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public String[] getSelect(String id) throws SQLException {
        String result[] = null;
        ResultSet rs = t.executeQuery("Select * from alumne where codi =" + id + ";");
        if (rs.next()) {
            result = getQuery(rs);
        } else {
            throw new SQLException("Empty");
        }
        return result;
    }

    /**
     * Returns all queries
     * @return
     * @throws SQLException 
     */
    @Override
    public String[][] getSelect() throws SQLException {
        ResultSet rs = t.executeQuery("Select count(*) as counter from alumne;");
        String[][] s;
        int size = 0;
        if (rs.next()) {
            size = rs.getInt("counter");
        }
        s = new String[size][2];
        rs = t.executeQuery("Select * from alumne;");
        String[] query = null;
        for (int i = 0; i < size && rs.next(); i++) {
            query = getQuery(rs);
            for (int j = 0; j < 2; j++) {
                s[i][j] = query[j];
            }
        }
        return s;
    }

    /**
     * 
     * @param id
     * @param sql
     * @throws SQLException 
     */
    @Override
    public void insertQuery(String id, String sql) throws SQLException {
        ResultSet rs = t.executeQuery("Select * from alumne where codi =" + id + ";");
        if (rs.next()) {
            throw new SQLException("Exists");
        } else {
            t.executeUpdate("insert into alumne values(" + id + "," + sql + " );");
        }
    }

    /**
     * 
     * @param id
     * @throws SQLException 
     */
    @Override
    public void deleteQuery(String id) throws SQLException {
        getSelect(id);
        t.executeUpdate("delete from alumne where codi ='" + id + "';");
    }

    /**
     * 
     * @param id
     * @param sql
     * @throws SQLException 
     */
    @Override
    public void updateQuery(String id, String sql) throws SQLException {
        getSelect(id);
        t.executeUpdate("update alumne set " + sql + "where codi = '" + id + "';");
    }

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    @Override
    public String[] getQuery(ResultSet rs) throws SQLException {
        String[] result = new String[2];
        result[0] = rs.getString("codi");
        result[1] = rs.getString("nom");
        return result;
    }

    /**
     * 
     */
    @Override
    public void close() {
        try {
            t.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("ERROR: closing db");
        }
    }

    @Override
    public void importFile(String path, String fields) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String[][] getAsignaturas(String id) throws SQLException {
        ResultSet rs = t.executeQuery("Select count(*) as counter from alumne inner join tutoriaalumne on alumne.codi = tutoriaalumne.codiAlumne inner join \n" +
"	tutoria on tutoria.codi = tutoriaalumne.codiTutoria inner join assignatura \n" +
"    	on assignatura.codi = tutoria.codiAssignatura where alumne.codi = " + id + " group by assignatura.codi ;");
        String[][] s;
        int size = 0;
        if (rs.next()) {
            size = rs.getInt("counter");
        }
        s = new String[size][3];
        rs = t.executeQuery("Select alumne.nom, assignatura.nom, tutoria.nom from alumne inner join tutoriaalumne on alumne.codi = tutoriaalumne.codiAlumne inner join \n" +
"	tutoria on tutoria.codi = tutoriaalumne.codiTutoria inner join assignatura \n" +
"    	on assignatura.codi = tutoria.codiAssignatura where alumne.codi = " + id + " group by assignatura.codi ;");
        String[] query = null;
        for (int i = 0; i < size && rs.next(); i++) {
            query = getQueryInnerJoin(rs);
            for (int j = 0; j < 3; j++) {
                s[i][j] = query[j];
            }
        }
        return s;
    }
    
        public String[][] getTutorias(String id) throws SQLException {
        ResultSet rs = t.executeQuery("Select count(*) as counter from alumne inner join tutoriaalumne on alumne.codi = tutoriaalumne.codiAlumne inner join \n" +
"	tutoria on tutoria.codi = tutoriaalumne.codiTutoria inner join assignatura \n" +
"    	on assignatura.codi = tutoria.codiAssignatura where alumne.codi = " + id + " group by tutoria.codi ;");
        String[][] s;
        int size = 0;
        if (rs.next()) {
            size = rs.getInt("counter");
        }
        s = new String[size][3];
        rs = t.executeQuery("Select alumne.nom, assignatura.nom, tutoria.nom from alumne inner join tutoriaalumne on alumne.codi = tutoriaalumne.codiAlumne inner join \n" +
"	tutoria on tutoria.codi = tutoriaalumne.codiTutoria inner join assignatura \n" +
"    	on assignatura.codi = tutoria.codiAssignatura where alumne.codi = " + id + " group by tutoria.codi ;");
        String[] query = null;
        for (int i = 0; i < size && rs.next(); i++) {
            query = getQueryInnerJoin(rs);
            for (int j = 0; j < 3; j++) {
                s[i][j] = query[j];
            }
        }
        return s;
    }
    
        public String[] getQueryInnerJoin(ResultSet rs) throws SQLException {
        String[] result = new String[3];
        result[0] = rs.getString("alumne.nom");
        result[1] = rs.getString("assignatura.nom");
        result[2] = rs.getString("tutoria.nom");
        return result;
    }

}
