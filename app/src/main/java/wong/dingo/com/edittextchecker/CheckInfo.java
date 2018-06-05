package wong.dingo.com.edittextchecker;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckInfo {

    boolean allowedEmpty() default true;

    int toastResId() default -1;

    String textName() default "";

    Type type() default Type.TextView;

    enum Type {TextView, EditTextView}


}
