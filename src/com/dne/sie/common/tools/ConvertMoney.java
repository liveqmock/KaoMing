package com.dne.sie.common.tools;

public class ConvertMoney {

	public static String[] STANDARD_STR = { "分", "角", "", "", "拾", "佰", "仟",
	    "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};

	public static String convertMoneyFormat(double src) {
	  String result = "";
	  if (src == 0)
	    return "零元整";

	  boolean isNegative = (src < 0) ? true : false;
	  long source = Math.round(src * 100.0);
	  if (isNegative)
	    source = -source;

	  String str = Long.toString(source);
	  int length = str.length() + 1;
	  if (length < 4) {
	    str = "0.00".substring(0, 5 - length) + str;
	    length = 4;
	  } else
	    str = str.substring(0, length - 3) + "." + str.substring(length - 3);

	  char a;
	  for (int i = 0; i < length; i++) {
	    a = str.charAt(i);
	    // 转换为对应数字（小数点转为货币单位“元”）
	    switch (a) {
	    case '.':
	    result += "元";
	    break;
	    case '1':
	    result += "壹";
	    break;
	    case '2':
	    result += "贰";
	    break;
	    case '3':
	    result += "叁";
	    break;
	    case '4':
	    result += "肆";
	    break;
	    case '5':
	    result += "伍";
	    break;
	    case '6':
	    result += "陆";
	    break;
	    case '7':
	    result += "柒";
	    break;
	    case '8':
	    result += "捌";
	    break;
	    case '9':
	    result += "玖";
	    break;
	    case '0':
	    default:
	    result += "零";
	    }
	    
	    // 加上表示数量级的量纲
	    result += STANDARD_STR[length - i - 1];
	  }

	  // 去除冗余字串
	  result = result.replaceAll("零拾", "零");
	  result = result.replaceAll("零佰", "零");
	  result = result.replaceAll("零仟", "零");
	  result = result.replaceAll("零零零", "零");
	  result = result.replaceAll("零零", "零");
	  result = result.replaceAll("零角零分", "整");
	  result = result.replaceAll("零分", "整");
	  result = result.replaceAll("零角", "零");
	  result = result.replaceAll("零亿零万零元", "亿元");
	  result = result.replaceAll("亿零万零元", "亿元");
	  result = result.replaceAll("零亿零万", "亿");
	  result = result.replaceAll("零万零元", "万元");
	  result = result.replaceAll("万零元", "万元");
	  result = result.replaceAll("零亿", "亿");
	  result = result.replaceAll("零万", "万");
	  result = result.replaceAll("零元", "元");
	  result = result.replaceAll("零零", "零");

	       // 特殊处理
	  if (result.startsWith("元"))
	    result = result.substring(1);

	       // 负数之前加上“负”
	  if (isNegative)
	    result = "负" + result;

	  return result;
	}

}
