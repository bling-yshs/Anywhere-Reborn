package com.yshs.anywhere_.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yshs.anywhere_.view.app.AnywhereDialogBuilder
import com.yshs.anywhere_.view.app.AnywhereDialogFragment
import com.yshs.anywhere_.viewbuilder.entity.ImageDialogBuilder
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageDialogFragment : AnywhereDialogFragment {

  private lateinit var mBuilder: ImageDialogBuilder
  private var mUri: String? = null

  constructor()

  constructor(uri: String, listener: OnDismissListener? = null) {
    mUri = uri

    setWrapOnDismissListener(listener)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    mBuilder = ImageDialogBuilder(requireContext())
    return AnywhereDialogBuilder(requireContext()).setView(mBuilder.root).create()
  }

  override fun onStart() {
    super.onStart()
    initView()
  }

  private fun initView() {
    lifecycleScope.launch(Dispatchers.Main) {
      activity?.let {
        Glide.with(it.applicationContext)
          .load(mUri)
          .into(mBuilder.image)
      }
    }
  }
}
