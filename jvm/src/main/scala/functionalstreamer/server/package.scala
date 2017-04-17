package functionalstreamer

import com.sun.net.httpserver.{HttpHandler, HttpExchange}

package object server {
  type PartialHandler = PartialFunction[HttpExchange, Response]
  type TotalHandler   = HttpExchange => Response
  type Path           = String

  // Converters between the native API types and the overlay types we defined
  object -> {
    def unapply(exchange: HttpExchange): Option[(Method, Path)] =
      exchange.getRequestMethod.toMethod.map { _ -> exchange.getRequestURI.getPath }
  }

  implicit class MethodString(str: String) {
    def toMethod: Option[Method] = str.toLowerCase match {
      case "get"  => Some(GET)
      case "post" => Some(POST)
      case _      => None
    }
  }
}
