package orderlord.website

class Store   {

	String storeName
	String login
	String password
	String role = "user"
	String storeAddress
	double latitude = 0.0
	double longitude = 0.0
	String phoneNumber
	String linkToPic
		
	static hasMany = [items:Item, activeorders:Activeorder, pendingorders:Pendingorder]
	
	static transients = ['admin']
	
	boolean isAdmin(){
		return role == "admin"
	}
	
	static mapping = {
		table 'Stores'
		storeName column: 'name'
		login column: 'username'
		password column: 'password'
		role column: 'role'
		storeAddress column: 'address'
		latitude colum: 'latitude'
		longitude colum: 'longitude'
		phoneNumber column: 'phoneNumber'
		linkToPic column: 'pictureLink'
	}
	
    static constraints = {
		storeName(blank:false)
		login(size:5..255, blank:false, unique:true)
        password(size:5..255, blank:false, password:true)
		role(inList:["admin", "user"])
		storeAddress(blank:false)
		latitude()
		longitude()
		phoneNumber(size:0..20, blank:true)
		linkToPic(size:0..2083)
    }
	
	String toString(){
		storeName
	}
	
}
