package orderlord.website

class CheckdbController {

    def index = {
		
	}
	
	def checkdb = {
//		render(view: "create", model: [activeorderInstance: activeorderInstance])
		def someList = []
		redirect (controller:"pendingorder", action:"list")
	}
}
