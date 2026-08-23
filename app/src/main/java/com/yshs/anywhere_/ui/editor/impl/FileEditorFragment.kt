package com.yshs.anywhere_.ui.editor.impl

import android.content.ActivityNotFoundException
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.yshs.anywhere_.AnywhereApplication
import com.yshs.anywhere_.R
import com.yshs.anywhere_.constants.GlobalValues
import com.yshs.anywhere_.databinding.EditorFileBinding
import com.yshs.anywhere_.ui.editor.BaseEditorFragment
import com.yshs.anywhere_.ui.editor.EditorActivity
import com.yshs.anywhere_.utils.AppUtils
import com.yshs.anywhere_.utils.ShortcutsUtils
import com.yshs.anywhere_.utils.ToastUtil

class FileEditorFragment : BaseEditorFragment() {

  private lateinit var binding: EditorFileBinding

  override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): View {
    binding = EditorFileBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun initView() {
    binding.tilUrl.isEnabled = false

    binding.tietAppName.setText(item.appName)
    binding.tietDescription.setText(item.description)

    binding.btnSelectFile.setOnClickListener {
      try {
        (requireContext() as EditorActivity).setDocumentResult("*/*") {
          binding.tietUrl.setText(it.toString())
        }
      } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        ToastUtil.makeText(R.string.toast_no_document_app)
      }
    }

    if (isEditMode) {
      binding.tietUrl.setText(item.param1)
    }
    requireActivity().invalidateOptionsMenu()
  }

  override fun tryRunning() {}

  override fun doneEdit(): Boolean {
    if (binding.tietAppName.text.isNullOrBlank()) {
      binding.tilAppName.error = getString(R.string.bsd_error_should_not_empty)
      return false
    }
    if (binding.tietUrl.text.isNullOrBlank()) {
      binding.tilUrl.error = getString(R.string.bsd_error_should_not_empty)
      return false
    }

    doneItem = item.copy().apply {
      appName = binding.tietAppName.text.toString()
      param1 = binding.tietUrl.text.toString()
      description = binding.tietDescription.text.toString()
    }

    if (super.doneEdit()) return true
    if (isEditMode && doneItem == item) return true

    if (isEditMode) {
      if (doneItem.appName != item.appName || doneItem.param1 != item.param1) {
        if (GlobalValues.shortcutsList.contains(doneItem.id)) {
          if (AppUtils.atLeastNMR1()) {
            ShortcutsUtils.updateShortcut(doneItem)
          }
        }
      }
      AnywhereApplication.sRepository.update(doneItem)
    } else {
      doneItem.id = System.currentTimeMillis().toString()
      AnywhereApplication.sRepository.insert(doneItem)
    }

    return true
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    menu.findItem(R.id.trying_run).isVisible = false
    super.onPrepareOptionsMenu(menu)
  }
}
