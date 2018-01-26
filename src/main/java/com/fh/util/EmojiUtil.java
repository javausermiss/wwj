package com.fh.util;
import com.github.binarywang.java.emoji.EmojiConverter;
 
 
/**
 * 表情处理类
 * @author Administrator
 *
 */
public final class EmojiUtil {
 
  private static EmojiConverter emojiConverter = EmojiConverter.getInstance();
   
  /**
   * 将emojiStr转为 带有表情的字符
   * @param emojiStr
   * @return
   */
  public static String emojiConverterUnicodeStr(String emojiStr){
     String result = emojiConverter.toUnicode(emojiStr);
     return result;
  }
   
  /**
   * 带有表情的字符串转换为编码
   * @param str
   * @return
   */
  public static String emojiConverterToAlias(String str){
	try{
	   if(StringUtils.isNotEmpty(str)){
		  str=emojiConverter.toAlias(str);
	   }
	}catch(Exception ex){
		str="";
	}
    return str;
  }
  
  public static void main(String[] args) {
	    String str = "😀你好";  
	    String alias = EmojiUtil.emojiConverter.toAlias(str);  
	    System.out.println(str);  
	    System.out.println("EmojiConverterTest.testToAlias()=====>");  
	    System.out.println(alias); 
  }
   
}