import cats.effect.IO
import org.example.game.entities.Game
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

class GameApi extends Http4sDsl[IO]{

  val routes = HttpRoutes.of[IO]{
    
    //req @ binds the whole HTTP request object to the variable req
    case post @ POST -> Root / "play"  =>
      
      //IO has  map and flatMap, so you can use for-comprehension

      for{
        text <- post.as[String]
        game = Game(text).result
        response <- Ok(game)
      } yield response
    /*
    this is equivalent to this code:
    val ioString: IO[String] =
        post.as[String]
      val ioResponse = ioString.flatMap { text =>
        Ok(Game(text).result)
      }
      ioResponse
    * */  
  }
}
