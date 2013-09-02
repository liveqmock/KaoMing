//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ' " \ < >
//**     �����true�����������ַ���false���������ַ�
//**     ˵����fieldNameΪҪ�����ı������
//******************************************************************
function f_verifyString(fieldName){
	var flag=true;
  	var sourcestr=new String(fieldName.value);
  	var tempstr;
  	for (var strposition = 0; strposition < sourcestr.length; strposition ++) {
    	if (sourcestr.charAt(strposition)=='\'' || sourcestr.charAt(strposition)=='\"' || sourcestr.charAt(strposition)=='\\' || sourcestr.charAt(strposition)=='<' || sourcestr.charAt(strposition)=='>') {
      		flag = false;
      		break;
    	}
  	}
  	return flag;
}

//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ����email�ĸ�ʽ
//**     �����true���ϣ�false������
//**     ˵����fieldNameΪҪ�����ı������
//******************************************************************
function verifyEmail(fieldName)
{
	var flag=true;
	var email = fieldName.value;
	if(email!=null&&email!=''){
	����var pattern = /^([.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	����flag = pattern.test(email);
		if(!flag){
			alert("email��ʽ�Ƿ�");
		}
	}
����return flag;
}


//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ񳬳���ָ���ĳ��ȣ������˺��ֵ��жϣ�һ��������2�����ȣ�һ����ͨ�ַ���1������
//**     �����true������ָ���ĳ��ȣ�falseû�г���ָ���ĳ���
//**     ˵����fieldNameΪҪ�����ı������lenΪ�޶�����󳤶ȣ���������len�򷵻�true
//******************************************************************
function f_overLength(fieldName,len){
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
  	if(endvalue >len){
  		return true;
  	}else{
  		return false;
  	}
}


//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ�գ��ж��˿ն��󣬳���Ϊ�㣬Ϊȫ�ո�Ҳ��Ϊ�գ�
//**     �����true��ʾΪ�գ�false��ʾ��Ϊ��
//**     ˵��������Ϊ��Ӧ���ı������
//******************************************************************
function f_trimIsNull(fieldName){
	var str = fieldName.value;
  	if(str==null){
  		return true;
  	}
  	if(str.length==0){
  		return true;
  	}
  	var i=0,j=str.length-1,c;
  	for(;i<str.length;i++){
  	  	c=str.charAt(i);
  	  	if((c!=' ') && (c!=' ')){
  	  		return false;
  	  	}
  	  	fieldName.value ="";
		return true;
  	}
}

//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��������
//**     �����
//**     ˵����
//******************************************************************
function f_isNumFormat(fieldName,fieldDesc){	
	var flag = true;
	var etext=fieldName.value;
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa<'0'||aa>'9'){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  alert(fieldDesc+"  ���������֣�");//���������֣�
	  fieldName.focus();	  
    }				
	return flag;
}

//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��������
//**     �����
//**     ˵����
//******************************************************************
function f_isNumFormat2(fieldName){	
	var flag = true;
	var etext=fieldName.value;
	if(etext == null){ // 20060825 sunhj added
		return false;
	}else{
		var elen=fieldName.value.length;
		var aa;
		for (i=0;i<=elen-1;i++){
			aa=etext.charAt(i);
			if (aa<'0'||aa>'9'){
				flag=false;
				break;
			}
		}				
		return flag;
	}
}

//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��������
//**     �����
//**     ˵����
//******************************************************************
function f_isNumFormat3(fieldName){	
	var flag = true;
	var etext=fieldName.value;
	if(etext == null){ // 20060825 sunhj added
		return false;
	}else{
		var elen=fieldName.value.length;
		var aa;
		for (i=0;i<=elen-1;i++){
			aa=etext.charAt(i);
			if(i==0){
				if(aa!='-'){
					if (aa<'0'||aa>'9'){
						flag=false;
						break;
					}
				}
			}else{
				if (aa<'0'||aa>'9'){
					flag=false;
					break;
				}
			}
		}				
		return flag;
	}
}

//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��������
//**     �����
//**     ˵�������Ƿ��ڣ�����֮�䣬��"."
//******************************************************************
function f_isFloatFormat(fieldName){	
	var flag = true;
	var etext=fieldName.value;
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if ((aa<'0'||aa>'9')&&(aa!='.')){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  fieldName.focus();	  
    }				
	return flag;
}


//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��ĸ������
//**     �����
//**     ˵�������Ƿ��ڣ�����֮�䣬��a~z,A~Z֮��
//******************************************************************
function f_isNumChar(fieldName,fieldDesc){	
    var flag = true;
    var etext=fieldName.value;

    if(etext!=null&&etext!=''){
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charCodeAt(i);

		if ((aa<48)||(aa>57&&aa<65)||(aa>90&&aa<97)||(aa>122)){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  	alert(fieldDesc+"  ֻ��������ĸ�����֣�");
	  	fieldName.focus();	  
    	}
    }				
    return flag;
}


//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��ĸ�����ֺ��»���
//**     �����
//**     ˵�������Ƿ��ڣ�����֮�䣬��a~z,A~Z֮��
//******************************************************************
function f_isNumChar2(fieldName,fieldDesc){	
     var flag = true;
    var etext=fieldName.value;

    if(etext!=null&&etext!=''){
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charCodeAt(i);

		if (((aa<48)||(aa>57&&aa<65)||(aa>90&&aa<97)||(aa>122))&&aa!=95){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  	alert(fieldDesc+"  ֻ��������ĸ�����ֺ��»��ߣ�");
	  	fieldName.focus();	  
    	}
    }				
    return flag;
}

//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��ĸ������
//**     �����
//**     ˵�������Ƿ��ڣ�����֮�䣬��a~z,A~Z֮�� Add by hao for repairBRDetail
//******************************************************************
function f_isNumChar3(fieldName){	
    var flag = true;
    var etext=fieldName.value;

    if(etext!=null&&etext!=''){
		var elen=fieldName.value.length;
		var aa;
		for (i=0;i<=elen-1;i++){
			aa=etext.charCodeAt(i);
	
			if (((aa<48)||(aa>57&&aa<65)||(aa>90&&aa<97)||(aa>122))){
				flag=false;
				break;
			}
		}	
	}			
    return flag;
}


//******************************************************************
//**     ���ܣ��ж�ֵ�Ƿ���8~222֮�䣬��Ϊ�Ϸ��ļ������루�����������Ļ����ĵ�������
//**     �����
//**     ˵����
//******************************************************************
function f_isASCIICode(fieldName){	
	var flag = true;
	var etext=fieldName.value;
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charCodeAt(i);
		if ((aa>222)||(aa<8)){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  fieldName.focus();	  
    }				
	return flag;
}
//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��Ч����
//**     �����
//**     ˵����
//******************************************************************
function f_isValidInt(fieldName,fieldDesc) 
{
  var flag=true;
  var Obj=fieldName;
  var elen=fieldName.value.length;
  var aa;
  var first;
  if(isNaN(Obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : ���������� ��"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  else {
  	first=new Number(fieldName.value.charAt(0));
  	if(first>=1&&first<=9) {
      for (i=1;i<=elen-1;i++){
		aa=fieldName.value.charAt(i);
		if (aa<'0'||aa>'9')
		{
			flag=false;
			alert(fieldDesc +" : ��������Ч���� ��"); 
	        fieldName.value="";
	        fieldName.focus();
			break;
		}
	  }
	}
	else {
	   flag=false;
		alert(fieldDesc +" : ��������Ч���� ��"); 
	    fieldName.value="";
	    fieldName.focus();
	}	  
  }		
  return flag;
}
//******************************************************************
//**     ���ܣ��ж��Ƿ�Ϊ��Ч����
//**     ���������
//**           fieldName ���������� eg: document.forms[0].textboxname
//**           fieldDesc : �������� eg: Ͷ�ʽ��
//**           decNum    : С������ eg: 2   
//**     ��������Ϸ���������Ӧ����ʾ��Ϣ������ false
//**           �Ϸ���  ����true
//**     ˵����
//******************************************************************
function f_isValidNum(fieldName,fieldDesc,decNum) 
{
  var flag=true;
  var obj=fieldName;
  var vDecIndex;
  vDecIndex=obj.value.indexOf(".");
  if(isNaN(obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : ���������� ��"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  else if(obj.value<0) {
     alert(fieldDesc +" :����Ϊ����");
     flag=false;
	 fieldName.focus();
  }
  else if(obj.value.charAt(0)=='0'&&obj.value.charAt(1)!='.'||obj.value.charAt(obj.value.length-1)<'0'||obj.value.charAt(obj.value.length-1)>'9')
  {
    flag=false; 
    alert(fieldDesc+" : ��������Ч���� ��");
	fieldName.value="";
	fieldName.focus();
  }
  if(vDecIndex!=-1) {
  	if(vDecIndex>9) {
  		flag=false; 
    	alert(fieldDesc+" : ����������ݹ����밴��Ҫ�����룡"); 
		fieldName.focus();
	}
	else if(obj.value.length-vDecIndex>decNum+1) {
  		flag=false; 
    	alert(fieldDesc+" : �밴��Ҫ����"+decNum+"λС����"); 
		fieldName.focus();
	}		
  }	
  return flag;
}
//******************************************************************
//**     ���ܣ��ж��Ƿ�Ϊ��Ч����
//**     ���������
//**           fieldName ���������� eg: document.forms[0].textboxname
//**           fieldDesc : �������� eg: Ͷ�ʽ��
//**           decNum    : С������ eg: 2   
//**     ��������Ϸ���������Ӧ����ʾ��Ϣ������ false
//**           �Ϸ���  ����true
//**     ˵����
//******************************************************************
function f_isValidNum4(fieldName,fieldDesc,decNum) 
{
  var flag=true;
  var Obj=fieldName;
  var first;
  if(isNaN(Obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : ���������� ��"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  else if(Obj.value<0) {
     alert(fieldDesc +" :����Ϊ����");
     flag=false;
	 fieldName.focus();
  }
  else if(Obj.value.charAt(0)=='0'&&Obj.value.charAt(1)!='.'||Obj.value.charAt(Obj.value.length-1)<'0'||Obj.value.charAt(Obj.value.length-1)>'9')
  {
    flag=false; 
    alert(fieldDesc+" : ��������Ч���� ��");
	fieldName.value="";
	fieldName.focus();
  }
  return flag;
}


//******************************************************************
//**     ���ܣ��ж��Ƿ�Ϊ��Ч������
//**     ���������
//**           fieldName ���������� eg: document.forms[0].textboxname
//**           fieldDesc : ��������	eg: Ͷ�ʽ��
//**			 intNum     :  ������Чλ��	eg:10
//**           decNum    : С������Чλ��	 eg: 2   
//**     ��������Ϸ���������Ӧ����ʾ��Ϣ������ false
//**           �Ϸ���  ����true
//**     ˵����
//******************************************************************
function f_isValidFloat(fieldName,fieldDesc,intNum,decNum) 
{
  var flag=true;
  var obj=fieldName;
  var vDecIndex;
  vDecIndex=obj.value.indexOf(".");
  if(isNaN(obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : ���������� ��"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  else if(obj.value<0) {
     alert(fieldDesc +" :����Ϊ����");
     flag=false;
	 fieldName.focus();
  }
  else if(obj.value!=0&&obj.value.charAt(0)=='0'&&obj.value.charAt(1)!='.'||obj.value.charAt(obj.value.length-1)<'0'||obj.value.charAt(obj.value.length-1)>'9')
  {
    flag=false; 
    alert(fieldDesc+" : ��������Ч���� ��");
	fieldName.value="";
	fieldName.focus();
  }
  else if(vDecIndex!=-1) {
  	if(vDecIndex>intNum) {
  		flag=false; 
    	alert("�������"+fieldDesc+"�������ִ���"+intNum+"λ�����������룡"); 
		fieldName.focus();
	}
	else if(obj.value.length-vDecIndex>decNum+1) {
  		flag=false; 
    	alert(fieldDesc+" : �밴��Ҫ����"+decNum+"λС����"); 
		fieldName.focus();
	}		
  }	else{
  	if(obj.value.length>intNum) {
  		flag=false; 
    	alert("�������"+fieldDesc+"�������ִ���"+intNum+"λ�����������룡"); 
		fieldName.focus();
	}
  }
  return flag;
}

//******************************************************************
//**     ���ܣ��ж���λֵ�Ƿ�Ϊ��
//**     �����
//**     ˵����
//******************************************************************
function f_isNull(fieldName,fieldDesc)
{
	if(fieldName==null) return true;
   var flag=true;
   if(fieldName.value==null||fieldName.value=="") {
      flag=false;
	  alert("������ :"+ fieldDesc);
	  if(fieldName.type!='hidden'){
	  	fieldName.focus();
	  }
   }
   return flag;
}


//******************************************************************
//**     ���ܣ�У�鳤��
//**     �����
//**     ˵����
//******************************************************************
function f_maxLength(fieldName,fieldDesc,len){
		var flag=true;
		
   	var endvalue=0;
  	var sourcestr=new String(fieldName.value);
  	var tempstr;
  	if(sourcestr!=''){
	  	for (var strposition = 0; strposition < sourcestr.length; strposition ++) {
	    	tempstr=sourcestr.charAt(strposition);
	    	if (tempstr.charCodeAt(0)>255 || tempstr.charCodeAt(0)<0) {
	      		endvalue=endvalue+2;
	    	}else{
	    		endvalue=endvalue+1;
	    	}
	  	}
	  }
		
  	if(endvalue >len){
  		flag=false;
  		alert("�ֶγ��ȳ����������("+len+") :"+ fieldDesc);
  	}
  	return flag;
}

//******************************************************************
//**     ���ܣ��ж���λֵ�Ƿ�ΪС��
//**     �����
//**     ˵����
//******************************************************************
function f_isDecemal(fieldName,fieldDesc)
{
  var flag=true;
  var Obj=fieldName;
  if(isNaN(Obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : ������С�� ��"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  if(Obj.value.charAt(0)=='0'&&Obj.value.charAt(1)!='.'||Obj.value.charAt(0)!='0')
  {
    flag=false; 
    alert(fieldDesc+" : ������'0'��'1'֮�����Ч���� ��");
	fieldName.value="";
	fieldName.focus();
  }
  return flag;
}
//******************************************************************
//**     ���ܣ��ж϶���(��λ)ֵ�Ƿ�Ϊ�գ������Ϊ�յĻ����Ƿ�Ϊ��Ч����
//**     �����
//**     ˵����
//******************************************************************
function f_isNumCondValid(fieldName,fieldDecs){	 
	var flag = true; 
    if(fieldName.value!=""&&fieldName.value!=null) {
       flag=f_isValidNum(fieldName,fieldDecs);
    }				
	return flag;
}


//�ų��������ַ�����,�ų�+-.������0��ͷ
function checkIsNumPlus(item,item2){
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa=='+'){
			getError("invalidPlus",item2);//��������'+'�ţ�
			item.focus();
		}
	}
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa=='-'){
			getError("invalidMinus",item2);//��������'-'�ţ�
			item.focus();
		}
	}	
	if (etext.charAt(0)=='0'){
		getError("firstNotDot",item2);//��λ������'0'��
		item.focus();
	}	
	if (isNaN(item.value))
	{
		getError("checkIsNum",item2);
		item.focus();
	}
	return !errfound;
}


//�ų��������ַ��������,û���ж�+-.ȫ�ǣ�����0��ͷ
function checkIsNum(item,item2){
	errfound = false;

	if (isNaN(item.value))
	{
		getError("checkIsNum",item2);
		item.focus();
	}
	if (item.value.charAt(0)=='.')
	{
		item.value='0'+item.value;
	}
	return !errfound;
}


//�ų��������ַ��������,�ų���һ�������ַ���'0'
function firstNotDot(item,item2){
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	if (elen == 0){
		getError("invalidNotNull",item2);//�������Ϊ�գ�
		item.focus();
	}
	if (isNaN(item.value)){
		getError("checkIsNum",item2);
		item.focus();
	}
	if (etext.charAt(0)=='0'){
		getError("firstNotDot",item2);//��λ������'0'��
		item.focus();
	}
	return !errfound;
}


//�ų��������ַ��������,�ų���һ�������ַ���'0',ֻ������
function firstNotDot2(item,item2){
    
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	
	if (elen == 0){
		getError("invalidNotNull",item2);//�������Ϊ�գ�
		item.focus();
	}
	if (isNaN(item.value)){
		getError("checkIsNum",item2);
		item.focus();
	}
	if (etext.indexOf('.')!=-1){
		getError("invalidInt",item2);
		item.focus();
	}
	if (etext.charAt(0)=='0'){
		getError("firstNotDot",item2);//��λ������'0'��
		item.focus();
	}
	return !errfound;
}


//�ж��ı����Ƿ�Ϊ��
//���þ���f_timeCheck(receive_date,'��������');
function invalidNotNull(item,item2){
	errfound = false;
	etext=item.value;
	elen=item.value.length;
		if (elen == 0){
			getError("invalidNotNull",item2);//�������Ϊ�գ�
			item.focus();
		}
	return !errfound;
}



//�ж��û����������Ƿ���"+"
function invalidPlus(item,item2){	
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa=='+'){
			getError("invalidPlus",item2);//��������'+'�ţ�
			item.focus();
		}
	}
	return !errfound;
}


//�ж��û����������Ƿ���"-"
function invalidMinus(item,item2){	
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa=='-'){
			getError("invalidMinus",item2);//��������'-'�ţ�
			item.focus();
		}
	}
	return !errfound;
}
//���ݴ������ͣ��õ�������Ϣ
function getError(text,item2){
	if(text == "invalidInt"){
		text="������һ��������";
	}
	if(text == "invalidNotNull"){
		text="�������Ϊ�գ�";
	}
	if(text == "tooLongNum"){
		text="�����������̫��";
	}
	if(text == "tooLongStr"){
		text="��������ַ���̫����";
	}
	if(text == "invalidDateFormat"){
		text="������'yyyy-mm-dd'��ʽ����";
	}
	if(text == "invalidStr"){
		text="��������ַ����Ƿ���";
	}
	if(text == "invalidEmail"){
		text="����Email��ʽ��Ч��";
	}
	if(text == "invalidNumFormat"){
		text="�����������֣�";
	}
	if(text == "invalidDot"){
		text="��������'.'�ţ�";
	}
	if(text == "invalidPlus"){
		text="��������'+'�ţ�";
	}
	if(text == "invalidMinus"){
		text="��������'-'�ţ�";
	}
	if(text == "checkIsNum"){
		text="���������֣�";
	}
	if(text == "checkIsLong"){
		text="������Ϸ���ֵ��";
	}
	if(text == "firstNotDot"){
		text="��λ������'0'��";
	}
	if(text == "dateInvalid"){
		text="���ڸ�ʽ���Ϸ�(ע:��׼����Ϊyyyy-mm-dd��ʽ)��";
	}
	if(text == "numberInvalid"){
		text="������������Ͳ��Ϸ���";
	}
	if(text == "noInvalid"){
		text="����ı�Ų��Ϸ�(ע:��׼���Ϊyyyymmdd####��ʽ)��";
	}
	if(text == "invalidPlusInt"){
		text="������һ����������";
	}
	if(errfound) return;
		window.alert(item2+text);
	errfound=true;
}




function chkPercent(data0){
    var varRet=false;
    
    if(data0!=null&&data0!=''){
	var data1=data0.substring(data0.length-1);
	if(data1=='%'){
		var data2=data0.substring(0,data0.length-1);
		  if(isNaN(data2)) {
			 alert("���������� ��"); 
		  }else if(data2<0) {
		     	alert("����Ϊ����");
		  }else{
		  	varRet=true;
		  }
		
	}
    }else{
    	alert("�ۿ۲���Ϊ��");
    }
	return varRet;
	
}



//******************************************************************
//**     ���ܣ��ж϶����ֵ�Ƿ�Ϊ��Ǯ(��������)
//**     �����
//**     ˵�������Ƿ��ڣ�����֮�䣬��"."
//******************************************************************
function f_isMoney(data0){	
  var flag=true;
  var vDecIndex=data0.indexOf(".");
  if(data0==null||data0==''){
  	alert("�۸���Ϊ��");
  	flag=false;
  }else if(isNaN(data0)) {
     	 flag=false; 
	 alert("���������� ��"); 
  }	
  else if(data0<0) {
     	alert("����Ϊ����");
     	flag=false;
  }

  else if(vDecIndex!=-1) {
  	if(data0.length-vDecIndex>3) {
	  	flag=false; 
	    	alert("�밴��Ҫ����2λС����"); 
	}		
  }	
  return flag;
}



//check Date format
function checkInputDate(obj){
	if(obj==null) return true;
    var   strDate=obj.value;
    var  re =/^(\d{4})-(\d{2})-(\d{2})$/;
    if(re.test(strDate))//�ж����ڸ�ʽ����YYYY-MM-DD��׼
    {
     var   dateElement=new   Date(RegExp.$1,parseInt(RegExp.$2,10)-1,RegExp.$3);
      if(!((dateElement.getFullYear()==parseInt(RegExp.$1))&&((dateElement.getMonth()+1)==parseInt(RegExp.$2,10))&&(dateElement.getDate()==parseInt(RegExp.$3))))//�ж������߼�
      {
       //alert("xx���ڸ�ʽ����ӦΪ��(YYYY-MM-DD)");
       //return false;
       }
    }else{
    	alert("���ڸ�ʽ����ӦΪ��(YYYY-MM-DD)");
    	return false;
    }
    return true;
  }
 