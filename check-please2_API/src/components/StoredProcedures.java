/** @author Ryan Gaffney */

package components;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import components.Table.Uncommitted_Orders;

public enum StoredProcedures
{
    Search_For_Stores()
    {
        private final static String Drop_Search_For_Stores =
                "DROP PROCEDURE IF EXISTS `check-please2`.`Search_For_Stores`;";

        private final static String Search_For_Stores =
                " CREATE PROCEDURE `check-please2`.`Search_For_Stores`" +
                        "(" +
                        "IN in_lat FLOAT, " +
                        "IN in_lon FLOAT, " +
                        "IN in_max_distance FLOAT" +
                        ")\n" +
                        " BEGIN" +
                        " DECLARE lon1 float; DECLARE lon2 float;" +
                        " DECLARE lat1 float; DECLARE lat2 float;" +
                        " SET lon1 = in_lon - in_max_distance/abs(cos(radians(in_lat))*111);" +
                        " SET lon2 = in_lon + in_max_distance/abs(cos(radians(in_lat))*111);" +
                        " SET lat1 = in_lat - (in_max_distance/111);" +
                        " SET lat2 = in_lat+(in_max_distance/111);" +
                        " SELECT id, version, name, address, latitude, longitude, phoneNumber, pictureLink, 6271 * 2 * ASIN(SQRT(POWER(SIN((in_lat - latitude) * pi()/180 / 2), 2) + COS(in_lat * pi()/180) *COS(latitude * pi()/180) * POWER(SIN((in_lon - longitude) * pi()/180 / 2), 2))) AS distance" +
                        " FROM Stores" +
                        " WHERE longitude BETWEEN lon1 AND lon2 AND latitude BETWEEN lat1 AND lat2" +
                        " AND NOT role = 'admin'" +
                        " HAVING distance <= in_max_distance" +
                        " ORDER BY distance ASC" +
                        " LIMIT 0,100;\n" +
                        " END;";

        @Override
        public void drop()
        {
            executeStatement(Drop_Search_For_Stores);
        }

        @Override
        public void create()
        {
            drop();
            executeStatement(Search_For_Stores);
        }

        @Override
        public JSONObject call(DatabasePackage params)
        {
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;

            try
            {
                conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;

                cs = conn.prepareCall("{CALL " + components.Responder.Search_For_Stores.name() + "(?, ?, ?)}");

                cs.setDouble(1, (Double) params.myItems.get(0).value());
                cs.setDouble(2, (Double) params.myItems.get(1).value());
                cs.setDouble(3, (Double) params.myItems.get(2).value());

                cs.execute();

                rs = cs.getResultSet();

                JSONArray stores = DatabaseAdapter.resultSetToJSONArray(rs);
                
                JSONObject result = new JSONObject();

                result.put(Table.Stores.getName(), stores);

                return result;
            }
            catch (SQLException | JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    },
    String_Splitter()
    {
        private final static String Drop_String_Splitter =
                "DROP PROCEDURE IF EXISTS `check-please2`.`String_Splitter`;";

        private final static String String_Splitter =
                /* Source: http://lists.mysql.com/mysql/199134 */
                " CREATE PROCEDURE `check-please2`.`String_Splitter`" +
                "(" +
                "IN input TEXT, " +
                "IN delimiter VARCHAR(10)" + 
                ")\n" +
                " SQL SECURITY INVOKER" +
                " BEGIN" +
                " DECLARE cur_position INT DEFAULT 1 ;" +
                " DECLARE remainder TEXT; " +
                " DECLARE cur_string VARCHAR(1000); " +
                " DECLARE delimiter_length TINYINT UNSIGNED;" +
                " DROP TEMPORARY TABLE IF EXISTS `check-please2`.SplitValues;" +
                " CREATE TEMPORARY TABLE SplitValues" +
                "(" +
                "value VARCHAR(1000) NOT NULL" +
                ") ENGINE=MEMORY;" +
                " SET remainder = input;" +
                " SET delimiter_length = CHAR_LENGTH(delimiter);" +
                " WHILE CHAR_LENGTH(remainder) > 0 AND cur_position > 0 DO" +
                " SET cur_position = INSTR(remainder, delimiter);" +
                " IF cur_position = 0 THEN" +
                " SET cur_string = remainder;" +
                " ELSE " +
                " SET cur_string = LEFT(remainder, cur_position - 1);" +
                " END IF;" +
                " IF TRIM(cur_string) != '' THEN" +
                " INSERT INTO SplitValues VALUES (cur_string);" +
                " END IF;" +
                " SET remainder = SUBSTRING(remainder, cur_position + delimiter_length);" +
                " END WHILE;" +
                " END;";

        @Override
        public void drop()
        {
            executeStatement(Drop_String_Splitter);
        }

        @Override
        public void create()
        {                
            drop();
            executeStatement(String_Splitter);
        }

        @Override
        public JSONObject call(DatabasePackage params)
        {
            // TODO Auto-generated method stub
            return null;
        }
    },
    Place_New_Order()
    {
        private final static String Drop_Place_New_Order = 
                "DROP PROCEDURE IF EXISTS `check-please2`.`Place_New_Order`;";

        private final static String Place_New_Order =
                "CREATE PROCEDURE `check-please2`.`Place_New_Order`" +
                        "(" +
                        "IN in_itemIDs TEXT, " +
                        "IN in_etp BIGINT UNSIGNED, " +
                        "IN in_pickupTime DATETIME, " +
                        "IN in_price DOUBLE UNSIGNED, " +
                        "IN in_customer_id BIGINT UNSIGNED, " +
                        "IN in_store_id BIGINT UNSIGNED, " +
                        "OUT newOrderId BIGINT UNSIGNED" +
                        ")" +
                        " BEGIN" +
                        " DROP TABLE IF EXISTS `check-please2`.`TMP_ORDER_ITEMS`;" +
                        " SET @ItemIDs := in_itemIDs;" +
                        /* Create new table of same size as item list */
                        " CALL String_Splitter(@ItemIDs, '|');" +
                        /* Set up items_idx for new items */
                        " CREATE TEMPORARY TABLE `check-please2`.`TMP_ORDER_ITEMS` SELECT * FROM SplitValues;" +
                        " ALTER TABLE `check-please2`.`TMP_ORDER_ITEMS` CHANGE `value` `item_id` BIGINT UNSIGNED;" +
                        " ALTER TABLE `check-please2`.`TMP_ORDER_ITEMS` ADD `order_id` BIGINT UNSIGNED;" +
                        " ALTER TABLE `check-please2`.`TMP_ORDER_ITEMS` ADD `items_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT, ADD PRIMARY KEY (`items_idx`);" +
                        " UPDATE `check-please2`.`TMP_ORDER_ITEMS` SET `items_idx` = `items_idx` - 1;" +
                        " ALTER TABLE `check-please2`.`TMP_ORDER_ITEMS` CHANGE `items_idx` `items_idx` BIGINT UNSIGNED NOT NULL;" +
                        " ALTER TABLE `check-please2`.`TMP_ORDER_ITEMS` DROP PRIMARY KEY;" +
                        /* Insert the order into Uncommitted_Orders */
                        " INSERT INTO `check-please2`.`Uncommitted_Orders` (etp, pickupTime, price, customer_id, store_id)" +
                        " VALUES (in_etp, in_pickupTime, in_price, in_customer_id, in_store_id);" +
                        " SELECT LAST_INSERT_ID() INTO newOrderId;" +
                        /* Finalize the temporary table by setting order_id to be the newly generated id */
                        " UPDATE `check-please2`.`TMP_ORDER_ITEMS` AS `oi` SET `oi`.`order_id` = newOrderId;" +
                        " INSERT INTO `check-please2`.`Uncommitted_Orders_Items` SELECT * FROM `check-please2`.`TMP_ORDER_ITEMS`;" +
                        " DROP TABLE `check-please2`.`TMP_ORDER_ITEMS`;" +
                        " SELECT * FROM `check-please2`.`Uncommitted_Orders` AS uo WHERE `uo`.`id` = newOrderId;" +
                        " END;";

        @Override
        public void drop()
        {
            executeStatement(Drop_Place_New_Order);
        }

        @Override
        public void create()
        {                
            drop();
            executeStatement(Place_New_Order);
        }

        @Override
        public JSONObject call(DatabasePackage serverOrder)
        {
            System.out.println("INFO -- Placing a new order! -- " + serverOrder);
            
            final boolean isScheduled = false;
            
            return placeOrder(serverOrder, isScheduled);
        }
    },
    Place_Scheduled_Order()
    {
        private final static String Drop_Place_Scheduled_Order =
                "DROP PROCEDURE IF EXISTS `check-please2`.`Place_Scheduled_Order`;";

        private final static String Place_Scheduled_Order = 
                "CREATE PROCEDURE `check-please2`.`Place_Scheduled_Order`" +
                        "(" +
                        "IN in_itemIDs TEXT, " +
                        "IN in_etp BIGINT UNSIGNED, " +
                        "IN in_pickupTime DATETIME, " +
                        "IN in_price DOUBLE UNSIGNED, " +
                        "IN in_customer_id BIGINT UNSIGNED, " +
                        "IN in_store_id BIGINT UNSIGNED, " +
                        "OUT out_uncommitted_order_id BIGINT UNSIGNED" +
                        ")" +
                        " BEGIN" +
                        /* Add to Uncommitted_Orders */
                        " CALL Place_New_Order(" +
                        "in_itemIDs, " +
                        "in_etp, " +
                        "in_pickupTime, " +
                        "in_price, " +
                        "in_customer_id, " +
                        "in_store_id, " +
                        "out_uncommitted_order_id" +
                        ");" +
                        " UPDATE `check-please2`.`Uncommitted_Orders`" +
                        " SET `Uncommitted_Orders`.`scheduleDay` = DAYOFWEEK(`in_pickupTime`)" +
                        " WHERE `Uncommitted_Orders`.`id` = out_uncommitted_order_id;" +
                        " END;";

        @Override
        public void drop()
        {
            executeStatement(Drop_Place_Scheduled_Order);
        }

        @Override
        public void create()
        {                
            drop();
            executeStatement(Place_Scheduled_Order);
        }

        @Override
        public JSONObject call(DatabasePackage serverOrder)
        {
            final boolean isScheduled = true;
            
            return placeOrder(serverOrder, isScheduled);
        }
    },

    Process_Orders()
    {
        private final static String Drop_Process_Orders = 
                "DROP PROCEDURE IF EXISTS `check-please2`.`Process_Orders`;";
        
        private final static String Process_Orders = 
                
                " CREATE PROCEDURE `check-please2`.`Process_Orders`" +
                        "(OUT sleepTime BIGINT UNSIGNED)" +
                        " proc_label:BEGIN" +
                        
                        /* Cleanup step (start): Drop all temporary tables */
                        
                        " DROP TABLE IF EXISTS `check-please2`.`ORDER_TO_MOVE_OR_COPY`;" +
                        " DROP TABLE IF EXISTS `check-please2`.`ITEMS_TO_MOVE_OR_COPY`;" +
                        
                        /* Set constant step: NOW() */
                        
                        " SET @NowDateTime:=(SELECT NOW());" +
                        " SET @NowMinutes:=(SELECT MINUTE(@Now) + 60*HOUR(@Now));" +
                        " SET @OneDay:=86400000;" +
                        
                        /* Sleep for a day if there is nothing in the database */
                        
                        " SELECT COUNT(*) INTO @cnt FROM `check-please2`.`Uncommitted_Orders`;" +
                        
                        " IF @cnt = 0 THEN" +
                        " SELECT GREATEST(0, @OneDay) INTO `sleepTime` FROM `check-please2`.`Uncommitted_Orders`;" +
                        " LEAVE proc_label;" +
                        " END IF;" +
                        
                        /* Else gather the things to move */
                        
                        " CREATE TEMPORARY TABLE `check-please2`.`ORDER_TO_MOVE_OR_COPY`" +
                        " SELECT * FROM `check-please2`.`Uncommitted_Orders` AS `uo`" +
                        " WHERE `uo`.`pickupTime` - INTERVAL `uo`.`etp` MINUTE < @NowDateTime LIMIT 1;" +
                        
                        " SELECT COUNT(*) INTO @moveCnt FROM `check-please2`.`ORDER_TO_MOVE_OR_COPY`;" +
                        
                        " IF @moveCnt = 0 THEN" +
                        " SELECT LEAST(@OneDay, 1000*MIN(TIMESTAMPDIFF(SECOND, @NowDateTime, `uo`.`pickupTime`) - 60*`uo`.`etp`))" +
                        " INTO `sleepTime`" +
                        " FROM `check-please2`.`Uncommitted_Orders` AS `uo`;" +
                        " LEAVE proc_label;" +
                        " END IF;" +
                        
                        /* Save order ID of order to be moved */
                        
                        " SELECT `ORDER_TO_MOVE_OR_COPY`.`id` FROM `ORDER_TO_MOVE_OR_COPY` LIMIT 1 INTO @oldOrderID;" +
                        
                        /* Copy into the committed order table */
                        
                        " INSERT INTO `check-please2`.`Committed_Orders` (`version`, `etp`, `pickupTime`, `price`, `customer_id`, `store_id`)" +
                        " SELECT `otmoc`.`version`, `otmoc`.`etp`, `otmoc`.`pickupTime`, `otmoc`.`price`, `otmoc`.`customer_id`, `otmoc`.`store_id`" +
                        " FROM `check-please2`.`ORDER_TO_MOVE_OR_COPY` AS `otmoc`;" +
                        
                        " SET @newOrderID = LAST_INSERT_ID();" +
                        " SELECT `ORDER_TO_MOVE_OR_COPY`.`scheduleDay` FROM `ORDER_TO_MOVE_OR_COPY` LIMIT 1 INTO @scheduleDay;" +
                        
                        " CREATE TEMPORARY TABLE `check-please2`.`ITEMS_TO_MOVE_OR_COPY`" +
                        " SELECT DISTINCT uoi.*" +
                        " FROM `check-please2`.`Uncommitted_Orders_Items` AS `uoi`, `check-please2`.`ORDER_TO_MOVE_OR_COPY` AS `otm`" +
                        " WHERE `uoi`.`order_id` = `otm`.`id`;" +
                        
                        " UPDATE `check-please2`.`ITEMS_TO_MOVE_OR_COPY` SET `ITEMS_TO_MOVE_OR_COPY`.`order_id` = @newOrderID;" +
                        
                        /* Copy the items associated with the order(s) */
                        
                        " INSERT INTO `check-please2`.`Committed_Orders_Items`" +
                        " SELECT `uoi`.`item_id`, `uoi`.`order_id`, `uoi`.`items_idx`" +
                        " FROM `check-please2`.`ITEMS_TO_MOVE_OR_COPY` AS `uoi`;" +

                        " IF @scheduleDay = 0 THEN" +
                        " DELETE `uo`" +
                        " FROM `check-please2`.`Uncommitted_Orders` AS `uo`, `check-please2`.`ORDER_TO_MOVE_OR_COPY` AS `otmoc`" +
                        " WHERE `uo`.`id` = `otmoc`.`id`;" +   
                        
                        " DELETE `uoi`" +
                        " FROM `check-please2`.`Uncommitted_Orders_Items` AS `uoi`, `check-please2`.`ORDER_TO_MOVE_OR_COPY` AS `otmoc`" +
                        " WHERE `uoi`.`order_id` = `otmoc`.`id`;" +
                        
                        " ELSE" +
                        
                        " UPDATE `check-please2`.`Uncommitted_Orders` AS `uo`" +
                        " SET `uo`.`pickupTime` = `uo`.`pickupTime` + INTERVAL 1 WEEK" +
                        " WHERE `uo`.`id` = @oldOrderID;" +
                        
                        " END IF;" +
                        
                        " SET @x = -1;" +
                        " SELECT COUNT(*) INTO @x FROM `check-please2`.`Uncommitted_Orders` AS `uo`" +
                        " WHERE `uo`.`pickupTime` - INTERVAL `uo`.`etp` MINUTE < NOW() LIMIT 1;" +
                        
                        " IF @x > 0 THEN" +
                        " SET `sleepTime` = 0;" +
                        " LEAVE proc_label;" +
                        " ELSE" +
                        
                        " SELECT LEAST(@OneDay, 1000*MIN(TIMESTAMPDIFF(SECOND, @NowDateTime, `uo`.`pickupTime`) - 60*`uo`.`etp`))" +
                        " INTO `sleepTime`" +
                        " FROM `check-please2`.`Uncommitted_Orders` AS `uo`;" +
                        
                        " END IF;" +
                        
                        " END;";

        @Override
        public void drop()
        {
            executeStatement(Drop_Process_Orders);
        }

        @Override
        public void create()
        {                
            drop();
            executeStatement(Process_Orders);
        }

        @Override
        public JSONObject call(DatabasePackage nothing)
        {
            Connection conn = null;
            CallableStatement cs = null;
            long sleepTime = -1;

            try
            {
                conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;

                cs = conn.prepareCall("{CALL " + components.StoredProcedures.Process_Orders.name() + "(?)}");

                cs.registerOutParameter(1, Types.BIGINT);

                cs.execute();
                
                sleepTime = cs.getLong(1);

                JSONObject result = new JSONObject();

                result.put(Extra.NONTABLE_FIELD_OM_SLEEP_TIME_IN_MILLISECONDS, sleepTime);
                
                return result;
            }
            catch (SQLException | JSONException e)
            {
                e.printStackTrace();
                return null;
            }
            finally
            {
                DatabaseAdapter.close(conn, cs);
            }
        }
    };
    
    public static void main(String[] argv)
    {
        Process_Orders.call(null);
    }

    private static final void executeStatement(String sql)
    {
        Connection conn = null;

        try
        {
            conn = (conn == null || conn.isClosed()) ? DatabaseAdapter.newConnection() : conn;

            conn.createStatement().execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            DatabaseAdapter.close(conn);
            conn = null;
        }
    }
    
    private static final JSONObject placeOrder(DatabasePackage serverOrder, boolean isScheduled)
    {
        Connection conn = null;
        CallableStatement cs = null;

        try
        {            
            conn = (conn == null || conn.isClosed()) ? components.DatabaseAdapter.newConnection() : conn;

            cs = conn.prepareCall("{CALL " + ((isScheduled) ? StoredProcedures.Place_Scheduled_Order.name() : StoredProcedures.Place_New_Order.name()) + "(?, ?, ?, ?, ?, ?, ?)}");
            
            cs.setString(1, (String) serverOrder.myItems.get(0).value());
            cs.setLong(2, (Long) serverOrder.myItems.get(1).value());
            cs.setString(3, (String) serverOrder.myItems.get(2).value());
            cs.setDouble(4, ((BigDecimal) serverOrder.myItems.get(3).value()).doubleValue());
            cs.setLong(5, (Long) serverOrder.myItems.get(4).value());
            cs.setLong(6, (Long) serverOrder.myItems.get(5).value());
            cs.registerOutParameter(7, Types.BIGINT);

            cs.execute();
            
            serverOrder.myItems.add(new KeyValuePair(Uncommitted_Orders.id.name(), cs.getLong(7)));
            serverOrder.myItems.add(new KeyValuePair(Uncommitted_Orders.version.name(), 0L));
            
            return serverOrder.toJSON();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            DatabaseAdapter.close(conn, cs);
        }
    }

    public abstract void drop();
    public abstract void create();
    public abstract JSONObject call(DatabasePackage params);
}
