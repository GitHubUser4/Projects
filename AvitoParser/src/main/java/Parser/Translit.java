/*  1:   */ package Parser;
/*  2:   */ 
/*  3:   */ class Translit
/*  4:   */ {
/*  5: 7 */   static final char[] RU = { 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '-', ' ' };
/*  6:14 */   static final String[] EN = { "a", "b", "v", "g", "d", "e", "o", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "y", "", "e", "yu", "ya", "-", "_" };
/*  7:   */   
/*  8:   */   public static String getTranslit(String str)
/*  9:   */   {
/* 10:23 */     char[] ch = str.toCharArray();
/* 11:24 */     StringBuilder trans = new StringBuilder();
/* 12:25 */     for (int i = 0; i < ch.length; i++) {
/* 13:26 */       for (int j = 0; j < RU.length; j++) {
/* 14:27 */         if (ch[i] == RU[j]) {
/* 15:28 */           trans = trans.append(EN[j]);
/* 16:   */         }
/* 17:   */       }
/* 18:   */     }
/* 19:31 */     str = trans.toString();
/* 20:32 */     return str;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.Translit
 * JD-Core Version:    0.7.0.1
 */