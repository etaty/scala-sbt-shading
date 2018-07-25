import org.specs2.mutable.Specification
import com.sksamuel.elastic4s.http.cluster.ClusterHealthResponse
import elastic4s6.com.sksamuel.elastic4s.http.cluster.{ClusterHealthResponse => CH }

import com.sksamuel.elastic4s.testkit.ResponseConverterImplicits
import elastic4s6.com.sksamuel.elastic4s.testkit.{ResponseConverterImplicits => RCI }

class ExampleSpec extends Specification {
  "ExampleSpec" should {
    "compile" in {
      ok
    }
  }
}