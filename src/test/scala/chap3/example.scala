package chap3

class example  {

  {
    class Time {
      private var inMinutes =0

      def this(hours: Int, minutes: Int){
        this()
        this.inMinutes = hours * 60 + minutes
      }

      def before(other: Time) = inMinutes < other.inMinutes

    }
  }
}

