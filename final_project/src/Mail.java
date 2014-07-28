import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Mail {
	
	public String id;
	public String sent_id;
	public String reciv_id;
	
	public int sent_year;
	
	public Date sent_time;
	public Date reciv_time;
	
	public String status;
	
	public String content;
	
	public Mail(String id, String sent_id, String reciv_id, ArrayList<String> timeList, String status)
	{
		this.id = id;
		this.sent_id = sent_id;
		this.reciv_id = reciv_id;
		this.status = status;
	}
	
	public void setRecTime(ArrayList<String> timeList)
	{
		this.reciv_time = transDate(timeList);
	}
	
	public void setSentTime(ArrayList<String> timeList)
	{
		this.sent_time = transDate(timeList);
	}
	public void set_sentMonth(String sent_month)
	{
		this.sent_id = sent_month;
	}
	
	public void set_sentYear(int sent_year)
	{
		this.sent_year = sent_year;
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
	
	//we don't use the content yet
	public void setContent(String content)
	{
		this.content = content;
	}
	
}
