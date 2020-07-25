package by.gradomski.apartments.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
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
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> busyConnections;
    private String url;
    private Properties properties;

    private ConnectionPool(){
        PropertiesHandler propHandler = new PropertiesHandler();
        url = propHandler.getUrl();
        properties = propHandler.getProperties();
        String driver = properties.getProperty(DRIVER);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
        freeConnections = new LinkedBlockingQueue<ProxyConnection>(DEFAULT_POOL_SIZE);
        busyConnections = new ArrayDeque<>();
        createConnections(DEFAULT_POOL_SIZE);
        int size = freeConnections.size();
        log.debug("pool size=" + size);
        if(!checkTotalAmount()){
            log.info("pool doesn't have enough connections=" + size);
            createConnections(DEFAULT_POOL_SIZE - size);
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

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
        } catch (InterruptedException e) {
            log.error("InterruptedException while taking a connection: ", e);
            Thread.currentThread().interrupt();
            log.error(Thread.currentThread().toString() + " was interrupted");
        }
        busyConnections.add(connection);
        return connection;
    }

    public void releaseConnection(Connection connection){
        if(connection.getClass() != ProxyConnection.class){
            log.warn("try to release not original ProxyConnection");
        }
        busyConnections.remove(connection);
        freeConnections.offer((ProxyConnection) connection);
        if(!checkTotalAmount()){
            log.info("free connections=" + freeConnections.size());
            log.info("busy connections=" + busyConnections.size());
            int amountToCreate = DEFAULT_POOL_SIZE - freeConnections.size() - busyConnections.size();
            createConnections(amountToCreate);
        }
    }

    public void destroyPool(){
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++){
            try {
                freeConnections.take().closeReally();
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

    private boolean checkTotalAmount(){
        return busyConnections.size() + freeConnections.size() == DEFAULT_POOL_SIZE;
    }

    private void createConnections(int amount){
        for (int i = 0; i < amount; i++){
            try {
                ProxyConnection proxyConnection = new ProxyConnection(DriverManager.getConnection(url, properties));
                freeConnections.add(proxyConnection);
            } catch (SQLException throwables) {
                log.error(throwables);
            }
        }
    }
}
