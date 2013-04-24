
//异常处理
function processException(e){
	switch (typeof(e)){
		case "string":{
			if (e!="abort"){
				if (e)
					alert(e);
				else
					alert(constErrUnknown);
			}
			break;
		}

		case "object":{
			alert(e.description+"\n"+constErrType+":"+(e.number & 0xFFFF));
			break;
		}
	}
}

//去掉两端空格

function trimStr(str){
	str=getValidStr(str);
	if (!str) return "";
	for(var i=str.length-1; i>=0; i--){
		if (str.charCodeAt(i, 10)!=32) break;
	}
	// 修正只去除后面空格的BUG
	for (var j=0; j <= str.length - 1; j++) {
		if (str.charCodeAt(j) != 32) {
			break;
		}
	}
	return str.substr(j, i+1-j);
}

//是否为有效字符""
function getValidStr(str) {
	str+="";
	if (str=="undefined" || str=="null")
		return "";
	else
		return str;
}

//编码
function encode(strIn)
{
	var intLen=strIn.length;
	var strOut="";
	var strTemp;

	for(var i=0; i<intLen; i++)
	{
		strTemp=strIn.charCodeAt(i);
		if (strTemp>255)
		{
			tmp = strTemp.toString(16);
			for(var j=tmp.length; j<4; j++) tmp = "0"+tmp;
			strOut = strOut+"^"+tmp;
		}
		else
		{
			if (strTemp < 48 || (strTemp > 57 && strTemp < 65) || (strTemp > 90 && strTemp < 97) || strTemp > 122)
			{				
				tmp = strTemp.toString(16);
				for(var j=tmp.length; j<2; j++) tmp = "0"+tmp;
				strOut = strOut+"~"+tmp;
			}
			else
			{
				strOut=strOut+strIn.charAt(i);
			}
		}
	}
	return (strOut);
}

//解码
function decode(strIn)
{
	var intLen = strIn.length;
	var strOut = "";
	var strTemp;

	for(var i=0; i<intLen; i++)
	{
		strTemp = strIn.charAt(i);
		switch (strTemp)
		{
			case "~":{
				strTemp = strIn.substring(i+1, i+3);
				strTemp = parseInt(strTemp, 16);
				strTemp = String.fromCharCode(strTemp);
				strOut = strOut+strTemp;
				i += 2;
				break;
			}
			case "^":{
				strTemp = strIn.substring(i+1, i+5);
				strTemp = parseInt(strTemp,16);				
				strTemp = String.fromCharCode(strTemp);
				strOut = strOut+strTemp;
				i += 4;
				break;
			}
			default:{
				strOut = strOut+strTemp;
				break;
			}
		}

	}
	return (strOut);
}

//效验并编码
function getEncodeStr(str) {
	return encode(getValidStr(str));
}

//效验并解码
function getDecodeStr(str) {
	return ((str)?decode(getValidStr(str)):"");
}

//比较两个文本是否相等(不区分大小写)，返回true或false
function compareText(str1, str2){
	str1=getValidStr(str1);
	str2=getValidStr(str2);
	if (str1==str2) return true;
	if (str1=="" || str2=="") return false;
	return (str1.toLowerCase()==str2.toLowerCase());
}

//
function isTrue(value){
	return (value==true || (typeof(value)=="number" && value!=0) ||
		compareText(value, "true") || compareText(value, "T") ||
		compareText(value, "yes") || compareText(value, "on"));
}

function getStringValue(value){
	if (typeof(value)=="string" || typeof(value)=="object")
		return "\""+getValidStr(value)+"\"";
	else if (typeof(value)=="date")
		return "\""+(new Date(value))+"\"";
	else if (getValidStr(value)=="")
		return "\"\"";
	else
		return value;
}

function getInt(value){
	var result=parseInt(value);
	if (isNaN(result)) result=0;
	return result;
}

function getFloat(value){
	var result=parseFloat(value);
	if (isNaN(result)) result=0;
	return result;
}

function formatFloat(value, decimalLength){
	var text=getValidStr(Math.round(getFloat(value)*Math.pow(10, decimalLength)));
	var len=text.length;
	return text.substr(0, len-decimalLength)+"."+text.substr(len-decimalLength, decimalLength);
}

function formatDateTime(date, mode,separator){
	
	function getDateString(date){
		var years=date.getFullYear();
		var months=date.getMonth()+1;
		var days=date.getDate();
		
		if (months<10) months="0"+months;
		if (days<10) days="0"+days;
		if (separator == null) {
			return years+ "-" + months+"-"+days;
		}
		else {
			return years+ separator + months+separator+days;
		}
	}
	
	function getTimeString(date){
		var hours=date.getHours();
		var minutes=date.getMinutes();
		var seconds=date.getSeconds();
		
		if (hours<10) hours="0"+hours;
		if (minutes<10) minutes="0"+minutes;
		if (seconds<10) seconds="0"+seconds;
		
		return hours+":"+minutes+":"+seconds;
	}
	
	if (typeof(date)=="object" && !isNaN(date)){
		if (!mode) mode="datetime";
		switch (mode){
			case "date":{
				return getDateString(date);
				break;
			}
			case "time":{
				return getTimeString(date);
				break;
			}
			case "datetime":{
				return getDateString(date)+" "+getTimeString(date);
				break;
			}
			default:{
				return getDateString(date)+" "+getTimeString(date);
				break;
			}
		}
	}
	else
		return "";
}

function getTypedValue(value, dataType){
	var result="";
	switch (dataType){
		case "float":{
			result=parseFloat(value);
			break;
		}
		case "int":{
			result=Math.round(parseFloat(value));
			break;
		}
		case "date":;
		case "datetime":;
		case "time":{
			result=new Date(value);
			break;
		}
		case "bool":{
			result=isTrue(value);
			break;
		}
		default:{
			result=getValidStr(value);
			break;
		}
	}
	return result;
}

function pArray(){
	this.firstUnit=null;
	this.lastUnit=null;
	this.length=0;
}

function pArray_clear(pArray){
	var unit=pArray.firstUnit;
	var _unit;
	while (unit){
		_unit=unit;
		unit=unit.nextUnit;
		if (_unit.data) delete _unit.data;
		delete _unit;
	}
	pArray.firstUnit=null;
	pArray.lastUnit=null;
	pArray.length=0;
}

/**
 * 增加记录
 */
function pArray_insert(pArray, mode, unit, newUnit){
	var u1, u2;
	switch (mode){
		case "begin":{
			u1 = null;
			u2 = pArray.firstUnit;
			break;
		}
		case "before":{
			u1 = (unit)?unit.prevUnit:null;
			u2 = unit;
			break;
		}
		case "after":{
			u1 = unit;
			u2 = (unit)?unit.nextUnit:null;
			break;
		}
		default:{
			u1 = pArray.lastUnit;
			u2 = null;
			break;
		}
	}

	newUnit.prevUnit=u1;
	newUnit.nextUnit=u2;
	if (u1) u1.nextUnit = newUnit; else pArray.firstUnit=newUnit;
	if (u2) u2.prevUnit = newUnit; else pArray.lastUnit=newUnit;
	pArray.length++;
}

function pArray_insertArray(pArray, mode, unit, subArray){
	if (!subArray || !subArray.firstUnit) return;

	var u1, u2;
	switch (mode){
		case "begin":{
			u1=null;
			u2=pArray.firstUnit;
			break;
		}
		case "before":{
			u1=(unit)?unit.prevUnit:null;
			u2=unit;
			break;
		}
		case "after":{
			u1=unit;
			u2=(unit)?unit.nextUnit:null;
			break;
		}
		default:{
			u1=pArray.lastUnit;
			u2=null;
			break;
		}
	}

	subArray.firstUnit.prevUnit=u1;
	subArray.lastUnit.nextUnit=u2;
	if (u1) u1.nextUnit=subArray.firstUnit; else pArray.firstUnit=subArray.firstUnit;
	if (u2) u2.prevUnit=subArray.lastUnit; else pArray.lastUnit=subArray.lastUnit;
	pArray.length+=subArray.length;
}

function pArray_delete(pArray, unit){
	var u1, u2;
	u1=unit.prevUnit;
	u2=unit.nextUnit;
	if (u1) u1.nextUnit=u2; else pArray.firstUnit=u2;
	if (u2) u2.prevUnit=u1; else pArray.lastUnit=u1;
	delete unit;
	pArray.length--;
}

function pArray_ex_insert(pArray, data){
	var found=false;
	var _unit=pArray.firstUnit;
	while (_unit){
		if (_unit.data==data){
			found=true;
			break;
		}
		_unit=_unit.nextUnit;
	}

	var newUnit=new Object();
	newUnit.data=data;
	if (!found) pArray_insert(pArray, "end", null, newUnit);
}

function pArray_ex_delete(pArray, data){
	var _unit=pArray.firstUnit;
	while (_unit){
		if (_unit.data==data){
			pArray_delete(pArray, _unit);
			break;
		}
		_unit=_unit.nextUnit;
	}
}
var _client_property_sql=null;
function setClientProperty(name, value){
//	Response.Write("<INPUT TYPE=hidden id=\"_client_property_"+name+"\" value=\""+getEncodeStr(value)+"\">\n");
	_client_property_sql=document.createElement("<INPUT TYPE=hidden >");
	_client_property_sql.id="_client_property_"+name;
//	_client_property_sql.value=getEncodeStr(value);
	_client_property_sql.value=value;
//id=\"_client_property_"+name+"\" value=\""+getEncodeStr(value)+"\"
	document.body.appendChild(_client_property_sql);
}

function getClientProperty(name){
	var value;
	eval("value=getDecodeStr(_client_property_"+name+".value);");
	return value;
}

// Cookie 操作类

// The constructor function: creates a cookie object for the specified
// document, with a specified name and optional attributes.
// Arguments:
//   document: The Document object that the cookie is stored for. Required.
//   name:     A string that specifies a name for the cookie. Required.
//   hours:    An optional number that specifies the number of hours from now
//             that the cookie should expire.
//   path:     An optional string that specifies the cookie path attribute.
//   domain:   An optional string that specifies the cookie domain attribute.
//   secure:   An optional Boolean value that, if true, requests a secure cookie.
//
function Cookie(document, name, hours, path, domain, secure)
{
    // All the predefined properties of this object begin with '$'
    // to distinguish them from other properties which are the values to
    // be stored in the cookie.
    this.$document = document;
    this.$name = name;
    if (hours)
        this.$expiration = new Date((new Date()).getTime() + hours*3600000);
    else this.$expiration = null;
    if (path) this.$path = path; else this.$path = null;
    if (domain) this.$domain = domain; else this.$domain = null;
    if (secure) this.$secure = true; else this.$secure = false;
}

// This function is the store() method of the Cookie object.
Cookie.prototype.store = function () {
    // First, loop through the properties of the Cookie object and
    // put together the value of the cookie. Since cookies use the
    // equals sign and semicolons as separators, we'll use colons
    // and ampersands for the individual state variables we store 
    // within a single cookie value. Note that we escape the value
    // of each state variable, in case it contains punctuation or other
    // illegal characters.
    var cookieval = "";
    for(var prop in this) {
        // Ignore properties with names that begin with '$' and also methods.
        if ((prop.charAt(0) == '$') || ((typeof this[prop]) == 'function')) 
            continue;
        if (cookieval != "") cookieval += '&';
        cookieval += prop + ':' + escape(this[prop]);
    }

    // Now that we have the value of the cookie, put together the 
    // complete cookie string, which includes the name and the various
    // attributes specified when the Cookie object was created.
    var cookie = this.$name + '=' + cookieval;
    if (this.$expiration)
        cookie += '; expires=' + this.$expiration.toGMTString();
    if (this.$path) cookie += '; path=' + this.$path;
    if (this.$domain) cookie += '; domain=' + this.$domain;
    if (this.$secure) cookie += '; secure';

    // Now store the cookie by setting the magic Document.cookie property.
    this.$document.cookie = cookie;
}

// This function is the load() method of the Cookie object.
Cookie.prototype.load = function() { 
    // First, get a list of all cookies that pertain to this document.
    // We do this by reading the magic Document.cookie property.
    var allcookies = this.$document.cookie;
    if (allcookies == "") return false;

    // Now extract just the named cookie from that list.
    var start = allcookies.indexOf(this.$name + '=');
    if (start == -1) return false;   // Cookie not defined for this page.
    start += this.$name.length + 1;  // Skip name and equals sign.
    var end = allcookies.indexOf(';', start);
    if (end == -1) end = allcookies.length;
    var cookieval = allcookies.substring(start, end);

    // Now that we've extracted the value of the named cookie, we've
    // got to break that value down into individual state variable 
    // names and values. The name/value pairs are separated from each
    // other by ampersands, and the individual names and values are
    // separated from each other by colons. We use the split method
    // to parse everything.
    var a = cookieval.split('&');    // Break it into array of name/value pairs.
    for(var i=0; i < a.length; i++)  // Break each pair into an array.
        a[i] = a[i].split(':');

    // Now that we've parsed the cookie value, set all the names and values
    // of the state variables in this Cookie object. Note that we unescape()
    // the property value, because we called escape() when we stored it.
    for(var i = 0; i < a.length; i++) {
        this[a[i][0]] = unescape(a[i][1]);
    }

    // We're done, so return the success code.
    return true;
}

// This function is the remove() method of the Cookie object.
Cookie.prototype.remove = function() {
    var cookie;
    cookie = this.$name + '=';
    if (this.$path) cookie += '; path=' + this.$path;
    if (this.$domain) cookie += '; domain=' + this.$domain;
    cookie += '; expires=Fri, 02-Jan-1970 00:00:00 GMT';

    this.$document.cookie = cookie;
}
