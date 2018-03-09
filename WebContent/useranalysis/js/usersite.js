//查询site分类
var findsiteTypeByPlaceUrl = "../siteAction!findSiteTypeByDateTypeSpace.action";
var findsiteTypeByTagUrl = "../siteAction!findSiteTypeByDateTag.action";
// 查询site分类，按天分组
var findsiteTypeByPlaceDayUrl = "../siteAction!findSiteTypeByDatePlaceDay.action";
var findsiteTypeByTagDayUrl = "../siteAction!findSiteTypeByDateTagDay.action";
// 根据siteType查询具体app访问情况in
var findSiteByPlaceUrl = "../siteAction!findSiteByDateSpace.action";
var findSiteByTagUrl = "../siteAction!findSiteByDateTypeTag.action";
// 查询app每天趋势
var findAppByDateAppIdDayUrl = "../siteAction!findSiteByDateAppIdDay.action";
// ///siteTypeJsonaction
var appJsonFindtypeByPlace = "../siteJsonAction!findSiteTypeByDatePlaceDay.action";
var appJsonFindtypeBytag = "../siteJsonAction!findSiteTypeByDateTagDay.action";
var siteconfig = {
	start : "",// 开始时间
	end : "",// 结束时间
	period : "30",
	place : "1",// 物理位置 如：城市 热点 区域
	tag : "",// eva圈子
	siteType : "",// 类别 32大类
	showType : "user",// 显示类别 user：访客数 visit:访问次数
	onchoose : "type",// table :表格 type：图形
	siteId : ""// appid

}
var clolors = [ '304e79', '505e79', '614e79', '844e79', '305e79', '506e79',
		'617e79', '848e79', '309e79', '505e19', '614e29', '844e39', '304e49',
		'505e59', '614e69', '844e89' ];
var colormap = new Map();
$(function() {

	// 初始化时间
	$(".dateplugin").DatePlugin({
		onClickTime : onClickTime,
		isFocus : isFocus
	});
	// 初始化区域
	$(".areaplugin").areaPlugin({
		areaContentClick : areaContentClick
	});
	$(".areaplugin").areaPlugin('getAreaVal', '1', '全国');// 初始化数据
	// 初始化标签
	$(".tagplugin").tagPlugin({
		tagContentClick : tagContentClick
	});
	$(".tagplugin").tagPlugin('getTagVal', '0', '分类');// 初始化数据

	$("#usernum").click(function() {
		siteconfig.showType = "user";
		changeAllData();
		// alert(showType);
	})
	$("#visitnum").click(function() {
		siteconfig.showType = "visit";
		changeAllData();
		// alert(showType);
	})

	$("#table_li").click(function() {
		siteconfig.onchoose = "table";
		changeAllData();
		// alert(showType);
	})
	$("#type_li").click(function() {
		siteconfig.onchoose = "type";
		changeAllData();
		// alert(showType);
	})
	/**
	 * 窗口的关闭
	 */
	$('.SHOW_CLOSE').click(function() {
		var win = $('.SHOW_WINDOW');
		win.css('display', 'none');

		// 清空数组中数据
	})
	// 初始化最近30天颜色
	$("#manyDay").css("background", "#304e79");
	// 去掉链接锚点
	$("#oneDay").attr("href", "javascript:void(0)");
	$("#sevenDay").attr("href", "javascript:void(0)");
	$("#manyDay").attr("href", "javascript:void(0)");
	$(".areaContentA").attr("href", "javascript:void(0)");
	$(".tagContentA").attr("href", "javascript:void(0)");
	// 初始化区域颜色
	$(".araaTableTitleTr").css("background", "#304e79");

	getParamAndSesrch();
	initStatics();

})
/** **********时间控件回调************************* */
// 点击事件
function onClickTime(v, obj) {
	// 移除所有添加背景色
	// removeBgColor();
	$(".dateDay").css("background", "none");
	// 添加背景色
	$(obj).css("background", "#304e79");
	siteconfig.period = v;
	siteconfig.start = "";
	siteconfig.end = "";
	$("#datepluginstart").val("");
	$("#datepluginend").val("");
	clearType();
	getParamAndSesrch();
}
// 选择时间控件
function isFocus(v, obj) {
	// 移除所有添加背景色
	$(".dateDay").css("background", "none");
	siteconfig.period = "";
	siteconfig.start = $("#datepluginstart").val();
	siteconfig.end = $("#datepluginend").val();
	clearType();
	getParamAndSesrch();
	// alert("控件时间："+start+"aaa"+end);
}

/** *********区域回调********************************** */
function areaContentClick(code, name) {
	// 显示区域列表
	showAreaGrid(code, name);
	// 隐藏标签控件
	$(".tagplugin").tagPlugin('hideTagDiv');
	// 移除标签背景色
	$(".tagTableTitleTr").css("background", "none");
	$(".araaTableTitleTr").css("background", "none");
	// 添加背景色
	$(".araaTableTitleTr").css("background", "#304e79");
	siteconfig.place = code;
	siteconfig.tag = "";
	clearType();
	getParamAndSesrch();
}

/** *********标签回调********************************** */
function tagContentClick(code, name) {
	// 显示标签列表
	showTagGrid(code, name)
	// 隐藏区域标签
	$(".areaplugin").areaPlugin('hideAreaDiv');
	// 移除标签背景色
	$(".tagTableTitleTr").css("background", "none");
	$(".araaTableTitleTr").css("background", "none");
	// 添加背景色
	$(".tagTableTitleTr").css("background", "#304e79");

	siteconfig.tag = code == '0' ? "" : code;
	siteconfig.place = "";
	clearType();
	getParamAndSesrch();
}

/** *********通过选择区域和标签显示列表********************** */
// 显示区域列表
function showAreaGrid(id, name) {
	$(".gridplugin").gridPlugin({
		oparam : 'id=' + id,
		tdOnClick : tdOnClick,
		fname : name
	});
	$(".areaContentA").attr("href", "javascript:void(0)");
}
// 显示区域列表
function showTagGrid(pId, name) {
	$(".gridplugin").gridPlugin({
		oparam : 'pId=' + pId,
		tdOnClick : tdOnClick,
		urlType : false,
		fname : name
	});
	$(".tagContentA").attr("href", "javascript:void(0)");
}
// 点击列表
function tdOnClick(v, bln) {
	if (bln) {
		siteconfig.tag = "";
		siteconfig.place = v;

	} else {
		siteconfig.place = "";
		siteconfig.tag = v;

	}
	clearType();
	getParamAndSesrch();
	// alert("列表编号："+v+":区域"+bln);
}
// 移除标签背景色
function removeBgColor() {
	//
}
// 刷新图标
function getParamAndSesrch() {

	makeCondition();
	findAllData();
	//changeAllData();
	
}

var makeCondition = function() {
	siteconfig.start = $("#datepluginstart").val();
	siteconfig.end = $("#datepluginend").val();
	return [ {
		name : 'start',
		value : siteconfig.start
	}, {
		name : 'end',
		value : siteconfig.end
	}, {
		name : 'place',
		value : siteconfig.place==1?'':siteconfig.place
	}, {
		name : 'tag',
		value : siteconfig.tag
	}, {
		name : 'siteType',
		value : siteconfig.siteType
	}, {
		name : 'showType',
		value : siteconfig.showType
	}, {
		name : 'period',
		value : siteconfig.period
	}, {
		name : 'siteId',
		value : siteconfig.siteId
	}
	
	];

}

// *******************************************************//
var typeList = null;
var modelList = null;
var param = null;

var datelist = [];
var vulelist = [];
var namelist = [];

/**
 * 左侧条件查询数据
 */
function findAllData() {
	// 组装查询条件
	param = makeCondition();
	// 查询type数据
	findTypeData();
	// 查询model数据
	findModelData();
	// 查询typeday数据
	findTypeDayData();
}
function findTypeData() {
	var tepUrl = siteconfig.place == "" ? findsiteTypeByTagUrl
			: findsiteTypeByPlaceUrl;
	$.ajax({
		url : tepUrl,
		type : "post",
		async : true,
		data : param,
		success : function(data, textStatus, jqXHR) {
			typeList = data;
			if (siteconfig.onchoose == "table") {
				siteTypeTableSearch();
			} else {
				pieSearch();

			}
		}
	})
}
function findModelData() {
	var columnUrl = siteconfig.place == "" ? findSiteByTagUrl
			: findSiteByPlaceUrl;
	$.ajax({
		url : columnUrl,
		type : "post",
		async : true,
		data : param,
		success : function(data, textStatus, jqXHR) {
			modelList = data;
			if (siteconfig.onchoose == "table") {
				appTableSearch();
			} else {
				columnSearch(null);

			}
		}
	})
}
function findTypeDayData() {
	datelist = [];
	vulelist = [];
	namelist = [];
	var tepUrl = siteconfig.place == "" ? findsiteTypeByTagDayUrl
			: findsiteTypeByPlaceDayUrl;
	$.ajax({
		url : tepUrl,
		type : "post",
		async : false,
		data : param,
		success : function(data, textStatus, jqXHR) {
			var typeData = data.data;
			if (typeData != null && typeData != "undefined") {
				namelist = typeData.nameList;
				vulelist = typeData.valueList;
				datelist = typeData.dateList;

			}
			
			if (siteconfig.onchoose == "table") {
				siteTypeDayTableSearch();
			} else {
				lineSearch();

			}
		}
	})
}
function changeAllData() {
	if (siteconfig.onchoose == "table") {
		tbaleSearch();
	} else {
		typeSearch();

	}
}

// *******************************************************//
/** ***统计图demo*** */
function initStatics() {
	siteTypeTableinit();
	appTableinit();
	siteTypeDayTableInit();

}

/**
 * siteTypeTable 初始化
 */
var siteTypeTableinit = function() {
	var w = $("#tongji").width();
	var wf = $(window).width() / 10 * 2;
	w = (w) * 48 / 100;
	var h = $(window).height() - 45;
	var height = h / 2 - 70;
	var width = w;
	var tdWidth = width / 3;
	// 初始化列表
	var tepUrl = siteconfig.place == "" ? findsiteTypeByTagUrl
			: findsiteTypeByPlaceUrl;
	$("#siteTypeTable").flexigrid({
		url : null,
		dataType : 'json',
		colModel : [ {
			display : '分类名称',
			name : 'siteTypeName',
			width : tdWidth,
			align : 'left',
		}, {
			display : '访客数',
			name : 'userNum',
			width : tdWidth - 15,
			align : 'left'
		}, {
			display : '访问次数',
			name : 'visitNum',
			width : tdWidth - 15,
			align : 'left'
		}, {
			display : 'siteTypeId',
			name : 'siteTypeId',
			align : 'left',
			hide : true
		} ],
		usepager : false,
		useRp : false,
		onSuccess : success,
		width : width,
		height : height
	});
}
/**
 * appTable 初始化
 */
var appTableinit = function() {
	var w = $("#tongji").width();
	w = (w) * 48 / 100;
	var h = $(window).height() - 45;
	var height = h / 2 - 70;
	var width = w;
	var tdWidth = width / 3;
	// 初始化列表
	var columnUrl = siteconfig.place == "" ? findSiteByTagUrl
			: findSiteByPlaceUrl;
	$("#appTable").flexigrid({
		url : null,
		dataType : 'json',
		colModel : [ {
			display : '应用名称',
			name : 'siteName',
			width : tdWidth,
			align : 'left',
		}, {
			display : '访客数',
			name : 'userNum',
			width : tdWidth - 15,
			align : 'left'
		}, {
			display : '访问次数',
			name : 'visitNum',
			width : tdWidth - 15,
			align : 'left'
		}, {
			display : 'siteId',
			name : 'siteId',
			align : 'left',
			hide : true
		} ],
		usepager : false,
		useRp : false,
		onSuccess : success,
		width : width,
		height : height
	});
}

var cols = [];
function makeCols(tdWidth) {
	cols = [];
	cols.push({
		display : '分类名称',
		name : 'pname',
		width : tdWidth + 30,
		align : 'left'
	});
	for ( var i = 0; i < datelist.length; i++) {
		cols.push({
			display : datelist[i],
			name : datelist[i],
			width : tdWidth,
			align : 'left'
		});
	}

}
var siteTypeDayTableInit = function() {
	var w = $("#tongji").width();
	var h = $(window).height() - 45;
	var height = h / 2 - 85;
	var width = w;
	var tdWidth = width / (datelist.length + 1);
	makeCols(100);
	// 判断是按照什么查询 place不为空就是按位置查询，place为空就是按照ｔａｇ 查询
	// //////////////////////////////////////////////////////////////////////////typejsonaction没写这个方法
	var tepUrl = siteconfig.place == "" ? appJsonFindtypeBytag
			: appJsonFindtypeByPlace;
	$("#siteTypeDayTable").flexigrid({
		url : null,
		dataType : 'json',
		colModel : cols,
		 resizable: false, 
		usepager : false,
		useRp : false,
		onSuccess : succ,
		width : w * 0.96,
		height : height

	});
}
/**
 * northTable 成功回调函数
 * 
 * @param result
 */
function success(result) {
	$("#northTable>tbody>tr").each(function() {

	});

}
function succ(result) {

}

// 点击图形事件
function typeSearch() {
	pieSearch();
	columnSearch(null);
	lineSearch();
}
// 饼状图
function pieSearch() {
	var swf = "Pie2D.swf?ChartNoDataText=暂无数据显示！";
	var w = $("#tongji").width();
	w = (w) / 30 * 10;
	var h = $(window).height() - 45;
	var width = w;
	var length = h / 2 - 55;
	pieChart2(swf, width, length, "usersiteTypePie", "", siteconfig.showType);
}
// 柱状图
function columnSearch(url) {
	var w = $("#tongji").width();
	w = (w) / 30 * 18;
	var h = $(window).height() - 45;
	var cloumn2Dwith = w;
	var cloumn2Dlength = h / 2 - 55;
	var cloumn2Dswf = "ScrollColumn2D.swf?ChartNoDataText=暂无数据显示！";

	cloumn2D(cloumn2Dswf, cloumn2Dwith, cloumn2Dlength, "userAppColumn", "",
			siteconfig.showType,url);
}
// 柱状图点击事件
function Map() {
	this.container = new Object();
}

Map.prototype.put = function(key, value) {
	this.container[key] = value;
}

Map.prototype.get = function(key) {
	return this.container[key];
}
var map = new Map();
var typeMap = new Map();
var showNewWindow = function(pcode) {

	var showTittle = map.get(pcode);
	siteconfig.siteId = pcode;
	$('.SHOW_TITLE span').html(showTittle);
	var win = $('.SHOW_WINDOW');
	win.css('display', 'inherit');
	param = makeCondition();
	lineChartUrl("ScrollLine2D.swf?ChartNoDataText=暂无数据显示！", 800, 400,
			"line_show", "", findAppByDateAppIdDayUrl);
}

// 多曲线图
function lineSearch() {
	var w = $("#tongji").width() / 10 * 9.6;
	var h = $(window).height() - 35;
	lineChart("ScrollLine2D.swf?ChartNoDataText=暂无数据显示！", w, (h / 2 - 65),
			"usersiteTypeTrend", "");
}
// 列表点击事件
function tbaleSearch() {

	siteTypeTableSearch();
	appTableSearch();
	siteTypeDayTableSearch();

}
// 查询siteType
function siteTypeTableSearch() {
	$("#siteTypeTable").flexAddData(typeList);

}
// 查询site
function appTableSearch() {
	$("#appTable").flexAddData(modelList);

}

var flexiMoudel = {
	total : "",// 开始时间
	rows : [],
	page : 1
}
// 查询siteType,按天分组数据
function siteTypeDayTableSearch() {
	flexiMoudel.rows = [];
	makeCols(100);
	var a = $(".test .flexigrid .hDiv .hDivBox table thead tr").remove();

	var b = $(".test .flexigrid .hDiv .hDivBox table thead");
	var trHtml = '<tr>';
	trHtml += '<th axis="col0" align="left" class=""><div style="text-align: left; width: 130px;" class="sundefined">分类名称</div></th>';
	for ( var i = 0; i < datelist.length; i++) {
		trHtml += '<th axis="col'
				+ (i + 1)
				+ '" align="left" class=""><div style="text-align: left; width: 130px;" class="sundefined">'+datelist[i].trim()+'</div></th>';
	}
	trHtml += '</tr>';
	b.html(trHtml);
	for ( var i = 0; i < namelist.length; i++) {
		var str = "{";
		var nameval = namelist[i];
		str += "'pname':'" + nameval + "',"

		for ( var j = 0; j < datelist.length; j++) {
			var dateval = datelist[j];

			var types = vulelist[i][j].split("@");
			;
			var t = siteconfig.showType == 'user' ? types[0] : types[1];
			str += "'" + dateval + "':'" + t + "',"

		}
		str = str.substring(0, str.length - 1)
		str += "}";
		//alert(str);
		console.log(str)
		var obj = eval('(' + str + ')');
		flexiMoudel.rows.push(obj);
	}
//	var tt="{'pname':'技术交流','2014-10-31':'120','2014-11-01':'0','2014-11-02':'0','2014-11-03':'0'}";
//	var obj = eval('(' + tt + ')');
//	flexiMoudel.rows.push(obj);
	flexiMoudel.total = flexiMoudel.rows.length;
	console.log(flexiMoudel)
	$("#siteTypeDayTable").flexAddDataAndCol(flexiMoudel,cols);

}
// //清楚分类，左侧查询条件地调用
function clearType() {
	$("#typeSpan").html("");
	$("#typeSpan1").html("");
	siteconfig.siteType = "";
}
// ////饼图点击事件
function showFusinValues(val) {

	$("#typeSpan").html("(" + typeMap.get(val) + ")");
	$("#typeSpan1").html("(" + typeMap.get(val) + ")");
	siteconfig.siteType = val;
	
	param = makeCondition();
	var columnUrl = siteconfig.place == "" ? findSiteByTagUrl
			: findSiteByPlaceUrl;
	columnSearch(columnUrl);
}
function showAppValues(val) {
	showNewWindow(val);
}

function pieChart2(swf, chartWidth, chartHeight, chartDiv, chartName, showType) {

	var h = $(window).height() / 8 * 2.35;
	var pieRadius = h / 400 * 180;
	var chartStyle = " useRoundEdges='2' slicingDistance='1' pieRadius='"
			+ pieRadius
			+ "'  baseFontSize='14' showBorder='0'  chartLeftMargin='0' chartRightMargin='0'chartTopMargin='0' chartBottomMargin='0' baseFontColor='FFFFFF' showValues='1' toolTipBgColor='304e79' showLabels='1' showLegend='0' bgColor='000000,FFFFFF' bgAlpha='0,0' ";
	var chartXMLData = "<chart caption='" + chartName + "' " + chartStyle
			+ " >";

	var typeData = typeList.rows;
	typeData.sort(by("userNum", by("visitNum")));
	for ( var int = 0; int < typeData.length; int++) {
		var pcode = typeData[int].siteTypeId;
		var val = siteconfig.showType == "user" ? typeData[int].userNum
				: typeData[int].visitNum;
		var pna = typeData[int].siteTypeName;
		typeMap.put(typeData[int].siteTypeId, pna);
		chartXMLData += "<set color='" + clolors[int] + "' name='" + pna
				+ "' value='" + val + "' link='javascript:showFusinValues("
				+ pcode + " )'/>";
		colormap.put(pna, clolors[int]);
	}
	chartXMLData += "</chart>";
	// alert(chartXMLData);
	drawChart(chartDiv, swf, chartWidth, chartHeight, chartXMLData);
}
function cloumn2D(swf, chartWidth, chartHeight, chartDiv, chartName, showType,tempUrl) {
	var typeData = modelList.rows;
	if(tempUrl!=null){
		$.ajax({
			url : tempUrl,
			type : "post",
			async : false,
			data : param,
			success : function(data, textStatus, jqXHR) {
				typeData = data.rows;
				modelList=data;
			}
		})
		
	}
	var chartStyle = " showValues='0' placeValuesInside='0' xAxisName='站点' yAxisName='数量'  palette='1' "
			+ " useRoundEdges='1' alternateHGridAlpha='0' canvasBgAlpha='0,0' canvasBorderAlpha='20' bgColor='000000,FFFFFF'  baseFontSize='14' "
			+ "showBorder='0'  chartLeftMargin='0' chartRightMargin='0'chartTopMargin='10' chartBottomMargin='0' baseFontColor='FFFFFF'"
			+ "  toolTipBgColor='304e79'  "
			+ " showLabels='1' showLegend='0'  bgAlpha='0,0,0'  ";

	var chartXMLData = "<chart caption='" + chartName + "' " + chartStyle
			+ " >";
	var typeData = modelList.rows;
	typeData.sort(by("userNum", by("visitNum")));
	chartXMLData += "<categories>";
	for ( var int = 0; int < typeData.length; int++) {
		var pna = typeData[int].siteName;
		chartXMLData += "<category  label='" + pna + "' />";
	}
	chartXMLData += "</categories> <dataset>";
	var columncolor = colormap.get(typeMap.get(siteconfig.siteType));
	for ( var int = 0; int < typeData.length; int++) {
		var pcode = typeData[int].siteId;
		var val = siteconfig.showType == "user" ? typeData[int].userNum
				: typeData[int].visitNum;

		var pna = typeData[int].siteName;
		map.put(pcode, pna);

		if (columncolor == null) {
			chartXMLData += "<set  value='" + val
					+ "' link='javascript:showAppValues(" + pcode + " )'/>";
		} else {
			chartXMLData += "<set color='" + columncolor + "' value='" + val
					+ "' link='javascript:showAppValues(" + pcode + " )'/>";
		}

	}
	chartXMLData += "</dataset>";
	chartXMLData += "</chart>";
	// alert(chartXMLData);
	drawChart(chartDiv, swf, chartWidth, chartHeight, chartXMLData);
}
function lineChart(swf, chartWidth, chartHeight, chartDiv, chartName) {
	var tepUrl = siteconfig.place == "" ? findsiteTypeByTagDayUrl
			: findsiteTypeByPlaceDayUrl;
	lineChartUrl(swf, chartWidth, chartHeight, chartDiv, chartName, tepUrl);
}
function lineChartUrl(swf, chartWidth, chartHeight, chartDiv, chartName, tempUrl) {
	var namelist2=namelist;
	var vulelist2=vulelist;
	var datelist2=datelist;
	if(tempUrl!=null){
		$.ajax({
			url : tempUrl,
			type : "post",
			async : false,
			data : param,
			success : function(data, textStatus, jqXHR) {
				var typeData = data.data;
				if (typeData != null && typeData != "undefined") {
					namelist2 = typeData.nameList;
					vulelist2 = typeData.valueList;
					datelist2 = typeData.dateList;

				}
			}
		})
		
	}
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
	var myChart = new FusionCharts("../../resource/fusioncharts/charts/"
			+ flashFileName, myChartId, width1, height1, "0", "1");
	myChart.setDataXML(chartXMLData);
	myChart.setTransparent(true);
	myChart.addParam("wmode", "Opaque")
	myChart.render(divId);
}

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
