<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>springMVC</title>
<!-- 引入依赖jquery -->
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<!-- 引入easyUI js文件 -->
<script type="text/javascript" src="js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<!-- 引入EasyUI的样式文件-->
<link rel="stylesheet" href="js/jquery-easyui-1.5.3/themes/default/easyui.css" type="text/css"/>
<!-- 引入EasyUI的图标样式文件-->
<link rel="stylesheet" href="js/jquery-easyui-1.5.3/themes/icon.css" type="text/css"/>


<script type="text/javascript">
	
</script>
<style type="text/css">
	.panel_class{
		width:700px;
		height:200px;
		margin:0 auto;
	}
	#wrapper {display:table;width:100%;height:100%;background:#FCFCFC;margin:0 auto;color:red;top:300px}
	#cell{display:table-cell; vertical-align:middle;width:400px;height:300px;text-align:center;}
</style>

</head>
<body class="easyui-layout">
    <div data-options="region:'north',title:'',split:true" style="height:100px;">
    	<div style="width:100%;text-align:center;line-height:100px">
    		Welcome to Easy-UI Classroom !  
    	</div>
    </div>
    <div data-options="region:'center',title:'',split:true" style="text-align:center">
	    <div id="wrapper">
		    <div id="cell" >
		    		<form id="ff" method="post" action="login">
					    <div>
							<label for="name">UserName:</label>
							<input class="easyui-validatebox" type="text" name="user.username" data-options="required:true" />
					    </div>
					    <BR>
					    <div>
							<label for="email">Password:</label>
							<input class="easyui-validatebox" type="password" name="user.password" data-options="required:true"/>
					    </div>
					    <BR>
					    <div>
					    	<input type="submit" value="submit">
					    </div>
		    	</form>
		    </div>
		</div>
	    
    </div>
    <div data-options="region:'south',title:''" style="padding:5px;background:#eee;">
    	<div style="width:100%;text-align:center">
    		@版权所有，转载请注明出处<br>
    		作者:不醉怎能入睡<br>
    		Emial:chenqk_123@163.com<br>
    		QQ:846049243
			你好！！！！ffffffffffffffffffffffff
    		
    	</div>
    </div>
</body>

</html>