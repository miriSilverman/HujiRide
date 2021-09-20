package huji.postpc.year2021.hujiride.database

import com.google.gson.Gson
import java.io.File
import java.io.FileWriter

// From: https://he.wikipedia.org/wiki/%D7%A7%D7%98%D7%92%D7%95%D7%A8%D7%99%D7%94:%D7%99%D7%A8%D7%95%D7%A9%D7%9C%D7%99%D7%9D:_%D7%A9%D7%9B%D7%95%D7%A0%D7%95%D7%AA
val __JERUSALEM_NEIGHBORHOODS = arrayListOf(
        "א-טור",
        "אבו תור",
        "אוהל שלמה",
        "ארנונה",
        "בית הכרם",
        "בית וגן",
        "בית חנינא",
        "בית צפאפא",
        "בקעה",
        "ג'בל מוכאבר",
        "גאולה",
        "גבעת המבתר",
        "גבעת המטוס",
        "גבעת מרדכי",
        "גבעת משה",
        "גבעת משואה",
        "גבעת רם",
        "גבעת שאול",
        "גוננים",
        "גילה",
        "הגבעה הצרפתית",
        "הולילנד",
        "המושבה הגרמנית",
        "הר הצופים",
        "חומת שמואל (הר חומה)",
        "הר חוצבים",
        "הר נוף",
        "טלביה",
        "ימין משה",
        "יפה נוף",
        "מרכז העיר",
        "מגרש הרוסים",
        "מוסררה",
        "מוצא",
        "מחנה יהודה",
        "מלחה",
        "משכנות שאננים",
        "נווה יעקב",
        "ניות",
        "נחלאות",
        "סנהדריה",
        "עין כרם",
        "עיסאוויה",
        "עיר גנים",
        "פסגת זאב",
        "גוש עציון",
        "גבעת זאב",
        "מבשרת ציון",
        "פת",
        "צור באהר",
        "קטמון",
        "קלנדיה",
        "קריית היובל",
        "קריית מנחם",
        "קריית משה",
        "רוממה",
        "רחביה",
        "רמת אשכול",
        "רמת בית הכרם",
        "רמת דניה",
        "רמת שרת",
        "רסקו",
        "שועפאט",
        "שייח' ג'ראח",
        "תלפיות",
        "חוץ לעיר (מרכז)",
        "חוץ לעיר (דרום)",
        "חוץ לעיר (צפון)",
)

fun main() {
        val m = mutableMapOf<String, String>()
        __JERUSALEM_NEIGHBORHOODS.map {
                m[__JERUSALEM_NEIGHBORHOODS.indexOf(it).toString()] = it
        }
        File("${System.getProperty("user.dir")}/app/src/main/java/huji/postpc/year2021/hujiride/database/JerusalemNeighborhoods.json")
                .writeText(
                        Gson().toJson(m).toString()
                )

}