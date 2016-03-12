<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<div class="row">
  <div class="col-xs-12">
    <form class="form-horizontal" id="form-menu-edit" role="form" action="${ctx}/editUser" method="post">
      <input type="hidden" name="id" id="id" value="${menu.id}"/>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="name"> 菜单名称 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="name" name="name"  value="${menu.name}" placeholder="菜单名称" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="url"> 菜单路径</label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="url" name="url" value="${menu.url}" placeholder="菜单路径" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="sort"> 排序</label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="sort" name="sort" value="${menu.sort}" placeholder="排序" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
       <!-- 修改菜单图标 -->
       <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="icon"> 图标 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <select name="icon" id="icon" >
					<option value="">--请选择--</option>
					<option value="icon-edit">
					<i class="icon-edit"></i>
						icon-edit
					</option>

											<option value="icon-envelope">
												<i class="icon-envelope"></i>
												icon-envelope
											</option>

											<option value="icon-envelope-alt">
												<i class="icon-envelope-alt"></i>
												icon-envelope-alt
											</option>

											<option value="icon-exchange">
												<i class="icon-exchange"></i>
												icon-exchange
											</option>

											<option value="icon-exclamation-sign">
												<i class="icon-exclamation-sign"></i>
												icon-exclamation-sign
											</option>

											<option value="icon-external-link">
												<i class="icon-external-link"></i>
												icon-external-link
											</option>

											<option value="icon-eye-close">
												<i class="icon-eye-close"></i>
												icon-eye-close
											</option>

											<option value="icon-eye-open">
												<i class="icon-eye-open"></i>
												icon-eye-open
											</option>

											<option value="icon-facetime-video">
												<i class="icon-facetime-video"></i>
												icon-facetime-video
											</option>

											<option value="icon-fighter-jet">
												<i class="icon-fighter-jet"></i>
												icon-fighter-jet
											</option>

											<option value="icon-film">
												<i class="icon-film"></i>
												icon-film
											</option>

											<option value="icon-filter">
												<i class="icon-filter"></i>
												icon-filter
											</option>

											<option value="icon-fire">
												<i class="icon-fire"></i>
												icon-fire
											</option>

											<option value="icon-flag">
												<i class="icon-flag"></i>
												icon-flag
											</option>

											<option value="icon-folder-close">
												<i class="icon-folder-close"></i>
												icon-folder-close
											</option>

											<option value="icon-folder-open">
												<i class="icon-folder-open"></i>
												icon-folder-open
											</option>

											<option value="icon-folder-close-alt">
												<i class="icon-folder-close-alt"></i>
												icon-folder-close-alt
											</option>

											<option value="icon-folder-open-alt">
												<i class="icon-folder-open-alt"></i>
												icon-folder-open-alt
											</option>

											<option value="icon-food">
												<i class="icon-food"></i>
												icon-food
											</option>
										
											<option value="icon-gift">
												<i class="icon-gift"></i>
												icon-gift
											</option>

											<option value="icon-glass">
												<i class="icon-glass"></i>
												icon-glass
											</option>

											<option value="icon-globe">
												<i class="icon-globe"></i>
												icon-globe
											</option>

											<option value="icon-group">
												<i class="icon-group"></i>
												icon-group
											</option>

											<option value="icon-hdd">
												<i class="icon-hdd"></i>
												icon-hdd
											</option>

											<option value="icon-headphones">
												<i class="icon-headphones"></i>
												icon-headphones
											</option>

											<option value="icon-heart">
												<i class="icon-heart"></i>
												icon-heart
											</option>

											<option value="icon-heart-empty">
												<i class="icon-heart-empty"></i>
												icon-heart-empty
											</option>

											<option value="icon-home">
												<i class="icon-home"></i>
												icon-home
											</option>

											<option value="icon-inbox">
												<i class="icon-inbox"></i>
												icon-inbox
											</option>

											<option value="icon-info-sign">
												<i class="icon-info-sign"></i>
												icon-info-sign
											</option>

											<option value="icon-key">
												<i class="icon-key"></i>
												icon-key
											</option>

											<option value="icon-leaf">
												<i class="icon-leaf"></i>
												icon-leaf
											</option>

											<option value="icon-laptop">
												<i class="icon-laptop"></i>
												icon-laptop
											</option>

											<option value="icon-legal">
												<i class="icon-legal"></i>
												icon-legal
											</option>

											<option value="icon-lemon">
												<i class="icon-lemon"></i>
												icon-lemon
											</option>

											<option value="icon-lightbulb">
												<i class="icon-lightbulb"></i>
												icon-lightbulb
											</option>

											<option value="icon-lock">
												<i class="icon-lock"></i>
												icon-lock
											</option>

											<option value="icon-unlock">
												<i class="icon-unlock"></i>
												icon-unlock
											</option>
									
			</select>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="icon">系统ID </label>
        <div class="col-sm-9">
          <div class="clearfix">
          	<ak:select key="MENU_SYS_ID" className="input-medium" value="${menu.sysId}" tips="--请选择--" name="sysId" id="sysId" />	
         </div>
         </div>
       </div>
    </form>
  </div>
</div>
<script type="text/javascript">
  jQuery(function($) {
    //documentation : http://docs.jquery.com/Plugins/Validation/validate
    var  icon = "${menu.icon}";
    $("#icon").val(icon);
    $('#form-menu-edit').validate({
      errorElement: 'div',
      errorClass: 'help-block',
      focusInvalid: false,
      rules: {
    	  name: {
          required: true,
          minlength:3,
          maxlength:20
        }
      },
      messages: {
    	  name: {
          required: "菜单名称不能为空",
          minlength: "菜单名称长度为3-20位",
          maxlength: "菜单名称长度为3-20位"
        }
      },

      invalidHandler: function (event, validator) { //display error alert on form submit
        $('.alert-danger', $('.login-form')).show();
      },

      highlight: function (e) {
        $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
      },

      success: function (e) {
        $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
        $(e).remove();
      },

      errorPlacement: function (error, element) {
        if(element.is(':checkbox') || element.is(':radio')) {
          var controls = element.closest('div[class*="col-"]');
          if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
          else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
        }
        else if(element.is('.select2')) {
          error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
        }
        else if(element.is('.chosen-select')) {
          error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
        }
        else error.insertAfter(element.parent());
      },

      submitHandler: function (form) {
      },
      invalidHandler: function (form) {
      }
    });
  });
</script>
