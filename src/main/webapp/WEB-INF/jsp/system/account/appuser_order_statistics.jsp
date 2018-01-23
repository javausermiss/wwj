﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

                        <!-- 检索  -->
                        <form action="account/detail/appUserOrderStatistics.do" method="post" name="Form" id="Form">
                            <table style="margin-top:5px;">
                                <tr>
                                    <td>
                                        <div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input"
                                                   id="nav-search-input" autocomplete="off" name="keywords"
                                                   value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
                                        </div>
                                    </td>
                                    <td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart"
                                                                         id="lastStart" value="${pd.lastStart }" type="text"
                                                                         data-date-format="yyyy-mm-dd"
                                                                         readonly="readonly" style="width:88px;"
                                                                         placeholder="开始日期" title="开始日期"/></td>
                                    <td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd"
                                                                         name="lastEnd" value="${pd.lastEnd }" type="text"
                                                                         data-date-format="yyyy-mm-dd"
                                                                         readonly="readonly" style="width:88px;"
                                                                         placeholder="结束日期" title="结束日期"/></td>
                                    <td style="vertical-align:top;padding-left:2px;">
                                        <%-- <select class="chosen-select form-control" name="STATUS" id="STATUS"
                                                data-placeholder="请选择状态" style="vertical-align:top;width: 120px;">
                                            <option value="">全部</option>
                                            <option value="1"
                                                    <c:if test="${pd.STATUS == '1' }">selected</c:if> >支付
                                            </option>
                                             <option value="0"
                                                    <c:if test="${pd.STATUS == '0' }">selected</c:if> >未支付
                                            </option>
                                        </select> --%>
                                    </td>
                                	
                                        <td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs"
                                                                                           onclick="tosearch();"
                                                                                           title="检索"><i
                                                id="nav-search-icon"
                                                class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a>
                                        </td>
                                        	<c:forEach items="${total}" var="s" varStatus="vs">
                                            <td class='center' ></td>
                                            	<td class='center' style="width:80px;color:red" bgcolor=""><b>总金额:</b></td>
                                        		<td class='center'><b>￥${s.money/100}</b></td>
                                        	</c:forEach>
                                        	<c:forEach items="${daytotal}" var="day" varStatus="vs">
                                            <td class='center' ></td>
                                            	<td class='center' style="width:80px;color:red" bgcolor=""><b>今日金额:</b></td>
                                        		<td class='center'><b>￥${day.money/100}</b></td>
                                        	</c:forEach>
                                        <td class='center' style="width:80px;color:red" bgcolor=""><b>每月金额:</b></td>
                                        <td>
                                        <select name="CREATETIME" id="CREATETIME" style="width: 100%">
                                        	<c:forEach items="${datetotal}" var="date" varStatus="vs">
                                        		<option value="date.CREATETIME"
                                        			<c:if test="">selected</c:if>>
                                        			${date.date}年 ${date.dateTime}月 ￥${date.countNumber/100}
                                        		</option>
                                        	</c:forEach>
                                        </select>
                                        </td>
<%--                                     <c:if test="${QX.toExcel == 1 }"> --%>
<!--                                         <td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" -->
<!--                                                                                             onclick="toExcel();" -->
<!--                                                                                             title="导出到EXCEL"><i -->
<!--                                                 id="nav-search-icon" -->
<!--                                                 class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a> -->
<!--                                         </td> -->
<%--                                     </c:if> --%>
                                </tr>
                            </table>
                            <!-- 检索  -->

                            <table id="simple-table" class="table table-striped table-bordered table-hover"
                                   style="margin-top:5px;">
                                <thead>
                                <tr>
                                    
                                    <th class="center">用户昵称</th>
                                    <th class="center">交易时间</th>
                                    <th class="center">支付方式</th>
                                    <th class="center">支付金额</th>
                                    <th class="center">状态</th>
                                </tr>
                                </thead>

                                <tbody>
                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty varlist}">
                                            <c:forEach items="${varlist}" var="var" varStatus="vs">
                                                <tr>
                                                    
                                                    <td class='center'>${var.NICKNAME}</td>
                                                    <td class='center'>${var.CREATETIME}</td>
                                                    <th class="center">${var.REGMODE}</th>
                                                    <th class="center">${var.REGAMOUNT/100}</th>
                                                    <td style="width: 200px;" class='center'>
                                                        <c:if test="${var.STATUS == '0' }">
                                                       		 <span class="label label-important arrowed-in">未支付</span>
                                                        </c:if>
                                                         <c:if test="${var.STATUS == '1' }">
                                                         	<span class="label label-success arrowed">支付</span>
                                                         </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach >
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
                                    <tr>
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
<%@ include file="../../system/index/foot.jsp" %>
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
    $(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        top.jzts();
        $("#Form").submit();
    }

    $(function () {

        //日期框
        $('.date-picker').datepicker({
            autoclose: true,
            todayHighlight: true
        });

        //下拉框
        if (!ace.vars['touch']) {
            $('.chosen-select').chosen({allow_single_deselect: true});
            $(window)
                .off('resize.chosen')
                .on('resize.chosen', function () {
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    });
                }).trigger('resize.chosen');
            $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
                if (event_name != 'sidebar_collapsed') return;
                $('.chosen-select').each(function () {
                    var $this = $(this);
                    $this.next().css({'width': $this.parent().width()});
                });
            });
            $('#chosen-multiple-style .btn').on('click', function (e) {
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                if (which == 2) $('#form-field-select-4').addClass('tag-input-style');
                else $('#form-field-select-4').removeClass('tag-input-style');
            });
        }


        //复选框全选控制
        var active_class = 'active';
        $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header
            $(this).closest('table').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
            });
        });
    });


    //导出excel
    function toExcel() {
        window.location.href = '<%=basePath%>doll/excel.do';
    }
</script>


</body>
</html>