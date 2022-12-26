package com.example.androidbooknomy.utils

fun isPhoneNumberValid(phoneNumber: String): Boolean {
    return phoneNumber.count() == 13 && phoneNumber.first() == '+'
}