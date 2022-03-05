package Projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BloomFilter implements Serializable{
	private static final long serialVersionUID = -6389477010405949L;
	private int size;
	private int numHashFunc;
	private int[] vector;
	private Map<Integer, List<Integer>> randomNumbers = new HashMap<Integer, List<Integer>>();
	
	public BloomFilter(int numElems, double probFalsPos) {
		//Numero de células necessárias no bloom filter
		size = calculateSize(numElems, probFalsPos);
		initialize(size);
		
		//Número de hash functions recomendado
		numHashFunc = optimalNumberHashFunc(size, numElems);
		
		//Info sobre o BF
		//System.out.println("Foi criado um BoomFilter de tamanho " + size + " para " + numElems + " elementos, com uma probabilidade de falsos positivos igual a " + probFalsPos);
		//System.out.println("O número ideal de Hash Functions é " + numHashFunc);
		
		randomNum();
	}
	
	public BloomFilter(Map<Integer, List<Integer>> randNum, int[] vector) {
		this.vector = vector;
		this.randomNumbers = randNum;
		this.numHashFunc = randNum.size();
	}
	
	//Bloom Filter's size
	private int calculateSize(int n, double p) {
		return (int) Math.round(((-n*Math.log(p))/(Math.pow(Math.log(2),2))));
	}
	
	//Bloom Filter's number of Hash Functions
	private static int optimalNumberHashFunc(int m, int n) {
		return (int) Math.round((m/n)*Math.log(2));
	}	
	
	public int[] getVector() {
		return vector;
	}
	
	public Map<Integer, List<Integer>> getMap() {
		return randomNumbers;
	}
	
	//------------- Counting Bloom Filter Operations ----------------
	//Initialize
	public int[] initialize(int size) {
		vector = new int[size];
			
		return vector;
	}
	
	//Insert
	public void insert(Countries countryVisited) {
		for (int i = 0; i < numHashFunc; i++) {
			int index = hashFunction(countryVisited.name(), i);
			vector[index] = vector[index]+1;
		}
	}
	
	//Is Member
	public boolean isMember(String country) {
		for (int i = 0; i < numHashFunc; i++) {
			int index = hashFunction(country, i);
			if (vector[index]<1) {
				System.out.println("It is NOT member");
				return false;
			}
		}
		System.out.println("Might be member");
		return true;
	}
	
	//Delete
	public void delete(String country) {
		for (int i = 0; i < numHashFunc; i++) {
			int index = hashFunction(country, i);
			if (vector[index] > 0) {
				vector[index]--;
			}
		}
	}
	
	//Count
	public int count(String country) {
		int arrayOfIndex[] = new int[numHashFunc];
		int valorMin = 9999999;
		
		for (int i = 0; i < numHashFunc; i++) {
			arrayOfIndex[i] = hashFunction(country, i);
			if (vector[arrayOfIndex[i]] < valorMin) {
				valorMin = vector[arrayOfIndex[i]];
			}
		}
		
		return valorMin;
	}
	
	//Stats
	public void stats() {
		for (Countries c : Countries.values()) {
			System.out.print("\n" + c.name() + " got " + count(c.name()) + " visits!");
		}
	}
	
	//------------------ Universal Hashing -----------------
	//Hash Function
	private int hashFunction(String x, int actualHashFunc) {
		int temp = 0;
		List<Integer> values = randomNumbers.get(actualHashFunc);
		for (int i = 0; i<x.length();i++) {
			temp += x.charAt(i)*(values.get(i));
		}

		int hash = temp % vector.length;
		
		return hash;
	}
	
	//Random Numbers generator
	private void randomNum() {
		List<Integer> randoms = null;
		for (int i = 0; i < numHashFunc; i++) {
			if (randomNumbers.containsKey(i)) {
				randoms = randomNumbers.get(i);
			} else {
				randoms = new ArrayList<Integer>();
			}
			for (int c = 1; c <= 25; c++) {
				int val = ((int)(Math.random() * vector.length));
				randoms.add(val);
			}
			randomNumbers.put(i, randoms);
		}
		
	}
}