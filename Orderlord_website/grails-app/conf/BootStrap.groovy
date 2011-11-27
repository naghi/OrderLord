import grails.util.GrailsUtil
import orderlord.website.Activeorder
import orderlord.website.Pendingorder
import orderlord.website.Item
import orderlord.website.Customer
import orderlord.website.Store

import java.sql.Time

class BootStrap {
	
	def geocoderService
	def orderCalculatorService
	
	def init = { servletContext ->
		switch(GrailsUtil.environment){
//			case "development" : break
			case "development":
//			case "false":
			
			//Customers ///////////////////////////////////////////////////////
			def customer1 = new Customer(
				userName:"roise_r",
				password:"admin",
				firstName:"Martin",
				lastName:"Klosi",
				email:"roise3000@gmail.com",
				balance:29.99,
				)
			customer1.save()
			if(customer1.hasErrors()){
				println customer1.errors
			}

			def customer2 = new Customer(
				userName:"bootstrap_bill",
				password:"admin",
				firstName:"Bill",
				lastName:"Parker",
				email:"bootstrap_bill@gmail.com",
				balance:40.90,
				)
			customer2.save()
			if(customer2.hasErrors()){
				println customer2.errors
			}

			def customer3 = new Customer(
				userName:"mimoza_gd",
				password:"admin",
				firstName:"Milena",
				lastName:"Paraskieva",
				email:"mimoza_gd@gmail.com",
				balance:40.00,
				)
			customer3.save()
			if(customer3.hasErrors()){
				println customer3.errors
			}

			def customer4 = new Customer(
				userName:"slayter",
				password:"admin",
				firstName:"Todorin",
				lastName:"Radev",
				email:"slayter@gmail.com",
				balance:30.00,
				)
			customer4.save()
			if(customer4.hasErrors()){
				println customer4.errors
			}
			
			def customer5 = new Customer(
				userName:"hok419",
				password:"naija",
				firstName:"HU",
				lastName:"KA",
				email:"hu@ka.com",
				balance:30000.00,
				)
			customer5.save()
			if(customer5.hasErrors()){
				println customer5.errors
			}
			
			def customer6 = new Customer(
				userName:"codemonkey",
				password:"admin",
				firstName:"Ryan",
				lastName:"Gaffney",
				email:"ryanmgaffney@gmail.com",
				balance:60000.00,
				)
			customer6.save()
			if(customer6.hasErrors()){
				println customer6.errors
			}
			
			def customer7 = new Customer(
				userName:"customer7",
				password:"admin",
				firstName:"Testing",
				lastName:"Rocks",
				email:"customer7@gmail.com",
				balance:70000.00,
				)
			customer7.save()
			if(customer7.hasErrors()){
				println customer7.errors
			}
			
			def customer8 = new Customer(
				userName:"customer8",
				password:"admin",
				firstName:"Debugging",
				lastName:"Sucks",
				email:"customer8@gmail.com",
				balance:80000.00,
				)
			customer8.save()
			if(customer8.hasErrors()){
				println customer8.errors
			}
			
			//end of customers
			
			//Stores ///////////////////////////////////////////////////////////
			
			def store1 = new Store(
				storeName:"La Burrita",
				login:"laburrita",
				password:"laburritapass",
				storeAddress:"1832 Euclid Ave., Berkeley, CA 94709-1318",
				phoneNumber:"(510) 845-9090",
//				latitude:37.875631D,
//				longitude:-122.260847D,
				linkToPic:"http://j.mp/No_Image_Available",
				)
			geocoderService.fillInLatLong(store1)
			store1.save()
			if(store1.hasErrors()){
				println store1.errors
			}

			def store2 = new Store(
				storeName:"McDonald's",
				login:"mcdonalds",
				password:"mcdonaldspass",
				storeAddress:"1998 Shattuck Avenue, Berkeley, CA 94704-1032",
				phoneNumber:"(510) 843-1500",
//				latitude:37.872125D,
//				longitude:-122.268680D,
				linkToPic:"http://j.mp/No_Image_Available",
				)
			geocoderService.fillInLatLong(store2)
			store2.save()
			if(store2.hasErrors()){
				println store2.errors
			}

			def store3 = new Store(
				storeName:"Bongo Burger",
				login:"bongoburger",
				password:"bongoburgerpass",
				storeAddress:"1839 Euclid Avenue, Berkeley, CA 94709-1317",
				phoneNumber:"(510) 548-3400",
//				latitude:37.875576D,
//				longitude:-122.260841D,
				linkToPic:"http://j.mp/No_Image_Available",
				)
			geocoderService.fillInLatLong(store3)
			store3.save()
			if(store3.hasErrors()){
				println store3.errors
			}
			
			def store4 = new Store(
				storeName:"Subway (restaurant 1)",
				login:"subway001",
				password:"subwaypass",
				storeAddress:"92 Shattuck Square, Berkeley, CA 94704",
				phoneNumber:"(510) 649-0224",
//				latitude:37.871766D,
//				longitude:-122.268034D,
				linkToPic:"http://j.mp/No_Image_Available",
				)
			geocoderService.fillInLatLong(store4)
			store4.save()
			if(store4.hasErrors()){
				println store4.errors
			}
			
			def storeAdmin = new Store(
				storeName:"Store Admin",
				login:"storeadmin",
				password:"storeadminpass",
				role:"admin",
				storeAddress:"ToBeDetermined",
				phoneNumber:"(510) 888-0000",
//				latitude:0.0D,
//				longitude:0.0D,
				linkToPic:"http://j.mp/No_Image_Available",
				)
			storeAdmin.save()
			if(storeAdmin.hasErrors()){
				println storeAdmin.errors
			}
			//end of Stores

			
			//items //////////////////////////////////////////////////
				def item1 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Coke",
					description:"Item of La Burrita",
					price:0.72,
					itemEtp:1,
					store:store1,
					storeName:"La Burrita",
					)
				item1.save()
				if(item1.hasErrors()){
					println item1.errors
				}
				
				def item2 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Burrito",
					description:"Item of La Burrita",
					price:4.55,
					itemEtp:7,
					store:store1,
					storeName:"La Burrita",
					)
				item2.save()
				if(item2.hasErrors()){
					println item2.errors
				}
				
				def item3 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Quesadilla",
					description:"Item of La Burrita",
					price:5.59,
					itemEtp:8,
					store:store1,
					storeName:"La Burrita",
					)
				item3.save()
				if(item3.hasErrors()){
					println item3.errors
				}

				def item4 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Fishburger",
					description:"Item of McDonald's",
					price:3.98,
					itemEtp:7,
					store:store2,
					storeName:"McDonald's",
					)
				item4.save()
				if(item4.hasErrors()){
					println item4.errors
				}

				def item5 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Double Cheeseburger",
					description:"Item of McDonald's",
					price:5.98,
					itemEtp:12,
					store:store2,
					storeName:"McDonald's",
					)
				item5.save()
				if(item5.hasErrors()){
					println item5.errors
				}
				
				def item6 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Large Fries",
					description:"Item of McDonald's",
					price:2.98,
					itemEtp:4,
					store:store2,
					storeName:"McDonald's",
					)
				item6.save()
				if(item6.hasErrors()){
					println item6.errors
				}
				
				def item7 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Coke",
					description:"Item of McDonald's",
					price:2.15,
					itemEtp:1,
					store:store2,
					storeName:"McDonald's",
					)
				item7.save()
				if(item7.hasErrors()){
					println item7.errors
				}
				
				def item8 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Coffee",
					description:"Item of Bongo Burger",
					price:0.69,
					itemEtp:2,
					store:store3,
					storeName:"Bongo Burger",
					)
				item8.save()
				if(item8.hasErrors()){
					println item8.errors
				}

				def item9 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Two Egger w/ Beef Sausage",
					description:"Item of Bongo Burger",
					price:5.95,
					itemEtp:11,
					store:store3,
					storeName:"Bongo Burger",
					)
				item9.save()
				if(item9.hasErrors()){
					println item9.errors
				}

				def item10 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Mushroom Burger",
					description:"Item of Bongo Burger",
					price:6.15,
					itemEtp:13,
					store:store3,
					storeName:"Bongo Burger",
					)
				item10.save()
				if(item10.hasErrors()){
					println item10.errors
				}

				def item11 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Bongo Fries",
					description:"Item of Bongo Burger",
					price:2.15,
					itemEtp:6,
					store:store3,
					storeName:"Bongo Burger",
					)
				item11.save()
				if(item11.hasErrors()){
					println item11.errors
				}

				def item12 = new Item(
					linkToPic:"http://j.mp/No_Image_Available",
					itemName:"Coke",
					description:"Item of Bongo Burger",
					price:0.89,
					itemEtp:1,
					store:store3,
					storeName:"Bongo Burger",
					)
				item12.save()
				if(item12.hasErrors()){
					println item12.errors
				}

			//end of Item
				
			//Activeorder ///////////////////////////////////////////////////////////
				def activeorder1 = new Activeorder(//***
					store:store2,
					customer:customer2,
					)
				.addToItems(item4)
				.addToItems(item5)
				.addToItems(item6)
				activeorder1.totalCost = orderCalculatorService.calculateTotalCost(activeorder1.items)
				activeorder1.orderEtp = orderCalculatorService.calculateOrderEtp(activeorder1.items)
				activeorder1.figureOutPickupTime()
				
				activeorder1.save()
				if(activeorder1.hasErrors()){
					println activeorder1.errors
				}

//				def activeorder2 = new Activeorder(
//					store:store2,
//					customer:customer3,
//					)
//				.addToItems(item4)
//				.addToItems(item5)
//				.addToItems(item6)
//				.addToItems(item6)
//				.addToItems(item7)
//				.addToItems(item7)
//				activeorder2.totalCost = orderCalculatorService.calculateTotalCost(activeorder2.items)
//				activeorder2.orderEtp = orderCalculatorService.calculateOrderEtp(activeorder2.items)
//				activeorder2.figureOutPickupTime()
//				
//				activeorder2.save()
//				if(activeorder2.hasErrors()){
//					println activeorder2.errors
//				}
				
//				def activeorder3 = new Activeorder(
//					store:store1,
//					customer:customer3,
//					)
//				.addToItems(item1)
//				.addToItems(item2)
//				activeorder3.totalCost = orderCalculatorService.calculateTotalCost(activeorder3.items)
//				activeorder3.orderEtp = orderCalculatorService.calculateOrderEtp(activeorder3.items)
//				activeorder3.figureOutPickupTime()
//				
//				activeorder3.save()
//				if(activeorder3.hasErrors()){
//					println activeorder3.errors
//				}

				def activeorder4 = new Activeorder(//***
					store:store1,
					customer:customer4,
					)
				.addToItems(item2)
				.addToItems(item2)
				.addToItems(item2)
				.addToItems(item1)
				.addToItems(item1)
				.addToItems(item1)
				activeorder4.totalCost = orderCalculatorService.calculateTotalCost(activeorder4.items)
				activeorder4.orderEtp = orderCalculatorService.calculateOrderEtp(activeorder4.items)
				activeorder4.figureOutPickupTime()
				
				activeorder4.save()
				if(activeorder4.hasErrors()){
					println activeorder4.errors
				}

//				def activeorder5 = new Activeorder(
//					store:store3,
//					customer:customer1,
//					)
//				.addToItems(item8)
//				.addToItems(item9)
//				activeorder5.totalCost = orderCalculatorService.calculateTotalCost(activeorder5.items)
//				activeorder5.orderEtp = orderCalculatorService.calculateOrderEtp(activeorder5.items)
//				activeorder5.figureOutPickupTime()
//				
//				activeorder5.save()
//				if(activeorder5.hasErrors()){
//					println activeorder5.errors
//				}

				def activeorder6 = new Activeorder(//***
					store:store2,
					customer:customer6,
					)
				.addToItems(item4)
				.addToItems(item5)
				.addToItems(item6)
				.addToItems(item7)
				activeorder6.totalCost = orderCalculatorService.calculateTotalCost(activeorder6.items)
				activeorder6.orderEtp = orderCalculatorService.calculateOrderEtp(activeorder6.items)
				activeorder6.figureOutPickupTime()
				
				activeorder6.save()
				if(activeorder6.hasErrors()){
					println activeorder6.errors
				}
				
				//end of activeorders
				
				//pendingorders ///////////////////////////////////////////////////////////////////////
				
				def pendingorder1 = new Pendingorder(
					orderType:"Scheduled-Order",
					pickupTime:new Date()+2,
					store:store3,
					customer:customer6,
					)
				.addToItems(item8)
				.addToItems(item9)
				pendingorder1.totalCost = orderCalculatorService.calculateTotalCost(pendingorder1.items)
				pendingorder1.orderEtp = orderCalculatorService.calculateOrderEtp(pendingorder1.items)
				pendingorder1.figureOutScheduleDay()
				
				pendingorder1.save()
				if(pendingorder1.hasErrors()){
					println pendingorder1.errors
				}

				def pendingorder2 = new Pendingorder(
					orderType:"Scheduled-Order",
					pickupTime:new Date()+4,
					store:store3,
					customer:customer3,
					)
				.addToItems(item10)
				.addToItems(item11)
				.addToItems(item12)
				pendingorder2.totalCost = orderCalculatorService.calculateTotalCost(pendingorder2.items)
				pendingorder2.orderEtp = orderCalculatorService.calculateOrderEtp(pendingorder2.items)
				pendingorder2.figureOutScheduleDay()
				
				pendingorder2.save()
				if(pendingorder2.hasErrors()){
					println pendingorder2.errors
				}

				def pendingorder3 = new Pendingorder(
					orderType:"One-Time-Order",
					pickupTime:new Date()+1,
					store:store3,
					customer:customer4,
					)
				.addToItems(item10)
				.addToItems(item10)
				pendingorder3.totalCost = orderCalculatorService.calculateTotalCost(pendingorder3.items)
				pendingorder3.orderEtp = orderCalculatorService.calculateOrderEtp(pendingorder3.items)
				pendingorder3.figureOutScheduleDay()
				
				pendingorder3.save()
				if(pendingorder3.hasErrors()){
					println pendingorder3.errors
				}

				def pendingorder6 = new Pendingorder(
					orderType:"One-Time-Order",
					pickupTime:new Date()+3,
					store:store1,
					customer:customer2,
					)
				.addToItems(item1)
				.addToItems(item1)
				.addToItems(item2)
				.addToItems(item3)
				pendingorder6.totalCost = orderCalculatorService.calculateTotalCost(pendingorder6.items)
				pendingorder6.orderEtp = orderCalculatorService.calculateOrderEtp(pendingorder6.items)
				pendingorder6.figureOutScheduleDay()
				
				pendingorder6.save()
				if(pendingorder6.hasErrors()){
					println pendingorder6.errors
				}
				
//				def pendingorder4 = new Pendingorder(
//					orderType:"One-Time-Order",
//					pickupTime:new Date(),
//					store:store1,
//					customer:customer6,
//					)
//				.addToItems(item1)
//				.addToItems(item2)
//				pendingorder4.totalCost = orderCalculatorService.calculateTotalCost(pendingorder4.items)
//				pendingorder4.orderEtp = orderCalculatorService.calculateOrderEtp(pendingorder4.items)
//				pendingorder4.figureOutScheduleDay()
//				
//				pendingorder4.save()
//				if(pendingorder4.hasErrors()){
//					println pendingorder4.errors
//				}
//				
//				def pendingorder5 = new Pendingorder(
//					orderType:"Scheduled-Order",
//					pickupTime:new Date(),
//					store:store2,
//					customer:customer6,
//					)
//				.addToItems(item4)
//				.addToItems(item6)
//				.addToItems(item7)
//				pendingorder5.totalCost = orderCalculatorService.calculateTotalCost(pendingorder5.items)
//				pendingorder5.orderEtp = orderCalculatorService.calculateOrderEtp(pendingorder5.items)
//				pendingorder5.figureOutScheduleDay()
//				
//				pendingorder5.save()
//				if(pendingorder5.hasErrors()){
//					println pendingorder5.errors
//				}
				
				//end of pending orders
				
				
								
				break
				
			case "test" : break
			case "production" : break
		}
	}
	def destroy = { }
}