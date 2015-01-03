function middleOpen(myUrl,myName,myStyle){

	return window.open(myUrl,myName,myStyle);
}

	function formatFloat(value)
	   {
		//alert(value);
	    digits = 2;
	    var baseValue = Math.pow(10,2);
	    value = Math.round(parseFloat(value)*baseValue)/baseValue;
	    
	    value = value.toString();
	    digits = digits - value.substr(value.toString().indexOf(".")+1).length;
	    
	    if(value.indexOf(".")<0 || digits >0 )
	    {
	     if(value.indexOf(".")<0) value += ".";
	     for(var i=0;i<2;i++)
		  value = value.toString();
	      value += "0";
	    }
	    
	    return value;
   }

function getValidStr(str) {
	str+="";
	if (str=="undefined" || str=="null")
		return "";
	else
		return str;
}


function trim(str){
	str=getValidStr(str);
	if (!str) return "";
	for(var i=str.length-1; i>=0; i--){
		if (str.charCodeAt(i, 10)!=32) break;
	}
	for (var j=0; j <= str.length - 1; j++) {
		if (str.charCodeAt(j) != 32) {
			break;
		}
	}
	return str.substr(j, i+1-j);
}



function drawToday(){
	var today = new Date();
	var todayStr = today.getYear()+"-"+(today.getMonth()+1)+"-"+today.getDate();
	document.write(todayStr);
}
function isNumber(obj){
	return !isNaN(parseFloat(obj.value));
}

function formatnumber(value,num) //ֱ��ȥβ
{
	var a,b,c,i
	a = value.toString();
	b = a.indexOf('.');
	c = a.length;
	if (num==0)
	{
		if (b!=-1) a = a.substring(0,b);
	}
	else
	{
		if (b==-1)
		{
			a = a + ".";
			for (i=1;i<=num;i++)
			a = a + "0";
		}
		else
		{
			a = a.substring(0,b+num+1);
			for (i=c;i<=b+num;i++)
			a = a + "0";
		}
	}
	return a
}


function formatStringToDate(str){
	var strs = str.split("-");
	var year = parseInt(strs[0]);
	var month = parseInt(strs[1]);
	var day = parseInt(strs[2]);
	var date = new Date(year,month,day);
	return date;
}
//================================================


//
function arrayContains(objArray,objValue){
	var booRet=false;
	for(var i=0;i<objArray.length;i++){
	    if(objArray[i]==objValue){
		booRet=true;
		break;
	    }
	}
	return booRet;
}

function stringToArray(initData,segment){
	var orgs = new Array(1);
	if( initData!=''&&initData.length>0 ){	
		var alSize=initData.length;
		if(alSize>segment){
			var quotient=Math.floor(alSize/segment);	
			var residue=alSize%segment;
			if(residue!=0) quotient++;		//�ֳ��Ķ���
			//alert("quotient="+quotient);
			//alert("residue="+residue);
			var strTemp=new Array(quotient);
			for(var i=0;i<quotient;i++){
				if(i!=quotient-1){
					strTemp[i]=initData.substring(i*segment,(i+1)*segment);
				}else{
					strTemp[i]=initData.substring(segment*i,alSize);		//���һ��
				}
			}
			orgs=strTemp;
		}else{
			orgs[0]=initData;
		}
		
	}
	return orgs;
}

function cleanHiddenValue(hiddenElementName){
	var hiddenElmt = eval("document.forms[0]."+hiddenElementName);
	if(hiddenElmt != null){
		hiddenElmt.value = "";
	}else{
		alert(hiddenElementName);
	}
}


function replaceAll0(objVal,regex,replacement){
	
	while(objVal.indexOf(regex)!=-1){
		objVal=objVal.replace(regex,replacement);
	}
	return objVal;
}

function replaceAll(objVal,regex,replacement){
	var spliter = unescape('%02');

	while(objVal.indexOf(regex)!=-1){
		objVal=objVal.replace(regex,spliter);
	}
	while(objVal.indexOf(spliter)!=-1){
		objVal=objVal.replace(spliter,replacement);
	}
	return objVal;
}

function showdiv(event,dscpid)
{

  var el, x, y;

  el = document.getElementById(dscpid);
  if (window.event) {
    x = window.event.clientX + document.documentElement.scrollLeft
                             + document.body.scrollLeft;
    y = window.event.clientY + document.documentElement.scrollTop +
                             + document.body.scrollTop;
  }
  else {
    x = event.clientX + window.scrollX;
    y = event.clientY + window.scrollY;
  }
  x += 12; y += 12;
  el.style.left = x + "px";
  el.style.top  = y + "px";
  el.style.display = "";
}
function movediv(event,dscpid)
{
  var el, x, y;

  el = document.getElementById(dscpid);
  if (window.event) {
    x = window.event.clientX + document.documentElement.scrollLeft
                             + document.body.scrollLeft;
    y = window.event.clientY + document.documentElement.scrollTop +
                             + document.body.scrollTop;
  }
  else {
    x = event.clientX + window.scrollX;
    y = event.clientY + window.scrollY;
  }
  x += 12; y += 12;
  el.style.left = x + "px";
  el.style.top  = y + "px";
}
function hiddendiv(event,dscpid)
{
  var e1
  el = document.getElementById(dscpid);
  el.style.left = "0px";
  el.style.top  = "0px";
  el.style.display = "none";
}

function checkNowTime()
{
//	var nowDate = new Date();
//	var intTime = nowDate.getHours();
//	if(intTime>=6&&intTime<=21){
//		alert("���������Ż�,�����ٲ�ѯ������");
//		return false;
//	}
//	return true;
	return true;
}


function close_child_win(){ 
	//if(child) child.close();
}


function chk_page_num(num,oThis){
  var flag=true;
//  var intNumPerPage=10,intGoto=1;
//  if (document.all.txtNumPerPage.length == null) {
//      intNumPerPage=document.all.txtNumPerPage.value;
//  }
//  else {
//      for (var i=0; i<document.all.txtNumPerPage.length;i++){
//          if(oThis == document.all.buttonGo[i]){
//              intNumPerPage=document.all.txtNumPerPage[i].value;
//              break;
//          }
//      }
//  }
//  if (document.all.txtGoto.length == null) {
//      intGoto=document.all.txtGoto.value;
//  }
//  else {
//      for (var i=0; i<document.all.txtGoto.length;i++){
//          if(oThis == document.all.buttonGo[i]){
//              intGoto=document.all.txtGoto[i].value;
//              break;
//          }
//      }
//  }
//  if(isNaN(intNumPerPage)||(intNumPerPage<1)||intNumPerPage.indexOf('.')!=-1){
//	 alert("�������˲��Ϸ���ÿҳ��¼��");
//     flag=false;  }else if(intNumPerPage>num){
//	 alert("ÿҳ��¼���𳬹�"+num);
//     flag=false;  }
//  if(isNaN(intGoto)||(intGoto<1)||intGoto.indexOf('.')!=-1){
//	alert("�������˲��Ϸ��ĵ�ǰҳ��");
//    flag=false;  }
//  if (flag){
//      document.all.hiddenGoto.value = intGoto;
//   }
   return flag;
}


function getParentByTagNameTimes(oElement,sTagName,times){
  var oParent=oElement;
  var count = 0;
  while (oParent!=null) {
    if (oParent.tagName.toLowerCase()==sTagName.toLowerCase()) {
      if (oParent.readyState=="complete"){
	    if(count < times-1){
			count++;
		}else{
			return oParent;
			break;
		}
	  }
    }
    oParent=oParent.parentElement;
  }
  return null;
}

function getParentByTagName(oElement,sTagName){
  var oParent=oElement;
  while (oParent!=null) {
    if (oParent.tagName.toLowerCase()==sTagName.toLowerCase()) {
      if (oParent.readyState=="complete") 
		return oParent;
      break;
    }
    oParent=oParent.parentElement;
  }
  return null;
}
function getChildByTagNameTimes(oElement,sTagName,times){
  var oChild=oElement;
  var count = 0;
  /*for(var i=0;i<oChild.all.length;i++){
	if(oChild.all[i].tagName.toLowerCase()==sTagName.toLowerCase()){
		if(count < times-1){
			count++;
		}else{
			return oChild.all[i];
			break;
		}
	}
  }*/
  return null;
}
function getChildByIdTimes(oElement,sId,times){
  var oChild=oElement;
  var count = 0;
  for(i=0;i<oChild.all.length;i++){
	if(oChild.all[i].id.toLowerCase()==sId.toLowerCase()){
		if(count < times-1){
			count++;
		}else{
			return oChild.all[i];
			break;
		}
	}
  }
  return null;
}


function setTitle(title){
		window.parent.title1.document.getElementById("titleName").innerHTML="<font size=5><strong>&nbsp;&nbsp;"+title+"</strong></font>";
}