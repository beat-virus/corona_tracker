import 'package:flutter/material.dart';
import 'package:corona_tracker/i18n/appLocalizations.dart';

import 'package:preferences/preference_service.dart';
import 'package:preferences/preferences.dart';

class SettingsScreen extends StatefulWidget {
  @override
  _SettingsScreenState createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title:
            Text(AppLocalizations.of(context).SettingsScreen_AppBarTitleText),
        automaticallyImplyLeading: true,
        leading: IconButton(
            icon: Icon(Icons.arrow_back),
            onPressed: () => Navigator.pop(context)),
      ),
      body:
//      ListView(children: <Widget>[
//        ListTile(
//          title: Text('Enable Feature'),
//          trailing: Checkbox(
//            value: PrefService.getBool('feature_enabled'),
//            onChanged: (val) {
//              setState(() {
//                PrefService.setBool('feature_enabled', val);
//              });
//            },
//          ),
//          onTap: () {
//            setState(() {
//              PrefService.setBool(
//                  'feature_enabled', !PrefService.getBool('feature_enabled'));
//            });
//          },
//        )
//      ]),
          PreferencePage([
            PreferenceTitle('General'),
            DropdownPreference(
              'Start Page',
              'start_page',
              defaultVal: 'Timeline',
              values: ['Posts', 'Timeline', 'Private Messages'],
            ),
            PreferenceTitle('Personalization'),
            RadioPreference(
              'Light Theme',
              'light',
              'ui_theme',
              isDefault: true,
            ),
            RadioPreference(
              'Dark Theme',
              'dark',
              'ui_theme',
            ),
      ]),
    );
  }
}
