package models

/**
 * Created by Beth on 28/03/2014.
 */
case class DownVote(username: String) {

  override def toString = {
    s"Vote down by ${username}"
  }

}
