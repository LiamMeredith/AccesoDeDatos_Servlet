/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Interface that allows an easy implementation of SQLTables
 * 
 * @author Liam-Portatil
 */
public interface Table {
    
   /**
    * Method that return the query by ID.
    * @param id
    * @return
    * @throws SQLException 
    */
    public String[] getSelect(String id) throws SQLException;

    /**
     * Methods that returns all of the table
     * @return
     * @throws SQLException 
     */
    public String[][] getSelect() throws SQLException;
    
    /**
     * Inserts query in table
     * @param id
     * @param sql
     * @throws SQLException 
     */
    public void insertQuery(String id, String sql) throws SQLException;

    /**
     * Deletes query from table
     * @param id
     * @throws SQLException 
     */
    public void deleteQuery(String id) throws SQLException;

    /**
     * Updates query from table
     * @param id
     * @param sql
     * @throws SQLException 
     */
    public void updateQuery(String id, String sql) throws SQLException;

    /**
     * Returns a user friendly query
     * @param rs
     * @return
     * @throws SQLException 
     */
    public String[] getQuery(ResultSet rs) throws SQLException;
    
    /**
     * Closes db
     */
    public void close();
    
    /**
     * Imports queries by file csv
     */
    public void importFile(String path, String fields) throws SQLException;
}
