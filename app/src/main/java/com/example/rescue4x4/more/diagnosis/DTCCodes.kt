package com.example.rescue4x4.more.diagnosis

import android.content.Context
import org.json.JSONObject


class DTCCodes{
    // companion object is used to create and provide static methods and properties
    companion object{
        // this is a method to search for the description of a DTC code
        fun searchDtcDescription(context: Context, dtcCode: String): String? {
            // read the contents of the dtc-codes.json file from assets
            val jsonString = context.assets.open("dtc-codes.json").bufferedReader().use { it.readText() }
            // convert the json string into a JSONObject
            val jsonObject = JSONObject(jsonString)
            // get the json array containg the dtc codes
            val dtcArray = jsonObject.getJSONArray("dtcs")

            // iterate over the dtc codes and find the description of the given dtc code
            for (i in 0 until dtcArray.length()) {
                val dtcObject = dtcArray.getJSONObject(i)
                // check if the code of the dtc object is equal to the given dtc code
                if (dtcObject.getString("code") == dtcCode) {
                    // return the description of the dtc code if found
                    return dtcObject.getString("description")
                }
            }
            return null
        }
    }

}