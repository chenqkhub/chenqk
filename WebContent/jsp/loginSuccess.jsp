<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>upload</TITLE>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
   <STYLE type=text/css>
   		table tr td{
   			font-size:15px;
   		}
   </STYLE>
   <META content="MSHTML 6.00.2900.5848" name=GENERATOR>
  
</head>
<body bgcolor='#E6EAE9'>
 &nbsp;<br>
&nbsp;<br>
	<table>
		<tr>
			<td>用户id</td>
			<td>用户名</td>
			<td>密码</td>
		</tr>
		<c:forEach var="mem" items="${userList}">
			<tr>
				<td>${mem.userId }</td>
				<td>${mem.userName}</td>
				<td>${mem.password}</td>
			</tr>
			
		</c:forEach>
	</table>
</body>

</html>

