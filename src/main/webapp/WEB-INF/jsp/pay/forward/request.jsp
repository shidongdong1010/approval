<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="${ctx}/assets/js/jquery-2.1.4.min.js"></script>
    <title>跳转第三方</title>
    <script type="text/javascript">
		$(function(){
			$("form").submit();
		});
	</script>
</head>
<body>
第三方跳转中,请不要关闭此页面...
<form action="${url}" method="post">
</form>
</body>
</html>
