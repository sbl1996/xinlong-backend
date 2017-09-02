package xinlong
package utils

import com.typesafe.config.ConfigFactory

trait Config {
  private val config = ConfigFactory.load()
  private val httpConfig = config.getConfig("http")

  val host = httpConfig.getString("host")
  val port = httpConfig.getInt("port")
}
