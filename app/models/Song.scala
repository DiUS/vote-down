package models

import org.bff.javampd.objects.MPDSong

object Song {
  def apply(mpdSong: MPDSong):Song = {
    Song(mpdSong.getName, mpdSong.getFile)
  }
}

case class Song(name: String, file: String, downVotes: Set[DownVote] = Set()) {

  def voteDown(username:String) = {
    this.copy(downVotes = downVotes.+(DownVote(username)))
  }


  override def toString = {
    s"${file} with ${downVotes.map( vote => s"${vote}")}"
  }
}
