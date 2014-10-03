/*   1:    */ package Parser;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.DriverManager;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.ResultSetMetaData;
/*   8:    */ import java.sql.SQLException;
/*   9:    */ import java.sql.Statement;
/*  10:    */ import java.sql.Timestamp;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import org.apache.log4j.Logger;
/*  15:    */ 
/*  16:    */ public class DataBase
/*  17:    */   implements IDatabaseWorkable
/*  18:    */ {
/*  19: 14 */   static Logger log = Logger.getLogger(Main.class.getName());
/*  20: 15 */   static Properties properties = new Properties();
/*  21:    */   Connection SQLconnection;
/*  22: 17 */   private List<List> dataForSearch = new ArrayList();
/*  23: 18 */   private List<List> dataForCsvFile = new ArrayList();
/*  24: 19 */   private int tempRequestId = 0;
/*  25: 19 */   private int parametersId = 0;
/*  26:    */   
/*  27:    */   public DataBase()
/*  28:    */   {
/*  29:    */     try
/*  30:    */     {
/*  31: 24 */       this.SQLconnection = setConnection();
/*  32:    */     }
/*  33:    */     catch (Exception e)
/*  34:    */     {
/*  35: 26 */       e.printStackTrace();
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Connection setConnection()
/*  40:    */     throws Exception
/*  41:    */   {
/*  42: 32 */     properties.readProperties();
/*  43: 33 */     Connection connection = null;
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47: 37 */     String connectionUrl = "jdbc:sqlserver:" + properties.getDatabaseHost() + ";databaseName=" + properties.getDatabaseName() + ";user=" + properties.getDatabaseLogin() + ";password=" + properties.getDatabasePassword();
/*  48:    */     try
/*  49:    */     {
/*  50:    */       try
/*  51:    */       {
/*  52: 41 */         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
/*  53:    */       }
/*  54:    */       catch (ClassNotFoundException e)
/*  55:    */       {
/*  56: 43 */         e.printStackTrace();
/*  57:    */       }
/*  58: 45 */       connection = DriverManager.getConnection(connectionUrl);
/*  59:    */     }
/*  60:    */     catch (SQLException e)
/*  61:    */     {
/*  62: 48 */       log.error(e.toString());
/*  63:    */     }
/*  64: 50 */     return connection;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List<List> putXmlDataToDataBase(List<List<String>> xmlFileData)
/*  68:    */     throws SQLException
/*  69:    */   {
/*  70: 56 */     PreparedStatement preparedStatement = null;
/*  71: 57 */     Statement statement = null;
/*  72: 58 */     ResultSet resultSet = null;
/*  73: 59 */     int count = 0;
/*  74: 60 */     String sqlQuery = "";String city = "";String category = "";String itemName = "";String owner = "";String sort = "";String urlString = "";
/*  75: 61 */     log.info("Try to put xml data into database");
/*  76:    */     try
/*  77:    */     {
/*  78: 64 */       sqlQuery = "insert into Request values (GETDATE())";
/*  79: 65 */       statement = this.SQLconnection.createStatement();
/*  80: 66 */       statement.execute(sqlQuery);
/*  81: 67 */       sqlQuery = "select top 1 request_id from Request order by request_date desc";
/*  82: 68 */       resultSet = statement.executeQuery(sqlQuery);
/*  83: 69 */       while (resultSet.next()) {
/*  84: 70 */         this.tempRequestId = resultSet.getInt(1);
/*  85:    */       }
/*  86:    */     }
/*  87:    */     catch (SQLException e)
/*  88:    */     {
/*  89: 73 */       log.error(e.toString());
/*  90:    */     }
/*  91: 76 */     for (int i = 0; i < xmlFileData.size(); i++) {
/*  92:    */       try
/*  93:    */       {
/*  94: 78 */         sqlQuery = "insert into Request_Parameters values (?, ?, ?, ?, ?, ?, ?, ?)";
/*  95: 79 */         city = (String)((List)xmlFileData.get(i)).get(0);
/*  96: 80 */         category = (String)((List)xmlFileData.get(i)).get(1);
/*  97: 81 */         itemName = (String)((List)xmlFileData.get(i)).get(2);
/*  98: 82 */         owner = (String)((List)xmlFileData.get(i)).get(3);
/*  99: 83 */         sort = (String)((List)xmlFileData.get(i)).get(4);
/* 100: 84 */         count = Integer.parseInt((String)((List)xmlFileData.get(i)).get(5));
/* 101: 85 */         urlString = URLWork.createUrl(city, category, itemName, owner, sort);
/* 102: 86 */         preparedStatement = this.SQLconnection.prepareStatement(sqlQuery);
/* 103: 87 */         preparedStatement.setInt(1, this.tempRequestId);
/* 104: 88 */         preparedStatement.setString(2, city);
/* 105: 89 */         preparedStatement.setString(3, category);
/* 106: 90 */         preparedStatement.setString(4, itemName);
/* 107: 91 */         preparedStatement.setString(5, owner);
/* 108: 92 */         preparedStatement.setString(6, sort);
/* 109: 93 */         preparedStatement.setInt(7, count);
/* 110: 94 */         preparedStatement.setString(8, urlString);
/* 111: 95 */         preparedStatement.execute();
/* 112: 96 */         this.dataForSearch.add(new ArrayList());
/* 113: 97 */         ((List)this.dataForSearch.get(i)).add(urlString);
/* 114: 98 */         ((List)this.dataForSearch.get(i)).add(Integer.valueOf(count));
/* 115:    */       }
/* 116:    */       catch (SQLException e)
/* 117:    */       {
/* 118:100 */         log.error(e.toString());
/* 119:101 */         return null;
/* 120:    */       }
/* 121:    */     }
/* 122:104 */     return this.dataForSearch;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void putSearchResultToDataBase(List<List> searchResult)
/* 126:    */     throws SQLException
/* 127:    */   {
/* 128:109 */     PreparedStatement preparedStatement = null;
/* 129:110 */     Statement statement = null;
/* 130:111 */     ResultSet resultSet = null;
/* 131:112 */     String sqlQuery = "";String itemAdName = "";String itemDescription = "";String sellerName = "";String sellerPhone = "";String itemUrl = "";
/* 132:113 */     int price = 0;
/* 133:    */     
/* 134:115 */     log.info("Try to put item result data into database");
/* 135:    */     try
/* 136:    */     {
/* 137:118 */       sqlQuery = "select parameters_id from Request_Parameters where request_id = ?";
/* 138:119 */       preparedStatement = this.SQLconnection.prepareStatement(sqlQuery);
/* 139:120 */       preparedStatement.setInt(1, this.tempRequestId);
/* 140:121 */       resultSet = preparedStatement.executeQuery();
/* 141:122 */       while (resultSet.next())
/* 142:    */       {
/* 143:123 */         int rowId = resultSet.getRow() - 1;
/* 144:124 */         for (int j = 0; j < ((List)searchResult.get(rowId)).size(); j++)
/* 145:    */         {
/* 146:125 */           sqlQuery = "insert into Results values (?, ?, ?, ?, ?, ?, ?, ?)";
/* 147:126 */           preparedStatement = this.SQLconnection.prepareStatement(sqlQuery);
/* 148:127 */           this.parametersId = resultSet.getInt(1);
/* 149:128 */           itemAdName = (String)((List)((List)searchResult.get(rowId)).get(j)).get(0);
/* 150:129 */           itemDescription = (String)((List)((List)searchResult.get(rowId)).get(j)).get(1);
/* 151:130 */           sellerName = (String)((List)((List)searchResult.get(rowId)).get(j)).get(2);
/* 152:131 */           sellerPhone = (String)((List)((List)searchResult.get(rowId)).get(j)).get(3);
/* 153:132 */           Date publicDate = (Date)((List)((List)searchResult.get(rowId)).get(j)).get(4);
/* 154:133 */           price = ((Integer)((List)((List)searchResult.get(rowId)).get(j)).get(5)).intValue();
/* 155:134 */           itemUrl = (String)((List)((List)searchResult.get(rowId)).get(j)).get(6);
/* 156:    */           
/* 157:136 */           preparedStatement.setInt(1, this.parametersId);
/* 158:137 */           preparedStatement.setString(2, itemAdName);
/* 159:138 */           preparedStatement.setString(3, itemDescription);
/* 160:139 */           preparedStatement.setString(4, sellerName);
/* 161:140 */           preparedStatement.setString(5, sellerPhone);
/* 162:141 */           Timestamp timestamp = new Timestamp(publicDate.getTime());
/* 163:142 */           preparedStatement.setTimestamp(6, timestamp);
/* 164:143 */           preparedStatement.setInt(7, price);
/* 165:144 */           preparedStatement.setString(8, itemUrl);
/* 166:145 */           preparedStatement.execute();
/* 167:    */         }
/* 168:    */       }
/* 169:    */     }
/* 170:    */     catch (SQLException e)
/* 171:    */     {
/* 172:149 */       log.error(e.toString());
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<List> getDataforCsvFile()
/* 177:    */     throws SQLException
/* 178:    */   {
/* 179:155 */     PreparedStatement preparedStatement = null;
/* 180:156 */     ResultSet resultSet = null;
/* 181:157 */     String sqlQuery = "";
/* 182:    */     
/* 183:    */ 
/* 184:160 */     log.info("Try to get search result from database");
/* 185:    */     try
/* 186:    */     {
/* 187:163 */       sqlQuery = "SELECT C.* FROM Results C LEFT OUTER JOIN Request_Parameters B ON (C.parameters_id = B.parameters_id)LEFT OUTER JOIN Request A ON (A.request_id = B.request_id)WHERE A.request_id = ?";
/* 188:164 */       preparedStatement = this.SQLconnection.prepareStatement(sqlQuery);
/* 189:165 */       preparedStatement.setInt(1, this.tempRequestId);
/* 190:166 */       resultSet = preparedStatement.executeQuery();
/* 191:167 */       int collumnCount = resultSet.getMetaData().getColumnCount();
/* 192:168 */       int i = 0;
/* 193:169 */       while (resultSet.next())
/* 194:    */       {
/* 195:170 */         this.dataForCsvFile.add(new ArrayList());
/* 196:171 */         int j = 3;
/* 197:172 */         while (j <= collumnCount) {
/* 198:173 */           ((List)this.dataForCsvFile.get(i)).add(resultSet.getString(j++));
/* 199:    */         }
/* 200:175 */         i++;
/* 201:    */       }
/* 202:    */     }
/* 203:    */     catch (SQLException e)
/* 204:    */     {
/* 205:178 */       log.error(e.toString());
/* 206:    */     }
/* 207:180 */     return this.dataForCsvFile;
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.DataBase
 * JD-Core Version:    0.7.0.1
 */