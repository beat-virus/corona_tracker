import 'package:flutter/material.dart';
import 'home_widget.dart';
import 'dart:async';

import 'package:unique_id/unique_id.dart';
import 'package:flutter/services.dart';
import 'globals.dart' as globals;


void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  // This widget is the root of your application.
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    initPlatformState();
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
      globals.deviceId = uniqueID;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: ThemeData(primarySwatch: Colors.blueGrey), home: Home());
  }
}