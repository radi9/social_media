import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
public class Comput {
	
	double data []=new double[10];//存pairNum,avg_resTime
	
	
	public double get_NumberOfEmails(User a)
	{
		int NumberOfEmails = 0;
		// 計算這個使用者a所有mail數
		NumberOfEmails = a.mailList.size();
		// 標準化? 介於0~1?
		return NumberOfEmails;
	}
	public double get_AvgResTime(User a)
	{
		double avg_resTime = 0;		//平均回復時間
		long resTime = 0;			//id相同的out時間-in時間
		double total_resTime = 0;	//res_time加總
		int pairNum = 0;			//總共有幾組resTime
		boolean check[] = new boolean[a.inMail.size()];	//檢查inMail中相同id的信有沒有被減過
		double data[] = new double[2];
		int count = 0;
		for( Mail outmail : a.outMail )
		{
			for(Mail inmail : a.inMail )
			{
//				if(outmail.id==inmail.id && check[a.inMail.indexOf(inmail)]!=true)//outMail中的信的id==inMail的信的id，且在inMail中沒有被算過
//				{
					if(outmail.sent_time != null || inmail.reciv_time != null)
					{
						if(inmail.sent_time == null)
						{
							resTime += inmail.reciv_time.getTime();
							pairNum++;
						}
						else if(outmail.sent_time == null)
						{
							resTime -= outmail.reciv_time.getTime();
							pairNum++;
						}
						
						
					}
//				}
//				break;
				count ++;
			}
			resTime = resTime/2;
			resTime = resTime *3600 * 24;
			total_resTime += resTime;
		}
		
		avg_resTime = total_resTime/count;
		
		data[0]=pairNum;
		data[1]=avg_resTime;
		
		return  avg_resTime;
		
/*	
		for(int i=0;i<a.outMail.size();i++)
		{
			Mail out = a.outMail.get(i);
			for(int j=0;j<a.inMail.size();j++)
			{
				Mail in = a.inMail.get(j);
				if (out.id == in.id && check[j]!=true)		//outMail中的信的id==inMail的信的id，且在inMail中沒有被算過
				{
					resTime = out.reciv_time-in.reciv_time;
					check[j]=true;
					pairNum++;
				}
			}
			total_resTime = total_resTime + resTime;
		}
		avg_resTime = total_resTime/pairNum;
		
		return avg_resTime;
*/
	}
	
	public double get_resScore(User a)
	{
		return (data[0]/a.outMail.size())/data[1];	
		//reply mail/outmail總和*(1/AvgResTime)
		//reply mail=有去有回
		//outmail總和=寄出的mail
		//(1/AvgResTime)=讓時間與score成反比
	}
	
	private int get_Dist(User a,User b)
	{
		// 回傳 a 到  b 的距離	 ,到不了回傳 -1	
		return a.visit[b.id];
		
	}
	private ArrayList<ArrayList<Integer>> get_Path(User a,User b)
	{
		//回傳 a 到  b 的路徑 ,可能含多條路徑
		ArrayList<ArrayList<Integer>> paths = new 	ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		// 先知道深度
		int deep = a.visit[b.id];
		if(deep==-1)return null;
		
		for(int i = 0 ;i<a.qu.size();i++){
			if(deep < a.deep.get(i)){
				return paths;
			}
			// 深度  ID 都要符合
			if(a.deep.get(i)==deep && a.qu.get(i).id == b.id){
				// 先清空
				path.clear();
				path.add(b.id);
				int p=i;
				// 找parent並記錄
				while(a.parent.get(p)!=-1){	
					p=a.parent.get(p);
					path.add(a.qu.get(p).id);					
				}
				path.add(a.id);
				// 反向
				Collections.reverse(path);
				// 紀錄
				paths.add(path);	
			}
		}	
		return null;
	}
	
	
	private double get_c_in_Path(User a,User b,User c)
	{
		
		// 功能: 計算 c 在 a b 兩點最短路徑上的比例 
		
		// 不包含ab =c  ,a = b 兩種情形
		if(a.id == c.id ||b.id ==c.id || a.id ==b.id){
			return 0;
		}		
		// 先知道深度
		int deep = this.get_Dist(a, b);
		// a b最短路徑 總數
		double total= 0;
		// a b最短路徑  包含 c 點
		double count =0;
		
		for(int i = 0 ;i<a.qu.size();i++){
			if(deep < a.deep.get(i)){
				break;
			}
			// 深度  ID 都要符合
			if(a.deep.get(i)==deep && a.qu.get(i).id == b.id){
				// a b 路徑數+1
				total++;
				int p=i;
				// 檢查 c 是否在路徑上
				while(a.parent.get(p)!=-1){	
					p=a.parent.get(p);				
					if(a.qu.get(p).id==c.id){
						// 包含 c 
						count++;
						break;
					} 
				}			
			}
		}	
		return count/total;
	}
	
	
	public void comput_Dist(User a)
	{
		//---implement the dijstra here ?? or floyd
		//---計算到 User a 到其他點的距離/路徑
		int Dist;
		// 記錄走過的點
		ArrayList<User> qu  = new ArrayList<User>();
		// parent
		ArrayList<Integer> parent = new ArrayList<Integer>();
		// 目前深度
		ArrayList<Integer> deep = new ArrayList<Integer>();
		// 紀錄拜訪過的USER
		ArrayList<User> visit_User =  new ArrayList<User>();
		int[] visit = new int[a.size];
		// 預先設定到不了 -1
		for(int i : visit){
			i = -1;
		}
		int count = 0 ;
		int now = 0;
		// 自己到自己 0
		visit[a.id] = 0;
		// 第一層 朋友
		for(int i=0;i<a.adjMatrix.size();i++)
		{
			qu.add(a.adjMatrix.get(i));
			parent.add(-1);
			deep.add(1);
			
		}
		//第一層朋友VISIT過
		for( ;now <qu.size();now ++){
			visit_User.add(a.adjMatrix.get(now));
			visit[a.adjMatrix.get(now).id] = 1;		
		}
		
		while(count<qu.size()){
			// 加入新的朋友
			for(int i=0;i<qu.get(count).adjMatrix.size();i++){
				// 沒有拜訪過
				if(visit[qu.get(count).adjMatrix.get(i).id]==-1 ){
					//加入新朋友
					qu.add(qu.get(count).adjMatrix.get(i));
					//記錄新朋友的PARENT
					parent.add(count);
					//記錄新朋友的深度
					deep.add(deep.get(count)+1);	
				}
				
			}
			//
			count++;
			//記錄拜訪過的朋友 & 深度
			if(count ==now-1){
				for( ;now <qu.size();now ++){
					if(visit[qu.get(now).id]==-1){
						visit_User.add(qu.get(now));
					}
					visit[qu.get(now).id] =deep.get(now);				
				}
								
			}				
		}
		// 複製到 user
		a.qu = qu;
		a.parent = parent;
		a.deep = deep;
		a.visit_User = visit_User;
		
	}
	
	public double get_avg_Dist(User a)
	{
		double total =0;
		double count =0;
		for(int i = 0;i<a.visit.length ;i++){
			if(a.visit[i]>0){
				total++;
				count = count+a.visit[i];
			}
		}
		
		return count/total ;
	}
	
	public double get_clustCoef(User a)
	{
		//k-mean algo, in user a on the community (specifity or for all ?? or just itself ??)
		//we'll find out
		// 計算clustercoef
		double clustCoef=0.0;
		//     clustCoef = (相連的使用者人群內)的相連數量/相連的使用者人群數量
		int count = 0;
		for (int i =0 ; i< a.adjMatrix.size(); i++){
			for(int j = i+1; j< a.adjMatrix.size();j++){
				if(check(a.adjMatrix.get(i),a.adjMatrix.get(j))){
					count++;
				}
			}		
		}	
		if(a.adjMatrix.size() != 0)
		{
			clustCoef = count/a.adjMatrix.size();
		}
		else
		{
			clustCoef = 0.001;
		}
		
		
		return clustCoef;
	}
	
	public double get_cliqScore(User a)
	{
		double sizeScore=0.0;
		return sizeScore;
	}
	
	public double get_cliqScore(User a, double resScore)
	{
		double timeScore=0.0;
		return timeScore;
	}
	
	public double get_degCen(User a)
	{
		double cenScore=0.0;
		// 一個使用者  和 其他有傳過MAIL的人數量 (計算相連邊數)  
		cenScore = a.adjMatrix.size();
		// normalize 除上總node數 
		cenScore = cenScore/a.size ;
		// 回傳
		return cenScore;
	}
	
	public double get_betCen(User a)
	{
		// 計算 between Centerlity
		double cenScore=0.0;	
		// 計算   get_c_in_Path(User a,User b,User c) 且 User a ,User b  all pair
		for(int i=0;i<a.visit_User.size();i++){
			for(int j=i+1;j<a.visit_User.size();j++){
				cenScore = cenScore + get_c_in_Path( a.visit_User.get(i) ,a.visit_User.get(j),a);		
			}
		}	
		return cenScore;
	}
	
	
	
	// 檢查 edge
	private boolean check(User one,User two){
		for(int i = 0 ;i< one.adjMatrix.size();i++){
			if(one.adjMatrix.get(i).id == two.id ){
				return true;
			}
		}
		return false;
	}
	
	public void comput_hub_ath(LinkedList<User> user){
		 
		// ArrayList<User> user  ==  所有USER的LIST (LIST可不用排序只要有記錄所有USER即可)
		// 計算完不回傳資料 自動把每一個user的connScore填滿
		// 程式進入一次即可做完所有 hubs and athorities
			
		// 所有user的LIST  計算一次  所有 hubs and athorities
		double[][] m = new double[user.size()][user.size()];
		double[] hub_ath ;
		for(int i=0;i<m.length;i++){
			for(int j=0;j<m[0].length;j++){
				m[i][j]=0;
			}
		}
		for(int i=0;i<m.length;i++){
			for(int j=0;j<user.get(i).adjMatrix.size();j++){
				m[user.get(i).id][user.get(i).adjMatrix.get(j).id]=1;			
			}
		}
		Matrix A = new Matrix(m);
		hub_ath = A.get_hub_ath();
		// user.id
		// user.connScore = hub_ath[user.id]
		for(int i=0;i<user.size();i++){
			user.get(i).connScore = hub_ath[user.get(i).id];
		}
	}
	public double normalize_numMail(double self ,LinkedList<User> user,double max,double min){		
	
		for(int i=0;i<user.size();i++){
			 if(user.get(i).mailList.size()>max){max =user.get(i).mailList.size();}
			 if(user.get(i).mailList.size()<min){min =user.get(i).mailList.size();}		
		}	
		return (self-min)/(max-min);
	}
	public  double normalizeDC(double self ,LinkedList<User> user,double max,double min){		
	
		for(int i=0;i<user.size();i++){
			 if(user.get(i).degCen>max){max =user.get(i).degCen;}
			 if(user.get(i).degCen<min){min =user.get(i).degCen;}		
		}	
		return (self-min)/(max-min);
	}
	public double normalizeClusterCoeff(double self ,LinkedList<User> user,double max,double min){		
		
		for(int i=0;i<user.size();i++){
			 if(user.get(i).clustCoef>max){max =user.get(i).clustCoef;}
			 if(user.get(i).clustCoef<min){min =user.get(i).clustCoef;}		
		}	
		return (self-min)/(max-min);
	}
	public double normalizeHubsAth(double self ,LinkedList<User> user,double max,double min){		
	
		for(int i=0;i<user.size();i++){
			 if(user.get(i).connScore>max){max =user.get(i).connScore;}
			 if(user.get(i).connScore<min){min =user.get(i).connScore;}		
		}	
		return (self-min)/(max-min);
	}
	public double normalizeRes(double self ,LinkedList<User> user,double max,double min){		
		
		for(int i=0;i<user.size();i++){
			 if(user.get(i).avg_resTime>max){max =user.get(i).avg_resTime;}
			 if(user.get(i).avg_resTime<min){min =user.get(i).avg_resTime;}		
		}	
		return (self-min)/(max-min);
	}
	public double getSocialScore(User a, LinkedList<User> user,double max,double min
			,double max2,double min2 ,double max3,double min3 ,double max4,double min4){
		// 放進 使用者 + 所有USER LIST
		double total_w =0;
		double total_w_c =0;
		
		total_w = a.mailList.size()+a.degCen+a.clustCoef+a.connScore;
		total_w_c = a.mailList.size()* normalize_numMail(a.mailList.size(),user,max,min)
				+ a.degCen* normalizeDC(a.degCen,user,max2,min2)
				+ a.clustCoef* normalizeDC(a.clustCoef,user,max3,min3)
				+ a.connScore* normalizeDC(a.connScore,user,max4,min4)
				;
		return  100*(total_w_c/total_w);		
	}
	public int rank(User u,LinkedList<User> user)
	{
		LinkedList<Double> sort_score = new LinkedList<Double>(); 
		int c =0;
		for(int i =0;i<user.size();i++){
			if(u.socialScore< user.get(i).socialScore) c++;
		}
		return c;
	}
	
}