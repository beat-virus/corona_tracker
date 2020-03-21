import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:developer';
import 'package:corona_tracker/models/PhoneNumber.dart';
import 'package:devicelocale/devicelocale.dart';


class ReportScreen extends StatelessWidget {

  @override
  ReportScreenFormState createState() {
    return ReportScreenFormState();
  }

  @override
  Widget build(BuildContext context) {
    final appTitle = 'Fall melden';
    return MaterialApp(
      theme: ThemeData(primarySwatch: Colors.blueGrey),
      title: appTitle,
      home: Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: AppBar(
          title: Text(appTitle),
        ),
        body: ReportScreenForm(),
      ),
    );
  }
}

// Create a Form widget.
class ReportScreenForm extends StatefulWidget {
  @override
  ReportScreenFormState createState() {
    return ReportScreenFormState();
  }
}

class ReportScreenFormState extends State<ReportScreenForm> {
  // Create a global key that uniquely identifies the Form widget
  // and allows validation of the form.
  final _formKey = GlobalKey<FormState>();
  String _locale;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String currentLocale;

    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      currentLocale = await Devicelocale.currentLocale;
      print(currentLocale);
    } on PlatformException {
      print("Error obtaining current locale");
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _locale = currentLocale;
    });
  }

  //TODO Am Besten eine User Instanz erzeugen und die gewaehlten Checkboxes in eine Liste packen

  bool _feverChecked = true;
  bool _tirednessChecked = true;
  bool _dryCoughChecked = true;
  bool _achesPainsChecked = true;


  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 5.0),
          ),
          Text (
              'Haben Sie folgende Symptome?',
              style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 17
              )
          ),
          CheckboxListTile(
            title: Text("Fieber"),
            value: _feverChecked,
            onChanged: (value) {
              setState(() {
                _feverChecked = value;
              });
            },
            controlAffinity: ListTileControlAffinity.leading, //  <-- leading Checkbox
          ),
          CheckboxListTile(
            title: Text("Müdigkeit"),
            value: _tirednessChecked,
            onChanged: (value) {
              setState(() {
                _tirednessChecked = value;
              });
            },
            controlAffinity: ListTileControlAffinity.leading, //  <-- leading Checkbox
          ),
          CheckboxListTile(
            title: Text("Trockener Husten"),
            value: _dryCoughChecked,
            onChanged: (value) {
              setState(() {
                _dryCoughChecked = value;
              });
            },
            controlAffinity: ListTileControlAffinity.leading, //  <-- leading Checkbox
          ),
          CheckboxListTile(
            title: Text("Beschwerden/Schmerzen"),
            value: _achesPainsChecked,
            onChanged: (value) {
              setState(() {
                _achesPainsChecked = value;
              });
            },
            controlAffinity: ListTileControlAffinity.leading, //  <-- leading Checkbox
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 15.0),
          ),
          Text (
            'Bitte tragen Sie ihre Mobilnummer ein',
                style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 17,
                    color: Colors.red
                )
          ),
          TextFormField(
            keyboardType: TextInputType.number,
            inputFormatters: <TextInputFormatter>[
              WhitelistingTextInputFormatter.digitsOnly
            ],
            decoration: const InputDecoration(
              icon: Icon(Icons.phone),
              labelText: 'Mobilnummer',
            ),
            validator: (value) {
              //TODO: i18n (Localization!)
              if (value.isEmpty) {
                return 'Die Mobilnummer darf nicht leer bleiben';
              }
              else if (value.length < 11) {
                return 'Die Mobilnummer ist zu kurz';
              }
              else if (value.length > 16) {
                return "Die Mobilnummer ist zu lang";
              }
              else
                return null;
            },
            onSaved: (String value) {
              //Hier werden die Daten an das Backend gesendet und dort weiter-
              //-bearbeitet
              String phoneNumberE164 = PhoneNumber(value, _locale).normalize();
              // vielleicht hier noch den String im TextFeld mit dem normalisierten ersetzen?
              print(phoneNumberE164);
            },
          ),
//
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 16.0),
            child: RaisedButton(
              onPressed: () {
                // Validate returns true if the form is valid, or false
                // otherwise.
                if (_formKey.currentState.validate()) {
                  // If the form is valid, display a Snackbar.
                  Scaffold.of(context)
                      .showSnackBar(SnackBar(content: Text('Fall wird gesendet')));
                  _formKey.currentState.save();
                }
              },
              child: Text('Senden'),
            ),
          ),
        ],
      ),
    );
  }
}