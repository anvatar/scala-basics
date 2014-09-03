package impatient.chapter06

abstract  class UnitConversion(val conversionValue: Double) {
  def convert(value: Double) = { value * conversionValue }
}
