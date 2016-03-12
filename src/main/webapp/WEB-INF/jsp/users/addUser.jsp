<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<div class="row">
  <div class="col-xs-12">
    <form class="form-horizontal" id="form-user-add" role="form" action="${ctx}/addUser" method="post">
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="username"> 用户名 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="username" name="username" placeholder="用户名" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="password"> 密码 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="password" id="password" name="password" placeholder="密码" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="password2"> 密码确认 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="password" id="password2" name="password2" placeholder="密码" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="hr hr-dotted"></div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="fullname"> 真实姓名 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="fullname" name="fullname" placeholder="真实姓名" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="mobile"> 手机 </label>
        <div class="col-sm-9">
          <div class="clearfix">
            <input type="text" id="mobile" name="mobile" placeholder="手机" class="col-xs-10 col-sm-8" />
          </div>
        </div>
      </div>
      <div class="hr hr-dotted"></div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> 选择角色 </label>
        <div class="col-sm-9">
          <c:forEach items="${roles}" var="role">
            <div>
              <label>
                <input name="roleId" class="ace ace-checkbox-2" type="checkbox" value="${role.id}"/>
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

    $('#form-user-add').validate({
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