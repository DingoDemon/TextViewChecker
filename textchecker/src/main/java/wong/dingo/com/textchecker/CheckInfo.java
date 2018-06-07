package wong.dingo.com.textchecker;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckInfo {

    boolean allowedEmpty() default true;

    int toastResId() default PRESENT_VALUE;

    String textName() default "";

    Type type() default Type.TextView;

    int position() default PRESENT_VALUE;

    enum Type {TextView, EditTextView}

    public static final int PRESENT_VALUE = -1;


}
