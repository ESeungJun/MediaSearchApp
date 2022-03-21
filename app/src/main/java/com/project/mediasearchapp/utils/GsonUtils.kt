package com.project.mediasearchapp.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object GsonUtils {
    fun <T> convertListToJson(list: List<T>): String {
        return Gson().toJson(list)
    }

    fun <T> convertJsonToList(jsonArray: String?, clazz: Class<T>?): List<T> {
        val typeOfT: Type = TypeToken.getParameterized(MutableList::class.java, clazz).type
        return Gson().fromJson(jsonArray, typeOfT) ?: emptyList()
    }
}