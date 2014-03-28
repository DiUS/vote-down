package models

object DataStore {
  //note: THIS IS BAD, NEVER EVER DO THIS
  var data: Map[String, Song] = Map()

  def upsertSong(song:Song) {
    println(s"Upserting song ${song}")
    data = data.+(song.file -> song)
  }

  def removeSong(song:Song) {
    data = data - song.name
  }

  def findSong(name:String):Option[Song] = {
    data.get(name)
  }

  def clearSongs() {
    data = Map()
  }
}
