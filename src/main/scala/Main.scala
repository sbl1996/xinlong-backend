package xinlong

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http

import utils.{DatabaseService, Config}
import services._
import http.HttpService


object Main extends App with Config {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher
  implicit val log =  Logging(system.eventStream, "xinlong")

  val databaseService = new DatabaseService("postgres")

  val productsService = new ProductsService(databaseService)

  val godownEntriesService = new GodownEntriesService(databaseService)

  val httpService = new HttpService(productsService, godownEntriesService)

  val bindingFuture = Http().bindAndHandle(httpService.routes, host, port)

  def clear() = {
    system.terminate()
    databaseService.db.close()
  }

  bindingFuture.map { serverBinding =>
    log.info(s"RestApi bound to ${serverBinding.localAddress} ")
  }.failed.foreach {
    case ex: Exception =>
      log.error(ex, "Failed to bind to {}:{}!", host, port)
      clear()
  }
  io.StdIn.readLine() 
  bindingFuture
    .flatMap(_.unbind()) 
    .onComplete(_ => clear()) 
}