package xinlong
package services

import scala.concurrent.{ExecutionContext, Future}

import utils.DatabaseService

import models.db.{GodownEntryEntityTable, GodownEntryItemTable}
import models.{GodownEntryEntity, GodownEntryCreate, GodownEntryItemCreate}

class GodownEntriesService(val databaseService: DatabaseService)(implicit ec: ExecutionContext)
  extends GodownEntryEntityTable
  with GodownEntryItemTable {
  import databaseService._
  import databaseService.api._

  def getNGodownEntries() = {
    val action = godownEntries.length.result
    db.run(action)
  }

  def getGodownEntries() = {
    val action = godownEntries.result
    db.run(action)
  }

  /*def getProductsByConditions(sort: String, order: String, start: Int, end: Int) = {

    val action = (sort match {
      case "id" =>
        order match {
          case "ASC" => products.sortBy(_.id.asc)
          case "DESC" => products.sortBy(_.id.asc)
        } 
      case "name" => 
        order match {
          case "ASC" => products.sortBy(_.name.asc)
          case "DESC" => products.sortBy(_.name.asc)
        }
      case "retailPrice" =>
        order match {
          case "ASC" => products.sortBy(_.retailPrice.asc)
          case "DESC" => products.sortBy(_.retailPrice.asc)
        }
      case "category" =>
        order match {
          case "ASC" => products.sortBy(_.category.asc)
          case "DESC" => products.sortBy(_.category.asc)
        }
      case "barcode" =>
        order match {
          case "ASC" => products.sortBy(_.barcode.asc)
          case "DESC" => products.sortBy(_.barcode.asc)
        }
    }).take(end).drop(start).result

    db.run(action)
  }*/

  def getGodownEntryByID(id: Long) = {
    val action = godownEntries.filter(_.id === id).result.headOption
    db.run(action)
  }

  def getGodownEntryItemsByID(godownEntryID: Long) = {
    val action = godownEntryItems.filter(_.godownEntryID === godownEntryID).result
    db.run(action)
  }


  def createGodownEntry(c: GodownEntryCreate) = {
    val action = godownEntries returning godownEntries += c.dbEntity
    db.run(action)
  }

  def createGodownEntryItems(godownEntryID: Long, items: Seq[GodownEntryItemCreate]) = {
    val dbEntities = items.map(_.dbEntity(godownEntryID))
    val action = godownEntryItems ++= dbEntities
    db.run(action).map(_ => dbEntities)
  }

}