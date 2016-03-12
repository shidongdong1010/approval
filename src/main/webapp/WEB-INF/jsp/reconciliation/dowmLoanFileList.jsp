<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>对账文件下载</title>
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
						<li class="active">对账文件下载</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
						<tr >
							<td> 对账日期:</td>
							<td><input type="text" id=settle_date_p2p name="settle_date_p2p" width="200px" onkeyup="value=value.replace(/[^\d]/g,'')"/></td>
						</tr>
					</table>

					<div class="padingtop20">
						<label for="result"></label>
						<!--  <textarea id="result" class="autosize-transition form-control" style="overflow: auto; word-wrap: break-word; resize: horizontal; height: 600px;">
						</textarea>-->
						<table align="left" width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="88B3E0">
							<tr class="px4">
								<td  align="left" colspan="4" class="fontbold">下载文件列表：</td>
							</tr>
							<tr class="px4">
								<td width="25%" align="right">充值对账文件：</td>
								<td width="25%" >
									<button onclick="gridReload('01')" type="button" class="btn btn-purple btn-sm">
											下载<i class="icon-cloud-download icon-on-right bigger-110"></i>
									</button>
									<button onclick="uploan('01')" type="button" class="btn btn-purple btn-sm">
											导入<i class="icon-cloud-upload icon-on-right bigger-110"></i>
									</button>
								</td>
								<td width="50%" align="left"></td>
							</tr>
							<tr class="px4">
								<td width="25%" align="right">提现对账文件：</td>
								<td width="25%" >
									<button onclick="gridReload('02')" type="button" class="btn btn-purple btn-sm">
											下载<i class="icon-cloud-download icon-on-right bigger-110"></i>
									</button>
									<button onclick="uploan('02')" type="button" class="btn btn-purple btn-sm">
											导入<i class="icon-cloud-upload icon-on-right bigger-110"></i>
									</button>
								</td>
								<td width="50%" align="left"></td>
							</tr>
							<tr class="px4">
								<td width="25%" align="right">标的对账文件：</td>
								<td width="25%" >
									<button onclick="gridReload('03')" type="button" class="btn btn-purple btn-sm">
											下载<i class="icon-cloud-download icon-on-right bigger-110"></i>
									</button>
									<button onclick="uploan('03')" type="button" class="btn btn-purple btn-sm">
											导入<i class="icon-cloud-upload icon-on-right bigger-110"></i>
									</button>
								</td>
								<td width="50%" align="left"></td>
							</tr>
							<tr class="px4">
								<td width="25%" align="right">标的转账对账文件：</td>
								<td width="25%" >
									<button onclick="gridReload('04')" type="button" class="btn btn-purple btn-sm">
											下载<i class="icon-cloud-download icon-on-right bigger-110"></i>
									</button>
									<button onclick="uploan('04')" type="button" class="btn btn-purple btn-sm">
											导入<i class="icon-cloud-upload icon-on-right bigger-110"></i>
									</button>
								</td>
								<td width="50%" align="left"></td>
							</tr>
							<tr class="px4">
								<td width="25%" align="right">托管用户开户对账文件：</td>
								<td width="25%" >
									<button onclick="gridReload('06')" type="button" class="btn btn-purple btn-sm">
											下载<i class="icon-cloud-download icon-on-right bigger-110"></i>
									</button>
									<button onclick="uploan('06')" type="button" class="btn btn-purple btn-sm">
											导入<i class="icon-cloud-upload icon-on-right bigger-110"></i>
									</button>
								</td>
								<td width="50%" align="left"></td>
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
		//查询
		function gridReload(type) {
			var settle_date_p2p = jQuery("#settle_date_p2p").val() || null;
			if(settle_date_p2p==null || settle_date_p2p==""){
				$().toastmessage('showErrorToast', "请输入对账日期");
				return false;
			}
			if(!checkDate(settle_date_p2p)){
				$().toastmessage('showErrorToast', "对账日期格式为:yyyyMMdd");
				return false;
			}
			jQuery.post("${ctx}/reconciliation/dowmLoanFile.json", {settle_date_p2p: settle_date_p2p, settle_type_p2p: type}, function(msg){
				if(msg.ret_code=="0000"){
					$().toastmessage('showSuccessToast', "文件下载成功");
				}else{
					$().toastmessage('showSuccessToast', "文件下载失败");
				}
				// 缩进一个tab
				//$("#result").text(JSON.stringify(msg, null, "\t"));
			}, "json");
			return false;
		}
		
		function uploan(type){
			var settle_date_p2p = jQuery("#settle_date_p2p").val() || null;
			if(settle_date_p2p==null || settle_date_p2p==""){
				$().toastmessage('showErrorToast', "请输入对账日期");
				return false;
			}
			if(!checkDate(settle_date_p2p)){
				$().toastmessage('showErrorToast', "对账日期格式为:yyyyMMdd");
				return false;
			}
			jQuery.post("${ctx}/reconciliation/upLoanFile.json", {settle_date_p2p: settle_date_p2p, settle_type_p2p: type}, function(msg){
				if(msg.code=="0000"){
					$().toastmessage('showSuccessToast', "文件导入成功,导入"+msg.total+"条数据");
				}else{
					$().toastmessage('showErrorToast', msg.desc);
				}
				// 缩进一个tab
				//$("#result").text(JSON.stringify(msg, null, "\t"));
			}, "json");
			return false;
		}

		function checkDate(date){
			var a = /^(\d{4})(\d{2})(\d{2})$/
			if (!a.test(date)) { 
				return false ;
			} else{
				return true; 
			} 
		}
	</script>
</body>
</html>