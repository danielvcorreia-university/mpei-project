package Projeto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
	private final static int numberOfCountries = 50;
	private final static String bftxt = "bloomFilter.txt";
	private final static String userstxt = "usersList.txt";
	private static List<User> users;
	
	public static void main (String[] args) throws ClassNotFoundException, IOException {
		
		do {
			//Initialize the Bloom Filter
			File file = new File(bftxt);
			BloomFilter bloomFilter;
			if (file.exists() && !file.isDirectory()) {
				bloomFilter = IO.readBF(bftxt);
			} else {
				bloomFilter = new BloomFilter(numberOfCountries, 0.01);
			}
			
			User user = null;
			boolean logged = false;
			//Reads the users list from last session
			do { 
				file = new File(userstxt);
				if (file.exists() && !file.isDirectory()) {
					users = IO.readScanner(userstxt);
					//Login and Register menu
					int mainMenuAns = 0;
					
					do {
						mainMenuAns = IO.loginOrRegister();
						switch(mainMenuAns) {
		
							case 1: 
									user = IO.login(users);
									if (!(user == null)) {
										logged = true;
									} 
									break;
									
							case 2: users = IO.register(users);
									break;
							case 3: System.exit(1);
									break;
						}
					} while(mainMenuAns!=3 && !logged);
				} else {
					users = new ArrayList<User>();
					int mainMenuAns = 0;
					logged = false;
					
					do {
						mainMenuAns = IO.registerOnly();
						switch(mainMenuAns) {
							case 2: users = IO.register(users);
									break;
							case 3: System.exit(1);
									break;
						}
					} while(mainMenuAns!=3 && users.isEmpty());
				}
		} while(!logged);
				
				
		//Logged in
		IO.portugalAirport();
		
		int ans = 0;
		do {
			ans = IO.userMenu(user);
			switch(ans) {
				case 1:
						Countries destination = IO.getDestination(); //get valid country by Enum Countries
						user.getContEst().add(destination); //increment country value in User's StochasticCounter
						bloomFilter.insert(destination); //increment country value in Bloom Filter
						IO.writeScanner(userstxt,users); //update usersList.txt after a new trip
						IO.writeBF(bftxt, bloomFilter.getMap(), bloomFilter.getVector()); //update bloomFilter.txt after a new trip
						
						IO.countryAirport(destination);
						break;
				case 2:
						user.getContEst().getCount();
						break;
				case 3:
						bloomFilter.stats();
						break;
				case 4:
						break;
			}
		} while (ans != 4);			
	} while (true);
		
		

		
		
		/* Testes
		BloomFilter.insert("Espanha");
		BloomFilter.insert("França");
		BloomFilter.insert("Reino Unido");
		BloomFilter.insert("Reino Unido");
		BloomFilter.insert("Alemanha");
		BloomFilter.insert("Grécia");
		BloomFilter.insert("Itália");
		BloomFilter.insert("Áustria");
		BloomFilter.insert("Portugaa");
		BloomFilter.insert("Portugll");
		BloomFilter.insert("Portugla");
		BloomFilter.isMember("França"); //True
		BloomFilter.isMember("Portugal"); //False
		BloomFilter.isMember("Alemanha"); //True
		BloomFilter.isMember("Reino Unido"); //True
		System.out.println();
		BloomFilter.delete("França");
		BloomFilter.delete("Reino Unido");
		BloomFilter.isMember("França"); //False
		BloomFilter.isMember("Portugal"); //False
		BloomFilter.isMember("Alemanha"); //True
		BloomFilter.isMember("Reino Unido"); //True
		BloomFilter.insert("Espanha");
		BloomFilter.insert("Espanha");
		BloomFilter.insert("Espanha");
		System.out.println();
		System.out.println(BloomFilter.count("França")); //0
		System.out.println(BloomFilter.count("Espanha")); //4
		System.out.println(BloomFilter.count("Reino Unido")); //1
		*/
				
	}
}
