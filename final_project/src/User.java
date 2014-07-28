import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	ONE("1-1-1999", "30-6-1999" ,"ONE"), TWO("1-7-1999", "31-12-1999", "TWO"), THREE("1-1-2000","30-6-2000", "THREE"), FOUR("1-7-2000", "31-12-2000", "FOUR"),
	FIVE("1-1-2001", "30-6-2001", "FIVE"), SIX("1-7-2001", "31-12-2001", "SIX"), SEVER("1-1-2002", "30-6-2002", "SEVER"), EIGHT("1-7-2002", "31-12-2002", "EIGHT");
	//not design six or eight snapshots
	
	public String name;//toString
	
	public Date low_bound;
	public Date up_bound;
	
	public LinkedList<Mail> mails = new LinkedList<Mail>();//mail list
	public LinkedList<Mail> in_mails = new LinkedList<Mail>();//mail list
	public LinkedList<Mail> out_mails = new LinkedList<Mail>();//mail list
	public LinkedList<User> users = new LinkedList<User>();//user record
	
	Snapshot(String low_boundStr, String up_boundStr, String name)
	{
		this.name = name;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		try 
		{
			low_bound = sdf.parse(low_boundStr);
			up_bound = sdf.parse(up_boundStr);
		} catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Snapshot next()
	{
		switch(this)
		{
			case ONE:
				return Snapshot.TWO;
			case TWO:
				return Snapshot.THREE;
			case THREE:
				return Snapshot.FOUR;
			case FOUR:
				return Snapshot.FIVE;
			case FIVE:
				return Snapshot.SIX;
			case SIX:
				return Snapshot.SEVER;
			case SEVER:
				return Snapshot.EIGHT;
			case EIGHT:
				return Snapshot.ONE;
		}
		return null;
	}
	
	public void upgrade(Mail mail)
	{
		if(mail.reciv_id == null)
		{
			out_mails.add(mail);
		}
		else if(mail.sent_id == null)
		{
			in_mails.add(mail);
		}
		else
		{
			out_mails.add(mail);
			in_mails.add(mail);
		}
		mails.add(mail);
//		if(!(in(user)))
//		{
//			users.add(user);
//		}
	}
	
	public boolean in(User a)
	{
		for(User user : users)
		{
			if(user.id == a.id)
			{
				return true;
			}
		}
		return false;
	}
	
	public LinkedList<User> get_userCotent()
	{
		return users;
	}
	
	public LinkedList<Mail> get_mailCotent()
	{
		return mails;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	
}

public class User implements Comparable
{
	
	public boolean comput_path_deep = false; 
	public ArrayList<User> qu  = new ArrayList<User>();  // 拜訪順序         // qu,parent,deep 是在一起使用的
	public ArrayList<Integer> parent = new ArrayList<Integer>(); // 路徑
	public ArrayList<Integer> deep = new ArrayList<Integer>();  //目前深度
	public ArrayList<User> visit_User =  new ArrayList<User>(); //紀錄可以拜訪到的USER
	public int size = 150; //所有使用者數量
	public int[] visit = new int[size];	// a.visit[id] = 記錄user a到 某id的距離, 無法到達=-1 ,自己 =0 
	public ArrayList<User> toatl_user =  new ArrayList<User>(); 
	// 所有使用者  toatl_user.get(1) = 拿到id=1的使用者
	
	public int id;
	public LinkedList<Mail> inMail;//in degree
	public LinkedList<Mail> outMail;//out degree
	
	public LinkedList<User> adjMatrix;//user connect records
	public LinkedList<Mail> mailList;//mail records
	
	public ArrayList<String> userIndex;
	
	public double avg_resTime;
	public double resScore;
	public double meanDist;//mean distance to all pair in itself networking
	public double clustCoef;
	public double cliq_sizeScore;//same clique same score
	public double cliq_timeScore;
	public double degCen;
	public double betCen;
	public double connScore;//hubs and authorities, depend indegree and outdegree
	
	public double socialScore;
	public int Rank ;
	
	private Snapshot status;
	private Snapshot[] status_set = {Snapshot.ONE, Snapshot.TWO, Snapshot.THREE, Snapshot.FOUR, Snapshot.FIVE,
			Snapshot.SIX, Snapshot.SEVER, Snapshot.EIGHT};
	
	public String userName;//related to folder name, not email address
	public String name;
	
	public User(String rootPath, String userName, String[] contenType, Snapshot status, int id)
	{
		this.id = id;
		this.inMail = new LinkedList<Mail>();
		this.outMail = new LinkedList<Mail>();
		this.adjMatrix = new LinkedList<User>();
		this.mailList = new LinkedList<Mail>();
		//set snapshot status;
		this.status = status;
		
		//load data, rootDir is user folder
		FileProcess file_process = new FileProcess(rootPath);
		
		
		String inbox_fName = null;
		String sentMail_fName = null;//use first match only

		Pattern pattern;
		Matcher matcher;
		boolean matchFound;
		
		for(String str : contenType)
		{
			if(str != null)
			{
				pattern = Pattern.compile("inbox");
				matcher = pattern.matcher(str);
				matchFound = matcher.find();
				if(matchFound)
				{
					inbox_fName = str;
				}
			}
		}
		
		for(String str : contenType)
		{
			if(str != null)
			{
				pattern = Pattern.compile("sent");
				matcher = pattern.matcher(str);
				matchFound = matcher.find();
				if(matchFound)
				{
					sentMail_fName = str;
				}
			}
		}
		
		String[] inMailName = null;
		String[] outMailName = null;
		if(inbox_fName != null)
		{
			inMailName = file_process.getFiles(userName, inbox_fName);
			for(int i = 0; i < inMailName.length; i++)
			{
				if(i == 800)
				{
					break;
				}
				if(inMailName[i] != null)
				{
					Mail mail = file_process.getMailContent(rootPath+"//"+userName+"//"+inbox_fName, inMailName[i]);
					name = mail.reciv_id;
					mailList.push(mail);
					
					if(mail.sent_id == null)
					{
						inMail.push(mail);//no reply
					}
					else
					{
						outMail.push(mail);
						inMail.push(mail);
					}
					switch(mail.status)
					{
						case "ONE":
							status_set[0].upgrade(mail);
						case "TWO":
							status_set[1].upgrade(mail);
						case "THREE":
							status_set[2].upgrade(mail);
						case "FOUR":
							status_set[3].upgrade(mail);
						case "FIVE":
							status_set[4].upgrade(mail);
						case "SIX":
							status_set[5].upgrade(mail);
						case "SEVER":
							status_set[6].upgrade(mail);
						case "EIGHT":
							status_set[7].upgrade(mail);
					}
				}
			}
		}
		
		if(sentMail_fName != null)
		{
			outMailName = file_process.getFiles(userName, sentMail_fName);
			for(int i = 0; i < outMailName.length; i++)
			{
				if(i == 800)
				{
					break;
				}
				if(outMailName[i] != null)
				{
					Mail mail = file_process.getMailContent(rootPath+"//"+userName+"//"+sentMail_fName, outMailName[i]);
					mailList.push(mail);
					name = mail.sent_id;
					
					if(mail.reciv_id == null)
					{
						outMail.push(mail);//no reply
					}
					else
					{
						outMail.push(mail);
						inMail.push(mail);
					}
					switch(mail.status)
					{
						case "ONE":
							status_set[0].upgrade(mail);
						case "TWO":
							status_set[1].upgrade(mail);
						case "THREE":
							status_set[2].upgrade(mail);
						case "FOUR":
							status_set[3].upgrade(mail);
						case "FIVE":
							status_set[4].upgrade(mail);
						case "SIX":
							status_set[5].upgrade(mail);
						case "SEVER":
							status_set[6].upgrade(mail);
						case "EIGHT":
							status_set[7].upgrade(mail);
					}		
				}
			}
		}
	}

	public void updateAdj(User a)
	{
		adjMatrix.push(a);
	}
	
	public void upgradeAdj(int userID, Mail mail)
	{
		int target;
		int count = 0;
		for(User user : adjMatrix)
		{
			if(user.id == userID)
			{
				target = count;
			}
			count ++;
		}
		
	}
	
	public Snapshot nextSnapShot()
	{	
		switch(this.status)
		{
			case ONE:
				merge(status_set[1].in_mails, status_set[1].out_mails, status_set[1].mails);
				return status_set[1];
			case TWO:
				merge(status_set[2].in_mails, status_set[2].out_mails, status_set[2].mails);
				return status_set[2];
			case THREE:
				merge(status_set[3].in_mails, status_set[3].out_mails, status_set[3].mails);
				return status_set[3];
			case FOUR:
				merge(status_set[4].in_mails, status_set[4].out_mails, status_set[4].mails);
				return status_set[4];
			case FIVE:
				merge(status_set[5].in_mails, status_set[5].out_mails, status_set[5].mails);
				return status_set[5];
			case SIX:
				merge(status_set[6].in_mails, status_set[6].out_mails, status_set[6].mails);
				return status_set[6];
			case SEVER:
				merge(status_set[7].in_mails, status_set[7].out_mails, status_set[7].mails);
				return status_set[7];
		}
		return null;
	}
	
	private void merge(LinkedList<Mail> in_mail, LinkedList<Mail> out_mail, LinkedList<Mail> mails)
	{
		inMail.addAll(in_mail);
		outMail.addAll(out_mail);
		mailList.addAll(mails);
	}
	
	@Override
	public int compareTo(Object o) 
	{
		// TODO Auto-generated method stub
		User other = (User) o;
		return other.id - this.id;
	}
	
}
