<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户查询</title>
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
						<li class="active">用户查询</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
						<tr >
							<td> 资金账户托管平台用户号:</td>
							<td><input type="text" id="user_id" name="user_id" width="500px"/></td>
							<td>是否查询余额:</td>
							<td>
								<select id="is_find_account" name="is_find_account">
									<option value="02">不查</option>
									<option value="01">查询</option>
								</select>
							</td>
							<td>是否查询授权协议:</td>
							<td>
								<select id="is_select_agreement" name="is_select_agreement">
									<option value="02">不查</option>
									<option value="01">查询</option>
								</select>
							</td>
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
								<td width="20%" align="right">姓名：</td>
								<td width="30%" id="cust_name"></td>
								<td width="20%" align="right">手机：</td>
								<td width="30%" id="contact_mobile"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">证件类型：</td>
								<td width="30%" id="identity_type"></td>
								<td width="20%" align="right">证件号：</td>
								<td width="30%" id="identity_code"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">邮箱：</td>
								<td width="30%" id="mail_addr"></td>
								<td width="20%" align="right">账户状态：</td>
								<td width="30%" id="account_state"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">账户余额：</td>
								<td width="30%" id="balance"></td>
								<td width="20%" align="right">提现银行卡：</td>
								<td width="30%" id="card_id"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">发卡银行编号：</td>
								<td width="30%" id="gate_id"></td>
								<td width="20%" align="right">用户签约约的协议列表信息：</td>
								<td width="30%" id="user_bind_agreement_list"></td>
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
			var user_id = jQuery("#user_id").val() || null;
			var mer_cust_id = jQuery("#mer_cust_id").val() || null;
			var is_find_account = jQuery("#is_find_account").val() || null;
			if(null==user_id || user_id==""){
				$().toastmessage('showErrorToast', "请输入资金账户托管平台用户号");
				return false;
			}
			jQuery.post("${ctx}/search/userSearchList.json", {user_id: user_id, mer_cust_id: mer_cust_id, is_find_account: is_find_account}, function(msg){
				getUrlParm = msg;
				if(msg.ret_code=="0000"){
					$().toastmessage('showSuccessToast', "查询第三方返回");
					$("#cust_name").html(msg.cust_name);
					$("#identity_type").html(msg.identity_type);
					$("#identity_code").html(msg.identity_code);
					$("#contact_mobile").html(msg.contact_mobile);
					$("#mail_addr").html(msg.mail_addr);
					$("#account_state").html(msg.account_state);
					$("#balance").html(msg.balance);
					$("#card_id").html(msg.card_id);
					$("#gate_id").html(msg.gate_id);
					$("#user_bind_agreement_list").html(msg.user_bind_agreement_list);
				}else{
					$("#cust_name").html("");
					$("#identity_type").html("");
					$("#identity_code").html("");
					$("#contact_mobile").html("");
					$("#mail_addr").html("");
					$("#account_state").html("");
					$("#balance").html("");
					$("#card_id").html("");
					$("#gate_id").html("");
					$("#user_bind_agreement_list").html("");
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