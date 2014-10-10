package Parser;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class URLWork {
    static Logger log = Logger.getLogger(Main.class.getName());
    static Properties properties = new Properties();
    Document doc;
    WebClient webClient;


    URLWork() {
        properties.readProperties();
        System.setProperty("http.proxyHost", properties.getProxyAddress());
        System.setProperty("http.proxyPort", properties.getProxyPort());
        this.webClient = new WebClient(BrowserVersion.CHROME, properties.getProxyAddress(), Integer.parseInt(properties.getProxyPort()));
        this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    }


    public static String createUrl(String city, String category, String item_name, String owner, String sort) {
        String urlString = null;
        city = Translit.getTranslit(city.toLowerCase());
        category = Translit.getTranslit(category.toLowerCase());
        item_name = "q=" + item_name.replace(' ', '+').toLowerCase();
        if (owner.equals("Частные")) {
            owner = "&user=1";
        } else if (owner.equals("Компании")) {
            owner = "&user=2";
        } else {
            owner = "";
        }
        if (sort.equals("Дешевле")) {
            sort = "&s=1";
        } else if (sort.equals("Дороже")) {
            sort = "&s=2";
        } else {
            sort = "";
        }
        urlString = MessageFormat.format("http://www.avito.ru/{0}/{1}?{2}{3}{4}", new Object[]{city, category, item_name, owner, sort});
        log.debug("url string: " + urlString);
        return urlString;
    }


    private static String removeUnnecessarySymbols(String string) {
        string = string.replaceAll("</p>|<p>|&quot|&amp|&sup|Размещено", "");
        return string;
    }


    private static Date parseDate(String date) throws Exception {
        int year = 0;
        int month = 0;
        String monthDay = "";
        String[] monthArray = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
        String hoursMinutes = date.substring(date.length() - 7, date.length() - 2);
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (date.contains("сегодня")) {
            calendar.add(5, 0);
            monthDay = dateFormat.format(calendar.getTime());
        } else if (date.contains("вчера")) {
            calendar.add(5, -1);
            monthDay = dateFormat.format(calendar.getTime());
        } else {
            String oMonth = date.substring(date.length() - 14, date.length() - 11);
            for (int i = 0; i < monthArray.length; i++) {
                if (monthArray[i].equals(oMonth)) {
                    month = i + 1;
                }
            }
            String day = date.substring(3, date.length() - 15);
            monthDay = String.valueOf(month) + "-" + day;
        }
        int currentMonth = calendar.get(2);
        if ((month > 10) && (currentMonth == 1)) {
            year = calendar.get(0);
        } else {
            year = calendar.get(1);
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(year + "-" + monthDay + " " + hoursMinutes);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public List<String> getResultUrlList(String urlString, int count) throws IOException {
        int itemIdInt = 0;
        int i = 0;
        List<String> resultUrlList = new ArrayList();
        this.doc = Jsoup.connect(urlString).get();
        Elements links = this.doc.select("a[href]");
        for (Element link : links) {
            String itemId = link.attr("abs:href").substring(link.attr("abs:href").length() - 9);
            String urlId = link.attr("abs:href").substring(0, 19);
            if ((StringUtil.isNumeric(itemId)) && (urlId.equals("http://www.avito.ru"))) {
                if (itemIdInt != Integer.parseInt(itemId)) {
                    resultUrlList.add(link.attr("abs:href"));
                    i++;
                    if (i == count) {
                        break;
                    }
                }
                itemIdInt = Integer.parseInt(itemId);
            }
        }
        return resultUrlList;
    }

    public List getResultItem(String urlString) {
        try {
            List resultItem = new ArrayList();
            this.doc = Jsoup.connect(urlString).get();
            try {
                Elements itemResultNameElement = this.doc.select("h1.h1");
                String itemAdName = removeUnnecessarySymbols(itemResultNameElement.get(0).childNode(0).toString());
                log.debug(itemAdName);
                resultItem.add(itemAdName);
            } catch (Exception e) {
                log.error(e.getMessage());
                resultItem.add("");
            }

            try {
                Elements itemDescriptionElement = this.doc.select("div#desc_text");
                String itemDescription = "";
                for (int j = 0; j < itemDescriptionElement.get(0).childNodes().size(); j++) {
                    itemDescription = itemDescription + " " + removeUnnecessarySymbols(itemDescriptionElement.get(0).childNode(j).toString());
                }
                log.debug(itemDescription);
                resultItem.add(itemDescription);
            } catch (Exception e) {
                log.error(e.getMessage());
                resultItem.add("");
            }

            try {
                Elements sellerNameElement = this.doc.select("strong[itemprop]");
                String sellerName = removeUnnecessarySymbols(sellerNameElement.get(0).childNode(0).toString());
                log.debug(sellerName);
                resultItem.add(sellerName);
            } catch (Exception e) {
                log.error(e.getMessage());
                resultItem.add("");
            }

            try {
                String phoneNumber = getPhoneNumber(urlString);
                log.debug(phoneNumber);
                resultItem.add(phoneNumber);
            } catch (Exception e) {
                log.error(e.getMessage());
                resultItem.add("");
            }
            try {
                Elements publicDateElement = this.doc.select("div.item-subtitle");
                String publicDateString = removeUnnecessarySymbols(publicDateElement.get(0).childNode(0).toString());
                log.debug(publicDateString);
                Date publicDate = parseDate(publicDateString);
                log.debug(publicDate);
                resultItem.add(publicDate);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            try {
                Elements priceElement = this.doc.select("span.p_i_price.t-item-price");
                String price = priceElement.get(0).childNode(1).childNode(0).toString().replaceAll("\\D+", "");
                log.debug(price);
                resultItem.add(Integer.valueOf(Integer.parseInt(price)));
            } catch (Exception e) {
                log.error(e.getMessage());
                resultItem.add(Integer.valueOf(0));
            }
            log.debug(urlString);
            resultItem.add(urlString);
            return resultItem;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    public String getPhoneNumber(String urlString) {
        try {
            log.debug("Pressing \"Показать номер\" button");
            HtmlPage htmlPage = (HtmlPage) this.webClient.getPage(urlString);
            HtmlElement htmlElement = (HtmlElement) htmlPage.getFirstByXPath("//div[@id='i_contact']/div[4]/div/span/span/span");
            htmlPage = (HtmlPage) htmlElement.click();
            HtmlImage htmlImage = (HtmlImage) htmlPage.getFirstByXPath("//div[@id='i_contact']/div[4]span/img");
            String phoneFileName = urlString.substring(urlString.length() - 9, urlString.length());
            Random valueForPhoneName = new Random();
            int randomNum = valueForPhoneName.nextInt((1000001) + 0);
            File imageFile = new File(properties.getTempPath() + phoneFileName + "_" + valueForPhoneName.nextInt((1000001) + 0) + ".png");
            //File imageFile = new File(properties.getTempPath() + phoneFileName + ".png");
            htmlImage.saveAs(imageFile);
            log.debug("Send file " + imageFile.getPath() + " to recognize");
            System.out.println("Send file " + imageFile.getPath() + " to recognize");
            //Recognition recognition = new Recognition(imageFile.getPath());
            String phone = Recognition.recognizePhone(imageFile.getPath());
            imageFile.delete();
            this.webClient.closeAllWindows();
            return phone;
        } catch (IOException e) {
            log.error(e.toString());
        }
        return null;
    }
}