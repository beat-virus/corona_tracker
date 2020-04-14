import 'dart:convert';
import 'dart:io';
import 'package:corona_tracker/persistence/UserStore.dart';
import 'package:flutter/material.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:corona_tracker/i18n/appLocalizations.dart';
import 'home_widget.dart';
import 'dart:async';

import 'package:unique_id/unique_id.dart';
import 'package:flutter/services.dart';
import 'globals.dart' as globals;

import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:openapi/api.dart';
import 'package:shared_preferences/shared_preferences.dart';


void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  // This widget is the root of your application.
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final FirebaseMessaging _firebaseMessaging = FirebaseMessaging();
  var apiInstance = DefaultApi();


  final FlutterLocalNotificationsPlugin flutterLocalNotificationsPlugin = new FlutterLocalNotificationsPlugin();

  @override
  void initState() {
    super.initState();
    initPlatformState();

    defaultApiClient.getAuthentication<HttpBasicAuth>('basicAuth').username = "API_USER";
    defaultApiClient.getAuthentication<HttpBasicAuth>('basicAuth').password = "API_PASSWORD";
    initFireBase();
    configLocalNotification();
  }

  Future<void> initFireBase() async {
    final prefs = await SharedPreferences.getInstance();
    _firebaseMessaging.configure(
      onMessage: (Map<String, dynamic> message) async {
        showNotification(message["notification"]);
        print("onMessage: $message");
      },
      onLaunch: (Map<String, dynamic> message) async {
        print("onLaunch: $message");
      },
      onResume: (Map<String, dynamic> message) async {
        print("onResume: $message");
      },
    );

    _firebaseMessaging.requestNotificationPermissions(
        const IosNotificationSettings(
            sound: true, badge: true, alert: true, provisional: true));
    _firebaseMessaging.onIosSettingsRegistered
        .listen((IosNotificationSettings settings) {
      print("Settings registered: $settings");
    });
    _firebaseMessaging.getToken().then((String token) {
      assert(token != null);
      print("my token: " + token);
      globals.deviceId = token;

      User myUser = User();
      //myUser.phoneHash = sha256.convert(utf8.encode("Eine Telefonnummer")).toString();
      //myUser.phoneHash = DateTime.now().toUtc().toIso8601String();
      myUser.token = token;
      UserStore().setUserToken(myUser.token);
      myUser.phoneHash = "0";
      apiInstance.createUser(myUser).then((User u){
        print("added User with token: " + u.token);
      }).catchError((e){
        print("error registering user");
      });
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String uniqueID;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      uniqueID = await UniqueId.getID;
    } on PlatformException {
      uniqueID = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      //globals.deviceId = uniqueID;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: ThemeData(
            primarySwatch: Colors.blueGrey,
            fontFamily: 'BebasNeue'
        ),
        home: Home(),
        localizationsDelegates: [
          // ... app specific delegates
          const AppLocalizationsDelegate(),
          GlobalMaterialLocalizations.delegate,
          GlobalWidgetsLocalizations.delegate,
          // TBD: Necessary at this point? probably not - garhon
          GlobalCupertinoLocalizations.delegate
        ],
        supportedLocales: [
          const Locale('en', ''), // English
          const Locale('de', ''), // German
        ],
        debugShowCheckedModeBanner: false,
    );
  }
  void configLocalNotification() {
    var initializationSettingsAndroid = new AndroidInitializationSettings('app_icon');
    var initializationSettingsIOS = new IOSInitializationSettings();
    var initializationSettings = new InitializationSettings(initializationSettingsAndroid, initializationSettingsIOS);
    flutterLocalNotificationsPlugin.initialize(initializationSettings);
  }

  void showNotification(message) async {
    var androidPlatformChannelSpecifics = new AndroidNotificationDetails(
      'com.example.corona_tracker',
      'Flutter chat demo',
      'your channel description',
      playSound: true,
      enableVibration: true,
      importance: Importance.Max,
      priority: Priority.High,
    );
    var iOSPlatformChannelSpecifics = new IOSNotificationDetails();
    var platformChannelSpecifics =
    new NotificationDetails(androidPlatformChannelSpecifics, iOSPlatformChannelSpecifics);
    await flutterLocalNotificationsPlugin.show(
        0, message['title'].toString(), message['body'].toString(), platformChannelSpecifics,
        payload: json.encode(message));
  }
}