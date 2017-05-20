package objects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

public class TeamMember {
	/*
	 * Sample Output From GetTeamPlayers
	 * 
	 * AccountLevel : 22
	 * JoinedDatetime : "3/4/2017 12:54:09 AM"
	 * LastLoginDatetime : "5/19/2017 12:03:01 AM"
	 * Name : "DemonSlab"
	 * ret_msg : null
	 * 
	 */
	private String accountLevel;
	private String lastLogin;
	private String joined;
	private String name;
	
	public TeamMember(JSONObject member){
		DateFormat format = new SimpleDateFormat("M/d/yyyy hh:mm:ss a");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		
		try {
			accountLevel = member.getInt("AccountLevel") + "";
			name = member.getString("Name");
			
			//date joined
			Date date = format.parse(member.getString("JoinedDatetime"));
			format.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
			joined = format.format(date);
			
			//last login
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			date = format.parse(member.getString("LastLoginDatetime"));
			format.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
			lastLogin = format.format(date);

		
		} catch (JSONException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return "**"+name +
				  "**: \n\t*Level "+accountLevel +
				  " \n\tDate Joined: "+joined +
				  " \n\tLastLogin: "+lastLogin+"*";
	}
	
}
