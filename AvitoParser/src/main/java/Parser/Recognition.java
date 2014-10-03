/*  1:   */ package Parser;
/*  2:   */ 
/*  3:   */

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.log4j.Logger;

import java.io.File;

/*  4:   */
/*  5:   */
/*  6:   */
/*  7:   */ 
/*  8:   */ public class Recognition
/*  9:   */ {
/* 10:16 */   static Logger log = Logger.getLogger(Main.class.getName());
/* 11:   */   
/* 12:   */   public static String recognizePhone(String fileName)
/* 13:   */   {
/* 14:19 */     File imageFile = new File(fileName);
/* 15:20 */     Tesseract tesseract = Tesseract.getInstance();
/* 16:   */     
/* 17:22 */     tesseract.setOcrEngineMode(2);
/* 18:23 */     tesseract.setPageSegMode(7);
/* 19:24 */     tesseract.setTessVariable("tessedit_char_whitelist", "0123456789- ");
/* 20:   */     
/* 21:26 */     log.debug("Try to recognize image with phone: " + fileName);
/* 22:   */     try
/* 23:   */     {
/* 24:28 */       return removeBadSymbolsFromNumber(tesseract.doOCR(imageFile));
/* 25:   */     }
/* 26:   */     catch (TesseractException e)
/* 27:   */     {
/* 28:30 */       log.error(e.getMessage());
/* 29:   */     }
/* 30:31 */     return "error";
/* 31:   */   }
/* 32:   */   
/* 33:   */   private static String removeBadSymbolsFromNumber(String number)
/* 34:   */   {
/* 35:36 */     number = number.replaceAll("\n|,", "");
/* 36:37 */     number = number.replaceFirst("^B5|^B 5", "8 9");
/* 37:38 */     number = number.replaceAll("T", "7");
/* 38:39 */     number = number.replaceAll("B", "8");
/* 39:40 */     number = number.replaceAll("l", "1");
/* 40:41 */     number = number.replaceAll("E", "6");
/* 41:42 */     number = number.replaceAll("I", "1");
/* 42:43 */     number = number.replaceAll("O", "0");
/* 43:44 */     return number;
/* 44:   */   }
/* 45:   */ }