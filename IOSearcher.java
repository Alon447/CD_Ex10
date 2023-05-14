package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOSearcher implements FileSearcher {
	private boolean stop;
	
	public IOSearcher()
	{
		stop=false;
	}

	public boolean search(String word, String...filenames) {
		for(String file: filenames)
		{
			if(stop)
			{
				break;
			}
			File f = new File(file);
			try {
				Scanner scanner = new Scanner(f);
				while(scanner.hasNext())
				{
					String next = scanner.next();
					if(next.equals(word))
						return true;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		return false;
	}

	@Override
	public void stop() {
		this.stop=true;
	}


}
