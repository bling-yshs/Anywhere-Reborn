package com.yshs.anywhere_.model

import androidx.annotation.Keep
import com.yshs.anywhere_.model.database.AnywhereEntity
import com.yshs.anywhere_.model.database.PageEntity
import com.google.gson.annotations.SerializedName

@Keep
data class BackupBean(
  @SerializedName("anywhereList") val anywhereList: List<AnywhereEntity>,
  @SerializedName("pageList") val pageList: List<PageEntity>
)
