package by.gradomski.apartments.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger();
    private static ConnectionPool instance;
    private static Lock lock = new ReentrantLock();
    private static final int DEFAULT_POOL_SIZE = 32;
    private static final String DRIVER = "db.driver";
    private BlockingQueue<Connection> freeConnections;


    private ConnectionPool(){
        PropertiesHandler propHandler = new PropertiesHandler();
        String url = propHandler.getUrl();
        Properties properties = propHandler.getProperties();
        String driver = properties.getProperty(DRIVER);
        System.out.println(driver);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("driver trouble");
        }
        freeConnections = new LinkedBlockingQueue<Connection>(DEFAULT_POOL_SIZE);
        for(int i = 0; i < DEFAULT_POOL_SIZE; i++){
            try {
                freeConnections.add(DriverManager.getConnection(url, properties));
            } catch (SQLException throwables) {
                log.error(throwables);
            }
        }
        int size = freeConnections.size();
        log.debug("pool size=" + size);
        if (size < DEFAULT_POOL_SIZE){
            for (int i = 0; i < DEFAULT_POOL_SIZE - size; i++){
                try {
                    freeConnections.add(DriverManager.getConnection(url, properties));
                } catch (SQLException throwables) {
                    log.error(throwables);
                }
            }
        }
        if(freeConnections.size() != DEFAULT_POOL_SIZE){
            log.debug("connections after adding =" + freeConnections.size());
            throw new ExceptionInInitializerError("all connection can't be created");
        }
    }

    public static ConnectionPool getInstance(){
        if(instance == null){
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
            return instance;
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
        } catch (InterruptedException e) {
            log.error("InterruptedException while taking a connection: ", e);
            Thread.currentThread().interrupt();
            log.error(Thread.currentThread().toString() + " was interrupted");
        }
        return connection;
    }

    public void releaseConnection(Connection connection){
        freeConnections.offer(connection);
    }

    public void destroyPool(){
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++){
            try {
                freeConnections.take().close();
            } catch (SQLException throwables) {
                log.error("SQLException while closing connection: ", throwables);
            } catch (InterruptedException e) {
                log.error("InterruptedException while closing connection: ", e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers(){
        Enumeration<Driver> e = DriverManager.getDrivers();
        for (Iterator<Driver> iterator = e.asIterator(); iterator.hasNext(); ) {
            try {
                DriverManager.deregisterDriver(iterator.next());
            } catch (SQLException throwables) {
                log.error("can't deregister driver: ", throwables);
            }
        }
    }
}
