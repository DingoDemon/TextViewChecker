package wong.dingo.com.textchecker;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


public class TextChecker {
    private final FieldsHandler fieldsHandler;


    public TextChecker() {
        fieldsHandler = new FieldsHandler();
    }

    /**
     * @param activity
     * @return 是否符合注解要求
     */
    public boolean checkTextViews(Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CheckInfo.class)) {
                fieldsHandler.collectAnnotationField(field);
            }
        }

        return fieldsHandler.check(activity);

    }

    public void checkTextViews(Activity activity, EmptyListener listener) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CheckInfo.class)) {
                fieldsHandler.collectAnnotationField(field);
            }
        }
        fieldsHandler.check(activity, listener);
    }


    interface EmptyListener {
        void findEmpty(View vew);
    }

    class FieldsHandler {

        private final MinFieldHeap<Field> priorityQueue;


        FieldsHandler() {
            this.priorityQueue = new MinFieldHeap<Field>();
        }

        void collectAnnotationField(Field field) {
            priorityQueue.insert(field);
        }


        boolean check(Activity activity) {
            while (priorityQueue.size() > 0) {
                Field field = priorityQueue.poll();
                CheckInfo checkInfo = field.getAnnotation(CheckInfo.class);
                if (!checkInfo.allowedEmpty()) {
                    try {
                        switch (checkInfo.type()) {
                            case TextView:
                                TextView textView = (TextView) field.get(activity);
                                if (TextUtils.isEmpty(textView.getText().toString())) {
                                    if (checkInfo.toastResId() != CheckInfo.PRESENT_VALUE) {
                                        Toast.makeText(activity, checkInfo.toastResId(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, activity.getResources().getString(R.string.please_select).concat(checkInfo.textName()), Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                                break;
                            case EditTextView:
                                EditText editText = (EditText) field.get(activity);
                                if (TextUtils.isEmpty(editText.getText().toString())) {
                                    if (checkInfo.toastResId() != CheckInfo.PRESENT_VALUE) {
                                        Toast.makeText(activity, checkInfo.toastResId(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, activity.getResources().getString(R.string.please_select).concat(checkInfo.textName()), Toast.LENGTH_SHORT).show();
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
            return true;
        }

        void check(Activity activity, EmptyListener listener) {
            while (priorityQueue.size() > 0) {
                Field field = priorityQueue.poll();
                CheckInfo checkInfo = field.getAnnotation(CheckInfo.class);
                if (!checkInfo.allowedEmpty()) {
                    try {
                        TextView textView = (TextView) field.get(activity);
                        if (TextUtils.isEmpty(textView.getText().toString()))
                            listener.findEmpty(textView);
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}


