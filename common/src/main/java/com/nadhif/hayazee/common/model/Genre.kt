package com.nadhif.hayazee.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    var id: Int = 0,
    var name: String? = null
): Parcelable