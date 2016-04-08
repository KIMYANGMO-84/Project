//용도
// 클래스나 메서드에 붙여서 명령어를 설정한다.
// 명령어
// 클래스에 붙인 값 + 메서드에 붙인 값
//  

package bitcamp.pms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface RequestMapping {
  String value() default "";
}
