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
					<form action="camera/${msg }.do" name="Form" id="Form" method="post">
					
						<input type="hidden" name="CAMERA_ID" id="CAMERA_ID" value="${pd.CAMERA_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
							<%-- <tr>
							<td style="width:75px;text-align: right;padding-top: 13px;">娃娃机名称:</td>
							<td>
							<span style="font-size:18px;">
								<select id="doll_id" name="doll_id">
									<c:forEach items="${list}" var="li">
  											<option value="${li.doll_id}">
  												${li.doll_id}
  											</option> 
										</c:forEach>
								</select>
							</span>
							</td></tr>  --%>
								
								
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">娃娃机名称:</td>
								<td><input type="text" name="DOLL_ID" id="DOLL_ID" value="${pd.DOLL_ID}" 
								maxlength="50" placeholder="这里输入设备名称" title="DOLL_ID" style="width:98%;"/></td>
							</tr>
						
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">设备名称:</td>
								<td><input type="text" name="CAMERA_NAME" id="CAMERA_NAME" value="${pd.CAMERA_NAME}" 
								maxlength="50" placeholder="这里输入设备名称" title="CAMERA_NAME" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">设备编号:</td>
								<td><input type="text" name="CAMERA_NUM" id="CAMERA_NUM" value="${pd.CAMERA_NUM}" 
								maxlength="16" placeholder="这里输入设备编号" title="CAMERA_NUM" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">推流地址:</td>
								<td><input type="text" name="RTMP_URL" id="RTMP_URL" value="${pd.RTMP_URL}" 
								maxlength="255" placeholder="这里输入推流地址" title="RTMP_URL" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">推流服务名称:</td>
								<td><input type="text" name="SERVER_NAME" id="SERVER_NAME" value="${pd.SERVER_NAME}" 
								maxlength="50" placeholder="这里输入推流服务名称" title="SERVER_NAME" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">流媒体名称:</td>
								<td><input type="text" name="LIVESTREAM" id="LIVESTREAM" value="${pd.LIVESTREAM}" 
								maxlength="50" placeholder="这里输入流媒体名称" title="LIVESTREAM" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">流媒体进程ID:</td>
								<td><input type="text" name="SERVER_ID" id="SERVER_ID" value="${pd.SERVER_ID}" 
								maxlength="8" placeholder="这里输入流媒体进程ID" title="SERVER_ID" style="width:98%;"/></td>
							</tr>
							<tr>
							<td style="width:75px;text-align: right;padding-top: 13px;">摄像头位置:</td>
								<td>
								<span style="font-size:12px;">
									<select id="CAMERA_TYPE" name="CAMERA_TYPE">
										<option value="1">S</option>
										<option value="2">M</option>
									</select>
									</span>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="text" name="DEVICE_STATE" id="DEVICE_STATE" value="${pd.DEVICE_STATE}" 
								maxlength="50" placeholder="这里输入状态" title="DEVICE_STATE" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">乐观锁版本号:</td>
								<td><input type="text" name="LOCK_VERSION" id="LOCK_VERSION" value="${pd.LOCK_VERSION}" 
								maxlength="50" placeholder="这里输入状态" title="LOCK_VERSION" style="width:98%;"/></td>
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
			 if($("#DOLL_ID").val()==""){
				$("#DOLL_ID").tips({
					side:3,
		            msg:'请输入娃娃机名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DOLL_ID").focus();
			return false;
			}   
			
			if($("#CAMERA_NAME").val()==""){
				$("#CAMERA_NAME").tips({
					side:3,
		            msg:'请输入设备名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CAMERA_NAME").focus();
			return false;
			}
			if($("#CAMERA_NUM").val()==""){
				$("#CAMERA_NUM").tips({
					side:3,
		            msg:'请输入设备编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CAMERA_NUM").focus();
			return false;
			}
			if($("#RTMP_URL").val()==""){
				$("#RTMP_URL").tips({
					side:3,
		            msg:'请输入推流地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RTMP_URL").focus();
			return false;
			}
			if($("#SERVER_NAME").val()==""){
				$("#SERVER_NAME").tips({
					side:3,
		            msg:'请输入推流服务名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SERVER_NAME").focus();
			return false;
			}
			if($("#LIVESTREAM").val()==""){
				$("#LIVESTREAM").tips({
					side:3,
		            msg:'请输入流媒体名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LIVESTREAM").focus();
			return false;
			}
			if($("#SERVER_ID").val()==""){
				$("#SERVER_ID").tips({
					side:3,
		            msg:'请输入流媒体进程ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SERVER_ID").focus();
			return false;
			}
			
			 if($("#CAMERA_TYPE").val()==""){
				$("#CAMERA_TYPE").tips({
					side:3,
		            msg:'请输入摄像头位置',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CAMERA_TYPE").focus();
			return false;
			} 
			
			
			if($("#DEVICE_STATE").val()==""){
				$("#DEVICE_STATE").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEVICE_STATE").focus();
			return false;
			}
			if($("#LOCK_VERSION").val()==""){
				$("#LOCK_VERSION").tips({
					side:3,
		            msg:'请输入乐观锁版本号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LOCK_VERSION").focus();
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