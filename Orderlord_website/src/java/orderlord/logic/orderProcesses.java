package orderlord.logic;

import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import storage.PreparedStatement;
//import storage.ResultSet;
//import storage.SQLException;


public class orderProcesses extends Thread
{
    
	private static Connection conn = null;
    
    public static void main(String[] argv)
    {
    	Connection connection = connect();

        if (connection == null)
        	System.out.println("null connection\n");
        else
        	(new orderProcesses()).run();
    }

    
    public void run()
    {
    	while (true)
    	{
    		System.out.println ("INFO -- Extracting all the pending orders and adding to active orders");
        	try
        	{
        		Connection connection = connect();
        		long time_to_sleep = 0;
        		if (connection != null)
        		{
        			System.out.println ("INFO -- Getting Orders by pickup time");
        			String sqlQuery_pending = "SELECT * FROM Uncommitted_Orders ORDER BY pickupTime";
        			PreparedStatement ps_pending = connection.prepareStatement(sqlQuery_pending);
                    ps_pending.execute();
                    ResultSet rs_uncommitted = ps_pending.getResultSet(); 
                    if (rs_uncommitted != null)
                    {
                    	System.out.println ("INFO -- Pending Table is not empty");
	                    while (rs_uncommitted.next())
	                    {
	                    	
	                    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                		Date date = new Date();
	                		//System.out.println(dateFormat.format(date));
	                		
	                		Calendar calTime = Calendar.getInstance();
	                		
	                		long curr_time_msec = calTime.getTimeInMillis();
	                		//if (rs.getDate("pickupTime").toString() == dateFormat.format(date))
	                		//check etp of the next order before adding into pending order table	
	                		if (rs_uncommitted.getTimestamp("pickupTime").getTime() < curr_time_msec)
	                		{
	                			// Delete all the pending orders below if pickuptime is less than current time
	                			System.out.println("INFO -- It's past pick up time and entry is still in pending table");
	                			while (rs_uncommitted.next())
	                			{
		                			String old_pending = "DELETE FROM Uncommitted_Orders WHERE id = ?";
		                        	PreparedStatement ps_old_pending = connection.prepareStatement (old_pending);
		                        	ps_old_pending.setLong(1, rs_uncommitted.getLong("id"));
		                        	ps_old_pending.execute();
	                			}
	                		}
	                		else
	                		{
	                			
		                    	if (rs_uncommitted.getInt("etp")*60*1000 + curr_time_msec >= rs_uncommitted.getTimestamp("pickupTime").getTime())
		                		{
		                    		System.out.println ("INFO -- Pending order to insert into Committed_Orders table");
			                    	String sqlQuery_active = "INSERT INTO Committed_Orders" + "(" + "version,"
			                                + "customer_id," + "etp," + "pickupTime,"
			                                + "store_id," + "price" + ") VALUES(" + "?,"
			                                + "?," + "?," + "?," + "?," + "?" + ")";
			                    	
			                    	PreparedStatement ps_active = connection.prepareStatement(sqlQuery_active);
			                  
			                        ps_active.setLong (1, rs_uncommitted.getLong("version"));
			                        ps_active.setLong (2, rs_uncommitted.getLong("customer_id"));
			                        ps_active.setLong (3, rs_uncommitted.getLong("etp"));
			                        ps_active.setTimestamp (4, rs_uncommitted.getTimestamp("pickupTime"));
			                        ps_active.setLong (5, rs_uncommitted.getLong("store_id"));
			                        ps_active.setDouble(6, rs_uncommitted.getDouble("price"));
			                       
			                        ps_active.execute();
			                        
			                        /*String sqlQuery_active2 = "SELECT * FROM activeorder ORDER BY pickup_time";
			                        PreparedStatement ps_active2 = connection.prepareStatement(sqlQuery_active2);
			                        ps_active2.execute();
			                        
			                        ResultSet rs_active2 = ps_active2.getResultSet();
			                        
			                        rs_active2.first()*/;
			                        //if scheduled
			                        //update the pickup_time
			                        long scheduledOrder = rs_uncommitted.getLong("scheduledOrder_id");
			                        if (scheduledOrder != 0)
			                        {
			                        	System.out.println("INFO -- The order is scheduled, update date");
			                        	String sqlQuery_updateDate = "UPDATE Comitted_Orders SET pickupTime = ? WHERE id = ?";
			                        	PreparedStatement ps_update_pending = connection.prepareStatement (sqlQuery_updateDate);
			                        	
			                        	// Update Date
			                        	java.sql.Timestamp ts = rs_uncommitted.getTimestamp("pickupTime");
			                        	Calendar cal = Calendar.getInstance();
			                        	cal.setTime(ts);
			                        	cal.add(Calendar.DAY_OF_WEEK, 7); //add seven days
			                        	ts.setTime(cal.getTime().getTime()); 
			                        	ts = new Timestamp(cal.getTime().getTime());
	
			                        	ps_update_pending.setTimestamp(1, ts);
			                        	ps_update_pending.setLong(2, rs_uncommitted.getLong("id"));
			                        	
			                        	ps_update_pending.execute();
			                     
			                        }
			                        else // Delete order from pending table 
			                        {
			                        	System.out.println("INFO -- The order is one time, delete it from pending table");
			                        	String sqlQuery_delete = "DELETE FROM Uncommitted_Orders WHERE id = ?";
			                        	PreparedStatement ps_delete_pending = connection.prepareStatement (sqlQuery_delete);
			                        	ps_delete_pending.setLong(1, rs_uncommitted.getLong("id"));
			                        	ps_delete_pending.execute();
			                        }
			                        
			                        //Look at the next pending entry and sleep.
			                        if (rs_uncommitted.next())
			                    	{
			                        	System.out.println("INFO -- Check the next entry in pending order table");
			                    		rs_uncommitted.next();
			                    		//if etp+currenttime < pickuptime
			                    		if (rs_uncommitted.getDate("pickupTime").toString() == dateFormat.format(date) && rs_uncommitted.getInt("etp")*60*1000 + curr_time_msec <= rs_uncommitted.getTimestamp("pickupTime").getTime())
			                    		time_to_sleep = rs_uncommitted.getTimestamp("pickupTime").getTime() - rs_uncommitted.getInt("etp")*60*1000 - curr_time_msec;
			                    		try {
											Thread.sleep(time_to_sleep);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			                    	}
		                    	}
		                    	// if pending order's pickup time is not there yet
		                    	else
		                    	{
		                    		System.out.println ("INFO -- It's not the pickup time for current pending order");
		                    		long time_curr_sleep = rs_uncommitted.getTimestamp("pickupTime").getTime() - rs_uncommitted.getInt("etp")*60*1000 - curr_time_msec;
		                    		try {
										Thread.sleep(time_curr_sleep);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		                    		
		                    	}
	                		}// end if pickup time is greater than current time
	                    }// end while (rs.next()
                    }// end if rs != null
                    else //if rs is null
                    {
                    	System.out.println("No pending orders\n");
                    }
        		}// end if connection != null
        		
        	}
        	catch (SQLException e) { e.printStackTrace();}
        	
    	}
    }
    
    private static Connection connect()
    {
        if (conn == null)
        {
            try
            {
                String userName = "check-please2";
                String password = "!checkPlease!@";
                String url = "jdbc:mysql://107.20.135.212:3306/check-please2";
    
					Class.forName("com.mysql.jdbc.Driver").newInstance();
		
                return conn = DriverManager.getConnection(url, userName,
                        password);
            
            }catch (SQLException | InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        }
        else return conn;
    }

    public static void disconnect()
    {
        if (conn != null)
        {
            try
            {
                conn.close();
                conn = null;
                System.out.println("INFO -- Database connection terminated");
            }
            catch (Exception e)
            {
                conn = null;
                e.printStackTrace();
            }
        }
    }
}