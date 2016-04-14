package bitcamp.pms.util;
import java.util.regex.Pattern;

/** 문자열의 형식을 검사하는 기능을 갖는 클래스 */
public class PatternChecker {
     
    /** 숫자 모양에 대한 형식 검사 */
    public static boolean isNum(String str) {
        return Pattern.matches("^[0-9]*$", str);
    }
     
    /** 영문으로만 구성되었는지에 대한 형식 검사 */
    public static boolean isEng(String str) {
        return Pattern.matches("^[a-zA-Z]*$", str);
    }
     
    /** 한글로만 구성되었는지에 대한 형식 검사 */
    public static boolean isKor(String str) {
        return Pattern.matches("^[ㄱ-ㅎ가-힣]*$", str);
    }
     
    /** 영문과 숫자로만 구성되었는지에 대한 형식 검사 */
    public static boolean isEngNum(String str) {
        return Pattern.matches("^[a-zA-Z0-9]*$", str);
    }
     
    /** 한글과 숫자로만 구성되었는지에 대한 형식 검사 */
    public static boolean isKorNum(String str) {
        return Pattern.matches("^[ㄱ-ㅎ가-힣0-9]*$", str);
    }
     
    /** 이메일 형식인지에 대한 검사 --> "아이디@도메인"의 형식을 충족해야 한다. */
    public static boolean isEmail(String str) {
        return Pattern.matches("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", str);
    }
     
    /** 핸드폰번호인지에 대한 형식검사.
        반드시 앞자리가 010,010,016~9사이를 충족해야 하며,
        각 부분에 대한 자리수도 충족시켜야 한다.
        "-"는 허용하지 않는다. */
    public static boolean isCellPhone(String str) {
        return Pattern.matches("(^\\d{2,4}-\\d{3,4}-\\d{4}$)", str);
    }
     
    /** 전화번호인지에 대한 형식검사. 각 연결부는 "-"로 구분되어야 한다.
        각 부분에 대한 자리수도 충족시켜야 한다.
        "-"는 허용하지 않는다. */
    public static boolean isTel(String str) {
        return Pattern.matches("(^\\d{3,4}-\\d{4}$)" , str);
    }
     
    /** 주민번호에 대한 글자수 및 뒷자리 첫글자가 1~4의 범위에 있는지에 대한 검사.
        "-"는 허용하지 않는다. */
    public static boolean isJumin(String str) {
        return Pattern.matches("^\\d{6}[1-4]\\d{6}", str);
    }
     
    /** 아이피주소 형식에 대한 검사 */
    public static boolean isIP(String str) {
        return Pattern.matches("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})", str);
    }

    public static boolean isPassword(String check) { 
      return Pattern.matches("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@?])([a-zA-Z0-9!@?]){4,10}$", check);
    }
}