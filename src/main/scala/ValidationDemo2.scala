import scalaz._
import scalaz.syntax.apply._
import scalaz.std.string._
import scalaz.syntax.either._
import scalaz.syntax.validation._
import syntax.std.option._

object ValidationDemo2 {

  def main(args: Array[String]): Unit = {
    val e = "error".failure[Int]
    val s = 3.success[String]
    //e | 2  = getOrelse
    println(e)
    println(s)
    println(e.getOrElse(5))
    println(s.getOrElse(5))
    println(e | 2)
    println(e.fold(_ => "ji", _ * 2))
    println(s.fold(_ => "", _ * 2))
    println(s.toOption)
    println(s.map(_ * 20))
    println(e.map(_ * 20))
    val e2 = Validation.failure[String, Int]("error2")
    val s2 = Validation.success[String, Int](2)
    println(e2)
    println(s2)
    //----------------------------------
    //Combine validation errors
    println((s |@| e) {
      _ + _
    })
    println((s |@| s2) {
      _ * _
    })
    println((e |@| e2) {
      _ + _
    })
    println(e.toValidationNel)
    println((e.toValidationNel |@| e.toValidationNel) {
      _ + _
    })
    //-------------------------------------
    val s3 = 4.right[String].validation.disjunction
    val s4 = 8.right[String].validation.disjunction
    val e3 = "error3".left[String].validation.disjunction
    println(s3)
    println(s4)
    println((s3 |@| s4) {
      _ + _
    })
    println(e3)
    //---------------------------------------
    //Validation NEL
    val s5 = "ok5".successNel[String]
    val s55 = "ok".success.toValidationNel
    val s555 = "ok".right.validation.toValidationNel
    println(s5 + " " + s55 + "  " + s555)
    val s6 = "ok6".successNel[String]
    val e4 = "error4".failureNel[String]
    val e5 ="error5".failureNel[String]
    println(e4 +++ s5 +++ e5)
    println(s6 +++ s5)
    //-------------------------------------------
    //Option with validation
    val op = Some(10).toSuccess("meesage")
    val op2 = None.toSuccess("meesage")

  }

}