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
    @GET @Path(value = "/hi")
    public String hello()
    {         
        return "Hey there, big boy.";
    }
    
    @GET @Path(value = "/db")
    @Produces(MediaType.TEXT_PLAIN)
    public synchronized String getDBName()
    {
        return components.DatabaseAdapter.mySQL_url;
    }
    
    @GET @Path(value = "/db/types")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getDatabase()
    {
        return components.Responder.Get_Database_Types.execute(null);
    }
    
    @GET @Path(value = "/db/all")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getEverythingInDatabase()
    {
        return components.Responder.Get_Everything_In_Database.execute(null);
    }
    
    @GET @Path("/customer")
    @Produces(MediaType.TEXT_PLAIN)
    public synchronized String getCustomerUsageExample()
    {
        return
                "/PUT/customer/in :: ../call/customer [@PUT -- Request]\t(Create)\n" +
                "/PUT/customer/out :: ../call/customer [@PUT -- Response]\t(Create)\n" +
                "/POST/customer/in :: ../call/customer [@POST -- Request]\t(Login)\n" +
                "/POST/customer/out :: ../call/customer [@POST -- Response]\t(Login)\n" +
                "/POST/customer/id/in :: ../call/customer/{customer_id} [@POST -- Request]\t(Update customer (e.g. username, password, etc.))\n" +
                "/POST/customer/id/out :: ../call/customer/{customer_id} [@POST -- Response]\t(Update customer (e.g. username, password, etc.))\n";
    }
    
    @GET @Path(value = "/PUT/customer/in")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject putCustomerInput()
    {
        return components.Responder.Add_New_Customer.sampleRequestInput();
    }
    
    @GET @Path(value = "/PUT/customer/out")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject putCustomerOutput()
    {
        return components.Responder.Add_New_Customer.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/customer/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getCustomerInputExample()
    {
        return components.Responder.Select_Customer.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/customer/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getCustomerOutputExample()
    {
        return components.Responder.Select_Customer.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/customer/id/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject updateCustomerExampleInput()
    {
        return components.Responder.Update_Customer.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/customer/id/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject updateCustomerExampleOutput()
    {
        return components.Responder.Update_Customer.sampleResponseOutput();
    }
    
    @GET @Path(value = "/store")
    @Produces({MediaType.TEXT_PLAIN})
    public synchronized String getStoreUsageExample()
    {
        return 
                "/GET/store :: ../call/store/{id} [@GET]\t (Store info)\n" +
                "/GET/store/id/menu :: ../call/store/{id}/menu [@GET] \t(Menu)\n" +
                "/POST/store/search/in :: ../call/store/{id}/menu [@POST -- Request]\t\n" +
                "/POST/store/search/out :: ../call/store/{id}/menu [@POST -- Response] \t(Menu)\n";
    }

    @GET @Path(value = "/GET/store")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getStoreExample()
    {
        return components.Responder.Get_Store_By_ID.sampleResponseOutput();
    }
    
    @GET @Path("/GET/store/id/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getStoreMenuExample()
    {
        return components.Responder.Get_Menu_From_Store.sampleResponseOutput();
    }
    
    @GET @Path("/POST/store/search/in")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getStoresInputExample()
    {
        return components.Responder.Find_Stores_Within_Radius.sampleRequestInput();
    }
    
    @GET @Path("/POST/store/search/out")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getStoresOutputExample()
    {
        return components.Responder.Find_Stores_Within_Radius.sampleResponseOutput();
    }
    
    @GET @Path("/order")
    @Produces(MediaType.TEXT_PLAIN)
    public synchronized String orderUsageExample()
    {
        return 
                "/POST/order/in :: ../call/order [@POST -- Request]\t (Add new order)\n" +
                "/POST/order/out :: ../call/order [@POST -- Response]\t(Add new order)\n" +
                "/POST/order/day/in :: ../call/order/{day} [@POST -- Request]\t(Add new scheduled order)\n" +
                "/POST/order/day/out :: ../call/order/{day} [@POST -- Response]\t(Add new scheduled order)\n" +
                "/POST/order/etp/in :: ../call/order/etp [@POST -- Request]\t(Get ETP and total cost for order)\n" +
                "/POST/order/etp/out :: ../call/order/etp [@POST -- Response]\t(Get ETP and total cost for order)\n" +
                "/POST/order/id/in :: ../call/order/{order_id} [@POST -- Request]\t(Update uncommitted order)\n" +
                "/POST/order/id/out :: ../call/order/{order_id} [@POST -- Response]\t(Update uncommitted order)\n" +
                "/DELETE/order/id/in :: ../call/order/{order_id} [@DELETE -- Request]\t(Delete uncommitted order)\n" +
                "/DELETE/order/id/out :: ../call/order/{order_id} [@DELETE -- Request]\t(Delete uncommitted order)\n";
    }
    
    @GET @Path(value = "/POST/order/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getOrderExampleInput()
    {
        return components.Responder.Submit_New_Order.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getOrderExampleOutput()
    {
        return components.Responder.Submit_New_Order.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/order/day/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getScheduledOrderExampleInput()
    {
        return components.Responder.Submit_Scheduled_Order.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/day/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getScheduledOrderExampleOutput()
    {
        return components.Responder.Submit_Scheduled_Order.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/order/etp/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getCalculatedETPAndCostExampleInput()
    {
        return components.Responder.Get_Total_ETP_And_Price.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/etp/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject getCalculatedETPAndCostExampleOutput()
    {
        return components.Responder.Get_Total_ETP_And_Price.sampleResponseOutput();
    }
    
    @GET @Path(value = "/POST/order/id/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject updateOrderExampleInput()
    {
        return components.Responder.Update_Order.sampleRequestInput();
    }
    
    @GET @Path(value = "/POST/order/id/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject updateOrderExampleOutput()
    {
        return components.Responder.Update_Order.sampleResponseOutput();
    }
    
    @GET @Path(value = "/DELETE/order/id/in")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject deleteOrderExampleInput()
    {
        return components.Responder.Delete_Order.sampleRequestInput();
    }
    
    @GET @Path(value = "/DELETE/order/id/out")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized JSONObject deleteOrderExampleOutput()
    {
        return components.Responder.Delete_Order.sampleResponseOutput();
    }
}
