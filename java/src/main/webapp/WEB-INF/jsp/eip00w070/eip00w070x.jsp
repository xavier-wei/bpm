<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w070Controller).CASE_KEY"/>
<c:set var="caseData" value="${sessionScope[caseKey]}"/>
<tags:layout>
<jsp:attribute name="buttons">    
    <tags:button id="btnEdit">
    	儲存<i class="fas fa-save"></i>
    </tags:button>    
    <tags:button id="btnDel">
    	刪除<i class="fas fa-trash-alt"></i>
    </tags:button>
    <tags:button id="btnBack">
    	取消<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

    <jsp:attribute name="contents">
    <tags:fieldset legend="編輯角色">
    	<div class="col-12 d-flex">
            <div class="col-6 col-md-6">
				<form:form id="eip00w070Form" name="eip00w070Form" modelAttribute="${caseKey}" method="POST">
					<tags:form-row>
						<tags:text-item label="角色代號"><c:out value="${caseData.role_id}"/></tags:text-item>
	                </tags:form-row>
	                <tags:form-row>
						<tags:text-item label="角色說明"><c:out value="${caseData.role_desc}"/></tags:text-item>
	                </tags:form-row>
	                <form:hidden path="item_id"/>
	                <form:hidden path="item_name"/>
	                <form:hidden path="hyperlink"/>
	                <form:hidden path="sub_link"/>
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
        $('#btnEdit').click(function () {
            $('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_editCharacter.action" />').submit();
        })
        $('#btnDel').click(function () {
            $('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_delCharacter.action" />').submit();
        })
        initTree();
    })
</script>
</jsp:attribute>
</tags:layout>