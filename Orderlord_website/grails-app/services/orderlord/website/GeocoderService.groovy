package orderlord.website

class GeocoderService {

    static transactional = true
	
	def base = "http://maps.googleapis.com/maps/api/geocode/xml?"
	def slurper = new XmlSlurper()

    def fillInLatLong(Store store) {
		def params = [sensor:false, address:[store.storeAddress].collect {URLEncoder.encode(it, 'UTF-8')}.join(',+')]
		
		def url = base + params.collect { k,v -> "$k=$v" }.join('&')
//		println url
		
		def response = slurper.parse(url)
		
		store.latitude = response.result.geometry.location.lat.toDouble()
		store.longitude = response.result.geometry.location.lng.toDouble()
//		println "$store.latitude, $store.longitude"
    }
	
}
