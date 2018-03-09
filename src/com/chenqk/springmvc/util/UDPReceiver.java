package com.chenqk.springmvc.util;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 定位数据接收方法类，只允许单例存在
 * 
 * @author RenLiang
 *
 */
public class UDPReceiver {
	
	//测试用,记录接收的包数
	private static int number = 0;
	
	private Queue<byte[]> queue = new ConcurrentLinkedQueue<byte[]>();

	//接收端口
	private final static int _receive_port = 10000;
		
	//接收字节数组大小
	WLDataParser wLDataParser = new WLDataParser();
	/**
	 * 单例模式
	 */
	private UDPReceiver(){
		
	}
	
	private static class SingletonReceiver {
		public final static UDPReceiver instance = new UDPReceiver();
	}

	public static UDPReceiver getInstance() {
		return SingletonReceiver.instance;
	}    
	
	/**
	 * 将接收的数据包存放到缓存中
	 * @return 
	 */
	public List<List<LocationVo>> receiveToCache(String mac){
		
		DatagramSocket socket = null;
		List<List<LocationVo>> stationList = new ArrayList<List<LocationVo>>();
		try {
			DatagramSocket ds = new DatagramSocket(11000);  //定义服务，监视端口上面的发送端口，注意不是send本身端口  
			
			byte[] buf = new byte[1024];//接受内容的大小，注意不要溢出  
			  
			DatagramPacket dp = new DatagramPacket(buf,0,buf.length);//定义一个接收的包  
			while(true){
				number++;
				
				ds.receive(dp);//将接受内容封装到包中  
				String data = new String(dp.getData(), 0, dp.getLength());//利用getData()方法取出内容
				  
				//System.out.println(data);//打印内容  
//				String ip = dp.getAddress().getHostAddress();
//				String data1 = new String(dp.getData(),0,dp.getLength(),"utf-8");
//				int port = dp.getPort();  
				byte[] dataTemp = dp.getData();
				System.out.println("第几次："+number+"===============");
				List<LocationVo> locationVoList = new ArrayList<LocationVo>();
				locationVoList = new WLDataParser().parser(dataTemp,"22:33:44:55:89:65");
				if(locationVoList!=null&&locationVoList.size()>0){
					for (int i = 0; i < locationVoList.size(); i++) {
						locationVoList.get(i).setCollTime(String.valueOf(System.currentTimeMillis()+i));
						System.out.println(
								locationVoList.get(i).getRSSI()+"-----"+
						locationVoList.get(i).getCollTime()+"-------"+
										locationVoList.get(i).getStaMac());
					}
					stationList.add(locationVoList);
				}
				if(stationList.size()>4){
					break;
				}
			} 
			return stationList;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
			if(socket != null){
				socket.close();
			}
		}
		return stationList;
	}
	
	public static void main(String args[]){
		UDPReceiver receiver = new UDPReceiver();
		receiver.receiveToCache("");
	}

	public static int get_receive_port() {
		return _receive_port;
	}

	public byte[] getQueueData() {
		return queue.poll();
	}
}
