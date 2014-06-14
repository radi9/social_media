import java.io.File;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map.Entry;

class Graph
{
	public LinkedList<User> users;
	
	public Graph()
	{
		if(users.isEmpty())
		{
			System.out.println("Social Networing is empty, ready to initilize");
		}
	}
	
	public void expend(User user)
	{
		users.add(user);
	}
	
	@SuppressWarnings("unchecked")
	public void sortTable()
	{
		Collections.sort(users);
	}
	

}

public class main 
{

	
	public static void get_userSnap(int userID)
	{
		
	}
	
	public static void set_userSnap(User a)
	{
		
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		//give the root path, and list all file under this directionary
		String root_path = "/home/red/ML_DM data/enron_mail_20110402/maildir";
		FileProcess dataSet = new FileProcess(root_path);
		String[] userIndex = dataSet.getRootDir();//150 folder, 150 user
		String[] user_contenType = dataSet.getDir(userIndex[0]);
		int count = 0;

		System.out.println(userIndex[0]);
		System.out.println(user_contenType[4]);
		String[] filesIndex = dataSet.getFiles(userIndex[0], user_contenType[4]);
		
		for(String temp : filesIndex)
		{
			count ++;
			System.out.println(count + temp);
		}
		
		//Date directory
		String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		int[] year = {2008, 2009, 2010, 2011};
		
		//read all of the folder and split them by snapshot
		int num_snap = 8;
		
//		String filePath = "logs/test.log";
//		File file = new File(filePath);
//		//此時如果直接createNewFile()
//
//		if(!file.exists())
//		{
//			try 
//			{
//				file.createNewFile();
//			} catch (IOException e) 
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}

}
