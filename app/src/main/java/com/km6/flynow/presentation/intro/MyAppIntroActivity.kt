package com.km6.flynow.presentation.intro

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment.Companion.newInstance
import com.km6.flynow.R

class MyAppIntroActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(newInstance(R.layout.intro_custom_layout_one))
        addSlide(newInstance(R.layout.intro_custom_layout_two))
        addSlide(newInstance(R.layout.intro_custom_layout_three))
        showStatusBar(true)
        setStatusBarColorRes(R.color.md_theme_primary)
        setNavBarColorRes(R.color.md_theme_primary)
        setIndicatorColor(Color.BLACK, Color.GRAY)

        setColorDoneText(Color.BLACK)
        setColorSkipButton(Color.BLACK)
        setNextArrowColor(Color.BLACK)
    }

    public override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    public override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
    }
}
