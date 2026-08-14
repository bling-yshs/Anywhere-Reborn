package com.absinthe.anywhere_.model

import androidx.annotation.Keep
import com.absinthe.anywhere_.model.database.AnywhereEntity
import com.absinthe.anywhere_.model.database.PageEntity
import com.google.gson.annotations.SerializedName

@Keep
data class BackupBean(
  @SerializedName("anywhereList") val anywhereList: List<AnywhereEntity>,
  @SerializedName("pageList") val pageList: List<PageEntity>
)
