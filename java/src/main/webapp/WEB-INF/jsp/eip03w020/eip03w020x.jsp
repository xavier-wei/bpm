<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 重要列管事項_解除列管detail --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip03w020Controller).CASE_KEY" />
<spring:eval var="mixKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip03w020Controller).MIX_CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<c:set var="mixData" value="${requestScope[mixKey]}" />

<tags:layout>
    <jsp:attribute name="buttons">
        <tags:button id="btnSave" style="display: none;">儲存<i class="fas fa-user-check"></i></tags:button>
	    <tags:button id="btnBack">返回<i class="fas fa-reply"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents" >
        <tags:fieldset legend="列管事項">
            <form:form id="eip03w020Form" name="eip03w020Form" modelAttribute="${mixKey}" method="POST">
                <tags:form-row>
                    <div class="col">
                        <form:label cssClass="col-form-label " path="trkID">列管編號：</form:label>
                        <c:out value="${mixData.trkID}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col">
                        <form:label cssClass="col-form-label " path="trkSts">列管狀態：</form:label>
                        <c:out value="${mixData.trkSts}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col">
                        <form:label cssClass="col-form-label " path="trkCont">內容：</form:label>
                        <c:out value="${mixData.trkCont}" escapeXml="false"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col">
                        <form:label cssClass="col-form-label " path="trkFrom">交辦來源：</form:label>
                        <c:out value="${mixData.trkFrom}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col">
                        <form:label cssClass="col-form-label " path="allStDt">全案列管日期：</form:label>
                        <func:minguo value="${mixData.allStDt}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col">
                        <form:label cssClass="col-form-label " path="clsDt">結案日期：</form:label>
                        <func:minguo value="${mixData.clsDt}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-6">
                        <form:label cssClass="col-form-label " path="creUser">建立人員：</form:label>
                        <c:out value="${mixData.creDept}-${mixData.creUser}"/>
                    </div>
                    <div class="col-md-6">
                        <form:label cssClass="col-form-label " path="creDt">建立時間：</form:label>
                        <c:out value="${mixData.creDt}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-6">
                        <form:label cssClass="col-form-label " path="updUser">更新人員：</form:label>
                        <c:out value="${mixData.updDept }-${mixData.updUser}" />
                    </div>
                    <div class="col-md-6">
                        <form:label cssClass="col-form-label " path="updDt">更新時間：</form:label>
                        <c:out value="${mixData.updDt}"/>
                    </div>
                </tags:form-row>
<%--                <form:hidden path="trkObj"/>--%>
                <form:hidden path="trkObjList"/>
                <tags:form-note>
                    <tags:form-note-item>請點選列管對象填報辦理進度。</tags:form-note-item>
                </tags:form-note>
            </form:form>
        </tags:fieldset>
<%--列管對象--%>
        <fieldset id="resultBox">
            <legend>列管對象</legend>
            <div class="table-responsive">
                <table id="dataTable" class="table">
                    <thead data-orderable="true">
                    <tr>
                        <th style="width: 2%" >項次</th>
                        <th style="" >列管對象</th>
                        <th style="" >處理狀態</th>
                        <th style="" >列管起日</th>
                        <th style="" >列管迄日</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${mixData.kdList}" var="item" varStatus="status">
                            <tr class="text-left">
                                <td class="text-center"><c:out value='${status.count}'/></td>
                                <td class="text-center"><a class="clickDept" href="#" data-obj="${item.trkObj.split('-')[0]}" data-prc="${item.prcSts}"><c:out value="${item.trkObj.split('-')[1]}" /></a></td>
                                <td class="text-center"><c:out value='${item.prcSts}'/></td>
                                <td class="text-center"><func:minguo value="${item.stDt}"/></td>
                                <td class="text-center"><func:minguo value="${item.endDt}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </fieldset>
<%--填報辦理進度--%>
        <fieldset id="progressBox" style="display: none;">
            <legend>填報辦理進度</legend>
            <form:form id="eip03w021Form" name="eip03w021Form" modelAttribute="${mixKey}" method="POST">
                <c:forEach items="${mixData.doubleMap}" var="item" varStatus="status">
                    <div id="${item.key}" style="display: none;">
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="trkObj">列管對象：</form:label>
                                <c:out value="${item.value['trkObjName']}"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="prcSts">處理狀態：</form:label>
                                <c:out value="${item.value['prcSts']}"/>
                            </div>
                        </tags:form-row>
                        <c:choose>
                            <%--開放編輯，顯示儲存按鈕。--%>
<%--                            <c:when test="${item.value['prcSts'] != '已解列' && item.value['isSameDept'] == 'Y'  }">--%>
                            <c:when test="${item.value['prcSts'] != '已解列' && item.value['trkObj'] == mixData.currentDept  }">
                                <tags:form-row>
                                    <div class="col d-flex">
                                        <form:label cssClass="col-form-label star" path="rptCont">辦理情形：</form:label>
                                        <form:textarea path="doubleMap[${item.key}]['rptCont']" cssClass="rptCont" cols="80" rows="5" maxlength="333"/>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col d-flex">
                                        <form:label cssClass="col-form-label " path="rptRate">完成進度：</form:label>
<%--                                        <c:out value="${item.value['rptRate']}"/>--%>
                                        <form:input path="doubleMap[${item.key}]['rptRate']" cssClass="form-control num_only rptRate" val="doubleMap[${item.key}]['rptRate']" size="3" maxlength="3"/>
                                        <span class="pt-2">％</span>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col">
<%--                                        <form:label cssClass="col-form-label " path="rptAskEnd">是否要求解列：</form:label>--%>
<%--                                        <c:out value="${item.value['rptAskEnd']}"/>--%>
                                        <form:label cssClass="col-form-label star" path="doubleMap[${item.key}]['rptAskEnd']" >是否要求解列：</form:label>
                                        <form:radiobutton path="doubleMap[${item.key}]['rptAskEnd']" label="是" value="Y" cssClass="mr-1 rptAskEnd" onclick="defaultRptRate('Y')"/>
                                        <form:radiobutton path="doubleMap[${item.key}]['rptAskEnd']" label="否" value="N" cssClass="mr-1 rptAskEnd" />
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col">
                                        <form:label cssClass="col-form-label " path="rptDept">指定填報單位：</form:label>
<%--                                        <c:out value="${item.value['rptDept']}"/>--%>
                                        <span id="deptRow" data-deptcodes="${item.value['rptDept']}" ><c:out value="${item.value['rptDeptName']}"/></span>
                                        <tags:button id="btnInsertRptDept">新增<i class="fas fa-pencil-alt"></i></tags:button>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col">
                                        <form:label cssClass="col-form-label " path="rptUser">指定填報人員：</form:label>
<%--                                        <c:out value="${item.value['rptUser']}"/>--%>
                                        <span id="usersRow" data-userscodes="${item.value['rptUser']}"><c:out value="${item.value['rptUserName']}"/></span>
                                        <tags:button id="btnInsertRptUser">新增<i class="fas fa-pencil-alt"></i></tags:button>
                                    </div>
                                </tags:form-row>
                            </c:when>
                            <%-- 檢視，隱藏儲存按鈕。--%>
                            <c:otherwise>
                                <tags:form-row>
                                    <div class="col-1.5">
                                        <form:label cssClass="col-form-label " path="rptCont">辦理情形：</form:label>
                                    </div>
                                    <div class="col pt-2">
                                         <c:out value="${item.value['rptCont']}" escapeXml="false"/>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col">
                                        <form:label cssClass="col-form-label " path="rptRate">完成進度：</form:label>
                                        <c:out value="${item.value['rptRate']}"/>
                                        <span class="pt-2">％</span>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col">
                                        <form:label cssClass="col-form-label" path="doubleMap[${item.key}]['rptAskEnd']" >是否要求解列：</form:label>
                                        <form:radiobutton path="doubleMap[${item.key}]['rptAskEnd']" label="是" value="Y" cssClass="mr-1" disabled="true"/>
                                        <form:radiobutton path="doubleMap[${item.key}]['rptAskEnd']" label="否" value="N" cssClass="mr-1" disabled="true"/>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col">
                                        <form:label cssClass="col-form-label " path="rptDept">指定填報單位：</form:label>
                                        <c:out value="${item.value['rptDeptName']}"/>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col">
                                        <form:label cssClass="col-form-label " path="rptUser">指定填報人員：</form:label>
                                        <c:out value="${item.value['rptUserName']}"/>
                                    </div>
                                </tags:form-row>
                            </c:otherwise>
                        </c:choose>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptUpdUser">更新人員：</form:label>
                                <c:out value="${item.value['rptUpdUser']}"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptUpdDt">更新時間：</form:label>
                                <c:out value="${item.value['rptUpdDt']}"/>
                            </div>
                        </tags:form-row>
                    </div>
                </c:forEach>
                <form:hidden path="currentDept"/>
                <form:hidden path="usersCodes"/>
                <form:hidden path="deptCodes"/>
                <form:hidden path="selectedTrkID" value="${caseData.selectedTrkID}"/>
                <tags:form-note>
                        <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
                </tags:form-note>
            </form:form>
        </fieldset>
<%--解除列管--%>
        <fieldset id="releasedBox" style="display: none;" >
            <legend>解除列管</legend>
            <form:form id="eip03w022Form" name="eip03w022Form" modelAttribute="${mixKey}" method="POST">
                <c:forEach items="${mixData.doubleMap}" var="item" varStatus="status">
                    <div id="${item.key}1" style="display: none;" >
                                <tags:form-row>
                                    <div class="col">
                                        <form:label cssClass="col-form-label " path="supCont">回應內容：</form:label>
                                        <c:out value="${item.value['supCont']}"/>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col-md-6">
                                        <form:label cssClass="col-form-label" path="doubleMap[${item.key}]['supAgree']" >是否同意解列：</form:label>
                                        <form:radiobutton path="doubleMap[${item.key}]['supAgree']" label="是" value="Y" cssClass="mr-1" disabled="true"/>
                                        <form:radiobutton path="doubleMap[${item.key}]['supAgree']" label="否" value="N" cssClass="mr-1" disabled="true"/>
                                    </div>
                                </tags:form-row>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>
                            <tags:form-row>
                                <div class="col">
                                    <form:label cssClass="col-form-label " path="supUser">回應人員：</form:label>
                                    <c:out value="${item.value['supDept']}-${item.value['supUser']}"/>
                                </div>
                            </tags:form-row>
                            <tags:form-row>
                                <div class="col">
                                    <form:label cssClass="col-form-label " path="supDt">回應時間：</form:label>
                                    <c:out value="${item.value['supDt']}"/>
                                </div>
                            </tags:form-row>
                    </div>
                </c:forEach>
            </form:form>
        </fieldset>
        <div class="modal fade" id="codesModal" tabindex="-1" aria-labelledby="codesModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="codesModalLabel">單位代碼</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="btnModalConfirm">確認</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">返回</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="usersModal" tabindex="-1" aria-labelledby="usersModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="usersModalLabel">人員代碼</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="btnUsersModalConfirm">確認</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">返回</button>
                    </div>
                </div>
            </div>
        </div>
        </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){
        //返回btnBack
        $('#btnBack').click(function(e) {
            e.preventDefault();
            $('#eip03w020Form').attr('action', '<c:url value="/Eip03w020_backHome.action" />').submit();
        });

        //儲存btnSave
        $('#btnSave').click(function(e) {
            e.preventDefault();
            $('#usersCodes').val($('#usersRow').data('userscodes'));
            $('#deptCodes').val($('#deptRow').data('deptcodes'));


            var rptCont = $('.rptCont').val(); //辦理情形
            var selectedRptAskEnd = $('input.rptAskEnd:checked').val(); //是否要求解列
            var rptRate = $(".rptRate").val() //完成進度

            if(rptCont === '' && selectedRptAskEnd === undefined){
                showAlert('辦理情形為必填\r\n是否要求解列為必選')
            }else if(selectedRptAskEnd === undefined){
                showAlert('是否要求解列為必選')
            }else if(rptCont === ''){
                showAlert('辦理情形為必填')
            }else if(rptRate > 100){
                showAlert('完成進度不可大於100％')
            }else {
                // mixCase
                $('#eip03w021Form').attr('action', '<c:url value="/Eip03w020_update.action" />').submit();
            }
        });

        // <span id="deptRow" data-deptcodes="04;15;">主任秘書室; 工程管理處; </span>
        let obj;
        $('.clickDept').css('text-decoration', 'underline').click(function(e){
            e.preventDefault();
            $('#trkObj').val($(this).data('obj'));

            // 點擊框框示框框
            $('#progressBox, #releasedBox').css('display','block');
            // 先將上一輪點擊顯示的資料隱藏
            $('#trkObjList').val().split(",").forEach(function(child,index){
                child = child.split("-")[0];
                $('#'+child).hide();
                $('#'+child+'1').hide();
            })
            // 顯示此次點擊需出現資料
            obj = $(this).data('obj');
            $('#'+obj).show();
            $('#'+obj+'1').show();

            //若為[KeepTrkDtl.Prcsts!=3(已解列)且KeepTrkDtl.TrkObj為登入者部室]，則綠字欄位開放編輯，顯示儲存按鈕。
            var prcSts = $(this).data('prc');
            var trkObj = $(this).data('obj');

            if(prcSts !== "已解列" && $('#currentDept').val() === trkObj.toString()){
                $('#btnSave').show()
            }else {
                $('#btnSave').hide()
            }
        });

        //新增 btnInsertRptDept
        $('#btnInsertRptDept').click(function (e){
            var codes = $('#deptRow').attr('data-deptCodes');
            var data = {
                'trkObj': obj
            };

            $.ajax({
                url: 'Eip03w020_findRptDept.action',
                type: 'POST',
                contentType : 'application/json',
                data: JSON.stringify(data)
            }).done(function (data) {
                var count = 0;
                var rowContent = $('<div class="row"/>');
                var wholeContent = $('<div/>');
                // var dataLength = data.length;
                var dataLength = data.length - 1;
                data.forEach(rowData => {
                    if(count % 3 === 0){
                        if (count !== 0){
                            wholeContent.append(rowContent);
                        }
                        // rowContent = $('<div class="row"/>');
                    } else if(count >= dataLength){  //最後一行
                        wholeContent.append(rowContent);
                    }
                    var checkbox = '<div class="form-check">';
                    checkbox += '<input class="form-check-input" type="checkbox" value="' + rowData.deptName + '" id="' + rowData.deptID + '"';
                    // 判斷該選框的 id 是否 === deptCodes ，若是則設定 checked 為 true
                    if(codes != null){
                        codes.split(";").forEach( a => {
                            if(a === rowData.deptID){
                                checkbox += ' checked';
                            }
                        })
                    }

                    checkbox += '>';
                    checkbox += '<label class="form-check-label" for="' + rowData.deptID + '">' + rowData.deptName + '</label>';
                    checkbox += '</div>';

                    var col = $('<div class="col-4"/>').append(checkbox);
                    rowContent.append(col);
                    count++;
                });
                var modalBody = $('#codesModal').find('.modal-body');
                modalBody.html('');
                modalBody.append(wholeContent);
                $('#codesModal').modal('show')
            })
        })
        //modal 點擊確認插入RptDept代碼
        $('#btnModalConfirm').click(function () {
            var codes = '';
            var codename = '';
            $.each($('.form-check-input:checkbox:checked'), function () {
                if($(this).attr('id')){
                    codes += $(this).attr('id').trim() + ";";
                    codename += $(this).val() + "; ";
                }
            });
            $('#deptRow').text(codename).attr("data-deptCodes", codes);
            $('#codesModal').modal('hide');
            $('#usersRow').text("").attr("data-usersCodes", "");
        })

        //新增 btnInsertRptUser
        $('#btnInsertRptUser').click(function (e){
            var codes = $('#deptRow').attr('data-deptCodes');
            var data = {
                'codes': codes
            };

            var users = $('#usersRow').attr('data-usersCodes');
            $.ajax({
                url: 'Eip03w020_findRptUser.action',
                type: 'POST',
                contentType : 'application/json',
                data: JSON.stringify(data)
            }).done(function (data) {
                var count = 0;
                var rowContent = $('<div class="row"/>');
                var wholeContent = $('<div/>');
                var dataLength = data.length - 1 ;
                data.forEach(userData => {
                    // alert( userData.empID)
                    if(count > 0 && count % 3 === 0){
                        if (count !== 0){
                            wholeContent.append(rowContent);
                        }
                        // rowContent = $('<div class="row"/>');
                    }else if(count >= dataLength){  //最後一行
                        wholeContent.append(rowContent);
                    }

                    var checkbox = '<div class="form-check">';
                    checkbox += '<input class="form-check-input-users" type="checkbox" value="' + userData.userName + '" id="' + userData.empID + '"';
                    // 判斷該選框的 id 是否 === deptCodes ，若是則設定 checked 為 true
                    if(users != null){
                        users.split(";").forEach( a => {
                            if(a === userData.empID){
                                checkbox += ' checked';
                            }
                        })
                    }

                    checkbox += '>';
                    checkbox += '<label class="form-check-label" for="' + userData.empID + '">' + userData.userName + '</label>';
                    checkbox += '</div>';

                    var col = $('<div class="col-4"/>').append(checkbox);
                    rowContent.append(col);
                    count++;
                });
                var modalBody = $('#usersModal').find('.modal-body');
                modalBody.html('');
                modalBody.append(wholeContent);
                $('#usersModal').modal('show')
            })
        })
        //modal 點擊確認插入RptUser代碼
        $('#btnUsersModalConfirm').click(function () {
            var codes = '';
            var codename = '';
            $.each($('.form-check-input-users:checkbox:checked'), function () {
                if($(this).attr('id')){
                    codes += $(this).attr('id').trim() + ";";
                    codename += $(this).val() + "; ";
                }
            });
            $('#usersRow').text(codename).attr("data-usersCodes", codes);
            $('#usersModal').modal('hide');
        })

    })

    function defaultRptRate(rptRate) {
        if(rptRate === 'Y'){
            $(".rptRate").val('100');
        }
    }

</script>
</jsp:attribute>
</tags:layout>