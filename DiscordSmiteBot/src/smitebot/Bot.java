package smitebot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
 
public class Bot {
 
    public static JDA jda;
 
//    public static final String BOT_TOKEN = "MzE1MTU0NTU3NDAxNDMyMDY2.DACqsg.nLkof0R_ChX-65TAgpQcXLhe99Y";
    
    public static void main(String[] args) {    	

    	SmiteApiMethods session = SmiteApiMethods.getSession();
    	try {
            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener(session)).setToken(Utils.botToken).buildBlocking();

	//		driver.getPlayer("DemonSlab");
	//		driver.getMatchHistory("DemonSlab");
	//		driver.getPlayerAchievements(2689961 + "");
        
        } catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
 
    }
 
}