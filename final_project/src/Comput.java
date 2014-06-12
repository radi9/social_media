package social_media;

public class Comput {

	public double get_AvgResTime()
	{
		double avg_resTime;
	}
	
	public double get_resScore()
	{
		double resScore;
	}
	
	public double get_Dist(User a)
	{
		//implement the dijstra here ?? or floyd
		double Dist;
	}
	
	public double get_clustCoef(User a)
	{
		//k-mean algo, in user a on the community (specifity or for all ?? or just itself ??)
		//we'll find out
		double clustCoef;
	}
	
	public double get_cliqScore(User a)
	{
		double sizeScore;
	}
	
	public double get_cliqScore(User a, double resScore)
	{
		double timeScore;
	}
	
	public double get_degCen(User a)
	{
		double cenScore;
	}
	
	public double get_betCen(User a)
	{
		double cenScore;
	}
	
	public double get_connScore(User a, int inMail, int outMail)
	{
		//in mail and out mail is necessary, and what about networking??
		double score;
	}
}
