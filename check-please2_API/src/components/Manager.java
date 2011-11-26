/** @author Ryan Gaffney */

package components;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import components.Tables.Uncommitted_Orders;

public class Manager
{
    private static boolean started = false;
    private Orderlord processor = null;
    
    public boolean justChillin() { return !started; }
    
    public void makeThingsHappen()
    {
        if (started)
            return;
        
        started = true;
        
        // TODO: for Table t...
        
        for (StoredProcedures s : StoredProcedures.values())
        {
            s.create();
        }
        
        processor = new Orderlord();
        processor.setName("OL");
        processor.start();
    }
    
    public String tellItLikeItIs()
    {
        if (processor == null || !processor.isAlive())
            return "$$ Processor is INACTIVE $$";
        
        else return "$$ Processor is ACTIVE $$\n"
                + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n"
                + ">> Status: " + processor.getState() + "\n"
                + ">> Last sleep: " + processor.timeLastAsleep() + "\n"
                + ">> Next awakening: " + processor.nextWakeTime() + "\n"
                + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n";
    }
    
    public void handlePing()
    {
        if (started)
        {
            processor.tapDat();
            
            if (processor.getState() == Thread.State.TIMED_WAITING)
                processor.interrupt();
        }
    }
    
    public JSONObject handleOrderUpdate(JSONObject clientOrder)
    {
        JSONObject result = Responder.Update_Order.execute(clientOrder);
        
        processor.tapDat();
        
        return result;
    }

    public JSONObject handleIncomingNonscheduledOrder(JSONObject clientOrder) throws JSONException
    {        
        @SuppressWarnings("unused")
        boolean isScheduled;
        
        if (clientOrder.getInt(Uncommitted_Orders.scheduleDay.name()) == 0)
            return null;
        
        return orderHandler(clientOrder, isScheduled = false);
    }

    public JSONObject handleIncomingScheduledOrder(JSONObject clientOrder) throws JSONException
    {        
        @SuppressWarnings("unused")
        boolean isScheduled;
        
        if (clientOrder.getInt(Uncommitted_Orders.scheduleDay.name()) != 0)
            return null;
        
        else return orderHandler(clientOrder, isScheduled = true);
    }
    
    private JSONObject orderHandler(JSONObject clientOrder, boolean isScheduled)
    {
        JSONObject order;
        
        if (started && (order = ((isScheduled) ? Responder.Submit_Scheduled_Order.execute(clientOrder) : Responder.Submit_Nonscheduled_Order.execute(clientOrder))) != null)
        {
            processor.tapDat();
            
            if (processor.getState() == Thread.State.TIMED_WAITING)
                processor.interrupt();
            
            return order;
        }
        return null;
    }
}
