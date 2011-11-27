/** @author Ryan Gaffney */

package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.EnumSet;

import junit.framework.TestCase;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import components.DatabaseAdapter;
import components.Extra;
import components.KeyValuePair;
import components.Table;
import components.Table.Customers;
import components.Table.Items;
import components.Table.Stores;
import components.Table.Uncommitted_Orders;
import static components.Table.Customers.*;

import resources.MainResource;

public class MainResourceTests extends TestCase
{
    abstract class Convertible
    {
        KeyValuePair[] data;
        String myTable;
        int columnCount;
        
        Convertible(Class<?> table)
        {
            Object[] columns = table.getEnumConstants();
            
            myTable = table.getSimpleName();            
            columnCount = columns.length;
            
            data = new KeyValuePair[columnCount];
            
            int i = 0;
            for (Object column : columns)
                data[i++] = new KeyValuePair(column.toString(), null);
        }
        
        public JSONObject toJSON()
        {
            JSONObject jo = new JSONObject();
            
            if (data == null)
                return jo;
            
            try
            {
                for (KeyValuePair kvp : data)
                    jo.put(kvp.key(), kvp.value());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                fail("ERROR -- Could not convert to JSON");
            }
            
            return jo;
        }
        
        public String toString()
        {
            return this.toJSON().toString();
        }
        
        public boolean addToDatabase()
        {
            if (data == null || data.length == 0)
                return false;
            
            String fields = " (";
            
            for (int i = 0; i < columnCount; ++i)
            {
                if (data[i].value() != null)
                {
                    fields += (data[i].key() + ((1 + i != columnCount) ? ", " : ")"));
                }
            }
            
            String values = " VALUES(";
            
            for (int i = 0; i < columnCount; ++i)
            {
                if (data[i].value() != null)
                {
                    values += (1 + i != columnCount) ? "?, " : ")";
                }
            }
            
            Connection conn = newConnection();
            
            final String sql = "INSERT INTO " + myTable + fields + values;
            
            
            try
            {
            
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            
            int sIndex = 1;
            
            for (int i = 0; i < columnCount; ++i)
            {
                if (data[i].value() != null)
                {
                    if (data[i].value() instanceof java.lang.String)
                        ps.setString(sIndex, (String) data[i].value());
                    
                    else if (data[i].value() instanceof java.lang.Integer)
                        ps.setInt(sIndex, (Integer) data[i].value());
                    
                    else if (data[i].value() instanceof java.lang.Long)
                        ps.setLong(sIndex, (Long) data[i].value());
                    
                    else if (data[i].value() instanceof java.lang.Double)
                        ps.setDouble(sIndex, (Double) data[i].value());
                    
                    else fail("ERROR -- Unknown data value: " + data[i].value());
                }
            }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            return true; // TODO
        }
    }
    
    interface Table_Entry
    {
        public boolean addToDatabase();
        public boolean removeFromDatabase();
    }
    
    class t_Store extends Convertible
    {
        public t_Store()
        {
            super(Table.Stores.class);
        }
    }
    
    class t_Item extends Convertible
    {
        boolean inDatabase = false;
        
        KeyValuePair[] data =
            {
                new KeyValuePair(Items.id.name(), null),
                new KeyValuePair(Items.version.name(), 0L),
                new KeyValuePair(Items.etp.name(), 1L),
                new KeyValuePair(Items.name.name(), "test-name"),
                new KeyValuePair(Items.description.name(), "test-description"),
                new KeyValuePair(Items.price.name(), 5.75D),
                new KeyValuePair(Items.store_id.name(), null)
            };
        
        t_Store myStore;
        
        public t_Item(t_Store myStore)
        {
            super(Table.Items.class);
            this.myStore = myStore;
            this.addToDatabase();
        }
    }
    
    private static JSONObject createAccount = new JSONObject();
    private static JSONObject loginWithUsername = new JSONObject();
    private static JSONObject loginWithEmail = new JSONObject();
    private static JSONObject updateAccountStartingWithAllFields = new JSONObject();
    private static JSONObject deleteAccount = new JSONObject();
    private static JSONObject searchForStores = new JSONObject();
    private static JSONObject getStore = new JSONObject();
    private static JSONObject getStoreMenu = new JSONObject();
    private static JSONObject calculateOrderTotals = new JSONObject();
    
    static
    {
        try
        {
            final Integer t_random = new java.util.Random().nextInt(Math.abs(((Long) (System.currentTimeMillis() % Integer.MAX_VALUE)).intValue() + System.identityHashCode(createAccount)));
            final String t_email = "email_" + t_random;
            final String t_username = "user" + t_random;
            final String t_password = "passw0rd";
            final String t_firstName = "test";
            final String t_lastName = "customer";
            final Double t_balance = 0.00D;
            final Double t_latitude = 37.875339D;
            final Double t_longitude = -122.258128D;
            final Double t_radius_in_miles = Double.MAX_VALUE;
            final Long t_store_id = Long.MAX_VALUE - ((Integer) t_random).longValue();
            final String t_pickupTimeNow = "NOW"; // TODO
            
            JSONArray t_Items = new JSONArray();
            
            /**
        @param parameters              Create_Account.parameters
             * 
             * @param email            Customer's email
             * @param username         Customer's desired username
             * @param password         Customer's desired password
             * @param firstName        Customer's first name
             * @param lastName         Customer's last name
             * @param balance          Customer's initial balance
             * 
             */
            createAccount.putOpt(email.name(), t_email);
            createAccount.putOpt(username.name(), t_username);
            createAccount.putOpt(password.name(), t_password);
            createAccount.putOpt(firstName.name(), t_firstName);
            createAccount.putOpt(lastName.name(), t_lastName);
            createAccount.putOpt(balance.name(), t_balance);
            /**
             * 
        @param parameters              Login.parameters
             * 
             * @param username         Customer's username
             * @param password         Customer's password
             * 
             */
            loginWithUsername.put(Customers.username.name(), t_email);
            loginWithUsername.put(Customers.password.name(), t_password);
            
            loginWithEmail.putOpt(Customers.email.name(), t_email);
            loginWithEmail.putOpt(Customers.password.name(), t_email);
            /**
             * 
        @param parameters              Update_Account.parameters
             * 
             * @param username         Customer's current username
             * @param password         Customer's current password
             * @param new_email        (Optional) Customer's desired new email
             * @param new_username     (Optional) Customer's desired new username
             * @param new_password     (Optional) Customer's new password
             * @param new_firstName    (Optional) Customer's new first name
             * @param new_lastName     (Optional) Customer's new last name
             * @param new_balance      (Optional) Customer's new  balance
             * 
             */
            updateAccountStartingWithAllFields.putOpt(Customers.username.name(), t_username);
            updateAccountStartingWithAllFields.putOpt(Customers.password.name(), t_password);
            updateAccountStartingWithAllFields.putOpt(Extra.NONTABLE_FIELD_NEW_EMAIL, "new_" + t_email);
            updateAccountStartingWithAllFields.putOpt(Extra.NONTABLE_FIELD_NEW_USERNAME, "new_" + t_username);
            updateAccountStartingWithAllFields.putOpt(Extra.NONTABLE_FIELD_NEW_PASSWORD, "new_" + t_password);
            updateAccountStartingWithAllFields.putOpt(Extra.NONTABLE_FIELD_NEW_FIRST_NAME, "new_" + t_firstName);
            updateAccountStartingWithAllFields.putOpt(Extra.NONTABLE_FIELD_NEW_LAST_NAME, "new_" + t_lastName);
            updateAccountStartingWithAllFields.putOpt(Extra.NONTABLE_FIELD_NEW_BALANCE, "new_" + t_balance);
            /**
             * 
        @param parameters              Delete_Account.parameters
             * 
             * @param username         Customer's username
             * @param password         Customer's password
             * 
             */
            deleteAccount.putOpt(Customers.username.name(), t_username);
            deleteAccount.putOpt(Customers.password.name(), t_password);
            /** 
             *                         
        @param parameters              Search_For_Stores.parameters
             *
             * @param latitude         Customer's latitude
             * @param longitude        Customer's longitude
             * @param radius_in_miles  Search radius in miles
             * 
             */
            searchForStores.putOpt(Stores.latitude.name(), t_latitude);
            searchForStores.putOpt(Stores.latitude.name(), t_longitude);
            searchForStores.putOpt(Stores.latitude.name(), t_radius_in_miles);
            /** 
             *                         
        @param parameters              Get_Store.parameters
             *
             * @param id               ID of the store
             * 
             */
            getStore.putOpt(Stores.id.name(), t_store_id);
            /** 
             *                         
        @param parameters              Get_Store_Menu.parameters
             *
             * @param id               ID of the store
             * 
             */
            getStoreMenu.putOpt(Stores.id.name(), t_store_id);
            /**
             * 
        @param parameters              Calculate_Order_Totals.parameters
             *     
             * @param username         Customer username associated with the order
             * @param password         Customer password associated with the order
             * @param pickupTime       Time to pick up the order
             * @param Items            List of items
             * 
             */
            calculateOrderTotals.put(Customers.username.name(), t_username);
            calculateOrderTotals.put(Customers.password.name(), t_password);
            calculateOrderTotals.put(Uncommitted_Orders.pickupTime.name(), t_pickupTimeNow);
            calculateOrderTotals.put(Items.getName(), t_Items);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            fail("ERROR -- Could not initialize one or more parameter objects");
        }
    }
    
    private MainResource setup()
    {
        //testEmailFirstNameLastNameBalance.put(key, value)
        return new MainResource();
    }
    
    private boolean manualDelete(String tableName, long value)
    {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        
        Connection conn = null;
        
        java.sql.PreparedStatement ps = null;
        
        try
        {
            conn = DatabaseAdapter.newConnection();
            
            ps = conn.prepareStatement(sql);
            
            int count = ps.executeUpdate();
            
            return (count > 0);
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    @Test
    protected void createAccountTest(JSONObject o)
    {
        MainResource r = setup();
        
        // Create a new account
        
        
        // Check to see if it exists in the database
        
        
        // Manually delete from database
    }
    
    @Test
    public void loginTest(JSONObject o)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void updateAccountTest(JSONObject o)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void deleteAccountTest(JSONObject o)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void searchForStoresTest(JSONObject o)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void getStoreTest(long store_id) throws JSONException
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void getStoreMenuTest(long store_id) throws JSONException
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void calculateOrderTotalsTest(JSONObject o)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void addNonscheduledOrderTest(JSONObject clientOrder) throws JSONException
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void addScheduledOrderTest(JSONObject o) throws JSONException
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void updateOrderTest(JSONObject o)
    {
        // TODO Auto-generated method stub
        
    }

    @Test
    public void deleteOrderTest(JSONObject o)
    {
        // TODO Auto-generated method stub
        
    }

    @Test
    public void startOrderProcessorTest()
    {
        // TODO Auto-generated method stub
        
    }

    @Test
    public void stopOrderProcessorTest()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void getOrderProcessorStatusTest()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    public void orderPingTest()
    {
        // TODO Auto-generated method stub
        
    }
    
    public static final String mySQL_url = "jdbc:mysql://107.20.135.212:3306/check-please2?allowMultiQueries=true&characterEncoding=UTF-8&sessionVariables=time_zone='-8:00'";
    
    private static final String mySQL_userName = "check-please2";
    private static final String mySQL_password = "!checkPlease!@";
    private static final String mySQL_JDBC_Driver = "com.mysql.jdbc.Driver";
    
    private static Connection newConnection()
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
            System.exit(1);
            return null;
        }
    }
    
}