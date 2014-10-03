/*  1:   */ package Parser;
/*  2:   */ 
/*  3:   */ import java.io.FileInputStream;
/*  4:   */ import java.io.FileNotFoundException;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.log4j.Logger;
/*  7:   */ 
/*  8:   */ public class Properties
/*  9:   */ {
/* 10:13 */   static Logger log = Logger.getLogger(Main.class.getName());
/* 11:   */   private FileInputStream fileInputStream;
/* 12:15 */   private java.util.Properties properties = new java.util.Properties();
/* 13:   */   String databaseHost;
/* 14:   */   String databaseName;
/* 15:   */   String databaseLogin;
/* 16:   */   String databasePassword;
/* 17:   */   String proxyAddress;
/* 18:   */   String proxyPort;
/* 19:   */   String tempPath;
/* 20:   */   
/* 21:   */   public String getProxyPort()
/* 22:   */   {
/* 23:25 */     return this.proxyPort;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getTempPath()
/* 27:   */   {
/* 28:29 */     return this.tempPath;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getDatabaseName()
/* 32:   */   {
/* 33:33 */     return this.databaseName;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getDatabaseHost()
/* 37:   */   {
/* 38:37 */     return this.databaseHost;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getDatabaseLogin()
/* 42:   */   {
/* 43:41 */     return this.databaseLogin;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String getDatabasePassword()
/* 47:   */   {
/* 48:45 */     return this.databasePassword;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getProxyAddress()
/* 52:   */   {
/* 53:49 */     return this.proxyAddress;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void readProperties()
/* 57:   */   {
/* 58:55 */     log.debug("Try to read file config.properties");
/* 59:   */     try
/* 60:   */     {
/* 61:57 */       this.fileInputStream = new FileInputStream("src/main/resources/config.properties");
/* 62:   */     }
/* 63:   */     catch (FileNotFoundException e)
/* 64:   */     {
/* 65:59 */       log.error(e.getMessage());
/* 66:   */     }
/* 67:61 */     log.debug("Try to load properties");
/* 68:   */     try
/* 69:   */     {
/* 70:63 */       this.properties.load(this.fileInputStream);
/* 71:64 */       this.databaseHost = this.properties.getProperty("db.host");
/* 72:65 */       this.databaseName = this.properties.getProperty("db.database");
/* 73:66 */       this.databaseLogin = this.properties.getProperty("db.login");
/* 74:67 */       this.databasePassword = this.properties.getProperty("db.password");
/* 75:68 */       this.proxyAddress = this.properties.getProperty("proxy.address");
/* 76:69 */       this.proxyPort = this.properties.getProperty("proxy.port");
/* 77:70 */       this.tempPath = this.properties.getProperty("temp.path");
/* 78:   */     }
/* 79:   */     catch (IOException e)
/* 80:   */     {
/* 81:72 */       log.error(e.getMessage());
/* 82:   */     }
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.Properties
 * JD-Core Version:    0.7.0.1
 */