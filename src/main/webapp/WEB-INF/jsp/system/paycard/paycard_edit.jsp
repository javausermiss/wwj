<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/ace/css/chosen.css"/>
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>
    <!-- 日期框 -->
    <link rel="stylesheet" href="static/ace/css/datepicker.css"/>
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

                        <form action="paycard/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">充值金额:</td>
                                        <td><input type="text" name="AMOUNT" id="AMOUNT" value="${pd.AMOUNT}"
                                                   maxlength="255" placeholder="这里输入充值金额" title="AMOUNT"
                                                   style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">实送金币:</td>
                                        <td><input type="text" name="GOLD" id="GOLD" value="${pd.GOLD}" maxlength="255"
                                                   placeholder="这里输入实送金币" title="GOLD" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">折扣:</td>
                                        <td><input type="text" name="DISCOUNT" id="DISCOUNT" value="${pd.DISCOUNT}"
                                                   maxlength="255" placeholder="这里输入折扣" title="DISCOUNT"
                                                   style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">文件域：</td>
                                        <td><input type="file" id="PAYCARD_FILE" name="PAYCARD_FILE"/></td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center;" colspan="10">
                                            <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
                                    src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4>
                            </div>
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
<%@ include file="../../system/index/foot.jsp" %>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
    $(top.hangge());

    //保存
    function save() {
        if ($("#AMOUNT").val() == "") {
            $("#AMOUNT").tips({
                side: 3,
                msg: '请输入AMOUNT',
                bg: '#AE81FF',
                time: 2
            });
            $("#AMOUNT").focus();
            return false;
        }
        if ($("#GOLD").val() == "") {
            $("#GOLD").tips({
                side: 3,
                msg: '请输入GOLD',
                bg: '#AE81FF',
                time: 2
            });
            $("#GOLD").focus();
            return false;
        }
        if ($("#DISCOUNT").val() == "") {
            $("#DISCOUNT").tips({
                side: 3,
                msg: '请输入DISCOUNT',
                bg: '#AE81FF',
                time: 2
            });
            $("#DISCOUNT").focus();
            return false;
        }
        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
    }

    $(function () {
        //日期框
        $('.date-picker').datepicker({autoclose: true, todayHighlight: true});
    });
</script>
</body>
</html>