package com.nadhif.hayazee.network.constant

object Constant {
    const val HEADER_ACCEPT = "Accept"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_APP_JSON = "application/json"

    const val DEFAULT_TIMEOUT_IN_SECOND: Long = 15

    object HiltNamed {
        const val HEADER_INTERCEPTOR = "header_interceptor"
        const val NETWORK_INTERCEPTOR = "network_interceptor"
    }
}