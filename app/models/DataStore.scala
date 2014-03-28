package models

object DataStore {
  //note: THIS IS BAD, NEVER EVER DO THIS
  var data: Map[String, Song] = Map()

  def addSong(song:Song) {
    data = data.+(song.name -> song)
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
