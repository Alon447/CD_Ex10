package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;

public class BloomFilter {
	private BitSet bt;
	private ArrayList<MessageDigest> alg;
	private BigInteger res;
	private int numOfBits; 
	
	
	public BloomFilter(int i, String ...strings )// get i bits and k strings
	{
		numOfBits=i;
		this.alg=new ArrayList<MessageDigest>();
		bt=new BitSet(i);
		for(String s: strings)
		{
			try {
				MessageDigest md=MessageDigest.getInstance(s);
				this.alg.add(md);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		}
	}

	public void add(String w) // adds a word to the bloom filter and applies all the hash funntion on the word
	{
		for(int i=0; i<alg.size(); i++)
		{
			byte[] bts=alg.get(i).digest(w.getBytes());
			int result=new BigInteger(bts).intValue();
			result=Math.abs(result)%this.numOfBits;
			this.bt.set(result);
		}
		

		
	}

	public boolean contains(String w) 
	{
		for(int i=0; i<alg.size(); i++)
		{
			byte[] bts=alg.get(i).digest(w.getBytes());
			int result=new BigInteger(bts).intValue();
			result=Math.abs((result)%this.numOfBits);
			if(!this.bt.get(result))
				return false;
		}
		return true;
	}
	
	public String toString() 
	{
		String s="";
		 for (int i = 0; i < this.bt.length(); i++) {
		       s+=(this.bt.get(i) ? 1 : 0);
		    }
		return s;
	}


}
