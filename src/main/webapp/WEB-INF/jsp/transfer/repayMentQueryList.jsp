<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>还款流水列表</title>
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
						<li class="active">还款管理列表</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
						<tr >
							<td> 标的ID:</td>
							<td><input type="text" id="projectId"  width="200px"/></td>
							<td>
								<span class="input-group-btn">
									<button onclick="gridReload()" type="button" class="btn btn-purple btn-sm">
														查询
									<i class="icon-search icon-on-right bigger-110"></i>
									</button>
									<input type="hidden" id = "reqProjectId" name="reqProjectId" value="${reqProjectId}"/>
								</span>
							</td>
						</tr>
					</table>
					<br>
					<!-- 表格 -->
					<table id="grid-table"></table>
					<!-- 分页 -->
					<div id="grid-pager"></div>
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
		var projectId = jQuery("#projectId").val() || null;
		jQuery("#grid-table").jqGrid('setGridParam', {
			url : "${ctx}/transfer/repayMentQueryList.json",
			mtype : "post",
			page : 1,
			postData: {projectId: projectId}
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
			var  reqProjectId = $("#reqProjectId").val();
			jQuery(grid_selector).jqGrid({
				url : '${ctx}/transfer/repayMentQueryList.json',
				datatype : "json",
				mtype : "post",
				height: 330,
				postData: {projectId:reqProjectId},
				colNames:['Id','借款人ID','标的ID','借款合同编号', '应还本金', '应还利息', '已还本金', '已还利息','应还罚息','已还罚息','垫付本金','垫付利息','应还日期','还款日期','创建时间','状态'],
				colModel:[
					{
						name:'id',
						index:'id',
						width:40,
						editable: false
					},
					{
						name:'loanUserId',
						index:'loanUserId',
						width:40,
						editable: false
					},
					{
						name:'projectId',
						index:'projectId',
						width:40,
						editable: false
					},
					{
						name:'loanContractNo',
						index:'loanContractNo',
						width:60,
						editable: false
					},
					{
						name:'duetoReapyCapital',
						index:'duetoReapyCapital',
						width:40,
						editable: false
					},
					{
						name:'duetoReapyInterest',
						index:'duetoReapyInterest',
						width:40,
						editable: false
					},
					{
						name:'paidReapyCapital',
						index:'paidReapyCapital',
						width:40,
						editable: false
					},
					{
						name:'paidReapyInterest',
						index:'paidReapyInterest',
						width:40,
						editable: false
					},
					
					{
						name:'duetoReapyPenalty',
						index:'duetoReapyPenalty',
						width:40,
						editable: false
					},
					{
						name:'paidReapyPenalty',
						index:'paidReapyPenalty',
						width:40,
						editable: false
					},
					
					
					{
						name:'advanceCapital',
						index:'advanceCapital',
						width:40,
						editable: false
					},
					{
						name:'advanceInterest',
						index:'advanceInterest',
						width:40,
						editable: false
					},
					
					{
						name:'duetoRepayDate',
						index:'duetoRepayDate',
						width:60,
						editable:false,
						edittype:"Date",
						formatter: function(value, options, rowObject){
							var date = new Date();
							date.setTime(value);
							return date.Format("yyyy-MM-dd");
						}
					},
					{
						name:'repayTime',
						index:'repayTime',
						width:60,
						editable:false,
						edittype:"Date",
						formatter: function(value, options, rowObject){
							var date = new Date();
							date.setTime(value);
							if(null == value || value == ''){
								return "";
							}else{
								return date.Format("yyyy-MM-dd hh:mm:ss");
							}
						}
					},
					{
						name:'createTime',
						index:'createTime',
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
						name:'status',
						index:'status',
						width:50,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							return formatDim("REPAY_INFO_STATUS", cellvalue);
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

				caption: "还款列表",
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
		});
	</script>
</body>
</html>