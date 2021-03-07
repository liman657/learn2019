<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="tag.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>商城高并发抢购-商品详情页面</title>
    <%@include file="head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <input id="killId" value="${detail.id}" type="hidden"/>
        <div class="panel-heading">
            <h1>商品名称：${detail.itemName}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">剩余数量：${detail.total}</h2>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">开始时间：<fmt:formatDate value="${detail.startTime}" pattern='yyyy-MM-dd HH:mm:ss'/></h2>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">结束时间：<fmt:formatDate value="${detail.endTime}" pattern='yyyy-MM-dd HH:mm:ss'/></h2>
        </div>
    </div>

    <td>
        <c:choose>
            <c:when test="${detail.canKill==1}">
                <a class="btn btn-info" style="font-size: 20px" onclick="executeKill()">抢购</a>
            </c:when>
            <c:otherwise>
                <a class="btn btn-info" style="font-size: 20px">抢购已结束！</a>
            </c:otherwise>
        </c:choose>
    </td>
</div>

</body>
<script src="${ctx}/statics/plugins/jquery.js"></script>
<script src="${ctx}/statics/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="${ctx}/statics/plugins/jquery.cookie.min.js"></script>
<script src="${ctx}/statics/plugins/jquery.countdown.min.js"></script>

<%--<script src="${ctx}/static/script/kill.js"></script>--%>
<link rel="stylesheet" href="${ctx}/statics/css/detail.css" type="text/css">
<script type="text/javascript">
    function executeKill() {
        $.ajax({
            type: "POST",
            url: "${ctx}/rob/execute",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(getJsonData()),
            dataType: "json",

            success: function(res){
                if (res.code==0) {
                    //alert(res.msg);
                    window.location.href="${ctx}/page/rob/success"
                }else{
                    //alert(res.msg);
                    window.location.href="${ctx}/page/rob/fail"
                }
            },
            error: function (message) {
                alert("抢购失败！"+JSON.stringify(message));
                return;
            }
        });
    }

    function getJsonData() {
        var killId=$("#killId").val();
        var data = {
            "killId":killId,
            "userId":10
        };
        // var data = {
        //     "killId":killId
        // };
        return data;
    }
</script>

</html>
















