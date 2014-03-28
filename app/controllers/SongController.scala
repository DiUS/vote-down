package controllers

import play.api.mvc.{Action, Controller}
import scala.util.parsing.json.JSONObject
import play.api.libs.json.Json
import models.DataStore

object SongController extends Song

class Song extends Controller {

  def voteDown(name:String) = Action {
    println(s"Voting down ${DataStore.findSong(name)}")
    DataStore.findSong(name).map(_.voteDown).map(DataStore.upsertSong)
    Ok(Json.obj())
  }
}
