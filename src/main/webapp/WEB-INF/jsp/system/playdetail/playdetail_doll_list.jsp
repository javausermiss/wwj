<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link rel="stylesheet" href="static/ace/css/chosen.css"/>
    <!-- jsp文件头和头部 -->
    <%@ include file="../index/top.jsp" %>
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
                        <!-- 检索  -->
                        <form action="playdetail/listGameDolls.do" method="post" name="userForm" id="userForm">
                            <!-- 检索  -->
                            <table id="simple-table" class="table table-striped table-bordered table-hover"
                                   style="margin-top:5px;">
                                <thead>
                                <tr>
                                
                                    <th class="center">总游戏次数</th>
                                    <th class="center">抓中次数</th>
                                    <th class="center">抓中概率</th>
                                    <th class="center">失败次数</th>
                                    <th class="center">失败概率</th>
                                </tr>
                                </thead>
                                <tbody>
                                          <tr>
                                              <td class="center">${dgs.ALLCOUNT }</td>
                                              <td class="center">${dgs.SUCCOUNT }</td>
                                              <td class="center">${dgs.SUCPROBABILITY }</td>
                                              <td class="center">${dgs.FAILCOUNT}</td>
                                              <td class="center">${dgs.FAILPROBABILITY}</td>
                                          </tr>
                                </tbody>
                            </table>
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
    <!-- 返回顶部 -->
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>
<!-- /.main-container -->
<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../index/foot.jsp" %>
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
    $(top.hangge());

    //检索
    function tosearch() {
        top.jzts();
        $("#userForm").submit();
    }
    

    $(function () {

        //日期框
        $('.date-picker').datepicker({
            autoclose: true,
            todayHighlight: true
        });
    });
</script>
</html>
