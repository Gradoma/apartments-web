package by.gradomski.apartments.pool;

import java.util.Properties;
import java.util.ResourceBundle;

class PropertiesHandler {
    private static final String PROPERTIES_PATH = "db/database";
    private ResourceBundle resource = ResourceBundle.getBundle(PROPERTIES_PATH);

    String getUrl(){
        return resource.getString("db.url");
    }

    Properties getProperties(){
        String driver = resource.getString("db.driver");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        String autoReconnect = resource.getString("db.autoReconnect");
        String characterEncoding = resource.getString("db.characterEncoding");
        String useEncoding = resource.getString("db.useEncoding");
        String useSSL = resource.getString("db.useSSL");
        String useJDBCCompliantTimezoneShift = resource.getString("db.useJDBCCompliantTimezoneShift");
        String useLegacyDatetimeCode = resource.getString("db.useLegacyDatetimeCode");
        String serverTimezone = resource.getString("db.serverTimezone");
        String serverSslCert = resource.getString("db.serverSslCert");
        Properties properties = new Properties();
        properties.put("db.driver", driver);
        properties.put("user", user);
        properties.put("password", pass);
        properties.put("autoReconnect", autoReconnect);
        properties.put("characterEncoding", characterEncoding);
        properties.put("useEncoding", useEncoding);
        properties.put("useSSL", useSSL);
        properties.put("useJDBCCompliantTimezoneShift", useJDBCCompliantTimezoneShift);
        properties.put("useLegacyDatetimeCode", useLegacyDatetimeCode);
        properties.put("serverTimezone", serverTimezone);
        properties.put("serverSslCert", serverSslCert);
        return properties;
    }
}
