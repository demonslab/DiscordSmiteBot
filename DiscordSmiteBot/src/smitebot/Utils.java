package smitebot;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

public class Utils {
	
	protected static String devId;
	protected static String authKey;
	protected static String botToken;
	
	protected static void setupUtils(){
			Properties prop = new Properties();
		    try {
				prop.load(Utils.class.getClassLoader().getResourceAsStream("credentials.properties"));
	
				devId = prop.getProperty("devId");
				authKey = prop.getProperty("authKey");
				botToken = prop.getProperty("botToken");
		
				System.out.println(devId);
				System.out.println(authKey);
				System.out.println(botToken);
				
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
	}
	
	protected static String getMd5(String input){
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(input.getBytes(Charset.forName("UTF-8")));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			return null;
		}
	}


	protected static String createSignature(String methodName, String timestamp){
		return getMd5(devId + methodName + authKey + timestamp);
	}
	
	protected static String getCurrentTimestamp(){
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(new Date());
	}
	
}
