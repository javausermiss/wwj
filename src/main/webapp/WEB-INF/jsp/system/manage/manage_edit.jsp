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
					
					<form action="manage/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PRO_MANAGE_ID" id="PRO_MANAGE_ID" value="${pd.PRO_MANAGE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">支付金额:</td>
								<td><input type="text" name="PAY_AMOUNT" id="PAY_AMOUNT" value="${pd.PAY_AMOUNT}" maxlength="12" placeholder="这里输入支付金额" title="支付金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">获取金币数:</td>
								<td><input type="text" name="GOLD" id="GOLD" value="${pd.GOLD}" maxlength="12" placeholder="这里输入获取金币数" title="获取金币数" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">图片地址:</td>
								<td><input type="file" id="IMG_URL" name="IMG_URL" /></td>
							</tr>
							<tr>
							<td style="width:75px;text-align: right;padding-top: 13px;">类型:</td>
								<td>
								<span style="font-size:12px;">
									<select id="TYPE" name="TYPE">
										<option value="0" <c:if test="${pd.TYPE == '0' }">selected</c:if>>无推广</option>
										<option value="1" <c:if test="${pd.TYPE == '1' }">selected</c:if>>推广</option>
									</select>
									</span>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px; ">返回比例:</td>
								<td><input type="text" name="RETURN_RATIO" id="RETURN_RATIO"  value="${pd.RETURN_RATIO}" maxlength="12" placeholder="这里输入返回比例" title="返回比例" style="width:98%;"/>
								<!-- <script type="text/javascript">
        				function reg()
        				{
            			var inp= document.getElementById("sr");
           				 var reg=/^[0-9]+$/;
            			var result= reg.test(pd.value);
            			if(!result)
            			{alert("请输入数字！");
            			pd.value="";
            			}
        					}
   						 </script> --></td>
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
			if($("#PAY_AMOUNT").val()==""){
				$("#PAY_AMOUNT").tips({
					side:3,
		            msg:'请输入支付金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PAY_AMOUNT").focus();
			return false;
			}
			if($("#GOLD").val()==""){
				$("#GOLD").tips({
					side:3,
		            msg:'请输入获取金币数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GOLD").focus();
			return false;
			}
			if($("#IMG_URL").val()==""){
				$("#IMG_URL").tips({
					side:3,
		            msg:'请输入图片地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#IMG_URL").focus();
			return false;
			}
			if($("#TYPE").val()==""){
				$("#TYPE").tips({
					side:3,
		            msg:'请输入类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPE").focus();
			return false;
			}
			if($("#RETURN_RATIO").val()==""){
				$("#RETURN_RATIO").tips({
					side:3,
		            msg:'请输入返回比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RETURN_RATIO").focus();
			return false;
			}
			if($("#CREATE_TIME").val()==""){
				$("#CREATE_TIME").tips({
					side:3,
		            msg:'请输入创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_TIME").focus();
			return false;
			}
			if($("#UPDATE_TIME").val()==""){
				$("#UPDATE_TIME").tips({
					side:3,
		            msg:'请输入更新时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UPDATE_TIME").focus();
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