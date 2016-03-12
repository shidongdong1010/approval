<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>资金流水列表</title>
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
						<li class="active">资金流水列表</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
						<tr >
							<td> 资金账户托管平台账户号:</td>
							<td><input type="text" id="account_id" name="account_id" width="200px"/></td>
							<td> 账户类型:</td>
							<td>
								<select id="account_type" name="account_type" >
									<option value="01">个人</option>
									<option value="02">商户</option>
									<option value="03">标的</option>
								</select>
							</td>
							<td> 交易开始日期:</td>
							<td><input type="text" id="start_date" name="start_date" width="200px" onkeyup="value=value.replace(/[^\d]/g,'')"/></td>
							<td> 交易结束日期:</td>
							<td><input type="text" id="end_date" name="end_date" width="200px" onkeyup="value=value.replace(/[^\d]/g,'')"/>
							<input type="hidden" id="logo" name="logo" width="200px" value="1"/></td>
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
					<div id="dialog-add-menu" class="hide">
					</div>
					<!-- dialog-edit menu-->
					<div id="dialog-edit-menu" class="hide">
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
		var account_id = jQuery("#account_id").val() || null;
		var account_type = jQuery("#account_type").val() || null;
		var start_date = jQuery("#start_date").val() || null;
		var end_date = jQuery("#end_date").val() || null;
		var logo = jQuery("#logo").val() || null;
		if(null==account_id ||""==account_id){
			$().toastmessage('showErrorToast', "请输入资金账户托管平台账户号");
			return false;
		}
		if(null==start_date ||""==start_date){
			$().toastmessage('showErrorToast', "请输入交易开始日期");
			return false;
		}else{
			if(!checkDate(start_date)){
				$().toastmessage('showErrorToast', "交易开始日期格式为:yyyyMMdd");
				return false;
			}
		}
		if(null==end_date ||""==end_date){
			$().toastmessage('showErrorToast', "请输入交易结束日期");
			return false;
		}else{
			if(!checkDate(end_date)){
				$().toastmessage('showErrorToast', "交易结束日期格式为:yyyyMMdd");
				return false;
			}
		}
		if(start_date>end_date){
			$().toastmessage('showErrorToast', "交易开始日期不能大于交易结束日期");
			return false;
		}
		if(dateDiff(start_date,end_date)>31){
			$().toastmessage('showErrorToast', "交易开始日期与交易结束日期不能间隔超过31天");
			return false;
		}
		jQuery("#grid-table").jqGrid('setGridParam', {
			url : "${ctx}/search/accountFlowSearchList.json",
			mtype : "post",
			page : 1,
			postData: {account_id:account_id,account_type: account_type,start_date:start_date,end_date:end_date,logo:logo}
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
				url : '${ctx}/search/accountFlowSearchList.json?pageNumber=1',
				datatype : "json",
				mtype : "post",
				height: 330,
				colNames:['交易记账日期','交易金额','资金类型','该笔交易后余额','借贷方向','商户订单日期', '商户订单号', '交易日期', '交易状态', '交易时间', '交易代码'],
				colModel:[
					{
						name:'acc_check_date',
						index:'acc_check_date',
						width:90,
						editable: false
					},
					{
						name:'amount',
						index:'amount',
						width:80,
						editable: false
					},
					{
						name:'amt_type',
						index:'amt_type',
						width:40,
						editable: false
					},
					{
						name:'balance',
						index:'balance',
						width:80,
						editable: false
					},
					{
						name:'dc_mark',
						index:'dc_mark',
						width:90,
						editable: false
					},
					{
						name:'order_date',
						index:'order_date',
						width:50,
						editable: false
					},
					{
						name:'order_id',
						index:'order_id',
						width:90,
						editable: false
					},
					{
						name:'trans_date',
						index:'trans_date',
						width:60,
						editable: false
					},
					{
						name:'trans_state',
						index:'trans_state',
						width:50,
						editable: false
					},
					{
						name:'trans_time',
						index:'trans_time',
						width:60,
						editable: false
					},{
						name:'trans_type',
						index:'trans_type',
						width:50,
						editable: false
					}
				],

				viewrecords : true,
				rowNum:10,
				rowList:[10],
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
				caption: "账户流水表",
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
		
		function checkDate(date){
			var a = /^(\d{4})(\d{2})(\d{2})$/
			if (!a.test(date)) { 
				return false ;
			} else{
				return true; 
			} 
		}
		function  dateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
		    var    oDate1,  oDate2,  iDays  ;
		    oDate1  =  new  Date(sDate1.substring(4,6)  +  '-'  +  sDate1.substring(6,8)  +  '-'  +  sDate1.substring(0,4)); //转换为12-18-2006格式   
		    oDate2  =  new  Date(sDate2.substring(4,6)  +  '-'  +  sDate2.substring(6,8)  +  '-'  +  sDate2.substring(0,4)) ; 
		    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24); //把相差的毫秒数转换为天数  
		    return  iDays;
	   }  
	</script>
</body>
</html>