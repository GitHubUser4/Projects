/*  1:   */ package Parser;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.apache.log4j.Logger;
/*  5:   */ 
/*  6:   */ public class Main
/*  7:   */ {
/*  8:11 */   static Logger log = Logger.getLogger(Main.class.getName());
/*  9:   */   
/* 10:   */   public static void main(String[] args)
/* 11:   */     throws Exception
/* 12:   */   {
/* 13:15 */     Properties properties = new Properties();
/* 14:16 */     properties.readProperties();
/* 15:17 */     log.info("-----------------------------------");
/* 16:18 */     log.info("Starting programm...");
/* 17:19 */     System.out.println("Starting programm...");
/* 18:20 */     Parser.execute(args[0], args[1]);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.Main
 * JD-Core Version:    0.7.0.1
 */