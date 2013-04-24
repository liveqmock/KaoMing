function showCustomerInfo(sFld){ 
	var clientScreenHeight = screen.height;
	var clientScreenWidth = screen.width;
	var openWindowHeight = 400;
	var openWindowWidth = 800;
	var popTop = (clientScreenHeight-openWindowHeight)/2;
	var popleft = (clientScreenWidth-openWindowWidth)/2;
	window.open("customerInfoAction.do?method=popupCustList&customerName="+encode64(escape(eval("document.forms[0]."+sFld).value))+"&txtNumPerPage=3","_blank","height="+openWindowHeight+",width="+openWindowWidth+",resizable=no,scrollbars=yes,status=yes,top="+popTop+",left="+popleft);
}

//base64位编码,解决中文乱码
var keyStr = "ABCDEFGHIJKLMNOP" +
                "QRSTUVWXYZabcdef" +
                "ghijklmnopqrstuv" +
                "wxyz0123456789+/" +
                "=";
function encode64(input) {
      input = escape(input);
      var output = "";
      var chr1, chr2, chr3 = "";
      var enc1, enc2, enc3, enc4 = "";
      var i = 0;

      do {
         chr1 = input.charCodeAt(i++);
         chr2 = input.charCodeAt(i++);
         chr3 = input.charCodeAt(i++);

         enc1 = chr1 >> 2;
         enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
         enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
         enc4 = chr3 & 63;

         if (isNaN(chr2)) {
            enc3 = enc4 = 64;
         } else if (isNaN(chr3)) {
            enc4 = 64;
         }

         output = output + 
            keyStr.charAt(enc1) + 
            keyStr.charAt(enc2) + 
            keyStr.charAt(enc3) + 
            keyStr.charAt(enc4);
         chr1 = chr2 = chr3 = "";
         enc1 = enc2 = enc3 = enc4 = "";
      } while (i < input.length);

      return output;
   }
