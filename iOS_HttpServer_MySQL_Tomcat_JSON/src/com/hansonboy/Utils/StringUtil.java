package com.hansonboy.Utils;

public class StringUtil {
	public static int getCountSubStr(String source, String target){  
        int number = 0;  
        int i = 0;  
        while((i=source.indexOf(target, i))!=-1) {  
            number++;  
            i++;  
        }  
        return number;  
    }  
}
