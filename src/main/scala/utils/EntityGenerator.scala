package xinlong
package utils

import models.ProductEntity

object EntityGenerator {

  val r = scala.util.Random

  def barcode = "65" + List.fill(12)(r.nextInt(10)).mkString

  def retailPrice = r.nextInt(1000) / 10.0

  def category = {
    val categories = Vector("副食","酒水","调料","饼干","纸品","日杂","特产","化妆品","其他")
    categories(r.nextInt(categories.length))
  }

  def product = 
    ProductEntity(
      r.nextString(10),
      retailPrice,
      barcode,
      category,
      None,
      None
    )

  def products(n: Int) = List.fill(n)(product)

}

/*

import xinlong.utils.DatabaseService
import xinlong.services.GodownEntriesService

val dbsv = new DatabaseService("postgres")
val db = dbsv.db
import dbsv.api._
import scala.concurrent.ExecutionContext.Implicits.global
val gesv = new GodownEntriesService(dbsv)
val gs = TableQuery[gesv.GodownEntries]
val gis = TableQuery[gesv.GodownEntryItems]

*/