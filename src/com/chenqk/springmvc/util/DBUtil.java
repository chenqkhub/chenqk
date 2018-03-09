package com.chenqk.springmvc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 创建数据库连接
 * @author chenqk
 *
 */
public class DBUtil {

 	private static String driver = "com.mysql.jdbc.Driver";
 	private static String dbName = "springmvc";
 	private static String passwrod = "123";
 	private static String userName = "root";
 	private static String url = "jdbc:mysql://localhost:3306/" + dbName;
   
	
	/**
	 * @param args
	 */
		 public static void main(String[] args) {
			// getDBConnect();
		       
		    }
		 

	/**
	 * 将数据存储到数据库中	 
	 * @param vo 实体类
	 */
	public static void getDBConnect(LocationVo vo){
		
			String sql = "insert into stamac_info(staMac,apMac,rssi,toa,channel,collTime)values(?,?,?,?,?,?)";
	        try {
	            Class.forName(driver);
	            Connection conn = DriverManager.getConnection(url, userName,
	                    passwrod);
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1,vo.getStaMac());
	            ps.setString(2,vo.getApMac());
	            ps.setDouble(3,vo.getRSSI());
	            ps.setInt(4,vo.getTOA());
	            ps.setInt(5,vo.getChannel());
	            ps.setString(6,vo.getCollTime());
	            ps.executeUpdate(); 
	           
	            closeResource(conn,ps,null);
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public static List<LocationVo> getAllStaMac(){
		List<LocationVo> locationVoList = new ArrayList<LocationVo>();
		String sql = "SELECT staMac,apMac,collTime,AVG(rssi) rssi from stamac_info group by collTime,apMac order by collTime Desc,avg(rssi) Desc";
		 try {
			Class.forName(driver);
			 Connection conn = DriverManager.getConnection(url, userName,
			         passwrod);
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs =ps.executeQuery();
			 while(rs.next()){
				 LocationVo locationVo = new LocationVo();
				 locationVo.setApMac(rs.getString("apMac"));
				 locationVo.setStaMac(rs.getString("staMac"));
				 locationVo.setCollTime(rs.getString("collTime"));
				 locationVo.setRSSI(rs.getInt("rssi"));
				 locationVoList.add(locationVo);
			 }
			 closeResource(conn,ps,rs);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return locationVoList;
	}

	/**
	 * 关闭数据库连接，释放资源
	 * @param con
	 * @param ps
	 * @param rs
	 */
	public static void closeResource(Connection con,PreparedStatement ps,ResultSet rs){
		
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static List<LocationVo> getAllStaMacByTime(String collTime) {
		List<LocationVo> locationVoList = new ArrayList<LocationVo>();
		String sql = "SELECT staMac,apMac,collTime,AVG(rssi) rssi from stamac_info WHERE collTime =? group by collTime,apMac order by collTime Desc,avg(rssi) Desc";
		 try {
			Class.forName(driver);
			 Connection conn = DriverManager.getConnection(url, userName,
			         passwrod);
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ps.setString(1, collTime);
			 ResultSet rs =ps.executeQuery();
			 while(rs.next()){
				 LocationVo locationVo = new LocationVo();
				 locationVo.setApMac(rs.getString("apMac"));
				 locationVo.setStaMac(rs.getString("staMac"));
				 locationVo.setCollTime(rs.getString("collTime"));
				 locationVo.setRSSI(rs.getInt("rssi"));
				 locationVoList.add(locationVo);
			 }
			 closeResource(conn,ps,rs);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return locationVoList;
	}
	
	/**
	 * 获取前十个时间点,用于生成时间坐标轴
	 * @return
	 */
	public static List<String> searchAllApByColltime(){
		
		List<String> collTimeList = new ArrayList<String>();
		String sql = "SELECT DISTINCT collTime FROM stamac_info ORDER BY collTime DESC;";
		 try {
			Class.forName(driver);
			 Connection conn = DriverManager.getConnection(url, userName,
			         passwrod);
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs =ps.executeQuery();
			 while(rs.next()){
			 String collTime = rs.getString("collTime");
			 collTimeList.add(collTime);
			 }
			 closeResource(conn,ps,rs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return collTimeList;
	}
}
