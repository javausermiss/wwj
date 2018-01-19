<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="sendgoods/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<%--<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">主键ID:</td>
								<td><input type="number" name="ID" id="ID" value="${pd.ID}" maxlength="32" placeholder="这里输入主键ID" title="主键ID" style="width:98%;"/></td>
							</tr>--%>
							<%--<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">发货数量:</td>
								<td><input type="text" name="GOODS_NUM" id="GOODS_NUM" value="${pd.GOODS_NUM}" maxlength="255" placeholder="这里输入发货数量" title="发货数量" style="width:98%;"/></td>
							</tr>--%>
							<%--<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">收货人名字:</td>
								<td><input type="text" name="CNEE_NAME" id="CNEE_NAME" value="${pd.CNEE_NAME}" maxlength="255" placeholder="这里输入收货人名字" title="收货人名字" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">收货人地址:</td>
								<td><input type="text" name="CNEE_ADDRESS" id="CNEE_ADDRESS" value="${pd.CNEE_ADDRESS}" maxlength="255" placeholder="这里输入收货人地址" title="收货人地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">收货人手机号码:</td>
								<td><input type="text" name="CNEE_PHONE" id="CNEE_PHONE" value="${pd.CNEE_PHONE}" maxlength="255" placeholder="这里输入收货人手机号码" title="收货人手机号码" style="width:98%;"/></td>
							</tr>--%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否发货:</td>
									<td><select id="SENDBOOLEAN" name="SENDBOOLEAN">
										<option value="1" <c:if test="${pd.SENDBOOLEAN == '1' }">selected</c:if>>已发货</option>
										<option value="0" <c:if test="${pd.SENDBOOLEAN == '0' }">selected</c:if>>未发货</option>
									</select>
									</td>
							</tr>

							<<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物流单号:</td>
								<td><input type="text" name="FMS_ORDER_NO" id="FMS_ORDER_NO" value="${pd.FMS_ORDER_NO}" maxlength="255" placeholder="这里输入物流单号" title="物流单号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物流名称:</td>
								<td><input type="text" name="FMS_NAME" id="FMS_NAME" value="${pd.FMS_NAME}" maxlength="255" placeholder="这里输入物流名称" title="物流名称" style="width:98%;"/></td>
							</tr>
						<%--	<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">更新时间:</td>
								<td><input type="text" name="UPDATE_TIME" id="UPDATE_TIME" value="${pd.UPDATE_TIME}" maxlength="255" placeholder="这里输入更新时间" title="更新时间" style="width:98%;"/></td>
							</tr>--%>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){


		/*	if($("#USER_ID").val()==""){
				$("#USER_ID").tips({
					side:3,
		            msg:'请输入用户ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_ID").focus();
			return false;
			}
			if($("#GOODS_NUM").val()==""){
				$("#GOODS_NUM").tips({
					side:3,
		            msg:'请输入发货数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GOODS_NUM").focus();
			return false;
			}
			if($("#CNEE_NAME").val()==""){
				$("#CNEE_NAME").tips({
					side:3,
		            msg:'请输入收货人名字',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CNEE_NAME").focus();
			return false;
			}
			if($("#CNEE_ADDRESS").val()==""){
				$("#CNEE_ADDRESS").tips({
					side:3,
		            msg:'请输入收货人地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CNEE_ADDRESS").focus();
			return false;
			}
			if($("#CNEE_PHONE").val()==""){
				$("#CNEE_PHONE").tips({
					side:3,
		            msg:'请输入收货人手机号码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CNEE_PHONE").focus();
			return false;
			}
			if($("#CREATE_TIME").val()==""){
				$("#CREATE_TIME").tips({
					side:3,
		            msg:'请输入订单创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_TIME").focus();
			return false;
			}
			if($("#MODE_DESPATCH").val()==""){
				$("#MODE_DESPATCH").tips({
					side:3,
		            msg:'请输入付款方式',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MODE_DESPATCH").focus();
			return false;
			}*/
			if($("#SENDBOOLEAN").val()==""){
				$("#SENDBOOLEAN").tips({
					side:3,
		            msg:'请输入是否发货',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SENDBOOLEAN").focus();
			return false;
			}
			/*if($("#POST_REMARK").val()==""){
				$("#POST_REMARK").tips({
					side:3,
		            msg:'请输入发货备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#POST_REMARK").focus();
			return false;
			}*/
			/*if($("#REMARK").val()==""){
				$("#REMARK").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#REMARK").focus();
			return false;
			}*/
			if($("#FMS_TIME").val()==""){
				$("#FMS_TIME").tips({
					side:3,
		            msg:'请输入发货时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FMS_TIME").focus();
			return false;
			}
			if($("#FMS_ORDER_NO").val()==""){
				$("#FMS_ORDER_NO").tips({
					side:3,
		            msg:'请输入物流单号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FMS_ORDER_NO").focus();
			return false;
			}
			if($("#FMS_NAME").val()==""){
				$("#FMS_NAME").tips({
					side:3,
		            msg:'请输入物流名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FMS_NAME").focus();
			return false;
			}
			/*if($("#UPDATE_TIME").val()==""){
				$("#UPDATE_TIME").tips({
					side:3,
		            msg:'请输入更新时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UPDATE_TIME").focus();
			return false;
			}*/
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>