<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<div class="row">
   <div class="col-xs-12">
              <table class="table table-striped table-bordered table-hover">
                <tbody>
                <tr>
                  <th class="">订单号</th>
                  <td>
                    ${itbCapitalFlowInfo.orderNo}
                  </td>
                  <th class="">业务类型</th>
                  <td>
                    <ak:toCn key="BUSINESS_TYPE" value="${itbCapitalFlowInfo.businessType}" />
                  </td>
                </tr>
                <tr>
                  <th class="">交易平台流水号</th>
                  <td>
                    ${itbCapitalFlowInfo.tradeNo}
                  </td>
                  <th class="">本地交易金额</th>
                  <td>
                  <c:choose>
  					 	<c:when test="${iseqAmount}">  
      						 ${itbCapitalFlowInfo.amount} 元
  						</c:when>
  				 		<c:otherwise> 
    						<span style="color: red;"> ${itbCapitalFlowInfo.amount} 元</span>
  				 		</c:otherwise>
					</c:choose>
                  </td>
                </tr>
               <tr>
               	 <th class="">本地流水交易状态</th>
                  <td>
                  	 成功
                  </td>
               </tr>
                </tbody>
              </table>
              <table class="table table-striped table-bordered table-hover">
                <tbody>
                 <tr>
                  <th class="">第三方原交易金额</th>
                  <td>
                    ${transferResult.orig_amt}
                  </td>
                   <th class="">第三方业务类型</th>
                  <td>
                    ${transferResult.busi_type}
                  </td>
                </tr>
                <tr>
                  <th class="">第三方手续费</th>
                  <td>
                    ${transferResult.com_amt}
                  </td>
                   <th class="">第三方交易金额</th>
                  <td>
                  <c:choose>
  					 	<c:when test="${iseqAmount}">  
      						 ${transferResult.amount} 元
  						</c:when>
  				 		<c:otherwise> 
    						<span style="color: red;">${transferResult.amount}元</span>
  				 		</c:otherwise>
					</c:choose>
                  </td>
                </tr>
                <tr>
                  <th class="">第三方交易状态</th>
                  <td>
                     <span style="color: red">${transferResult.tran_state}</span>
                  </td>
                </tr>
                </tbody>
                </table>
          </div>
</div>
<script type="text/javascript">
  jQuery(function($) {
    
  });
</script>