package com.nschirmer.heroes_list.list.viewpager

import androidx.viewpager.widget.ViewPager
import android.view.View
import kotlin.math.abs
import kotlin.math.max


/**
 * [ViewPager] Transformation animation with parallax effect between pages.
 * **/
internal class PageParallaxTransformer: ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 1f
                }
                position <= 1 -> { // [-1,1]
                    val scaleFactor = max(0.5f, 1 - abs(position))

                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2

                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 40
                    } else {
                        horzMargin + vertMargin / 120
                    }
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 1f
                }
            }
        }
    }


}