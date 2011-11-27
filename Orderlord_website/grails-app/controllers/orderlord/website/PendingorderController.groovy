package orderlord.website

class PendingorderController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def orderCalculatorService
	def pendingorderPostService
	def localHostFinderService
	
	void callFlashMessage(String minTime, String maxTime){
		flash.message = "The specified time does not fall between the allowed values of '${minTime}' and '${maxTime}'"
		return
	}
	
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
			viewType = "pendingbody"

		def pendingorders = Pendingorder.list(params)
		for (pendingorder in pendingorders){
			pendingorder.orderType = pendingorder.figureOutOrderType()
		}
		
		render(view: "${viewType}", model: [localHostAddress: localHostAddress, pendingorderInstanceList: Pendingorder.list(params), pendingorderInstanceTotal: Pendingorder.count()])
        	
    }

    def create = {
        def pendingorderInstance = new Pendingorder()
        pendingorderInstance.properties = params
        return [pendingorderInstance: pendingorderInstance]
    }

    def save = {
        def pendingorderInstance = new Pendingorder(params)
		
//		if ( pendingorderInstance.pickupTime.before(pendingorderInstance.minDate()) || pendingorderInstance.pickupTime.after(pendingorderInstance.maxDate()) ){
//			callFlashMessage("${pendingorderInstance.minDate()}", "${pendingorderInstance.maxDate()}")
//			render(view: "create", model: [pendingorderInstance: pendingorderInstance])
//			return
//		}
		
		pendingorderInstance.figureOutPickupTime()
		pendingorderInstance.figureOutScheduleDay()
		
        if (pendingorderInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), pendingorderInstance.id])}"
            redirect(action: "show", id: pendingorderInstance.id)
        }
        else {
            render(view: "create", model: [pendingorderInstance: pendingorderInstance])
        }
    }

    def show = {
        def pendingorderInstance = Pendingorder.get(params.id)
        if (!pendingorderInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), params.id])}"
            redirect(action: "list")
        }
        else {
			pendingorderInstance.orderType = pendingorderInstance.figureOutOrderType()
			
            [pendingorderInstance: pendingorderInstance]
        }
    }

    def edit = {
        def pendingorderInstance = Pendingorder.get(params.id)
        if (!pendingorderInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), params.id])}"
            redirect(action: "list")
        }
        else {
			pendingorderInstance.orderType = pendingorderInstance.figureOutOrderType()
			
            return [pendingorderInstance: pendingorderInstance]
        }
    }

    def update = {
        def pendingorderInstance = Pendingorder.get(params.id)
        if (pendingorderInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (pendingorderInstance.version > version) {
                    
                    pendingorderInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'pendingorder.label', default: 'Pendingorder')] as Object[], "Another user has updated this Pendingorder while you were editing")
                    render(view: "edit", model: [pendingorderInstance: pendingorderInstance])
                    return
                }
            }
			
			Date oldPickupTime = pendingorderInstance.pickupTime
            pendingorderInstance.properties = params
				
			pendingorderInstance.totalCost = orderCalculatorService.calculateTotalCost(pendingorderInstance.items)
			pendingorderInstance.orderEtp = orderCalculatorService.calculateOrderEtp(pendingorderInstance.items)
			
//			pendingorderInstance.figureOutPickupTime()
			
			if ( pendingorderInstance.pickupTime.before(pendingorderInstance.minDate()) || pendingorderInstance.pickupTime.after(pendingorderInstance.maxDate()) ){
				callFlashMessage("${pendingorderInstance.minDate()}", "${pendingorderInstance.maxDate()}")
				pendingorderInstance.pickupTime = oldPickupTime
				render(view: "edit", model: [pendingorderInstance: pendingorderInstance])
				return
			}
								
			pendingorderInstance.figureOutScheduleDay()
			
            if (!pendingorderInstance.hasErrors() && pendingorderInstance.save(flush: true)) {
				if (pendingorderInstance.orderEtp == 0)
					flash.message = "Pendingorder ${pendingorderInstance.id} updated. No items were selected, thus pickupTime is still 6 days from now!"
				else
                	flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), pendingorderInstance.id])}"
				
				if (pendingorderInstance.orderEtp > 0)
					pendingorderPostService.interruptSleep()
		
                redirect(action: "show", id: pendingorderInstance.id)
				return
            }
            else {
                render(view: "edit", model: [pendingorderInstance: pendingorderInstance])
				return
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def pendingorderInstance = Pendingorder.get(params.id)
        if (pendingorderInstance) {
            try {
                pendingorderInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), params.id])}"
            redirect(action: "list")
        }
    }
}
