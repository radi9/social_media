import java.util.LinkedList;

public class main 
{

	public static double find_min(double data[])
	{
		double min = 100000;
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] < min)
			{
				min = data[i];
			}
		}
		if(min == 0)
		{
			min = 1;
		}
		System.out.println("min " + min);
		return min;
	}
	
	public static double find_max(double data[])
	{
		double max = -100000;
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] > max)
			{
				max = data[i];
			}
		}
		if(max == 0)
		{
			max = 1;
		}
		System.out.println("max " + max);
		return max;
	}
	
	public static void change(Graph social_network)
	{
		
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		//give the root path, and list all file under this directionary
		String root_path = "/Users/red/ML_DM data/enron/maildir";
		FileProcess dataSet = new FileProcess(root_path);
		String[] userIndex = dataSet.getRootDir();//150 folder, 150 user
		String[] user_contenType = dataSet.getDir(userIndex[0]);
		
		String[] filesIndex = dataSet.getFiles(userIndex[0], user_contenType[4]);
		
		
		Graph social_network = new Graph(root_path);

//		User user = social_network.getUser(1);
//		
//		System.out.println(user.mailList.size());
//		System.out.println(user.adjMatrix.size());
		Comput com = new Comput();
		//lsb

		//double[] test = com.get_AvgResTime(user);
//		System.out.println("NOM: "+com.get_NumberOfEmails(user));// Y
//		//System.out.println(test[1]);
//		System.out.println(com.get_resScore(user));
//		System.out.println("DC: "+com.get_degCen(user)); // Y	
		com.comput_hub_ath(social_network.users); // Y
//		System.out.println("connScore: "+user.connScore);// Y
//		
//		System.out.println("cluster: " +com.get_clustCoef(user));
		//com.comput_Dist(user);
//		System.out.println(com.get_avg_Dist(user));//nan
//		System.out.println("between: " +com.get_betCen(user)); //nan
		
		//normalized
		double[] user_mails = new double[social_network.users.size()];
		double[] user_deg = new double[social_network.users.size()];
		double[] user_conn = new double[social_network.users.size()];
		double[] user_cluse = new double[social_network.users.size()];
		double[] user_resTime = new double[social_network.users.size()];
		double[] user_resScore = new double[social_network.users.size()];
		
		for(int i = 0; i < social_network.users.size(); i++)
		{
			user_mails[i] = com.get_NumberOfEmails(social_network.users.get(i));
			user_deg[i] = com.get_degCen(social_network.users.get(i));
			user_conn[i] = social_network.users.get(i).connScore;
			user_cluse[i] = com.get_clustCoef(social_network.users.get(i));
			user_resTime[i] = com.get_AvgResTime(social_network.users.get(i));
			user_resScore[i] = com.get_resScore(social_network.users.get(i));
		}
		
		double mail_max = find_max(user_mails);
		double deg_max = find_max(user_deg);
		double conn_max = find_max(user_conn);
		double cluse_max = find_max(user_cluse);
		double res_max = find_max(user_resTime);
		
		
		double mail_min = find_min(user_mails);
		double deg_min = find_min(user_deg);
		double conn_min = find_min(user_conn);
		double cluse_min = find_min(user_cluse);
		double res_min = find_min(user_resTime);
		
		for(User _user : social_network.users)
		{
			_user.socialScore = com.getSocialScore(_user, social_network.users, mail_max, mail_min, deg_max, deg_min,
					cluse_max, cluse_min, conn_max, conn_min);
		}
		
		Rank rank = new Rank();
		
		//social_network.transition();
		
		//rank.setdata(0, social_network.users.get(3), 2);
		for(User u: social_network.users){
			u.Rank = com.rank(u, social_network.users);
		}
		
	    
		for(User u: social_network.users)
		{
			if(u.Rank<10)
			{
				//com.normalize_numMail(, user, mail_max, mail_min)
				//rank.setdata(0, u, u.Rank);
				double nor_mail = 0.0;
				double nor_DC = 0.0;
				double nor_cluse = 0.0;
				double nor_aub = 0.0;
				double nor_res = 0.0;
				u.avg_resTime = user_resTime[u.id];
				u.connScore = user_conn[u.id];
				u.degCen = user_deg[u.id];
				u.clustCoef = user_cluse[u.id];
				nor_mail = com.normalize_numMail(u.mailList.size(), social_network.users, mail_max, mail_min);
				nor_DC = com.normalizeDC(u.degCen, social_network.users, deg_max, deg_min);
				nor_cluse = com.normalizeClusterCoeff(u.clustCoef, social_network.users, cluse_max, cluse_min);
				nor_aub = com.normalizeHubsAth(u.connScore, social_network.users, cluse_max, cluse_min);
				nor_res = com.normalizeRes(u.avg_resTime, social_network.users, res_max, res_min);
				System.out.print("6 "+u.id + " " + u.Rank + " " + u.name + " " + nor_mail+
						" " + nor_res);
				
				System.out.print(" " + u.degCen + " " + nor_aub);
				System.out.print(" " + nor_cluse + " " + u.socialScore + " ");
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		//com.get_degCen(user);
		
		
	}

}
