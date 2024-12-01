import day2.Color
import day2.Record

//" 2 green, 8 blue, 5 red; 3 blue, 9 red; ..."
fun extractSelections(selections: String): List<String> {
    return selections.split(";")
}


//" x red, y green, ..."
fun selectionToRecords(recordString: String): List<Record> {
    return recordString.split(",").map {
        val strings = it.split(" ").drop(1)
        Record(strings[0].toInt(), Color.valueOf(strings[1]))
    }
}
