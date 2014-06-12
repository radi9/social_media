package social_media;

import java.util.LinkedList;

/*
 * each user node got the flowing 11 attributes:
 * 
 */
public class User {
	
	public int id;
	public int inMail;//in degree
	public int outMail;//out degree
	public LinkedList<User> adjMatrix;
	public double avg_resTime;
	public double resScore;
	public double meanDist;//mean distance to all pair in itself networking
	public double clustCoef;
	public double cliq_sizeScore;//same clique same score
	public double cliq_timeScore;
	public double degCen;
	public double betCen;
	public double connScore;//hubs and authorities, depend indegree and outdegree
	public int numMail = inMail + outMail;
	
	public User(LinkedList<User> connect)
	{
		adjMatrix = new LinkedList<User>();
		this.adjMatrix = connect;
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
