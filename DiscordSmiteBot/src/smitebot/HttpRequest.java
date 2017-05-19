package smitebot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequest {

	private static final String url = "http://api.smitegame.com/smiteapi.svc/";
	private ArrayList<String> params;
	
	public HttpRequest(){
		params = new ArrayList<String>();
	}
	
	// HTTP GET request
	public String sendGet(String path) throws Exception {

		URL obj = new URL(url + path + buildParamString());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
			// NONE CURRENTLY
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//return result
		return response.toString();
	}
	
	public void addParam(String name, String value){
		String param = name+"="+value;
		char add = params.isEmpty() ? '?' : '&';
		this.params.add(add + param);
	}
	
	public void clearParams(){
		params.clear();
	}
	
	private String buildParamString(){
		if (params.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		for (String param : params){
			sb.append(param);
		}
		return sb.toString();
	}
}
