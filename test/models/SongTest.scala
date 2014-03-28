package models

import play.api.test.PlaySpecification

/**
 * Created by Beth on 28/03/2014.
 */
class SongTest extends PlaySpecification {

  "adding a vote" should {

    val user = "Bob the Grumpy Builder"
    val song = Song("Singalong", "Singalong").voteDown(user)

    "is recorded" in {
      song.downVotesCount must beEqualTo(1)
    }

    "can only be done once per user" in {
      song.voteDown(user).downVotesCount must beEqualTo(1)
      song.voteDown("Grumpy the Dwarf").downVotesCount must beEqualTo(2)
    }
  }
}
