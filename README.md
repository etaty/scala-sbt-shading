# scala-sbt-shading

Example of how to shade a scala library.

TL;DR; Didn't worked. Only works with Java
Once the jar is unzipped, the package is renamed, but the content in `.class` files are not renamed to the new package name.

#### Achievement

We demonstrate the use of 2 versions of [elastic4s](https://github.com/sksamuel/elastic4s) in the same project.

The 2 versions are able to access their `test` dependencies in `test`.

We run `assembly` in the shaded subproject, when `compile` or `test` is called in the parent project.  

#### Quick start

* `sbt test`
* `sbt compile`

#### Links

* https://github.com/sbt/sbt-assembly#shading
* https://github.com/wsargent/shade-with-sbt-assembly
* [shading in apache/incubator-gearpump](https://github.com/apache/incubator-gearpump/blob/b6f5ccd6ed0bb70d878c1f786da637ac4597a84f/project/BuildGearpump.scala)
