package orderlord.website

class StoreController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def geocoderService
	
    def index = {
		if (session.store == null){
			render ("You don't have permissions to access this resource or you are not logged in!")
			return
		}
		
		if (session?.store?.admin)
			redirect(action:"list", params: params)
		else if (!session?.store?.admin){
			params.id = session.store.id
			redirect(action:"show", params: params)
		}
    }

    def list = {
        //params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.max = 50
		
		if (session.store == null){
			render ("You don't have permissions to access this resource or you are not logged in!")
			return
		}
			
		def stores = [session.store]
		
		if (session?.store?.admin)
			[storeInstanceList: Store.list(params), storeInstanceTotal: Store.count()]
		else if (!session?.store?.admin)
			[storeInstanceList: stores, storeInstanceTotal: stores.count()]
    }

    def create = {
        def storeInstance = new Store()
        storeInstance.properties = params
        return [storeInstance: storeInstance]
    }

    def save = {
        def storeInstance = new Store(params)
				
		if (storeInstance.role != "admin" && storeInstance.storeAddress != "")
			geocoderService.fillInLatLong(storeInstance)
		
        if (storeInstance.save(flush: true)) {
			println storeInstance.latitude
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'store.label', default: 'Store'), storeInstance.id])}"
            redirect(action: "show", id: storeInstance.id)
        }
        else {
            render(view: "create", model: [storeInstance: storeInstance])
        }
    }

    def show = {
        def storeInstance = Store.get(params.id)
        if (!storeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'store.label', default: 'Store'), params.id])}"
            redirect(action: "list")
        }
        else {
//			println storeInstance.latitude
            [storeInstance: storeInstance]
        }
    }

    def edit = {
        def storeInstance = Store.get(params.id)
        if (!storeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'store.label', default: 'Store'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [storeInstance: storeInstance]
        }
    }

    def update = {
        def storeInstance = Store.get(params.id)
        if (storeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (storeInstance.version > version) {
                    
                    storeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'store.label', default: 'Store')] as Object[], "Another user has updated this Store while you were editing")
                    render(view: "edit", model: [storeInstance: storeInstance])
                    return
                }
            }
            storeInstance.properties = params
			
			if (storeInstance.role != "admin" && storeInstance.storeAddress != "")
				geocoderService.fillInLatLong(storeInstance)
				
//			println storeInstance.latitude
			
            if (!storeInstance.hasErrors() && storeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'store.label', default: 'Store'), storeInstance.id])}"
                redirect(action: "show", id: storeInstance.id)
            }
            else {
                render(view: "edit", model: [storeInstance: storeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'store.label', default: 'Store'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def storeInstance = Store.get(params.id)
        if (storeInstance) {
            try {
                storeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'store.label', default: 'Store'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'store.label', default: 'Store'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'store.label', default: 'Store'), params.id])}"
            redirect(action: "list")
        }
    }
}
