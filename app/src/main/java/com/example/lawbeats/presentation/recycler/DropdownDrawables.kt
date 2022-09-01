package com.example.lawbeats.presentation.recycler

import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.example.lawbeats.R

enum class DropdownDrawables(val resId: Int) {
    COLLAPSED(R.drawable.ic_baseline_arrow_drop_up_24), EXPANDED(R.drawable.ic_baseline_arrow_drop_down_24);

    fun getAnimation(): Animation {
        when (this) {
            COLLAPSED -> {
                val animation = RotateAnimation(
                    180f, 0f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f
                )
                animation.duration = 500
                return animation
            }
            EXPANDED -> {
                val animation = RotateAnimation(
                    -180f, 0f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f
                )
                animation.duration = 500
                return animation
            }
        }
    }
}