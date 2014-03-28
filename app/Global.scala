import helper.MpdMonitor
import models.{Song, DataStore}
import play.api.GlobalSettings
import play.api.Application
import scala.collection.JavaConversions._
/**
 * Created by Beth on 28/03/2014.
 */

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    println("Starting application")
    MpdMonitor.getInstance.getMPD.getMPDDatabase.listAllSongs.map(Song.apply).foreach(DataStore.addSong)
  }

  override def onStop(app: Application) {
    println("Stopping application")
    MpdMonitor.getInstance.stop
  }

}
