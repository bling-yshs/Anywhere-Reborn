package com.yshs.anywhere_.adapter.page

import com.yshs.anywhere_.AnywhereApplication
import com.yshs.anywhere_.R
import com.yshs.anywhere_.constants.AnywhereType
import com.yshs.anywhere_.model.database.AnywhereEntity
import com.yshs.anywhere_.utils.UxUtils
import com.absinthe.libraries.utils.extensions.dp
import com.blankj.utilcode.util.Utils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.chip.Chip

class ChipAdapter internal constructor(category: String) :
  BaseQuickAdapter<AnywhereEntity, BaseViewHolder>(R.layout.item_chip) {

  init {
    AnywhereApplication.sRepository.allAnywhereEntities.value?.let { list ->
      for (item in list) {
        setList(list.filter { it.category == category || (it.category.isNullOrEmpty() && category == AnywhereType.Category.DEFAULT_CATEGORY) })
      }
    }
  }

  override fun convert(holder: BaseViewHolder, item: AnywhereEntity) {
    val chip: Chip = holder.getView(R.id.chip)
    chip.apply {
      text = item.appName
      chipIcon = UxUtils.getAppIcon(Utils.getApp(), item, 16.dp)
    }
  }

  override fun getItemId(position: Int): Long {
    return try {
      data[position].id.hashCode().toLong()
    } catch (e: Exception) {
      super.getItemId(position)
    }
  }
}
