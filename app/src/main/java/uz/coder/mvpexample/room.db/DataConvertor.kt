package uz.coder.mvpexample.room.dbimport androidx.room.TypeConverterimport com.google.gson.Gsonimport com.google.gson.reflect.TypeTokenimport uz.coder.mvpexample.models.books.books.BuyLinkclass DataConvertor {    @TypeConverter    fun fromString(str: String): List<BuyLink> {        val list = object : TypeToken<List<BuyLink>>() {}.type        return Gson().fromJson(str, list)    }    @TypeConverter    fun fromList(list: List<BuyLink>): String {        val gson = Gson()        return gson.toJson(list)    }}