package smitebot;

import org.json.JSONArray;
import org.json.JSONObject;

public class SmiteApiMethods {
	
	private static SmiteApiMethods session = null;
	
	private HttpRequest request;
	private String sessionId;
	
	public SmiteApiMethods(){	
    	Utils.setupUtils();

		request = null;
		try {
			sessionId = createSession();
			System.out.println("Session Id: "+sessionId);

		//	System.out.println("Team Details: "+getTeamDetails("Beeforoni"));
		//	System.exit(0);
			
		} catch (Exception e) {
			sessionId = null;
			e.printStackTrace();
		}
		
	}
	
	
	protected static SmiteApiMethods getSession(){
		if (session == null) session = new SmiteApiMethods();
		return session;
	}
	
	private String createSession() throws Exception{
		System.out.println("Creating Session");
		request = new HttpRequest();
		String methodName = "createsession";
		String timestamp = Utils.getCurrentTimestampGMT();
		System.out.println("Timestamp: "+timestamp);
		String path = methodName+"JSON/"+Utils.devId+"/"+Utils.createSignature(methodName, timestamp)+"/"+timestamp;
		String response = request.sendGet(path);

		JSONObject responseJson = new JSONObject(response);
		return responseJson.getString("session_id");
	}
	
	public boolean hasSession(){
		return sessionId != null;
	}
	

	
	/* 
	 * METHODS 
	 */
	
	public String getClanId(String clanName) throws Exception{
		String methodName = "searchteams";
		String timestamp = Utils.getCurrentTimestampGMT();
		String path = methodName+"JSON/"+Utils.devId+"/"+Utils.createSignature(methodName, timestamp)+"/"+sessionId+"/"+timestamp+"/"+clanName;
		String response = request.sendGet(path);

		JSONObject responseJson = new JSONArray(response).getJSONObject(0);
		
		return responseJson.getInt("TeamId")+"";
	}
	
	public String getTeamPlayers(String clanName) throws Exception{
		String methodName = "getteamplayers";
		String timestamp = Utils.getCurrentTimestampGMT();
		String path = methodName+"JSON/"+Utils.devId+"/"+Utils.createSignature(methodName, timestamp)+"/"+sessionId+"/"+timestamp+"/"+getClanId(clanName);
		String response = request.sendGet(path);
		
		return response;
	}
	
	public String getTeamDetails(String clanName) throws Exception{
		String methodName = "getteamdetails";
		String timestamp = Utils.getCurrentTimestampGMT();
		String path = methodName+"JSON/"+Utils.devId+"/"+Utils.createSignature(methodName, timestamp)+"/"+sessionId+"/"+timestamp+"/"+getClanId(clanName);
		String response = request.sendGet(path);
		
		return response;
	}
	
	public String getPlayer(String player) throws Exception{
		String methodName = "getplayer";
		String timestamp = Utils.getCurrentTimestampGMT();
		String path = methodName+"JSON/"+Utils.devId+"/"+Utils.createSignature(methodName, timestamp)+"/"+sessionId+"/"+timestamp+"/"+player;
		String response = request.sendGet(path);
		
		return response;
	}
	
	
	public String getMatchHistory(String player) throws Exception{
		String methodName = "getmatchhistory";
		String timestamp = Utils.getCurrentTimestampGMT();
		String path = methodName+"JSON/"+Utils.devId+"/"+Utils.createSignature(methodName, timestamp)+"/"+sessionId+"/"+timestamp+"/"+player;
		String response = request.sendGet(path);
		
		return response;
	}
	
	public String getPlayerAchievements(String playerId) throws Exception{
		String methodName = "getplayerachievements";
		String timestamp = Utils.getCurrentTimestampGMT();
		String path = methodName+"JSON/"+Utils.devId+"/"+Utils.createSignature(methodName, timestamp)+"/"+sessionId+"/"+timestamp+"/"+playerId;
		String response = request.sendGet(path);

		return response;
	}
	
	public String getPatchInfo() throws Exception{
		String methodName = "getpatchinfo";
		String timestamp = Utils.getCurrentTimestampGMT();
		String path = methodName+"JSON/"+Utils.devId+"/"+Utils.createSignature(methodName, timestamp)+"/"+sessionId+"/"+timestamp;
		String response = request.sendGet(path);
		
		return response;
	}
	
}
