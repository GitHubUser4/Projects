package Parser;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.log4j.Logger;

import java.io.File;

public class Recognition {

    static Logger log = Logger.getLogger(Main.class.getName());
//private static Recognition instance;


    /*
    public static synchronized Recognition getInstance(String fileName){
        if (instance == null) {
            instance = new Recognition(fileName);
        }
        return instance;
    }
    */

    public static synchronized String recognizePhone(String fileName) {
        log.debug("Try to recognize image with phone: " + fileName);
        try {
            Tesseract tesseract = Tesseract.getInstance();
            File imageFile = new File(fileName);
            tesseract.setOcrEngineMode(2);
            tesseract.setPageSegMode(7);
            tesseract.setTessVariable("tessedit_char_whitelist", "0123456789- ");
            return removeBadSymbolsFromNumber(tesseract.doOCR(imageFile));
        } catch (TesseractException e) {
            log.error(e.getMessage());
            return "error";
        }
    }

    private static String removeBadSymbolsFromNumber(String number) {
        number = number.replaceAll("\n|,", "");
        number = number.replaceFirst("^B5|^B 5", "8 9");
        number = number.replaceAll("T", "7");
        number = number.replaceAll("B", "8");
        number = number.replaceAll("l", "1");
        number = number.replaceAll("E", "6");
        number = number.replaceAll("I", "1");
        number = number.replaceAll("O", "0");
        return number;
    }
}