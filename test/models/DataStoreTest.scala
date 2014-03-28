package models
import play.api.test.PlaySpecification

class DataStoreTest extends PlaySpecification {
  "findSong" should {

    DataStore.clearSongs()
    val song = new Song("Smells like teen spirit", "Smells like teen spirit.mp3")
    DataStore.upsertSong(song)

    "test a thing" in {
      DataStore.findSong("Smells like teen spirit.mp3") must beSome(song)
    }
  }

}
