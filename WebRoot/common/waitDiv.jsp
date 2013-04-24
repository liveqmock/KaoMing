




<SCRIPT language=JAVASCRIPT1.2>

function showWaitDiv(myheight,mywidth){

	waiting.style.top=(myheight-91)/2;
	waiting.style.left=(mywidth-195)/2;
	coverAll.style.height=myheight;
	waiting.style.visibility="visible";
	coverAll.style.visibility="visible";
}

function showWaitDiv2(myheight,mywidth){ 
	waiting.style.top=(myheight-180);
	waiting.style.left=(mywidth-195)/2;
	coverAll.style.height=myheight;
	waiting.style.visibility="visible";
	coverAll.style.visibility="visible";
}

function hideWaitDiv(){

	waiting.style.visibility="hidden";
	coverAll.style.visibility="hidden";
}

</SCRIPT>

<div id="waiting" style="position:absolute; z-index:100; visibility:hidden;text-align:center">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr><td>			
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><img border="0" src="<%= request.getContextPath()%>/images/loding.gif" width="195" height="91"></td>
    </tr>
 </table>
		</td>


		</tr>
	</table>
</div>

<div id="coverAll" style="position:absolute; top:0; left:0; z-index:99; visibility:hidden;width:100%;heigth:100%">
	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center"><br>
			</td>
		</tr>
	</table>
</div>