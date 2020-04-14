import 'package:corona_tracker/navigation/BodyTemperatureScreen.dart';
import 'package:corona_tracker/navigation/ContactScreen.dart';
import 'package:corona_tracker/navigation/DummyScreen.dart';
import 'package:corona_tracker/navigation/HelpScreen.dart';
import 'package:corona_tracker/navigation/MapScreen.dart';
import 'package:corona_tracker/navigation/QuestionnaireScreen.dart';
import 'package:corona_tracker/navigation/TestScreen.dart';
import 'package:corona_tracker/navigation/StatusScreen.dart';
import 'package:corona_tracker/navigation/ReportScreen.dart';
import 'package:corona_tracker/i18n/appLocalizations.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';




class Home extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomeState();
  }
}

class _HomeState extends State<Home> {
  int _currentIndex = 0;
  final List<Widget> _children = [
    StatusScreen(),
    MapScreen(),
    ReportScreen(),
    HelpScreen(),
    ContactScreen(),
//    BodyTemperatureScreen(),
    //SettingsScreen(),
    //DummyScreen()
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      /*appBar: AppBar(
        title: Text('My Flutter App'),
      ),*/
      body: _children[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        onTap: onTabTapped,
        currentIndex: _currentIndex, // this will be set when a new tab is tapped
        items: [
          BottomNavigationBarItem(
            icon: FaIcon(FontAwesomeIcons.houseUser),
            title: new Text(AppLocalizations.of(context).bottomNavigationBarStatusText),
          ),
          BottomNavigationBarItem(
            icon: FaIcon(FontAwesomeIcons.viruses),
            title: new Text(AppLocalizations.of(context).bottomNavigationBarMapText),
          ),
          BottomNavigationBarItem(
            icon: FaIcon(FontAwesomeIcons.temperatureHigh),
              title: new Text(AppLocalizations.of(context).bottomNavigationBarReportText),
          ),
          BottomNavigationBarItem(
              icon: FaIcon(FontAwesomeIcons.hospitalSymbol),
              title: new Text(AppLocalizations.of(context).bottomNavigationBarHelpText)
          ),
          BottomNavigationBarItem(
              icon: FaIcon(FontAwesomeIcons.peopleArrows),
              // TODO: i18n
              title: new Text('Freunde')
          ),
//          BottomNavigationBarItem(
//              icon: FaIcon(FontAwesomeIcons.biohazard),
//              // TODO: i18n
//              title: new Text('Dev')
//          ),
        ],
      ),
    );
  }

  void onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }
}