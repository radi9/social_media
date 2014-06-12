import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * each user node got the flowing 11 attributes:
 * 
 */
public class User {
	
	public int id;
	public LinkedList<Mail> inMail;//in degree
	public LinkedList<Mail> outMail;//out degree
	
	public LinkedList<User> adjMatrix;//user connect records
	public LinkedList<Mail> mailList;//mail records
	
	public double avg_resTime;
	public double resScore;
	public double meanDist;//mean distance to all pair in itself networking
	public double clustCoef;
	public double cliq_sizeScore;//same clique same score
	public double cliq_timeScore;
	public double degCen;
	public double betCen;
	public double connScore;//hubs and authorities, depend indegree and outdegree
	public int numMail = mailList.size();
	
	private String rootPath;
	
	public User(String folderName, LinkedList<User> connect)
	{
		//not finished yet, the three case need to consider:
		//"From, To" and "From" and "To"
		
		//initialize, get the sent box and in box mail
		//consider the sent item ??
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(folderName));
			
			ArrayList<String> mailInfo = new ArrayList<String>();
			Mail mail;
			
			//only store the first 4 line
			//4 line may error, because three cases need to be consider
			int count = 0;
			for(String line = br.readLine(); line != null; line = br.readLine())
			{
				mailInfo.add(line);
				
				if(count == 4)
				{
					break;
				}
				count ++;
			}
			
			
		}
		catch (IOException e)
		{
			System.err.println("not exist this user, check the file name you given");
		}
		
		adjMatrix = new LinkedList<User>();
		this.adjMatrix = connect;
	}
	
	private ArrayList getMessID(String rawData)
	{
		return str_split(rawData, "mess");
	}
	
	private ArrayList getDate(String rawData)
	{
		return str_split(rawData, "date");
	}
	
	private ArrayList getSentID(String rawData)
	{
		return str_split(rawData, "sent");
	}
	
	private ArrayList getRecvID(String rawData)
	{
		return str_split(rawData, "recive");
	}
	
	private ArrayList<String> str_split(String rawData, String type)
	{
		StringTokenizer st = null;
		String patternStr = null;
		String infor = null;
		ArrayList<String> content = new ArrayList<String>();
		switch(type){
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
	
	public void updateAdj(User a)
	{
		adjMatrix.push(a);
	}
	
	public void topology()
	{
		//bfs here
	}
	
}
