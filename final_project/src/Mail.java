public class Mail {
	
	int id;
	int sent_id;
	int reciv_id;
	int sent_time;
	int reciv_time;
	
	String content;
	
	public Mail(int id, int sent_id, int reciv_id, int sent_time, int reciv_time)
	{
		this.id = id;
		this.sent_id = sent_id;
		this.reciv_id = reciv_id;
		this.sent_time = sent_time;
		this.reciv_time = reciv_time;
	}
	
	//we don't use the content yet
	public void setContent(String content)
	{
		this.content = content;
	}
	
}
