<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>

 <style>
 .profile-info-name {
    position: absolute;
    width: 130px;
    }
 </style>
<div class="col-xs-12 col-sm-9">
	<h4 class="blue">
		<span class="middle">请核查信息后操作：</span>
	</h4>
	<div class="profile-user-info">
		<div class="profile-info-row">
			<div class="profile-info-name"> 标的联动余额： </div>
			<div class="profile-info-value">
				<span><fmt:formatNumber pattern="#,##0.00" value="${payBalance }"></fmt:formatNumber>元 </span>
			</div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 标的系统余额： </div>
			<div class="profile-info-value">
				<span><fmt:formatNumber pattern="#,##0.00" value="${localBalance }"></fmt:formatNumber>元</span>
			</div>
		</div>
		<c:if test="${not empty  companyBalance  }">
			<div class="profile-info-row">
				<div class="profile-info-name"> ${companyName }企业联动余额： </div>
				<div class="profile-info-value">
					<span><fmt:formatNumber pattern="#,##0.00" value="${companyPayBalance }"></fmt:formatNumber>元 </span>
				</div>
			</div>
			<div class="profile-info-row">
				<div class="profile-info-name">  ${companyName }企业系统余额： </div>
				<div class="profile-info-value">
					<span><fmt:formatNumber pattern="#,##0.00" value="${companyBalance }"></fmt:formatNumber>元</span>
				</div>
			</div>
		</c:if>
	</div>
</div>
