package orderlord.website

class PendingorderController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def orderCalculatorService
	def pendingorderPostService
	
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
		
		def pendingorders = Pendingorder.list(params)
		for (pendingorder in pendingorders){
			pendingorder.orderType = pendingorder.figureOutOrderType()
		}
		
        [pendingorderInstanceList: pendingorders, pendingorderInstanceTotal: Pendingorder.count()]
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
            pendingorderInstance.properties = params
				
			pendingorderInstance.totalCost = orderCalculatorService.calculateTotalCost(pendingorderInstance.items)
			pendingorderInstance.orderEtp = orderCalculatorService.calculateOrderEtp(pendingorderInstance.items)
			
			pendingorderInstance.figureOutPickupTime()
			
			if ( pendingorderInstance.pickupTime.before(pendingorderInstance.minDate()) || pendingorderInstance.pickupTime.after(pendingorderInstance.maxDate()) ){
				callFlashMessage("${pendingorderInstance.minDate()}", "${pendingorderInstance.maxDate()}")
				render(view: "edit", model: [pendingorderInstance: pendingorderInstance])
				return
			}
								
			pendingorderInstance.figureOutScheduleDay()
			
            if (!pendingorderInstance.hasErrors() && pendingorderInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pendingorder.label', default: 'Pendingorder'), pendingorderInstance.id])}"
				
				//println "aaaaaaaaaaaaa"
				if (pendingorderInstance.orderEtp > 0)
					pendingorderPostService.interruptSleep()
		
                redirect(action: "show", id: pendingorderInstance.id)
            }
            else {
                render(view: "edit", model: [pendingorderInstance: pendingorderInstance])
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
