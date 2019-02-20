import scalaz._
import scalaz.ValidationNel
import scalaz.syntax.apply._
import scalaz.std.string._
import scalaz.syntax.either._
import scalaz.syntax.validation._
import syntax.std.option._

object ValidationDemo3 {

  case class User(name: String)

  case class Category(user: User, parent: Category, name: String, desc: String)

  // Validation method that Accumulates errors
  def nonNull[A](a: A, msg: String): ValidationNel[String, A] = {
    Option(a).toSuccess(msg).toValidationNel
  }

  def nonNull2[A](a: A, msg: String): \/[String, A] = {
    Option(a) \/> msg
  }

  def buildCategory(user: User, parent: Category, name: String, desc: String) = (
    nonNull(user, "User is mandatory for a normal category") |@|
      nonNull(parent, "Parent category is mandatory for a normal category")|@|
      nonNull(name, "Name is mandatory for a normal category") |@|
      nonNull(desc, "Description is mandatory for a normal category")
    ) (Category.apply)

  def buildCategory2(user: User, parent: Category, name: String, desc: String) = (
    nonNull2(user, "User is mandatory for a normal category")|@|
      nonNull2(parent, "Parent category is mandatory for a normal category")|@|
      nonNull2(name, "Name is mandatory for a normal category") |@|
      nonNull2(desc, "Description is mandatory for a normal category")
    ) (Category.apply)

  def main(args: Array[String]): Unit = {
    println(buildCategory(User("Naseem"),null,"games",""))
    println(buildCategory2(User("Naseem"),null,"games",""))
  }

}
