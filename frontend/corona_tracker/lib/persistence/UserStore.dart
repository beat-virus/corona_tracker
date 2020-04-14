import 'package:shared_preferences/shared_preferences.dart';

class UserStore {
  static final UserStore _instance = UserStore._internal();

  factory UserStore() {
    return _instance;
  }

  UserStore._internal() {}

  static const _USER_TOKEN = 'userToken';
  static const _USER_BODY_TEMPERATURE = 'bodyTemperature';

  setUserToken(String userToken) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString(_USER_TOKEN, userToken);
  }

  Future<String> getUserToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString(_USER_TOKEN);
  }

  setBodyTemperature(double bodyTemperature) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setDouble(_USER_BODY_TEMPERATURE, bodyTemperature);
  }

  Future<double> getBodyTemperature() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getDouble(_USER_BODY_TEMPERATURE);
  }


}
