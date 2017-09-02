package xinlong
package utils

class DatabaseService(dbConfigName: String) {
  val api = slick.jdbc.PostgresProfile.api
  import api._
  val db = Database.forConfig(dbConfigName)
}