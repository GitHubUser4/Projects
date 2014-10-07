package Parser;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Files
        implements IFileWorkable {
    static Logger log = Logger.getLogger(Main.class.getName());
    private String xmlFileName;
    private List<List<String>> xmlFileData;
    private String csvFilePath;

    public Files(String xmlFilePath, String csvFilePath) {
        this.xmlFileName = xmlFilePath;
        this.xmlFileData = null;
        this.csvFilePath = csvFilePath;
    }

    private String getCsvFileName() {
        SimpleDateFormat currentDateTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();
        String csvFileFirstPartOfName = currentDateTime.format(now);
        String csvfileSecondPartOfName = this.xmlFileName.substring(this.xmlFileName.lastIndexOf("\\") + 1, this.xmlFileName.length() - 3) + "csv";
        String csvFileName = this.csvFilePath + csvFileFirstPartOfName + "_" + csvfileSecondPartOfName;
        log.debug("csv file name: " + csvFileName);
        return csvFileName;
    }

    public List<List<String>> loadXmlFileData(String xmlFileName)
            throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XmlParser xmlParser = new XmlParser();
        try {
            log.info("Try to parse file: " + xmlFileName);
            saxParser.parse(new File(xmlFileName), xmlParser);
            return this.xmlFileData = xmlParser.getXMLFileData();
        } catch (SAXException e) {
            log.error(e.toString());
        }
        return null;
    }

    public void saveCsvFileData(List<List> dataForCsvFile)
            throws IOException {
        log.info("Try to save data to csv file");
        String[] csvTitle = {"Название товара", "Описание", "Имя продавца", "Телефон продавца", "Дата публикации", "Цена", "Ссылка на товар"};
        CSVWriter csvWriter = new CSVWriter(new FileWriter(getCsvFileName()), ';');
        try {
            csvWriter.writeNext(csvTitle, false);
            for (List<String> csvStringList : dataForCsvFile) {
                String[] csvStringArray = (String[]) csvStringList.toArray(new String[csvStringList.size()]);
                log.debug("Adding string in csv: " + csvStringArray.toString());
                csvWriter.writeNext(csvStringArray, false);
            }
            csvWriter.close();
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
}