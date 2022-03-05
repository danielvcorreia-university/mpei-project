package Projeto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StochasticCounter {
	private Map<Countries, Integer> counter;
	private double prob;
	
	public StochasticCounter(int numCountries, double p) {
		counter = new HashMap<Countries, Integer>(numCountries);
		prob = p;
	}
	
	public StochasticCounter(HashMap<Countries,Integer> dict) {
		counter = dict;
		prob = 0.8;
	}
	
	public void add(Countries c) {
		double rand = Math.random();
		if (rand < 0.8) {
			if (counter.containsKey(c)) {
				int val = counter.get(c);
				counter.put(c, ++val);
			} else {
				counter.put(c, 1);
			}
		}
	}
	
	public int get(Countries c) {
		if (!counter.containsKey(c)) {
			return 0;
		}
		return (int) Math.round(counter.get(c)/prob);
	}
	
	public void getCount() {
		for (Countries c : counter.keySet()) {
			if (counter.get(c) == 1) {
				System.out.print("\nYou went to " + c.name() + " " + get(c) + " time!");
			} else {
				System.out.print("\nYou went to " + c.name() + " " + get(c) + " times!");
			}
		}
	}
	
	@Override
	public String toString() {
		String string = "";
		for (Countries c : counter.keySet()) {
			string+=c.name()+"~"+counter.get(c)+"#";
		}
		if (!string.equals("")) {
			string = string.substring(0, string.length()-1);
		}
		return string;
	}
}
