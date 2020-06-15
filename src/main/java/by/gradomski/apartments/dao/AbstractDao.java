package by.gradomski.apartments.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface AbstractDao {

    default void closeStatement(Statement statement){
        try{
            if(statement != null){
                statement.close();
            }
        } catch (SQLException e){
            // log
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
