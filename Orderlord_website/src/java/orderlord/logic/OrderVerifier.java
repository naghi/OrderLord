package orderlord.logic;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.math.*;


public class OrderVerifier {

	public static void main(String[] args) {
		System.out.print("into main...\n");
		
		
		verifyTotalCosts();
		verifyTotalETP();
		
		
		System.out.print("\nend of main");
	}

	public static void verifyTotalCosts() {
		Connection connection = null;
		Statement statement1 = null;
		Statement statement2 = null;
		Statement statement3 = null;
		ResultSet result_order = null;
		ResultSet result_order_item = null;
		ResultSet result_item = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://67.188.195.86:3306/test", "check-please2", "!checkPlease!@");
			
		// start
		
			statement1 = connection.createStatement();
			result_order = statement1.executeQuery("SELECT id,total_cost FROM `test`.`activeorder`;");
		
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
				result_order_item = statement2.executeQuery("SELECT item_id FROM `test`.`activeorder_item` " +
						"where activeorder_items_id="+order_id+";");
				
				while (result_order_item.next()){
					item_id = result_order_item.getInt(1);
					statement3 = connection.createStatement();
					result_item = statement3.executeQuery("SELECT price FROM `test`.`item` where id="+item_id+";");
					result_item.next();
					calculatedTotalCostSoFar += result_item.getDouble(1);
				}
//				
				calculatedTotalCost = (new BigDecimal(calculatedTotalCostSoFar)).setScale(3,BigDecimal.ROUND_HALF_EVEN).doubleValue();
				if (calculatedTotalCost != givenTotalCost)
					System.out.print("\nOrder "+order_id+" has failed cost assertion!!!\n");
				else
					System.out.print("\nOrder "+order_id+" total cost asserted!\n");
			}
			
			//assert();
			
			
	//end
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
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
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}//end of verifyTotalCosts

	public static void verifyTotalETP(){
		Connection connection = null;
		Statement statement1 = null;
		Statement statement2 = null;
		Statement statement3 = null;
		ResultSet result_order = null;
		ResultSet result_order_item = null;
		ResultSet result_item = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://67.188.195.86:3306/test", "check-please2", "!checkPlease!@");
			
		// start
		
			statement1 = connection.createStatement();
			result_order = statement1.executeQuery("SELECT id,order_etp FROM `test`.`activeorder`;");
		
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
				result_order_item = statement2.executeQuery("SELECT item_id FROM `test`.`activeorder_item` " +
						"where activeorder_items_id="+order_id+";");
				
				while (result_order_item.next()){
					item_id = result_order_item.getInt(1);
					statement3 = connection.createStatement();
					result_item = statement3.executeQuery("SELECT item_etp FROM `test`.`item` where id="+item_id+";");
					result_item.next();
					calculatedTotalETPSoFar = Math.max(calculatedTotalETPSoFar, result_item.getLong(1));
				}
//				
				
				calculatedTotalETP = calculatedTotalETPSoFar;
				if (calculatedTotalETP != givenTotalETP)
					System.out.print("\nOrder "+order_id+" has failed ETP assertion!!!\n");
				else
					System.out.print("\nOrder "+order_id+" ETP asserted!\n");
			}
			
			//assert();
			
			
	//end
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
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
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}//end of verifyTotalETP

	
}
