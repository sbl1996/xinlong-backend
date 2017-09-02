package xinlong
package models

import java.sql.Timestamp

final case class GodownEntryEntity(
    entryDate: Timestamp, 
    transactor: String,
    remark: Option[String],
    id: Option[Long] = None
) {
  def withItems(items: Seq[GodownEntryItem]) = {
    require(items.forall(_.godownEntryID == id.get), "items must belong to the godown entry")
    CompleteGodownEntry(items, entryDate, transactor, remark, id.get)
  }
}