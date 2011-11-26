/** @author Ryan Gaffney */

package components;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import components.Tables.Items;
import components.Tables.Stores;
import components.Tables.Uncommitted_Orders;

public class DatabasePackage
{   
    protected LinkedList<Pair<String, Object>> myItems = null;
    
    public DatabasePackage createServerOrderPackage(String items, Long etp, String pickupTime, BigDecimal price, Long customer_id, Long store_id)
    {
        if (items == null || etp == null || pickupTime == null ||
                price == null || customer_id == null || store_id == null)
        {
            myItems = null;
            return null;
        }
        
        myItems = new LinkedList<Pair<String, Object>>();
        
        myItems.add(new Pair<String, Object>(Items.getName(), items));
        myItems.add(new Pair<String, Object>(Uncommitted_Orders.etp.name(), etp));
        myItems.add(new Pair<String, Object>(Uncommitted_Orders.pickupTime.name(), pickupTime));
        myItems.add(new Pair<String, Object>(Uncommitted_Orders.price.name(), price));
        myItems.add(new Pair<String, Object>(Uncommitted_Orders.customer_id.name(), customer_id));
        myItems.add(new Pair<String, Object>(Uncommitted_Orders.store_id.name(), store_id));
        
        return this;
    }
    
    public DatabasePackage createSearchPackage(Double latitude, Double longitude, Double radius_in_miles)
    {
        if (latitude == null || longitude == null || radius_in_miles == null)
        {
            myItems = null;
            return null;
        }
        
        myItems = new LinkedList<Pair<String, Object>>();
        
        myItems.add(new Pair<String, Object>(Stores.latitude.name(), latitude));
        myItems.add(new Pair<String, Object>(Stores.longitude.name(), latitude));
        myItems.add(new Pair<String, Object>(Extra.NONTABLE_FIELD_RADIUS_IN_MILES, radius_in_miles));
        
        return this;
    }

    public JSONObject toJSON()
    {
        if (myItems == null)
        {
            return null;
        }

        JSONObject result = new JSONObject();

        try
        {
            for (Pair<String, Object> p : myItems)
                result.put(p.left(), p.right());

            return result;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String toString()
    {
        if (this.toJSON() != null)
            return this.toJSON().toString();
        
        else return null;
    }
}
