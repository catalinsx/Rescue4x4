package com.example.rescue4x4.more.diagnosis

import android.content.Context
import org.json.JSONObject


class DTCCodes{
    companion object{
        fun searchDtcDescription(context: Context, dtcCode: String): String? {
            val jsonString = context.assets.open("dtc-codes.json").bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val dtcArray = jsonObject.getJSONArray("dtcs")

            for (i in 0 until dtcArray.length()) {
                val dtcObject = dtcArray.getJSONObject(i)
                if (dtcObject.getString("code") == dtcCode) {
                    return dtcObject.getString("description")
                }
            }
            return null
        }
    }

}