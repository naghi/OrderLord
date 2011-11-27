package orderlord.website

import grails.util.GrailsUtil

class LocalHostFinderService {

    static transactional = true

	def static internalAddress = null
	def static externalAddress = null
	def static address
	
	static String figureOutLocalHostAddress(){
		switch(GrailsUtil.environment){
			case "development":
				address = "localhost"
//				address = getInternalAddress()
//				address = getExternalAddress()
				break
			case "test" :
				address = getExternalAddress()
				break
			case "production" :
				address = getExternalAddress()
				break
		}
		return address
	}
	/////////////////////////////////////////
	
	static String getInternalAddress(){
		
		if (internalAddress != null){
			return internalAddress
		}
		else{
			internalAddress = InetAddress.localHost.hostAddress
			return internalAddress
		}
	}//end of getInternalAddress()
	
	static String getExternalAddress(){
	
		if (externalAddress != null){
			return externalAddress
		}
		else{
			URL whatismyip = new URL("http://automation.whatismyip.com/n09230945.asp");
			BufferedReader br = new BufferedReader(new InputStreamReader( whatismyip.openStream() ));
			
			externalAddress = br.readLine(); //you get the IP as a String
			br.close();
			
	//		System.out.println(ip);
			return externalAddress
		}
		
	}//end of getExternalAddress()
	
}//end of class
