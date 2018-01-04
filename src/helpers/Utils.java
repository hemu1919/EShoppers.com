package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	private static String base_url = "";

	public static List<String> getPathParts(String ref, String act) {
		String[] ref_parts = (base_url + ref).split("/");
		String[] act_parts = act.split("/");
		int steps = act_parts.length - ref_parts.length;
		int length = act_parts.length - 1;
		if(steps == 0)
			return null;
		List<String> result = new ArrayList<>();
		for(int i = 0; i < steps; i++) {
			result.add(0, act_parts[length - i]);
		}
		return result;
	}
	
	public static String getStorageURL(String uri) {
		return "http://eshoppers.us-east-1.elasticbeanstalk.com/storage/" + uri;
	}
	
	public static String hashPassword(String password)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }        
        return sb.toString();        
    }
    
}
