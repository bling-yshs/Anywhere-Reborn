package com.yshs.anywhere_.adapter.page

import com.chad.library.adapter.base.entity.node.BaseNode

class PageNode : BaseNode() {

  var title: String = "Page"

  override val childNode: MutableList<BaseNode>?
    get() = null
}
