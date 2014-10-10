package Parser;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase
        implements IDatabaseWorkable {
    static Logger log = Logger.getLogger(Main.class.getName());
    static Properties properties = new Properties();
    Connection SQLconnection;
    private List<List> dataForSearch = new ArrayList();
    private List<List> dataForCsvFile = new ArrayList();
    private int tempRequestId = 0;
    private int parametersId = 0;

    public DataBase() {
        try {
            this.SQLconnection = setConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection setConnection()
            throws Exception {
        properties.readProperties();
        Connection connection = null;


        String connectionUrl = "jdbc:sqlserver:" + properties.getDatabaseHost() + ";databaseName=" + properties.getDatabaseName() + ";user=" + properties.getDatabaseLogin() + ";password=" + properties.getDatabasePassword();
        try {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            log.error(e.toString());
        }
        return connection;
    }

    public List<List> putXmlDataToDataBase(List<List<String>> xmlFileData)
            throws SQLException {
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        String sqlQuery = "";
        String city = "";
        String category = "";
        String itemName = "";
        String owner = "";
        String sort = "";
        String urlString = "";
        log.info("Try to put xml data into database");
        try {
            sqlQuery = "insert into Request values (GETDATE())";
            statement = this.SQLconnection.createStatement();
            statement.execute(sqlQuery);
            sqlQuery = "select top 1 request_id from Request order by request_date desc";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                this.tempRequestId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.toString());
        }
        for (int i = 0; i < xmlFileData.size(); i++) {
            try {
                sqlQuery = "insert into Request_Parameters values (?, ?, ?, ?, ?, ?, ?, ?)";
                city = (String) ((List) xmlFileData.get(i)).get(0);
                category = (String) ((List) xmlFileData.get(i)).get(1);
                itemName = (String) ((List) xmlFileData.get(i)).get(2);
                owner = (String) ((List) xmlFileData.get(i)).get(3);
                sort = (String) ((List) xmlFileData.get(i)).get(4);
                count = Integer.parseInt((String) ((List) xmlFileData.get(i)).get(5));
                urlString = URLWork.createUrl(city, category, itemName, owner, sort);
                preparedStatement = this.SQLconnection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, this.tempRequestId);
                preparedStatement.setString(2, city);
                preparedStatement.setString(3, category);
                preparedStatement.setString(4, itemName);
                preparedStatement.setString(5, owner);
                preparedStatement.setString(6, sort);
                preparedStatement.setInt(7, count);
                preparedStatement.setString(8, urlString);
                preparedStatement.execute();
                this.dataForSearch.add(new ArrayList());
                ((List) this.dataForSearch.get(i)).add(urlString);
                ((List) this.dataForSearch.get(i)).add(Integer.valueOf(count));
            } catch (SQLException e) {
                log.error(e.toString());
                return null;
            }
        }
        return this.dataForSearch;
    }

    public void putSearchResultToDataBase(List<List> searchResult)
            throws SQLException {
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "";
        String itemAdName = "";
        String itemDescription = "";
        String sellerName = "";
        String sellerPhone = "";
        String itemUrl = "";
        int price = 0;

        log.info("Try to put item result data into database");
        try {
            sqlQuery = "select parameters_id from Request_Parameters where request_id = ?";
            preparedStatement = this.SQLconnection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, this.tempRequestId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int rowId = resultSet.getRow() - 1;
                for (int j = 0; j < ((List) searchResult.get(rowId)).size(); j++) {
                    sqlQuery = "insert into Results values (?, ?, ?, ?, ?, ?, ?, ?)";
                    preparedStatement = this.SQLconnection.prepareStatement(sqlQuery);
                    this.parametersId = resultSet.getInt(1);
                    itemAdName = (String) ((List) ((List) searchResult.get(rowId)).get(j)).get(0);
                    itemDescription = (String) ((List) ((List) searchResult.get(rowId)).get(j)).get(1);
                    sellerName = (String) ((List) ((List) searchResult.get(rowId)).get(j)).get(2);
                    sellerPhone = (String) ((List) ((List) searchResult.get(rowId)).get(j)).get(3);
                    Date publicDate = (Date) ((List) ((List) searchResult.get(rowId)).get(j)).get(4);
                    price = ((Integer) ((List) ((List) searchResult.get(rowId)).get(j)).get(5)).intValue();
                    itemUrl = (String) ((List) ((List) searchResult.get(rowId)).get(j)).get(6);

                    preparedStatement.setInt(1, this.parametersId);
                    preparedStatement.setString(2, itemAdName);
                    preparedStatement.setString(3, itemDescription);
                    preparedStatement.setString(4, sellerName);
                    preparedStatement.setString(5, sellerPhone);
                    Timestamp timestamp = new Timestamp(publicDate.getTime());
                    preparedStatement.setTimestamp(6, timestamp);
                    preparedStatement.setInt(7, price);
                    preparedStatement.setString(8, itemUrl);
                    preparedStatement.execute();
                }
            }
        } catch (SQLException e) {
            log.error(e.toString());
        }
    }

    public List<List> getDataforCsvFile()
            throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "";


        log.info("Try to get search result from database");
        try {
            sqlQuery = "SELECT C.* FROM Results C LEFT OUTER JOIN Request_Parameters B ON (C.parameters_id = B.parameters_id)LEFT OUTER JOIN Request A ON (A.request_id = B.request_id)WHERE A.request_id = ?";
            preparedStatement = this.SQLconnection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, this.tempRequestId);
            resultSet = preparedStatement.executeQuery();
            int collumnCount = resultSet.getMetaData().getColumnCount();
            int i = 0;
            while (resultSet.next()) {
                this.dataForCsvFile.add(new ArrayList());
                int j = 3;
                while (j <= collumnCount) {
                    ((List) this.dataForCsvFile.get(i)).add(resultSet.getString(j++));
                }
                i++;
            }
        } catch (SQLException e) {
            log.error(e.toString());
        }
        return this.dataForCsvFile;
    }
}