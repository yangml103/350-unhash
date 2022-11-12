package hw5;

import java.security.*;

public class Hash {
	
	String hash(int to_hash) {
		try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		String value = String.valueOf(to_hash);
		byte[] digest = md.digest(value.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1,3));
		}
		return sb.toString();
		}
		catch(java.security.NoSuchAlgorithmException e) {
			return null;
		}
		
	}

	
	
	public static void main(String [] args) {
		
		Integer value = Integer.parseInt(args[0]);
		Hash func = new Hash();
		System.out.println(func.hash(value));
		
	}

}
