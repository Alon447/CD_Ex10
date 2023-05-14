package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
	private CacheManager exists;
	private CacheManager notExists;
	private BloomFilter bf;
	private String[] files;
	private ParIOSearcher a;
	
	
	public Dictionary(String...filenames)
	{

		this.exists=new CacheManager(400, new LRU());
		this.notExists=new CacheManager(100, new LFU());
		this.bf =new BloomFilter(256,"MD5","SHA1");
		this.files=new String[filenames.length];
		int i=0;
		for(String file: filenames)
		{
			this.files[i]=file;
			File f = new File(file);
			try {
				Scanner scanner = new Scanner(f);
				while(scanner.hasNext())
				{
					String next = scanner.next();
					bf.add(next);
						
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			i++;
		}

	}
	
	public boolean query(String word)
	{
		if(this.exists.query(word))
			return true;
		if(this.notExists.query(word))
			return false;
		if(this.bf.contains(word))
		{
			this.exists.add(word);
			return true;
		}
		this.notExists.add(word);
		return false;
		
	}

	public boolean challenge(String word) {
		a=new ParIOSearcher();
		if(a.search(word, this.files))
			return true;
		return false;
	}

	public void close() {
		this.a.stop();		
	}

}
