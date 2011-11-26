package orderlord.website

import java.util.Date;

class Activeorder {

	long orderEtp
	Date pickupTime
	double totalCost
	//int storeId
	//int customerId
	
	static belongsTo = [store:Store, customer:Customer]
	List items
	static hasMany = [items:Item]
	
	static mapping = {
		sort pickupTime:'asc'
		
		table 'Committed_Orders'
		orderEtp column: 'etp'
		pickupTime column: 'pickupTime'
		totalCost column: 'price'
		store column: 'store_id'
		customer column: 'customer_id'
		
		//join table
		items column: 'order_id'
	}
	
	void figureOutPickupTime(){
		Date currentTime = new Date()
		
		if (orderEtp == 0)
			pickupTime = currentTime + 6
		else{
			pickupTime = new Date(currentTime.getYear(),
						currentTime.getMonth(),
						currentTime.getDate(),
						currentTime.getHours(),
						currentTime.getMinutes() + orderEtp.toInteger(),
						)
		}
		return
	}
	
//	Date minDate(){
//		Date currentTime = new Date()
//		Date minDate = new Date(currentTime.getYear(),
//						currentTime.getMonth(),
//						currentTime.getDate(),
//						currentTime.getHours(),
//						currentTime.getMinutes()-1,
//						currentTime.getSeconds()
//						)
//		return minDate
//	}
//	
//	Date maxDate(){
//		Date currentTime = new Date()
//		Date maxDate = new Date(currentTime.getYear(),
//						currentTime.getMonth(),
//						currentTime.getDate(),
//						currentTime.getHours(),
//						currentTime.getMinutes()+orderEtp.toInteger(),
//						currentTime.getSeconds()
//						)
//		return maxDate
//	}
	
    static constraints = {
		pickupTime()
		orderEtp(min:0L)
		totalCost(min:0D)
		store()
		customer()
    }
	
	String toDisplayDateTime(){
		return "${pickupTime.format("yyyy-MM-dd 'at' HH:mm:ss")}"
	}
	
	String toString(){
		"ActiveOrder due ${toDisplayDateTime()} -- TotalCost: \$${totalCost} -- OrderETP: ${orderEtp} min"
	}
}
