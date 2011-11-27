/** @author Ryan Gaffney */

package components;

import java.sql.*;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class DatabaseAdapter
{
    public static final String mySQL_url = "jdbc:mysql://107.20.135.212:3306/check-please2?allowMultiQueries=true&characterEncoding=UTF-8&sessionVariables=time_zone='-8:00'";
    
    private static final String mySQL_userName = "check-please2";
    private static final String mySQL_password = "!checkPlease!@";
    private static final String mySQL_JDBC_Driver = "com.mysql.jdbc.Driver";
    
    public static Connection newConnection()
    {   
        try
        {            
            Class.forName(mySQL_JDBC_Driver).newInstance();
            return DriverManager.getConnection(mySQL_url, mySQL_userName, mySQL_password);
        }
        catch (Exception e)
        {
            System.err.println("ERROR -- [Storage.connect] :: IllegalAccessException");
            e.printStackTrace();
            return null;
        }
    }
      
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs, CallableStatement cs)
    {
        try
        {
            if (cs != null)
                cs.close();
            
            if (ps != null)
                ps.close();

            if (rs != null)
                rs.close();
            
            if (conn != null)
                conn.close();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    public static void close(Connection conn) { close(conn, null, null, null); }
    public static void close(PreparedStatement ps) { close(null, ps, null, null); }
    public static void close(ResultSet rs) { close(null, null, rs, null); }
    public static void close(CallableStatement cs) { close(null, null, null, cs); }
    public static void close(Connection conn, PreparedStatement ps) { close(conn, ps, null, null); }
    public static void close(PreparedStatement ps, ResultSet rs) { close(null, ps, rs, null); }
    public static void close(Connection conn, ResultSet rs) { close(conn, null, rs, null); }
    public static void close(Connection conn, CallableStatement cs) { close(conn, null, null, cs); }
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) { close(conn, ps, rs, null); }
    
    public static void printResultSet(ResultSet rs)
    {
        System.err.println("WARNING -- [Storage.printResultSet] moves row pointer to first, and does NOT disconnect nor close ResultSet!");

        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();

            int numColumns = rsmd.getColumnCount();

            while (rs.next())
            {
                for (int i = 1; i < numColumns; ++i)
                {
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                    
                    if (1 + i != numColumns)
                        System.out.print(", ");
                }
                System.out.println();
            }
            
            rs.first();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    
    public static JSONArray resultSetToJSONArray(java.sql.ResultSet rs)
    {
        return resultSetConverter(rs, false);
    }
    
    public static JSONArray getColumnTypes(java.sql.ResultSet rs)
    {
        return resultSetConverter(rs, true);
    }
    
    private static JSONArray resultSetConverter(java.sql.ResultSet rs, boolean getTypes)
    {
        if (rs == null) return null;
        
        JSONArray json = new JSONArray();

        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            
            while (rs.next() || getTypes)
            {                
                JSONObject o = new JSONObject();
                String columnName;

                for (int i = 1; i < 1 + numColumns; ++i)
                {
                    columnName = rsmd.getColumnName(i);

                    switch (rsmd.getColumnType(i))
                    {
                    case java.sql.Types.ARRAY:
                        o.put(columnName, (getTypes) ? new ArrayList<Object>().getClass().getCanonicalName() : rs.getArray(columnName));
                        break;
                    case java.sql.Types.BIGINT:
                        o.put(columnName, (getTypes) ? new Long(0L).getClass().getCanonicalName() : rs.getLong(columnName));
                        break;
                    case java.sql.Types.BOOLEAN:
                        o.put(columnName, (getTypes) ? new Boolean(true).getClass().getCanonicalName() : rs.getBoolean(columnName));
                        break;
                    case java.sql.Types.BLOB:
                        o.put(columnName, (getTypes) ? new Byte[0].getClass().getCanonicalName() : rs.getBlob(columnName));
                        break;
                    case java.sql.Types.DOUBLE:
                        o.put(columnName, (getTypes) ? new Double(0D).getClass().getCanonicalName() : rs.getDouble(columnName));
                        break;
                    case java.sql.Types.FLOAT:
                        o.put(columnName, (getTypes) ? new Float(0.00F).getClass().getCanonicalName() : rs.getFloat(columnName));
                        break;
                    case java.sql.Types.INTEGER:
                        o.put(columnName, (getTypes) ? new Integer(0).getClass().getCanonicalName() : rs.getInt(columnName));
                        break;
                    case java.sql.Types.NVARCHAR:
                        o.put(columnName, (getTypes) ? new String().getClass().getCanonicalName() : rs.getString(columnName));
                        break;
                    case java.sql.Types.VARCHAR:
                        o.put(columnName, (getTypes) ? new String().getClass().getCanonicalName() : rs.getString(columnName));
                        break;
                    case java.sql.Types.TINYINT:
                        o.put(columnName, (getTypes) ? new Byte((byte) 0).getClass().getCanonicalName() : rs.getInt(columnName));
                        break;
                    case java.sql.Types.SMALLINT:
                        o.put(columnName, (getTypes) ? new Short((short) 0).getClass().getCanonicalName() : rs.getInt(columnName));
                        break;
                    case java.sql.Types.DATE:
                        o.put(columnName, (getTypes) ? new java.sql.Timestamp(0L).getClass().getCanonicalName() : rs.getDate(columnName));
                        break;
                    case java.sql.Types.TIMESTAMP:
                        o.put(columnName, (getTypes) ? new Timestamp(0L).getClass().getCanonicalName() : rs.getTimestamp(columnName));
                        break;
                    default:
                        o.put(columnName, (getTypes) ? new Object().getClass().getCanonicalName() : rs.getObject(columnName));
                        break;
                    }
                }
                json.put(o);
                
                if (getTypes)
                    break;
            }
            
            return json.length() > 0 ? json : null;
        }
        catch (SQLException | JSONException e) { e.printStackTrace(); }

        return null;
    }
}