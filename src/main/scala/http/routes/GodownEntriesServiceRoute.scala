package xinlong
package http
package routes

import akka.http.scaladsl.model.{StatusCodes, headers}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.LongNumber
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import models.{GodownEntryEntity, GodownEntryCreate}
import services.GodownEntriesService
import utils.MyJsonProtocol._

import scala.concurrent.ExecutionContext

class GodownEntriesServiceRoute(
    godownEntriesService: GodownEntriesService)(
    implicit executionContext: ExecutionContext) {

  import StatusCodes._
  import godownEntriesService._

  val route = pathPrefix("godown-entries") {
    pathPrefix(LongNumber) { id =>
      pathEndOrSingleSlash {
        get {
          onSuccess(getGodownEntryByID(id)) {
            case Some(ge) =>
              onSuccess(getGodownEntryItemsByID(id)) { items =>
                complete(StatusCodes.OK, ge.withItems(items))
              }
            case None => complete(StatusCodes.NotFound)
          }
        } ~
        put {
          entity(as[GodownEntryEntity]) { ge =>
            onSuccess(updateGodownEntry(id, ge)) { _ =>
              complete(StatusCodes.OK, ge)
            }
          }
        } ~
        delete {
          onSuccess(deleteGodownEntry(id)) { ge =>
            complete(StatusCodes.OK, ge)
          }
        }
      }
    } ~
    pathEndOrSingleSlash {
      get {
        onSuccess(getGodownEntries()) { godownEntries =>
          onSuccess(getNGodownEntries()) { n =>
            respondWithHeader(headers.RawHeader("X-Total-Count", s"${n}")) {
              complete(StatusCodes.OK, godownEntries)
            }
          }
        }
      } ~
      post {
        entity(as[GodownEntryCreate]) { c =>
          onSuccess(createGodownEntry(c)) { ge =>
            onSuccess(createGodownEntryItems(ge.id.get, c.items)) { items =>
              complete(StatusCodes.OK, ge.withItems(items))
            }
          }
        }
      }
    }
  }
    
}