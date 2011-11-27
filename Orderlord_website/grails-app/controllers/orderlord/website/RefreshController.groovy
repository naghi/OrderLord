package orderlord.website

class RefreshController {

    def refreshactiveorders = {
		params.refreshType = "ajax"
		redirect (controller:"activeorder", action:"list", params: params)
	}
	
	def refreshpendingorders = {
		params.refreshType = "ajax"
		redirect (controller:"pendingorder", action:"list", params: params)
	}
}
