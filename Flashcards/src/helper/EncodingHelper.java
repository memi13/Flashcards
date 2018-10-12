package helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public   class  EncodingHelper
{
	/**
	 * a encode mh5 hasch
	 * @param value the value to anecode
	 * @return
	 */
	public static String createMd5(String value)
	{ 
		try  
		{
			MessageDigest md = MessageDigest.getInstance("MD5");			
			byte[] hashInBytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
	        for (byte b : hashInBytes) 
	        {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
		}
		catch(Exception ex)
		{
			
		}
		return "";
	}

}
