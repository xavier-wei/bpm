<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w070Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
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
	                        <form:textarea path="role_desc" cssClass="form-control eng_num_only" size="20" maxlength="20"/>
	                    </div>
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
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
    
    
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
            onActivate: function (dnode) {
                if (dnode.hasChildren())
                    dnode.toggleExpand();
                dnode.toggleSelect();
                ajaxItemData(dnode.data.key);
            },
            onSelect: function (flag, dnode) {
            	let idlist = [];
                let selectedNodes = dnode.tree.getSelectedNodes();
                $.map(selectedNodes, function (node) {
                    ajaxItemData(node.data.key);
                    idlist.push(node.data.key);
                });
                $('#selectedIdlist').val(idlist);
            },
            onDblClick: function (dnode, event) {
                dnode.toggleSelect();
            }
        });
    }
    
    function ajaxItemData(item_id) {
        $('#action_type').val('edit');
        $('#pid').val(item_id);
        let data = {};
        data['item_id'] = item_id;
        $.ajax({
            url: '<c:url value="Eip00w070_info.action"/>',
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

    function validate() {
        return $('#item_name').val() !== '' && $('#sortOrder').val() !== '';
    }

    function rebuildTree(data) {
        $("#treeBlock").html("<div id='dynaTree' class='dynatree'></div>");

        if (data.indexOf("{status:") >= 0) {
            var info = data.substring(data.indexOf("{status:"));
            $("#dynaTree").html(data.substring(0, data.indexOf("{status:")));
            initTree();
            $.bli.information.explain(info);
        } else {
            $("#dynaTree").html(data);
            initTree();
        }
    }
    $(function(){            
		$('#btnBack').click(function(){
			$('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_enter.action" />').submit();
		})
		$('#btnAdd').click(function(){
			$('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_addCharacter.action" />').submit();
		})
		initTree();
	})
</script>
</jsp:attribute>
</tags:layout>