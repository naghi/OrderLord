package orderlord.website

class FooterTagLib {
	
	def thisYear = {
		out << new Date().format("yyyy")
	}
	
}
