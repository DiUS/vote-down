package controllers

import play.api.mvc.{Action, Controller}
import scala.util.parsing.json.JSONObject
import play.api.libs.json.Json
import models.DataStore

object SongController extends Song

class Song extends Controller {

  def voteDown(name:String) = Action {
    println(s"Voting down ${name}")
    DataStore.findSong(name).foreach(song => println(song.name))
    Ok(Json.obj())
  }
}
