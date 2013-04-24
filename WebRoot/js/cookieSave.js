
function getArray(){
	//var len=document.all.tags("tr").length;
	var len=document.forms[0].elements.length;
	var theNames = new Array();
	var theValues = new Array();
	for(var i=0;i<len;i++){
	    var theType=document.forms[0].elements[i].type;
	    if(theType=='text'||theType=='select-one'||theType=='textarea'){
		theNames[i]=document.forms[0].elements[i].name;
		theValues[i]=document.forms[0].elements[i].value;
	    }
	}
	var theAll=new Array(theNames,theValues);
	return theAll;
}

function saveAll(day){
	//if(day==null||day=='') day=1;
	var theAll=getArray();
	var theNames = theAll[0];
	var theValues = theAll[1];
	for(var i=0;i<theNames.length;i++){
	    if(theValues[i]!=null&&theNames[i]!=''){
			saveOne(theNames[i],theValues[i],day);
	    }
	}
}

function saveOne(objName,objValue,day){
	//if(day==null||day=='') day=1;
	if(objValue!=null&&objValue!=''){
		setCookie(objName,objValue,day);
	}
	
}


function saveOne2(objName,objValue,day){
	var oldCookieValue=getCookie("chk");
	var newValue=objValue;
	if(oldCookieValue!=null&&oldCookieValue!=""){
		newValue=oldCookieValue;
		var tempVal1=oldCookieValue.split(",");
		var tempVal2=objValue.split(",");
		for(var i=0;i<tempVal2.length;i++){
			if(!arrayContains(tempVal1,tempVal2[i])){
				newValue+=","+tempVal2[i];
			}
		}
	}
	if(objValue!=null&&objValue!=''){
		setCookie(objName,newValue,day);
	}
	
}

function resetCookie(){
	document.forms[0].reset();
	saveAll();
}

function setCookie (name, value , day){
  var argv = setCookie.arguments;
  var argc = setCookie.arguments.length;
  var expires = (argc > 2) ? argv[2] : null;

  if(expires!=''&&expires!=null){
  	var today = new Date();
  	var expiry = new Date(today.getTime() + day * 24 * 60 * 60 * 1000);//��Cookie���� day ��
 	expires=expiry;
  }
  var path = (argc > 3) ? argv[3] : null;
  var domain = (argc > 4) ? argv[4] : null;
  var secure = (argc > 5) ? argv[5] : false;

  document.cookie = name + "=" + escape (value) +
    ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
    ((path == null) ? "" : ("; path=" + path)) +
    ((domain == null) ? "" : ("; domain=" + domain)) +
    ((secure == true) ? "; secure" : "");
    
}
  
function getCookie (name) {
  var arg = name + "=";
  var alen = arg.length;
  var clen = document.cookie.length;
  var i = 0;
  while (i < clen) {
    var j = i + alen;
    if (document.cookie.substring(i, j) == arg)
      return getCookieVal (j);
    i = document.cookie.indexOf(" ", i) + 1;
    if (i == 0) break; 
  }
  return null;
}
  
function getCookieVal (offset){
  var endstr = document.cookie.indexOf (";", offset);
  if (endstr == -1)
    endstr = document.cookie.length;
  return unescape(document.cookie.substring(offset, endstr));
}

function getValue(){
	var theAll=getArray();
	var theNames = theAll[0];
	for(var i=0;i<theNames.length;i++){
		var temp=getCookie(theNames[i]);
		if(temp!=null) document.forms[0].elements[i].value=temp;
		
	}
	
}

function deleteAllAndReset(){
	deleteAll();
	window.location.reload();
}


function deleteAll(){
	var theAll=getArray();
	var theNames = theAll[0];
	var theValues = theAll[1];
	for(var i=0;i<theNames.length;i++){
	    if(theValues[i]!=null&&theNames[i]!=''){
			delCookie(theNames[i],theValues[i]);
	    }
	}
}

function delCookie(sName,sValue)
{
  document.cookie = sName + "=" + escape(sValue) + "; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
}

//Add by Hao ������Form�������1��Cookie����Ϊ����ͬһ����վ��˵������ܱ���20��Cookie�����˾��Զ����ǵ���,����Ҫ��ԼCookies��Դ

function saveAllToOne(name,day){//name ΪCookies�����֣�dayΪ��������������dayΪnull���ʾCookie����Ч��Ϊ��������̣����ر��������ʧЧ
	var len=document.forms[0].elements.length;
	var tempStr = "";
	var spliter1 = unescape('%01');//�ָ���
	var spliter2 = unescape('%02');//�滻��ֵ
	var tempNum = 0;
	for(var i=0;i<len;i++){
	    var theType=document.forms[0].elements[i].type;
	    if(theType=='text'||theType=='select-one'||theType=='textarea'||theType=='radio'|| theType=='hidden'){
	    	if(tempNum == 0){
	    		if(document.forms[0].elements[i].value == ""){
	    			tempStr = tempStr + document.forms[0].elements[i].name + spliter1 + spliter2;
	    		}else{
	    			if(theType == 'radio'){//�����radio���͵ģ������Ѿ�ѡ�����ˣ��ű���
	    				if(document.forms[0].elements[i].checked){
	    					tempStr = tempStr + document.forms[0].elements[i].name + spliter1 + document.forms[0].elements[i].value;
	    				}
	    			}else{
	    				tempStr = tempStr + document.forms[0].elements[i].name + spliter1 + document.forms[0].elements[i].value;
	    			}
	    		}
	    		tempNum = 1;	
	    	}else{
	    		if(document.forms[0].elements[i].value == ""){
	    			tempStr = tempStr + spliter1 + document.forms[0].elements[i].name + spliter1 + spliter2;
	    		}else{
	    			if(theType == 'radio'){//�����radio���͵ģ������Ѿ�ѡ�����ˣ��ű���
	    				if(document.forms[0].elements[i].checked){
	    					tempStr = tempStr + spliter1 + document.forms[0].elements[i].name + spliter1 + document.forms[0].elements[i].value;
	    				}
	    			}else{
	    				tempStr = tempStr + spliter1 + document.forms[0].elements[i].name + spliter1 + document.forms[0].elements[i].value;
	    			}
	    		}
	    	}
	    }
	}
	setCookie(name,tempStr,day);
}

function getAllFromOne(name){
	var spliter1 = unescape('%01');//�ָ���
	var spliter2 = unescape('%02');//�滻��ֵ
	var tempStr = getCookie(name);
	if(tempStr!=null){
		var strs = tempStr.split(spliter1);
		var len = strs.length/2;
		for(var i=0;i<len;i++){
			if(strs[i*2+1] != spliter2){
				var tempObject = document.getElementsByName(strs[i*2]);
				if(tempObject[0].type != "radio"){
					tempObject[0].value = strs[i*2+1];
				}else{
					for(var j=0;j<tempObject.length;j++){
						if(tempObject[j].value == strs[i*2+1]){
							tempObject[j].checked = true;
						}
					}
				}
			}
		}
	}
}

function deleteAllFromOne(name){
	delCookie(name,"");
}