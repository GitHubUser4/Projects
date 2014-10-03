/*   1:    */ package Parser;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebClientOptions;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlImage;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   9:    */ import java.io.File;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.text.DateFormat;
/*  12:    */ import java.text.MessageFormat;
/*  13:    */ import java.text.ParseException;
/*  14:    */ import java.text.SimpleDateFormat;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Calendar;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.jsoup.Connection;
/*  21:    */ import org.jsoup.Jsoup;
/*  22:    */ import org.jsoup.helper.StringUtil;
/*  23:    */ import org.jsoup.nodes.Document;
/*  24:    */ import org.jsoup.nodes.Element;
/*  25:    */ import org.jsoup.nodes.Node;
/*  26:    */ import org.jsoup.select.Elements;
/*  27:    */ 
/*  28:    */ public class URLWork
/*  29:    */ {
/*  30: 31 */   static Logger log = Logger.getLogger(Main.class.getName());
/*  31: 32 */   static Properties properties = new Properties();
/*  32:    */   Document doc;
/*  33:    */   WebClient webClient;
/*  34:    */   
/*  35:    */   URLWork()
/*  36:    */   {
/*  37: 37 */     properties.readProperties();
/*  38: 38 */     System.setProperty("http.proxyHost", properties.getProxyAddress());
/*  39: 39 */     System.setProperty("http.proxyPort", properties.getProxyPort());
/*  40: 40 */     this.webClient = new WebClient(BrowserVersion.CHROME, properties.getProxyAddress(), Integer.parseInt(properties.getProxyPort()));
/*  41: 41 */     this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static String createUrl(String city, String category, String item_name, String owner, String sort)
/*  45:    */   {
/*  46: 46 */     String urlString = null;
/*  47: 47 */     city = Translit.getTranslit(city.toLowerCase());
/*  48: 48 */     category = Translit.getTranslit(category.toLowerCase());
/*  49: 49 */     item_name = "q=" + item_name.replace(' ', '+').toLowerCase();
/*  50: 51 */     if (owner.equals("Частные")) {
/*  51: 51 */       owner = "&user=1";
/*  52: 53 */     } else if (owner.equals("Компании")) {
/*  53: 53 */       owner = "&user=2";
/*  54:    */     } else {
/*  55: 55 */       owner = "";
/*  56:    */     }
/*  57: 59 */     if (sort.equals("Дешевле")) {
/*  58: 59 */       sort = "&s=1";
/*  59: 61 */     } else if (sort.equals("Дороже")) {
/*  60: 61 */       sort = "&s=2";
/*  61:    */     } else {
/*  62: 63 */       sort = "";
/*  63:    */     }
/*  64: 66 */     urlString = MessageFormat.format("http://www.avito.ru/{0}/{1}?{2}{3}{4}", new Object[] { city, category, item_name, owner, sort });
/*  65:    */     
/*  66: 68 */     log.debug("url string: " + urlString);
/*  67: 69 */     return urlString;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List<String> getResultUrlList(String urlString, int count)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73: 74 */     int itemIdInt = 0;int i = 0;
/*  74: 75 */     List<String> resultUrlList = new ArrayList();
/*  75:    */     
/*  76: 77 */     this.doc = Jsoup.connect(urlString).get();
/*  77: 78 */     Elements links = this.doc.select("a[href]");
/*  78: 79 */     for (Element link : links)
/*  79:    */     {
/*  80: 80 */       String itemId = link.attr("abs:href").substring(link.attr("abs:href").length() - 9);
/*  81: 81 */       String urlId = link.attr("abs:href").substring(0, 19);
/*  82: 82 */       if ((StringUtil.isNumeric(itemId)) && (urlId.equals("http://www.avito.ru")))
/*  83:    */       {
/*  84: 83 */         if (itemIdInt != Integer.parseInt(itemId))
/*  85:    */         {
/*  86: 84 */           resultUrlList.add(link.attr("abs:href"));
/*  87: 85 */           i++;
/*  88: 86 */           if (i == count) {
/*  89:    */             break;
/*  90:    */           }
/*  91:    */         }
/*  92: 88 */         itemIdInt = Integer.parseInt(itemId);
/*  93:    */       }
/*  94:    */     }
/*  95: 91 */     return resultUrlList;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List getResultItem(String urlString)
/*  99:    */   {
/* 100:    */     try
/* 101:    */     {
/* 102: 97 */       List resultItem = new ArrayList();
/* 103: 98 */       this.doc = Jsoup.connect(urlString).get();
/* 104:    */       try
/* 105:    */       {
/* 106:101 */         Elements itemResultNameElement = this.doc.select("h1.h1");
/* 107:102 */         String itemAdName = removeUnnecessarySymbols(itemResultNameElement.get(0).childNode(0).toString());
/* 108:103 */         log.debug(itemAdName);
/* 109:104 */         resultItem.add(itemAdName);
/* 110:    */       }
/* 111:    */       catch (Exception e)
/* 112:    */       {
/* 113:106 */         log.error(e.getMessage());
/* 114:107 */         resultItem.add("");
/* 115:    */       }
/* 116:    */       try
/* 117:    */       {
/* 118:111 */         Elements itemDescriptionElement = this.doc.select("div#desc_text");
/* 119:112 */         String itemDescription = "";
/* 120:113 */         for (int j = 0; j < itemDescriptionElement.get(0).childNodeSize(); j++) {
/* 121:114 */           itemDescription = itemDescription + " " + removeUnnecessarySymbols(itemDescriptionElement.get(0).childNode(j).toString());
/* 122:    */         }
/* 123:116 */         log.debug(itemDescription);
/* 124:117 */         resultItem.add(itemDescription);
/* 125:    */       }
/* 126:    */       catch (Exception e)
/* 127:    */       {
/* 128:119 */         log.error(e.getMessage());
/* 129:120 */         resultItem.add("");
/* 130:    */       }
/* 131:    */       try
/* 132:    */       {
/* 133:124 */         Elements sellerNameElement = this.doc.select("strong[itemprop]");
/* 134:125 */         String sellerName = removeUnnecessarySymbols(sellerNameElement.get(0).childNode(0).toString());
/* 135:126 */         log.debug(sellerName);
/* 136:127 */         resultItem.add(sellerName);
/* 137:    */       }
/* 138:    */       catch (Exception e)
/* 139:    */       {
/* 140:129 */         log.error(e.getMessage());
/* 141:130 */         resultItem.add("");
/* 142:    */       }
/* 143:    */       try
/* 144:    */       {
/* 145:134 */         String phoneNumber = getPhoneNumber(urlString);
/* 146:135 */         log.debug(phoneNumber);
/* 147:136 */         resultItem.add(phoneNumber);
/* 148:    */       }
/* 149:    */       catch (Exception e)
/* 150:    */       {
/* 151:138 */         log.error(e.getMessage());
/* 152:139 */         resultItem.add("");
/* 153:    */       }
/* 154:    */       try
/* 155:    */       {
/* 156:143 */         Elements publicDateElement = this.doc.select("div.item-subtitle");
/* 157:144 */         String publicDateString = removeUnnecessarySymbols(publicDateElement.get(0).childNode(0).toString());
/* 158:145 */         log.debug(publicDateString);
/* 159:146 */         Date publicDate = parseDate(publicDateString);
/* 160:147 */         log.debug(publicDate);
/* 161:148 */         resultItem.add(publicDate);
/* 162:    */       }
/* 163:    */       catch (Exception e)
/* 164:    */       {
/* 165:150 */         log.error(e.getMessage());
/* 166:    */       }
/* 167:    */       try
/* 168:    */       {
/* 169:154 */         Elements priceElement = this.doc.select("span.p_i_price.t-item-price");
/* 170:155 */         String price = priceElement.get(0).childNode(1).childNode(0).toString().replaceAll("\\D+", "");
/* 171:156 */         log.debug(price);
/* 172:157 */         resultItem.add(Integer.valueOf(Integer.parseInt(price)));
/* 173:    */       }
/* 174:    */       catch (Exception e)
/* 175:    */       {
/* 176:159 */         log.error(e.getMessage());
/* 177:160 */         resultItem.add(Integer.valueOf(0));
/* 178:    */       }
/* 179:163 */       log.debug(urlString);
/* 180:164 */       resultItem.add(urlString);
/* 181:    */       
/* 182:166 */       return resultItem;
/* 183:    */     }
/* 184:    */     catch (Exception e)
/* 185:    */     {
/* 186:169 */       log.error(e.toString());
/* 187:    */     }
/* 188:170 */     return null;
/* 189:    */   }
/* 190:    */   
/* 191:    */   private static String removeUnnecessarySymbols(String string)
/* 192:    */   {
/* 193:175 */     string = string.replaceAll("</p>|<p>|&quot|&amp|&sup|Размещено", "");
/* 194:176 */     return string;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private static Date parseDate(String date)
/* 198:    */     throws Exception
/* 199:    */   {
/* 200:181 */     int year = 0;
/* 201:182 */     int month = 0;
/* 202:183 */     String monthDay = "";
/* 203:184 */     String[] monthArray = { "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек" };
/* 204:    */     
/* 205:186 */     String hoursMinutes = date.substring(date.length() - 7, date.length() - 2);
/* 206:    */     
/* 207:188 */     DateFormat dateFormat = new SimpleDateFormat("MM-dd");
/* 208:189 */     Calendar calendar = Calendar.getInstance();
/* 209:190 */     if (date.contains("сегодня"))
/* 210:    */     {
/* 211:191 */       calendar.add(5, 0);
/* 212:192 */       monthDay = dateFormat.format(calendar.getTime());
/* 213:    */     }
/* 214:193 */     else if (date.contains("вчера"))
/* 215:    */     {
/* 216:194 */       calendar.add(5, -1);
/* 217:195 */       monthDay = dateFormat.format(calendar.getTime());
/* 218:    */     }
/* 219:    */     else
/* 220:    */     {
/* 221:197 */       String oMonth = date.substring(date.length() - 14, date.length() - 11);
/* 222:198 */       for (int i = 0; i < monthArray.length; i++) {
/* 223:199 */         if (monthArray[i].equals(oMonth)) {
/* 224:199 */           month = i + 1;
/* 225:    */         }
/* 226:    */       }
/* 227:201 */       String day = date.substring(3, date.length() - 15);
/* 228:202 */       monthDay = String.valueOf(month) + "-" + day;
/* 229:    */     }
/* 230:205 */     int currentMonth = calendar.get(2);
/* 231:206 */     if ((month > 10) && (currentMonth == 1)) {
/* 232:207 */       year = calendar.get(0);
/* 233:    */     } else {
/* 234:209 */       year = calendar.get(1);
/* 235:    */     }
/* 236:    */     try
/* 237:    */     {
/* 238:213 */       return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(year + "-" + monthDay + " " + hoursMinutes);
/* 239:    */     }
/* 240:    */     catch (ParseException e)
/* 241:    */     {
/* 242:215 */       log.error(e.getMessage());
/* 243:    */     }
/* 244:216 */     return null;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getPhoneNumber(String urlString)
/* 248:    */   {
/* 249:    */     try
/* 250:    */     {
/* 251:223 */       log.debug("Pressing \"Показать номер\" button");
/* 252:224 */       HtmlPage htmlPage = (HtmlPage)this.webClient.getPage(urlString);
/* 253:225 */       HtmlElement htmlElement = (HtmlElement)htmlPage.getFirstByXPath("//div[@id='i_contact']/div[4]/div/span/span/span");
/* 254:226 */       htmlPage = (HtmlPage)htmlElement.click();
/* 255:227 */       HtmlImage htmlImage = (HtmlImage)htmlPage.getFirstByXPath("//div[@id='i_contact']/div[4]/div/span[1]/span/img");
/* 256:228 */       String phoneFileName = urlString.substring(urlString.length() - 9, urlString.length());
/* 257:229 */       File imageFile = new File(properties.getTempPath() + phoneFileName + ".png");
/* 258:230 */       htmlImage.saveAs(imageFile);
/* 259:231 */       log.debug("Send file " + imageFile.getPath() + " to recognize");
/* 260:232 */       String phoneNumberString = Recognition.recognizePhone(imageFile.getPath());
/* 261:233 */       imageFile.delete();
/* 262:234 */       this.webClient.closeAllWindows();
/* 263:235 */       return phoneNumberString;
/* 264:    */     }
/* 265:    */     catch (IOException e)
/* 266:    */     {
/* 267:237 */       log.error(e.toString());
/* 268:    */     }
/* 269:238 */     return null;
/* 270:    */   }
/* 271:    */ }


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.URLWork
 * JD-Core Version:    0.7.0.1
 */