package util

fun lines(filename: String): List<String> {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream(filename)!!.bufferedReader()
    return file.useLines { it.toList() }
}
