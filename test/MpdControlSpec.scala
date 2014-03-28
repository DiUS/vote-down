import helper.MpdMonitor
import org.bff.javampd.events._
import org.bff.javampd.objects.MPDSong
import play.api.libs.concurrent.Execution.Implicits
import play.api.test.{WithApplication, PlaySpecification}
import scala.collection.JavaConversions._
import scala.concurrent.duration._
import scala.concurrent.Promise

class MpdControlSpec extends PlaySpecification {
  "MPC controller" should {

    def mpd = MpdMonitor.getInstance.getMPD

    def player = mpd.getMPDPlayer

    def playlist = mpd.getMPDPlaylist

    def database = mpd.getMPDDatabase

    def monitor = MpdMonitor.getInstance.getMonitor

    def song(from: MPDSong): (Int, String) = {
      (from.getId, from.getName)
    }

//    "keep the current song on top of the playlist" in new WithApplication {
//      playlist.addSongs(database.listAllSongs)
//
//      player.play()
//      player.playNext()
//
////      player.getCurrentSong must beEqualTo(playlist.getSongList.head)
//
//      player.stop()
//      player.getStatus must beEqualTo(PlayerStatus.STATUS_STOPPED)
//    }

    "notifywhen song ends" in new WithApplication {
      playlist.clearPlaylist()
      playlist.addSong(database.listAllSongs.head)
      player.setRepeat(false)

      player.play()

      val stopper = Promise[PlaylistBasicChangeEvent]()

      monitor.addPlaylistChangeListener(new PlaylistBasicChangeListener {
        def playlistBasicChange(event: PlaylistBasicChangeEvent) = {
          if(event.getId == PlaylistBasicChangeEvent.PLAYLIST_ENDED) {
            stopper.success(event)
          }
        }
      })

      val msg = stopper.future.map(_.getMsg)(Implicits.defaultContext)
      val timeout = FiniteDuration(10, "s")
      msg must beEqualTo("1.mp3").await(0, timeout)
    }

//    "stop music" in new WithApplication {
//      playlist.addSongs(database.listAllSongs)
//
//      player.play()
//      player.playNext()
//
//      player.getStatus must beEqualTo(PlayerStatus.STATUS_PLAYING)
//
//      Thread.sleep(2000)
//
//      println("\n\n now playing: ")
//      println(song(player.getCurrentSong))
//
//      println("\n\nplaylist")
//      println(playlist.getSongList.map(song).mkString("\n"))
//
//      playlist.clearPlaylist()
//
//      player.getStatus must beEqualTo(PlayerStatus.STATUS_PLAYING)
//      playlist.getSongList.isEmpty must beTrue
//
//      println("\n\ndatabase")
//      println(database.listAllSongs.map(song).mkString("\n"))
//
//      player.stop()
//      player.getStatus must beEqualTo(PlayerStatus.STATUS_STOPPED)
//    }
  }
}
