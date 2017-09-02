package xinlong
package http

import akka.http.scaladsl.server.Directives._
import services._
import utils.CorsSupport
import routes._

import scala.concurrent.ExecutionContext

class HttpService(
    productsService: ProductsService,
    godownEntriesService: GodownEntriesService)(
    implicit executionContext: ExecutionContext)
  extends CorsSupport {

  val productsRouter = new ProductsServiceRoute(productsService)
  val godownEntriesRouter = new GodownEntriesServiceRoute(godownEntriesService)

  val routes =
    corsHandler {
      productsRouter.route ~
      godownEntriesRouter.route
    }

}
