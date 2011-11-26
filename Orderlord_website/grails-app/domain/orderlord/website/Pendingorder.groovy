package orderlord.website

import java.util.Date;
import java.util.List;

class Pendingorder {

	long orderEtp
	int scheduleDay
	Date pickupTime
	double totalCost
	String orderType
	//int storeId
	//int customerId
//	long scheduledorderId //can be 0 for one-time order
	
	static transients = ['orderType']
	
	static belongsTo = [store:Store, customer:Customer]
	List items
	static hasMany = [items:Item]
	  	
	static mapping = {
		sort pickupTime:'asc'
		
		table 'Uncommitted_Orders'
		orderEtp column: 'etp'
		scheduleDay column: 'scheduleDay'
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
		
		return
	}
	
	Date minDate(){
		Date currentTime = new Date()
		Date minDate = new Date(currentTime.getYear(),
						currentTime.getMonth(),
						currentTime.getDate(),
						currentTime.getHours(),
						currentTime.getMinutes()+orderEtp.toInteger(),
						currentTime.getSeconds(),
						)
//		println "aaaaa"
		return minDate
	}
	
	Date maxDate(){
		Date maxDate = new Date()+7
		return maxDate
	}
		
    static constraints = {
		scheduleDay(inList:0..7)
		pickupTime()
//		pickupTime(validator: { val -> val?.after(it.minDate()) })
//		pickupTime(min: it.minDate())
		orderEtp(min:0L)
		totalCost(min:0D)
		store()
		customer()
		orderType(inList:["One-Time-Order", "Scheduled-Order"])
    }
	
	String figureOutOrderType(){
		if (scheduleDay == 0)
			return "One-Time-Order"
		else 
			return "Scheduled-Order"
	}
	
	void figureOutScheduleDay(){
		if (orderType == "Scheduled-Order")
			scheduleDay = pickupTime.getDay()+1
		else if (orderType == "One-Time-Order")
			scheduleDay = 0
	}
	
	String toDisplayDay(){
		if (scheduleDay == 0)
			return "${toStringDay()}"
		else
			return "every ${toStringDay()}"
	}
	
	String toDisplayTime(){
		if (scheduleDay == 0)
			return "${pickupTime.format("yyyy-MM-dd 'at' HH:mm:ss")}"
		else
			return "${pickupTime.format("HH:mm:ss")}"
	}
	
	String toStringDay(){
		switch (scheduleDay){
			case 0:
				return "N/A"
			case 2:
				return "Monday"
			case 3:
				return "Tuesday"
			case 4:
				return "Wednesday"
			case 5:
				return "Thursday"
			case 6:
				return "Friday"
			case 7:
				return "Saturday"
			case 1:
				return "Sunday"
		}	
	}
	
	String toString(){
		if (scheduleDay == 0)
			return "PendingOrder due ${toDisplayTime()} -- TotalCost: \$${totalCost} -- OrderETP: ${orderEtp} min"
		else
			return "ScheduledOrder due ${toDisplayDay()} at ${toDisplayTime()} -- TotalCost: \$${totalCost} -- OrderETP: ${orderEtp} min"
	}
	
} //end of class
