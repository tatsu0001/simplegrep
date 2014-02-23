package tools

import scopt._
import scala.io.Source

import java.io.File
import java.util.regex.Pattern

case class GrepOpts(
  pattern: Pattern = null,
  file: File = null,
  matchCount: Boolean = false,
  encoding: String = "UTF-8"
)

object SimpleGrep {
  private val cmdName: String = "simplegrep"

  private def createOptParser(): OptionParser[GrepOpts] = {
    new OptionParser[GrepOpts](cmdName) {
      head(cmdName, "0.1")
      opt[File]('f', "file")
        .required()
        .action { (f, opt) => opt.copy(file = f) }
      opt[String]('p', "pattern")
        .required()
        .action { (p, opt) => opt.copy(pattern = Pattern.compile(p)) }
      opt[String]('e', "encoding")
        .optional() 
        .action { (e, opt) => opt.copy(encoding = e) }
      opt[Unit]('c', "count")
        .optional() 
        .action { (c, opt) => opt.copy(matchCount = true) }
    }
  }

  private def grep(opt: GrepOpts): List[String] = {
    val src = Source.fromFile(opt.file, opt.encoding)
    val pattern = (line: String) => {
      opt.pattern.matcher(line).find
    }
    src.getLines().toList.filter(pattern)
  }

  def main(args: Array[String]): Unit = {
    val parser = createOptParser()
    parser.parse(args, GrepOpts()).map { opt => 
      val result: List[String] = grep(opt)

      result.foreach(println)
      if (opt.matchCount) {
        println("pattern matches count:" + result.size)
      }
    }
  }
}
