import org.scalatest.{ Matchers, WordSpec }
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._

import xinlong.utils.DatabaseService

trait ProductEntityTestTable {

  protected val databaseService: DatabaseService
  import databaseService.api._

  final class Products(tag: Tag) extends Table[ProductEntity](tag, "test_product") {
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def retailPrice = column[Double]("retail_price")
    def barcode = column[String]("barcode")
    def category = column[String]("category")
    def specification = column[Option[String]]("specification")
    def * = (name, retailPrice, barcode, category, specification, id).mapTo[ProductEntity]
  }

  protected val products = TableQuery[Products]

}

class ProductsServiceRouteSpec
    extends WordSpec
    with Matchers
    with ScalatestRouteTest
    with ProductEntityTestTable {

  val databaseService = new DatabaseService("postgres")

  
}