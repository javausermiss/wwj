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
                        <form action="happuser/listGameUsers.do" method="post" name="userForm" id="userForm">
                            <table style="margin-top:5px;">
                                <tr>
                                    <td>
                                        <div class="nav-search">
											<span class="input-icon">
												<input class="nav-search-input" autocomplete="off" id="nav-search-input"
		                                               type="text" name="keywords" value="${pd.keywords }"
		                                               placeholder="这里输入用户昵称,ID"/>
												<i class="ace-icon fa fa-search nav-search-icon"></i>
											</span>
                                        </div>
                                    </td>
                                      <td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart"
                                                                         id="lastStart" value="${pd.lastStart}" type="text"
                                                                         data-date-format="yyyy-mm-dd"
                                                                         readonly="readonly" style="width:88px;"
                                                                         placeholder="开始日期" title="开始日期"/></td>
                                    <td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd"
                                                                         name="lastEnd" value="${pd.lastEnd}" type="text"
                                                                         data-date-format="yyyy-mm-dd"
                                                                         readonly="readonly" style="width:88px;"
                                                                         placeholder="结束日期" title="结束日期"/></td>
                                        <td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs"
                                                                                           onclick="tosearch();"
                                                                                           title="检索"><i
                                                id="nav-search-icon"
                                                class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a>
                                        </td>
                                </tr>
                            </table>
                            <!-- 检索  -->
                            <table id="simple-table" class="table table-striped table-bordered table-hover"
                                   style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <th class="center" style="width:50px;">序号</th>
                                    <th class="center">用户名</th>
                                    <th class="center">姓名</th>
                                    <th class="center">游戏次数</th>
                                    <th class="center">抓中次数</th>
                                    <th class="center">抓中概率</th>
                                    <th class="center">充值金额</th>
                                    <th class="center">金币余额</th>
                                    <th class="center">用户ID</th>
                                </tr>
                                </thead>
                                <tbody>
                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty userList}">
                                            <c:forEach items="${userList}" var="user" varStatus="vs">
                                                <tr>
                                                    <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                    <td class="center">${user.NICKNAME }</td>
                                                    <td class="center">${user.NAME }</td>
                                                    <td class="center">${user.PALYCOUNT }</td>
                                                    <td class="center">${user.CAUGHTCOUNT }</td>
                                                    <td class="center">${user.PROBABILITY }</td>
                                                    <td class="center">${user.REGAMOUNT}</td>
                                                    <td class="center">${user.BALANCE}</td>
                                                    <td class="center">${user.USER_ID }</td>
                                                </tr>
                                            </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="main_info">
                                            <td colspan="100" class="center">没有相关数据</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                                </tbody>
                            </table>
                            <div class="page-header position-relative">
                                <table style="width:100%;">
                                       <td style="vertical-align:top;">
                                            <div class="pagination"
                                                 style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
                                        </td>
                                    </tr>
                                </table>
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
