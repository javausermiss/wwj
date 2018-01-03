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
					
					<form action="playdetail/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td>
								<td><input type="text" name="USERID" id="USERID" value="${pd.USERID}" 
								maxlength="50" placeholder="这里输入用户名" title="用户名" style="width:98%;"/></td>
								<%-- <td>
									 <select class="chosen-select" name="USERID" id="USERID" data-placeholder="请选择用户名" style="width:320px;">
											<c:forEach items="${userList}" var="var" varStatus="vs">
											  <option value="${var.USER_ID}"  
											  			<c:if test="${pd.USERID == var.USER_ID }">selected</c:if> >
	                                            		${var.NICKNAME}(${var.USER_ID})
	                                            </option>
                                            </c:forEach>
								  	</select> 
							
								</td> --%>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">娃娃机名称:</td>
								<td><input type="text" name="DOLLID" id="DOLLID" value="${pd.DOLLID}" 
								maxlength="50" placeholder="这里输入娃娃机名称" title="娃娃机名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: right;padding-top: 13px;">娃娃名称:</td>
								<td>
									<select class="chosen-select" name="DOLLID" id="DOLLID" data-placeholder="请选择娃娃" style="width:320px;">
											<c:forEach items="${dolList}" var="var" varStatus="vs">
											  <option value="${var.DOLL_ID}"  
											  			<c:if test="${pd.DOLLID == var.DOLL_ID }">selected</c:if> >
	                                            		${var.DOLL_NAME}(${var.DOLL_ID})
	                                            </option>
                                            </c:forEach>
								  	</select>
							
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="text" name="STATE" id="STATE" value="${pd.STATE}" 
								maxlength="50" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
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
			if($("#USERID").val()==""){
				$("#USERID").tips({
					side:3,
		            msg:'请输入用户名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USERID").focus();
			return false;
			}
			if($("#DOLLID").val()==""){
				$("#DOLLID").tips({
					side:3,
		            msg:'请输入娃娃机名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DOLLID").focus();
			return false;
			}
			if($("#DOLL_NAME").val()==""){
				$("#DOLL_NAME").tips({
					side:3,
		            msg:'请输入娃娃名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DOLL_NAME").focus();
			return false;
			}
			if($("#STATE").val()==""){
				$("#STATE").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATE").focus();
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