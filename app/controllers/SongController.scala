package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import models.DataStore

object SongController extends Song

class Song extends Controller {

  def voteDown(name:String) = Action { request =>
    println(s"Voting down ${DataStore.findSong(name)}")

    request.session.get("email") match {
      case Some(username) => DataStore.findSong(name).map{song => song.voteDown(username)}.map(DataStore.upsertSong)
      case None => None
    }
    Ok(Json.obj())
  }

}
