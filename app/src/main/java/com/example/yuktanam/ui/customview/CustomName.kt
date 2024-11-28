package com.example.yuktanam.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.yuktanam.R

class CustomName : AppCompatEditText {
    private lateinit var nameIconDrawable: Drawable

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
        context, attrs, defStyleAttr
    ) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        nameIconDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_person) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        compoundDrawablePadding = 24

        setHint(R.string.name_hint)
        setIconDrawable(nameIconDrawable)
        importantForAccessibility

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