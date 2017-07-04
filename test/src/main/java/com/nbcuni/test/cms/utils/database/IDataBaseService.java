package com.nbcuni.test.cms.utils.database;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface IDataBaseService {
    /**
     *
     * @return Opened {@link Connection} instance
     */
    public Connection getConnection();

    /**
     * Method to perform Connection by SSH
     * @return Opened {@link Connection} instance
     */
    public Connection getSShConnection();

    /**
     * Execute query
     *
     * @param query - query to execute
     * @return - result of query
     */
    public List<Map<String, String>> executeQueryResultAsList(String query);


    /**
     * Execute query using SSH connection
     *
     * @param query - query to execute
     * @return - result of query
     */
    public List<Map<String, String>> executeQueryResultAsListBySsh(String query);

    /**
     * Execute update of table
     *
     * @param query - update to execute
     * @return - number of lines updated
     */
    int executeUpdate(String query);

    /**
     * Close connection
     */
    public void closeConnection();


}