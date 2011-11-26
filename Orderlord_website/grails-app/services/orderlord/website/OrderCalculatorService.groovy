package orderlord.website;

class OrderCalculatorService {

    static transactional = true

    double calculateTotalCost(List listOfItems){
		double calculatedTotalCost = 0
		for (item in listOfItems){
			calculatedTotalCost += item.price
		}
		calculatedTotalCost = (new BigDecimal(calculatedTotalCost)).setScale(2,BigDecimal.ROUND_HALF_EVEN).doubleValue();
		return calculatedTotalCost
	}
	
	long calculateOrderEtp(List listOfItems){
		
		long etpTotal = 0L;
		long maxSoFar = 0L;
		Item item
		Long[] etps = new Long[listOfItems.size()];
		
		double ITEM_COUNT_WEIGHT = 0.20D;
	   
		try
		{
			for (int i = 0; i < listOfItems.size(); ++i)
			{
				item = listOfItems.get(i);
				etps[i] = item.itemEtp;
				maxSoFar = (etps[i] > maxSoFar) ? etps[i] : maxSoFar;
				etpTotal += ITEM_COUNT_WEIGHT*etps[i];
			}
		   
			etpTotal -= ITEM_COUNT_WEIGHT*maxSoFar;
		}
		catch (Exception e) { e.printStackTrace(); }
	   
		return etpTotal + maxSoFar;
	}
	
	
}
