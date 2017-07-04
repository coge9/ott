package com.nbcuni.test.cms.utils.database;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.database.mysql.SshEntity;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Hanna_Pazniak
 */
public abstract class DataBaseService implements IDataBaseService {
    public static final String DATA_BASE_FORMAT = "MM/dd/yyyy HH:mm:ss a";
    public static final String DECIMAL_FORMAT = "#####.0000";

    private final ThreadLocal<Connection> threadSafeConnection = new ThreadLocal<Connection>();
    private final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    private final DateFormat timeFormat = new SimpleDateFormat("hh:mma");

    protected void loadJdbcDriver(final String driverName) {
        try {
            Utilities.logInfoMessage("Load JDBC driver '" + driverName + "'");
            Class.forName(driverName);
        } catch (final ClassNotFoundException e) {
            Utilities.logSevereMessageThenFail("Error while loading JDBC '" + driverName + "' driver");
        }
    }

    public Connection getConnection(final String connectionUrl, final String login, final String password) {
        final Connection connection = threadSafeConnection.get();
        try {
            if (null == connection || connection.isClosed()) {
                establishConnection(connectionUrl, login, password);
                return threadSafeConnection.get();
            }
        } catch (final SQLException e) {
            Utilities.logSevereMessageThenFail("Error while connection instance obtaining");
        }
        Utilities.logInfoMessage("Use ready instance of SQL connection: " + connection);
        return connection;
    }


    protected void establishConnection(final String connectionUrl, final String login, final String password) {
        try {
            Utilities.logInfoMessage("Establish SQL connection with DB. Connection string: '" + connectionUrl + "', user: '"
                    + login + "', password: '" + password + "'");
            threadSafeConnection.set(DriverManager.getConnection(connectionUrl, login, password));
        } catch (final SQLException e) {
            closeConnection();
            Utilities.logSevereMessageThenFail("Error while establishing SQL connection with DB");
        }
    }

    public void doSshTunnel(SshEntity sshEntity) {
        try {
            final Properties config = new Properties();
            final JSch jsch = new JSch();
            config.put("kex", "diffie-hellman-group1-sha1,diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha1,diffie-hellman-group-exchange-sha256");
            config.put("StrictHostKeyChecking", "no");
            config.put("ConnectionAttempts", "3");
            Session session = jsch.getSession(sshEntity.getSshUser(), sshEntity.getSshHost(), 22);
            jsch.addIdentity(sshEntity.getSshPassword());
            Utilities.logWarningMessage("Establishing Connection to server");
            session.setConfig(config);

            session.connect();
            Utilities.logInfoMessage("SSH Connected!");
            int assinged_port = session.setPortForwardingL(sshEntity.getLocalPort(), sshEntity.getSshHost(), sshEntity.getDatabasePort());
            Utilities.logInfoMessage("localhost:" + assinged_port + " -> " + sshEntity.getSshHost() + ":" + sshEntity.getDatabasePort());
            Utilities.logInfoMessage("Port Forwarded");

        } catch (JSchException e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }

    }

    /*
     * (non-Javadoc)
     * @see com.nbcuni.test.cms.utils.database.IDataBaseService#closeConnection()
     */
    @Override
    public void closeConnection() {
        final Connection connection = threadSafeConnection.get();
        Utilities.logInfoMessage("Attempt to close SQL connection");
        if (connection != null) {
            try {
                if (!threadSafeConnection.get().isClosed()) {
                    threadSafeConnection.get().close();
                }
                Utilities.logInfoMessage("Connection successfully closed");
            } catch (final SQLException e) {
                Utilities.logSevereMessageThenFail("Error while closing SQL connection");
            } finally {
                threadSafeConnection.set(null);
            }
        } else {
            Utilities.logInfoMessage("Connection not initialized or already closed.");
        }
    }

    @Override
    public List<Map<String, String>> executeQueryResultAsList(final String query) {
        Utilities.logInfoMessage("Execute query by TCP/IP");
        final Connection conn = getConnection();
        return executeQuery(query, conn);
    }

    @Override
    public List<Map<String, String>> executeQueryResultAsListBySsh(final String query) {
        Utilities.logInfoMessage("Execute query by SSH");
        final Connection conn = getSShConnection();
        return executeQuery(query, conn);
    }

    private List<Map<String, String>> executeQuery(final String query, Connection connection) {
        Utilities.logInfoMessage("Execute DML query {" + query + "}");
        List<Map<String, String>> results = new LinkedList<Map<String, String>>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            Utilities.logInfoMessage("Query executed successfully");
            results = parseDataFromResultSet(resultSet);
        } catch (final SQLException e) {
            Utilities.logSevereMessageThenFail("The Query was not executed successfully");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                    Utilities.logSevereMessageThenFail("The result set cannot be closed");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                    Utilities.logSevereMessageThenFail("The prepared statement cannot be closed");
                }
            }
            closeConnection();
        }
        return results;
    }

    @Override
    public int executeUpdate(final String query) {
        int numberOfUpdates = 0;
        Utilities.logInfoMessage("Execute DML query {" + query + "}");
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        final Connection conn = getConnection();
        try {
            statement = conn.prepareStatement(query);
            numberOfUpdates = statement.executeUpdate();
            Utilities.logInfoMessage("Query executed successfully");
        } catch (final SQLException e) {
            Utilities.logSevereMessageThenFail("The Query was not executed successfully");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                    Utilities.logSevereMessageThenFail("The result set cannot be closed");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                    Utilities.logSevereMessageThenFail("The prepared statement cannot be closed");
                }
            }
            closeConnection();
        }
        return numberOfUpdates;
    }

    private List<Map<String, String>> parseDataFromResultSet(final ResultSet resultSet) {
        /* Query results */

        final List<Map<String, String>> queryResult = new LinkedList<Map<String, String>>();
        final String id = RandomStringUtils.randomAlphanumeric(5);
        /* Get column names */
        try {
            final List<String> columns = getColumnNames(resultSet);
            while (resultSet.next()) {
                /*
                 * Current row results. Map<Column Name, Column Value>
                 */
                final Map<String, String> rowData = new HashMap<String, String>(columns.size());
                for (final String column : columns) {

                    final int type = resultSet.getMetaData().getColumnType(resultSet.findColumn(column));
                    String value = null;
                    switch (type) {
                        case Types.DATE: {
                            value = DateFormatUtils.format(resultSet.getDate(column), DATA_BASE_FORMAT);
                            break;
                        }
                        case Types.TIMESTAMP: {
                            final Timestamp timestamp = resultSet.getTimestamp(column);
                            if (timestamp != null) {
                                value = DateFormatUtils.format(timestamp.getTime(), DATA_BASE_FORMAT);
                            }
                            break;
                        }
                        case Types.DECIMAL: {
                            if (resultSet.getString(column) != null) {
                                final DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance();
                                formatter.applyPattern(DECIMAL_FORMAT);
                                value = formatter.format(resultSet.getObject(column));
                            }
                            break;
                        }
                        default: {
                            value = resultSet.getString(column);
                            if (value != null) {
                                if (value.contains("${id}")) {
                                    value = value.replace("${id}", id);
                                }
                                if (value.contains("${image}")) {
                                    value = value.replace("${image}", Config.getInstance().getPathToImages());
                                }
                                if (value.contains("${int}")) {
                                    final int number = SimpleUtils.randomNumber(20, 500);
                                    value = value.replace("${int}", String.valueOf(number));
                                }
                                if (value.contains("${today}")) {
                                    final Calendar cal = Calendar.getInstance();
                                    String date = dateFormat.format(cal.getTime());
                                    if (date.startsWith("0")) {
                                        date = date.substring(1);
                                    }
                                    value = value.replace("${today}", date);
                                }
                                if (value.contains("${currentTime}")) {
                                    final Calendar cal = Calendar.getInstance();
                                    // cal.add(Calendar.MINUTE, 0);
                                    value = value.replace("${currentTime}", timeFormat.format(cal.getTime()));
                                }
                            } else {
                                value = StringUtils.EMPTY;
                            }
                            break;
                        }
                    }// end of switch
                    rowData.put(column, value);
                }// end of loop through columns
                queryResult.add(rowData);

            }
        } catch (final SQLException e) {
            Utilities.logSevereMessageThenFail("Could not get column all data from database/catalog: " + e.getLocalizedMessage());
        }
        return queryResult;
    }

    private List<String> getColumnNames(final ResultSet rs) throws SQLException {
        final ResultSetMetaData rsMetaData = rs.getMetaData();
        final List<String> columns = new ArrayList<String>(rsMetaData.getColumnCount());
        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
            columns.add(rsMetaData.getColumnLabel(i));
        }
        return columns;
    }
}