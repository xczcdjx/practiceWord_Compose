package com.xczcdjx.word.utils

object calcFun {
    fun getRate(f: Int, e: Int):String=if (e==0) "0" else String.format("%.2f", f.toFloat() / e * 100)
}