package xinlong
package services

import scala.concurrent.{ExecutionContext, Future}

import utils.DatabaseService

import models.db.{ProductEntityTable}
import models.{ProductEntity}

class ProductsService(val databaseService: DatabaseService)(implicit ec: ExecutionContext)
    extends ProductEntityTable {
  import databaseService._
  import databaseService.api._

  def getNProducts() = {
    val action = products.length.result
    db.run(action)
  }

  def getProducts() = {
    val action = products.result
    db.run(action)
  }

  def getProductsByConditions(sort: String, order: String, start: Int, end: Int) = {

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
  }

  def getProductByID(id: Long) = {
    val action = products.filter(_.id === id).result.headOption
    db.run(action)
  }

  def getProductByBarcode(barcode: String) = {
    val action = products.filter(_.barcode === barcode).result.headOption
    db.run(action)
  }

  def createProduct(p: ProductEntity) = {
    val action = products returning products += p
    db.run(action)
  }

  def deleteProduct(id: Long) = getProductByID(id).flatMap {
    case Some(p) =>
      db.run(products.filter(_.id === id).delete).map(_ => Some(p))
    case None => Future.successful(None)
  }

  def updateProduct(id: Long, p: ProductEntity) = {
    val action = products.filter(_.id === id).update(p)
    db.run(action)
  }
}