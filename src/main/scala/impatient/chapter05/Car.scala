package impatient.chapter05

class Car(val manufacturer: String, val modelName: String, val modelYear: Int, var registeredNum: String) {
  def this(manufacturer: String, modelName: String, modelYear: Int) {
    this(manufacturer, modelName, modelYear, "")
  }

  def this(manufacturer: String, modelName: String, registeredNum: String) {
    this(manufacturer, modelName, -1, registeredNum)
  }

  def this(manufacturer: String, modelName: String) {
    this(manufacturer, modelName, -1)
  }
}
