package models

object DataStore {
  //note: THIS IS BAD, NEVER EVER DO THIS
  var data: Map[String, Song] = Map()

  def addSong(s:Song) {
    data = data.+(s.name -> s)
  }

  def removeSong(s:Song) {
    data = data - s.name
  }
}
