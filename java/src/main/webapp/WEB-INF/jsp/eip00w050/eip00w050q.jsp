<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w050Controller).CASE_KEY"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout>
    <jsp:attribute name="buttons">
        <tags:button id="btnReload">重新整理<i class="fas fa-reply"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        
        <div class="col-12 d-flex">

            <div class="col-6 col-md-6">
                <tags:fieldset legend="節點資訊">
                <div class="container-title">
                    <div class="title-function text-right">
                        <tags:button id="save" cssClass="btn-sm">儲存新增<i class="fas fa-save"></i></tags:button>
                        <tags:button id="update" cssClass="btn-sm">儲存更新<i class="fas fa-edit"></i></tags:button>
                        <tags:button id="delete" cssClass="btn-sm">刪除<i class="fas fa-trash-alt"></i></tags:button>
                    </div>
                </div>
                <div class="container-content">
                    <form:form id="eip00w050Form" modelAttribute="${caseKey}">
                        <tags:form-row>
                            <form:label cssClass="col-form-label star" path="item_name">顯示名稱：</form:label>
                            <div class="col-12 col-md">
                                <form:input path="item_name" cssClass="form-control" maxlength="40"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <form:label cssClass="col-form-label" path="item_name">連結路徑：</form:label>
                            <div class="col-12 col-md">
                                <form:input path="hyperlink" cssClass="form-control" maxlength="40"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <form:label cssClass="col-form-label" path="sort_order">排序：</form:label>
                            <div class="col-12 col-md">
                                <form:input path="sort_order" cssClass="form-control" maxlength="10" value="0"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <form:label cssClass="col-form-label star" path="disable">啟用/停用：</form:label>
                            <div class="col-12 col-md form-top-std">
                                <form:radiobutton path="disable" value="Y"/><span>停用</span>
                                <form:radiobutton path="disable" value=""/><span>啟用</span>
                            </div>
                        </tags:form-row>
                        <form:hidden path="pid"/>
                        <form:hidden path="item_id"/>
                        <form:hidden path="action_type"/>
                    </form:form>
                </div>
                </tags:fieldset>
            </div>
            <div class="col-6 col-md-6">
                <tags:fieldset legend="功能節點樹">
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
                </tags:fieldset>
            </div>
        </div>
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
        $('#save').hide();
        $('#update').show();
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
        nullValue($('#sort_order'), json.sort);
        const disableValue = json.disable != null && json.disable !== 'null' ? json.disable : '';
        $('input[name="disable"]').prop('checked', function () {
            return $(this).val() === disableValue;
        });
    }

    function nullValue(obj, value) {
        obj.val(value != null && value !== 'null' ? value : '');
    }

    function clean() {
        $('form#eip00w050Form').trigger('reset');
    }

    $(function () {
        $("#delete").click(function () {
            let selNodes = $("#dynaTree").dynatree("getSelectedNodes");
            let selKeys = selNodes.map(node => node.data.key);
            if (selKeys.length === 0) {
                showAlert('請先挑選功能');
            } else {
                showConfirm("刪除功能若包含子節點以及相關授權會一併刪除，確定刪除？", () => {
                    $("#item_id").val(selKeys);
                    $('#eip00w050Form').attr('action', '<c:url value="/Eip00w050_delete.action"/>').submit();
                });
            }
        });
        $('#save,#update').click(function () {
            let selNodes = $("#dynaTree").dynatree("getSelectedNodes");
            let selKeys = $.map(selNodes, function (node) {
                return node.data.key;
            });
            if (!selKeys || selKeys.length === 0) {
                showAlert('請先挑選功能');
                return false;
            }
            let actionType = $('#action_type').val();
            let url = actionType === 'edit' ? '<c:url value="/Eip00w050_update.action"/>' : '<c:url value="/Eip00w050_save.action"/>';
            $('#eip00w050Form').attr('action', url).submit();
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
            let selKeys = selNodes.map(node => node.data.key);
            if (selKeys.length === 0) {
                showAlert('請先挑選功能');
            } else {
                clean();
                $('#pid').val(selKeys);
                $('#action_type').val('create');
                $('#save').show();
                $('#update').hide();
            }
        });
        
        function clean() {
            $('#eip00w050Form input:text:enabled:visible').val('');
            $('#sort_order').val('0');
        }

        if ($('#action_type').val() === 'create') {
            $('#save').show();
            $('#update').hide();
        } else {
            $('#save').hide();
            $('#update').show();
        }
        initTree();
    });
</script>
    </jsp:attribute>
</tags:layout>