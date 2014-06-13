import java.io.File;

class Graph
{
	
}

public class main 
{
	public static String[] get_userDir(String rootPath)
	{
		File folder = new File(rootPath);
		File[] listFiles = folder.listFiles();
		String[] files = new String[listFiles.length];
		for(int i = 0; i < files.length; i++)
		{
			if(listFiles[i].isFile())
			{
				files[i] = listFiles[i].getName();
			}
			else
			{
				System.out.println("error occur : unknow files or wrong root path");
			}
		}
		return files;
	}
	
	public static void set_userSnap(User a)
	{
		
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		//give the root path, and list all file under this directionary
		String root_path = "/home/red/ML_DM data/enron_mail_20110402/";
		String[] userIndex = get_userDir("root_path");
		
		//Date directory
		String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		int[] year = {2008, 2009, 2010, 2011};
		
		//read all of the folder and split them by snapshot
		int num_snap = 8;
		String filePath = "logs/test.log";
		File file = new File(filePath);
		//此時如果直接createNewFile()

		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
