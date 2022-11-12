package hw5;

public class UnHash {
	
	Hash hashFunc = new Hash();
	
	int unhash(String to_unhash) {
		try {
			
		for (int i = 0; i < Double.POSITIVE_INFINITY; i++) {
			String to_compare = hashFunc.hash(i);

			if (to_compare.equals(to_unhash)) {
				return i;
			}	
			if (Thread.currentThread().isInterrupted()) {
				return -1;
			}
		}
		return -3;
		}
		catch(Exception e) {
			e.printStackTrace();
			return -2;
		}
		
	}
	
	public static void main(String [] args) {
		String value = args[0];
		UnHash func = new UnHash();
		System.out.println(func.unhash(value));
	}

}
