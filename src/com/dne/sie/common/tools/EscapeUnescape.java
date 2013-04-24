/*
 * 创建日期 2006-4-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dne.sie.common.tools;

/**
 * 编码与解码类
 * @author HaoShuang
 *
 */
public class EscapeUnescape {
	/**
	 * 把制定的字符串进行编码
	 * @param src 字符串
	 * @return String 编码结果
	 */
	public static String escape(String src) {
		if(src == null){
			return null;
		}else{
			int i;
			char j;
			StringBuffer tmp = new StringBuffer();
			tmp.ensureCapacity(src.length() * 6);
			for (i = 0; i < src.length(); i++) {
				j = src.charAt(i);
				if (Character.isDigit(j)
					|| Character.isLowerCase(j)
					|| Character.isUpperCase(j))
					tmp.append(j);
				else if (j < 256) {
					tmp.append("%");
					if (j < 16)
						tmp.append("0");
					tmp.append(Integer.toString(j, 16));
				} else {
					tmp.append("%u");
					tmp.append(Integer.toString(j, 16).toUpperCase()); // To same with javascript escape, I add toUpperCase(). Sunhj 060510
				}
			}
			return tmp.toString();
		}
	}
	/**
	 * 根据编码解码出字符串
	 * @param src 编码字符串
	 * @return String 解码后的字符串
	 */
	public static String unescape(String src) {
		if(src == null){
			return null;
		}else{
			StringBuffer tmp = new StringBuffer();
			tmp.ensureCapacity(src.length());
			int lastPos = 0, pos = 0;
			char ch;
			while (lastPos < src.length()) {
				pos = src.indexOf("%", lastPos);
				if (pos == lastPos) {
					if (src.charAt(pos + 1) == 'u') {
						ch =
							(char) Integer.parseInt(
								src.substring(pos + 2, pos + 6),
								16);
						tmp.append(ch);
						lastPos = pos + 6;
					} else {
						ch =
							(char) Integer.parseInt(
								src.substring(pos + 1, pos + 3),
								16);
						tmp.append(ch);
						lastPos = pos + 3;
					}
				} else {
					if (pos == -1) {
						tmp.append(src.substring(lastPos));
						lastPos = src.length();
					} else {
						tmp.append(src.substring(lastPos, pos));
						lastPos = pos;
					}
				}
			}
			return tmp.toString();
		}
	}
	public static void main(String[] args) {
		String tmp = "点击进入^~^aa地址";
		String tmp2 = "%u70B9%u51FB%u8FDB%u5165%5e%7e%5eaa%u5730%u5740";
		
		tmp = escape(tmp);
	System.out.println(tmp);
		
		System.out.println(unescape(tmp2));
	}
}
