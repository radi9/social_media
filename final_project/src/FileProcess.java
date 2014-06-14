import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * major function : get directory, get mail content
 */

public class FileProcess
{
	
	private String rootPath;
	private String userName;//a.k.a user folder name
	
	private String subDir;//inbox, sent item, mail..etc
	
	private File root_folder;
	private File curr_folder;
	
	public FileProcess(String rootPath)
	{
		this.rootPath = rootPath;
		this.root_folder = new File(rootPath);
	}
	
	public String[] getRootDir()
	{
		//get all user director
		
		File[] listDirs = root_folder.listFiles();
		
		String[] dirs = new String[listDirs.length];//directors
		
		for(int i = 0; i < dirs.length; i++)
		{
			if(listDirs[i].isDirectory())
			{
				dirs[i] = listDirs[i].getName();
			}
			else
			{
				System.out.println("error occur : unknow directory or wrong root path");
			}
		}
		return dirs;
	}
	
	public String[] getDir(String userName)
	{
		//get user mail folders
		
		this.userName = userName;
		
		curr_folder = new File(rootPath + "//" +userName + "//");
		
		File[] listDirs = curr_folder.listFiles();
		
		String[] dirs = new String[listDirs.length];//directors
		
		for(int i = 0; i < dirs.length; i++)
		{
			if(listDirs[i].isDirectory())
			{
				dirs[i] = listDirs[i].getName();
			}
			else
			{
				System.out.println("error occur : unknow directory or wrong user Name");
			}
		}
		return dirs;
	}
	
	public String[] getFiles(String userName, String mailType)
	{
		//get user files..content mail list
		
		curr_folder = new File(rootPath + "//" +userName + "//" + mailType + "//");
		
		File[] listFiles = curr_folder.listFiles();
		
		String[] files = new String[listFiles.length];//directors

		//mail file name match "."
		Pattern pattern = Pattern.compile(".");
		Matcher matcher;
		boolean matchFound;
		for(int i = 0; i < files.length; i++)
		{
			if(listFiles[i].isFile())
			{
				String fileName = listFiles[i].getName();
				matcher = pattern.matcher(fileName);
				matchFound = matcher.find();
				if(matchFound)
				{
					files[i] = listFiles[i].getName();
				}
			}
			else
			{
				System.out.println("worng mail type or user name");
			}
		}
		return files;
	}
	
	public Mail getMailContent(String workFolder, String file_name)
	{
		Mail mail;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(workFolder + "//" + file_name));
			
			for(int line_count = 1; line_count <= 4; line_count ++)
			{   
				//read first 4 line
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line, " ");
				String[] temp = new String[st.countTokens()];
				
				String match_word;
					
				for(int i = 0;st.hasMoreTokens(); i++)
				{
					temp[i] = st.nextToken();
				}
					
				Pattern pattern;
				Matcher matcher;
				boolean matchFound;
					
				if(line_count == 4)
				{
					match_word = "To";
					pattern = Pattern.compile(match_word);
					matcher = pattern.matcher(temp[0]);
					matchFound = matcher.find();
					if(matchFound)
					{
						//this is a reply mail
						ArrayList<String> messID = getMessID(temp[0]);
						ArrayList<String> date = getDate(temp[1]);
						ArrayList<String> sent = getSentID(temp[2]);
						ArrayList<String> recv = getRecvID(temp[3]);
						Mail mail = new Mail();
					}
					else
					{
						//not match
						//this is just sent mail or just reciv mail
						ArrayList<String> messID = getMessID(temp[0]);
						ArrayList<String> date = getDate(temp[1]);
						ArrayList<String> sent = getSentID(temp[2]);
						Mail mail = new Mail();
					}
				}
			}
		}
		catch (IOException e)
		{
			System.err.println(e.fillInStackTrace());
		}
		return mail;
	}
	
	
	private ArrayList<String> getMessID(String rawData)
	{
		return str_split(rawData, "mess");
	}
	
	private ArrayList<String> getDate(String rawData)
	{
		return str_split(rawData, "date");
	}
	
	private ArrayList<String> getSentID(String rawData)
	{
		return str_split(rawData, "sent");
	}
	
	private ArrayList<String> getRecvID(String rawData)
	{
		return str_split(rawData, "recive");
	}
	
	private ArrayList<String> str_split(String rawData, String type)
	{
		StringTokenizer st = null;
		String patternStr = null;
		String infor = null;
		ArrayList<String> content = new ArrayList<String>();
		switch(type)
		{
			case "mess":
				patternStr = ":| |<|>";
				st = new StringTokenizer(rawData, patternStr);
				infor = "Message";
				break;
			case "date":
				st = new StringTokenizer(rawData, " |,");
				infor = "Date";
				break;
			case "sent":
				st = new StringTokenizer(rawData, ":| ｜,");
				infor = "From";
				break;
			case "recive":
				st = new StringTokenizer(rawData, ":| ");
				infor = "To";
				break;
			default:
				System.err.println("no pattern found");
				break;
		}
		
		//just use the head to distinguish that the type name and content
		Pattern pattern = Pattern.compile(infor);
		Matcher matcher;
		boolean matchFound;
		
		while(st.hasMoreTokens())
		{
			String temp = st.nextToken();
			matcher = pattern.matcher(temp);
			matchFound = matcher.find();
			if( !(matchFound) )
			{
				content.add(temp);
			}
		}
		return content;
	}

}
