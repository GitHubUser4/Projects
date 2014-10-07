package Parser;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    static Logger log = Logger.getLogger(Main.class.getName());

    public static void execute(String xmlFilePath, String csvFilePath)
            throws Exception {
        List<List> searchItemDescriptionResult = new ArrayList();


        Files file = new Files(xmlFilePath, csvFilePath);
        List<List<String>> xmlFileData = file.loadXmlFileData(xmlFilePath);

        DataBase dataBase = new DataBase();


        List<List> dataForSearch = dataBase.putXmlDataToDataBase(xmlFileData);


        URLWork urlWork = new URLWork();
        for (int i = 0; i < dataForSearch.size(); i++) {
            String urlString = String.valueOf(((List) dataForSearch.get(i)).get(0));
            Integer count = (Integer) ((List) dataForSearch.get(i)).get(1);
            System.out.println("Trying to parse first " + count + " result for " + urlString);
            List<String> searchItemResult = urlWork.getResultUrlList(urlString, count.intValue());

            searchItemDescriptionResult.add(new ArrayList());
            for (int j = 0; j < searchItemResult.size(); j++) {
                System.out.println(Thread.currentThread().getName() + ". Gathering information for: " + (j + 1) + " item");
                ((List) searchItemDescriptionResult.get(i)).add(urlWork.getResultItem((String) searchItemResult.get(j)));
                Thread.sleep(5000L);
            }
        }
        dataBase.putSearchResultToDataBase(searchItemDescriptionResult);


        List<List> dataForCsvFile = dataBase.getDataforCsvFile();
        file.saveCsvFileData(dataForCsvFile);
    }
}