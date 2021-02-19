package util;

public class StringUtil {
    public static boolean isEmpty(Object str){
        if(null == str){
            return true;
        }else if(str.toString().trim().equals("")||str.toString().trim().equals("null")){
            return true;
        }
        return false;
    }
}
