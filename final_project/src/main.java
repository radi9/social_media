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

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		//give the root path, and list all file under this directionary
		String[] userDir = get_userDir("/home/red/");
	}

}
