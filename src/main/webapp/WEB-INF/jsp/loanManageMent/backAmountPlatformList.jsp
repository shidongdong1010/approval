<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户返款管理列表</title>
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
						<li class="active">返款管理列表</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
						<tr >
							<td> 标的名称:</td>
							<td><input type="text" id="projectName" width="200px"/></td>
							<td> 合同编号:</td>
							<td><input type="text" id="loanContractNo"  width="200px"/></td>
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
					<br>
					<!-- 表格 -->
					<table id="grid-table"></table>
					<!-- 分页 -->
					<div id="grid-pager"></div>

					<!-- dialog-addUser -->
					<div id="dialog-invest-list" class="hide">
						<div>
							<table style="width: 100%;">
								<tr>
									<td>标的账户余额</td>
									<td id="localBalance"></td>
									<td>第三方标的余额</td>
									<td id="payBalance"></td>
								</tr>
							</table>
						</div>
						<br/>
						<!-- 表格 -->
						<table id="invest-grid-table"></table>
						<!-- 分页 -->
						<div id="invest-grid-pager"></div>
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
	<!-- form scripts -->
	<%@ include file="/common/form-script.jsp"%>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	//查询
	function gridReload() {
		var projectName = jQuery("#projectName").val() || null;
		var loanContractNo = jQuery("#loanContractNo").val() || null;
		jQuery("#grid-table").jqGrid('setGridParam', {
			url : "backAmountAdvancePlatformList.json",
			mtype : "post",
			page : 1,
			postData: {projectName:projectName, loanContractNo: loanContractNo}
		}).trigger("reloadGrid");
	}
		jQuery(function($) {
			//override dialog's title function to allow for HTML titles
			$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
				_title: function(title) {
					var $title = this.options.title || '&nbsp;'
					if( ("title_html" in this.options) && this.options.title_html == true )
						title.html($title);
					else title.text($title);
				}
			}));

			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
			jQuery(grid_selector).jqGrid({
				url : 'backAmountAdvancePlatformList.json',
				datatype : "json",
				mtype : "post",
				height: 330,
				colNames:['标的Id','借款人姓名','借款人手机','借款合同编号', '借款合同金额', '标的名称', '招的结束时间','应还延迟还款服务费','已还延迟还款服务费','状态','操作'],
				colModel:[
					{
						name:'id',
						index:'id',
						width:40,
						editable: false
					},
					{
						name:'loanName',
						index:'loanName',
						width:90,
						editable: false
					},
					{
						name:'loanMobile',
						index:'loanMobile',
						width:90,
						editable: false
					},
					{
						name:'loanContractNo',
						index:'loanContractNo',
						width:90,
						editable: false
					},
					{
						name:'loanAmount',
						index:'loanAmount',
						width:90,
						editable: false
					},
					{
						name:'projectName',
						index:'projectName',
						width:90,
						editable: false
					},
					{
						name:'endDate',
						index:'endDate',
						width:80,
						editable:false,
						edittype:"Date",
						formatter: function(value, options, rowObject){
							var date = new Date();
							date.setTime(value);
							return date.Format("yyyy-MM-dd hh:mm:ss");
						}
					},
					{
						name:'platformDuetoLateRepayServerAmount',
						index:'platformDuetoLateRepayServerAmount',
						width:90,
						editable: false
					},
					{
						name:'platformPaidLateRepayServerAmount',
						index:'platformPaidLateRepayServerAmount',
						width:90,
						editable: false
					},
					{
						name:'status',
						index:'status',
						width:50,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							return formatDim("PROJECT_STATUS", cellvalue);
						}
					},
					{
						name:'',
						index:'',
						width:50,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							return "<a href=\"#\" data-value=\""+rowObject['id']+"\" class=\"backDialogBtn\">返款</a>";
						}
					}
				],

				viewrecords : true,
				rowNum:10,
				rowList:[5, 10, 15, 20, 50, 200],
				pager : pager_selector,
				altRows: true,
				toppager: false,
				multiselect: true,
				multiboxonly: true,
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						styleCheckbox(table);
						updateActionIcons(table);
						updatePagerIcons(table);
						enableTooltips(table);
					}, 0);
				},

				caption: "返款管理列表",
				autowidth: true

			});

			//enable datepicker
			function pickDate( cellvalue, options, cell ) {
				setTimeout(function(){
					$(cell) .find('input[type=text]')
							.datepicker({format:'yyyy-mm-dd' , autoclose:true});
				}, 0);
			}

			// 修改默认按钮功能
			$(grid_selector).jqGrid("navGrid", pager_selector, {
				edit: false,
				editicon : 'icon-pencil blue',
				add: false,
				addicon : 'icon-plus-sign purple',
				del: false,
				delicon : 'icon-trash red',
				search: false,
				searchicon : 'icon-search orange',
				refresh: true,
				refreshicon : 'icon-refresh green',
				view: true,
				viewicon : 'icon-zoom-in grey',
				alerttext : "请选择一条数据"   // 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息
			},{// 修改编辑相关的参数
			},{// 修改添加相关的参数
			},{// 修改删除相关的参数
			},{// 修改查询相关的参数
			},{// 修改查看相关的参数
			});

			/// 对表格式样式进行修改
			//it causes some flicker when reloading or navigating grid
			//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
			//or go back to default browser checkbox styles for the grid
			function styleCheckbox(table) {
			/**
				$(table).find('input:checkbox').addClass('ace')
				.wrap('<label />')
				.after('<span class="lbl align-top" />')


				$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
				.find('input.cbox[type=checkbox]').addClass('ace')
				.wrap('<label />').after('<span class="lbl align-top" />');
			*/
			}


			//unlike navButtons icons, action icons in rows seem to be hard-coded
			//you can change them like this in here if you want
			function updateActionIcons(table) {
				/**
				var replacement =
				{
					'ui-icon-pencil' : 'icon-pencil blue',
					'ui-icon-trash' : 'icon-trash red',
					'ui-icon-disk' : 'icon-ok green',
					'ui-icon-cancel' : 'icon-remove red'
				};
				$(table).find('.ui-pg-div span.ui-icon').each(function(){
					var icon = $(this);
					var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
					if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
				})
				*/
			}

			//replace icons with FontAwesome icons like above
			function updatePagerIcons(table) {
				var replacement =
				{
					'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
					'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
					'ui-icon-seek-next' : 'icon-angle-right bigger-140',
					'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
				};
				$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
					var icon = $(this);
					var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

					if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
				})
			}

			function enableTooltips(table) {
				$('.navtable .ui-pg-button').tooltip({container:'body'});
				$(table).find('.ui-pg-div').tooltip({container:'body'});
			}
			//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

			$(document).on("click", ".backDialogBtn", function(){
				var id = $(this).attr("data-value");
				openInvestDialog(id);
			});

			//  投资列表对话框
			function openInvestDialog(id){
				var invest_grid_selector = "#invest-grid-table";
				jQuery(invest_grid_selector).jqGrid({
					url : 'backAdvancePlatform.json',
					datatype : "json",
					postData: {projectId: id},
					mtype : "post",
					height: '180',
					width: '640',
					colNames:['应还延迟还款服务费', '已还延迟还款服务费','返款状态'],
					colModel:[
						{
							name: 'repayInfo.duetoLateRepayServerAmount',
							index:'duetoLateRepayServerAmount',
							width:220,
							editable: false
						},{
							name: 'backPlatformInfo.paidLateRepayServerAmount',
							index:'paidLateRepayServerAmount',
							width:220,
							editable: false,
							formatter:function(cellvalue, options, rowObject){
								if(cellvalue == null || "" ==cellvalue){
									return  "0.0";
								}
							}
						},
						{
							name:'backPlatformInfo.status',
							index:'status',
							width:200,
							editable: false,
							formatter:function(cellvalue, options, rowObject){
								return formatDim("BACK_USER_STATUS", cellvalue);
							}
						}
					],
					altRows: true,
					toppager: false,
					multiselect: false,
					multiboxonly: false,
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
					autowidth: true
				});

				jQuery(invest_grid_selector).jqGrid('setGridParam', {
					url : 'backAdvancePlatform.json',
					mtype : "post",
					page : 1,
					postData: {projectId: id}
				}).trigger("reloadGrid");

				// 查询标的余额
				jQuery.post("${ctx}queryProject.json", {projectId: id}, function(msg){
					$("#localBalance").text(msg['localBalance']);
					$("#payBalance").text(msg['payBalance']);
				}, "json");

				var dialog = $( "#dialog-invest-list" ).removeClass('hide').dialog({
					modal: true,
					height: 400,
					width: 750,
					title: "<div class='widget-header widget-header-small'><h4 class='smaller'> 投资列表 </h4></div>",
					title_html: true,
					buttons: [
						{
							text: "返款",
							"class" : "btn btn-primary btn-xs",
							click: function() {
								var _dialog = $(this);
								// 表单提交事件
								var data = {projectId: id};
								$.post("${ctx}/backAmountPlatform.json", data, function(msg){
									if(msg.code != 200){
										$().toastmessage('showErrorToast', msg.msg);
									}else{
										_dialog.dialog( "close" );
										jQuery(invest_grid_selector).trigger("reloadGrid");
										$().toastmessage('showSuccessToast', msg.msg);
									}
								}, "json");
							}
						},{
							text: "取消",
							"class" : "btn btn-xs",
							click: function() {
								$( this ).dialog( "close" );
							}
						}
					]
				});
			}
		});
	</script>
</body>
</html>