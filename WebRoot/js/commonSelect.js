
function chk(){
	if(document.forms[0].chk==null){
 		return;
    }
    var chkId = '';
    var len = document.forms[0].chk.length;
	if(len>1){
	    var tag = 0;
		for(var i=0;i<len;i++){
			if(document.forms[0].chk[i].checked){   
			    if (tag==0) {
			    	chkId = chkId + document.forms[0].chk[i].value;
			    	tag = 1;
			    } else {
			    	chkId = chkId + ","+document.forms[0].chk[i].value;
			    }           
									
			}
		}
	} else {
		if(document.forms[0].chk.checked){
			chkId = document.forms[0].chk.value;
	 	}
	}
	if (chkId=='') {
	    return;
	}
	return chkId;
}

function chkOnly(){
	if(document.forms[0].chk==null){
 		return false;
    }
    var chkId = '';
    var len = document.forms[0].chk.length;
	if(len>1){
	    var tag = 0;
		for(var i=0;i<len;i++){
			if(document.forms[0].chk[i].checked){   
			    tag = tag+1;
			}
		}
	} else {
		if(document.forms[0].chk.checked){
			tag = 1;
		}
	}
	if (tag>0) {
	    if (tag==1) {
	       return true;
	    } else {
	    	alert("只能选择一条记录");
	    	return false;
	    }
	} else {
	    alert("请选择记录");
	    return false;
	}
}

function checkAll(){
        var checkAll=0;
	var theForm = document.forms[0];

	if(theForm.allbox.checked){
	   var checkAll=1;
		for(var i = 0; i < theForm.length; i++) {
	      if (theForm[i].type == "checkbox" && theForm[i].disabled==false) {
	         theForm[i].checked=true;
		  }
	   }
	}
	else{
		for(var i = 0; i < theForm.length; i++) {
	      if (theForm[i].type == "checkbox") {
	         theForm[i].checked=false;
		  }
	   }
	}
	return checkAll;
}

