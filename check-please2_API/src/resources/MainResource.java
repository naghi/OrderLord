/** @author Ryan Gaffney */

package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import components.Tables.*;

@Singleton
@Path(value = "/call")
public class MainResource
{
    private Manager theMan = new Manager();
    
    @GET @Path("/start")
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
    
    @GET @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public synchronized String getOrderProcessorStatus()
    {
        System.out.println("INFO -- Retreiving the status of the order processor...");
        
        return theMan.tellItLikeItIs();
    }
    
    @GET @Path("/website")
    public synchronized Response websitePing()
    {
        System.out.println("INFO -- Got pinged!");
        
        theMan.handleWebsitePing();
        
        return Response.ok().build();
    }

    @PUT @Path("/customer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject addCustomer(JSONObject o)
    {
        System.out.println("INFO -- Attempting to add customer " + o);
        
        return Responder.Add_New_Customer.execute(o);
    }
    
    @DELETE @Path("/customer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject removeCustomer(JSONObject o)
    {
        System.out.println("INFO -- Attempting to remove customer " + o);
        
        return Responder.Remove_Customer.execute(o);
    }
    
    @POST @Path("/customer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getCustomer(JSONObject o)
    {   
        System.out.println("INFO -- Attempting to retrieve customer " + o);
        
        return Responder.Select_Customer.execute(o);
    }
    
    @POST @Path("/customer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject updateCustomerSamePassword(@PathParam("id") long customer_id, JSONObject o)
    {
        System.out.println("INFO -- Attempting to update customer " + o);
        
        return Responder.Update_Customer.execute(o);
    }
    
    @GET @Path("/store/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getStore(@PathParam("id") long store_id) throws JSONException
    {
        System.out.println("INFO -- Attempting to retreive store with ID " + store_id);

        return Responder.Get_Store_By_ID.execute(new JSONObject().putOpt(Stores.id.name(), store_id));
    }
    
    @GET @Path("/store/{id}/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getMenu(@PathParam("id") long store_id) throws JSONException
    {
        System.out.println("INFO -- Attempting to retreive menu of store with ID " + store_id);
        
        return Responder.Get_Menu_From_Store.execute(new JSONObject().putOpt(Stores.id.name(), store_id));
    }
    
    @POST @Path("/store/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject searchForStoresAroundMe(JSONObject searchParams)
    {
        System.out.println("INFO -- Attempting to retreive stores using " + searchParams);

        return Responder.Find_Stores_Within_Radius.execute(searchParams);
    }

    @POST @Path("/order")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject addOrder(JSONObject clientOrder)
    {
        System.out.println("INFO -- Attempting to add order: " + clientOrder);
        
        return theMan.handleIncomingOrder(clientOrder);
    }
    
    @POST @Path("/order/etp")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject getTotalETP(JSONObject clientOrder)
    {
        System.out.println("INFO -- Attempting to retreive the total ETP of order: " + clientOrder);
        
        return Responder.Get_Total_ETP_And_Price.execute(clientOrder);
    }
    
    @POST @Path("/order/scheduled/{day}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized JSONObject addScheduledOrder(@PathParam("day") int day_of_week, JSONObject clientOrder)
    {
        System.out.println("INFO -- Attempting to add scheduled order: " + clientOrder);
        
        if (day_of_week < 1 || day_of_week > 7)
            return null;
        
        return theMan.handleIncomingScheduledOrder(clientOrder);
    }
}