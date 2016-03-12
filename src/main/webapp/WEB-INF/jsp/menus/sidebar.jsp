<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<a class="menu-toggler" id="menu-toggler" href="#">
  <span class="menu-text"></span>
</a>
<div class="sidebar" id="sidebar">
  <script type="text/javascript">
    try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
  </script>

  <div class="sidebar-shortcuts" id="sidebar-shortcuts">
    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
      <button class="btn btn-success">
        <i class="icon-signal"></i>
      </button>

      <button class="btn btn-info">
        <i class="icon-pencil"></i>
      </button>

      <button class="btn btn-warning" i>
        <i class="icon-group"></i>
      </button>

      <button class="btn btn-danger">
        <i class="icon-cogs"></i>
      </button>
    </div>

    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
      <span class="btn btn-success"></span>

      <span class="btn btn-info"></span>

      <span class="btn btn-warning"></span>

      <span class="btn btn-danger"></span>
    </div>
  </div><!-- #sidebar-shortcuts -->

  <ul class="nav nav-list">
    <c:forEach items="${menus}" var="menu">
      <li>
        <a href="${ctx}${menu.url}" class="dropdown-toggle">
          <i class="${menu.icon}"></i>
          <span class="menu-text">${menu.name}</span>

          <c:if test="${!empty menu.childrens}">
            <b class="arrow icon-angle-down"></b>
          </c:if>
        </a>
        <c:if test="${!empty menu.childrens}">
          <ul class="submenu">
            <c:forEach items="${menu.childrens}" var="m">
              <li>
                <a href="${ctx}${m.url}">
                  <i class="icon-double-angle-right"></i>
                  ${m.name}
                </a>
              </li>
            </c:forEach>
          </ul>
        </c:if>
      </li>
    </c:forEach>
  </ul>
  <!-- /.nav-list -->

  <div class="sidebar-collapse" id="sidebar-collapse">
    <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
  </div>

  <script type="text/javascript">
    try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
  </script>
</div>