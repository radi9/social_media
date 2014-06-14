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
enum Snapshot
{
	ONE(1999, "Jan", "Jun"), TWO(1999, "Jul", "Dec"), THREE(2000, "Jan", "Jun"), FOUR(2000, "Jul", "Dec"),
	FIVE(2001, "Jan", "Jun"), SIX(2002, "Jul", "Dec"), SEVER(2011, "Jan", "Jun"), EIGHT(2011, "Jul", "Dec");
	//not design six or eight snapshots
	
	public int boud_year;
	public String low_bound;
	public String up_bound;
	
	private LinkedList<Mail> mails;//users records, two dimimetion
	private LinkedList<User> users;
	
	Snapshot(int bound_year, String low_bound, String up_bound)
	{
		this.boud_year = bound_year;
		this.low_bound = low_bound;
		this.up_bound = up_bound;
	}
	
	public void upgrade(User user, Mail mail)
	{
		mails.add(mail);
	}
	
	public void upgrade(User user)
	{
		users.add(user);
	}
	
	public void upgrade(Mail mail)
	{
		mails.add(mail);
	}
	
	
}

public class User implements Comparable
{
	
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
	
	private Snapshot status;
	
	private String rootPath;
	
	public User(String userPath, LinkedList<User> connect, Snapshot status)
	{
		//not finished yet, the three case need to consider:
		//"From, To" and "From" and "To"
		
		//initialize, get the sent box and in box mail
		//consider the sent item ??
		
		//take care fo inmail and outmail, outmail : sent_mail and sent_item, inmail : in box and ...???
		
		
		//set snapshot status;
		this.status = status;
		
		//load data, rootDir is user folder
		FileProcess file_process = new FileProcess(userPath);
		String[] uesr_folders = file_process.getRootDir();//user content folders
		
		LinkedList<Mail> mails;
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(folderName));
			
			ArrayList<String> mailInfo = new ArrayList<String>();
			Mail mail;
			User user;
			
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
//	private String[] getDirct()
//	{
//		
//	}
	

	
	public void updateAdj(User a)
	{
		adjMatrix.push(a);
	}
	
	public void topology()
	{
		//bfs here
	}
	
	public void shapshot()
	{
		//eight shapshot, 2008~2011, head half year,back-ahead half year
		
	}

	@Override
	public int compareTo(Object o) 
	{
		// TODO Auto-generated method stub
		User other = (User) o;
		return other.id - this.id;
	}
	
}
