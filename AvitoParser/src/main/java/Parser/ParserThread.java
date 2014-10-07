package Parser;

import org.apache.log4j.Logger;

/**
 * Created by svpopov on 03.10.2014.
 */
public class ParserThread extends Thread {
    static Logger log = Logger.getLogger(Main.class.getName());
    String xmlFilePath;
    String csvFilePath;

    ParserThread(String name, String xmlFilePath, String csvFilePath) {
        super(name);
        this.xmlFilePath = xmlFilePath;
        this.csvFilePath = csvFilePath;
    }

    public void run() {
        try {
            Parser.execute(xmlFilePath, csvFilePath);
        } catch (InterruptedException e) {
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

