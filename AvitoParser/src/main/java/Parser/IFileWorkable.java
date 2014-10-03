package Parser;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public abstract interface IFileWorkable
{
  public abstract List<List<String>> loadXmlFileData(String paramString)
    throws ParserConfigurationException, SAXException, IOException;
  
  public abstract void saveCsvFileData(List<List> paramList)
    throws IOException;
}


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.IFileWorkable
 * JD-Core Version:    0.7.0.1
 */