package controllers

import play.api.mvc.{Controller, Action}
import scala.concurrent.Promise
import org.bff.javampd.events.{PlaylistBasicChangeListener, PlaylistBasicChangeEvent}
import helper.MpdMonitor
import scala.collection.JavaConversions._
import models.DataStore
import com.dius.votedown.Playlist
import play.api.libs.concurrent.Execution.Implicits

object PlaylistController extends Controller {
  def mpd = MpdMonitor.getInstance.getMPD

  def player = mpd.getMPDPlayer

  def playlist = mpd.getMPDPlaylist

  def database = mpd.getMPDDatabase

  def monitor = MpdMonitor.getInstance.getMonitor

  def next(e: PlaylistBasicChangeEvent) {
    println("finished, playing next")
    val justFinished = playlist.getSongList.head
    val allSongs = DataStore.data.values
    Playlist(mpd).update(justFinished, allSongs)
  }

  def manage = Action { request =>

    monitor.addPlaylistChangeListener(new PlaylistBasicChangeListener {
      def playlistBasicChange(event: PlaylistBasicChangeEvent) = {
        println(s"playlist event:${event.getId}")
        if(event.getId == PlaylistBasicChangeEvent.PLAYLIST_ENDED) {
          next(event)
        }
      }
    })

    player.stop()
    playlist.clearPlaylist()
    playlist.addSong(database.listAllSongs().head)
    player.play()

    Ok("woo")
  }
}
