package com.project.mediasearchapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferencesUtils {


    private lateinit var shared: SharedPreferences

    fun init(context: Context) {
        shared = context.getSharedPreferences("media_search_preferences", Context.MODE_PRIVATE)
    }


    fun <T> setList(key: String, value: List<T>) {
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putString(key, Gson().toJson(value)).commit()
    }

    fun getString(key: String, defaultValue: String): String? {
        return shared.getString(key, defaultValue)
    }



    private object LazyHolder {
        val INSTANCE = SharedPreferencesUtils()
    }

    companion object {

        const val KEY_FAVORITE_LIST = "key_favorite_list"

        @JvmStatic
        fun getInstance(): SharedPreferencesUtils {
            return LazyHolder.INSTANCE
        }
    }
}