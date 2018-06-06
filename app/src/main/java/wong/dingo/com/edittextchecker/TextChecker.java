package wong.dingo.com.edittextchecker;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class TextChecker {
    private FieldsHandler fieldsHandler;


    public TextChecker() {
        fieldsHandler = new FieldsHandler();
    }

    /**
     * @param activity
     * @return 是否符合注解要求
     */
    public boolean checkTextViews(Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getFields();
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

        private MinFieldHeap<Field> priorityQueue;


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
                                if (TextUtils.isEmpty(textView.getText().toString()) && !checkInfo.allowedEmpty()) {
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
                                Log.i("!!!",editText.getText().toString().length()+"");
                                if (TextUtils.isEmpty(editText.getText().toString()) && !checkInfo.allowedEmpty()) {
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
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}


