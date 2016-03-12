<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理列表</title>
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
						<li class="active">用户管理列表</li>
					</ul><!-- .breadcrumb -->

					<!-- nav-search -->
					<%@ include file="/common/search.jsp"%>
				</div>

				<div class="page-content">
					<table style="padding-bottom: 20px">
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
					</table>
					<br>
					<!-- 表格 -->
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
		//查询
		function gridReload() {
			var username = jQuery("#username").val() || null;
			var fullname = jQuery("#fullname").val() || null;
			jQuery("#grid-table").jqGrid('setGridParam', {
				url : "userList.json",
				mtype : "post",
				page : 1,
				postData: {username:username, fullname: fullname}
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
				url : 'userList.json',
				datatype : "json",
				mtype : "post",
				height: 330,
				colNames:['序列','登录名','姓名','手机', '是否禁用', '是否锁定', '密码是否过期', '创建时间'],
				colModel:[
					{
						name:'id',
						index:'id',
						width:40,
						editable: false
					},
					{
						name:'username',
						index:'username',
						width:90,
						editable: false
					},
					{
						name:'fullname',
						index:'fullname',
						width:90,
						editable: false
					},
					{
						name:'mobile',
						index:'mobile',
						width:90,
						editable: false
					},
					{
						name:'enabled',
						index:'enabled',
						width:50,
						editable: false,
						unformat:function(cellvalue, options, rowObject){
							if(cellvalue == "否"){
								return 0;
							}else if(cellvalue == "是"){
								return 1;
							}
							return cellvalue;
						},
						formatter:function(cellvalue, options, rowObject){
							if(cellvalue == 0){
								return "否";
							}else if(cellvalue == 1){
								return "是";
							}
							return cellvalue;
						}
					},
					{
						name:'locked',
						index:'locked',
						width:50,
						editable: false,
						unformat:function(cellvalue, options, rowObject){
							if(cellvalue == "否"){
								return 0;
							}else if(cellvalue == "是"){
								return 1;
							}
							return cellvalue;
						},
						formatter:function(cellvalue, options, rowObject){
							if(cellvalue == 0){
								return "否";
							}else if(cellvalue == 1){
								return "是";
							}
							return cellvalue;
						}
					},
					{
						name:'expired',
						index:'expired',
						width:50,
						editable: false,
						unformat:function(cellvalue, options, rowObject){
							if(cellvalue == "否"){
								return 0;
							}else if(cellvalue == "是"){
								return 1;
							}
							return cellvalue;
						},
						formatter:function(cellvalue, options, rowObject){
							if(cellvalue == 0){
								return "否";
							}else if(cellvalue == 1){
								return "是";
							}
							return cellvalue;
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

				caption: "用户列表",
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
				edit: true,
				editicon : 'icon-pencil blue',
				editfunc: openDialogEdit, // 点击编辑按钮
				add: true,
				addicon : 'icon-plus-sign purple',
				addfunc: openDialogAdd,   // 点击添加按钮
				del: true,
				delicon : 'icon-trash red',
				delfunc: deletefunc,  	  // 点击删除按钮
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

			// 增加自定义按钮
			jQuery(grid_selector)
					.navGrid(pager_selector,{edit:false,add:false,del:false,search:false, refresh: false})
					// 重置密码按钮
					.navButtonAdd(pager_selector,{
						caption:"",
						title: "重置密码",
						buttonicon:"icon-lock purple",
						onClickButton: resetPassword
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



			// 新增对话框
			function openDialogAdd(){
				$("#dialog-add-user" ).load('${ctx}/addUser.html');

				var dialog = $( "#dialog-add-user" ).removeClass('hide').dialog({
					modal: true,
					height: 400,
					width: 550,
					title: "<div class='widget-header widget-header-small'><h4 class='smaller'> 新增用户 </h4></div>",
					title_html: true,
					buttons: [
						{
							text: "提交",
							"class" : "btn btn-primary btn-xs",
							click: function() {
								if(!$('#form-user-add').valid()){
									return false;
								}
								var _dialog = $(this);
								// 表单提交事件
								var data = $("#form-user-add").serialize();
								$.post("${ctx}/addUser", data, function(msg){
									if(msg.code != 200){
										$().toastmessage('showErrorToast', msg.msg);
									}else{
										_dialog.dialog( "close" );
										jQuery(grid_selector).trigger("reloadGrid");
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

			// 编辑对话框
			function openDialogEdit(id){
				$("#dialog-edit-user" ).load('${ctx}/editUser.html?id='+id);

				var dialog = $( "#dialog-edit-user" ).removeClass('hide').dialog({
					modal: true,
					height: 400,
					width: 550,
					title: "<div class='widget-header widget-header-small'><h4 class='smaller'> 修改用户 </h4></div>",
					title_html: true,
					buttons: [
						{
							text: "提交",
							"class" : "btn btn-primary btn-xs",
							click: function() {
								if(!$('#form-user-edit').valid()){
									return false;
								}
								var _dialog = $(this);
								// 表单提交事件
								var data = $("#form-user-edit").serialize();
								$.post("${ctx}/editUser", data, function(msg){
									if(msg.code != 200){
										$().toastmessage('showErrorToast', msg.msg);
									}else{
										_dialog.dialog( "close" );
										jQuery(grid_selector).trigger("reloadGrid");
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
			// 删除事件
			function deletefunc(id){
				$.post("${ctx}/delUser", {id: id[0]}, function(msg){
					if(msg.code != 200){
						$().toastmessage('showErrorToast', msg.msg);
					}else{
						$().toastmessage('showSuccessToast', msg.msg);
						jQuery(grid_selector).trigger("reloadGrid");
					}
				}, "json");
			}

			// 重置密码事件
			function resetPassword(e){
				var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");
				if(selectedIds.length != 1){
					bootbox.alert("请选择一条数据");
					return;
				}
				bootbox.prompt("输入新密码", function(result) {
					if (result === null || result == '') {
						//$().toastmessage('showErrorToast', "密码不能为空");
					} else {
						var id = selectedIds[0];
						$.post("${ctx }/resetPassword", {id: id, password: result}, function(msg){
							if(msg.code != 200){
								$().toastmessage('showErrorToast', msg.msg);
							}else{
								$().toastmessage('showSuccessToast', msg.msg);
							}
						}, "json");
					}
				});
			}
		});
	</script>
</body>
</html>