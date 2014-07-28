import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		
	}
	
	public String[] getRootDir()
	{
		//get all user director
		
		this.root_folder = new File(rootPath);
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
			else if(listDirs[i].isFile())
			{
//				System.out.println("error occur : unknow directory or wrong user Name");
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
			else if(listFiles[i].isDirectory())
			{
//				System.out.println("we got a directory here...");
			}
		}
		return files;
	}
	
	public Mail getMailContent(String workFolder, String file_name)
	{
		Mail mail = null;
		
		ArrayList<String> messID = null;
		ArrayList<String> date = null;
		ArrayList<String> sent = null;
		ArrayList<String> recv = null;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(workFolder + "//" + file_name ));
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
					
				//line 1 messID, line 2 date
				switch(line_count)
				{
				case 1:
						messID = getMessID(line);
						break;
				case 2:
						date = getDate(line);
						break;
				}
				Pattern pattern;
				Matcher matcher;
				boolean matchFound;
				
				if(line_count > 2)
				{
					match_word = "To";
					pattern = Pattern.compile(match_word);
					matcher = pattern.matcher(temp[0]);
					matchFound = matcher.find();

					if(matchFound)
					{
						//this is a reply mail
						switch(line_count)
						{
						case 3:
								sent = getSentID(line);//just sent mail
								break;
						case 4:
								sent = getSentID(line);//reciv mail
								break;
						}
											
//						Mail mail = new Mail(messID[0], );//consider multiple sent
					}
					else
					{
						switch(line_count)
						{
						case 3:
							recv = getRecvID(line);//line three is "From"
							break;
						}
					}
				}	
			}
		}
		catch (IOException e)
		{
			System.err.println(e.fillInStackTrace());
		}
		finally
		{
			
		}
		Date _date = transDate(date);
		Snapshot _status = estimaSnap(Snapshot.ONE, _date);
		String status = _status.toString();
		
		if(sent == null)
		{
			mail = new Mail(messID.get(0), null, recv.get(1), date, status);//this is reciv mail
			mail.setRecTime(date);
		}
		else if(recv == null)
		{
			mail = new Mail(messID.get(0), recv.get(1), null, date, status);//this is sent only mail
			mail.setSentTime(date);
		}
		else
		{
			if(sent.size()==1)
			{
				mail = new Mail(messID.get(0), sent.get(0), recv.get(1), date, status);//reply mail
			}	
			else
			{
				mail = new Mail(messID.get(0), sent.get(1), recv.get(1), date, status);//reply mail
			}
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
				st = new StringTokenizer(rawData, ":| ");
				infor = "From";
				break;
			case "recive":
				st = new StringTokenizer(rawData, " |:| ");
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
	
	
	public Snapshot estimaSnap(Snapshot status, Date time)
	{	
		Snapshot curr_status = status;
		int times = 10;
		while(times != 0)
		{
			if(compareTime(status.low_bound, status.up_bound, time))
			{
				return curr_status;
			}
			else
			{
				curr_status = curr_status.next();
			}
			times--;
		}
		return Snapshot.ONE;
	}
	
	private boolean compareTime(Date low_bound, Date up_bound, Date mailDate)
	{
		/*
		 * using bound in snapshot
		 */
		if(mailDate.after(low_bound) && mailDate.before(up_bound))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Date transDate(ArrayList<String> rawDate)
	{
		//rawDate 
		//index 1: date, 2: month, 3:year, 4:time
		String[] dir_month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		String day = rawDate.get(1);
		int month = 0;
		for(int i = 0; i < dir_month.length; i++)
		{
			if(rawDate.get(2) == dir_month[i])
			{
				month = i;
			}
		}
		String year = rawDate.get(3);
		String time = rawDate.get(4);
		
		StringBuilder dateString = new StringBuilder();
		dateString.append(day+"-");
		dateString.append(month+"-");
		dateString.append(year+" ");
		dateString.append(time);
		
		
		Date date = null; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		try 
		{
			date = sdf.parse(dateString.toString());
		} catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

}
