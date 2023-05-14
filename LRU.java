package test;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy {
	private Deque<String> dq;
	private HashMap<String,String> map;
	
	public LRU()
	{
		dq=new LinkedList<>();
		map=new HashMap<>();
	}

	@Override
	public void add(String word) //adds a word and checks if the word exists in the queue already
	{
		if (!map.containsKey(word)) {
			dq.addFirst(word);
			map.put(word, word);
        }
		else
		{
			dq.remove(word);
			dq.addFirst(word);
		}
		
		
	}

	@Override
	public String remove() {
		String rem=dq.pollLast();
		return rem;
	}

}
