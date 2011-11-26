package orderlord.website

class ItemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
			
		def storeid = session.store.id
		def items = Item.findAllByStore(Store.load(storeid))
		
		if (session?.store?.admin)
			[itemInstanceList: Item.list(params), itemInstanceTotal: Item.count()]
		else if (!session?.store?.admin)
			[itemInstanceList: items, itemInstanceTotal: items.count()]		
    }

    def create = {
        def itemInstance = new Item()
        itemInstance.properties = params
        return [itemInstance: itemInstance]
    }

    def save = {
        def itemInstance = new Item(params)
        if (itemInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'item.label', default: 'Item'), itemInstance.id])}"
            redirect(action: "show", id: itemInstance.id)
        }
        else {
            render(view: "create", model: [itemInstance: itemInstance])
        }
    }

    def show = {
        def itemInstance = Item.get(params.id)
        if (!itemInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
            redirect(action: "list")
        }
        else {
            [itemInstance: itemInstance]
        }
    }

    def edit = {
        def itemInstance = Item.get(params.id)
		
//		${activeorderInstance.store ? orderlord.website.Item.findAllByStore(activeorderInstance.store) : []}
		
		def orders = []
		orders.addAll(orderlord.website.Activeorder.list())
		orders.addAll(orderlord.website.Pendingorder.list())
		
		for (order in orders){ //*** extract this into some kind of func
			for (item in order.items){
//				println item.id
//				println itemInstance.id
				if (item.id == itemInstance.id){
//					println "yahoo"
					flash.message = "Cannot modify this item! It is part of: ${order.toString()}"
					redirect(controller:"item", action:"show", id: itemInstance.id)
					return
				}
			}
		}

        if (!itemInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [itemInstance: itemInstance]
        }
    }

    def update = {
        def itemInstance = Item.get(params.id)
        if (itemInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (itemInstance.version > version) {
                    
                    itemInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'item.label', default: 'Item')] as Object[], "Another user has updated this Item while you were editing")
                    render(view: "edit", model: [itemInstance: itemInstance])
                    return
                }
            }
            itemInstance.properties = params
            if (!itemInstance.hasErrors() && itemInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'item.label', default: 'Item'), itemInstance.id])}"
                redirect(action: "show", id: itemInstance.id)
            }
            else {
                render(view: "edit", model: [itemInstance: itemInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def itemInstance = Item.get(params.id)
		
		def orders = []
		orders.addAll(orderlord.website.Activeorder.list())
		orders.addAll(orderlord.website.Pendingorder.list())
		
		for (order in orders){
			for (item in order.items){
//				println item.id
//				println itemInstance.id
				if (item.id == itemInstance.id){
//					println "yahoo"
					flash.message = "Cannot delete this item! It is part of: ${order.toString()}"
					redirect(controller:"item", action:"show", id: itemInstance.id)
					return
				}
			}
		}
		
        if (itemInstance) {
            try {
                itemInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
            redirect(action: "list")
        }
    }
}
