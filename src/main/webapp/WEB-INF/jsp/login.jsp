<%--
  Created by IntelliJ IDEA.
  User: dongdongshi
  Date: 16/1/13
  Time: 下午10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="utf-8" />
  <title>登录页面 - 账务管理系统</title>
  <!-- css & js  -->
  <%@ include file="/common/header.jsp"%>
</head>

<body class="login-layout">
<div class="main-container">
  <div class="main-content">
    <div class="row">
      <div class="col-sm-10 col-sm-offset-1" style="margin-top: 5%;">
        <div class="login-container">
          <div class="center">
            <h1>
              <i class="icon-leaf green"></i>
              <span class="red">爱碳邦</span>
              <span class="white">账务管理系统</span>
            </h1>
            <h4 class="blue">&copy; 爱康富罗纳金融信息服务（上海）有限公司</h4>
          </div>

          <div class="space-6"></div>

          <div class="position-relative">
            <div id="login-box" class="login-box visible widget-box no-border">
              <div class="widget-body">
                <div class="widget-main">
                  <h4 class="header blue lighter bigger">
                    <i class="icon-coffee green"></i>
                    请输入您的信息
                  </h4>

                  <div class="space-6"></div>

                  <form action="<c:url value='/login' />" method="post">
                    <fieldset>
                      <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" name="username" class="form-control" placeholder="用户名" />
															<i class="icon-user"></i>
														</span>
                      </label>

                      <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" name="password" class="form-control" placeholder="密码" />
															<i class="icon-lock"></i>
														</span>
                      </label>

                      <div class="space"></div>

                      <div class="clearfix">
                        <label class="inline">
                          <input type="checkbox" name="remember-me" value="true" class="ace" />
                          <span class="lbl"> 记住密码</span>
                        </label>

                        <button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
                          <i class="icon-key"></i>
                          登陆
                        </button>
                      </div>

                      <div class="space-4"></div>
                    </fieldset>
                  </form>

                  ${error}
                  <div class="social-or-login center">
                    <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}">
                      <div class="error"><spring:message code="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}"/></div>
                    </c:if>
                  </div>

                </div><!-- /widget-main -->

                <div class="toolbar clearfix">
                  <div>
                    <a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
                      <i class="icon-arrow-left"></i>
                      我忘记了我的密码
                    </a>
                  </div>

                  <div>
                    <a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link">
                      我要注册
                      <i class="icon-arrow-right"></i>
                    </a>
                  </div>
                </div>
              </div><!-- /widget-body -->
            </div><!-- /login-box -->

            <div id="forgot-box" class="forgot-box widget-box no-border">
              <div class="widget-body">
                <div class="widget-main">
                  <h4 class="header red lighter bigger">
                    <i class="icon-key"></i>
                    Retrieve Password
                  </h4>

                  <div class="space-6"></div>
                  <p>
                    Enter your email and to receive instructions
                  </p>

                  <form>
                    <fieldset>
                      <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
                      </label>

                      <div class="clearfix">
                        <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                          <i class="icon-lightbulb"></i>
                          Send Me!
                        </button>
                      </div>
                    </fieldset>
                  </form>
                </div><!-- /widget-main -->

                <div class="toolbar center">
                  <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                    Back to login
                    <i class="icon-arrow-right"></i>
                  </a>
                </div>
              </div><!-- /widget-body -->
            </div><!-- /forgot-box -->

            <div id="signup-box" class="signup-box widget-box no-border">
              <div class="widget-body">
                <div class="widget-main">
                  <h4 class="header green lighter bigger">
                    <i class="icon-group blue"></i>
                    New User Registration
                  </h4>

                  <div class="space-6"></div>
                  <p> Enter your details to begin: </p>

                  <form>
                    <fieldset>
                      <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
                      </label>

                      <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="icon-user"></i>
														</span>
                      </label>

                      <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="icon-lock"></i>
														</span>
                      </label>

                      <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Repeat password" />
															<i class="icon-retweet"></i>
														</span>
                      </label>

                      <label class="block">
                        <input type="checkbox" class="ace" />
														<span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
                      </label>

                      <div class="space-24"></div>

                      <div class="clearfix">
                        <button type="reset" class="width-30 pull-left btn btn-sm">
                          <i class="icon-refresh"></i>
                          Reset
                        </button>

                        <button type="button" class="width-65 pull-right btn btn-sm btn-success">
                          Register
                          <i class="icon-arrow-right icon-on-right"></i>
                        </button>
                      </div>
                    </fieldset>
                  </form>
                </div>

                <div class="toolbar center">
                  <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                    <i class="icon-arrow-left"></i>
                    Back to login
                  </a>
                </div>
              </div><!-- /widget-body -->
            </div><!-- /signup-box -->
          </div><!-- /position-relative -->
        </div>
      </div><!-- /.col -->
    </div><!-- /.row -->
  </div>
</div><!-- /.main-container -->

<!-- basic scripts -->
<%@ include file="/common/basic-script.jsp"%>

<!-- inline scripts related to this page -->

<script type="text/javascript">
  function show_box(id) {
    jQuery('.widget-box.visible').removeClass('visible');
    jQuery('#'+id).addClass('visible');
  }
</script>

</body>
</html>