document.onkeydown = onQuery;
function onQuery() {
    if (event.keyCode == 13) {
        if (typeof(query) == "function") {
            var srcname = event.srcElement.name;
            if (!isObjNull(srcname) && srcname == "turntovalue") {
                turnQuery();
            }
            else {
                validatezero("perPageCount");
                query();
            }
        }
    }
}
if (navigator.userAgent.indexOf("MSIE") > 0) {
    window.attachEvent("onload", initSelectlist);
}
else if (navigator.userAgent.indexOf("Firefox")) {
    window.addEventListener("onload", initSelectlist);
}
else {
    alert("该系统支持IE,Firefox浏览器，系统某些功能在别的浏览器上可能受影响！");
}

function initSelectlist() {
    // setElementValue(null,"selectlist","#");
}
function isObjNull(obj) {
    if (obj == null || "undefined" == obj || obj == "null" || obj == "" || undefined == obj) {
        return true;
    }

    return false;
}

function checkNull(obj, msg) {
    if (isObjNull(obj.val())) {
        alert(msg);
        obj.focus();
        return true;
    }
    return false;
}

/**
 * 功能:获取指定标签的值
 * 如果target为空则为本页面的值
 * 如果需取父页面 target 为 window.parent
 */
function getElementValue(target, name) {
    if (null == target) {
        if (null != document.getElementById(name)) {
            return document.getElementById(name).value + "";
        }
    } else {
        if (null != target.document.getElementById(name)) {
            return target.document.getElementById(name).value + "";
        }
    }
    return "";
}
/**
 * 功能:设置指定标签的值
 * 如果target为空则为本页面的值
 * 如果需设置父页面 target 为 window.parent
 */
function setElementValue(target, name, value) {
    if (null != target) {
        if (null != target.document.getElementById(name)) {
            target.document.getElementById(name).value = value;
        }
    } else {
        if (null != document.getElementById(name)) {
            document.getElementById(name).value = value;
        }
    }
}


function setFocus(target, name) {
    if (null != target) {
        if (null != target.document.getElementById(name)) {
            target.document.getElementById(name).focus();
        }
    } else {
        if (null != document.getElementById(name)) {
            document.getElementById(name).focus();
        }
    }
}

/*
 Function Name:	trim
 Description	 :	去掉字符串两边空格
 Params:					str	-- input string
 Return Value:		trimmed string
 Author:					Ada	shi(0174)
 CreatedTime:		2001/09/18
 */
function trim(s) {
    var m = s.match(/^\s*(\S+(\s+\S+)*)\s*$/);
    return (m == null) ? "" : m[1];
}


/*
 Function Name:				isInteger
 Description	 :				to check whether the string is representing an integer
 Params:
 sNum								one number value string
 Return Value:
 true								if the string represents an integer
 false								if not
 Author:					Bob Huang(0243)
 CreatedTime:		2001/09/11
 */
function isInteger(sData) {
    if (sData == "")
        return false;
    var l = sData.length;

    for (var i = 0; i < l; i++) {
        var digit = sData.charAt(i);
        if (digit == ".") {
            return false;
        }
        else if (digit < "0" || digit > "9")
            return false;
    }
    return true;
    ;
}


/*
 Function Name:	isDate
 Description:		to check whether a string representing a date value.
 Params:
 sDate		a string representing a date value
 Return Value:
 true		if it's a date value string
 false		if not
 Annotation:			The syntax of the date string should be:yyyy/mm/dd.
 Author:					Leo Bao(0096)
 CreatedTime:		2001/05/15
 */

function isDate(dateString) {
    var pattern1 = /\d{4}-\d{2}-\d{2}/;
    var pattern2 = /\d{4}\/\d{2}\/\d{2}/;
    var pattern3 = /\d{4}\.\d{2}\.\d{2}/;
    var monthDay = new Array(0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    //var val = obj.value;
    var val = dateString;
    if (val.length == 10 && (pattern1.test(val) || pattern2.test(val) || pattern3.test(val))) {
        var y = val.substring(0, 4);
        var m = val.substring(5, 7);
        var d = val.substring(8);
        if (m.substring(0, 1) == "0") m = m.substring(1);
        if (d.substring(0, 1) == "0") d = d.substring(1);
        var yint = parseInt(y);
        var mint = parseInt(m);
        var dint = parseInt(d);
        if (yint < 1900 || mint < 0 || mint > 12 || dint < 0 || dint > 31)
            return false;
        if (mint == 2 && dint == 29 && yint % 4 != 0)
            return false;
        if (dint > monthDay[m])
            return false;
        return true;
    }
    return false;
}


/*
 *检查页面控件是否为空

 *array1 控件名数组

 *array2 提示给用户控件名数组
 */
function isnull(array1, array2) {
    var res = "";
    var temp = null;
    for (i = 0; i < array1.length; i++) {
        var element = document.getElementById(array1[i]);
        if (trim(element.value) == "") {
            if (null == temp) {
                temp = element;
            }
            if (res == "") {
                res = array2[i];
            } else {
                res = res + "、" + array2[i];
            }
        }
    }
    if (res != "") {
        temp.focus();
        return (res + "不能为空");
    } else {
        return "";
    }
}

/**
 * 检查 日期格式
 *obj  控件对象 （传入 this）
 *name 控件中文名称
 */
function checkDate(obj, name) {
    if (isDate(obj.value) == false && obj.value != "") {
        alert(name + "-日期格式错误");
        obj.value = "";
        obj.focus();
    }
}

/**
 * 检查 浮点型
 *obj  控件对象 （传入 this）
 *name 控件中文名称
 */
function checkDouble(obj, name) {
    if (checkIsDouble(obj.value) == false && obj.value != "") {
        alert(name + "-浮点型数据格式错误");
        obj.value = "";
        obj.focus();
    }
}

/**
 * 检查 整型
 *obj  控件对象 （传入 this）
 *name 控件中文名称
 */
function checkInteger(obj, name) {
    if (isInteger(obj.value) == false && obj.value != "") {
        alert(name + "-整数型数据格式错误");
        obj.value = "";
        obj.focus();
    }
}


/**
 * 检查 电话号码
 *obj  控件对象 （传入 this）
 *name 控件中文名称
 */
function checkPhone(obj, name) {
    if (isTelephoneNumber(obj.value) == false && obj.value != "") {
        alert(name + "-格式错误");
        obj.value = "";
        obj.focus();
    }
}

/**
 *  检查 Email
 *obj  控件对象 （传入 this）
 *name 控件中文名称
 */
function checkEmail(obj, name) {
    if (chkemail(obj.value) == false && obj.value != "") {
        alert(name + "-电子邮箱格式错误");
        obj.value = "";
        obj.focus();
    }
}

/**
 *  检查 邮政编码
 *obj  控件对象 （传入 this）
 *name 控件中文名称
 */
function checkPost(obj, name) {
    if (isPostalcode(obj.value) == false && obj.value != "") {
        alert(name + "-邮政编码格式错误");
        obj.value = "";
        obj.focus();
    }
}
function getDate() {
    var d = new Date();
    var s = "";
    s += d.getFullYear();
    s += "-";
    if ((d.getMonth() + 1) < 10) s += "0";
    s += d.getMonth() + 1;
    s += "-";
    if (d.getDate() < 10) s += "0";
    s += d.getDate();
    return s;
}


/**************** public variable section **************/
//added by xb 2007/12/20

/*
 查询页面table的载入
 */
function loadtable() {
    var parForm = window.parent.document.forms[0];
    var obj_parRows = window.parent.document.getElementById("icount");     //父页面总记录数
    var obj_parCurrPage = window.parent.document.getElementById("currp");  //父页面当前页码显示
    var obj_CurrPage = window.parent.document.getElementById("currPage");  //父页面当前页码显示
    var obj_parCountPage = window.parent.document.getElementById("countpage");

    var toPage = window.parent.document.getElementById("property");

    var countRow = document.getElementById("countRow").value;
    var countPage = document.getElementById("countPage").value;
    var currPage = document.getElementById("currPage").value;

    if (isObjNull(countRow))
        countRow = "0";
    if (isObjNull(countPage))
        countPage = "0";
    if (isObjNull(currPage))
        currPage = "0";

    obj_parRows.innerHTML = countRow;
    obj_parCurrPage.innerHTML = currPage;
    obj_parCountPage.innerHTML = countPage;
    obj_CurrPage.value = currPage;

    //控制翻页按钮   
    //12.11 页面操作后 返回回来时 需要重新选择
//    setElementValue(window.parent,"selectlist","#");//清除历史选择记录

    ///测试用，由于数据量大，该段代码注释掉
//    if(!isObjNull(toPage))
//    {
//        for(var k = toPage.options.length - 1;k >=0 ;k--) 
//        {
//            toPage.options.remove(k);
//        }
//        toPage.options.length = 0;
//        
//        if(countPage == 0)
//        {
//             toPage.options[0] = new Option("","");
//        }else
//        {
//            var len = toPage.options.length;
//	        for(var i = 0;i<countPage;i++)
//	        {
//	             toPage.options[len++] = new Option("", "");
//	        }
//         }
//         
//        toPage.selectedIndex = currPage - 1;
//    }
    parForm.firstpage.src = "../images/button/sy3.gif";
    parForm.nextpage.src = "../images/button/xyy3.gif";
    parForm.previouspage.src = "../images/button/syy3.gif";
    parForm.lastpage.src = "../images/button/wy3.gif";
    if (parseInt(currPage) > 1) {
        parForm.firstpage.src = "../images/button/sy2.gif";
        parForm.previouspage.src = "../images/button/syy2.gif";
    }
    if (currPage != countPage && currPage != "0") {
        parForm.nextpage.src = "../images/button/xyy2.gif";
        parForm.lastpage.src = "../images/button/wy2.gif";
    }

}

function refremParent() {
    if ((typeof window.parent.reinitIframe == "function")) {
        window.parent.reinitIframe();
    }

}

function reinitIframe() {
    var frame1 = document.getElementById("frame1");
    try {
        var bHeight = frame1.contentWindow.document.body.scrollHeight;
        var dHeight = frame1.contentWindow.document.documentElement.scrollHeight;
        var height = Math.max(bHeight, dHeight);
        frame1.height = bHeight;
    } catch (ex) {
    }
}

function ChangeTab(selfObj) {
    var tag = document.getElementById("tags").getElementsByTagName("li");
    var taglength = tag.length;
    for (i = 0; i < taglength; i++) {
        tag[i].className = "";
    }
    selfObj.parentNode.className = "selectTag";
}

function showTab(selfObj, theUrl) {
    ChangeTab(selfObj);
    window.frames["TabFrm"].location.href = theUrl;
}

function showFirstTab() {
    document.getElementById("TabMu_1").click();
}
function getBaseInfoContent() {
    var ttt = document.getElementById("showHiddenCommonUserInfo")
    var parent = window.parent.document.getElementById("hiddenCommonUserInfo")
    if (!isObjNull(ttt) && !isObjNull(parent)) {
        ttt.innerHTML = parent.innerHTML;
    }
}

// 说明：获取鼠标位置
// 整理：http://www.codebit.cn
// 来源：http://www.webreference.com

function mousePosition(ev) {
    if (ev.pageX || ev.pageY) {
        return {x: ev.pageX, y: ev.pageY};
    }
    return {
        x: ev.clientX + document.body.scrollLeft - document.body.clientLeft,
        y: ev.clientY + document.body.scrollTop - document.body.clientTop
    };
}
function initParentFrameHeight() {
    var frame1 = window.parent.document.getElementById("frame1");
    var tableHeight = document.getElementById("tttttttttt");
    if (!isObjNull(frame1) && !isObjNull(tableHeight)) {
        //frame1.height = tableHeight.offsetHeight;
        if (tableHeight.offsetHeight > 300) {
            frame1.height = tableHeight.offsetHeight;
        }
        else {
            frame1.height = 300;
        }
    }
}
function validateturnpage() {
    var turntovalue = getElementValue(null, "turntovalue");
    if (isObjNull(turntovalue)) {
        setElementValue(null, "turntovalue", "1");
    }

    var countPage = parseInt(document.getElementById("countpage").innerHTML);
    if (turntovalue > countPage) {
        setElementValue(null, "turntovalue", countPage);
    }

    if (turntovalue == "0") {
        setElementValue(null, "turntovalue", "1");
    }
}
function turnQuery() {
    validateturnpage()
    var indexint = getElementValue(null, "turntovalue");
    document.forms[0].currPage.value = indexint;
    document.forms[0].target = "frame1";
    document.forms[0].submit();
}

function validateperpage() {
    var perPageCount = getElementValue(null, "perPageCount");
    if (isObjNull(perPageCount)) {
        setElementValue(null, "perPageCount", "15");
    }
    if (!isInteger(perPageCount)) {
        setElementValue(null, "perPageCount", "15");
        setFocus(null, "perPageCount");
    }

    validatezero("perPageCount");
}
function validatezero(target) {
    var value = getElementValue(null, target);
    if (value == "0") {
        setElementValue(null, target, "15");
    }
}
//CheckInt()函数内容如下：
function CheckInt() {
    var keyC = event.keyCode - 48;
    if ((keyC >= 0 && keyC <= 9)) {
        if (!isNaN(event.srcElement.value + String.fromCharCode(event.keyCode)))
            return true;
    }
    return false;
}
//全选-全不选
function checkAllOrNo(checked, obj) {
    var obj = document.getElementsByName(obj);
    for (var i = 0; i < obj.length; i++) {
        obj[i].checked = checked;
        selectidinfo(obj[i].value, obj[i].checked)
    }
}

function checkEmail(emailStr) {
    if (emailStr.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1) {
        return true;
    }
    else {
        return false;
    }
}
function checkCheckBoxRequired(name) {
    var flag = true;
    var elements = document.getElementsByName(name);
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].checked) {
            return true;
        }
    }
    return false;
}

function checkLetterOrNumber(s) {
    var regu = "^[0-9a-zA-Z]+$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}

function byId(id) {
    return document.getElementById(id);
}
function checkLetterSpace(s) {
    var regu = "^[ A-Za-z]*$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}

function isLetter(s) {
    var regu = "^[A-Za-z]+/\s/$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}
function isMoney(s) {
    var regu = "^[0-9]+(\.\[0-9]{0,3})$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}


// 验证多选框是否有选择项
function checkBoxNull(obj) {
    var objFlag = false;
    for (var i = 0; i < obj.length; i++) {
        if (obj[i].checked) {
            objFlag = true;
            break;
        }
    }
    return objFlag;
}

//选一个
function checkOne(obj) {
    if (obj.checked) {
        var objForm = document.forms[0];
        var elems = document.getElementsByName(obj.name);
        var objLen = elems.length;
        for (var iCount = 0; iCount < objLen; iCount++) {
            if (elems[iCount].type == "checkbox" && obj.value != elems[iCount].value) {
                elems[iCount].checked = false;
            }
        }
    }
}

//检查国内邮编
function checkChinaPostCode(s) {
    var rs = /\d{6}/;
    if (!rs.test(s))
        return false;
    return true;
}


//校验身份证号码
function checkIdNumber(idCard) {
    var id = idCard;
    var id_length = id.length;

    if (id_length == 0) {
        alert("请输入身份证号码!");
        return false;
    }

    if (id_length != 15 && id_length != 18) {
        alert("身份证号长度应为15位或18位！");
        return false;
    }

    if (id_length == 15) {
        yyyy = "19" + id.substring(6, 8);
        mm = id.substring(8, 10);
        dd = id.substring(10, 12);

        if (mm > 12 || mm <= 0) {
            alert("输入身份证号,月份非法！");
            return false;
        }

        if (dd > 31 || dd <= 0) {
            alert("输入身份证号,日期非法！");
            return false;
        }

        birthday = yyyy + "-" + mm + "-" + dd;

        if ("13579".indexOf(id.substring(14, 15)) != -1) {
            sex = "1";
        } else {
            sex = "2";
        }
    } else if (id_length == 18) {
        if (id.indexOf("X") > 0 && id.indexOf("X") != 17 || id.indexOf("x") > 0 && id.indexOf("x") != 17) {
            alert("身份证中\"X\"输入位置不正确！");
            return false;
        }

        yyyy = id.substring(6, 10);
        if (yyyy > 2200 || yyyy < 1900) {
            alert("输入身份证号,年度非法！");
            return false;
        }

        mm = id.substring(10, 12);
        if (mm > 12 || mm <= 0) {
            alert("输入身份证号,月份非法！");
            return false;
        }

        dd = id.substring(12, 14);
        if (dd > 31 || dd <= 0) {
            alert("输入身份证号,日期非法！");
            return false;
        }

        if (id.charAt(17) == "x" || id.charAt(17) == "X") {
            if ("x" != GetVerifyBit(id) && "X" != GetVerifyBit(id)) {
                alert("身份证校验错误，请检查最后一位！");
                return false;
            }

        } else {
            if (id.charAt(17) != GetVerifyBit(id)) {
                alert("身份证校验错误，请检查最后一位！");
                return false;
            }
        }

        birthday = id.substring(6, 10) + "-" + id.substring(10, 12) + "-" + id.substring(12, 14);
        if ("13579".indexOf(id.substring(16, 17)) > -1) {
            sex = "1";
        } else {
            sex = "2";
        }
    }

    return true;
}
//15位转18位中,计算校验位即最后一位
function GetVerifyBit(id) {
    var result;
    var nNum = eval(id.charAt(0) * 7 + id.charAt(1) * 9 + id.charAt(2) * 10 + id.charAt(3) * 5 + id.charAt(4) * 8 + id.charAt(5) * 4 + id.charAt(6) * 2 + id.charAt(7) * 1 + id.charAt(8) * 6 + id.charAt(9) * 3 + id.charAt(10) * 7 + id.charAt(11) * 9 + id.charAt(12) * 10 + id.charAt(13) * 5 + id.charAt(14) * 8 + id.charAt(15) * 4 + id.charAt(16) * 2);
    nNum = nNum % 11;
    switch (nNum) {
        case 0 :
            result = "1";
            break;
        case 1 :
            result = "0";
            break;
        case 2 :
            result = "X";
            break;
        case 3 :
            result = "9";
            break;
        case 4 :
            result = "8";
            break;
        case 5 :
            result = "7";
            break;
        case 6 :
            result = "6";
            break;
        case 7 :
            result = "5";
            break;
        case 8 :
            result = "4";
            break;
        case 9 :
            result = "3";
            break;
        case 10 :
            result = "2";
            break;
    }
    //document.write(result);
    return result;
}
//15位转18位
function Get18(idCard) {
    if (CheckValue(idCard)) {
        var id = idCard;
        var id18 = id;
        if (id.length == 0) {
            alert("请输入15位身份证号！");
            return false;
        }
        if (id.length == 15) {
            if (id.substring(6, 8) > 20) {
                id18 = id.substring(0, 6) + "19" + id.substring(6, 15);
            } else {
                id18 = id.substring(0, 6) + "20" + id.substring(6, 15);
            }

            id18 = id18 + GetVerifyBit(id18);
        }
        return id18;
    } else {
        return false;
    }
}

function getAge(idCard) {
    var brith;
    if (idCard.length == 15) {
        brith = "19" + idCard.substring(6, 8) + "-" + idCard.substring(8, 10) + "-" + idCard.substring(10, 12);
    }
    if (idCard.length == 18) {
        brith = idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
    }
    var birthday = new Date(brith.replace(/-/g, "\/"));
    var d = new Date();
    var age = d.getFullYear() - birthday.getFullYear() - ((d.getMonth() < birthday.getMonth() || d.getMonth() == birthday.getMonth() && d.getDate() < birthday.getDate()) ? 1 : 0);
    return age;
}

function daysBetween(strDateStart, strDateEnd) {
    var strSeparator = "-"; //日期分隔符
    var strDateArrayStart;
    var strDateArrayEnd;
    var intDay;
    strDateArrayStart = strDateStart.split(strSeparator);
    strDateArrayEnd = strDateEnd.split(strSeparator);
    var strDateS = new Date(strDateArrayStart[0] + "/" + strDateArrayStart[1] + "/" + strDateArrayStart[2]);
    var strDateE = new Date(strDateArrayEnd[0] + "/" + strDateArrayEnd[1] + "/" + strDateArrayEnd[2]);
    intDay = (strDateE - strDateS) / (1000 * 3600 * 24);
    return intDay;
}

// 虚拟表单提交
function post(URL, PARAMS) {
    var temp = $("<form>");
    temp.attr("action", URL);
    temp.attr("method", "post");
    temp.css("display", "none");
    for (var x in PARAMS) {
        var opt = $("<textarea>");
        opt.attr("name", x);
        opt.text(PARAMS[x]);
        opt.appendTo(temp);
    }
    temp.appendTo($("body"));
    temp.submit();
    return temp;
}

// dimnode格式化
function formatDim(key, value) {
    var html = "";
    if (isObjNull(key) || isObjNull(value)) {
        return html;
    }
    return dimDatas.get(key, value);
}


// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

// 左则菜单, 使之变为激活状态
$(function(){
    var url= window.location.href;

    $(".nav-list li a").each(function(index, element){
        var href = $(this).attr("href");
        if(href == "/" || href == "#" || href=="") return;
        if(url.indexOf(href) > 0){
            $(this).parent().addClass("active");
            $(this).parent().parents("li").addClass("active").addClass("open");
        }
    });
});

/*
 * 格式化成金额
 *
 * @param num 数值(Number或者String)
 * @param pos 数值(保留小数位数)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatCurrency(num,pos) {
	num = parseFloat(num).toFixed(pos);
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
    cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}
/**
 * 查询标的余额和企业余额
 * @param projectId
 * @param companyType
 * @returns {String}
 */
function queryBalance(projectId,companyType){
	var msg = "";
	$.ajax({
		url:"/queryBalance.html",
		type:"post",
		data:{projectId:projectId,companyType:companyType},
		async:false,
		success:function(data){
			msg = data;
		}
	});
	return msg;
	
}