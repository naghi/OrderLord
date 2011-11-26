package orderlord.website

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.HttpParams

//import org.apache.http.HttpResponse
//import org.apache.http.client.HttpClient
//import org.apache.http.client.methods.HttpGet
//import org.apache.http.client.methods.HttpPost
//import org.apache.http.entity.StringEntity
//import org.apache.http.impl.client.DefaultHttpClient
//import org.apache.http.params.HttpParams

class PendingorderPostService {

	static transactional = true

//	static String URL = "http://107.20.135.212:12080/APIv5/call/website"
	static String URL = "http://192.168.1.211:8080/APIv2/call/website"

	public static HttpClient getHttpClient() {
		HttpClient httpClient = new DefaultHttpClient();
		final HttpParams params = httpClient.getParams();

		return httpClient;
	}

	public static String interruptSleep(){
		final HttpResponse resp;

//		println URL
		try
		{
			final HttpGet getsmth = new HttpGet(URL);
			System.out.println("Post Request: " + getsmth);

			//System.out.println("Se: "+ getsmth);
			resp = getHttpClient().execute(getsmth);
		} catch (Exception e){e.printStackTrace()}
	}
	
//	private static String convertStreamToString(InputStream is) {
//
//		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//		StringBuilder sb = new StringBuilder();
//
//		String line = null;
//		try {
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + "\n");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				is.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return sb.toString();
//	}

}
