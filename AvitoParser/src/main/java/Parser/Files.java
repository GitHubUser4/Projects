/*  1:   */ package Parser;
/*  2:   */ 
/*  3:   */ import au.com.bytecode.opencsv.CSVWriter;
/*  4:   */ import java.io.File;
/*  5:   */ import java.io.FileWriter;
/*  6:   */ import java.io.IOException;
/*  7:   */ import java.text.SimpleDateFormat;
/*  8:   */ import java.util.Date;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.xml.parsers.ParserConfigurationException;
/* 11:   */ import javax.xml.parsers.SAXParser;
/* 12:   */ import javax.xml.parsers.SAXParserFactory;
/* 13:   */ import org.apache.log4j.Logger;
/* 14:   */ import org.xml.sax.SAXException;
/* 15:   */ 
/* 16:   */ public class Files
/* 17:   */   implements IFileWorkable
/* 18:   */ {
/* 19:21 */   static Logger log = Logger.getLogger(Main.class.getName());
/* 20:   */   private String xmlFileName;
/* 21:   */   private List<List<String>> xmlFileData;
/* 22:   */   private String csvFilePath;
/* 23:   */   
/* 24:   */   public Files(String xmlFilePath, String csvFilePath)
/* 25:   */   {
/* 26:28 */     this.xmlFileName = xmlFilePath;
/* 27:29 */     this.xmlFileData = null;
/* 28:30 */     this.csvFilePath = csvFilePath;
/* 29:   */   }
/* 30:   */   
/* 31:   */   private String getCsvFileName()
/* 32:   */   {
/* 33:35 */     SimpleDateFormat currentDateTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
/* 34:36 */     Date now = new Date();
/* 35:37 */     String csvFileFirstPartOfName = currentDateTime.format(now);
/* 36:38 */     String csvfileSecondPartOfName = this.xmlFileName.substring(this.xmlFileName.lastIndexOf("\\") + 1, this.xmlFileName.length() - 3) + "csv";
/* 37:39 */     String csvFileName = this.csvFilePath + csvFileFirstPartOfName + "_" + csvfileSecondPartOfName;
/* 38:40 */     log.debug("csv file name: " + csvFileName);
/* 39:41 */     return csvFileName;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public List<List<String>> loadXmlFileData(String xmlFileName)
/* 43:   */     throws ParserConfigurationException, SAXException, IOException
/* 44:   */   {
/* 45:48 */     SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
/* 46:49 */     SAXParser saxParser = saxParserFactory.newSAXParser();
/* 47:50 */     XmlParser xmlParser = new XmlParser();
/* 48:   */     try
/* 49:   */     {
/* 50:52 */       log.info("Try to parse file: " + xmlFileName);
/* 51:53 */       saxParser.parse(new File(xmlFileName), xmlParser);
/* 52:54 */       return this.xmlFileData = xmlParser.getXMLFileData();
/* 53:   */     }
/* 54:   */     catch (SAXException e)
/* 55:   */     {
/* 56:56 */       log.error(e.toString());
/* 57:   */     }
/* 58:57 */     return null;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void saveCsvFileData(List<List> dataForCsvFile)
/* 62:   */     throws IOException
/* 63:   */   {
/* 64:65 */     log.info("Try to save data to csv file");
/* 65:66 */     String[] csvTitle = { "Название товара", "Описание", "Имя продавца", "Телефон продавца", "Дата публикации", "Цена", "Ссылка на товар" };
/* 66:67 */     CSVWriter csvWriter = new CSVWriter(new FileWriter(getCsvFileName()), ';');
/* 67:   */     try
/* 68:   */     {
/* 69:69 */       csvWriter.writeNext(csvTitle, false);
/* 70:70 */       for (List<String> csvStringList : dataForCsvFile)
/* 71:   */       {
/* 72:71 */         String[] csvStringArray = (String[])csvStringList.toArray(new String[csvStringList.size()]);
/* 73:72 */         log.debug("Adding string in csv: " + csvStringArray.toString());
/* 74:73 */         csvWriter.writeNext(csvStringArray, false);
/* 75:   */       }
/* 76:75 */       csvWriter.close();
/* 77:   */     }
/* 78:   */     catch (IOException e)
/* 79:   */     {
/* 80:77 */       log.error(e.toString());
/* 81:   */     }
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.Files
 * JD-Core Version:    0.7.0.1
 */