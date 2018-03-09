<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="js/jquery-1.7.2.min.js"></script>
		<script src="js/FusionCharts.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/FusionCharts.js"></script>
		<title>aotelan测试接口</title>
		
		<script type="text/javascript">
		var typeList = null;
		var modelList = null;
		var param = null;

		var datelist = [];
		var vulelist = [];
		var namelist = [];
		var staMac ;
		// 点击事件
		function getAllAp(){
			
			staMac =  document.getElementById("publishTime").value;
			var w = $("#tongji").width() / 10 * 9.6;
			var h = $(window).height() - 35;
			lineChartUrl("ScrollLine2D.swf?ChartNoDataText=暂无数据显示！", w, (h / 2 - 65),
					"usersiteTypeTrend");

		}
		
		// 数据处理
		function lineChartUrl(swf, chartWidth, chartHeight, chartDiv, chartName) {
			
			var namelist2=namelist;
			var vulelist2=vulelist;
			var datelist2=datelist;
			 $.post('showListener.do?staMac='+staMac, function(data) {
					//var typeData = data.data;
					alert(data.toString());
					
					if (data != null && data != "undefined") {
						namelist2 = data.apList;
						vulelist2 = data.rssiList;
						datelist2 = data.collTimeList;

					}
			}, 'json');
				
			
			var chartStyle = " numdivlines='9' alternateHGridAlpha='0' canvasBgAlpha='0,0' canvasBorderAlpha='20' lineThickness='2' showValues='0'  chartLeftMargin='0' chartRightMargin='15'chartTopMargin='5' chartBottomMargin='0' anchorRadius='3' anchorBgAlpha='50' numVDivLines='24' toolTipBgColor='304e79'  showAlternateVGridColor='1' alternateVGridAlpha='3' showLegend='0' baseFontSize='14' showBorder='0' baseFontColor='FFFFFF' animation='0' bgAlpha='0,0' "
			var chartXMLData = "<chart caption='" + chartName + "' " + chartStyle
					+ " >";
			chartXMLData += "<categories >";
			for ( var int = 0; int < datelist2.length; int++) {
				chartXMLData += "<category name='" + datelist2[int] + "'/>";
			}
			chartXMLData += "</categories >";
			for ( var int = 0; int < namelist2.length; int++) {
				chartXMLData += "<dataset seriesName='" + namelist2[int] + "'>";
				var vallist = vulelist2[int];
				var columncolor = colormap.get(namelist2[int]);
				// alert(columncolor);
				for ( var i = 0; i < vallist.length; i++) {
					var values = vallist[i].split("@");
					var val = siteconfig.showType == "user" ? values[0] : values[1];
					chartXMLData += "<set color='" + columncolor + "' value='" + val
							+ "' />";
				}
				chartXMLData += " </dataset>";
			}

			chartXMLData += "</chart>";
			// alert("linexml:"+chartXMLData);
			drawChart(chartDiv, swf, chartWidth, chartHeight, chartXMLData);

		}
		     
		
		// 画图 (以指定 xml格式字符串为数据源)
		function drawChart(divId, flashFileName, width1, height1, chartXMLData) {
			var myChartId = new Date().getTime();
			var myChart = new FusionCharts("charts/"
					+ flashFileName, myChartId, width1, height1, "0", "1");
			myChart.setDataXML(chartXMLData);
			myChart.setTransparent(true);
			myChart.addParam("wmode", "Opaque");
			myChart.render(divId);
		}
		
		//转换日期格式
		Date.prototype.format = function(format) {
			var o = {
				"M+" : this.getMonth() + 1, // month
				"d+" : this.getDate(), // day
				"h+" : this.getHours(), // hour
				"m+" : this.getMinutes(), // minute
				"s+" : this.getSeconds(), // second
				"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
				"S" : this.getMilliseconds()
			// millisecond
			}
			if (/(y+)/.test(format))
				format = format.replace(RegExp.$1, (this.getFullYear() + "")
						.substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(format))
					format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
			return format;
		}
		</script>
		
	</head>
	<body>
		<div class="content" id="tongji">
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
					<div id="usersiteTypeTrend"
						style="position: relative; margin: 0 auto; width: 1000px; margin-top: 10px;"></div>
				</div>
			</div>
		</div>
	</body>
</html>
