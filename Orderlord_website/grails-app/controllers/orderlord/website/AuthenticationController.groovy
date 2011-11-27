package orderlord.website

class AuthenticationController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def index = {
		if (session.store == null){
			render ("You don't have permissions to access this resource or you are not logged in!")
			return
		}
		render (view:"/index", model:[storename:session.store.storeName])
	}
	
    def login = {
		if (session.store == null){
			render (view:"/login")
		}
		else{
			redirect(action:"index")
		}
	}
	
	def logout = {
		if (session.store == null){
		}
		else if (session.store.admin)
			flash.message = "Administrator ${session.store.storeName} has logged out!"
		else if (!session.store.admin)
			flash.message = "Store ${session.store.storeName} has logged out!"
			
		session.store = null
		redirect(action:"login")
	}
	
	def authenticate = {
		def store = Store.findByLoginAndPassword(params.login, params.password)
		if(store){
			session.store = store
			flash.message = "Store ${store.login} has logged in!" //*** change to store name
			redirect(action:"index")
		}else{
			flash.message = "Store ${params.login}: Wrong login and/or password! Please try again."
			redirect(action:"login")
		}
	}
	
}