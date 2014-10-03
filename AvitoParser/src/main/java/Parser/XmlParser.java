/*  1:   */ package Parser;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.apache.log4j.Logger;
/*  6:   */ import org.xml.sax.Attributes;
/*  7:   */ import org.xml.sax.SAXException;
/*  8:   */ import org.xml.sax.helpers.DefaultHandler;
/*  9:   */ 
/* 10:   */ public class XmlParser
/* 11:   */   extends DefaultHandler
/* 12:   */ {
/* 13:17 */   static Logger log = Logger.getLogger(Main.class.getName());
/* 14:18 */   private String thisElement = "";
/* 15:19 */   private List<List<String>> xmlFileData = new ArrayList();
/* 16:20 */   private int counter = -1;
/* 17:   */   
/* 18:   */   public List<List<String>> getXMLFileData()
/* 19:   */   {
/* 20:24 */     return this.xmlFileData;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void startDocument()
/* 24:   */     throws SAXException
/* 25:   */   {
/* 26:29 */     log.info("Starating parse");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void startElement(String uri, String localName, String qName, Attributes attributes)
/* 30:   */     throws SAXException
/* 31:   */   {
/* 32:34 */     this.thisElement = qName;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void characters(char[] ch, int start, int length)
/* 36:   */     throws SAXException
/* 37:   */   {
/* 38:   */     try
/* 39:   */     {
/* 40:41 */       if (this.thisElement.equals("item"))
/* 41:   */       {
/* 42:42 */         this.xmlFileData.add(new ArrayList());
/* 43:43 */         this.counter += 1;
/* 44:   */       }
/* 45:46 */       if (this.thisElement.equals("item_name")) {
/* 46:47 */         ((List)this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
/* 47:   */       }
/* 48:49 */       if (this.thisElement.equals("city")) {
/* 49:50 */         ((List)this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
/* 50:   */       }
/* 51:52 */       if (this.thisElement.equals("category")) {
/* 52:53 */         ((List)this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
/* 53:   */       }
/* 54:55 */       if (this.thisElement.equals("owner")) {
/* 55:56 */         ((List)this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
/* 56:   */       }
/* 57:58 */       if (this.thisElement.equals("sort")) {
/* 58:59 */         ((List)this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
/* 59:   */       }
/* 60:61 */       if (this.thisElement.equals("count")) {
/* 61:62 */         ((List)this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
/* 62:   */       }
/* 63:   */     }
/* 64:   */     catch (NullPointerException e)
/* 65:   */     {
/* 66:64 */       log.error(e);
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void endElement(String uri, String localName, String qName)
/* 71:   */     throws SAXException
/* 72:   */   {
/* 73:70 */     this.thisElement = "";
/* 74:   */   }
/* 75:   */   
/* 76:   */   public void endDocument()
/* 77:   */     throws SAXException
/* 78:   */   {
/* 79:75 */     log.info("Parse finished");
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.XmlParser
 * JD-Core Version:    0.7.0.1
 */