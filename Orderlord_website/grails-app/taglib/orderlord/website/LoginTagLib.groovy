package orderlord.website

class LoginTagLib {
	
	def loginControl = {
		if(request.getSession(false) && session.store){
			out << "${session.store.storeName} "
			out << """[${link(action:"logout", controller:"authentication"){"Logout"}}]"""
		} 
		else {
			out << """[${link(action:"login", controller:"authentication"){"Login screen"}}]"""
		}
	}
	
}