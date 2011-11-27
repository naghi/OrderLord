package orderlord.website

class ActiveorderController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def orderCalculatorService
	def localHostFinderService
	
//	void callFlashMessage(String minTime, String maxTime){
//		flash.message = "The specified time does not fall between the allowed values of '${minTime}' and '${maxTime}'"
//		return
//	}
	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        //params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.max = 50
		
		if (session.store == null){
			render ("You don't have permissions to access this resource or you are not logged in!")
			return
		}
		
		def localHostAddress = localHostFinderService.figureOutLocalHostAddress()
		
		def viewType = "list"
		if (params.refreshType == "ajax")
			viewType = "activebody"
			
		if (session?.store?.admin){
			render(view: "${viewType}", model: [localHostAddress: localHostAddress, activeorderInstanceList: Activeorder.list(params), activeorderInstanceTotal: Activeorder.count()])
//			return
		}
		else if (!session?.store?.admin){
			def storeid = session.store.id
			def activeorders = Activeorder.findAllByStore(Store.load(storeid))
			render(view: "${viewType}", model: [localHostAddress: localHostAddress, activeorderInstanceList: activeorders, activeorderInstanceTotal: activeorders.count()])
//			return
		}
    }

    def create = {
        def activeorderInstance = new Activeorder()
        activeorderInstance.properties = params
        return [activeorderInstance: activeorderInstance]
    }

    def save = {
        def activeorderInstance = new Activeorder(params)
		
//		if ( activeorderInstance.pickupTime.before(activeorderInstance.minDate()) || activeorderInstance.pickupTime.after(activeorderInstance.maxDate()) ){
//			callFlashMessage("${activeorderInstance.minDate()}", "${activeorderInstance.maxDate()}")
//			render(view: "create", model: [activeorderInstance: activeorderInstance])
//			return
//		}
		
		activeorderInstance.figureOutPickupTime()
		
        if (activeorderInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'activeorder.label', default: 'Activeorder'), activeorderInstance.id])}"
            redirect(action: "show", id: activeorderInstance.id)
        }
        else {
            render(view: "create", model: [activeorderInstance: activeorderInstance])
        }
    }

    def show = {
        def activeorderInstance = Activeorder.get(params.id)
        if (!activeorderInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activeorder.label', default: 'Activeorder'), params.id])}"
            redirect(action: "list")
        }
        else {
			session.activeorderid = params.id
            [activeorderInstance: activeorderInstance]
        }
    }

    def edit = {
        def activeorderInstance = Activeorder.get(params.id)
        if (!activeorderInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activeorder.label', default: 'Activeorder'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [activeorderInstance: activeorderInstance]
        }
    }

    def update = {
        def activeorderInstance = Activeorder.get(params.id)
        if (activeorderInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (activeorderInstance.version > version) {
                    
                    activeorderInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'activeorder.label', default: 'Activeorder')] as Object[], "Another user has updated this Activeorder while you were editing")
                    render(view: "edit", model: [activeorderInstance: activeorderInstance])
                    return
                }
            }
            activeorderInstance.properties = params
			
			activeorderInstance.totalCost = orderCalculatorService.calculateTotalCost(activeorderInstance.items)
			activeorderInstance.orderEtp = orderCalculatorService.calculateOrderEtp(activeorderInstance.items)
			
//			if ( activeorderInstance.pickupTime.before(activeorderInstance.minDate()) || activeorderInstance.pickupTime.after(activeorderInstance.maxDate()) ){
//				callFlashMessage("${activeorderInstance.minDate()}", "${activeorderInstance.maxDate()}")
//				render(view: "edit", model: [activeorderInstance: activeorderInstance])
//				return
//			}
			
			activeorderInstance.figureOutPickupTime()
				
            if (!activeorderInstance.hasErrors() && activeorderInstance.save(flush: true)) {
				if (activeorderInstance.orderEtp == 0)
					flash.message = "Activeorder ${activeorderInstance.id} updated. No items were selected!"
				else
					flash.message = "${message(code: 'default.updated.message', args: [message(code: 'activeorder.label', default: 'Activeorder'), activeorderInstance.id])}"
				redirect(action: "show", id: activeorderInstance.id)
				return
            }
            else {
                render(view: "edit", model: [activeorderInstance: activeorderInstance])
				return
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activeorder.label', default: 'Activeorder'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def activeorderInstance = Activeorder.get(params.id)
        if (activeorderInstance) {
            try {
                activeorderInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'activeorder.label', default: 'Activeorder'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'activeorder.label', default: 'Activeorder'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activeorder.label', default: 'Activeorder'), params.id])}"
            redirect(action: "list")
        }
    }
}
