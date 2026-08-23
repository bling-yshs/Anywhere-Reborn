package com.yshs.anywhere_.adapter.a11y.bean

import com.yshs.anywhere_.a11y.A11yActionBean
import com.chad.library.adapter.base.entity.node.BaseNode

open class A11yBaseBean(val actionBean: A11yActionBean) : BaseNode() {
  override val childNode: MutableList<BaseNode>? = null
}
