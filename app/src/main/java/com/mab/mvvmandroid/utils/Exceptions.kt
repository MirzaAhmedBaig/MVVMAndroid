package com.mab.mvvmandroid.utils

import java.io.IOException

class ApiExceptions(message: String?, val code: Int?) : IOException(message)