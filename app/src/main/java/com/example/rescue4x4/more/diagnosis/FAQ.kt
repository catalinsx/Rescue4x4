package com.example.rescue4x4.more.diagnosis

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.InputStreamReader

data class FaqItem(
    @SerializedName("question")
    val question: String,
    @SerializedName("answer")
    val answer: String
)

data class FaqData(
    @SerializedName("faq")
    val faq: List<FaqItem>
)

class FAQ {
    companion object {
        fun deserializeFaqJson(context: Context): FaqData{
            // open the json file from assets
            val inputStream = context.assets.open("faq.json")
            // create a reader to read the json content
            val reader = InputStreamReader(inputStream)
            // deserialize the json content into a FaqData object using Gson
            // Gson is a library that helps in converting json to objects and vice versa
            val faqData = Gson().fromJson(reader, FaqData::class.java)
            reader.close()
            return faqData
        }

        fun checkAndDisplayFaq(faqData: FaqData, description: String): List<FaqItem> {
            // split the description into words
            // \\s means any whitespace character
            val words = description.split("\\s+".toRegex())
            val matchingQuestions = mutableListOf<FaqItem>()
            for (word in words){
                for (faqItem in faqData.faq){
                    if (faqItem.question.contains(word, ignoreCase = true)){
                        matchingQuestions.add(faqItem)
                    }
                }
            }
            return matchingQuestions
        }
    }
}