<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>交易查询</title>
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
						<li class="active">交易查询</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
						<tr >
							<td> 订单号:</td>
							<td><input type="text" id="order_id" name="order_id" width="200px" onkeyup="value=value.replace(/[^\d]/g,'')"/></td>
							<td>交易类型:</td>
							<td>
								<select id="busi_type" name="busi_type">
									<option value="01">01充值</option>
									<option value="02">02提现</option>
									<option value="03">03标的转账</option>
									<option value="04">04转账</option>
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
								<td width="20%" align="right">订单号：</td>
								<td width="30%" id="order_ids"></td>
								<td width="20%" align="right">订单日期：</td>
								<td width="30%" id="mer_dates"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">交易平台流水号：</td>
								<td width="30%" id="trade_no"></td>
								<td width="20%" align="right">对账日期：</td>
								<td width="30%" id="mer_check_date"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">业务类型：</td>
								<td width="30%" id="busiType"></td>
								<td width="20%" align="right">交易金额：</td>
								<td width="30%" id="amount"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">手续费：</td>
								<td width="30%" id="com_amt"></td>
								<td width="20%" align="right">手续费承担方类型：</td>
								<td width="30%" id="com_amt_type"></td>
							</tr>
							<tr class="px4">
								<td width="20%" align="right">交易状态：</td>
								<td width="30%" id="tran_state"></td>
								<td width="20%" align="right">原交易金额：</td>
								<td width="30%" id="orig_amt"></td>
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
			var order_id = jQuery("#order_id").val() || null;
			var busi_type = jQuery("#busi_type").val() || null;
			if(order_id!=null && order_id!=""){
				if(order_id.length!=15){
					$().toastmessage('showErrorToast', "订单输入错误");
					return false;
				}
			}else{
				$().toastmessage('showErrorToast', "请输入要查询的订单号");
				return false;
			}
			jQuery.post("${ctx}/search/transferSearchList.json", {order_id: order_id, busi_type: busi_type}, function(msg){
				getUrlParm = msg;
				if(msg.ret_code=="0000"){
					$().toastmessage('showSuccessToast', "查询第三方返回");
					$("#order_ids").html(msg.order_id);
					$("#busiType").html(msg.busi_type);
					$("#com_amt").html(msg.com_amt);
					$("#com_amt_type").html(msg.com_amt_type);
					$("#mer_check_date").html(msg.mer_check_date);
					$("#mer_dates").html(msg.mer_date);
					$("#mer_id").html(msg.mer_id);
					$("#order_id").html(msg.order_id);
					$("#orig_amt").html(msg.orig_amt);
					$("#trade_no").html(msg.trade_no);
					$("#tran_state").html(msg.tran_state);
					$("#amount").html(msg.amount);
				}else{
					$().toastmessage('showSuccessToast', "第三方无订单数据");
					$("#order_ids").html("");
					$("#busiType").html("");
					$("#com_amt").html("");
					$("#com_amt_type").html("");
					$("#mer_check_date").html("");
					$("#mer_dates").html("");
					$("#mer_id").html("");
					$("#order_id").html("");
					$("#orig_amt").html("");
					$("#trade_no").html("");
					$("#tran_state").html("");
					$("#amount").html("");
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