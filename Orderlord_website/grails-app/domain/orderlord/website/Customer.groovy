package orderlord.website

class Customer {

	String userName
	String password
	String email
	String firstName
	String lastName
	double balance
	
	static hasMany = [activeorders:Activeorder, pendingorders:Pendingorder]
	
	static mapping = {
		table 'Customers'
		userName column: 'username'
		password column: 'password'
		email column: 'email'
		firstName column: 'firstName'
		lastName column: 'lastName'
		balance column: 'balance'
	}
	
    static constraints = {
		userName(size:5..255, blank:false, unique:true)
        password(size:5..255, blank:false, password:true)
        email(email:true, blank:false, unique:true)
		firstName()
		lastName()
		balance(min:0D)
    }
	
	String toString(){
		"${firstName} ${lastName}"
	}
	
}
