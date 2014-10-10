package Parser;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlParser
        extends DefaultHandler {
    static Logger log = Logger.getLogger(Main.class.getName());
    private String thisElement = "";
    private List<List<String>> xmlFileData = new ArrayList();
    private int counter = -1;

    public List<List<String>> getXMLFileData() {
        return this.xmlFileData;
    }

    public void startDocument()
            throws SAXException {
        log.info("Starating parse");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        this.thisElement = qName;
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        try {
            if (this.thisElement.equals("item")) {
                this.xmlFileData.add(new ArrayList());
                this.counter += 1;
            }
            if (this.thisElement.equals("item_name")) {
                ((List) this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
            }
            if (this.thisElement.equals("city")) {
                ((List) this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
            }
            if (this.thisElement.equals("category")) {
                ((List) this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
            }
            if (this.thisElement.equals("owner")) {
                ((List) this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
            }
            if (this.thisElement.equals("sort")) {
                ((List) this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
            }
            if (this.thisElement.equals("count")) {
                ((List) this.xmlFileData.get(this.counter)).add(new String(ch, start, length));
            }
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        this.thisElement = "";
    }

    public void endDocument()
            throws SAXException {
        log.info("Parse finished");
    }
}