package smitebot;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
 
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
		        if (input[1].equalsIgnoreCase("ping")) {
		            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Pong!").queue();
		        }
		
		        else if (input[1].equalsIgnoreCase("getclan")) {
		    		try {
						String response = session.getTeamPlayers(input[2]);
						//parse and simplify response
						e.getChannel().sendMessage(e.getAuthor().getAsMention() +response).queue();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
		        }
	        }
	    }
    }
}
