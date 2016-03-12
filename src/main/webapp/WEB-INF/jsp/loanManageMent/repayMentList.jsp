<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>还款管理列表</title>
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
	//查询企业账户的金额
	function queryCompanyAmount(id){
		 $.ajaxSetup({
			  async: false
		 });
		 $.post("repay/queryCompanyAmount.json", {id:id}, function(msg){
			 if(msg.code != 200){
		          $().toastmessage('showErrorToast', msg.msg);
		        }else{
		        	openReapayPage(msg);
		        }
		      }, "json");
	}
	
	//打开还款页面
	function goReapayPage(id){
		queryCompanyAmount(id);
	}
	
	//打开还款弹窗
	function openReapayPage(msg){
		bootbox.dialog({
			title : "还款操作",
		    message : "<div class='well ' style='margin-top:25px;'>"+
		    "<form action='' class='form-horizontal' role='form' id='repayFrom' target='_blank' method='post'>"+
		    "<div class='form-group'>"+
		    "<input type='hidden' id='id' name='id' value="+msg.data['id']+"/>"+
		    "<label class='col-sm-3 control-label no-padding-right' for='sumDuetoAmount'>应还金额:</label>"+
		    "<div class='col-sm-9'>"+
		    "<div style='color:red' class='col-xs-10 col-sm-5'>"+msg.data['sumDuetoAmount']+"</div>"+
		    "</div></div>"+
		    
		    "<div class='form-group'>"+
		    "<label class='col-sm-3 control-label no-padding-right' for='sumPaidAmount'>已还金额:</label>"+
		    "<div class='col-sm-9'>"+
		    "<div style='color:red' class='col-xs-10 col-sm-5'>"+msg.data['sumPaidAmount']+"</div>"+
		    "</div></div>"+
		    
		    "<div class='form-group'>"+
		    "<label class='col-sm-3 control-label no-padding-right' for='balance'>第三方账户金额:</label>"+
		    "<div class='col-sm-9'>"+
		    "<div style='color:red' class='col-xs-10 col-sm-5'>"+msg.data['balance']+"</div>"+
		    "</div></div>"+
		    
		    "<div class='form-group'>"+
		    "<label class='col-sm-3 control-label no-padding-right' for='localBalance'>本地余额:</label>"+
		    "<div class='col-sm-9'>"+
		    "<div style='color:red' class='col-xs-10 col-sm-5'>"+msg.data['localBalance']+"</div>"+
		    "</div></div>"+
		    
		    "<div class='form-group'>"+
		    "<label class='col-sm-3 control-label no-padding-right' for='repayMentAmount'>还款金额</label>"+
		    "<div class='col-sm-9'>"+
		    "<input type='text' id='repayMentAmount' placeholder='请输入还款金额'  value="+msg.data['repayMentAmount']+" class='col-xs-10 col-sm-5'/>"+
		    "</div></div>"+
		    "</form></div>",
		    buttons : {
		        "success" : {
		            "label" : "<i class='icon-ok'></i> 还款",
		            "className" : "btn-sm btn-success",
		            "callback" : function() {
		            	var  id = msg.data['id'];//还款ID
		            	var  repayMentAmount = $("#repayMentAmount").val();//还款金额
		            	var  balance = msg.data['balance'];//第三方余额
		            	var  localBalance = msg.data['localBalance'];//本地余额
		            	var sumDuetoAmount = msg.data['sumDuetoAmount'];//应还金额
		            	if(parseFloat(balance)!=parseFloat(localBalance)){
		            		//第三方账户余额与系统账户余额不匹配
		            		bootbox.alert("第三方账户余额与系统账户余额不匹配!");
		                    return false;
		            	}
		                if(repayMentAmount == "" ){
		                    bootbox.alert("请输入还款金额!");
		                    return false;
		                }
		                if(parseFloat(repayMentAmount) > parseFloat(localBalance) ){
		                    bootbox.alert("还款金额大于第三方账户余额!");
		                    return false;
		                }
		                if(parseFloat(repayMentAmount) > parseFloat(sumDuetoAmount)){
		                	 bootbox.alert("还款金额大于应还金额!");
			                 return false;
		                }
		             $.ajaxSetup({
		      			  async: false
		      		 });
		      		 $.post("doRepayMent.json", {id:id,repayAmount:repayMentAmount}, function(msg){
		      			 if(msg.code != 200){
		      					bootbox.alert("还款申请提交失败。");
		      		        }else{
		      		        	bootbox.alert("还款金额申请提交成功。");
		      		        }
		      		      }, "json");
		            }
		        },
		        "cancel" : {
		            "label" : "<i class='icon-info'></i> 取消",
		            "className" : "btn-sm btn-danger",
		            "callback" : function() { }
		        }
		    }
		}); 
	}
	//查询
	function gridReload() {
		var loanContractNo = jQuery("#loanContractNo").val() || null;
		jQuery("#grid-table").jqGrid('setGridParam', {
			url : "repayMentList.json",
			mtype : "post",
			page : 1,
			postData: {loanContractNo: loanContractNo}
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
				url : 'repayMentList.json',
				datatype : "json",
				mtype : "post",
				height: 330,
				colNames:['Id','借款人ID','借款合同编号', '应还本金', '应还利息', '已还本金', '已还利息','应还罚息','已还罚息','垫付本金','垫付利息','应还日期','还款日期','创建时间','状态','操作'],
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
						name:'loanContractNo',
						index:'loanContractNo',
						width:90,
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
					},
					{
						name:'status',
						index:'status',
						width:50,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							if(cellvalue == '0'){
								//正常还款
								return "<a href=\"javascript: goReapayPage("+rowObject['id']+")\">正常还款</a>";
							}else if(cellvalue == '2'){
								//逾期还款
								return "<a href=\"javascript: goReapayPage("+rowObject['id']+")\">逾期还款</a>";
							}else if(cellvalue =='3'){
								//逾期已垫付
								return "<a href=\"javascript: goReapayPage("+rowObject['id']+")\">逾期已垫付还款</a>";
							}else{
								return "";
							}
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

				caption: "还款管理列表",
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