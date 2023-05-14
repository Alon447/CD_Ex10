package test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParIOSearcher implements FileSearcher {
	private ExecutorService executor;
	private ArrayList<Future<Boolean>> ans;
	
	public ParIOSearcher()
	{
		executor = Executors.newCachedThreadPool();
		ans=new ArrayList<Future<Boolean>>();

	}
	
	public boolean search(String word, String... fileNames) 
	{
		for(String file: fileNames)
		{
			Future<Boolean> res=this.executor.submit(()->{
				IOSearcher searcher= new IOSearcher();
				 return searcher.search(word, file);
			});
			ans.add(res);
		}
		int temp = ans.size();
		while(temp>0)
		{
			for(Future<Boolean> f: ans)
			{
				if(f.isDone())
				{
					temp--;
					try {
						if(f.get())
						{
							this.stop();
							return true;
						}
							
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		return false;
	}
		
	public void finalize()
	{
		this.executor.shutdown();
	}
	
	public void stop()
	{
		this.executor.shutdownNow();
	}

	
	
}
