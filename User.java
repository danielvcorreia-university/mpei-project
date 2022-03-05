package Projeto;

import java.util.HashMap;
import java.util.Map;

public class User {
	private String nickname;
	private String firstName;
	private String surname;
	private String password;
	private final int numCount = 50;
	private StochasticCounter contEst;
	
	public User(String data, char r) {
		String[] strings = data.split(",");
		nickname = strings[0];
		firstName = strings[1];
		surname = strings[2];
		password = strings[3];
		contEst = new StochasticCounter(numCount, 0.8);
	}
	
	public User(String data) {
		HashMap<Countries, Integer> counter = new HashMap<Countries, Integer>(50);
		String[] strings = data.split(",");
		nickname = strings[0];
		firstName = strings[1];
		surname = strings[2];
		password = strings[3];
		
		if (strings.length == 5) {
			String[] dictKV = strings[4].split("#");
			
			for (String s : dictKV) {
				String[] split = s.split("~");
				for (Countries c : Countries.values()) {
					if (c.name().toLowerCase().equals(split[0].toLowerCase())) {
						counter.put(c, Integer.parseInt(split[1]));
					}
				}
			}
			
			contEst = new StochasticCounter(counter);
		} else {
			contEst = new StochasticCounter(numCount, 0.8);
		}
		
	}

	public StochasticCounter getContEst() {
		return contEst;
	}
	
	public boolean checkPassword(String pw) {
		if (pw.equals(password)) {
			return true;
		}
		return false;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		return nickname+","+firstName+","+surname+","+password+","+contEst;
	}
}
