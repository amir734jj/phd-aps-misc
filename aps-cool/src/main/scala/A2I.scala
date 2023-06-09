import cool_implicit._
class A2I() {
  
  // Convert a string of digits into an integer.
  // return a negative number if something went wrong.
  def a2i(s : String) : Int = {
    var i : Int = 0;
    var result : Int = 0;
    while (if (result < 0) false else i < s.length()) {
      var digit : Int = c2i(s.substring(i,i+1));
      if (digit < 0) result = digit else result = result * 10 + digit;
      i = i + 1
    };
    result
  };

  //   c2i   Converts a 1-character string to an integer.  returns -1
  //         if the string is not "0" through "9"
  def c2i(char : String) : Int = {
    var ch : Int = char.charAt(0);
    if (ch < 48) -1
    else if (58 <= ch) -1
    else ch - 48
  };

  def i2a(i : Int) : String = i.toString();
}
