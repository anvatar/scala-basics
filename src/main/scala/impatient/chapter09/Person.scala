package impatient.chapter09

import scala.collection.mutable.ArrayBuffer

@SerialVersionUID(43L) class Person extends Serializable {
  val friends = new ArrayBuffer[Person]

  def addFriend(friend: Person) {
    friends += friend
  }
}
