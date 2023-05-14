package test;

import java.util.HashSet;

public class CacheManager {
	private HashSet<String> cache;
	private int limit;
	private CacheReplacementPolicy crp;
	
	public CacheManager(int size, CacheReplacementPolicy crp)
	{
		this.limit=size;
		this.cache=new HashSet<String>(size);
		this.crp=crp;
	}
	
	public boolean query(String word)
	{
		if(cache.contains(word))
			return true;
		return false;
		
	}

	public void add(String string) {
		if(cache.size()<this.limit)
		{
			this.cache.add(string);
			this.crp.add(string);
		}
		else // we need to drop an item out of the cache
		{
			String rem=this.crp.remove();
			this.cache.remove(rem);
			this.cache.add(string);
			this.crp.add(string);
		}
		
	}

}
