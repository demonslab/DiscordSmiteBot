package smitebot.embed_objects;

import java.awt.Color;
import java.text.ParseException;

import org.json.JSONObject;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import smitebot.Utils;

public class PlayerEmbed extends EmbedBuilder{
	/*
	 * "Avatar_URL":"http:\/\/cds.q6u4m8x5.hwcdn.net\/web\/smite-app\/\/wp-content\/uploads\/2015\/06\/T_Ratatoskr_Default_Icon.png",
	 * "Created_Datetime":"12\/15\/2013 1:11:19 AM",
	 * "Id":2689961,
	 * "Last_Login_Datetime":"5\/20\/2017 4:17:30 PM",
	 * "Leaves":1,
	 * "Level":22,
	 * "Losses":107,
	 * "MasteryLevel":10,
	 * "Name":"[BFRN]DemonSlab",
	 * "Personal_Status_Message":"Let's do that again",
	 * "Rank_Stat_Conquest":0,
	 * "Rank_Stat_Duel":0,
	 * "Rank_Stat_Joust":0,
	 * "RankedConquest":
	 * 		"Leaves":0,
	 * 		"Losses":0,
	 * 		"Name":"Conquest",
	 * 		"Points":0,
	 * 		"PrevRank":0,
	 * 		"Rank":0,
	 * 		"Rank_Stat_Conquest":null,
	 * 		"Rank_Stat_Duel":null,
	 * 		"Rank_Stat_Joust":null,
	 * 		"Season":0,
	 * 		"Tier":0,
	 * 		"Trend":0,
	 * 		"Wins":0,
	 * 		"player_id":null,
	 * 		"ret_msg":null
	 * "RankedDuel":
	 * 		"Leaves":0,
	 * 		"Losses":0,
	 * 		"Name":"Duel",
	 * 		"Points":0,
	 * 		"PrevRank":0,
	 * 		"Rank":0,
	 * 		"Rank_Stat_Conquest":null,
	 * 		"Rank_Stat_Duel":null,
	 * 		"Rank_Stat_Joust":null,
	 * 		"Season":0,
	 * 		"Tier":0,
	 * 		"Trend":0,
	 * 		"Wins":0,
	 * 		"player_id":null,
	 * 		"ret_msg":null
	 * "RankedJoust":
	 * 		"Leaves":0,
	 * 		"Losses":0,
	 * 		"Name":"Joust",
	 * 		"Points":0,
	 * 		"PrevRank":0,
	 * 		"Rank":0,
	 * 		"Rank_Stat_Conquest":null,
	 * 		"Rank_Stat_Duel":null,
	 * 		"Rank_Stat_Joust":null,
	 * 		"Season":0,
	 * 		"Tier":0,
	 * 		"Trend":0,
	 * 		"Wins":0,
	 * 		"player_id":null,
	 * 		"ret_msg":null
	 * "Region":"North America",
	 * "TeamId":900428,
	 * "Team_Name":"Beeforoni",
	 * "Tier_Conquest":0,
	 * "Tier_Duel":0,
	 * "Tier_Joust":0,
	 * "Total_Achievements":45,
	 * "Total_Worshippers":1599,
	 * "Wins":91,
	 * "ret_msg":null
	 *
	 */
	
	private static final String SMITE_PROFILE_BASEURL = "https://www.smitegame.com/player-stats/?set_platform_preference=pc&player-name=";
	
	public PlayerEmbed(JSONObject player, MessageReceivedEvent e){
			//Header
		this.setColor(Color.ORANGE);
		this.setThumbnail(player.getString("Avatar_URL"));
		String discordAvatar = e.getAuthor().getAvatarUrl();
		this.setAuthor(
				player.getString("Name"), 
				SMITE_PROFILE_BASEURL+player.getString("Name").replaceFirst("\\[(.*?)\\]", ""), 
				discordAvatar == null ? e.getAuthor().getDefaultAvatarUrl() : discordAvatar
			);
		
		//Description
		CharSequence description = "**" +
				player.getString("Team_Name") +
				"**\n*" + player.getString("Personal_Status_Message") + "*";
		this.appendDescription(description);
		
		//Levels
		this.addField("Stats", 
			"*\0**Lvl**:" + player.getInt("Level") +
					"\t**Mastery Lvl**: " + player.getInt("MasteryLevel")+
					"\n**Wins**: "+ player.getInt("Wins") + " " +
					"\t**Losses**: "+ player.getInt("Losses") +
					"\n**Worshippers**: "+ player.getInt("Total_Worshippers")
					+"*" 
			, true);
		
		//Account Date Created
		try {
			this.addField(new Field("Account Created:", Utils.changeDateTimeZone(player.getString("Created_Datetime")), false));
			this.addField(new Field("Last Login:", Utils.changeDateTimeZone(player.getString("Last_Login_Datetime")), true));

		} catch (ParseException e1) {
			e1.printStackTrace();
			this.addBlankField(true);
		}
	}
	

}
