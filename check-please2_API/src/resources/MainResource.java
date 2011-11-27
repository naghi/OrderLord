/** @author Ryan Gaffney */

package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import com.sun.jersey.spi.resource.Singleton;
import components.Manager;
import components.Responder;
import components.Table.*;

@Singleton
@Path(value = "/call")
public class MainResource
{    
    // ===============================================================
    // CUSTOMER
    // ===============================================================
    
    @POST @Path("/customer/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject createAccount(JSONObject o)
    {
        System.out.println("INFO -- Attempting to create a customer using parameters: " + o);
        
        return Responder.Create_Account.execute(o);
    }
    
    @POST @Path("/customer/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject login(JSONObject o)
    {   
        System.out.println("INFO -- Attempting to login using parameters: " + o);
        
        return Responder.Login.execute(o);
    }
    
    @GET @Path(value = "/customer/update")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject updateAccount(JSONObject o)
    {
        System.out.println("INFO -- Attempting to update a customer using parameters: " + o);
        
        return components.Responder.Update_Account.execute(o);
    }
    
    @POST @Path("/customer/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject deleteAccount(JSONObject o)
    {
        System.out.println("INFO -- Attempting to delete a customer using parameters: " + o);
        
        return Responder.Delete_Account.execute(o);
    }
    
    // ===============================================================
    // STORE
    // ===============================================================
    
    @POST @Path("/store/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject searchForStores(JSONObject o)
    {
        System.out.println("INFO -- Attempting to retreive stores using parameters: " + o);
        
        return Responder.Search_For_Stores.execute(o);
    }
    
    @GET @Path("/store/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getStore(@PathParam("id") long store_id) throws JSONException
    {
        System.out.println("INFO -- Attempting to retreive store with ID " + store_id);

        return Responder.Get_Store.execute(new JSONObject().putOpt(Stores.id.name(), store_id));
    }
    
    @GET @Path("/store/{id}/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getStoreMenu(@PathParam("id") long store_id) throws JSONException
    {
        System.out.println("INFO -- Attempting to retreive menu of store with ID " + store_id);
        
        return Responder.Get_Store_Menu.execute(new JSONObject().putOpt(Stores.id.name(), store_id));
    }
    
    // ===============================================================
    // ORDER
    // ===============================================================

    @POST @Path("/order/calculate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject calculateOrderTotals(JSONObject o)
    {
        System.out.println("INFO -- Attempting to calculate the totals of an order using parameters: " + o);
        
        return Responder.Calculate_Order_Totals.execute(o);
    }
    
    @POST @Path("/order")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject addNonscheduledOrder(JSONObject clientOrder) throws JSONException
    {
        System.out.println("INFO -- Attempting to add order: " + clientOrder);
        
        return theMan.handleIncomingNonscheduledOrder(clientOrder);
    }
    
    @POST @Path("/order/scheduled")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject addScheduledOrder(JSONObject o) throws JSONException
    {
        System.out.println("INFO -- Attempting to add a scheduled order using parameters: " + o);
        
        return theMan.handleIncomingScheduledOrder(o);
    }
    
    @POST @Path("/order/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject updateOrder(JSONObject o)
    {
        System.out.println("INFO -- Attempting to update an order using parameters: " + o);
        
        return theMan.handleOrderUpdate(o);
    }
    
    @POST @Path("/order/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject deleteOrder(JSONObject o)
    {
        System.out.println("INFO -- Attempting to delete an order using parameters: " + o);
        
        return Responder.Delete_Order.execute(o);
    }
    
    // ===============================================================
    // ORDER PROCESSOR
    // ===============================================================
    
    private static Manager theMan = new Manager();
    
    static
    {
        theMan.makeThingsHappen();
    }
    
    @POST @Path("/start")
    public synchronized Response startOrderProcessor()
    {
        if (theMan.justChillin())
        {
            System.out.println("INFO -- Starting things up");
            
            theMan.makeThingsHappen();
            
            return Response.ok().build();
        }
        else return Response.notModified().build();
    }
    
    @POST @Path("/stop")
    public synchronized Response stopOrderProcessor()
    {
        System.out.println("INFO -- Shutting down the processor");
        
        theMan.partysOver();
        
        return Response.ok().build();
    }
    
    @GET @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public synchronized String getOrderProcessorStatus()
    {
        System.out.print(".");
        
        return theMan.tellItLikeItIs();
    }
    
    @GET @Path("/website")
    public synchronized Response orderPing()
    {
        System.out.println("INFO -- Pinged!");
        
        theMan.handlePing();
        
        return Response.ok().build();
    }
}