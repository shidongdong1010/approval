<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>标的借口查询</title>
    <!-- css & js  -->
    <%@ include file="/common/header.jsp"%>
</head>
<body>
	<%@ include file="/common/navbar.jsp"%>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>

		<div class="main-container-inner">
			<!-- sidebar -->
			<c:import url="/sidebar.html" />

			<!-- main-content -->
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>

					<ul class="breadcrumb">
						<li>
							<i class="icon-home home-icon"></i>
							<a href="index">首页</a>
						</li>
						<li class="active">标的接口查询</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
						<tr >
							<td> 资金账户托管平台用户号:</td>
							<td><input type="text" id="project_id" name="project_id" width="500px"/></td>
							<td>
								<span class="input-group-btn">
									<button onclick="gridReload()" type="button" class="btn btn-purple btn-sm">
														查询
									<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</td>
						</tr>
					</table>

					<div class="padingtop20">
						<label for="result"></label>
						<!--  <textarea id="result" class="autosize-transition form-control" style="overflow: auto; word-wrap: break-word; resize: horizontal; height: 600px;">
						</textarea>-->
						<table align="left" width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="88B3E0">
							<tr class="px4">
								<td  align="left" colspan="4" class="fontbold">查询结果：</td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">商户端标的号：</td>
								<td width="30%" id="project_ids"></td>
								<td width="20%" align="right">标的账户号：</td>
								<td width="30%" id="project_account_id"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">标的状态：</td>
								<td width="30%" id="project_state"></td>
								<td width="20%" align="right">标的账户状态：</td>
								<td width="30%" id="project_account_state"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">标的余额：</td>
								<td width="30%" id="balance"></td>
								<td width="20%" align="right"></td>
								<td width="30%" ></td>
							</tr>
							
						</table>
					</div>

					

				  <script type="text/javascript">
					 var $path_base = "/";//this will be used in gritter alerts containing images
				  </script>
				</div><!-- /.page-content -->
			</div><!-- /.main-content -->

			<!-- #ace-settings-container -->
			<%@ include file="/common/settings.jsp"%>
		</div><!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div><!-- /.main-container -->

	<!-- basic scripts -->
	<%@ include file="/common/basic-script.jsp"%>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">

		var getUrlParm = null;
		//查询
		function gridReload() {
			var project_id = jQuery("#project_id").val() || null;
			if(null==project_id || ""==project_id){
				$().toastmessage('showErrorToast', "请输入资金账户托管平台用户号");
				return false;
			}
			jQuery.post("${ctx}/search/projectSearchList.json", {project_id: project_id}, function(msg){
				$().toastmessage('showSuccessToast', "查询第三方返回");
				getUrlParm = msg;
				if(msg.ret_code=="0000"){
					$("#balance").html(msg.balance);
					$("#project_ids").html(msg.project_id);
					$("#project_account_id").html(msg.project_account_id);
					$("#project_account_state").html(msg.project_account_state);
					$("#project_state").html(msg.project_state);
				}else{
					$("#balance").html("");
					$("#project_ids").html("");
					$("#project_account_id").html("");
					$("#project_account_state").html("");
					$("#project_state").html("");
					$().toastmessage('showErrorToast', msg.ret_msg);
				}
				
				// 缩进一个tab
				//$("#result").text(JSON.stringify(msg, null, "\t"));
			}, "json");
			return false;
		}

		// 获得URL
		function getUrl(){
			jQuery.post("${ctx}/search/transferSearchGetUrl.json", getUrlParm, function(msg){
				$().toastmessage('showSuccessToast', "获得URL成功");
				// 缩进一个tab
				$("#url-result").text(msg);
			}, "text");
		}
	</script>
</body>
</html>