package com.yshs.anywhere_.utils.manager

import androidx.annotation.Keep

@Keep
object IzukoHelper {

  init {
    System.loadLibrary("izuko")
  }

  val cipherKey: String
    external get

}
