<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>标的信息列表</title>
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
			<input type="hidden"  id="parentIds" name="parentIds">
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
						<li class="active">标的信息列表</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
						<tr >
							<td> 合同编号:</td>
							<td><input type="text" id="loanContractNo" name="loanContractNo" width="200px"/></td>
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
					
					<!-- dialog-query-capitalFlowInfo-->
					<div id="dialog-query-projectInfo" class="hide">
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
	//前往投资页面
	function  goInvestList(id){
		window.location.href="${ctx}/transfer/investInfoList.html?reqProjectId="+id;
	}
	//前往还款页面
	function goRepayList(id){
		window.location.href="${ctx}/transfer/repayMentQueryList.html?reqProjectId="+id;
	}
	// 查询本地信息与第三方信息
	function queryUserInfo(id){
		$("#dialog-query-userInfo").empty();
		$("#dialog-query-userInfo").load('${ctx}/transfer/queryUserInfo.html?id='+id);
		var dialog = $("#dialog-query-userInfo" ).removeClass('hide').dialog({
			modal: true, 
			height: 400,
			width: 750,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'>查看用户详细信息</h4></div>",
			title_html: true,
			buttons: [
				{
					text: "取消",
					"class" : "btn btn-xs",
					click: function() {
						$( this ).dialog( "close" );
					}
				}
			]
		});
	}
	
		function gridReload() {
			var loanContractNo = $("#loanContractNo").val();//手机号码
			jQuery("#grid-table").jqGrid('setGridParam', {
				url : "${ctx}/transfer/projectInfoList.json",
				mtype : "post",
				page : 1,
				postData: {loanContractNo:loanContractNo}
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
				url : '${ctx}/transfer/projectInfoList.json',
				datatype : "json",
				mtype : "post",
				height: 330,
				colNames:['标的ID','申请件ID','借款人姓名','借款人类型','借款人手机','借款人合同编号','借款合同金额','贷款利率(年)','补贴利率(年)','收益率(年)','标的名称','标的状态','活动类型','创建时间','操作'],
				colModel:[
					{
						name:'id',
						index:'id',
						width:40,
						editable: false
					},
					{
						name:'appId',
						index:'appId',
						width:40,
						editable: false
					},
					{
						name:'loanName',
						index:'loanName',
						width:100,
						editable: false
					},
					{
						name:'loanUserType',
						index:'loanUserType',
						width:60,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							if(cellvalue == '0'){
								return "个人用户";
							}
							if(cellvalue == '1'){
								return "企业用户";
							}
						}
					},
					{
						name:'loanMobile',
						index:'loanMobile',
						width:60,
						editable: false
					},
					{
						name:'loanContractNo',
						index:'loanContractNo',
						width:70,
						editable: false
					},
					{
						name:'loanAmount',
						index:'loanAmount',
						width:70,
						editable: false
					},
					{
						name:'loanRate',
						index:'loanRate',
						width:40,
						editable: false
					},
					{
						name:'addRate',
						index:'addRate',
						width:40,
						editable: false
					},
					{
						name:'returnRate',
						index:'returnRate',
						width:40,
						editable: false
					},
					{
						name:'projectName',
						index:'projectName',
						width:130,
						editable: false
					},
					{
						name:'status',
						index:'status',
						width:60,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							return  formatDim("PROJECT_STATUS",cellvalue)
						}
					},
					{
						name:'activityType',
						index:'activityType',
						width:60,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							if(cellvalue == '0' || cellvalue == null || cellvalue == ""){
								return "无活动";
							}
							if(cellvalue == '1'){
								return "新手体验";
							}
							if(cellvalue == '2'){
								return "亲友推";
							}
						}
					},
					{
						name:'createTime',
						index:'createTime',
						width:80,
						editable: false,
						edittype:"Date",
						formatter: function(value, options, rowObject){
							var date = new Date();
							date.setTime(value);
						    return date.Format("yyyy-MM-dd hh:mm:ss");
						}
					},
					{
						name:'',
						index:'',
						width:90,
						editable: false,
						formatter: function(value, options, rowObject){
							return "<a href=\"javascript: goInvestList("+rowObject['id']+")\">投资记录</a>&nbsp;<a href=\"javascript: goRepayList("+rowObject['id']+")\">还款记录</a>";
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
				caption: "标的信息列表",
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
				add: false,
				del: false,
				search: false,
				refresh: true,
				view: false
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



		});
	</script>
</body>
</html>