package smitebot;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import objects.TeamMember;
import objects.TeamOverview;
 
public class BotListener extends ListenerAdapter {
 
	private final static String[] verifiedChannels = {"smitebot"};
	private final static String botName = "Smite Bot";
	private final SmiteApiMethods session;
	
	
	public BotListener(SmiteApiMethods session){
		this.session = session;
	}
	
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
    	String[] input = e.getMessage().getRawContent().split(" ");

    	//Check if the bot is messaged
    	boolean messageToBot = false;
    	for (User user : e.getMessage().getMentionedUsers()){
    		if (user.getName().equals(botName))
    			messageToBot = true;
    	}
    	
    	
    	if (messageToBot){
	    	if (! e.getChannel().getName().equals("smitebot")) {
	    		e.getChannel().sendMessage(e.getAuthor().getAsMention() + " use one of the verified chat channels to utilize bot commands");
	    	}
	    	else {	
	    		//Ping
		        if (input[1].equalsIgnoreCase("ping")) {
		            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Pong!").queue();
		        }
		        
		        //Help
		        else if (input[1].equalsIgnoreCase("help")) {
		        	String help = 
		        			  "**ping**:\n\t test the bot\n"
		        			+ "**getClanInfo** ***[clanName]***:\n\t return info about the clan\n"
		        			+ "**getClanMembers** ***[clanName]***:\n\t return info about the members of the clan\n";

		        	e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Try the following commands:\n" + help + "Have Fun!").queue();
		        }
		
		        //Get Clan Info
		        else if (input[1].equalsIgnoreCase("getclaninfo")) {
		    		try {
						String response = session.getTeamDetails(input[2]);
						//parse and simplify response
						e.getChannel().sendMessage(e.getAuthor().getAsMention() +new TeamOverview(new JSONArray(response).getJSONObject(0)).toString()).queue();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
		        }
		        
		        //Get Clan Members
		        else if (input[1].equalsIgnoreCase("getclanmembers")) {
		    		try {
						String response = session.getTeamPlayers(input[2]);
						
						//parse and simplify response
						
						StringBuffer buffer = new StringBuffer();
						JSONArray members = new JSONArray(response);
						for (int i=0; i < (members.length()>20 ? 20 : members.length()); i++){
							buffer.append(new TeamMember(members.getJSONObject(i)).toString() +"\n");
						}
						e.getChannel().sendMessage(e.getAuthor().getAsMention() +" The members of **"+input[2]+ "** are:\n" +buffer.toString() +"Done listing members.").queue();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
		        }
	        }
	    }
    }
}
