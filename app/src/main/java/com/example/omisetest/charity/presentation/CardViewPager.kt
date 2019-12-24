package com.example.omisetest.charity.presentation

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent


class CardViewPager(context: Context? = null, attrs: AttributeSet? = null) : ViewPager(context!!) {

  private var enabled: Boolean? = false

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    var heightMeasureSpec = heightMeasureSpec
    var height = 0
    for (i in 0 until childCount) {
      val child = getChildAt(i)
      child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
      val h = child.measuredHeight
      if (h > height) height = h
    }

    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)

    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
  }


  override fun onTouchEvent(event: MotionEvent): Boolean {
    return if (this.enabled!!) {
      super.onTouchEvent(event)
    } else false

  }

  override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
    return if (this.enabled!!) {
      super.onInterceptTouchEvent(event)
    } else false

  }

  fun setPagingEnabled(enabled: Boolean) {
    this.enabled = enabled
  }
}