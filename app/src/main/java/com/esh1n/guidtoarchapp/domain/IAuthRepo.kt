package com.esh1n.guidtoarchapp.domain

interface IAuthRepo {
    val hasAuth: Boolean
    fun updateAuth(isAuth: Boolean)
}