<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="js/jquery-1.7.2.min.js"></script>
		<script src="js/highcharts.js" type="text/javascript"></script>
		<title>呼和浩特市精细化预报综合业务平台</title>
		
		<script type="text/javascript">
		var staMac;
		
		$(function(){   
		Highcharts.setOptions({ 
	        global: { 
	            useUTC: false 
	        } 
	    }); 
	  
	   var chart = new Highcharts.Chart({ 
	            chart: { 
	                renderTo: 'container', //图表放置的容器，DIV 
	                defaultSeriesType: 'spline' //图表类型为曲线图 
	             
	            }, 
	            title: { 
	                text: '用户设备趋势图'  //图表标题 
	            }, 
	            xAxis: { //设置X轴 
	            	title: '上报时间', 
	                type: 'datetime',  //X轴为日期时间类型 
	                tickPixelInterval: 150 , //X轴标签间隔 
	                //categories:["2014-11-12 21:10:27"]
	            }, 
	            yAxis: { //设置Y轴 
	                title: 'RSSI值', 
	                max: 0, //Y轴最大值 
	                min: -90  //Y轴最小值 
	            }, 
	            tooltip: {//当鼠标悬置数据点时的提示框 
	                formatter: function() { //格式化提示信息 
	                    return 'CPU使用率'+ 
	                    Highcharts.dateFormat('%H:%M:%S', this.x) +''+  
	                    Highcharts.numberFormat(this.y, 2)+'%'; 
	                } 
	            }, 
	            legend: { 
	                enabled: false  //设置图例不可见 
	            }, 
	            exporting: { 
	                enabled: false  //设置导出按钮不可用 
	            }, 
	            credits: { 
	                text: 'lain.com.cn', //设置LOGO区文字 
	                url: 'http://www.lain.com.cn' //设置LOGO链接地址 
	            }, 
	            series: [{ 
	                data: (function() { //设置默认数据， 
	                    var data = [], 
	                    time = (new Date()).getTime(), 
	                    i; 
	     
	                    for (i = -19; i <= 0; i++) { 
	                        data.push({ 
	                            x: time + i * 5000,  
	                            y: Math.random()*100 
	                        }); 
	                    } 
	                    return data; 
	                })() 
	            }] 
	        }); 
		
		
		function getAllAp(){
			staMac = document.getElementById("publishTime").value;
			alert("--------------------"+staMac);
			
	    	//setInterval(getflux, 6000);                
			getflux();
	    };
		
	    function getflux(){
			
		    $.post('showListener.do?staMac='+staMac, function(data) {
		    	alert(data.colltime);
		            //这里的x(时间) 格式要是毫秒式13位
		            
		     var result = {"x":data.colltime,"y":data.stationList};
		     var list = data.stationList;
		     var series1 = chart.series[0]; 
		     var length = list.getLenth();
		     
		     for(var i =0 ;i <length ; i++){
		    	 var x = result.x; // current time  
			     var y = list[i].rssi;  
			     series1.addPoint([x, y], true, true);
		     }
		     
		    
		     chart.redraw();
		     //parent.$.messager.progress('close');
		     }, 'json');            
		};
		 $(document).ready(function() {   
             //每隔3秒自动调用方法，实现图表的实时更新   
             window.setInterval(getflux,3000);                 
          });  
	    
		});
		
		
		         
		</script>
		
	</head>
	<body>
		<div class="content">
			<div class="content_data">
				<div>
					<fieldset class="query_fields">
						<legend style="color: white;">
							STA:
						</legend>
							
							<div>
								起报时间:
								<input id="publishTime" name="publishTime"
								 style="width: 100px" type="text"
									/>
							</div>
							<div>
								<input style="width: 60px"  value="查询" id="submit" type="button" onclick="getAllAp();"/>
							</div>
					</fieldset>
					<div id="container"
						style="position: relative; margin: 0 auto; width: 1000px; margin-top: 10px;"></div>
				</div>
			</div>
		</div>
	</body>
</html>
