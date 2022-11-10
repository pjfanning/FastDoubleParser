package ch.randelshofer.fastdoubleparser;

public class Strings {
    private Strings(){}
    public static String repeat(String s,int count){
        StringBuilder buf = new StringBuilder(s.length() * count);
        for (int i=0;i<count;i++){
            buf.append(s);
        }
        return buf.toString();
    }

}
