<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w010Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="heads">
        <style>
            .col-form-label {
                min-width: 170px;
            }

            .custom-file-label {
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
            }

            #btnPath {
                height: 90%;
                margin-top: 2.5px;
                padding-top: 6px;
                padding-bottom: 6px;
                padding-left: 8px;
                padding-right: 8px;
            }

            .tmp-label {
                min-width: 85px;
            }

            .img-box {
                margin: 10px;
                display: inline-block;
                position: relative;
            }

            .myimage {
                max-width: 150px;
                min-width: 150px;
            }

            .image-remove {
                --background-color: white;

                font-size: 28px;
                width: 20px;
                height: 20px;
                text-align: center;
                border-radius: 100%;
                transform: rotate(45deg);
                cursor: pointer;
                opacity: 0.5;
                top: 2px;
                right: 2px;
                display: flex;
                position: absolute;
                align-items: center;

                justify-content: center;
            }

            .image-remove:hover {
                font-weight: 700;
                opacity: 1;
            }
			.textblock {
			    padding-left: 175px;
			}
			.imgblock {
			    padding-left: 170px;
			}
            .text-box {
                margin: 7px;
                padding-right: 25px;
                display: inline-block;
                position: relative;
            }

            .text-remove {
                padding-bottom: 4px;
                background-color: black;
                font-size: 23px;
                width: 18px;
                height: 18px;
                color: white;
                text-align: center;
                border-radius: 100%;
                transform: rotate(135deg);
                cursor: pointer;
                opacity: 0.3;
                top: 2px;
                right: 0px;
                display: flex;
                position: absolute;
                align-items: center;
                justify-content: center;
            }

            .text-remove:hover {
                background-color: red;
                font-weight: 800;
                opacity :0.6;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="buttons">
        <tags:button id="btnSave">
            存檔<i class="fas fa-user-check"></i>
        </tags:button>
        <tags:button id="btnPreview">
            預覽<i class="fas fa-window-restore"></i>
        </tags:button>
        <tags:button id="btnUpload">
            上稿<i class="fas fa-paper-plane"></i>
        </tags:button>
        <tags:button id="btnDelete">
            刪除<i class="fas fa-trash-alt"></i>
        </tags:button>
        <tags:button id="btnBack">
            返回<i class="fas fa-reply"></i>
        </tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset legend="編輯">
            <form:form id="eip01w010Form" modelAttribute="${caseKey}" enctype="multipart/form-data">
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="pagetype">頁面型態：</form:label>
                    <div class="col-sm-6">
                        <form:select path="pagetype" cssClass="form-control">
                            <option value="" selected disabled hidden>請選擇</option>
                            <c:forEach var="item" items="${caseData.pagetypes}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="status">狀態：</form:label>
                    <div class="col-sm-6 my-auto">
                        <c:choose>
                            <c:when test="${caseData.status == '0' || caseData.status == '1'}">
                                <form:hidden path="status" />
                                <form:hidden path="statusText" />
                                <c:out value="${caseData.statusText}" />
                            </c:when>
                            <c:otherwise>
                                <form:select path="status" cssClass="form-control">
                                    <c:forEach var="item" items="${caseData.statuses2}" varStatus="status">
                                        <form:option value="${item.codeno}">
                                            <c:out value="${item.codename}" />
                                        </form:option>
                                    </c:forEach>
                                </form:select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="attributype">屬性：</form:label>
                    <div class="col-sm-6">
                        <form:select path="attributype" cssClass="form-control">
                            <option value="" selected disabled hidden>請選擇</option>
                            <c:forEach var="item" items="${caseData.attrtypes}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="msgtype">訊息類別：</form:label>
                    <div class="col-sm-6">
                        <form:select path="msgtype" cssClass="form-control">
                            <option value="">請先選擇屬性</option>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="locatearea">顯示位置：</form:label>
                    <div class="col-sm-6 d-flex my-auto">
                        <c:forEach var="item" items="${caseData.locateareas}" varStatus="status">
                            <label class="mb-0 d-inline-flex" style="margin-left: 2px;">
                                <form:radiobutton path="locatearea" value="${item.codeno }" cssClass="mr-1" />
                                <span class="mr-2">${item.codename }</span>
                            </label>
                        </c:forEach>
                        <!--                             <div class="d-inline-flex mt-2"> -->
                        <%--                                 <form:checkbox path="locatearea" value="${item.codeno }" --%>
                        <%--                                     id="${item.codeno }${item.codename }" cssClass="d-flex form-check" /> --%>
                        <%--                                 <label class="ml-1" for="${item.codeno }${item.codename }" --%>
                        <%--                                     style="margin-bottom: 2px;">${item.codename --%>
                        <%--                                     }</label> --%>
                        <!--                             </div> -->
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="availabledep">分眾：</form:label>
                    <div class="col-md col-sm-6">
                        <c:forEach var="item" items="${caseData.availabledeps}" varStatus="status">
                            <div class="d-inline-flex mt-2">
                                <form:checkbox path="availabledep" value="${item.codeno }"
                                    id="${item.codeno }${item.codename }" cssClass="d-flex form-check" />
                                <label class="ml-0" for="${item.codeno }${item.codename }"
                                    style="margin-bottom: 2px;">${item.codename
                                    }</label>
                            </div>
                        </c:forEach>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-3 col-md d-flex align-items-center">
                        <form:label cssClass="col-form-label star" path="issearch">是否提供外部查詢：</form:label>
                        <label class="mb-0 d-flex" style="margin-left: 2px;">
                            <form:radiobutton path="issearch" value="1" cssClass="mr-1" />
                            <span class="mr-2">是</span>
                        </label>
                        <label class="mb-0 d-flex" style="margin-left: 2px;">
                            <form:radiobutton path="issearch" value="2" cssClass="mr-1" />
                            <span class="mr-2">否</span>
                        </label>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="showorder">顯示順序：</form:label>
                    <div class="col-sm-6">
                        <form:input path="showorder" cssClass="form-control num_eng_only" size="2" maxlength="2" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-3 col-md d-flex align-items-center">
                        <form:label cssClass="col-form-label" path="istop">是否置頂：</form:label>
                        <label class="mb-0 d-flex">
                            <form:radiobutton path="istop" value="1" cssClass="mr-1" />
                            <span class="mr-2">是</span>
                        </label>
                        <label class="mb-0 d-flex">
                            <form:radiobutton path="istop" value="2" cssClass="mr-1" />
                            <span class="mr-2">否</span>
                        </label>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-3 col-md d-flex align-items-center">
                        <form:label cssClass="col-form-label" path="isfront">前台是否顯示：</form:label>
                        <label class="mb-0 d-flex">
                            <form:radiobutton path="isfront" value="1" cssClass="mr-1" />
                            <span class="mr-2">是</span>
                        </label>
                        <label class="mb-0 d-flex">
                            <form:radiobutton path="isfront" value="2" cssClass="mr-1" />
                            <span class="mr-2">否</span>
                        </label>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="subject">主旨/連結網址：</form:label>
                    <div class="col-sm-6">
                        <form:input path="subject" cssClass="form-control" style="width:100%;" maxlength="400"
                            placeholder="400字內" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-3 col-md d-flex align-items-center">
                        <form:label cssClass="col-form-label" path="oplink">是否需要另開視窗：</form:label>
                        <label class="mb-0 d-flex">
                            <form:radiobutton path="oplink" value="1" cssClass="mr-1" />
                            <span class="mr-2">是</span>
                        </label>
                        <label class="mb-0 d-flex">
                            <form:radiobutton path="oplink" value="2" cssClass="mr-1" />
                            <span class="mr-2">否</span>
                        </label>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="mcontent">內文：</form:label>
                    <div class="col-sm-6">
                        <div class="d-inline-flex" style="width:100%;">
                            <form:textarea cssClass="col-12 form-control" path="mcontent" maxlength="4000" rows="3"
                                placeholder="4000字內"></form:textarea>
                        </div>
                        <div class="d-inline-flex" id="word-count"
                            style="font-size: 14px; position: absolute; margin: auto; bottom: 0; right: 2%;"></div>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="images">內文附圖：</form:label>
                    <div class="custom-file" style="width:50%;">
                        <input type="file" class="custom-file-input" id="images" name="images" multiple
                            accept="image/*">
                        <label class="custom-file-label" for="images" style="margin-left: 2px;">Choose images</label>
                    </div>
                </tags:form-row>
                <tags:form-row cssClass="textblock">
                    <c:forEach var="item" items="${caseData.imageFileNameMap}" >
                        <div class="text-box"> ${item.value }<span class="text-remove" id="${item.key }">+</span></div>
                    </c:forEach>
                </tags:form-row>
                <tags:form-row cssClass="imgblock">

                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="indir">存放目錄：</form:label>
                    <div class="col-sm-6 d-flex">
                        <form:input path="indir" cssClass="form-control" size="40" maxlength="0" />
                        <tags:button cssClass="ml-2" id="btnPath">選取目錄</tags:button>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="files">附加檔案：</form:label>
                    <div class="custom-file" style="width:50%;">
                        <input type="file" class="custom-file-input" id="files" name="files" multiple
                            accept=".docx,.doc,.xls,.xlsx,.pdf,.txt,.csv,.ods">
                        <label class="custom-file-label" for="files" style="margin-left: 2px;">Choose files</label>
                    </div>
                </tags:form-row>
                <tags:form-row cssClass="textblock">
                    <c:forEach var="item" items="${caseData.fileNameMap}" >
                        <div class="text-box">${item.value }<span class="text-remove" id="${item.key }">+</span></div>
                    </c:forEach>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="releasedt">上架時間：</form:label>
                    <div class="col-sm-6">
                        <form:input path="releasedt" cssClass="form-control d-inline-block dateTW date" size="10"
                            maxlength="7" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="offtime">下架時間：</form:label>
                    <div class="col-sm-6">
                        <form:input path="offtime" cssClass="form-control d-inline-block dateTW date" size="10"
                            maxlength="7" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="contactunit">聯絡單位：</form:label>
                    <div class="col-sm-6">
                        <form:select path="contactunit" cssClass="form-control">
                            <c:forEach var="item" items="${caseData.contactunits}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="contactperson">聯絡人：</form:label>
                    <div class="col-sm-6">
                        <form:input path="contactperson" cssClass="form-control" size="10" maxlength="10" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label star" path="contacttel">連絡電話：</form:label>
                    <div class="col-sm-6">
                        <form:input path="contacttel" cssClass="form-control num_eng_only" size="10" maxlength="10" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="memo">備註：</form:label>
                    <div class="col-sm-6">
                        <form:textarea cssClass="col-12 form-control" path="memo" maxlength="400" style="width:100%;"
                            rows="3" placeholder="400字內"></form:textarea>
                    </div>
                </tags:form-row>
                <c:if test="${caseData.status == '4' || caseData.status == 'X' }">
                    <tags:form-row>
                        <form:label cssClass="col-form-label" path="offreason">下架原因：</form:label>
                        <div class="col-sm-6">
                            <form:textarea cssClass="col-12 form-control" path="offreason" maxlength="400"
                                style="width:100%;" rows="3"></form:textarea>
                        </div>
                    </tags:form-row>
                </c:if>
                <tags:form-row>
                    <div class="col-xl-3 col-6 my-auto">
                        <form:label cssClass="col-form-label" path="creatid">建立人員：</form:label>
                        <c:out value="${caseData.creatid }" />
                        <form:hidden path="creatid" />
                    </div>
                    <div class="col-md-6 col-sm-12 my-auto">
                        <form:label cssClass="col-form-label" path="creatdt">建立時間：</form:label>
                        <c:out value="${caseData.creatdt }" />
                        <form:hidden path="creatdt" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-xl-3 col-6 my-auto">
                        <form:label cssClass="col-form-label" path="updid">更新人員：</form:label>
                        <c:out value="${caseData.updid }" />
                        <form:hidden path="updid" />
                    </div>
                    <div class="col-md-6 col-sm-12 my-auto">
                        <form:label cssClass="col-form-label" path="upddt">更新時間：</form:label>
                        <c:out value="${caseData.upddt }" />
                        <form:hidden path="upddt" />
                    </div>
                </tags:form-row>
                <tags:form-note>
                    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
                </tags:form-note>
                <form:hidden path="mode" />
                <form:hidden path="fseq" />
                <form:hidden path="keep" />
                <form:hidden path="tmpPath" />
                <form:hidden path="p1id" />
                <form:hidden path="p1page" />
                <form:hidden path="p1title" />
                <form:hidden path="seq" />
            </form:form>

            <div id="popModal1" class="modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">預覽</h5>
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
            <div id="popModal" class="modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">新增/選取目錄</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="d-flex">
                                <div class="col-6">
                                    <div style="margin-top:10px;padding-bottom:19.5px;height:100%;">
                                        <fieldset style="padding:3px;height:100%;">
                                            <div style="margin:5px;">
                                                <button type="button" class="btn btn-outline-be btn-sm mr-1"
                                                    id="newFolder">新增資料夾</button>
                                                <button type="button" class="btn btn-outline-be btn-sm mr-1"
                                                    id="newSubFolder">新增子資料夾</button>
                                                <button type="button" class="btn btn-outline-be btn-sm"
                                                    id="rename">儲存</button>
                                            </div>
                                            <hr>
                                            <div class="form-row py-1 no-gutters">
                                                <label for="tmpN" class="col-form-label tmp-label">目錄名稱：</label>
                                                <input type="text" class="form-control" id="tmpN" size="24">
                                                <input type="hidden" class="form-control" id="tmpP">
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                                <div class="col-6" id="dynaTree">
                                    <!--  <ul> -->
                                    <!--      <li id="H" class="folder expanded">人事室 -->
                                    <!--          <ul> -->
                                    <!--              <li id="HB" class="folder">訓練專區 -->
                                    <!--                  <ul> -->
                                    <!--                      <li id="HBA" class="folder">職能訓練 -->
                                    <!--                          <ul> -->
                                    <!--                              <li id="HBAA" class="folder">公文 -->
                                    <!--                          </ul> -->
                                    <!--                      <li id="HBB" class="folder">體能訓練 -->
                                    <!--                          <ul> -->
                                    <!--                              <li id="HBBB" class="folder">重訓課程 -->
                                    <!--                          </ul> -->
                                    <!--                  </ul> -->
                                    <!--          </ul> -->
                                    <!--  </ul> -->
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-be" id="pathSave"
                                data-dismiss="modal">確定</button>
                        </div>
                    </div>
                </div>
            </div>
        </tags:fieldset>
    </jsp:attribute>

    <jsp:attribute name="footers">
        <script type="text/javascript">
            $(function() {
                //$("#datepicker").datepicker({});
                var gnodeKey; // 紀錄當前活動節點
                var seq = 0; // 新資料夾流水號
                let tmp = '新資料夾';

                function getAllNode(attr) {
                    $.ajax({
                        type: 'POST',
                        url: '<c:url value="/Eip01w010_getTree.action" />',
                        data: {
                            'attr': attr
                        },
                        success: function(data) {
                            $('#dynaTree').html('');
                            var str = '';
                            $.each(data, function(k, v) {
                                str += v;
                            });
                            $('#dynaTree').append(str);
                            initTree();
                        },
                        error: function(e) {
                            console.log(e);
                        }
                    });
                }
                // 初始化樹
                function initTree() {
                    $("#dynaTree").dynatree({
                        selectMode: 1, //1:single, 2:multi, 3:multi-hier
                        minExpandLevel: 1,
                        onActivate: function(node) {
                            gnodeKey = node.data.key; // 紀錄當前活動節點
                            $('#tmpN').val(node.data.title);
                            $('#tmpP').val(gnodeKey);
                        }
                    });
                }
                // modal 儲存
                $('#pathSave').click(function() {
                    if (gnodeKey != null || gnodeKey != '') {
                        var node = $("#dynaTree").dynatree("getTree").getNodeByKey(gnodeKey);
                        $('#indir').val(node.data.filepath);
                        $('#tmpPath').val($('#tmpP').val());
                    }
                });
                // modal 重新命名
                $('#rename').click(function() {
                    if (gnodeKey.includes('_')) { // 原已存在於資料庫之資料夾名稱不允修改
                        var tmpName = $('#tmpN').val();
                        var node = $("#dynaTree").dynatree("getTree").getNodeByKey(gnodeKey);
                        var path = node.data.filepath.split('/');
                        path[path.length - 1] = tmpName;
                        node.data.title = tmpName;
                        node.data.filepath = path.join('/');
                        node.render();
                        if (node.hasChildren()) { // 若先改子層資料夾再回去改父層資料夾名稱
                            node.visit(function(dnode) { // 訪問子層 重新修改filepath
                                var subNodePath = dnode.data.filepath.split('/');
                                subNodePath[path.length - 1] = tmpName;
                                dnode.data.filepath = subNodePath.join('/');
                            })
                        }
                    }
                });
                // modal 新增資料夾
                $('#newFolder').click(function() {
                    var rootNode = $("#dynaTree").dynatree("getRoot");
                    var childNode = rootNode.addChild({
                        title: tmp,
                        key: '_' + (seq++),
                        isFolder: true,
                        activate: true,
                        filepath: '/' + tmp
                    });
                    rootNode.render();
                    gnodeKey = rootNode.childList[rootNode.childList.length - 1].data.key;
                    $('#tmpN').val(tmp);
                    $('#tmpP').val(gnodeKey);
                });
                // modal 新增子資料夾
                $('#newSubFolder').click(function() {
                    var node = $("#dynaTree").dynatree("getTree").getNodeByKey(gnodeKey);
                    node.addChild({
                        title: tmp,
                        key: node.data.key + '_' + (seq++),
                        isFolder: true,
                        activate: true,
                        filepath: node.data.filepath + '/' + tmp
                    });
                    node.data.isFolder = true;
                    node.expand(true);
                    node.render();
                    gnodeKey = node.childList[node.childList.length - 1].data.key;
                    $('#tmpN').val(tmp);
                    $('#tmpP').val(gnodeKey);
                });
                // 開啟 選擇路徑
                $('#btnPath').click(function() {
                    $('#popModal').modal('show');
                });
                // 清除
                $('#btnClear').click(function() {
                    $('input[type="text"]').val('');
                });
                // 存檔
                $("#btnSave").click(function(e) {
                    e.preventDefault();
                    localStorage.clear();
                    $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_save.action" />')
                        .submit();
                });
                // 預覽
                $("#btnPreview").click(function(e) {
                    //                             $('#popModal1').modal('show');
                    //                     let size = localStorage.length;
                    //                     var filelist = [];
                    //                     for (var i = 0; i < size; i++) {
                    //                         filelist.push(localStorage.getItem(localStorage.key(i)));
                    //                     }
                    //                     $('#imagesDecode').val(filelist);
                    //console.log(Object.getPrototypeOf($('#images')[0].files));
                    //Object.getPrototypeOf($('#images')[0].files) = filelist;
                    localStorage.clear();
                });
                $('#word-count').hide();
                $('#mcontent').on('keydown keyup keypress change', function(e) {
                    let count = $(this).val().length;
                    $('#word-count').css('visibility', '');
                    $('#word-count').html('已輸入' + count + '字');
                    if (count == 0) {
                        $('#word-count').css('visibility', 'hidden');
                    }
                });
                // 上稿
                $("#btnUpload").click(function(e) {
                    $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_btnup.action" />')
                        .submit();
                });
                // 刪除
                $('#btnDelete').click(function(e) {
                    showConfirm('確定要刪除資料？', () => {
                        $('#eip01w010Form').attr('action',
                                '<c:url value="/Eip01w010_btndel.action" />')
                            .submit();
                    });
                });
                // 返回
                $("#btnBack").click(function(e) {
                    e.preventDefault();
                    $('#fseq').val('');
                    if ($('#mode').val() === 'I' || $('#mode').val() === 'Q') {
                        $('#mode').val('');
                        localStorage.clear();
                        $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_enter.action" />')
                            .submit();
                    } else {
                        $('#status').val(status); // D
                        $('#mode').val('Q');
                        $('#eip01w010Form').attr('action',
                                '<c:url value="/Eip01w010_queryList.action" />')
                            .submit();
                    }
                });
                let status = '<c:out value="${caseData.status }"/>';
                let availabledep = '<c:out value="${caseData.availabledep }"/>';
                let $availabledep = availabledep.split(",")
                if (status == 4 || status == 'X') {
                    $('#btnUpload').attr('disabled', true);
                }
                // 分眾 checkbox
                $.each($availabledep, function(k, v) {
                    $this = v;
                    $(':checkbox[name="availabledep"]').filter(function() {
                        return this.value === $this;
                    }).prop('checked', true);
                });
                // 屬性下拉選單 連動 1.訊息類別下拉選單 2.樹
                $('#attributype').on('change', function() {
                    var selectValue = $(this).val();
                    $('#msgtype').empty();
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w010_getOptionData.action" />',
                        data: {
                            'attr': selectValue
                        },
                        timeout: 100000,
                        success: function(data) {
                            if (data != null) {
                                $.each(data, function(i, e) {
                                    $('#msgtype').append("<option value='" + i +
                                        "'>" + e + "</option>");
                                });
                            }
                            let reportNo = '<c:out value="${caseData.msgtype}"/>';
                            if (reportNo !== '') {
                                $("#msgtype option[value=" + reportNo + "]").prop(
                                    'selected', 'selected');
                            }
                        },
                        error: function(e) {
                            showAlert("取得資料發生錯誤");
                        }
                    });
                    getAllNode(selectValue);
                });
                $('#attributype').trigger('change');
                // 選擇檔案
                $("#files").on("change", function() {
                    let counts = $(this)[0].files.length;
                    if (counts > 10) {
                        alert('超過檔案數量 請重新選擇!');
                        return false;
                    }
                    var files = [];
                    for (var i = 0; i < counts; i++) {
                        files.push($(this)[0].files[i].name);
                    }
                    $(this).next('.custom-file-label').html(files.join(', '));
                });
                $("#images").on("change", function() {
                	$('.imgblock').html('');
                    let counts = $(this)[0].files.length;
                    if (counts > 10) {
                        alert('超過檔案數量 請重新選擇!');
                        return false;
                    }
                    var files = [];
                    for (var i = 0; i < counts; i++) {
                        console.log($(this)[0].files[i]);
                        files.push($(this)[0].files[i].name);
                        getBase64($(this)[0].files[i], $(this)[0].files[i].name);
                    }
                    $(this).next('.custom-file-label').html(files.join(', '));
                });
                function getBase64(file, filename) {
                    var reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onload = function() {
                        //$('.imgblock').append(
                        //  '<div class="d-flex col-xxl-2 col-xl-3 col-lg-4 col-md-6 col-sm-8"><img style="max-width: 200px;max-height: 170px;" src="' +
                        //  reader.result + '"/></div>');
                        $('.imgblock').append(
                            '<div class="img-box"><img class="myimage" src="' +
                            reader.result +
                            '"/></div>'); // <span class="image-remove" @click="removeImage(index)">+</span> 
                    };
                    reader.onerror = function(error) {
                        console.log('Error: ', error);
                    };
                }
                $('.text-remove').click(function(){
                    showConfirm('確定要刪除附檔？', () => {
                    	$('#seq').val(this.id);
                        $('#eip01w010Form').attr('action',
                                '<c:url value="/Eip01w010_delFile.action" />')
                            .submit();
                    });
                })
            });
        </script>
    </jsp:attribute>
</tags:layout>