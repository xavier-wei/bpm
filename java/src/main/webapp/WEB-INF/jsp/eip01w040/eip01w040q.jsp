<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w040Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>

    <jsp:attribute name="contents">
        <tags:fieldset legend="查詢條件">
            <form:form id="eip01w040Form" modelAttribute="${caseKey}">
                <!--         <a href="#" id="btnExpandAll">Expand all</a> - -->
                <!-- 		<a href="#" id="btnCollapseAll">Collapse all</a> - -->
                <!-- 		<a href="#" id="btnToggleExpand">Toggle expand</a> -->
                <!-- 		<br> -->
                <div id="dynaTree">
                    <ul>
                        <c:forEach var="items" items="${items}">
                            <c:out value="${items}" escapeXml="false" />
                        </c:forEach>
                        <!--             <li id="H" class="folder expanded active">人事室 -->
                        <!--                 <ul> -->
                        <!--                     <li id="HB" class="folder">訓練專區 -->
                        <!--                         <ul> -->
                        <!--                             <li id="HBA" class="folder">職能訓練 -->
                        <!--                                 <ul> -->
                        <!--                                     <li id="HBAA" class="folder">公文 -->
                        <!--                                 </ul> -->
                        <!--                             <li id="HBB" class="folder">體能訓練 -->
                        <!--                                 <ul> -->
                        <!--                                     <li id="HBBB" class="folder">重訓課程 -->
                        <!--                                 </ul> -->
                        <!--                         </ul> -->
                        <!--                 </ul> -->
                    </ul>
                </div>
                <!-- <button id="btnAddCode">Add nodes programmatically</button> -->
                <!-- <button id="btnActiveNode">Activate item id4.3.2</button> -->
                <!-- <button id="btnRemoveNode">Remove Node</button> -->
<!--                 關鍵字查詢 -->
                <c:if test="${not empty caseData.qryList }">
                    <div class="table-responsive mt-4">
                        <table class="table" id="qryListTable">
                            <thead>
                                <tr>
                                    <th class="text-center">序號</th>
                                    <th class="text-center">主題</th>
                                    <th class="text-center">更新日期</th>
                                    <th class="text-center">操作區</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${caseData.qryList}" var="item" varStatus="status">
                                    <tr data-seq="${status.index + 1 }">
                                        <td class="text-center">
                                            <c:out value="${status.index + 1 }" />
                                        </td>
                                        <td class="text-left">
                                            <c:out value="${item.subject}" />
                                            <form:hidden path="qryList[${status.index}].subject" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${item.upddt}" />
                                            <form:hidden path="qryList[${status.index}].upddt" />
                                        </td>
                                        <td class="text-center" data-fseq="${item.fseq}">
                                            <tags:button id="btnDetail">明細<i class="fas fa-list-alt"></i></tags:button>
                                            <form:hidden path="qryList[${status.index}].fseq" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <form:hidden path="fseq" />
                <form:hidden path="seq" />
                <form:hidden path="filename" />
                <form:hidden path="treenode" />
                <form:hidden path="key" />
            </form:form>
        </tags:fieldset>

        <div id="popModal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">下載專區</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="footers">
        <script type="text/javascript">
            $(function() {
                var gnodeKey;
                $("#dynaTree").dynatree({
                    selectMode: 1, //1:single, 2:multi, 3:multi-hier
                    //checkbox: true,
                    //classNames: {checkbox: "ui-dynatree-radio"},
                    minExpandLevel: 1, // *** 從哪個階層開始展開收合
                    persist: false,
                    autoCollapse: false,
                    activeVisible: true,
                    cookie: false,
                    onActivate: function(node) {
                        //  console.log($("#dynaTree").dynatree("getRoot"))
                        //  $("#dynaTree").dynatree("getRoot").visit(function(dtnode, data){
                        //      console.log(dtnode);
                        //  });
//                         alert('parnet: ' + node.parent.data.title + ' ; key: ' + node.data.key);
//                         console.log(node);
                        //                          alert(node.data.key);
                        gnodeKey = node.data.key;
                        $('#key').val(gnodeKey);
                        ajaxGetList(node.data.key);
                    }
                    // children: JSON.parse($('#treenode').val())
                    // [ // Pass an array of nodes. .replace(/&#034;/g, '\"')
                    //     {title: "Folder 2", key:"A", isFolder: true,
                    //         children: [
                    //             {title: "Sub-item 2.1", key:"AA",isFolder: true,
                    //            	 children: [
                    //            		 {title:"xxx", key:"AAA"}
                    //                 ]},
                    //             {title: "Sub-item 2.2", key:"AB"}
                    //         ]
                    //     },
                    //     {title: "Item 3", key:"B"}
                    // ]
                });

                function ajaxGetList(key) {
                    $('#eip01w040Form').attr('action', '<c:url value="/Eip01w040_pathQuery.action" />')
                    .submit();
//                     $.ajax({
//                         type: "POST",
//                         url: '<c:url value="/Eip01w040_pathQuery.action" />',
//                         data: {
//                             'key': key
//                         },
//                         timeout: 100000,
//                         success: function(data) {
//                             if (data == '') {
//                                 // showAlert('查無資料!');
//                                 $('.prog').html('查無資料');
//                                 $("#msgarea").toggleClass("newheight");
//                             } else {
//                                 $.each(data, function(i, e) {
//                                     console.log(i + ' ' + e.fseq + ' ' + e.subject + ' ' + e
//                                         .upddt);
//                                 });
//                             }
//                         },
//                         error: function(e) {
//                             // showAlert("取得資料發生錯誤");
//                             $('.prog').html('取得資料發生錯誤');
//                             $("#msgarea").toggleClass("newheight");
//                         }
//                     });
                }
                $("#btnToggleExpand").click(function() {
                    $("#dynaTree").dynatree("getRoot").visit(function(dtnode) {
                        dtnode.toggleExpand();
                    });
                    return false;
                });
                $("#btnCollapseAll").click(function() {
                    $("#dynaTree").dynatree("getRoot").visit(function(dtnode) {
                        dtnode.expand(false);
                    });
                    return false;
                });
                $("#btnExpandAll").click(function() {
                    $("#dynaTree").dynatree("getRoot").visit(function(dtnode) {
                        dtnode.expand(true);
                    });
                    return false;
                });
                $("#btnAddCode").click(function() {
                    // Sample: add an hierarchic branch using code.
                    // This is how we would add tree nodes programatically
                    var rootNode = $("#dynaTree").dynatree("getRoot");
                    var childNode = rootNode.addChild({
                        title: "Programatically addded nodes",
                        tooltip: "This folder and all child nodes were added programmatically.",
                        isFolder: true
                    });
                    childNode.addChild({
                        title: "Document using a custom icon",
                        icon: "customdoc1.gif"
                    });
                    return false;
                });
                $('#btnRemoveNode').click(function(e) {
                    e.preventDefault();
                    var node = $("#dynaTree").dynatree("getTree").getNodeByKey(gnodeKey);
                    node.remove();
                })
                $("#btnActiveNode").click(function(e) {
                    e.preventDefault();
                    // var node = $("#dynaTree").dynatree("getTree").getNodeByKey("xxx");
                    // node.addChild({title: "New Node", key: "3333"});
                    // node.data.isFolder = true;
                    // node.render();
                    alert(gnodeKey);
                    var node = $("#dynaTree").dynatree("getTree").getNodeByKey(gnodeKey);
                    node.addChild({
                        title: "New Node",
                        key: "3333"
                    });
                    node.data.isFolder = true;
                    node.render();
                    $("#dynaTree").dynatree("getTree").getNodeByKey("yyy").select();
                });
                // 查詢
                $('#btnQuery').click(function() {
                    $('#eip01w040Form').attr('action', '<c:url value="/Eip01w040_query.action" />')
                        .submit();
                });
                // 明細
                $('#qryListTable #btnDetail').click(function() {
                    $('#fseq').val($(this).parent('td').data('fseq'));
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w040_detail.action" />',
                        data: {
                            'fseq': $(this).parent('td').data('fseq')
                        },
                        timeout: 100000,
                        success: function(data) {
                            if (data == '') {
                                showAlert('查無資料!');
                            } else {
                                var str = '';
                                $.each(data.file, function(i, e) {
                                    str +=
                                        '<a href="javascript:;" class="alink" id=' +
                                        i + '>' +
                                        e + '</a>' + '　';
                                });
                                $('.modal-body').html('公告事項：' + data.msgtype +
                                    '<br>發佈單位：' + data.contactunit +
                                    '<br>主　　題：' + data.subject +
                                    '<br>　　　　　' + data.mcontent +
                                    '<br>附加檔案：' + str +
                                    '<br>更新日期：' + data.upddt +
                                    '<br>聯絡人　：' + data.contactperson +
                                    '<br>聯絡電話：' + data.contacttel);
                                $('#popModal').modal('show');
                            }
                        },
                        error: function(e) {
                            showAlert("取得資料發生錯誤");
                        }
                    });
                });
            });
            // 檔案下載連結
            $(document).on('click', '.alink', function(e) {
                $('#seq').val($(this).attr('id'));
                $('#filename').val($(this).html());
                $('#eip01w040Form').attr('action', '<c:url value="/Eip01w040_getFile.action" />')
                    .submit();
            });
        </script>
    </jsp:attribute>
</tags:layout>