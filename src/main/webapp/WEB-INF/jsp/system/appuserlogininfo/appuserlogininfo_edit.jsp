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
					
					<form action="appuserlogininfo/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="APPUSERLOGININFO_ID" id="APPUSERLOGININFO_ID" value="${pd.APPUSERLOGININFO_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">设备信息:</td>
								<td><input type="text" name="LOGIN_PHONE_TYPE" id="LOGIN_PHONE_TYPE" value="${pd.LOGIN_PHONE_TYPE}" maxlength="255" placeholder="这里输入设备信息" title="设备信息" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">IP:</td>
								<td><input type="text" name="LOGIN_IP" id="LOGIN_IP" value="${pd.LOGIN_IP}" maxlength="255" placeholder="这里输入IP" title="IP" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户ID:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="255" placeholder="这里输入用户ID" title="用户ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">登录时间:</td>
								<td><input class="span10 date-picker" name="LOGIN_TIME" id="LOGIN_TIME" value="${pd.LOGIN_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="登录时间" title="登录时间" style="width:98%;"/></td>
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
			if($("#LOGIN_PHONE_TYPE").val()==""){
				$("#LOGIN_PHONE_TYPE").tips({
					side:3,
		            msg:'请输入设备信息',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LOGIN_PHONE_TYPE").focus();
			return false;
			}
			if($("#LOGIN_IP").val()==""){
				$("#LOGIN_IP").tips({
					side:3,
		            msg:'请输入IP',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LOGIN_IP").focus();
			return false;
			}
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
			if($("#LOGIN_TIME").val()==""){
				$("#LOGIN_TIME").tips({
					side:3,
		            msg:'请输入登录时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LOGIN_TIME").focus();
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