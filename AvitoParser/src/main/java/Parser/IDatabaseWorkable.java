package Parser;

import java.sql.SQLException;
import java.util.List;

public abstract interface IDatabaseWorkable
{
  public abstract List<List> putXmlDataToDataBase(List<List<String>> paramList)
    throws SQLException;
}


/* Location:           C:\AvitoParserRecovery\target\classes\
 * Qualified Name:     Parser.IDatabaseWorkable
 * JD-Core Version:    0.7.0.1
 */