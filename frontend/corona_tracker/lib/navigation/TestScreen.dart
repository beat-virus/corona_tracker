import 'dart:async';
import 'dart:convert';

import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:corona_tracker/i18n/appLocalizations.dart';

import 'package:http/http.dart' as http;

class TestScreen extends StatelessWidget {

  final String serverToken = 'AAAAT47gnLk:APA91bGyqRSUyxyNOwtptjBIeLhg9KPD3_5smefMBcNyV5uqZ4XsdGMwgjuw-hTmSNtXoq9XfmB6DsNmfNEL-YFEyEhUYFkbA2cNZA2jaSohpl5geMbN9qEIMe9PnJUNzuMzB6G1t-8h';
  final FirebaseMessaging firebaseMessaging = FirebaseMessaging();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).SettingsScreen_AppBarTitleText),
        automaticallyImplyLeading: true,
        leading: IconButton(icon: Icon(Icons.arrow_back),
            onPressed: () => Navigator.pop(context)),
      ),
      body: Center(
          child: RaisedButton(
            child: Text('Ping me'),
            onPressed: () {
              sendAndRetrieveMessage();
            },
            color: Theme.of(context).primaryColor,
            textColor: Colors.white,
            /*onPressed: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (BuildContext context) => StatusScreen(),
                  ))*/
          )),
    );
  }

  void sendAndRetrieveMessage() async {
    http.post(
      'https://fcm.googleapis.com/fcm/send',
      headers: <String, String>{
        'Content-Type': 'application/json',
        'Authorization': 'key=$serverToken',
      },
      body: jsonEncode(
        <String, dynamic>{
          'notification': <String, dynamic>{
            'body': 'World :D',
            'title': 'Hello'
          },
          'priority': 'high',
          'data': <String, dynamic>{
            'click_action': 'FLUTTER_NOTIFICATION_CLICK',
            'id': "1",
            'status': 'done'
          },
          'to': await firebaseMessaging.getToken(),
        },
      ),
    );
  }

}