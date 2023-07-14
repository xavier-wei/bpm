<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 重要列管事項_解除列管detail --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip03w030Controller).CASE_KEY" />
<spring:eval var="mixKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip03w030Controller).MIX_CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<c:set var="mixData" value="${requestScope[mixKey]}" />

<tags:layout>
    <jsp:attribute name="buttons">
        <tags:button id="btnSave" style="display: none;">儲存<i class="fas fa-user-check"></i></tags:button>
	    <tags:button id="btnBack">返回<i class="fas fa-reply"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents" >
        <tags:fieldset legend="列管事項">
            <form:form id="eip03w030Form" name="eip03w030Form" modelAttribute="${mixKey}" method="POST">
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
                        <form:label cssClass="col-form-label " path="allStDt">結案日期：</form:label>
                        <func:minguo value="${mixData.allStDt}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label " path="creUser">建立人員：</form:label>
                        <c:out value="${mixData.creDept}-${mixData.creUser}"/>
                    </div>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label " path="creDt">建立時間：</form:label>
                        <func:minguo value="${mixData.creDt}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label " path="updUser">修改人員：</form:label>
                        <c:out value="${mixData.updDept }-${mixData.updUser}" />
                    </div>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label " path="updDt">修改時間：</form:label>
                        <func:minguo value="${mixData.updDt}"/>
                    </div>
                </tags:form-row>
<%--                <form:hidden path="rptDept"/>--%>
                <form:hidden path="trkObj"/>
<%--                <form:hidden path="selectedTrkID"/>--%>
<%--                <form:hidden path="trkID"/>--%>
                <form:hidden path="trkObjList"/>
                <%--                <form:hidden path="organizerId" value="${mixData.organizerId }"/>--%>
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
            <form:form id="eip03w031Form" name="eip03w031Form" modelAttribute="${mixKey}" method="POST">
                <c:forEach items="${mixData.doubleMap}" var="item" varStatus="status">
                    <div id="${item.key}" style="display: none;">
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptDept">列管對象：</form:label>
                                <c:out value="${item.value['rptDept']}"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="prcSts">處理狀態：</form:label>
                                <c:out value="${item.value['prcSts']}"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptCont">辦理情形：</form:label>
                                <c:out value="${item.value['rptCont']}" escapeXml="false"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptRate">完成進度：</form:label>
                                <c:out value="${item.value['rptRate']}"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptAskEnd">是否要求解列：</form:label>
                                <c:out value="${item.value['rptAskEnd']}"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptDept">指定填報單位：</form:label>
                                <c:out value="${item.value['rptDept']}"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptUser">指定填報人員：</form:label>
                                <c:out value="${item.value['rptUser']}"/>
                            </div>
                        </tags:form-row>
                        <tags:form-row>
                            <div class="col">
                                <form:label cssClass="col-form-label " path="rptUpdUser">更新人員：</form:label>
                                <c:out value="${item.value['rptUpdDept']}-${item.value['rptUpdUser']}"/>
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
            </form:form>
        </fieldset>
<%--解除列管--%>
        <fieldset id="releasedBox" style="display: none;" >
            <legend>解除列管</legend>
            <form:form id="eip03w032Form" name="eip03w032Form" modelAttribute="${mixKey}" method="POST">
                <c:forEach items="${mixData.doubleMap}" var="item" varStatus="status">
                    <div id="${item.key}1" style="display: none;" >
                        <c:choose>
                            <c:when test="${item.value['prcSts'] == '待解列'}">
                                <tags:form-row>
                                    <div class="col d-flex">
                                        <form:label cssClass="col-form-label " path="supCont">回應內容：</form:label>
                                        <form:textarea path="doubleMap[${item.key}]['supCont']" cssClass="" cols="80" rows="5" maxlength="333"/>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col-md-6">
                                        <form:label cssClass="col-form-label star" path="doubleMap[${item.key}]['supAgree']" >是否同意解列：</form:label>
                                        <form:radiobutton path="doubleMap[${item.key}]['supAgree']" label="是" value="Y" cssClass="mr-1" />
                                        <form:radiobutton path="doubleMap[${item.key}]['supAgree']" label="否" value="N" cssClass="mr-1" />
                                    </div>
                                </tags:form-row>
                            </c:when>
                            <c:otherwise>
                                <tags:form-row>
                                    <div class="col">
                                        <form:label cssClass="col-form-label " path="supCont">回應內容：</form:label>
                                        <c:out value="${item.value['supCont']}"/>
                                    </div>
                                </tags:form-row>
                                <tags:form-row>
                                    <div class="col-md-6">
                                        <form:label cssClass="col-form-label star" path="doubleMap[${item.key}]['supAgree']" >是否同意解列：</form:label>
                                        <form:radiobutton path="doubleMap[${item.key}]['supAgree']" label="是" value="Y" cssClass="mr-1" disabled="true"/>
                                        <form:radiobutton path="doubleMap[${item.key}]['supAgree']" label="否" value="N" cssClass="mr-1" disabled="true"/>
                                    </div>
                                </tags:form-row>
                            </c:otherwise>
                        </c:choose>
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
                <tags:form-note>
                        <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
                </tags:form-note>
                <form:hidden path="trkID"/>

            </form:form>
        </fieldset>
        </jsp:attribute>

    <jsp:attribute name="footers">
<script>
    $(function(){

        //返回btnBack
        $('#btnBack').click(function(e) {
            e.preventDefault();
            $('#eip03w030Form').attr('action', '<c:url value="/Eip03w030_enter.action" />').submit();
        });

        //儲存btnSave
        $('#btnSave').click(function(e) {
            e.preventDefault();
            $('#eip03w032Form').attr('action', '<c:url value="/Eip03w030_update.action" />').submit();
        });

        $('.clickDept').css('text-decoration', 'underline').click(function(e){
            e.preventDefault();
            $('#trkObj').val($(this).data('obj'));
            // 點擊框框示框框
            $('#progressBox, #releasedBox').css('display','block');
            // 先將上一輪點擊顯示的資料隱藏
            $('#trkObjList').val().split(",").forEach(function(child,index){
                $('#'+child).hide();
                $('#'+child+'1').hide();
            })
            // 顯示此次點擊需出現資料
            var obj = $(this).data('obj');
            $('#'+obj).show();
            $('#'+obj+'1').show();

            //若KeepTrkDtl.Prcsts=2(待解列)，則綠字欄位開放編輯，顯示儲存按鈕。
            var prcSts = $(this).data('prc')
            if(prcSts === "待解列"){
                $('#btnSave').show()
            }else {
                $('#btnSave').hide()
            }
        });
    })

</script>
</jsp:attribute>
</tags:layout>