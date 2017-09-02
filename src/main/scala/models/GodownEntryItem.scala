package xinlong
package models

final case class GodownEntryItem(
    godownEntryID: Long,
    productID: Long, 
    amount: Int,
    purchasePrice: Double
)