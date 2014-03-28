package models
import play.api.test.PlaySpecification

class DataStoreTest extends PlaySpecification {
  "findSong" should {

    DataStore.clearSongs()
    val song = new Song("Smells like teen spirit")
    DataStore.addSong(song)

    "test a thing" in {
      DataStore.findSong("Smells like teen spirit") must beSome(song)
    }
  }

}
