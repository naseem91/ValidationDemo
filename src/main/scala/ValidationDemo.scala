import scala.util.{Failure, Success, Try}
import scalaz.syntax.validation._
import scalaz.{Applicative, NonEmptyList, Validation}

object ValidationDemo {

  //Return type X or non empty list of strings in case of error messages
  type ValidOrErrorStrings[X] = Validation[NonEmptyList[String], X]
  //----------------------------------------------------
  //Convert a list of String to Integer
  //----------------------------------------------------

  def toInteger(maybeInt: List[String]): Try[List[Int]] = {
    Try(maybeInt map (_.toInt))
  }

  val successCase: Try[List[Int]] = toInteger(List("1", "2", "3"))
  val failureCase: Try[List[Int]] = toInteger(List("n", "8", "1"))

  successCase match {
    case Success(a) => println("Success Case =>" + successCase)
    case Failure(ex) => println(ex)
  }

  failureCase match {
    case Failure(ex) => println("Failure Case => "+ex)
  }

  println()
  //-------------Use Scala-z validation -----------------
  //-----------------------------------------------------
  //Validate Class members
  //-----------------------------------------------------

  case class Student(name: String, id: Int)

  def validateName(name: String) = if (name.length > 3) name.success else "Invalid name".failure

  def validateId(id: Int) = if (id > 0) id.success else "invalid Id".failure

  val StudentValidated = Applicative[ValidOrErrorStrings].lift2(Student)

  val validName = validateName("naseem")
  val invalidName = validateName("ab")
  val validId = validateId(9)
  val invalidId = validateId(-8)

  println("Valid name and Id  " + StudentValidated(validName.toValidationNel, validId.toValidationNel))
  println("Valid name and invalid Id  " + StudentValidated(invalidName.toValidationNel, validId.toValidationNel))
  println("Invalid name and Id  " + StudentValidated(invalidName.toValidationNel, invalidId.toValidationNel))

  def main(args: Array[String]): Unit = {
  }
}
