package xinlong
package http
package routes

import akka.http.scaladsl.model.{StatusCodes, headers}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.LongNumber
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import models.ProductEntity
import services.ProductsService
import utils.MyJsonProtocol._

import scala.concurrent.ExecutionContext

class ProductsServiceRoute(
    productsService: ProductsService)(
    implicit executionContext: ExecutionContext) {

  import StatusCodes._
  import productsService._

  val route = pathPrefix("products") {
    pathPrefix(LongNumber) { id =>
      pathEndOrSingleSlash {
        get {
          onSuccess(getProductByID(id)) { p =>
            complete(StatusCodes.OK, p)
          }
        } ~
        put {
          entity(as[ProductEntity]) { p =>
            onSuccess(updateProduct(id, p)) { _ =>
              complete(StatusCodes.OK, p)
            }
          }
        } ~
        delete {
          val a = deleteProduct(id)
          onSuccess(a) { p =>
            complete(StatusCodes.OK, p)
          }
        }
      }
    } ~
    pathEndOrSingleSlash {
      get {
        parameter("barcode") { barcode =>
          onSuccess(getProductByBarcode(barcode)) {
            case Some(p) =>
              complete(StatusCodes.OK, p)
            case None =>
              complete(StatusCodes.NotFound)
          }
        } ~
        parameters(('_sort ? "id", '_order ? "ASC", '_start ? 0, '_end ? 10)) { (sort, order, start, end) =>
          onSuccess(getProductsByConditions(sort, order, start, end)) { products =>
            onSuccess(getNProducts()) { n =>
              respondWithHeader(headers.RawHeader("X-Total-Count", s"${n}")) {
                complete(StatusCodes.OK, products)
              }
            }
          }
        }
      } ~
      post {
        entity(as[ProductEntity]) { p =>
          onSuccess(createProduct(p)) { p =>
            complete(StatusCodes.Created, p)
          }
        }
      }
    }
  }
    
}