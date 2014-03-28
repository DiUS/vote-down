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

  def manage = Action { request =>
    val songPromise = Promise[PlaylistBasicChangeEvent]()

    monitor.addPlaylistChangeListener(new PlaylistBasicChangeListener {
      def playlistBasicChange(event: PlaylistBasicChangeEvent) = {
        if(event.getId == PlaylistBasicChangeEvent.PLAYLIST_ENDED) {
          songPromise.success(event)
        }
      }
    })

    songPromise.future.onComplete { e =>
      println("finished, playing next")
      val justFinished = playlist.getSongList.head
      val allSongs = DataStore.data.values
      Playlist(mpd).update(justFinished, allSongs)
    } (Implicits.defaultContext)

    player.stop()
    playlist.clearPlaylist()
    playlist.addSong(database.listAllSongs().head)
    player.play()

    Ok("woo")
  }
}
