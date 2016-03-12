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

        <div class="nav-search" id="nav-search">
          <form class="form-search">
									<span class="input-icon">
										<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
										<i class="icon-search nav-search-icon"></i>
									</span>
          </form>
        </div><!-- #nav-search -->
      </div>

      <div class="page-content">
        <div class="row">
          <div class="col-xs-12">
            <form class="form-horizontal" id="form-user-add" role="form" action="${ctx}/addUser" method="post">
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="username"> 用户名 </label>
                <div class="col-sm-9">
                  <div class="clearfix">
                    <input type="text" id="username" name="username" placeholder="用户名" class="col-xs-10 col-sm-5" />
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="password"> 密码 </label>

                <div class="col-sm-9">
                  <input type="password" id="password" name="password" placeholder="密码" class="col-xs-10 col-sm-5" />
                    <span class="help-inline col-xs-12 col-sm-7">
                      <span class="middle">密码为6到10位字符</span>
                    </span>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="fullname"> 真实姓名 </label>
                <div class="col-sm-9">
                  <input type="text" id="fullname" name="fullname" placeholder="真实姓名" class="col-xs-10 col-sm-5" />
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="mobile"> 手机 </label>
                <div class="col-sm-9">
                  <input type="text" id="mobile" name="mobile" placeholder="手机" class="col-xs-10 col-sm-5" />
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"> 选择角色 </label>
                <div class="col-sm-9">

                  <c:forEach items="${roles}" var="role">
                    <div class="checkbox">
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
<!-- basic scripts -->
<%@ include file="/common/basic-script.jsp"%>

<!-- inline scripts related to this page -->
<script src="${ctx }/assets/js/jquery.inputlimiter.1.3.1.min.js"></script>
<script src="${ctx }/assets/js/jquery.maskedinput.min.js"></script>
<script src="${ctx }/assets/js/jquery.validate.min.js"></script>

<!-- inline scripts related to this page -->

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

</body>
</html>

