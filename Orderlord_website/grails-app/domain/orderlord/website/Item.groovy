package orderlord.website


class Item {

	String linkToPic
	String itemName
	double price
	long itemEtp
	String description
	//int storeId
	
	static belongsTo = [store:Store]
	
	static mapping = {
		table 'Items'
		linkToPic column: 'pictureLink'
		itemName column: 'name'
		price column: 'price'
		itemEtp column: 'etp'
		description column: 'description'
		store column:'store_id'
	}
	
    static constraints = {
		itemName(blank:false)
		description()
		price(min:0D)
		itemEtp(min:0L)
		linkToPic(size:0..2083)
		store()
	}
	
	String toString(){
		"${itemName} -- ${description}  --  price: \$${price}  --  ETP: ${itemEtp} min"
	}
	
}
