<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>企业账户信息列表</title>
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
						<li class="active">企业账户信息列表</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<!-- <table style="padding-bottom: 20px">
						<tr >
							<td> 登录名:</td>
							<td><input type="text" id="username" width="200px"/></td>
							<td> 真实姓名:</td>
							<td><input type="text" id="fullname"  width="200px"/></td>
							<td>
								<span class="input-group-btn">
									<button onclick="gridReload()" type="button" class="btn btn-purple btn-sm">
														查询
									<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</td>
						</tr>
					</table> -->
					<br>
					<!-- 表格 -->
					<input type="hidden"  id="balance" name="balance"/>
					<table id="grid-table"></table>
					<!-- 分页 -->
					<div id="grid-pager"></div>

					<!-- dialog-addUser -->
					<div id="dialog-add-user" class="hide">
					</div>
					<!-- dialog-editUser -->
					<div id="dialog-edit-user" class="hide">
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
		//查询企业账户的金额
		function queryCompanyAmount(id){
			 $.ajaxSetup({
				  async: false
			 });
			 $.post("queryCompanyAmount.json", {id:id}, function(msg){
				 if(msg.code != 200){
			          $().toastmessage('showErrorToast', msg.msg);
			        }else{
			        	$("#balance").val(msg.data);
			        }
			      }, "json");
		}
		
		//打开充值页面
		function goRechargePage(id,type,localAmount){
			queryCompanyAmount(id);
			var  balance = $("#balance").val();
			if(type == '1'){
				//充值
				openRechargeDiv(id,balance,localAmount);
			}else{
				//提现
				goWithDrawPage(id,balance,localAmount);
			}
		}
		
		
		//打开提现弹窗
		function goWithDrawPage(id,balance,localAmount){
			bootbox.dialog({
				title : "企业提现",
			    message : "<div class='well ' style='margin-top:25px;'>"+
			    "<form action='' class='form-horizontal' role='form' id='rechargeFrom' target='_blank' method='post'>"+
			    "<div class='form-group'>"+
			    "<label class='col-sm-3 control-label no-padding-right' for='amount'>第三方账户余额:</label>"+
			    "<div class='col-sm-9'>"+
			    "<div style='color:red' class='col-xs-10 col-sm-5'>"+balance+"</div>"+
			    "</div></div>"+
			    "<div class='form-group'>"+
			    "<label class='col-sm-3 control-label no-padding-right' for='amount'>提现金额</label>"+
			    "<div class='col-sm-9'>"+
			    "<input type='text' id='amount' placeholder='请输入提现金额' class='col-xs-10 col-sm-5' />"+
			    "</div></div>"+
			    "</form></div>",
			    buttons : {
			        "success" : {
			            "label" : "<i class='icon-ok'></i> 提现",
			            "className" : "btn-sm btn-success",
			            "callback" : function() {
			            	var  companyId = id;
			            	var  amount = $("#amount").val();
			            	if(parseFloat(balance)!=parseFloat(localAmount)){
			            		//第三方账户余额与系统账户余额不匹配
			            		bootbox.alert("第三方账户余额与系统账户余额不匹配!");
			                    return false;
			            	}
			                if(amount == "" ){
			                    bootbox.alert("请输入提现金额!");
			                    return false;
			                }
			            	$('#rechargeFrom')[0].action = "${ctx }/pay/withdraw/enterprise.html?companyId="+companyId+"&amount="+amount;
			            	$("#rechargeFrom").submit();
			            	//打开提示页面
			            	bootbox.dialog({
								message: "<span class='bigger-110'>请确认你的提现操作</span>",
								buttons: 			
								{
									"提现成功" :
									 {
										"label" : "提现成功",
										"className" : "btn-sm ",
										"callback": function() {
											loadJqGrid();
										}
									},
									"提现失败" :
									{
										"label" : "提现失败",
										"className" : "btn-sm",
										"callback": function() {
											loadJqGrid();
										}
									}
								}
							});
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
		
		//刷新列表
		function  loadJqGrid(){
			jQuery("#grid-table").jqGrid('setGridParam', {
				url : "companyInfoList.json",
				mtype : "post",
				page : 1,
				postData: null
			}).trigger("reloadGrid");
		}
		//打开充值弹窗
		function openRechargeDiv(id,balance,localAmount){
			bootbox.dialog({
				title : "企业充值",
			    message : "<div class='well ' style='margin-top:25px;'>"+
			    "<form action='' class='form-horizontal' role='form' id='rechargeFrom' target='_blank' method='post'>"+
			    "<div class='form-group'>"+
			    "<label class='col-sm-3 control-label no-padding-right' for='amount'>第三方账户余额:</label>"+
			    "<div class='col-sm-9'>"+
			    "<div style='color:red' class='col-xs-10 col-sm-5'>"+balance+"</div>"+
			    "</div></div>"+
			    "<div class='form-group'>"+
			    "<input type='hidden' id='companyId'  value='"+id+"'>"+
			    "<label class='col-sm-3 control-label no-padding-right' for='pay_type'>支付方式</label>"+
			    "<div class='col-sm-9'>"+
			    "<select id='pay_type'><option value=''>--请选择--</option><option  value='B2BBANK'>--企业网银--</option><option  value='B2CDEBITBANK'>--个人借记卡网银--</option></select>"+
			    "</div>"+
			    "</div>"+
			    "<div class='form-group'>"+
			    "<label class='col-sm-3 control-label no-padding-right' for='amount'>充值金额</label>"+
			    "<div class='col-sm-9'>"+
			    "<input type='text' id='amount' placeholder='请输入充值金额' class='col-xs-10 col-sm-5' />"+
			    "</div></div>"+
			    "</form></div>",
			    buttons : {
			        "success" : {
			            "label" : "<i class='icon-ok'></i> 充值",
			            "className" : "btn-sm btn-success",
			            "callback" : function() {
			            	var  companyId = $("#companyId").val();
			            	var  pay_type =$("#pay_type").val();
			            	var  amount = $("#amount").val();
			            	if(parseFloat(balance)!=parseFloat(localAmount)){
			            		//第三方账户余额与系统账户余额不匹配
			            		bootbox.alert("第三方账户余额与系统账户余额不匹配!");
			                    return false;
			            	}
			            	if(pay_type == ""){
			                    bootbox.alert("请选择支付方式!");
			                    return false;
			                }
			                if(amount == "" ){
			                    bootbox.alert("请输入充值金额!");
			                    return false;
			                }
			            	$('#rechargeFrom')[0].action = "${ctx }/pay/recharge/enterprise.html?companyId="+companyId+"&pay_type="+pay_type+"&amount="+amount;
			            	$("#rechargeFrom").submit();
			            	//打开提示页面
			            	bootbox.dialog({
								message: "<span class='bigger-110'>请确认你的充值操作</span>",
								buttons: 			
								{
									"充值成功" :
									 {
										"label" : "充值成功",
										"className" : "btn-sm ",
										"callback": function() {
											loadJqGrid();
										}
									},
									"充值失败" :
									{
										"label" : "充值失败",
										"className" : "btn-sm",
										"callback": function() {
											loadJqGrid();
										}
									}
								}
							});
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
				url : 'companyInfoList.json',
				datatype : "json",
				mtype : "post",
				height: 330,
				colNames:['企业账户Id','企业账户代码','企业名称','第三方支付账户ID', '第三方支付账户号', '创建时间', '状态', '类型','企业账户余额','操作'],
				colModel:[
					{
						name:'id',
						index:'id',
						width:40,
						editable: false
					},
					{
						name:'code',
						index:'code',
						width:90,
						editable: false
					},
					{
						name:'name',
						index:'name',
						width:90,
						editable: false
					},
					{
						name:'payId',
						index:'payId',
						width:90,
						editable: false
					},
					{
						name:'payNo',
						index:'payNo',
						width:90,
						editable: false
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
							if(cellvalue == '0'){
								return "启用";
							}else{
								return "禁用";
							}
						}
					},
					{
						name:'type',
						index:'type',
						width:50,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							return formatDim("COMPANY_TYPE", cellvalue);
						}
					},
					{
						name:'balance',
						index:'balance',
						width:90,
						editable: false
					},
					{
						name:'',
						index:'',
						width:50,
						editable: false,
						formatter:function(cellvalue, options, rowObject){
							return "<a href=\"javascript: goRechargePage("+rowObject['id']+",'1',"+rowObject['balance']+")\">充值</a> &nbsp;<a href=\"javascript: goRechargePage("+rowObject['id']+",'2',"+rowObject['balance']+")\">提现</a>";
							
						}
					},
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

				caption: "企业账户列表",
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