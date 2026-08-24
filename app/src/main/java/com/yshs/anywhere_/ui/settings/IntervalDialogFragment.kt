package com.yshs.anywhere_.ui.settings

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import com.yshs.anywhere_.R
import com.yshs.anywhere_.constants.GlobalValues
import com.yshs.anywhere_.view.app.AnywhereDialogBuilder
import com.yshs.anywhere_.view.app.AnywhereDialogFragment
import com.yshs.anywhere_.viewbuilder.entity.IntervalDialogBuilder

class IntervalDialogFragment : AnywhereDialogFragment() {

    private lateinit var mBuilder: IntervalDialogBuilder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBuilder = IntervalDialogBuilder(requireContext()).apply {
            slider.value = when {
                GlobalValues.dumpInterval / 1000f > slider.valueTo -> {
                    slider.valueTo
                }
                GlobalValues.dumpInterval / 1000f < slider.valueFrom -> {
                    slider.valueFrom
                }
                else -> {
                    GlobalValues.dumpInterval / 1000f
                }
            }
        }

        return AnywhereDialogBuilder(requireContext()).setView(mBuilder.root)
                .setTitle(R.string.dialog_set_interval_title)
                .setPositiveButton(R.string.dialog_delete_positive_button) { _: DialogInterface?, _: Int ->
                    GlobalValues.dumpInterval = mBuilder.slider.value.toInt() * 1000
                }
                .setNegativeButton(android.R.string.cancel, null)
                .create()
    }
}