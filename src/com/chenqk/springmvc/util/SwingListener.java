package com.chenqk.springmvc.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

public class SwingListener extends javax.swing.JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2247993805920738178L;
	private JLabel jLabel;
	private JTextField jTextField;
	private JButton jButton;
	private JTextArea jTextArea;
	private UDPReceiver ur = UDPReceiver.getInstance();
	
	private List<List<LocationVo>> stationList = new ArrayList<List<LocationVo>>();
	
	public SwingListener(){
		   super(); 
		   this.setSize(800, 600); 
		   this.getContentPane().setLayout(null); 
		   this.add(getJLabel(), null); 
		   this.add(getJTextField(), null); 
		   this.add(getJButton(), null);
		   this.add(getJTextArea(),null);
		   this.setTitle("监听事件");
		   //监听事件    匿名内部类
		   jButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				 System.out.println("你单击了按钮");
				 System.out.println(jTextField.getText());
				 stationList = ur.receiveToCache(jTextField.getText());
				 int numTemp =0;
				for (int i = 0; i < stationList.size(); i++) {
					for (int j = 0; j < stationList.get(i).size(); j++) {
						System.out.println("解析udp之后的数据="+stationList.get(i).get(j).getRSSI()+","+stationList.get(i).get(j).getCollTime());
						
					}
					numTemp += stationList.get(i).size();
				}
				 
				 String [] rowKeys = new String [stationList.size()];// 定义生成线    
				 String []  colKeys = new String [numTemp];// 时间X轴坐标
				 double [][] data = new double[stationList.size()][];// 数据
				 
				
				 int num = 0;
				// 遍历生成 坐标数据 
				for (int i = 0; i < stationList.size(); i++) {
					List<LocationVo>locationVoList =  stationList.get(i);
					
					for (int j = 0; j < locationVoList.size(); j++) {
						colKeys[num] = locationVoList.get(j).getCollTime();//将时间值添加到数组中
						num++;
					}
					rowKeys[i] = locationVoList.get(0).getApMac();
				}   
				
				
				// 遍历生成坐标
				for (int i = 0; i < stationList.size(); i++) {
					double[] dataTemp = new double[numTemp];// 每个udp报文的rssi值
					List<LocationVo>locationVoList =  stationList.get(i);// 一次取得存放udp的集合
					String startTemp = locationVoList.get(0).getCollTime();// 获取每个udp的接受其实时间
					String endTemp = locationVoList.get(locationVoList.size()-1).getCollTime();// 获取每个udp的结束时间
					int startNum = 0;// 定义开始时间下标
					int endNum = 0;// 定义结束时间下标
					// 取开始和结束时间
					for (int j = 0; j < colKeys.length; j++) {
						if(colKeys[j].equals(startTemp)){
							startNum = j;
						}
						if(colKeys[j].equals(endTemp)){
							endNum = j;
						}
						
					// 获取每个udp在时间轴上所对应的数据	
					for (int j2 = 0; j2 <colKeys.length; j2++) {
						
						if(j2<startNum){
							dataTemp[j2] = 0;
						}else if(j2 >= startNum && j2 <= endNum){
							dataTemp[j2] = locationVoList.get(j2-startNum).getRSSI();
						}else if(j2 > endNum){
							dataTemp[j2] = 0;
						}
					}	
					}
					data[i] = dataTemp;
				}
				//遍历生成数量坐标
				
				 //步骤1：创建CategoryDataset对象（准备数据）     
		        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);    
		        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置     
		        JFreeChart freeChart = createChart(dataset);     
		        //步骤3：将JFreeChart对象输出到文件，Servlet输出流等     
		        saveAsFile(freeChart, "F:\\jfreechart\\line.png", 600, 400); 
			}
		});
		
		   
		
	}
	
	 /**    
     * 创建CategoryDataset对象    
     *     
     */    
    public static CategoryDataset createDataset(int[]colKeys,double[][]data) {     
    
        //String[] rowKeys = { "One", "Two", "Three" ,"four","five"};     
        
    
        // 或者使用类似以下代码     
        // DefaultCategoryDataset categoryDataset = new     
        // DefaultCategoryDataset();     
        // categoryDataset.addValue(10, "rowKey", "colKey");     
    
       // return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);     
        return null;
    }     

	
    // 根据CategoryDataset创建JFreeChart对象     
    public static JFreeChart createChart(CategoryDataset categoryDataset) {     
        // 创建JFreeChart对象：ChartFactory.createLineChart     
        JFreeChart jfreechart = ChartFactory.createLineChart("Line Chart Demo", // 标题     
                "时间", // categoryAxisLabel （category轴，横轴，X轴标签）     
                "数量", // valueAxisLabel（value轴，纵轴，Y轴的标签）     
                categoryDataset, // dataset     
                PlotOrientation.VERTICAL, true, // legend     
                false, // tooltips     
                false); // URLs     
    
        // 使用CategoryPlot设置各种参数。以下设置可以省略。     
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();     
        // 背景色 透明度     
        plot.setBackgroundAlpha(0.5f);     
        // 前景色 透明度     
        plot.setForegroundAlpha(0.5f);     
        // 其他设置 参考 CategoryPlot类     
    
        return jfreechart;     
    }     
    
    // 保存为文件     
    public static void saveAsFile(JFreeChart chart, String outputPath,     
            int weight, int height) {     
        FileOutputStream out = null;     
        try {     
            File outFile = new File(outputPath);     
            if (!outFile.getParentFile().exists()) {     
                outFile.getParentFile().mkdirs();     
            }     
            out = new FileOutputStream(outputPath);     
            // 保存为PNG     
            ChartUtilities.writeChartAsPNG(out, chart, weight, height);     
            // 保存为JPEG     
            // ChartUtilities.writeChartAsJPEG(out, chart, weight, height);     
            out.flush();     
        } catch (FileNotFoundException e) {     
            e.printStackTrace();     
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            if (out != null) {     
                try {     
                    out.close();     
                } catch (IOException e) {     
                }     
            }     
        }     
    }     
    
	private javax.swing.JTextArea getJTextArea() {
		if(jTextArea == null){
			jTextArea = new JTextArea();
			jTextArea.setBounds(40, 80, 500, 400);
		}
		return jTextArea;
	}
	
	private javax.swing.JButton getJButton() {
		if(jButton == null){
			jButton = new JButton();
			jButton.setBounds(260,50, 71, 18);
			jButton.setText("监听");
		}
		return jButton;
	}
	
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null){
			jTextField = new JTextField();
			jTextField.setBounds(100,50, 160, 18);
		}
		return jTextField;
	}
	
	private javax.swing.JLabel getJLabel() {
		if(jLabel==null){
			jLabel = new JLabel();
			jLabel.setBounds(40, 50, 60, 18);
			jLabel.setText("STA：");
		}
		return jLabel;
	}
	/**
	 * @param args
	 */
	 public static void main(String[] args) {  
		 SwingListener listener = new SwingListener();
		 listener.setVisible(true); 
	    }

}
