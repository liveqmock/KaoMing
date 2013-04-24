package com.dne.sie.common.tools;

public class ConvertMoney {

	public static String[] STANDARD_STR = { "��", "��", "", "", "ʰ", "��", "Ǫ",
	    "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ"};

	public static String convertMoneyFormat(double src) {
	  String result = "";
	  if (src == 0)
	    return "��Ԫ��";

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
	    // ת��Ϊ��Ӧ���֣�С����תΪ���ҵ�λ��Ԫ����
	    switch (a) {
	    case '.':
	    result += "Ԫ";
	    break;
	    case '1':
	    result += "Ҽ";
	    break;
	    case '2':
	    result += "��";
	    break;
	    case '3':
	    result += "��";
	    break;
	    case '4':
	    result += "��";
	    break;
	    case '5':
	    result += "��";
	    break;
	    case '6':
	    result += "½";
	    break;
	    case '7':
	    result += "��";
	    break;
	    case '8':
	    result += "��";
	    break;
	    case '9':
	    result += "��";
	    break;
	    case '0':
	    default:
	    result += "��";
	    }
	    
	    // ���ϱ�ʾ������������
	    result += STANDARD_STR[length - i - 1];
	  }

	  // ȥ�������ִ�
	  result = result.replaceAll("��ʰ", "��");
	  result = result.replaceAll("���", "��");
	  result = result.replaceAll("��Ǫ", "��");
	  result = result.replaceAll("������", "��");
	  result = result.replaceAll("����", "��");
	  result = result.replaceAll("������", "��");
	  result = result.replaceAll("���", "��");
	  result = result.replaceAll("���", "��");
	  result = result.replaceAll("����������Ԫ", "��Ԫ");
	  result = result.replaceAll("��������Ԫ", "��Ԫ");
	  result = result.replaceAll("��������", "��");
	  result = result.replaceAll("������Ԫ", "��Ԫ");
	  result = result.replaceAll("����Ԫ", "��Ԫ");
	  result = result.replaceAll("����", "��");
	  result = result.replaceAll("����", "��");
	  result = result.replaceAll("��Ԫ", "Ԫ");
	  result = result.replaceAll("����", "��");

	       // ���⴦��
	  if (result.startsWith("Ԫ"))
	    result = result.substring(1);

	       // ����֮ǰ���ϡ�����
	  if (isNegative)
	    result = "��" + result;

	  return result;
	}

}
