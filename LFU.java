package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.PriorityQueue;

class Pair {
    int  frequency;
    String word;
    public Pair(String word, int frequency)
    {
        this.word= word;
        this.frequency = frequency;
    }
    public String toString()
    {
		return ("word: "+ this.word+", frequency: "+this.frequency);
    }
    public int compareTo(Pair other)
    {
    	return this.frequency-other.frequency;
    }
}

public class LFU implements CacheReplacementPolicy {
	private Map<String, Pair> cache;
	private Map<Integer, LinkedHashSet<Integer>> freqList;
    ArrayList<Pair> pq;

	int minFreq;
	
	public LFU()
	{
		cache = new HashMap<>();
		freqList = new HashMap<>();
		minFreq=0;
		pq = new ArrayList<>();
	}

	@Override
	public void add(String word) {
		boolean added=false;
		for(int i=0; i<pq.size();i++)
		{
			Pair item=pq.get(i);
			if(item.word==word)
			{
				added=true;
				pq.remove(i);
				pq.add(new Pair(item.word,item.frequency+1));
				break;
			}
		}
		if(!added)
			pq.add(new Pair(word,1));
		

		
		/*
		if(cache.containsKey(word))
		{
			int freq=cache.get(word).frequency;
			cache.get(word).frequency++;
			
			if(freq==this.minFreq && freqList.get(freq).size()==0)
			{
				this.minFreq++;
			}
		}
		else
		{
			Pair newPair=new Pair(word,1);
			cache.put(word, newPair);
			this.minFreq=1;
		}
		*/
	}

	@Override
	public String remove() // removes the least frequent
	{
		int minF=this.search();
		for(int i=0;i<this.pq.size();i++)
		{
			if(this.pq.get(i).frequency==minF)
				return this.pq.get(i).word;
		}
		
		return null;
	}
	
	private int search() // return the minimum frequncy in the cache
	{
		int minF=this.pq.get(0).frequency;
		for(int i=1;i<this.pq.size();i++)
		{
			if(this.pq.get(i).frequency<minF)
				minF=this.pq.get(i).frequency;
		}
		return minF;
	}

}
