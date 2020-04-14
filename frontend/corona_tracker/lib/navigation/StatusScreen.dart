import 'package:corona_tracker/i18n/appLocalizations.dart';
import 'package:corona_tracker/navigation/SettingsScreen.dart';
import 'package:corona_tracker/navigation/TestScreen.dart';
import 'package:flutter/material.dart';
import 'package:openapi/api.dart';
import 'package:preferences/preference_service.dart';
import '../globals.dart' as globals;
import 'package:corona_tracker/navigation/CreditsScreen.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';



class StatusScreen extends StatelessWidget {

  var api_instance = DefaultApi();

  CurrentHealthState defaultHealthState = CurrentHealthState();

  Future<String> getUserCount() async {
    //Save locally
    final prefs = await SharedPreferences.getInstance();
    String userCount = prefs.getString('userCount') ?? '0';

    //Testing
//    prefs.setString('userCount', '777');

    await api_instance.getUsers().then((List<User> users) {
      //print("got back " + users.length.toString() + " users");
      prefs.setString('userCount', users.length.toString());
      userCount = prefs.getString('userCount');
    }).catchError((error) {
      print("error getting all users");
    });
    
    return userCount;
  }

  Future<String> getUsersCuredCount() async {
    final prefs = await SharedPreferences.getInstance();
    int userCount = prefs.getInt('usersCuredCount') ?? 0;

    //Testing
//    prefs.setInt('usersCuredCount', 111);

    await api_instance.getUsers().then((List<User> users) {
      for(User u in users) {
        if (u.healthHistory.last.medicalState == MedicalStateEnum.CURED.toString())
          prefs.setInt('usersCuredCount', ++userCount);
      }
    }).catchError((error) {
      print("error getting all cured users");
    });

    return prefs.getInt('usersCuredCount').toString();
  }

  Future<String> getUsersInfectedCount() async {
    final prefs = await SharedPreferences.getInstance();
    int userCount = prefs.getInt('usersInfectedCount') ?? 0;

    await api_instance.getUsers().then((List<User> users) {
      for(User u in users) {
        if (u.healthHistory.last.medicalState == MedicalStateEnum.INFECTED.toString())
          prefs.setInt('usersInfectedCount', ++userCount);
      }
    }).catchError((error) {
      print("error getting all infected users");
    });

    return prefs.getInt('usersInfectedCount').toString();
  }

  Future<CurrentHealthState> getCurrentHealthstate() async {
    //Save health status locally
    final prefs = await SharedPreferences.getInstance();

    //Only uncomment for testing purpose!
    //prefs.setString('healthState', 'Gesund');
    //prefs.remove('healthState');

    defaultHealthState.text = prefs.getString('healthState') ?? 'Gesund';
    defaultHealthState.color = Colors.green[500];
    CurrentHealthState healthState = CurrentHealthState();

    healthState.text = defaultHealthState.text;
    healthState.color = Colors.green[500];

    await api_instance.getUsers().then((List<User> users) {
      for(User u in users) {
        if(u.token == globals.deviceId) {
          if(u.healthHistory.last.medicalState.isNotEmpty) {
            //TODO: Use Switches
            if (u.healthHistory.last.medicalState == MedicalStateEnum.UNKNOWN.toString()) {
              prefs.setString('healthState', 'Gesund');
              healthState.text = prefs.getString('healthState');
              healthState.color = Colors.green[500];
            }
            else if(u.healthHistory.last.medicalState == "SUSPECTED")
            //else if(u.healthHistory.last.medicalState == MedicalStateEnum.SUSPECTED.toString())
            {
              prefs.setString('healthState', 'Verdacht');
              healthState.text = prefs.getString('healthState');
              healthState.color = Colors.orange[500];
            }
            else if(u.healthHistory.last.medicalState == MedicalStateEnum.INFECTED.toString()) {
              prefs.setString('healthState', 'Infiziert');
              healthState.text = prefs.getString('healthState');
              healthState.color = Colors.red[500];
            }
            else if(u.healthHistory.last.medicalState == MedicalStateEnum.CURED.toString()) {
              prefs.setString('healthState', 'Immun');
              healthState.text = prefs.getString('healthState');
              healthState.color = Colors.blue[500];
            }
            else {
              prefs.setString('healthState', 'Unknown');
              healthState.text = prefs.getString('healthState');
              healthState.color = Colors.grey[500];
            }
          }
        }
      }
    }).catchError((error) {
      print("error getting health state of current user");
    });

    return healthState;
  }

  _onPreferenceScreen(BuildContext context)  async {
    await PrefService.init(prefix: 'pref_');
    Navigator.of(context).push(MaterialPageRoute(builder: (BuildContext context) => SettingsScreen()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.blueGrey[50],
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).StatusScreen_AppBarTitleText),
        actions: <Widget>[
          IconButton(
            icon: FaIcon(FontAwesomeIcons.infoCircle),
            onPressed: () {
              Navigator.of(context).push(MaterialPageRoute(builder: (BuildContext context) => CreditsScreen()));
            },
          ),
          IconButton(
            icon: FaIcon(FontAwesomeIcons.userCog),
            onPressed: () {
              //Navigator.of(context).push(MaterialPageRoute(builder: (BuildContext context) => SettingsScreen()));
              _onPreferenceScreen(context);
            },
          )
        ],
      ),
      body: Center(
        child: ListView(
          children: [
            Container(
              child: Image.asset(
                'assets/images/corona_tracker_homescreen_teaser.png',
                fit: BoxFit.cover,
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 25.0),
            ),
            Container(
              //color: Colors.lightBlue,
              child: Row (
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
                  //StatusUsers
                  Container(
                    width: 150,
                    height: 150,
                    decoration: BoxDecoration(
                        color: Colors.blueGrey[100],
                        borderRadius: BorderRadius.all(
                          Radius.circular(20)
                      )
                    ),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: //StatusUsers
                      [
                        FaIcon(
                          (FontAwesomeIcons.users),
                          color: Colors.blueGrey,
                          size: 50.0,
                        ),
                        // TODO i18n
                        Text(
                          'Benutzer',
                          style: TextStyle(
                              fontSize: 34,
                              decoration: TextDecoration.underline,
                          ),

                        ),
                        FutureBuilder(
                          future: getUserCount(),
                          builder: (context, AsyncSnapshot<String> snapshot) {
                            if (snapshot.hasData) {
                              return Text(
                                  snapshot.data.toString(),
                                  style: TextStyle(
                                    fontWeight: FontWeight.bold,
                                    fontSize: 34,
                                  ));
                            }
                            else {
                              return CircularProgressIndicator();
                            }
                          },
                        )
                      ],
                    ),
                  ),
                  Container(
                    //color: Colors.yellow,
                    width: 150,
                    height: 150,
                    decoration: BoxDecoration(
                        color: Colors.blueGrey[100],
                        borderRadius: BorderRadius.all(
                            Radius.circular(20)
                        )
                    ),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        FaIcon(
                          (FontAwesomeIcons.userAlt),
                          color: Colors.green[500],
                          size: 50.0,
                        ),
                        // TODO i18n
                        Text(
                          'Status',
                          style: TextStyle(
                            //fontWeight: FontWeight.bold,
                            fontSize: 34,
                            decoration: TextDecoration.underline,
                          ),
                        ),
                        FutureBuilder(
//                          initialData: defaultHealthState,
                          future: getCurrentHealthstate(),
                          builder: (context, AsyncSnapshot<CurrentHealthState> snapshot) {
                            if (snapshot.hasData) {
                              return Text(
                                  snapshot.data.text,
                                  style: TextStyle(
                                    fontWeight: FontWeight.bold,
                                    fontSize: 34,
                                    color: snapshot.data.color,
                                  ));
                            }
                            else {
                              return CircularProgressIndicator();
                            }
                          },
                        )
                      ],
                    ),
                  ),
                ],
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 25.0),
            ),
            Container(
              //color: Colors.lightBlue,
              width: 150,
              child: Row (
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
                  Container(
                    //color: Colors.red,
                    width: 150,
                    height: 150,
                    decoration: BoxDecoration(
                        color: Colors.blueGrey[100],
                        borderRadius: BorderRadius.all(
                            Radius.circular(20)
                        )
                    ),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        FaIcon(
                          (FontAwesomeIcons.userShield),
                          color: Colors.green[500],
                          size: 50.0,
                        ),
                        // TODO i18n
                        Text(
                          'Geheilt',
                          style: TextStyle(
                            //fontWeight: FontWeight.bold,
                            fontSize: 34,
                            color: Colors.green[500],
                            decoration: TextDecoration.underline,
                          ),
                        ),
                        FutureBuilder(
                          future: getUsersCuredCount(),
                          builder: (context, AsyncSnapshot<String> snapshot) {
                            if (snapshot.hasData) {
                              return Text(
                                  snapshot.data.toString(),
                                  style: TextStyle(
                                    fontWeight: FontWeight.bold,
                                    fontSize: 34,
                                    color: Colors.green[500],
                                  ));
                            }
                            else {
                              return CircularProgressIndicator();
                            }
                          },
                        )
                      ],
                    ),
                  ),
                  Container(
                    //color: Colors.yellow,
                    width: 150,
                    height: 150,
                    decoration: BoxDecoration(
                        color: Colors.blueGrey[100],
                        borderRadius: BorderRadius.all(
                            Radius.circular(20)
                        )
                    ),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        FaIcon(
                          (FontAwesomeIcons.virus),
                          color: Colors.red[500],
                          size: 50.0,
                        ),
                        // TODO i18n
                        Text(
                          'Infiziert',
                          style: TextStyle(
                            //fontWeight: FontWeight.bold,
                            fontSize: 34,
                            color: Colors.red[500],
                            decoration: TextDecoration.underline,
                          ),
                        ),
                        FutureBuilder(
                          future: getUsersInfectedCount(),
                          builder: (context, AsyncSnapshot<String> snapshot) {
                            if (snapshot.hasData) {
                              return Text(
                                  snapshot.data.toString(),
                                  style: TextStyle(
                                    fontWeight: FontWeight.bold,
                                    fontSize: 34,
                                    color: Colors.red[500],
                                  ));
                            }
                            else {
                              return CircularProgressIndicator();
                            }
                          },
                        )
                      ],
                    ),
                  ),
//                Column(
//                  children: <Widget>[
//                    SizedBox(height: 10, width: 10),
//                    makeElement(image: 'assets/images/corona_tracker_homescreen_teaser.png', tag: 'example')
//                  ],
//                )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

Widget makeElement({image, tag}) {
  return Hero(
    tag: tag,
    child: GestureDetector(
        child: Container(
          height: 250,
          width: double.infinity,
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(20),
              image: DecorationImage(
                  image: AssetImage(image)
              )
          ),
        )
    ),
  );
}



class StatusUsers extends StatefulWidget {
  @override
  StatusUsersState createState() {
    return StatusUsersState();
  }
}

class StatusUsersState extends State<StatusUsers> {

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Icon(
          Icons.healing,
          color: Colors.green[500],
          size: 50.0,
        ),
        // TODO i18n
        Text(
          'Geheilt',
          style: TextStyle(
            //fontWeight: FontWeight.bold,
            fontSize: 34,
            color: Colors.green[500],
            decoration: TextDecoration.underline,
          ),
        ),
        // TODO API CALL
        Text(
          '12446',
          style: TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 34,
            color: Colors.green[500],
          ),
        ),
      ],
    );
  }
}

class CurrentHealthState {
  String text;
  Color color;
}