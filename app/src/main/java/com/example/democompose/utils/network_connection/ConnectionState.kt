package com.example.democompose.utils.network_connection

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
