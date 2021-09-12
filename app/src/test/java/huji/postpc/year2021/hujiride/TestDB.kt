package huji.postpc.year2021.hujiride

import huji.postpc.year2021.hujiride.database.Database
import org.junit.Test

class TestDB {
    val db : Database = Database()

    @Test
    fun clientTest() {
        db.newClient("TEST", "Test", "000", "TEST1234")
    }

}