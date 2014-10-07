package Parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public abstract interface IFileWorkable
{
  public abstract List<List<String>> loadXmlFileData(String paramString)
    throws ParserConfigurationException, SAXException, IOException;
  
  public abstract void saveCsvFileData(List<List> paramList)
    throws IOException;
}