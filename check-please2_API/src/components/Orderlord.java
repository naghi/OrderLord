/** @author Ryan Gaffney */

package components;

public class Orderlord extends Thread
{
    private static long tapCount = 1;
    private static java.util.Date timeLastAsleep;
    private static long sleepTime;
    
    public void run()
    {
        sleepTime = 0;
        timeLastAsleep = new java.util.Date();
        
        System.out.println("INFO -- Order processor starting (" + tapCount + ")");

        while (!Thread.currentThread().isInterrupted())
        {   
            try
            {
                System.out.println("INFO -- Sleeping for " + sleepTime + " milliseconds");
                
                timeLastAsleep.setTime(new java.util.Date().getTime());
                
                Thread.sleep(sleepTime);
                
                tapCount = 1;

                while (tapCount > 0)
                {
                    System.out.println("INFO -- Beginning to process orders...");
                    
                    sleepTime = StoredProcedures.Process_Orders.call(null).getLong(Extra.NONTABLE_FIELD_OM_SLEEP_TIME_IN_MILLISECONDS);
                    
                    System.out.println("INFO -- Received sleep time of " + sleepTime + " from Order_Processor");
                    
                    --tapCount;
                }
            }
            catch (InterruptedException e)
            {
                System.out.println("INFO -- Waking up!");
                sleepTime = 10;
            }
            catch (Exception e)
            {
                System.err.println("ERROR -- Processor caught an exception!  Quitting...");
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void tapDat() { ++tapCount; }
    public java.util.Date timeLastAsleep() { return timeLastAsleep; }
    public java.util.Date nextWakeTime() { return (timeLastAsleep == null) ? new java.util.Date(Long.MAX_VALUE) : new java.util.Date(timeLastAsleep.getTime() + sleepTime); }
}