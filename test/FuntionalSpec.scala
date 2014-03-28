import play.api.libs.ws.WS
import play.api.test.{WithServer, PlaySpecification}

/**
 * Created by dallas on 28/03/2014.
 */
class FuntionalSpec extends PlaySpecification {

  "Redirect Homepage" should {
    "redirect to '/playlist/' " in new WithServer{
      val result = WS.url(s"http://localhost:$port").get
      val status = result.map(r => r.statusText)(play.api.libs.concurrent.Execution.Implicits.defaultContext)
      val body = result.map(r => r.body)(play.api.libs.concurrent.Execution.Implicits.defaultContext)

      status must beEqualTo("OK").await
      body must contain("Play! MPC").await
    }
  }

    "Playlist" should {
      "list music" in new WithServer {
        val result = WS.url(s"http://localhost:$port/playlist").withQueryString("p" -> "0").get.map(r => r.status)(play.api.libs.concurrent.Execution.Implicits.defaultContext)


        result must beEqualTo(OK).await

      }
    }


}
