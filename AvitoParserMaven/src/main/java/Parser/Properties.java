package Parser;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Properties {
    static Logger log = Logger.getLogger(Main.class.getName());
    String databaseHost;
    String databaseName;
    String databaseLogin;
    String databasePassword;
    String proxyAddress;
    String proxyPort;
    String tempPath;
    private FileInputStream fileInputStream;
    private java.util.Properties properties = new java.util.Properties();

    public String getProxyPort() {
        return this.proxyPort;
    }

    public String getTempPath() {
        return this.tempPath;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public String getDatabaseHost() {
        return this.databaseHost;
    }

    public String getDatabaseLogin() {
        return this.databaseLogin;
    }

    public String getDatabasePassword() {
        return this.databasePassword;
    }

    public String getProxyAddress() {
        return this.proxyAddress;
    }

    public void readProperties() {
        log.debug("Try to read file config.properties");
        try {
            this.fileInputStream = new FileInputStream("src/main/resources/config.properties");
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        log.debug("Try to load properties");
        try {
            this.properties.load(this.fileInputStream);
            this.databaseHost = this.properties.getProperty("db.host");
            this.databaseName = this.properties.getProperty("db.database");
            this.databaseLogin = this.properties.getProperty("db.login");
            this.databasePassword = this.properties.getProperty("db.password");
            this.proxyAddress = this.properties.getProperty("proxy.address");
            this.proxyPort = this.properties.getProperty("proxy.port");
            this.tempPath = this.properties.getProperty("temp.path");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}