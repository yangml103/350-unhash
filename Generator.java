package hw5;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;


public class Generator {

	public static void main(String[] args) throws Exception {
	BufferedWriter writer = new BufferedWriter(new FileWriter("./hashes.txt"));
	BufferedWriter writer2 = new BufferedWriter(new FileWriter("./nums.txt"));
	Random random = new Random();

	for (int i = 0; i < 500; i++){
	int num = random.nextInt((10000000) + 1);
	String hash = new Hash().hash(num);
	writer2.write(num + "\n");
	System.out.println(hash);
	writer.write(hash + "\n");
	}
	int num = random.nextInt((100) + 1);
	String hash = new Hash().hash(num);
	writer2.write(num);
	writer.write(hash);
	writer.close();
	writer2.close();
	}

}
