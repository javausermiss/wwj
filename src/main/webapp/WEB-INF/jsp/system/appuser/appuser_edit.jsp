<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
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
						<form action="happuser/${msg }.do" name="userForm" id="userForm" method="post">
							<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
							<div id="zhongxin" style="padding-top: 13px;">
							<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">用户昵称:</td>
									<td><input type="text" name="NICKNAME" id="NICKNAME" value="${pd.NICKNAME }" maxlength="32" placeholder="这里昵称" title="用户名" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">到期日期:</td>
									<td><input class="span10 date-picker" name="END_TIME" id="END_TIME" value="${pd.END_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="到期日期" title="到期日期" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">编号:</td>
									<td><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER }" maxlength="32" placeholder="这里输入编号" title="编号" onblur="hasN('${pd.USERNAME }')" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
									<td><input type="email" name="EMAIL" id="EMAIL"  value="${pd.EMAIL }" maxlength="32" placeholder="这里输入邮箱" title="邮箱" onblur="hasE('${pd.USERNAME }')" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">密码:</td>
									<td><input type="password" name="PASSWORD" id="password"  placeholder="输入密码"  title="密码" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">手机号:</td>
									<td><input type="tel" name="PHONE" id="PHONE" value="${pd.PHONE }" placeholder="这里输入手机号" title="手机号" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">确认密码:</td>
									<td><input type="password" name="chkpwd" id="chkpwd"  placeholder="确认密码"  title="确认密码" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">身份证号:</td>
									<td><input type="text" name="SFID" id="SFID" value="${pd.SFID }" placeholder="这里输入身份证号" title="身份证号" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
									<td><input type="text" name="NAME" id="name"  value="${pd.NAME }" placeholder="这里输入姓名" title="姓名" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">开通年限:</td>
									<td><input type="number" name="YEARS" id="YEARS" class="input_txt" value="${pd.YEARS }" placeholder="开通年限(请输入数字)" title="开通年限" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
									<td><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="这里输入备注" title="备注" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">状态:</td>
									<td>
										<select name="STATUS" title="状态">
										<option value="1" <c:if test="${pd.STATUS == '1' }">selected</c:if> >正常</option>
										<option value="0" <c:if test="${pd.STATUS == '0' }">selected</c:if> >冻结</option>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">追加金币:</td>
									<td colspan="3"><input type="number" name="ADD_GOLD" id="ADD_GOLD" value="0" maxlength="10" placeholder="这里输入追加金币数量" title="ADD_GOLD" style="width:98%;"/></td>
								</tr>
								<tr>
									<td class="center" colspan="4">
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
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>						
<script type="text/javascript">
	$(top.hangge());
	
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}

	//保存
	function save(){
		if($("#user_id").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $("#loginname").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>happuser/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $("#EMAIL").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>happuser/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱'+EMAIL+'已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					$('#EMAIL').val('');
				 }
			}
		});
	}

</script>
</html>