import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class demo extends ApplicationFrame {
	/** * */
	private static final long serialVersionUID = 1L;

	public static DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
	
	
	public demo(String s,DefaultCategoryDataset linedataset) {
		super(s);
		setContentPane(createDemoLine(linedataset));
		this.pack();
		this.setSize(1000,700);
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
		
	}
	

	//public static void main(String[] args) {
	//	demo fjc = new demo("分析");
	//}// 生成显示图表的面板

	public static JPanel createDemoLine(DefaultCategoryDataset linedataset) {
		JFreeChart jfreechart = createChart(createDataset(linedataset));
		return new ChartPanel(jfreechart);
	}// 生成图表主对象JFreeChart

	public static JFreeChart createChart(DefaultCategoryDataset linedataset) { // 定义图表对象
		JFreeChart chart = ChartFactory.createLineChart("chart", // 折线图名称
				"time", // 横坐标名称
				"amount", // 纵坐标名称
				linedataset, // 数据
				PlotOrientation.VERTICAL, // 水平显示图像
				true, // include legend
				true, // tooltips
				false // urls
				);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinesVisible(true); // 是否显示格子线
		plot.setBackgroundAlpha(0.3f); // 设置背景透明度
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);
		return chart;
	}// 生成数据

	public static DefaultCategoryDataset createDataset(DefaultCategoryDataset linedataset) {
		
		// 各曲线名称
		String series1 = "form";
		String series2 = "dissolve";
		String series3 = "survive";
		String series4 = "spilt";
		String series5 = "merge";
		
		// 横轴名称(列名称)
		/*String type1 = "1999/1";
		String type2 = "1999/6";
		String type3 = "2000/1";
		String type4 = "2000/6";
		String type5 = "2001/1";
		String type6 = "2001/6";
		String type7 = "2002/1";
		String type8 = "2002/6";
		*/
		/*for(int i=0;i<form.length;i++){
			linedataset.addValue(form[i], series1, "snapshot"+i);
		}
		for(int i=0;i<dissolve.length;i++){
			linedataset.addValue(dissolve[i], series2, "snapshot"+i);
		}
		for(int i=0;i<survive.length;i++){
			linedataset.addValue(survive[i], series3, "snapshot"+i);
		}
		for(int i=0;i<spilt.length;i++){
			linedataset.addValue(spilt[i], series4, "snapshot"+i);
		}
		for(int i=0;i<merge.length;i++){
			linedataset.addValue(merge[i], series5, "snapshot"+i);
		}*/
		
			return linedataset;
	}
}