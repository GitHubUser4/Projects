import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by svpopov on 12.09.2014.
 */
public class Connection {

    public static String getContentOfHTTPPage(String pageAddress, String codePage) throws Exception {
        StringBuilder sb = new StringBuilder();
        URL pageUrl = new URL(pageAddress);
        URLConnection urlConnection = pageUrl.openConnection();
        urlConnection.setConnectTimeout(15 * 1000);
        urlConnection.connect();
        String content = urlConnection.getContent().toString();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream(), codePage));
        try {
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }

}
