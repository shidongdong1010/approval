<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/common/config.jsp"%>
<div class="row">
   <div class="col-xs-12">
              <table class="table table-striped table-bordered table-hover">
                <tbody>
                <tr>
                  <th class="">姓名</th>
                  <td>
                    ${user.realName}
                  </td>
                  <th class="">本地余额</th>
                  <td>
                     <span style="color: red;"><fmt:formatNumber value="${userAccount.usedAmount}" pattern="#,#00.0#"/>元</span>
                  </td>
                </tr>
                <tr>
                  <th class="">手机号码</th>
                  <td>
                   ${user.mobile}
                  </td>
                  <th class="">身份证号</th>
                  <td>
                 	 ${user.idCard}
                  </td>
                </tr>
               <tr>
               	 <th class="">银行编号</th>
                  <td>
                  	 ${user.bankType}
                  </td>
                  <th class="">银行卡号</th>
                  <td>
                  	 ${user.bankNo}
                  </td>
               </tr>
                </tbody>
              </table>
              <table class="table table-striped table-bordered table-hover">
                <tbody>
                 <tr>
                  <th class="">第三方用户姓名</th>
                  <td>
                    ${userResult.cust_name}
                  </td>
                   <th class="">第三方账户余额</th>
                  <td>
                      <span style="color: red;">${userResult.balance}</span>
                  </td>
                </tr>
                <tr>
                  <th class="">第三方用户手机号</th>
                  <td>
                    ${userResult.contact_mobile}
                  </td>
                   <th class="">第三方用户身份证号</th>
                  <td>
                  	${userResult.identity_code}
                  </td>
                </tr>
                <tr>
                  <th class="">第三方用户银行编号</th>
                  <td>
                     ${userResult.gate_id}
                  </td>
                  <th class="">第三方用户银行卡号</th>
                  <td>
                     ${userResult.card_id}
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