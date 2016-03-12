<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<div class="row">
  <div class="col-xs-12">
    <form class="form-horizontal" id="form-user-edit" role="form" action="${ctx}/editUser" method="post">
      <input type="hidden" name="id" id="id" value="${user.id}"/>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="username"> 用户名 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="username" name="username" disabled value="${user.username}" placeholder="用户名" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="fullname"> 真实姓名 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="fullname" name="fullname" value="${user.fullname}" placeholder="真实姓名" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="mobile"> 手机 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="mobile" name="mobile" value="${user.mobile}" placeholder="手机" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="hr hr-dotted"></div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="enabled"> 是否禁用 </label>
        <div class="col-xs-3">
          <label>
            <input id="enabled" name="enabled" ${user.enabled eq '1' ? 'checked' : ''} value="1" class="ace ace-switch ace-switch-5" type="checkbox" />
            <span class="lbl"></span>
          </label>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="expired"> 密码是否过期 </label>
        <div class="col-xs-3">
          <label>
            <input id="expired" name="expired" ${user.expired eq '1' ? 'checked' : ''} value="1" class="ace ace-switch ace-switch-5" type="checkbox" />
            <span class="lbl"></span>
          </label>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="locked"> 是否锁定 </label>
        <div class="col-xs-3">
          <label>
            <input id="locked" name="locked" ${user.locked eq '1' ? 'checked' : ''} value="1" class="ace ace-switch ace-switch-5" type="checkbox" />
            <span class="lbl"></span>
          </label>
        </div>
      </div>
      <div class="hr hr-dotted"></div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> 选择角色 </label>
        <div class="col-xs-3">
          <c:forEach items="${roles}" var="role">
            <c:set var="checked" value="false"/>
            <c:forEach items="${userroles}" var="r">
              <c:if test="${role.id eq r.id}">
                <c:set var="checked" value="true"/>
              </c:if>
            </c:forEach>
            <div>
              <label>
                <input name="roleId" class="ace ace-checkbox-2" ${checked ? 'checked': ''} type="checkbox" value="${role.id}"/>
                <span class="lbl"> ${role.name} </span>
              </label>
            </div>
          </c:forEach>
        </div>
      </div>
    </form>
  </div>
</div>
<script type="text/javascript">
  jQuery(function($) {

    //documentation : http://docs.jquery.com/Plugins/Validation/validate
    jQuery.validator.addMethod("phone", function (value, element) {
      return this.optional(element) || /^\d{11}$/.test(value);
    }, "Enter a valid phone number.");

    $('#form-user-edit').validate({
      errorElement: 'div',
      errorClass: 'help-block',
      focusInvalid: false,
      rules: {
        username: {
          required: true,
          minlength:5,
          maxlength:20
        },
        password: {
          required: true,
          minlength: 5,
          maxlength:20
        },
        password2: {
          required: true,
          minlength: 5,
          maxlength:20,
          equalTo: "#password"
        },
        fullname: {
          required: true
        },
        mobile: {
          required: false,
          phone: 'required'
        },
        roleId: {
          required: true
        }
      },

      messages: {
        username: {
          required: "用户名不能为空",
          minlength: "用户名长度为5-20位",
          maxlength: "用户名长度为5-20位"
        },
        password: {
          required: "密码不能为空",
          minlength: "密码长度为5-20位",
          maxlength: "密码长度为5-20位"
        },
        password2: {
          required: "确认密码不能为空",
          minlength: "确认密码长度为5-20位",
          maxlength: "确认密码长度为5-20位",
          equalTo: "两次密码必须一致"
        },
        fullname:{
          required: "真实姓名不能为空",
        },
        mobile:{
          phone: "输入正确的手机号码格式"
        },
        roleId: "至少选择一个角色"
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