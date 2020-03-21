class PhoneNumber {
  String number;
  String region_code;

  PhoneNumber(String _number, String _country_code) {
    number = _number;
    //print(_country_code); TODO: Locale always giving back en-US
    _country_code = _country_code.substring(_country_code.indexOf("_") + 1, _country_code.length);

    region_code = "49"; // default
    switch(_country_code) {
      case "DE":
        region_code = "49";
        break;
      case "CH":
        region_code = "41";
        break;
      case "AT":
        region_code = "43";
        break;
      case "FR":
        region_code = "33";
        break;
      case "NL":
        region_code = "31";
        break;
      case "PL":
        region_code = "48";
        break;
      case "DK":
        region_code = "45";
        break;
      case "BE":
        region_code = "32";
        break;
      case "LU":
        region_code = "352";
        break;
      case "CZ":
        region_code = "420";
        break;
      case "US":
        region_code = "1";
        break;
    }
  }

  String get_number() {
    return number;
  }

  String normalize() {
    if(number.length > 2) {
      if(number[0] == "0" && number[1] == "0") {
        //print("replacing 00 with +");
        number = number.substring(2, number.length);
        number = "+" + number;
      } else if(number[0] == "0") {
        //print("replacing 0 with +<region_code>");
        number = number.substring(1, number.length);
        number = "+" + region_code + number;
      } else if(number[0] == "+") {
        //print("number already in right format");
      } else {
        //print("adding prefix to number");
        number = "+" + region_code + number;
      }
    }
    return number;
  }
}