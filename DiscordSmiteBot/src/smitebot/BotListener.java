package smitebot;

import java.awt.Color;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import smitebot.embed_objects.PlayerEmbed;
import smitebot.embed_objects.TeamMember;
import smitebot.embed_objects.TeamOverview;
 
public class BotListener extends ListenerAdapter {
 
		//Eventually verified channels will be kept in a database with the name and asMentioned
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
    	List<User> users = e.getMessage().getMentionedUsers();
    	User bot = null;

    	if (users == null)
    		return;
    	if (users.isEmpty())
    		return;
    		
    	bot = users.get(0);
    	
    	if (botName.equals(bot.getName())){
    		boolean verified = false;
    		StringBuilder sb = new StringBuilder("**");
    		for (String verifier : verifiedChannels) {
    			sb.append("\n\t"+verifier);
    			if (verifier.equals(e.getChannel().getName()))
    				verified = true;
    		}
    		if (!verified) {
    			e.getChannel().sendMessage(
    					e.getAuthor().getAsMention() + " use one of the verified chat channels to properly use "+bot.getAsMention()+":"+sb.toString()+"**"
    				).queue();
	    	}
	    	else {	
	    		if (input.length == 1) 
	    			e.getChannel().sendMessage(e.getAuthor().getAsMention() + " to see a list of commands, send the message:\n\t**"+bot.getAsMention()+" help**").queue();
	    		//Ping
	    		else if (input[1].equalsIgnoreCase("ping")) {
		            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Pong!").queue();
		        }
		        
		        //Help
		        else if (input[1].equalsIgnoreCase("help")) {
		     
		        	EmbedBuilder builder = new EmbedBuilder();
		        	builder.setColor(Color.GREEN);		
		        	
		        	builder.addField("ping", "*tests the bot*", false);
		        	builder.addField("getPatchInfo", "*returns info about the latest patch version*", false);
		        	builder.addField("getClanInfo [clanName]", "*returns info about the given clan*", false);
		        	builder.addField("getClanMembers [clanName]", "*returns info about members of the given clan (limited to 20/1000)*", false);
		        	builder.addField("getPlayer [playerName]", "*returns info about the given player*", false);
		        	
		        	MessageBuilder mb = new MessageBuilder();
		        	mb.setEmbed(builder.build());
		        	mb.append(e.getAuthor().getAsMention() + " below are a list of commands for "+bot.getAsMention() +":");
		        	e.getChannel().sendMessage(mb.build()).queue();
		        }
		
	    		//Get Patch Info
		        else if (input[1].equalsIgnoreCase("getPatchInfo")) {
		            try {
						e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Version "+ new JSONObject(session.getPatchInfo()).getString("version_string")).queue();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
		        }
		        
		        //Get Clan Info
		        else if (input[1].equalsIgnoreCase("getclaninfo")) {
		    		try {
						String response = session.getTeamDetails(input[2]);
						//parse and simplify response
						e.getChannel().sendMessage(e.getAuthor().getAsMention() +"\n"+new TeamOverview(new JSONArray(response).getJSONObject(0)).toString()).queue();
						
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
		        
		        //Get Player
		        else if (input[1].equalsIgnoreCase("getPlayer")) {
		        	try {
		        		String response = session.getPlayer(input[2]);
		        		System.out.println(response);
		        		
		        		JSONObject player = new JSONArray(response).getJSONObject(0);
	
		        		e.getChannel().sendMessage(new PlayerEmbed(player, e).build()).queue();
		        		
		        	} catch (Exception e1) {
		        		e1.printStackTrace();
		        	}
		        }
		        
		        //No Such Method
		        else {
	    			e.getChannel().sendMessage(e.getAuthor().getAsMention() + " method not found. To see a list of commands, send the message:\n\t**"+bot.getAsMention()+" help**").queue();
		        }
	        }
	    }
    }
}
