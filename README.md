# TextViewChecker

_一个简单的想法_

额，最近工作一两年都是呆在从事互金行业里的。采集用户信息是风控环节中非常重要的一环。所以，App开发中存在着大量用户信息填写表单的页面：

For Example：

![](https://github.com/DingoDemon/AndroidNotes/blob/master/LinkPics/view.png?raw=true)



在对用户输入内容进行非空的时候，往往会产生一大批机械代码：


*不是我写的，但是原来我去写，可能思路也是如此*

```java
 if (StringUtils.isNull(tvCity.getText().toString())) {
            showToast(R.string.input_city);
            return false;
        }
        if (StringUtils.isNull(editDetailAddress.getText().toString())) {
            showToast(R.string.input_detail_address);
            return false;
        }
        if (StringUtils.isNull(tvMarrige.getText().toString())) {
            showToast(R.string.pick_marriage);
            return false;
        }
。。。
        if (!StringUtils.isEmail(editEmail.getText().toString().trim())) {
            showToast(R.string.input_valid_email_address);
            return false;
        }
```

这样的代码逻辑对于我个人来说，完全是灾难。em...真的很讨厌面向cv编程。

想了想，借鉴了ButterKnife的思路，可以在定义输入栏目的时候，就给它加上注解,最后统一检查就好了。


定义了5个注解元素：

 元素 | 示意 | 默认值
| ------------ | ------------ | --------- |
| allowedEmpty|是否允许为空| true |
| textName | TextView名字(填写内容) | ""  |
| type  | TextView or EditText 主要为了区分“选择”还是“填写”| TextView |
| position | 在界面中从上到下的位置，读取Field 顺序无法控制，如果对检查顺序有要求，请赋值| -1 |
| toastResId | 检查为空toast内容string的resid | -1 |

使用非常简单:

```java
 @CheckInfo(allowedEmpty = false, textName = "姓名", type = CheckInfo.Type.EditTextView, priority = 1)
    @BindView(R.id.name_edit)
    EditText editTextName;

    @CheckInfo(allowedEmpty = true, textName = "手机号", type = CheckInfo.Type.EditTextView, priority = 2)
    @BindView(R.id.phone_edit)
    EditText editTextPhone;

    @CheckInfo(allowedEmpty = false, textName = "邮箱", type = CheckInfo.Type.EditTextView, priority = 3)
    @BindView(R.id.email_edit)
    EditText editTextEmail;

    @CheckInfo(allowedEmpty = false, toastResId = R.string.date_toast, type = CheckInfo.Type.TextView, priority = 4)
    @BindView(R.id.birth_text)
    TextView textViewBirth;
```

    





