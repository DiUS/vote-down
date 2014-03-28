package models

object DataStore {
  var data: Map[String, Song] = Map()

  def addSong(s:Song) {
    data = data.+(s.name -> s)
  }

  def removeSong(s:Song) {
    data = data - s.name
  }
}
