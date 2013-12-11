<%@ page contentType="text/html;charset=GBK"%>



<SCRIPT language="javascript" src="js/checkValid.js"></SCRIPT>
<SCRIPT language=JAVASCRIPT1.2>

function getValueLenCheckInfo(fieldName,len){
	var endvalue=0;
  	var sourcestr=new String(fieldName.value);
  	var tempstr;
  	for (var strposition = 0; strposition < sourcestr.length; strposition ++) {
    	tempstr=sourcestr.charAt(strposition);
    	if (tempstr.charCodeAt(0)>255 || tempstr.charCodeAt(0)<0) {
      		endvalue=endvalue+2;
    	}else{
    		endvalue=endvalue+1;
    	}
  	}
  	var returnStr = "";
  	if(endvalue > len){
  		var myCountChina = parseInt((endvalue - len)/2);
  		var myCountEnglish = endvalue - len;
  		returnStr = "<font size=\"2\" color=\"red\"><B>(超出 "+myCountChina+" 个汉字或 "+myCountEnglish+" 个英文)</B></font>";
  	}else{
  		var myCountChina = parseInt((len - endvalue)/2);
  		var myCountEnglish = len - endvalue;
  		returnStr = "<font size=\"2\" color=\"blue\"><B>(剩余 "+myCountChina+" 个汉字或 "+myCountEnglish+" 个英文)</B></font>";  	
  	}
  	return returnStr;
}

function showCountDiv(myObject,len){
	myValueCountCell.innerHTML = getValueLenCheckInfo(myObject,len); 
	myCountSetoffsets(myObject);
	myValueCountDIV.style.visibility="visible";
}

function hideCountDiv(myObject,len){ 
	if(f_overLength(myObject,len)){
		alert("内容输入过长,字数不能超过"+(len/2)+"个汉字或"+len+"个英文！");
		myObject.focus();
		myObject.select();		
	}else{
		myValueCountDIV.style.visibility="hidden";
	}	
}

function myCountSetoffsets(inputField){
   //var end=inputField.offsetWidth;
   var left=myCountCalculateOffsetLeft(inputField);
   var top=myCountCalculateOffsetTop(inputField)+inputField.offsetHeight;
   myValueCountDIV.style.border="black 1px solid";
   myValueCountDIV.style.left=left+"px";
   myValueCountDIV.style.top=top+"px";
   //myValueCountDIV.style.width=end+"px";
}

function myCountCalculateOffsetLeft(field){
   return myCountCalculateOffset(field,"offsetLeft");
}

function myCountCalculateOffsetTop(field){
   return myCountCalculateOffset(field,"offsetTop");
}

function myCountCalculateOffset(field,attr){
   var offset=0;
   while(field){
	   offset +=field[attr];
	   field=field.offsetParent;
   }
   return offset;
}

</SCRIPT>

<div id="myValueCountDIV" style="position:absolute; z-index:101; visibility:hidden;text-align:center">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr class="tableback1">
			<td ID="myValueCountCell"></td>
		</tr>
	</table>
</div>