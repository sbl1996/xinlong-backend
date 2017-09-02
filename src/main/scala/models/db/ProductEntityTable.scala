package xinlong
package models
package db

import utils.DatabaseService

trait ProductEntityTable {

  protected val databaseService: DatabaseService
  import databaseService.api._

  final class Products(tag: Tag) extends Table[ProductEntity](tag, "product") {
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