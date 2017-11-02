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
					
					<form action="doll/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="DOLL_ID" id="DOLL_ID" value="${pd.DOLL_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">娃娃机机SN:</td>
								<td><input type="text" name="DOLL_SN" id="DOLL_SN" value="${pd.DOLL_SN}" maxlength="50" placeholder="这里输入娃娃机SN" title="sn" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">娃娃机名字:</td>
								<td><input type="text" name="DOLL_NAME" id="DOLL_NAME" value="${pd.DOLL_NAME}" maxlength="10" placeholder="这里输入娃娃机名字" title="DOLL_NAME" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">娃娃机房间号:</td>
								<td><input type="text" name="ROOM_ID" id="ROOM_ID" value="${pd.ROOM_ID}" maxlength="10" placeholder="这里输入娃娃机房间号" title="ROOM_ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">娃娃机金币:</td>
								<td><input type="text" name="DOLL_GOLD" id="DOLL_GOLD" value="${pd.DOLL_GOLD}" maxlength="10" placeholder="这里输入娃娃机花费金额" title="DOLL_GOLD" style="width:98%;"/></td>
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
			if($("#DOLL_SN").val()==""){
				$("#DOLL_SN").tips({
					side:3,
		            msg:'请输入sn',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DOLL_SN").focus();
			return false;
			}
            if($("#DOLL_GOLD").val()==""){
                $("#DOLL_GOLD").tips({
                    side:3,
                    msg:'请输入DOLL_GOLD',
                    bg:'#AE81FF',
                    time:2
                });
                $("#DOLL_GOLD").focus();
                return false;
            }
			if($("#DOLL_NAME").val()==""){
				$("#DOLL_NAME").tips({
					side:3,
		            msg:'请输入DOLL_NAME',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DOLL_NAME").focus();
			return false;
			}
			if($("#ROOM_ID").val()==""){
				$("#ROOM_ID").tips({
					side:3,
		            msg:'请输入ROOM_ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ROOM_ID").focus();
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