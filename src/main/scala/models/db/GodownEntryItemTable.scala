package xinlong
package models
package db

import utils.DatabaseService

trait GodownEntryItemTable {

  protected val databaseService: DatabaseService
  import databaseService.api._

  final class GodownEntryItems(tag: Tag) extends Table[GodownEntryItem](tag, "godown_entry_item") {
    def godownEntryID = column[Long]("godown_entry_id")
    def productID = column[Long]("product_id")
    def amount = column[Int]("amount")
    def purchasePrice = column[Double]("purchase_price")

    def pk = primaryKey("godown_entry_item_pk", (godownEntryID, productID))

    def * = (godownEntryID, productID, amount, purchasePrice).mapTo[GodownEntryItem]
  }

  protected val godownEntryItems = TableQuery[GodownEntryItems]

}