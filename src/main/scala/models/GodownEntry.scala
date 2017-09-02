package xinlong
package models

import java.sql.Timestamp

final case class GodownEntryCreate(
    items: Seq[GodownEntryItemCreate],
    entryDate: Timestamp, 
    transactor: String,
    remark: Option[String]
) {
  def dbEntity = GodownEntryEntity(entryDate, transactor, remark)
}

final case class GodownEntryItemCreate(
    productID: Long,
    amount: Int,
    purchasePrice: Double
) {
  def dbEntity(godownEntryID: Long) = GodownEntryItem(godownEntryID, productID, amount, purchasePrice)
}

final case class CompleteGodownEntry(
    items: Seq[GodownEntryItem],
    entryDate: Timestamp, 
    transactor: String,
    remark: Option[String],
    id: Long
)