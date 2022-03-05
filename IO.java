package Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IO {
	private static Scanner sc = new Scanner(System.in);
	
	//------------- Login Process -------------
	//Initial Menu
	public static int loginOrRegister() {
		int ans;
		do {
			System.out.println("\n1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print("> ");
			ans = sc.nextInt();
		} while (ans < 1 || ans > 3);
		
		return ans;
	}
	
	public static int registerOnly() {
		int ans;
		do {
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print("> ");
			ans = sc.nextInt();
		} while (ans < 2 || ans > 3);
		
		return ans;
	}
	
	//Login
	public static User login(List<User> users) {
		String name;
		boolean check = false;
		do {
			System.out.print("Nickname: ");
			name = sc.next();
			for (User u : users) {
				if (u.getNickname().equals(name)) {
					check = true;
				} 
			}
			if (!check) { 
				System.out.println("Nickname incorrect!\nDo you want to leave? [Y/N] "); 
				if (readChar() == 'y') {
					return null;
				}
			}
		} while(!check);
		
		
		for (User u : users) {
			if (u.getNickname().equals(name)) {
				System.out.print("Password: ");
				String pw = sc.next();
				if (u.getPassword().equals(pw)) {
					System.out.println("Login successful");
					return u;
				}
			}
		}
		
		System.out.println("Password incorrect!");
		return null;
	}
	
	//Register
	public static List<User> register(List<User> users) throws FileNotFoundException {
		String ans;
		boolean check;
		do {
			check = false;
			System.out.print("Nickname: ");
			ans = sc.next();
			for (User u : users) {
				if (u.getNickname().equals(ans)) {
					check = true;
					System.out.println("Nickname already in use!");
				} 
			}
		} while(check);
		System.out.print("First name: ");
		ans += "," + sc.next();
		System.out.print("Surname: ");
		ans += "," + sc.next();
		System.out.print("Password: ");
		ans += "," + sc.next();
		
		System.out.println("You have successfully registered!");
		
		users.add(new User(ans,'r'));
		writeScanner("usersList.txt",users);
		return users;
	}
	
	
	//------------- Logged in interactions -------------
	//User Menu
	public static int userMenu(User u) {
		int ans=0;
		do { 
			System.out.print("\n\nHello " + u.getFirstName() + " " + u.getSurname() + "! What do you wanna do?\n"
				+ "\n1. New flight"
				+ "\n2. Check your flight stats"
				+ "\n3. Check global flight stats"
				+ "\n4. Logout"
				+ "\n> ");
			ans = sc.nextInt();
		} while (ans < 1 || ans > 4);
		
		return ans;
	}
	
	//Origin
	public static void portugalAirport() {
		System.out.println("\n\n######################################");
		System.out.println("#                                    #");
		System.out.printf("# %-34s #\n", StringUtils.center("PORTUGAL'S AIRPORT",34));
		System.out.println("#                                    #");
		System.out.println("######################################");
	}
	
	//Destination
	public static void countryAirport(Countries country) {
		System.out.println("\n\n######################################");
		System.out.println("#                                    #");
		System.out.printf("# %-34s #\n", StringUtils.center("WELCOME TO",34));
		System.out.printf("# %-34s #\n", StringUtils.center(country.toString().toUpperCase(),34));
		System.out.println("#                                    #");
		System.out.println("######################################");
	}	
		
	//Returns a valid country of Enum Countries
	public static Countries getDestination() {
		Countries country = null;
		String dest;
		do {
			System.out.print("\nWhere do you want to fly to? ");
			dest = sc.next().toLowerCase();

			for (Countries c : Countries.values()) {
				if (c.name().toLowerCase().equals(dest)) {
					country = c;
				}
			}
		} while(country == null);
		return country;
	}	
	
	//----------- Load and Save Database --------------
	//Read users list from file
	public static List<User> readScanner(String fileName) throws FileNotFoundException  {
		File file = new File(fileName);
		Scanner scf = new Scanner(file); 
		
		List<User> usersFromFile = new ArrayList<User>();
		
		while(scf.hasNextLine()) {
			String line = scf.nextLine();
			
			usersFromFile.add(new User(line));
		}
		
		scf.close();
		
		return usersFromFile;
	}
	
	//Write users list to file
	public static void writeScanner(String fileName, List<User> list) throws FileNotFoundException {
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(file);
		
		for (User u : list) {
			pw.println(u.toString());
		}
		
		pw.close();
	}		
	
	
	//Read Bloom Filter from file
	public static BloomFilter readBF(String fileName) throws IOException, ClassNotFoundException  {
		ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(fileName)); 
		
		Map<Integer, List<Integer>> mapRandNum = (Map<Integer, List<Integer>>)objectIn.readObject();
		int[] vector = (int[])objectIn.readObject();
		
		objectIn.close();
		
		return new BloomFilter(mapRandNum, vector);
	}
	
	//Write Bloom Filter to file
	public static void writeBF(String fileName, Map<Integer, List<Integer>> mapRandNum, int[] vector) throws FileNotFoundException, IOException {
		ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(fileName));
		
		objectOut.writeObject(mapRandNum);
		objectOut.writeObject(vector);
		
		objectOut.close();
	}		
			
	//Read char
	public static char readChar() {
		return sc.next().toLowerCase().charAt(0);
	}			
}

//Format help
class StringUtils {

    public static String center(String s, int size) {
        return center(s, size, ' ');
    }

    public static String center(String s, int size, char pad) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }
}
