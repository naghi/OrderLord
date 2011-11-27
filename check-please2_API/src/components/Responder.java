/** @author Ryan Gaffney */

package components;

import static components.DatabaseAdapter.close;
import static components.DatabaseAdapter.getColumnTypes;
import static components.DatabaseAdapter.newConnection;
import static components.DatabaseAdapter.resultSetToJSONArray;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import components.Table.Customers;
import components.Table.Items;
import components.Table.Stores;
import components.Table.Uncommitted_Orders;
import components.Table.Uncommitted_Orders_Items;

public enum Responder
{   
    Create_Account()
    {
        /**
         * 
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
        @Override
        public JSONObject execute(JSONObject params)
        {
            try
            {
                conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;

                final String f1 = Customers.email.name();
                final String a1_S = params.getString(f1);

                final String f2 = Customers.username.name();
                final String a2_S = params.getString(f2);

                final String f3 = Customers.password.name();
                final String a3_S = params.getString(f3);

                final String f4 = Customers.firstName.name();
                final String a4_S = params.getString(f4);

                final String f5 = Customers.lastName.name();
                final String a5_S = params.getString(f5);

                final String f6 = Customers.balance.name();
                final Double a6_D = params.getDouble(f6);

                final String q =
                        "INSERT INTO "
                        + Customers.getName()
                        + " ("
                        + f1 + ", "
                        + f2 + ", "
                        + f3 + ", "
                        + f4 + ", "
                        + f5 + ", "
                        + f6
                        + ") "
                        + "VALUES"
                        + "(?, ?, ?, ?, ?, ?)";
                
                ps = conn.prepareStatement(q, PreparedStatement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, a1_S);
                ps.setString(2, a2_S);
                ps.setString(3, a3_S);
                ps.setString(4, a4_S);
                ps.setString(5, a5_S);
                ps.setDouble(6, a6_D);
                
                int updateCount = ps.executeUpdate();
                
                if (updateCount == 0)
                    return null;
                
                rs = ps.getGeneratedKeys();
                
                if (rs != null && rs.next())
                {
                    JSONObject result = Responder_Helper.selectOne_FieldValueEqualsId(Customers.getName(), Customers.id.name(), rs.getLong(1));
                    return result.length() > 0 ? result : null;
                }
                return null;
            }
            catch (JSONException | SQLException e)
            {
                e.printStackTrace();
                return null;
            }
            finally
            {
                DatabaseAdapter.close(conn, ps, rs, cs);
            }
        }

        @Override
        public JSONObject sampleRequestInput()
        { 
            JSONObject result = Login.sampleResponseOutput();
            
            result.remove(Customers.id.name());
            result.remove(Customers.version.name());

            return result;
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Login.sampleResponseOutput();
        }
    },
    Login()
    {
        /**
         * 
    @param parameters              Login.parameters
         * 
         * @param username         Customer's username
         * @param password         Customer's password
         * 
         */
        @Override
        public JSONObject execute(JSONObject parameters)
        {
            return Responder_Helper.selectCustomer(parameters);
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            JSONObject fullCustomer = Responder_Helper.sample(Customers.getName(), false);
            JSONObject required = new JSONObject();
            
            if (fullCustomer == null)
                return null;

            try
            {
                required.put(Table.Customers.username.name(), fullCustomer.get(Table.Customers.username.name()));
                required.put(Table.Customers.password.name(), fullCustomer.get(Table.Customers.password.name()));

                return required;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Login.execute(Login.sampleRequestInput());
        }
    },
    Update_Account()
    {
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
        @Override
        public JSONObject execute(JSONObject parameters)
        {
            try
            {
                conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;
                
                final KeyValuePair[] changeables =
                    {
                        new KeyValuePair(Extra.NONTABLE_FIELD_NEW_EMAIL, parameters.optString(Extra.NONTABLE_FIELD_NEW_EMAIL)),
                        new KeyValuePair(Extra.NONTABLE_FIELD_NEW_USERNAME, parameters.optString(Extra.NONTABLE_FIELD_NEW_USERNAME)),
                        new KeyValuePair(Extra.NONTABLE_FIELD_NEW_PASSWORD, parameters.optString(Extra.NONTABLE_FIELD_NEW_PASSWORD)),
                        new KeyValuePair(Extra.NONTABLE_FIELD_NEW_FIRST_NAME, parameters.optString(Extra.NONTABLE_FIELD_NEW_FIRST_NAME)),
                        new KeyValuePair(Extra.NONTABLE_FIELD_NEW_LAST_NAME, parameters.optString(Extra.NONTABLE_FIELD_NEW_LAST_NAME)),
                        new KeyValuePair(Extra.NONTABLE_FIELD_NEW_BALANCE, parameters.optDouble(Extra.NONTABLE_FIELD_NEW_BALANCE))
                    };
                
                final int totalChangeables = changeables.length;
                
                int shift[] = new int[totalChangeables];
                
                int last = -1;
                int index = 0;
                
                for (KeyValuePair kvp : changeables)
                {
                    if (kvp.value() == null)
                    {
                        for (int i = 1 + index; i < totalChangeables; ++i)
                            shift[i] += 1;
                    }
                    else last = index;
                    
                    ++index;
                }
                
                if (last == -1) return null;
                
                String sql = "UPDATE " + Customers.getName() + " SET ";
                
                index = 0;
                for (KeyValuePair kvp : changeables)
                {
                    sql += ((kvp.value() != null) ? kvp.key() + " = ?" + ((last != index) ? ", " : " ") : "");
                    
                    ++index;
                }
                
                sql += " WHERE (" + Customers.username.name() + " = ? OR " + Customers.email.name() + " = ?)" +
                        " AND " + Customers.password.name() + " = ?;";
                
                ps = conn.prepareStatement(sql);
                
                index = 0;
                for (KeyValuePair kvp : changeables)
                {
                    if (kvp.value() instanceof String)
                        ps.setString(index - shift[index], (String) kvp.value());
                    
                    else if (kvp.value() instanceof Double)
                        ps.setDouble(index - shift[index], (Double) kvp.value());
                    
                    else return null;
                    
                    ++index;
                }
                
                int count = ps.executeUpdate();
                
                if (count == 0)
                    return null;
                
                return Login.execute(parameters);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return null;
            }
            finally
            {
                DatabaseAdapter.close(conn, ps);
            }
        }
        
        @Override
        public JSONObject sampleRequestInput()
        {
            try
            {                
                JSONObject result = new JSONObject();
                
                result.put(Customers.username.name(), "(Required) myCurrentUsernameOrEmail");
                result.put(Customers.password.name(), "(Required) myCurrentPassword");
                result.put(Extra.NONTABLE_FIELD_NEW_EMAIL, "(Optional) new_email@mail.com");
                result.put(Extra.NONTABLE_FIELD_NEW_USERNAME, "(Optional) pleaseBeAvailable001");
                result.put(Extra.NONTABLE_FIELD_NEW_PASSWORD, "(Optional) myNeWPaZSW0rD");
                result.put(Extra.NONTABLE_FIELD_NEW_FIRST_NAME, "(Optional) Jonathan");
                result.put(Extra.NONTABLE_FIELD_NEW_LAST_NAME, "(Optional) Peters");
                result.put(Extra.NONTABLE_FIELD_NEW_BALANCE, "(Optional) Double.MAX_VALUE");
                
                return result;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Login.sampleResponseOutput();
        }
    },
    Delete_Account()
    {
        /**
         * 
    @param parameters              Delete_Account.parameters
         * 
         * @param username         Customer's username
         * @param password         Customer's password
         * 
         */
        @Override
        public JSONObject execute(JSONObject parameters)
        {
            JSONObject customer = Responder.Login.execute(parameters);
            
            if (customer == null)
                return null;

            try
            {
                conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;

                String sql = "DELETE FROM " + Customers.getName() + " WHERE " + Customers.username.name() + " = ? AND " + Customers.password.name() + " = ?;";

                ps = conn.prepareStatement(sql);
                
                ps.setString(1, parameters.getString(Customers.username.name()));
                ps.setString(2, parameters.getString(Customers.password.name()));

                int count = ps.executeUpdate();

                if (count == 0)
                    return null;

                return parameters;

            }
            catch (SQLException | JSONException e)
            {
                e.printStackTrace();
                return null;
            }
            finally
            {
                DatabaseAdapter.close(conn, ps, rs);
            }
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return Responder_Helper.sample(Customers.getName(), false);
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Responder_Helper.sample(Customers.getName(), false);
        }
    },
    Search_For_Stores()
    {
        /** 
         *                         
    @param parameters              Search_For_Stores.parameters
         *
         * @param latitude         Customer's latitude
         * @param longitude        Customer's longitude
         * @param radius_in_miles  Search radius in miles
         * 
         */
        @Override
        public JSONObject execute(JSONObject params)
        { 
            try
            {
                DatabasePackage searchPackage = new DatabasePackage();

                searchPackage.createSearchPackage(
                        params.getDouble(Table.Stores.latitude.name()),
                        params.getDouble(Table.Stores.longitude.name()),
                        params.getDouble(Extra.NONTABLE_FIELD_RADIUS_IN_MILES));

                return StoredProcedures.Search_For_Stores.call(searchPackage);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return (new DatabasePackage()).createSearchPackage(
                    37.8791D,
                    -122.2667D,
                    5.00D).toJSON();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Responder_Helper.sample(Stores.getName(), true);
        }
    },
    Get_Store()
    {
        /** 
         *                         
    @param parameters              Get_Store.parameters
         *
         * @param id               ID of the store
         * 
         */
        @Override
        public JSONObject execute(JSONObject parameters)
        {
            try
            {
                JSONObject store = Responder_Helper.selectOne_FieldValueEqualsId(Stores.getName(), Stores.id.name(), parameters.getLong(Stores.id.name()));
                
                if (store == null)
                    return null;
                
                store.remove(Stores.password.name());
                store.remove(Stores.username.name());
                store.remove(Stores.role.name());
                
                return store;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return new JSONObject();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Responder_Helper.sample(Stores.getName(), false);
        }
    },
    Get_Store_Menu()
    {
        /** 
         *                         
    @param parameters              Get_Store_Menu.parameters
         *
         * @param id               ID of the store
         * 
         */
        @Override
        public JSONObject execute(JSONObject parameters)
        {
            try
            {
                return Responder_Helper.selectAll_FieldValueEqualsId(Items.getName(), Items.store_id.name(), parameters.getLong(Stores.id.name()));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return new JSONObject();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            try
            {
                JSONArray items = Responder_Helper.getSampleOrder().getJSONArray(Items.getName());
                JSONObject result = new JSONObject();
                result.put(Items.getName(), items);
                
                return result;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    },
    Calculate_Order_Totals()
    {
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
        @Override
        public JSONObject execute(JSONObject clientOrder)
        {            
            DatabasePackage serverOrder = Responder_Helper.processClientOrder(clientOrder);
            
            if (serverOrder == null)
                return null;

            try
            {
                JSONObject order = serverOrder.toJSON();

                JSONObject result = new JSONObject();

                result.put(Uncommitted_Orders.etp.name(), order.getLong(Uncommitted_Orders.etp.name()));
                result.put(Uncommitted_Orders.price.name(), order.getDouble(Uncommitted_Orders.price.name()));

                System.out.println("serverOrder: " + serverOrder + " vs result: " + result);
                
                return result;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return Responder_Helper.getSampleOrder();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Calculate_Order_Totals.execute(Responder_Helper.getSampleOrder());
        }
    },
    Submit_Nonscheduled_Order()
    {
        /**
         * 
    @param parameters              Submit_Nonscheduled_Order.parameters
         *     
         * @param username         Customer username associated with the order
         * @param password         Customer password associated with the order
         * @param pickupTime       Time to pick up the order
         * @param Items            List of items
         * 
         */
        @Override
        public JSONObject execute(JSONObject clientOrder)
        {
            DatabasePackage serverOrder = Responder_Helper.processClientOrder(clientOrder);
            
            return StoredProcedures.Place_New_Order.call(serverOrder);
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return Responder_Helper.getSampleOrder();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            try
            {
                JSONObject order = Responder_Helper.processClientOrder(Responder_Helper.getSampleOrder()).toJSON();
                
                order.put(Uncommitted_Orders.id.name(), 23L);
                
                return order;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return new JSONObject();
            }
        }
    },
    Submit_Scheduled_Order()
    {
        /**
         * 
    @param parameters              Submit_Scheduled_Order.parameters
         *     
         * @param username         Customer username associated with the order
         * @param password         Customer password associated with the order
         * @param pickupTime       Time to pick up the order
         * @param Items            List of items
         * 
         */
        @Override
        public JSONObject execute(JSONObject clientOrder)
        {            
            DatabasePackage serverOrder = Responder_Helper.processClientOrder(clientOrder);
            
            return StoredProcedures.Place_Scheduled_Order.call(serverOrder);
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return Submit_Nonscheduled_Order.sampleRequestInput();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Submit_Nonscheduled_Order.sampleResponseOutput();
        }
    },
    Update_Order()
    {
        /** 
         * 
    @param parameters              Update_Order.parameters
         *     
         * @param username         Customer username associated with the order
         * @param password         Customer password associated with the order
         * @param pickupTime       Time to pick up the order
         * @param Items            List of items
         * @param order_id         The ID of the order to be modified
         * 
         */
        @Override
        public JSONObject execute(JSONObject parameters)
        {
            try
            {
                conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;
                
                String sql =
                        "SELECT DISTINCT uo.* FROM " +
                                Uncommitted_Orders.getName() + " AS uo, " +
                                Customers.getName() + " AS cu" +
                                " WHERE uo." + Uncommitted_Orders.id.name() + " = ?" + 
                                " AND uo." + Uncommitted_Orders.customer_id.name() +
                                " IN(SELECT cust." + Customers.id.name() +
                                " FROM " + Customers.getName() +
                                " AS cust WHERE " +
                                Customers.username.name() + " = ? AND " + Customers.password.name() + " = ?)";
                
                ps = conn.prepareStatement(sql);
                
                ps.setLong(1, parameters.getLong(Uncommitted_Orders_Items.order_id.name()));
                
                rs = ps.executeQuery();
                
                JSONArray result = DatabaseAdapter.resultSetToJSONArray(rs);
                
                if (result == null || result.length() == 0)
                    return null;
                
                JSONObject orderToUpdate = result.getJSONObject(0);
                
                int scheduleDay = orderToUpdate.getInt(Uncommitted_Orders.scheduleDay.name());
                
                Responder.Delete_Order.execute(parameters);
                
                return (scheduleDay > 0) ? Responder.Submit_Scheduled_Order.execute(parameters) : Responder.Submit_Nonscheduled_Order.execute(parameters);
            }
            catch (JSONException | SQLException e)
            {
                e.printStackTrace();
                return null;
            }
            finally
            {
                DatabaseAdapter.close(conn, ps, rs);
            }
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            try
            {                
                JSONObject newOrder = Responder.Submit_Nonscheduled_Order.sampleRequestInput();
                
                newOrder.put(Uncommitted_Orders_Items.order_id.name(), "< Your previous order ID >");
                
                return newOrder;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Responder.Submit_Nonscheduled_Order.sampleResponseOutput();
        }
        
    },
    Delete_Order()
    {
        /** 
         * 
    @param parameters              Delete_Order.parameters
         *
         * @param username         Customer username associated with the order
         * @param password         Customer password associated with the order
         * @param order_id         Order ID to be deleted
         * 
         */
        @Override
        public JSONObject execute(JSONObject parameters)
        {
            JSONObject customer;

            if ((customer = Responder.Login.execute(parameters)) == null)
                /* Invalid credentials */
                return null;
            
            try
            {
                conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;

                String sql = "DELETE FROM " + Uncommitted_Orders.getName() + " WHERE " + Uncommitted_Orders.id.name() + " = ? AND " + Uncommitted_Orders.customer_id.name() + " = ?";
                
                System.out.println("INFO -- Preparing delete statement: " + sql);
                
                ps = conn.prepareStatement(sql);
                
                ps.setLong(1, parameters.getLong(Uncommitted_Orders_Items.order_id.name()));
                ps.setLong(2, customer.getLong(Customers.id.name()));
                
                int count = ps.executeUpdate();
                
                if (count == 0)
                    /* No such order, or order was already committed */
                    return null;
                
                return parameters;
            }
            catch (SQLException | JSONException e)
            {
                e.printStackTrace();
                return null;
            }
            finally
            {
                DatabaseAdapter.close(conn, ps);
            }
        }
        
        @Override
        public JSONObject sampleRequestInput()
        {
            return Update_Order.sampleRequestInput();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return Responder.Update_Order.sampleRequestInput();
        }
    },
    Retreive_Database_Types()
    {
            /** 
             *                         
        @param parameters              Retreive_Database_Types.parameters
             *
             */
        @Override
        public JSONObject execute(JSONObject arbitrary)
        {
            JSONObject result = new JSONObject();

            try
            {
                for (Class<?> t : Table.list)
                    result.put(t.getSimpleName(), Responder_Helper.getColumnNamesAndTypes(t.getSimpleName()));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            
            return result;
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return new JSONObject();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return execute(null);
        }
    },
    Retreive_Database_Contents()
    {
        /** 
         *                         
    @param parameters              Retreive_Database_Contents.parameters
         *
         */
        @Override
        public JSONObject execute(JSONObject arbitrary)
        {
            JSONObject result = new JSONObject();

            try
            {
                conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;
                
                String q = "";
                for (Class<?> t : Table.list)
                {        
                    q = "SELECT * FROM " + t.getSimpleName() + ";";
                    
                    ps = conn.prepareStatement(q);
                    
                    rs = ps.executeQuery();
                    
                    JSONArray jay = DatabaseAdapter.resultSetToJSONArray(rs);
                    
                    result.put(t.getSimpleName(), jay);
                }
                
                return result;
            }
            catch (JSONException | SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                DatabaseAdapter.close(conn, ps, rs);
            }
            
            return result;
        }

        @Override
        public JSONObject sampleRequestInput()
        {
            return new JSONObject();
        }

        @Override
        public JSONObject sampleResponseOutput()
        {
            return execute(null);
        }
    };
    
    protected CallableStatement cs = null;
    protected PreparedStatement ps = null;
    protected ResultSet rs = null;
    protected Connection conn = null;
    
    public abstract JSONObject execute(JSONObject parameters);
    public abstract JSONObject sampleRequestInput();
    public abstract JSONObject sampleResponseOutput();
}

// ===============================================================
// RESPONDER HELPER
// ===============================================================

final class Responder_Helper
{
    protected final static DatabasePackage processClientOrder(JSONObject clientOrder)
    {
        DatabasePackage serverOrder = new DatabasePackage();

        System.out.println("INFO -- Converting client order: " + clientOrder);

        Connection conn = null;
        ResultSet rs = null;
        Statement s = null;
        
        try
        {            
            if (clientOrder == null
                    || clientOrder.isNull(Customers.username.name())
                    || clientOrder.isNull(Customers.password.name())
                    || clientOrder.isNull(Uncommitted_Orders.pickupTime.name()))
                return null;

            JSONObject customer = new JSONObject();

            customer.put(Customers.username.name(), clientOrder.getString(Customers.username.name()));
            customer.put(Customers.password.name(), clientOrder.getString(Customers.password.name()));

            if ((customer = selectCustomer(customer)) == null)
                return null;

            JSONArray items = clientOrder.getJSONArray(Items.getName());
            
            /* For security purposes,
             * 
             * Only use the item IDs for calculations
             */

            conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;

            JSONObject item;
            String inValues = "(";

            for (int i = 0; i < items.length(); ++i)
            {
                item = items.getJSONObject(i);
                inValues += ("'" + item.getLong(Items.id.name()) + "'");
                inValues += (1 + i != items.length()) ? ", " : ")";
            }

            inValues = "SELECT " + Items.etp.name() + ", " + Items.price.name() + " FROM " + Items.getName() + " WHERE " + Items.id.name() + " IN " + inValues; 

            s = conn.createStatement();

            final double ITEM_WEIGHT = 0.20D;

            BigDecimal orderTotal = new BigDecimal(0.00D);
            BigDecimal priceToAdd;

            long weightedETPTotal = 0L;
            long currentETP = 0L;
            long maxETPSoFar = 0L;

            if (s.execute(inValues))
            {
                rs = s.getResultSet();

                while (rs.next())
                {
                    currentETP = rs.getLong(1);
                    maxETPSoFar = (currentETP > maxETPSoFar) ? currentETP : maxETPSoFar;
                    weightedETPTotal += ITEM_WEIGHT*weightedETPTotal;

                    priceToAdd = new BigDecimal(rs.getDouble(2));
                    orderTotal = orderTotal.add(priceToAdd);
                }
                
                weightedETPTotal += (maxETPSoFar*(1.00D + ITEM_WEIGHT));
                orderTotal = orderTotal.setScale(2, RoundingMode.HALF_UP);

                serverOrder = new DatabasePackage();

                serverOrder.createServerOrderPackage(
                        getItemIDs(items),
                        weightedETPTotal,
                        clientOrder.getString(Uncommitted_Orders.pickupTime.name()),
                        orderTotal,
                        customer.getLong(Customers.id.name()),
                        items.getJSONObject(0).getLong(Uncommitted_Orders.store_id.name()));
                
                System.out.println("INFO -- Client order converted to: " + serverOrder);

                return serverOrder.myItems != null && serverOrder.myItems.size() > 0 ? serverOrder : null;
            }
            else return null;
        }
        catch (JSONException | SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            DatabaseAdapter.close(conn, rs);
        }
    }

    private final static String getItemIDs(JSONArray items)
    {
        String itemIDList = "";
        int len = items.length();

        try
        {
            String next = "";

            for (int i = 0; i < len; ++i)
            {
                next = items.getJSONObject(i).getString(Items.id.name());
                itemIDList += (1 + i != len) ? next + "|" : next;
            }
        }
        catch (Exception e) { e.printStackTrace(); }

        return itemIDList;
    }

    protected final static JSONObject selectOne_FieldValueEqualsId(String tableName, String field, long id)
    {
        return selectFieldValueEqualsId(tableName, field, id, 1L);
    }

    protected final static JSONObject selectAll_FieldValueEqualsId(String tableName, String field, long id)
    {
        return selectFieldValueEqualsId(tableName, field, id, Long.MAX_VALUE);
    }

    protected final static JSONObject selectCustomer(JSONObject params)
    {
        Connection connection = newConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (connection == null)
            return null;

        try
        {            
            final String sql =
                    "SELECT * FROM " +
                            Customers.getName() +
                            " WHERE (" + Customers.username.name() +
                            " = ? OR " + Customers.email.name() +
                            " = ?) AND " +
                            Customers.password.name() + " = ?";
            
            ps = connection.prepareStatement(sql);
            
            ps.setString(1, params.getString(Customers.username.name()));
            ps.setString(2, params.getString(Customers.username.name()));
            ps.setString(3, params.getString(Customers.password.name()));

            rs = ps.executeQuery();

            if (rs.next())
            {
                JSONObject result = new JSONObject();
                
                result.put(Customers.id.name(), rs.getLong(Customers.id.name()));
                result.put(Customers.version.name(), rs.getLong(Customers.version.name()));
                result.put(Customers.email.name(), rs.getString(Customers.email.name()));
                result.put(Customers.username.name(), rs.getString(Customers.username.name()));
                result.put(Customers.password.name(), JSONObject.NULL);
                result.put(Customers.firstName.name(), rs.getString(Customers.firstName.name()));
                result.put(Customers.lastName.name(), rs.getString(Customers.lastName.name()));
                result.put(Customers.balance.name(), rs.getDouble(Customers.balance.name()));
                
                return result;
            }
            else return null;
        }
        catch (JSONException | SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            close(connection, ps, rs);
        }
    }

    private final static JSONObject selectFieldValueEqualsId(String tableName, String field, long id, long limit)
    {        
        Connection connection = newConnection();

        JSONObject obj = new JSONObject();

        try
        {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + " AS ft WHERE ft." + field + " = " + id + " LIMIT " + limit); 

            JSONArray array = resultSetToJSONArray(rs);

            if (limit == 1 && array != null && array.length() > 0)
                return array.getJSONObject(0);

            else
            {
                obj.put(tableName, array);

                return obj;
            }
        }
        catch (Exception e) { e.printStackTrace(); }

        return null;
    }

    protected final static JSONObject getSampleOrder()
    {
        Random r = new Random();

        JSONObject clientOrder = new JSONObject();
        JSONArray clientArray = null;
        JSONObject client = new JSONObject();

        try
        {
            clientArray = executeQuery("SELECT cust.* FROM " + Customers.getName() + " AS cust WHERE cust." + Customers.id.name() + " = (SELECT MAX(c." + Customers.id.name() + ") FROM " + Customers.getName() + " AS c)");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        try
        {
            if (clientArray == null || clientArray.length() == 0)
            {
                // Improvise
                int currentRandom = r.nextInt(Math.abs(r.nextInt()));
                String email = "email" + currentRandom + "@mail.com";
                String username = "User_" + currentRandom;
                String password = Table.Customers.password.name().toUpperCase();
                String firstName = "Fake";
                String lastName = "Name";
                Double balance = 1000000.00D;

                client.put(Customers.email.name(), email);
                client.put(Customers.username.name(), username);
                client.put(Customers.password.name(), password);
                client.put(Customers.firstName.name(), firstName);
                client.put(Customers.lastName.name(), lastName);
                client.put(Customers.balance.name(), balance);

                client = components.Responder.Login.execute(client);
            }
            else client = clientArray.getJSONObject(0);

            JSONArray menuItems = executeQuery("SELECT it.* FROM " + Items.getName() + " AS it WHERE it." + Items.store_id.name() + " = (SELECT MAX(i." + Items.store_id.name() + ") FROM " + Items.getName() + " AS i, " + Stores.getName() + " AS s WHERE i." + Items.store_id.name() + " = s." + Stores.id.name() + ")");

            java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
            Timestamp ts = new Timestamp(now.getTime());
            String pickupTime = ts.toString().replaceAll("[.][0-9]*$", "");

            clientOrder.put(Customers.username.name(), client.get(Customers.username.name()));
            clientOrder.put(Customers.password.name(), client.get(Customers.password.name()));
            clientOrder.put(Uncommitted_Orders.pickupTime.name(), pickupTime);
            clientOrder.put(Items.getName(), menuItems);
        }
        catch (Exception e) { e.printStackTrace(); }

        return clientOrder;
    }

    private final static JSONArray executeQuery(String query) throws SQLException
    {
        if (query == null)
            return null;

        Connection connection = newConnection();

        PreparedStatement ps = connection.prepareStatement(query);

        ps.executeQuery();

        ResultSet rs = ps.getResultSet();

        JSONArray result = DatabaseAdapter.resultSetToJSONArray(rs);

        DatabaseAdapter.close(connection, ps, rs);

        return result;
    }

    private final static JSONObject selectAll(String tableName)
    {
        return select(tableName, Long.MAX_VALUE);
    }

    private final static JSONObject select(String tableName, long limit)
    {
        Connection connection = newConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("SELECT * FROM " + tableName + " LIMIT " + limit);
            ps.executeQuery();
            rs = ps.getResultSet();

            JSONObject o = new JSONObject();

            JSONArray result = resultSetToJSONArray(rs);

            o.put(tableName, result);

            DatabaseAdapter.close(connection, ps, rs);

            return (limit == 1 && result.length() > 0) ? result.getJSONObject(0) : o;
        }
        catch (Exception e) { e.printStackTrace(); }

        return null;
    }

    private final static JSONObject selectOne(String tableName)
    {
        return select(tableName, 1L);
    }

    protected final static JSONObject sample(String tableName, boolean all)
    {        
        JSONObject databaseSample = (all) ? selectAll(tableName) : selectOne(tableName);

        if (tableName.equals(Stores.getName()))
        {
            List<String> fieldsToRemoveFromStoreRequest = new LinkedList<String>();

            fieldsToRemoveFromStoreRequest.add(Stores.username.name());
            fieldsToRemoveFromStoreRequest.add(Stores.password.name());
            fieldsToRemoveFromStoreRequest.add(Stores.role.name());

            databaseSample = deepPurge(databaseSample, fieldsToRemoveFromStoreRequest);
        }

        else if (tableName.equals(Uncommitted_Orders.getName()))
            databaseSample = Responder_Helper.getSampleOrder();

        return databaseSample.length() > 0 ? databaseSample : getColumnNamesAndTypes(tableName);
    }

    protected final static JSONObject generateEmptyClientOrder()
    {
        try
        {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            List<String> keysToKeep = new ArrayList();
            keysToKeep.add(Customers.username.name());
            keysToKeep.add(Customers.password.name());

            JSONObject clientOrder = valueCopy(
                    getColumnNamesAndTypes(Customers.getName()), keysToKeep);

            clientOrder.put(Uncommitted_Orders.pickupTime.name(),
                    "yyyy-mm-dd hh:mm:ss");

            System.out.println("clientOrder = " + clientOrder);

            JSONArray items = new JSONArray();
            items.put(getColumnNamesAndTypes(Items.getName()));
            items.put(getColumnNamesAndTypes(Items.getName()));
            clientOrder.put(Items.getName(), items);

            System.out.println("clientOrder = " + clientOrder);

            return clientOrder;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private final static JSONObject deepPurge(JSONObject o, List<String> keysToRemove)
    {
        try
        {
            JSONObject next = new JSONObject();
            Queue<JSONObject> queue = new LinkedList<JSONObject>();

            queue.add(o);

            String nextKey = "";
            Object nextValue;

            while ((next = queue.poll()) != null)
            {
                @SuppressWarnings("unchecked")
                Iterator<String> jsonIterator = next.keys();

                while (jsonIterator.hasNext())
                {
                    nextKey = jsonIterator.next();
                    nextValue = next.get(nextKey);

                    if (keysToRemove.contains(nextKey))
                        jsonIterator.remove();

                    else if (nextValue instanceof JSONObject)
                        queue.add((JSONObject) nextValue);

                    else if (nextValue instanceof JSONArray)
                    {
                        for (int i = 0; i < ((JSONArray) nextValue).length(); ++i)
                            queue.add(((JSONArray) nextValue).getJSONObject(i));
                    }
                }
            }
            return o;
        }
        catch (ConcurrentModificationException | JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private final static JSONObject valueCopy(JSONObject o, List<String> keysToKeep)
    {
        try
        {
            JSONObject result = new JSONObject();

            for (String key : keysToKeep)
                result.put(key, o.get(key));

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    protected final static JSONObject getColumnNamesAndTypes(String tableName)
    {
        Connection connection = newConnection();

        JSONObject obj = new JSONObject();

        try
        {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM " + tableName
                    + " LIMIT 0");

            JSONArray array = getColumnTypes(rs);

            return array.length() > 0 ? array.getJSONObject(0) : null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return obj;
    }
}