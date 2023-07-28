<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w050Controller).CASE_KEY"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout>
    
 <jsp:attribute name="heads">
    <style>
        .container-title .title-text {
            float: left;
            font-weight: bold
        }
    </style>
 </jsp:attribute>

    <jsp:attribute name="buttons">
        <tags:button id="btnReload">重新整理<i class="fas fa-reply"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
    <tags:fieldset legend="功能管理">
        <div class="col-12 d-flex">
            <div class="col-6 col-md-6">
                <div class="container-title">
                    <div class="title-text text-left"><b>連結或節點資訊</b></div>
                    <div class="title-function text-right">
                        <tags:button id="save" cssClass="btn-sm">儲存<i class="fas fa-save"></i></tags:button>
                        <tags:button id="delete" cssClass="btn-sm">刪除<i class="fas fa-trash-alt"></i></tags:button>
                    </div>
                </div>
                <div class="container-content">
        <form:form id="eip00w050Form" modelAttribute="${caseKey}">
			<ol>
                <li>
                    <label class="label">顯示名稱：</label>
                    <form:input path="item_name" id="item_name" cssClass="form-control" size="30" maxlength="40"/>
                </li>
                <li>
                    <label class="label">連結路徑：</label>
                    <form:input path="hyperlink" id="hyperlink" cssClass="form-control" size="30" maxlength="100"/>
                </li>
                <li>
                    <label class="label">排序：</label>
                    <form:input path="sort_order" id="sort_order" cssClass="form-control" size="5" maxlength="10"
                                value="0"/>
                </li>
                <li>
                    <label class="label">功能編號：</label>
                    <form:input path="sub_link" id="sub_link" cssClass="form-control" size="30" maxlength="20"/>
                </li>
                <li>
                    <label class="label">啟用/停用：</label>
                    <form:radiobutton path="disable" value="Y"/>停用
                    <form:radiobutton path="disable" value=""/>啟用
                </li>
            </ol>
            <form:hidden path="pid"/>
            <form:hidden path="item_id"/>
            <form:hidden path="action_type"/>
		</form:form>
                </div>
            </div>
            <div class="col-6 col-md-6">
                <div class="container-title">
                    <div class="text-right">
                        <tags:button id="open" cssClass="btn-sm">展開<i class="fas fa-expand"></i></tags:button>
                        <tags:button id="close" cssClass="btn-sm">收合<i class="fas fa-compress"></i></tags:button>
                        <tags:button id="create" cssClass="btn-sm">新增子項<i class="fas fa-plus"></i></tags:button>
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
<code id="tmp"></code>

    </tags:fieldset>
</jsp:attribute>
    <jsp:attribute name="footers">
<script type="text/javascript">
    let te;

    function initTree() {
        te = $("#dynaTree").dynatree(
            {
                selectMode: 1, //1:single, 2:multi, 3:multi-hier
                checkbox: true,
                classNames: {checkbox: "ui-dynatree-radio"},
                minExpandLevel: 2,
                persist: false,
                autoCollapse: false,
                activeVisible: true,
                cookie: false,
                idPrefix: 'TREE_',
                onSelect: function (flag, dnode) {
                    let selectedNodes = dnode.tree.getSelectedNodes();
                    $.map(selectedNodes, function (node) {
                        ajaxItemData(node.data.key);
                    });
                },
                onDblClick: function (dnode, event) {
                    dnode.toggleSelect();
                }
            }
        );
        let dynatree = $(te).dynatree('getSelectedNodes');
        if (dynatree && dynatree.length > 0) {
            dynatree[0].makeVisible();
        }
    }

    function ajaxItemData(item_id) {
        $('#action_type').val('edit');
        $('#pid').val(item_id);
        let data = {};
        data['item_id'] = item_id;
        $.ajax({
            url: '<c:url value="Eip00w050_info.action"/>',
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (json) {
                edit(json);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                showAlert("網路發生錯誤");
                console.log(xhr.status + " " + thrownError);
            }
        });
    }

    function edit(json) {
        nullValue($('#item_id'), json.item_id);
        nullValue($('#item_name'), json.item_name);
        nullValue($('#hyperlink'), json.hyperlink);
        nullValue($('#sub_link'), json.sub_link);
        nullValue($('#sort_order'), json.sort);
        $.each($('input[name="disable"]'), function () {
            if ($(this).val() === json.disable || ($(this).val() === '' && json.disable === 'null')) {
                $(this).attr("checked", 'checked');
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


    function clean() {
        $('form#eip00w050Form').each(function () {
            this.reset();
        });
    }

    $(function () {
        $("#delete").click(function () {
            let selNodes = $("#dynaTree").dynatree("getSelectedNodes");
            let selKeys = $.map(selNodes, function (node) {
                return node.data.key;
            });
            if (selKeys === '' || selKeys === 'undefined' || selKeys.length === 0) {
                showAlert('請先挑選功能');
            } else {
                showConfirm("刪除功能若包含子節點以及相關授權會一併刪除，確定刪除？", () => {
                    $("#item_id").val(selKeys);
                    $('#eip00w050Form').attr('action', '<c:url value="/Eip00w050_delete.action"/>').submit();
                });
            }
        });
        $('#save').click(function () {
            let selNodes = $("#dynaTree").dynatree("getSelectedNodes");
            let selKeys = $.map(selNodes, function (node) {
                return node.data.key;
            });
            if (selKeys === '' || selKeys === 'undefined' || selKeys.length === 0) {
                showAlert('請先挑選功能');
                return false;
            } else {
                let url;
                if ($('#action_type').val() === 'edit') {
                    url = '<c:url value="/Eip00w050_update.action"/>';
                } else {
                    url = '<c:url value="/Eip00w050_save.action"/>';
                }
                $('#eip00w050Form').attr('action', url).submit();
            }
        });

        $('#btnReload').click(function () {
            $('#eip00w050Form').attr('action', '<c:url value="/Eip00w050_enter.action"/>').submit();
        });

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

        $('#create').click(function () {

            let selNodes = $("#dynaTree").dynatree("getSelectedNodes");
            let selKeys = $.map(selNodes, function (node) {
                return node.data.key;
            });
            if (selKeys === '' || selKeys === 'undefined' || selKeys.length === 0) {
                showAlert('請先挑選功能');
            } else {
                clean();
                $('#pid').val(selKeys);
                $('#action_type').val('create');
            }
        });


        function clean() {
            $('#eip00w050Form input:text:enabled:visible').val('');
            $('#sort_order').val('0');
        }

        initTree();
    });
</script>
</jsp:attribute>
</tags:layout>