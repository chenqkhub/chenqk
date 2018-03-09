package com.chenqk.springmvc.util;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;


public class JfreeChartDemo extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -369817049040127985L;
	private TimeSeries series;
	private double lastValue = 100.0;

	/**
	 * 构造
	 */
	public JfreeChartDemo() {
		getContentPane().setBackground(Color.green);
	}

	/**
	 * 创建应用程序界面
	 */
	public void createUI() {
		
		this.series = new TimeSeries("Math.random()-随机数据", Millisecond.class);
		TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
		ChartPanel chartPanel = new ChartPanel(createChart(dataset));
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		add(chartPanel);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * 根据结果集构造JFreechart报表对象
	 * 
	 * @param dataset
	 * @return
	 */
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart result = ChartFactory.createTimeSeriesChart("Swing动态折线图",
				"系统当前时间", "动态数值变化", dataset, true, true, false);
		XYPlot plot = (XYPlot) result.getPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(60000.0);
		axis = plot.getRangeAxis();
		axis.setRange(0.0, 200.0);
		return result;
	}

	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * 动态运行
	 */
	public void dynamicRun() {
		Map<String,Map<String, Integer>> mapInfo = new HashMap<String,Map<String, Integer>>()  ;
		int numFlag = 0;
		List<LocationVo> locationVo = DBUtil.getAllStaMac();
		String collTime = locationVo.get(0).getCollTime();
		List<LocationVo> locationVo1 = DBUtil.getAllStaMacByTime(collTime);
		Map<String,Integer> vo = new HashMap<String,Integer>();
		for (int i = 0; i < locationVo1.size(); i++) {
			numFlag++;			
			if(numFlag<6){
				//vo.put(locationVo1.get(i).getApMac(), locationVo1.get(i).getRSSI());
			}
		}
		mapInfo.put(collTime,vo);
		
		while (true) {
			//List<LocationVo> LocationVo = DBUtil.getAllStaMac();
			double factor = 0.98+0.745*Math.random();
			this.lastValue =  this.lastValue*factor;
			Millisecond now = new Millisecond();
			this.series.add(now, this.lastValue);
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 主函数入口
	public static void main(String[] args) {
		JfreeChartDemo jsdChart = new JfreeChartDemo();
		jsdChart.setTitle("Swing动态折线图");
		jsdChart.createUI();
		jsdChart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jsdChart.setBounds(100, 100, 900, 600);
		jsdChart.setVisible(true);
		// Color c=new
		// Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));

		jsdChart.dynamicRun();
	}

	/**
	 * 获取map
	 */
	public static void getMapInfo(){
		
		Map<String,Map<String, Integer>> mapInfo = new HashMap<String,Map<String, Integer>>()  ;
		int numFlag = 0;
		List<LocationVo> locationVo = DBUtil.getAllStaMac();
		String collTime = locationVo.get(0).getCollTime();
		List<LocationVo> locationVo1 = DBUtil.getAllStaMacByTime(collTime);
		Map<String,Integer> vo = new HashMap<String,Integer>();
		for (int i = 0; i < locationVo1.size(); i++) {
			numFlag++;			
			if(numFlag<6){
				//vo.put(locationVo1.get(i).getApMac(), locationVo1.get(i).getRSSI());
			}
		}
		mapInfo.put(collTime,vo);		
	}
}  