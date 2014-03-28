package models

import org.bff.javampd.objects.MPDSong

object Song {
  def apply(mpdSong: MPDSong):Song = {
    Song(mpdSong.getName)
  }
}

case class Song(name: String) {
  def downVotesCount: Int = ???
}
