package xinlong
package models
package db

import utils.DatabaseService
import java.sql.Timestamp

trait GodownEntryEntityTable {

  protected val databaseService: DatabaseService
  import databaseService.api._

  final class GodownEntries(tag: Tag) extends Table[GodownEntryEntity](tag, "godown_entry") {
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def entryDate = column[Timestamp]("entry_date")
    def transactor = column[String]("transactor")
    def remark = column[Option[String]]("remark")
    def * = (entryDate, transactor, remark, id).mapTo[GodownEntryEntity]
  }

  protected val godownEntries = TableQuery[GodownEntries]

}