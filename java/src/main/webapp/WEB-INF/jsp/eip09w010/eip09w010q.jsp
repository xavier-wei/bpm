<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip09w010Controller).CASE_KEY"/>
<c:set var="caseData" value="${sessionScope[caseKey]}"/>
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .list-group-item-action {
            cursor: move
        }
    </style>
</jsp:attribute>
    <jsp:attribute name="buttons">    
    <tags:button id="btnEdit">
    	儲存<i class="fas fa-save"></i>
    </tags:button>    
    <tags:button id="btnBack">
    	取消<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>
    <jsp:attribute name="contents">
    <form:form id="eip09w010Form" name="eip09w010Form" modelAttribute="${caseKey}" method="POST">

    <tags:fieldset legend="設定個人儀表板">
		<form:form id="eip00w020Form" name="eip00w020Form" modelAttribute="${caseKey}" method="POST">
    <div class="col-12 d-flex user-select-none text-center">
        <div class="col-6">
            <div class="container-title">
                <div class="title-function text-right">
                    <button id="allRight" type="button" class="btn btn-outline-be btn-sm">
                        <span class="btn-txt">全部選取 <i class="fa fa-chevron-right"></i></span>
                    </button>
                </div>
            </div>
            <div class="list-group-item list-group-item-secondary">可選取儀表板</div>
            <ul class="list-group" id="tabList">
                <c:forEach var="item" items="${caseData.tabList}">
                    <li data-id="${item.dashboard_fig_id}"
                        class="list-group-item list-group-item-action">${item.item_name}</li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-6">
            <div class="container-title">
                <div class="title-function text-left">
                    <button id="allLeft" type="button" class="btn btn-outline-be btn-sm">
                        <span class="btn-txt"><i class="fa fa-chevron-left"></i> 全部取消</span>
                    </button>
                </div>
            </div>
            <div class="list-group-item list-group-item-primary ">已選取儀表板</div>
            <ul class="list-group" id="pickTabList">
                <c:forEach var="item" items="${caseData.pickTabList}">
                    <li data-id="${item.dashboard_fig_id}"
                        class="list-group-item list-group-item-action">${item.item_name}</li>
                </c:forEach>
            </ul>
        </div>
    </div>
            <form:hidden path="pickTabListString"/>
        </form:form>
    </tags:fieldset>
        <tags:form-note>
        <tags:form-note-item>可透過拖曳來選取及排序。</tags:form-note-item>
        </tags:form-note>
    </form:form>
</jsp:attribute>
    <jsp:attribute name="footers">
<script>
    let sortable = null;
    $(function () {
        Sortable.create(tabList, {
            group: 'list-1',
            handle: '.list-group-item-action',
            sort: false,
            chosenClass: 'list-group-item-primary'
        });

        sortable = Sortable.create(pickTabList, {
            animation: 100,
            group: 'list-1',
            handle: '.list-group-item-action',
            sort: true,
            chosenClass: 'list-group-item-primary'
        });

        $('#allRight').click(function () {
            $('#tabList .list-group-item-action').appendTo('#pickTabList');
        })
        $('#allLeft').click(function () {
            $('#pickTabList .list-group-item-action').appendTo('#tabList');
        })
        $('#btnBack').click(function () {
            $('#eip09w010Form').attr('action', '<c:url value="/Eip09w010_enter.action" />').submit();
        })
        $('#btnEdit').click(function () {
            $('#pickTabListString').val(sortable.toArray().join(','));
            $('#eip09w010Form').attr('action', '<c:url value="/Eip09w010_save.action" />').submit();
        })
    })
</script>
</jsp:attribute>
</tags:layout>