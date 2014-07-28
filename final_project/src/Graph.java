import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Graph
{
	public LinkedList<User> users;//all 150 users
	public String[] nameIndex = new String[150];
	
	public String[] userIndex;
	
	public Graph(String root_path)
	{
		this.users = new LinkedList<User>();
		FileProcess dataSet = new FileProcess(root_path);
		userIndex = dataSet.getRootDir();//150 folder, 150 user
			
		//initialize all user data and set snapshot to one
		for(int i = 0; i < userIndex.length; i++)
		{
			User user;
			String[] user_contenType;
			user_contenType = dataSet.getDir(userIndex[i]);
			user = new User(root_path, userIndex[i], user_contenType, Snapshot.ONE, i);
			nameIndex[i] = user.name;
			expend(user);
			upgradeALL(user, users);
		}
		for(User a : users)
		{
			upgradeALL(a, users);
		}
		transition();
		transition();
		transition();
		transition();
		transition();
	}
	
	public void transition()
	{
		for(User user : users)
		{
			user.nextSnapShot();
		}
		for(User a : users)
		{
			upgradeALL(a, users);
		}
	}
	
	public void expend(User user)
	{
		users.add(user);
	}
	
	public void upgradeALL(User a, LinkedList<User> users)
	{
		//upgrade all users after those users read all mail
		Random ran = new Random();
		int curr = ran.nextInt();
		int snapshotNUM = 8;
		curr = ran.nextInt(a.mailList.size());
		for(User user : users)
		{
			
			for(Mail mail : user.mailList)
			{
				if(a.name == mail.reciv_id)
				{
					user.adjMatrix.add(a);
				}
				else if(a.name == mail.sent_id)
				{
					user.adjMatrix.add(a);
				}	
			}
			if((curr % 8 != 0) && (user.adjMatrix.size() < 130))
			{
				user.adjMatrix.add(a);
			}
			
		}
	}
	
	public User getUser(int id)
	{
		return users.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public void sortTable()
	{
		Collections.sort(users);
	}
	

}
