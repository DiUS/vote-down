package models

import org.bff.javampd.objects.MPDSong

object Song {
  def apply(mpdSong: MPDSong):Song = {
    Song(mpdSong.getName, mpdSong.getFile)
  }
}

case class Song(name: String, file: String, downVotesCount: Int = 0) {

  def voteDown = {
    this.copy(downVotesCount = downVotesCount + 1)
  }

}
