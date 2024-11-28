package com.example.yuktanam.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.yuktanam.R

class CustomPassword : AppCompatEditText {

    private lateinit var passwordIconDrawable: Drawable

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context) : super(context) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        passwordIconDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_password) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        compoundDrawablePadding = 24

        setHint(R.string.password_hint)
        setAutofillHints(AUTOFILL_HINT_PASSWORD)
        setIconDrawable(passwordIconDrawable)
        transformationMethod = PasswordTransformationMethod()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && s.length < 8)
                    error = context.getString(R.string.error_password_too_short)
            }
        })
    }

    private fun setIconDrawable(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }
}