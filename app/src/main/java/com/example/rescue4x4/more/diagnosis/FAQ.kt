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
            val inputStream = context.assets.open("faq.json")
            val reader = InputStreamReader(inputStream)
            val faqData = Gson().fromJson(reader, FaqData::class.java)
            reader.close()
            return faqData
        }

        fun checkAndDisplayFaq(faqData: FaqData, description: String): List<FaqItem> {
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