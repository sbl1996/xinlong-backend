package xinlong
package models

final case class ProductEntity(
    name: String, 
    retailPrice: Double,
    barcode: String,
    category: String,
    specification: Option[String] = None,
    id: Option[Long] = None
)