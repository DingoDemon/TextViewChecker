package wong.dingo.com.edittextchecker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.root)
    LinearLayout root;


    @CheckInfo(allowedEmpty = false, textName = "姓名", type = CheckInfo.Type.EditTextView, priority = 99)
    @BindView(R.id.name_edit)
    EditText editTextName;

    @CheckInfo(allowedEmpty = false, textName = "手机号", type = CheckInfo.Type.EditTextView, priority = 98)
    @BindView(R.id.phone_edit)
    EditText editTextPhone;

    @CheckInfo(allowedEmpty = false, textName = "邮箱", type = CheckInfo.Type.EditTextView, priority = 97)
    @BindView(R.id.email_edit)
    EditText editTextEmail;

    @CheckInfo(allowedEmpty = false, toastResId = R.string.date_toast, type = CheckInfo.Type.TextView, priority = 96)
    @BindView(R.id.birth_text)
    TextView textViewBirth;

    @BindView(R.id.commit_btn)
    Button buttonCommit;

    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.birth_text)
    void selectBirthDay() {
        final Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String result = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        textViewBirth.setText(result);
                    }
                }
                , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @OnClick(R.id.commit_btn)
    void checkValue() {
        TextChecker textChecker = new TextChecker();
        Boolean finish = textChecker.checkTextViews(MainActivity.this);
        Log.i("user finish his info :", finish.toString());
    }


}
