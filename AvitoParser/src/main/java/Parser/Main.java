package Parser;

import org.apache.log4j.Logger;

public class Main {
    static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.readProperties();
        log.info("-----------------------------------");
        log.info("Starting programm...");
        System.out.println("Starting programm...");
        for (Integer i = 0; i < args.length; ++i) {
            if (isXml(args[i])) {
                log.info("Creating thread " + (i + 1));
                new ParserThread("Thread " + (i + 1), args[i], args[args.length - 1]).start();
                Thread.sleep(2000L);
            }
        }
    }

    public static boolean isXml(String parameterValue) {
        if (parameterValue.substring(parameterValue.length() - 4, parameterValue.length()).equals(".xml")) {
            return true;
        } else {
            return false;
        }
    }
}