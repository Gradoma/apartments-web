package by.gradomski.apartments.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Base interface of every dao layer interfaces
 */
public interface BaseDao {

    /**
     * Close statement if statement not null
     * @param statement
     */
    default void closeStatement(Statement statement){
        try{
            if(statement != null){
                statement.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    default void closeConnection(Connection connection){
        try{
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e){
            //log
        }
    }
}
