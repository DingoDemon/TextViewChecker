package wong.dingo.com.edittextchecker;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

public class TextChecker {

    private static final String defaultSelectTip = "请选择";
    private static final String defaultEditTip = "请输入";


    /**
     * @param activity
     * @return 是否符合要求
     */
    public static boolean checkTextViewInActivity(Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CheckInfo.class)) {
                CheckInfo checkInfo = field.getAnnotation(CheckInfo.class);
                if (!checkInfo.allowedEmpty()) {
                    try {
                        switch (checkInfo.type()) {
                            case TextView:
                                TextView textView = (TextView) field.get(activity);
                                if (TextUtils.isEmpty(textView.getText().toString()) && !checkInfo.allowedEmpty()) {
                                    if (checkInfo.toastResId() != -1) {
                                        Toast.makeText(activity, checkInfo.toastResId(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, defaultSelectTip.concat(checkInfo.textName()), Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                                break;
                            case EditTextView:
                                EditText editText = (EditText) field.get(activity);
                                if (TextUtils.isEmpty(editText.getText().toString()) && !checkInfo.allowedEmpty()) {
                                    if (checkInfo.toastResId() != -1) {
                                        Toast.makeText(activity, checkInfo.toastResId(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, defaultEditTip.concat(checkInfo.textName()), Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                                break;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                }
            }

        }
        return true;
    }


}
