/*  1:   */ package Parser;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import org.apache.log4j.Logger;
/*  7:   */ 
/*  8:   */ public class Parser
/*  9:   */ {
/* 10:13 */   static Logger log = Logger.getLogger(Main.class.getName());
/* 11:   */   
/* 12:   */   public static void execute(String xmlFilePath, String csvFilePath)
/* 13:   */     throws Exception
/* 14:   */   {
/* 15:20 */     List<List> searchItemDescriptionResult = new ArrayList();
/* 16:   */     
/* 17:   */ 
/* 18:   */ 
/* 19:24 */     Files file = new Files(xmlFilePath, csvFilePath);
/* 20:25 */     List<List<String>> xmlFileData = file.loadXmlFileData(xmlFilePath);
/* 21:   */     
/* 22:27 */     DataBase dataBase = new DataBase();
/* 23:   */     
/* 24:   */ 
/* 25:30 */     List<List> dataForSearch = dataBase.putXmlDataToDataBase(xmlFileData);
/* 26:   */     
/* 27:   */ 
/* 28:33 */     URLWork urlWork = new URLWork();
/* 29:34 */     for (int i = 0; i < dataForSearch.size(); i++)
/* 30:   */     {
/* 31:35 */       String urlString = String.valueOf(((List)dataForSearch.get(i)).get(0));
/* 32:36 */       Integer count = (Integer)((List)dataForSearch.get(i)).get(1);
/* 33:37 */       System.out.println("Trying to parse first " + count + " result for " + urlString);
/* 34:38 */       List<String> searchItemResult = urlWork.getResultUrlList(urlString, count.intValue());
/* 35:   */       
/* 36:40 */       searchItemDescriptionResult.add(new ArrayList());
/* 37:41 */       for (int j = 0; j < searchItemResult.size(); j++)
/* 38:   */       {
/* 39:42 */         System.out.println("Gathering information for: " + (j + 1) + " item");
/* 40:43 */         ((List)searchItemDescriptionResult.get(i)).add(urlWork.getResultItem((String)searchItemResult.get(j)));
/* 41:44 */         Thread.sleep(5000L);
/* 42:   */       }
/* 43:   */     }
/* 44:49 */     dataBase.putSearchResultToDataBase(searchItemDescriptionResult);
/* 45:   */     
/* 46:   */ 
/* 47:52 */     List<List> dataForCsvFile = dataBase.getDataforCsvFile();
/* 48:53 */     file.saveCsvFileData(dataForCsvFile);
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.Parser
 * JD-Core Version:    0.7.0.1
 */