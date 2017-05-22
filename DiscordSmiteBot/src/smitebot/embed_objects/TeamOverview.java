package smitebot.embed_objects;

import org.json.JSONObject;

public class TeamOverview {
	/*
	 *Sample Output From GetTeamDetails
	 *
	 *"Founder":"PineappleK1llah",
	 *"FounderId":"7221465",
	 *"Losses":0,
	 *"Name":"Beeforoni",
	 *"Players":7,
	 *"Rating":0,
	 *"Tag":"BFRN",
	 *"TeamId":900428,
	 *"Wins":0,
	 *"ret_msg":null
	 * 
	 */
	private String name;
	private String tag;
	private String founder;
	private String numPlayers;
	private String wins;
	private String losses;
	private String rating;	

	public TeamOverview(JSONObject details){
		name = details.getString("Name");
		tag = details.getString("Tag");
		founder = details.getString("Founder");
		numPlayers = details.getInt("Players") + "";
		wins = details.getInt("Wins") + "";
		losses = details.getInt("Losses") + "";
		rating = details.getInt("Rating") + "";
	}
	
	public String toString(){
		return "**"+name +
				  "**: \n\t*Tag: "+tag+
				  " \n\tFounder: "+founder+
				  " \n\tPlayers: "+numPlayers+
				  " \n\tWins: "+wins+
				  " \n\tLosses: "+losses+
				  " \n\tRating: "+rating+"*";
	}
}
