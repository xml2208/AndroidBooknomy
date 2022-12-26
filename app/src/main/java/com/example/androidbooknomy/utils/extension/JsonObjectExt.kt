package com.example.androidbooknomy.utils.extension

import com.example.androidbooknomy.model.BookModel
import org.json.JSONArray

fun JSONArray.toBooks(): ArrayList<BookModel> {
    val jsonArray = this
    val items = ArrayList<BookModel>(jsonArray.length())
    for (i in 0 until jsonArray.length()) {
        val item = jsonArray.getJSONObject(i)
        items.add(
            BookModel(
                title = item.getString("title"),
                content = item.getString("content"),
                price = item.getString("price"),
                salePrice = item.getString("sale_price"),
                salePercent = item.getString("sale_percent"),
                economyPrice = item.getString("economy_price"),
                photo = item.getString("photo"),
            )
        )
    }
    return items
}
