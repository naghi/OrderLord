package orderlord.logic;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

//import java.math.*;


public class OrderVerifierTestCase {

	static Connection connection = null;
	static Statement statement1 = null;
	static Statement statement2 = null;
	static Statement statement3 = null;
	static ResultSet result_order = null;
	static ResultSet result_order_item = null;
	static ResultSet result_item = null;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://67.188.195.86:3306/test", "check-please2", "!checkPlease!@");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void setUp()  {
	}

	@After
	public void tearDown()  {
		try {
			if (result_order != null)
				result_order.close();
			if (result_order_item != null)
				result_order_item.close();
			if (result_item != null)
				result_item.close();
			if (statement1 != null)
				statement1.close();
			if (statement2 != null)
				statement2.close();
			if (statement3 != null)
				statement3.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testActiveOrderCosts(){
		verifyOrderCosts("activeorder");
	}
	
	@Test
	public void testPendingOrderCosts(){
		verifyOrderCosts("pendingorder");
	}
	
	@Test
	public void testScheduledOrderCosts(){
		verifyOrderCosts("scheduledorder");
	}
	///////////////////////////////////////////
	@Test
	public void testActiveOrderETPs(){
		verifyOrderETPs("activeorder");
	}

	@Test
	public void testPendingOrderETPs(){
		verifyOrderETPs("pendingorder");
	}

	@Test
	public void testScheduledOrderETPs(){
		verifyOrderETPs("scheduledorder");
	}

	
	
	public void verifyOrderCosts(String orderType){
		
		try {
			statement1 = connection.createStatement();
			result_order = statement1.executeQuery("SELECT id,total_cost FROM `test`.`"+orderType+"`;");

			int order_id, item_id;
			double givenTotalCost, calculatedTotalCost, calculatedTotalCostSoFar;

			while (result_order.next()){
				givenTotalCost = 0;
				calculatedTotalCost = 0;
				calculatedTotalCostSoFar = 0;
//				System.out.print(result.getDouble(1));
				order_id = result_order.getInt(1);
				givenTotalCost = result_order.getDouble(2);

				statement2 = connection.createStatement();
				result_order_item = statement2.executeQuery("SELECT item_id FROM `test`.`"+orderType+"_item` " +
						"where "+orderType+"_items_id="+order_id+";");

				while (result_order_item.next()){
					item_id = result_order_item.getInt(1);
					statement3 = connection.createStatement();
					result_item = statement3.executeQuery("SELECT price FROM `test`.`item` where id="+item_id+";");
					result_item.next();
					calculatedTotalCostSoFar += result_item.getDouble(1);
				}
				//			

				calculatedTotalCost = calculatedTotalCostSoFar;
//				calculatedTotalCost = (new BigDecimal(calculatedTotalCostSoFar)).setScale(2,BigDecimal.ROUND_HALF_EVEN).doubleValue();
//				if (calculatedTotalCost != givenTotalCost)
//					System.out.print("\nOrder "+order_id+" has failed cost assertion!!!\n");
//				else
//					System.out.print("\nOrder "+order_id+" total cost asserted!\n");
				assertEquals("Asserting cost for "+orderType+" "+order_id+"", givenTotalCost, calculatedTotalCost, 0.0003);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end of verifyOrderCosts
	
	public void verifyOrderETPs(String orderType){
		
		try {
			statement1 = connection.createStatement();
			result_order = statement1.executeQuery("SELECT id,order_etp FROM `test`.`"+orderType+"`;");
		
			int order_id, item_id;
			long givenTotalETP, calculatedTotalETP, calculatedTotalETPSoFar;
			
			while (result_order.next()){
				givenTotalETP = 0;
				calculatedTotalETP = 0;
				calculatedTotalETPSoFar = 0;
//				System.out.print(result.getDouble(1));
				order_id = result_order.getInt(1);
				givenTotalETP = result_order.getLong(2);
				
				statement2 = connection.createStatement();
				result_order_item = statement2.executeQuery("SELECT item_id FROM `test`.`"+orderType+"_item` " +
						"where "+orderType+"_items_id="+order_id+";");
				
				while (result_order_item.next()){
					item_id = result_order_item.getInt(1);
					statement3 = connection.createStatement();
					result_item = statement3.executeQuery("SELECT item_etp FROM `test`.`item` where id="+item_id+";");
					result_item.next();
					calculatedTotalETPSoFar = Math.max(calculatedTotalETPSoFar, result_item.getLong(1));
				}
//				
				
				calculatedTotalETP = calculatedTotalETPSoFar;
//				if (calculatedTotalETP != givenTotalETP)
//					System.out.print("\nOrder "+order_id+" has failed ETP assertion!!!\n");
//				else
//					System.out.print("\nOrder "+order_id+" ETP asserted!\n");
				assertEquals("Asserting ETP for "+orderType+" "+order_id+"", givenTotalETP, calculatedTotalETP);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}//end of verifyOrderETPs

	
}
