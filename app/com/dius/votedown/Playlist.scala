package com.dius.votedown

import models.Song
import org.bff.javampd.{MPD, MPDPlayer, MPDPlaylist}
import scala.collection.JavaConversions._
import org.bff.javampd.objects.MPDSong


object Playlist {
  def nextSong(justFinished:Song, playlist: List[Song]) = {
    playlist.sortBy(_.voteDown).dropWhile(_ != justFinished).tail.headOption.getOrElse(playlist.head)
  }
}

case class Playlist(mpd: MPD) {
  def player: MPDPlayer = mpd.getMPDPlayer
  def queue: MPDPlaylist = mpd.getMPDPlaylist
  def database: Iterable[MPDSong] = mpd.getMPDDatabase.listAllSongs
  
  def update(justFinished: MPDSong, 
             playlist: List[Song]) = {
    val next = Playlist.nextSong(Song(justFinished), playlist)
    database.find(_ == next).foreach { song =>
      playNext(queue, player, song)
    }
  }
  
  def playNext(queue: MPDPlaylist, player: MPDPlayer, next: MPDSong) {
    queue.clearPlaylist()
    queue.addSong(next)
    player.play()
  }
}