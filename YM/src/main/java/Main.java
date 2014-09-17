


/**
 * Created by svpopov on 12.09.2014.
 */
public class Main {

    public static void main(String[] args) {
        try {
            Connection.getContentOfHTTPPage("http://www.google.com", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
