/** @author Ryan Gaffney */

package components;

public final class Tables
{    
    public final static Class<?>[] list = {
        Committed_Orders.class,
        Committed_Orders_Items.class,
        Customers.class,
        Items.class,
        Stores.class,
        Uncommitted_Orders.class,
        Uncommitted_Orders_Items.class
    };

    public enum Committed_Orders
    {
        id,
        version,
        etp,
        pickupTime,
        price,
        customer_id,
        store_id;
        
        public static String getName() { return "Committed_Orders"; }
    }
    
    public enum Committed_Orders_Items
    {
        item_id,
        order_id,
        items_idx;
        
        public static String getName() { return "Committed_Orders_Items"; }
    };
    
    public static enum Customers
    {
        id,
        version,
        email,
        username,
        password,
        firstName,
        lastName,
        balance;
        
        public static String getName() { return "Customers"; }
    };
    
    public static enum Items
    {
        id,
        version,
        etp,
        name,
        description,
        pictureLink,
        price,
        store_id;
        
        public static String getName() { return "Items"; }
    };
    
    public static enum Stores
    {
        id,
        version,
        username,
        password,
        role,
        name,
        address,
        latitude,
        longitude,
        phoneNumber,
        pictureLink;
        
        public static String getName() { return "Stores"; }
    };
    
    public static enum Uncommitted_Orders
    {
        id,
        version,
        etp,
        scheduleDay,
        pickupTime,
        price,
        customer_id,
        store_id;
        
        public static String getName() { return "Uncommitted_Orders"; }
    };
    
    public static enum Uncommitted_Orders_Items
    {
        Uncommitted_Orders_Items,
        item_id,
        order_id,
        items_idx;
        
        public static String getName() { return "Uncommitted_Orders_Items"; }
    };
}
