/** @author Ryan Gaffney */

package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path(value = "/examples")
public class ExampleResource
{    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public synchronized String examples()
    {
        return
                
                "\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -" +
                
                "\nC U S T O M E R" +
                
                "\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -" +
                "\n\n\t<-- Create a new customer -->\n" +
                
                "\n\t\t/POST/customer/create/in" +
                "\n\t\t/POST/customer/create/out" +
                
                "\n\n\t<-- Login -->\n" +
                
                "\n\t\t/POST/customer/login/in" +
                "\n\t\t/POST/customer/login/out" +
                
                "\n\n\t<-- Update customer -->\n" +
                
                "\n\t\t/POST/customer/update/in" +
                "\n\t\t/POST/customer/update/out" +
        
                "\n\n\t<-- Delete customer -->\n" +
        
                "\n\t\t/POST/customer/delete/in" +
                "\n\t\t/POST/customer/delete/out" +
        
                "\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -" +
        
                "\nS T O R E" +
        
                "\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -" +
        
                "\n\n\t<-- Search for stores -->\n" +
        
                "\n\t\t/POST/store/search/in" +
                "\n\t\t/POST/store/search/out" +
        
                "\n\n\t<-- Get a specific store -->\n" +
        
                "\n\t\t/GET/store/id" +
        
                "\n\n\t<-- Get the menu of a store -->\n" +
        
                "\n\t\t/GET/store/id/menu" +
        
                "\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -" +
        
                "\nO R D E R" +
        
                "\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -" +
        
                "\n\n\t<-- Calculate total ETP and cost of an order -->\n" +
                
                "\n\t\t/POST/order/calculate/in" +
                "\n\t\t/POST/order/calculate/out" +
                
                "\n\n\t<-- Submit new order -->\n" +
                
                "\n\t\t/POST/order/in" +
                "\n\t\t/POST/order/out" +
                
                "\n\n\t<-- Submit new scheduled order-->" +
                
                "\n\t\t/POST/order/day/in" +
                "\n\t\t/POST/order/day/out" +
                
                "\n\n\t<-- Update an order -->\n" +
                
                "\n\t\t/POST/order/update/in" +
                "\n\t\t/POST/order/update/out" +
                
                "\n\n\t<-- Delete an order -->\n" +
                
                "\n\t\t/POST/order/delete/in" +
                "\n\t\t/POST/order/delete/out";
    }
    
    // ===============================================================
    // DB
    // ===============================================================
    
    @GET @Path(value = "/db")
    @Produces(MediaType.TEXT_PLAIN)
    public synchronized String retreiveDatabaseInfo()
    {
        return components.DatabaseAdapter.mySQL_url;
    }
    
    @GET @Path(value = "/db/types")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject retreiveDatabaseTypes()
    {
        return components.Responder.Retreive_Database_Types.execute(null);
    }
    
    @GET @Path(value = "/db/all")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject retreiveDatabaseContents()
    {
        return components.Responder.Retreive_Database_Contents.execute(null);
    }
    
    // ===============================================================
    // CUSTOMER
    // ===============================================================
    
    @GET @Path(value = "/POST/customer/create/in")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject createAccount_Input()
    {
        return components.Responder.Create_Account.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/customer/create/out")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject createAccount_Output()
    {
        return components.Responder.Create_Account.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/customer/login/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject login_Input()
    {
        return components.Responder.Login.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/customer/login/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject login_Output()
    {
        return components.Responder.Login.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/customer/update/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject updateAccount_Input()
    {
        return components.Responder.Update_Account.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/customer/update/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject updateAccount_Output()
    {
        return components.Responder.Update_Account.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/customer/delete/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject deleteAccount_Input()
    {
        return components.Responder.Delete_Account.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/customer/delete/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject deleteAccount_Output()
    {
        return components.Responder.Delete_Account.sampleResponseOutput();
    } 
    
    // ===============================================================
    // STORE
    // ===============================================================
    
    @GET @Path("/POST/store/search/in")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject searchForStores_Input()
    {
        return components.Responder.Search_For_Stores.sampleRequestInput();
    }
    
    @GET @Path("/POST/store/search/out")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject searchForStores_Output()
    {
        return components.Responder.Search_For_Stores.sampleResponseOutput();
    }

    @GET @Path(value = "/GET/store/id")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getStore_Output()
    {
        return components.Responder.Get_Store.sampleResponseOutput();
    }
    
    @GET @Path("/GET/store/id/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getStoreMenu_Output()
    {
        return components.Responder.Get_Store_Menu.sampleResponseOutput();
    }
    
    // ===============================================================
    // ORDER
    // ===============================================================
    
    @GET @Path(value = "/POST/order/calculate/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject calculateOrderTotals_Input()
    {
        return components.Responder.Calculate_Order_Totals.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/calculate/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject calculateOrderTotals_Output()
    {
        return components.Responder.Calculate_Order_Totals.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/order/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject submitNonscheduledOrder_Input()
    {
        return components.Responder.Submit_Nonscheduled_Order.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject submitNonscheduledOrder_Output()
    {
        return components.Responder.Submit_Nonscheduled_Order.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/order/scheduled/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject submitScheduledOrder_Input()
    {
        return components.Responder.Submit_Scheduled_Order.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/scheduled/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject submitScheduledOrder_Output()
    {
        return components.Responder.Submit_Scheduled_Order.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/order/update/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject updateOrder_Input()
    {
        return components.Responder.Update_Order.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/update/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject updateOrder_Output()
    {
        return components.Responder.Update_Order.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/order/delete/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject deleteOrderExampleInput()
    {
        return components.Responder.Delete_Order.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/delete/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject deleteOrderExampleOutput()
    {
        return components.Responder.Delete_Order.sampleResponseOutput();
    }
}
