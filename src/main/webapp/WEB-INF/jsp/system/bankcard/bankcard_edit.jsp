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
					
					<form action="bankcard/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="BANKCARD_ID" id="BANKCARD_ID" value="${pd.BANKCARD_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户ID:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="255" placeholder="这里输入用户ID" title="用户ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行地址:</td>
								<td><input type="text" name="BANK_ADDRESS" id="BANK_ADDRESS" value="${pd.BANK_ADDRESS}" maxlength="255" placeholder="这里输入银行地址" title="银行地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行名称:</td>
								<td><input type="text" name="BANK_NAME" id="BANK_NAME" value="${pd.BANK_NAME}" maxlength="255" placeholder="这里输入银行名称" title="银行名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行支行:</td>
								<td><input type="text" name="BANK_BRANCH" id="BANK_BRANCH" value="${pd.BANK_BRANCH}" maxlength="255" placeholder="这里输入银行支行" title="银行支行" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行卡号:</td>
								<td><input type="text" name="BANK_CARD_NO" id="BANK_CARD_NO" value="${pd.BANK_CARD_NO}" maxlength="255" placeholder="这里输入银行卡号" title="银行卡号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">身份证号:</td>
								<td><input type="text" name="ID_NUMBER" id="ID_NUMBER" value="${pd.ID_NUMBER}" maxlength="255" placeholder="这里输入身份证号" title="身份证号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户姓名:</td>
								<td><input type="text" name="USER_REA_NAME" id="USER_REA_NAME" value="${pd.USER_REA_NAME}" maxlength="255" placeholder="这里输入用户姓名" title="用户姓名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号码:</td>
								<td><input type="text" name="BANK_PHONE" id="BANK_PHONE" value="${pd.BANK_PHONE}" maxlength="255" placeholder="这里输入手机号码" title="手机号码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否默认:</td>
								<td><input type="text" name="IS_DEFAULT" id="IS_DEFAULT" value="${pd.IS_DEFAULT}" maxlength="255" placeholder="这里输入是否默认" title="是否默认" style="width:98%;"/></td>
							</tr>
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
			if($("#USER_ID").val()==""){
				$("#USER_ID").tips({
					side:3,
		            msg:'请输入用户ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_ID").focus();
			return false;
			}
			if($("#BANK_ADDRESS").val()==""){
				$("#BANK_ADDRESS").tips({
					side:3,
		            msg:'请输入银行地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BANK_ADDRESS").focus();
			return false;
			}
			if($("#BANK_NAME").val()==""){
				$("#BANK_NAME").tips({
					side:3,
		            msg:'请输入银行名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BANK_NAME").focus();
			return false;
			}
			if($("#BANK_BRANCH").val()==""){
				$("#BANK_BRANCH").tips({
					side:3,
		            msg:'请输入银行支行',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BANK_BRANCH").focus();
			return false;
			}
			if($("#BANK_CARD_NO").val()==""){
				$("#BANK_CARD_NO").tips({
					side:3,
		            msg:'请输入银行卡号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BANK_CARD_NO").focus();
			return false;
			}
			if($("#ID_NUMBER").val()==""){
				$("#ID_NUMBER").tips({
					side:3,
		            msg:'请输入身份证号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ID_NUMBER").focus();
			return false;
			}
			if($("#USER_REA_NAME").val()==""){
				$("#USER_REA_NAME").tips({
					side:3,
		            msg:'请输入用户姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_REA_NAME").focus();
			return false;
			}
			if($("#BANK_PHONE").val()==""){
				$("#BANK_PHONE").tips({
					side:3,
		            msg:'请输入手机号码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BANK_PHONE").focus();
			return false;
			}
			if($("#IS_DEFAULT").val()==""){
				$("#IS_DEFAULT").tips({
					side:3,
		            msg:'请输入是否默认',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#IS_DEFAULT").focus();
			return false;
			}
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