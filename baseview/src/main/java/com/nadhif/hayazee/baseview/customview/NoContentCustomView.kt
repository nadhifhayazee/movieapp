package com.nadhif.hayazee.baseview.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.nadhif.hayazee.baseview.R

class NoContentCustomView(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(
    context,
    attrs
) {

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : this(context, attrs)

    private var _attributes: TypedArray
    private var _ivNoContent: ImageView
    private var _tvMessage: TextView

    val noContentImage get() = _ivNoContent
    val noContentTextMessage get() = _tvMessage

    init {
        inflate(context, R.layout.no_content_view_layout, this)
        _attributes = context.obtainStyledAttributes(attrs, R.styleable.NoContentCustomView)
        _ivNoContent = findViewById(
            R.id.iv_no_content
        )
        _tvMessage = findViewById(
            R.id.tv_message
        )

        setupView()

        _attributes.recycle()
    }

    private fun setupView() {
        val messageColor =
            _attributes.getColor(
                R.styleable.NoContentCustomView_messageTextColor,
                ContextCompat.getColor(context, android.R.color.black)
            )

        val message = _attributes.getString(R.styleable.NoContentCustomView_message) ?: "No data found!"

        val imgNoContent = _attributes.getResourceId(
            R.styleable.NoContentCustomView_noContentDrawable,
            R.drawable.ic_no_movie_found
        )

        _tvMessage.text = message
        _ivNoContent.setBackgroundResource(imgNoContent)
        _tvMessage.setTextColor(messageColor)

    }

}