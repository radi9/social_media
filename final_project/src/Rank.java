import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.category.DefaultCategoryDataset;

class Rank extends JFrame implements ActionListener {
	private String [][] tmpTableData;
    private String [] BookField;
    private DefaultTableModel tmodel;      
    private JTable book; //�إ�JTable
    JScrollPane jp ;
    String [][][] snapshot =new String[8][10][9] ;
	JButton[] but =new JButton[8];
	public static DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
	
	Rank(){
		super("�ƦW��");
		setLayout(null);
		this.setSize(1218,700);
		this.setVisible(true);
		for(int i=0;i<8;i++){
			but[i] = new JButton("SnapShot"+i);
			but[i].setSize(1200/8, 50);
			but[i].setLocation(i*1200/8,0);
			but[i].addActionListener(this);
			this.add(but[i]);		
			
		}
		String [][] s =new String[snapshot[0].length][snapshot[0][0].length];
		tmpTableData = s;
        //BookField = new String[]{"�ƦW","name","#Email","Avg_Time","ResponScore","#Cliques","RCS","WCS","Degree","Betweenness","Hubs","AvgDistance","ClusterCoeff","SocialScore"};
		BookField = new String[]{"�ƦW","name","#Email","Avg_resTime","Degree","Hubs_Authorities","ClusterCoeff","SocialScore"};
		tmodel = new DefaultTableModel(tmpTableData,BookField); //�إߪ��		
        book = new JTable(tmodel); //�إ�JTable
        book.setRowHeight(40);
        jp = new JScrollPane(book);
        jp.setSize(1200, 1000);
        jp.setLocation(0, 50);
        //jp.setVisible();
		this.add(jp);
			
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		Object src = evt.getSource();
		
	    if (src == but[0]) {read(0);
	    }else if (src == but[1]) {read(1);   
	    }else if (src == but[2]) {read(2); 
	    }else if (src == but[3]) {read(3);
	    }else if (src == but[4]) {read(4);
	    }else if (src == but[5]) {read(5);
	    }else if (src == but[6]) {read(6);
	    }else if (src == but[7]) {read(7);
	    }

	}	
	
	public void read(int snap){
		String [][] s =new String[snapshot[0].length][snapshot[0][0].length];
		for(int i = 0;i <snapshot[0].length;i++ ){
			for(int j =0;j<snapshot[0][0].length;j++){
				
				s[i][j]=snapshot[snap][i][j];
				
			}			
		}	
		tmpTableData = s;
        //BookField = new String[]{"�ƦW","name","#Email","Avg_Time","ResponScore","#Cliques","RCS","WCS","Degree","Betweenness","Hubs","AvgDistance","ClusterCoeff","SocialScore"};
		BookField = new String[]{"�ƦW","name","#Email","Avg_resTime","Degree","Hubs_Authorities","ClusterCoeff","SocialScore"};
		tmodel = new DefaultTableModel(tmpTableData,BookField); //�إߪ��	
        book = new JTable(tmodel); //�إ�JTable
        book.setRowHeight(40);
        jp = new JScrollPane(book);
        jp.setSize(1200, 1000);
        jp.setLocation(0, 50);
        //jp.setVisible();
		this.add(jp);
	}
	public void setdata(int snap,User user,int rank){
		//"�ƦW","name","#Email","Avg_Time","ResponScore",
		//"#Cliques","RCS","WCS","Degree","Betweenness",
		// "Hubs","AvgDistance","ClusterCoeff","SocialScore"
		snapshot[snap][rank][0] = String.valueOf(rank);
		snapshot[snap][rank][1] = String.valueOf(user.id);
		snapshot[snap][rank][2] = String.valueOf(user.mailList.size());
		snapshot[snap][rank][3] = String.valueOf(user.avg_resTime);
		snapshot[snap][rank][4] = String.valueOf(user.resScore);
		//snapshot[snap][rank][5]	= //"#Cliques"								
		//snapshot[snap][rank][6] = String.valueOf(user.cliq_sizeScore);
		//snapshot[snap][rank][7] = String.valueOf(user.cliq_timeScore);
		snapshot[snap][rank][5] = String.valueOf(user.degCen);
		//snapshot[snap][rank][5]	= String.valueOf(user.betCen);
		snapshot[snap][rank][6] = String.valueOf(user.connScore);
		//snapshot[snap][rank][11] = String.valueOf(user.meanDist);
		snapshot[snap][rank][7] = String.valueOf(user.clustCoef);
		snapshot[snap][rank][8] = String.valueOf(user.socialScore);
		linedataset.addValue(user.socialScore,user.name,"snapshot"+snap);
		
	}

	//public static void main(String[] args) {
		// �ƦW��
	//	Rank a = new Rank();
		
	//	demo b = new demo("�ƦW���u��",linedataset);
    //}
}