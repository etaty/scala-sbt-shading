import com.sksamuel.elastic4s.Years
import cats.{implicits => imp1}
import cats6.{implicits => imp6 }

//import elastic4s6.com.sksamuel.elastic4s.{Years => Years6 }

object Example extends App {
  val y = Years.symbol
  val y6 = Years.symbol
  println(imp1.catsContravariantForHash)
  println(imp6.catsContravariantForHash)
  println(s"runnning ... test => $y & $y6")
}
