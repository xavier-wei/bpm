<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w070Controller).CASE_KEY"/>
<c:set var="caseData" value="${sessionScope[caseKey]}"/>
<tags:layout>
<jsp:attribute name="buttons">    
    <tags:button id="btnAdd">
    	新增<i class="fas fa-edit"></i>
    </tags:button>
    <tags:button id="btnBack">
    	取消<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>
    
<jsp:attribute name="contents">
    <tags:fieldset legend="新增角色">
    	<div class="col-12 d-flex">
            <div class="col-6 col-md-6">
				<form:form id="eip00w070Form" name="eip00w070Form" modelAttribute="${caseKey}" method="POST">
					<tags:form-row>
	                 <form:label path="role_id" cssClass="col-form-label">角色代號：</form:label>
	                    <div class="col-6 col-md form-inline">
                            <form:input path="role_id" cssClass="form-control eng_num_only" size="20" maxlength="20"/>
                        </div>
	                </tags:form-row>
	                <tags:form-row>
	                 <form:label path="role_desc" cssClass="col-form-label">角色說明：</form:label>
	                    <div class="col-6 col-md form-inline">
                            <form:textarea path="role_desc" cssClass="form-control eng_num_only" size="20"
                                           maxlength="20"/>
                        </div>
	                </tags:form-row>
	                <form:hidden path="item_id"/>
	                <form:hidden path="item_name"/>
	                <form:hidden path="hyperlink"/>
	                <form:hidden path="selectedIdlist"/>
		        </form:form>
            </div>
            <div class="col-6 col-md-6">
                <div class="container-title">
                    <div class="text-right">
                        <tags:button id="open" cssClass="btn-sm">展開<i class="fas fa-expand"></i></tags:button>
                        <tags:button id="close" cssClass="btn-sm">收合<i class="fas fa-compress"></i></tags:button>
                    </div>
                </div>
                <div id="treeBlock">
                    <div id="dynaTree" class="dynatree">
						<c:forEach var="items" items="${items}">
							<c:out value="${items}" escapeXml="false"/>
						</c:forEach>
                    </div>
                </div>
            </div>
        </div>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

    let suppressOnSelect = false;

    function lookup(x, isSel) {
        if (x != null && isSel && x.getLevel() > 1) {
            console.log('up:' + x);
            x.select();
            lookup(x.parent, isSel);
        }
    }

    function lookdown(x, isSel) {
        if (x != null) {
            console.log('down:' + x);
            x.select(isSel);
            $(x.childList).each((i, o) => {
                lookdown(o, isSel);
            });
        }
    }

    function initTree() {
        $("#dynaTree").dynatree({
            selectMode: 2, //1:single, 2:multi, 3:multi-hier
            checkbox: true,
            classNames: {checkbox: "ui-dynatree-checkbox"},
            minExpandLevel: 2,
            persist: false,
            autoCollapse: false,
            activeVisible: true,
            cookie: false,
            idPrefix: 'TREE_',
            onSelect: function (flag, dnode) {
                if (suppressOnSelect) {
                    return;
                }
                try {
                    suppressOnSelect = true;
                    let idlist = [];
                    lookup(dnode, dnode.isSelected());
                    lookdown(dnode, dnode.isSelected());
                    let selectedNodes = dnode.tree.getSelectedNodes();
                    $.map(selectedNodes, function (node) {
                        idlist.push(node.data.key);
                    });
                    $('#selectedIdlist').val(idlist);
                } finally {
                    suppressOnSelect = false;
                }
            }
        });
    }

    function nullValue(obj, value) {
        if (value != null && value !== 'null') {
            obj.val(value);
        } else {
            obj.val('');
        }
    }

    $(function () {


        $('#open').click(function () {
            $("#dynaTree").dynatree("getRoot").visit(function (dtnode) {
                dtnode.expand(true);
            });
        });

        $('#close').click(function () {
            $("#dynaTree").dynatree("getRoot").visit(function (dtnode) {
                dtnode.expand(false);
            });
        });

        $('#btnBack').click(function () {
            $('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_enter.action" />').submit();
        })
        $('#btnAdd').click(function () {
            $('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_addCharacter.action" />').submit();
        })
        initTree();
    })
</script>
</jsp:attribute>
</tags:layout>