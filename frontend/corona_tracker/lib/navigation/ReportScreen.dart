import 'package:corona_tracker/navigation/BodyTemperatureScreen.dart';
import 'package:corona_tracker/navigation/QuestionnaireScreen.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

//import 'dart:developer';
import 'package:corona_tracker/models/PhoneNumber.dart';
import 'package:corona_tracker/i18n/appLocalizations.dart';

//import 'package:devicelocale/devicelocale.dart';
import 'package:dropdown_formfield/dropdown_formfield.dart';
import 'package:openapi/api.dart';

class ReportScreen extends StatelessWidget {
//   @override
//   ReportScreenFormState createState() {
//     return ReportScreenFormState();
//   }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).ReportScreen_AppBarTitleText),
      ),
      body: ReportScreenForm(),
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
  bool _enabled = true;
  String _locale;

  // @override
  // void initState() {
  //   super.initState();
  //   // Locale is provided via Localizations.localeOf(context)
  //   //initPlatformState();
  //   //_locale = Localizations.localeOf(context).countryCode;
  // }

  // // Platform messages are asynchronous, so we initialize in an async method.
  // Future<void> initPlatformState() async {
  //   String currentLocale;

  //   // Platform messages may fail, so we use a try/catch PlatformException.
  //   try {
  //     currentLocale = await Devicelocale.currentLocale;
  //     print(currentLocale);
  //   } on PlatformException {
  //     print("Error obtaining current locale");
  //   }

  //   // If the widget was removed from the tree while the asynchronous platform
  //   // message was in flight, we want to discard the reply rather than calling
  //   // setState to update our non-existent appearance.
  //   if (!mounted) return;

  //   setState(() {
  //     _locale = currentLocale;
  //   });
  // }

  //TODO Am Besten eine User Instanz erzeugen und die gewaehlten Checkboxes in eine Liste packen

  bool _feverChecked = false;
  bool _tirednessChecked = false;
  bool _dryCoughChecked = false;
  bool _achesPainsChecked = false;
  String medicalState = MedicalStateEnum.UNKNOWN.toString();

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: AppBar(
          flexibleSpace:
              new Column(mainAxisAlignment: MainAxisAlignment.end, children: [
            TabBar(
              tabs: [
                new Container(
//                  child: Tab(icon: Icon(Icons.help_outline)),
                    child: Tab(
                        child: Text(
                          'Köpertemp.',
                          style: TextStyle(fontSize: 20),
                        ))),
                new Container(
//                  child: Tab(icon: Icon(Icons.help_outline)),
                    child: Tab(
                        child: Text(
                  'Symptome',
                  style: TextStyle(fontSize: 20),
                ))),
                new Container(
//                  child: Tab(icon: Icon(Icons.help_outline)),
                    child: Tab(
                        child: Text(
                  'Sende Fall',
                  style: TextStyle(fontSize: 20),
                ))),
              ],
            ),
          ]),
        ),
        body: TabBarView(
          children: <Widget>[
            //Build a Form widget using the _formKey created above.
            new BodyTemperatureScreen(),
            new QuestionnaireScreen(),
            Form(
              key: _formKey,
              child: Scrollbar(
                child: ListView(
                  //mainAxisAlignment: MainAxisAlignment.center,
                  //crossAxisAlignment: CrossAxisAlignment.center,
                  shrinkWrap: true,
                  padding: EdgeInsets.all(20.0),
                  children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 5.0),
                    ),
                    DropDownFormField(
                      titleText:
                          'Mein aktueller Gesundheitsstatus in Bezug auf SARS-CoV-2',
                      hintText: 'Bitte wählen Sie aus',
                      value: medicalState,
                      onSaved: (value) {
                        setState(() {
                          medicalState = value;
                        });
                      },
                      onChanged: (value) {
                        setState(() {
                          medicalState = value;
                        });
                      },
                      dataSource: [
                        {
                          "display": "Unbekannt/Gesund",
                          "value": MedicalStateEnum.UNKNOWN.toString(),
                        },
                        {
                          "display": "Verdachtsfall (ungetestet)",
                          "value": "SUSPECTED",
                          //"value": MedicalStateEnum.SUSPECTED.toString(),
                        },
                        {
                          "display": "Infiziert (positiver Test)",
                          "value": MedicalStateEnum.INFECTED.toString(),
                        },
                        {
                          "display": "Cured",
                          "value": MedicalStateEnum.CURED.toString(),
                        }
                      ],
                      textField: 'display',
                      valueField: 'value',
                    ),
//                    Padding(
//                      padding: const EdgeInsets.symmetric(vertical: 15.0),
//                    ),
//                    Text(
//                        AppLocalizations.of(context)
//                            .ReportScreen_FormSymptomPromptText,
//                        style:
//                            TextStyle(fontWeight: FontWeight.bold, fontSize: 25)),
//                    CheckboxListTile(
//                      title: Text(
//                          AppLocalizations.of(context)
//                              .ReportScreen_CheckboxFeverText,
//                          style: TextStyle(
//                              fontWeight: FontWeight.bold, fontSize: 20)),
//                      value: _feverChecked,
//                      onChanged: (value) {
//                        setState(() {
//                          _feverChecked = value;
//                        });
//                      },
//                      controlAffinity: ListTileControlAffinity
//                          .leading, //  <-- leading Checkbox
//                    ),
//                    CheckboxListTile(
//                      title: Text(
//                          AppLocalizations.of(context)
//                              .ReportScreen_CheckboxFatigueText,
//                          style: TextStyle(
//                              fontWeight: FontWeight.bold, fontSize: 20)),
//                      value: _tirednessChecked,
//                      onChanged: (value) {
//                        setState(() {
//                          _tirednessChecked = value;
//                        });
//                      },
//                      controlAffinity: ListTileControlAffinity
//                          .leading, //  <-- leading Checkbox
//                    ),
//                    CheckboxListTile(
//                      title: Text(
//                          AppLocalizations.of(context)
//                              .ReportScreen_CheckboxDryCoughText,
//                          style: TextStyle(
//                              fontWeight: FontWeight.bold, fontSize: 20)),
//                      value: _dryCoughChecked,
//                      onChanged: (value) {
//                        setState(() {
//                          _dryCoughChecked = value;
//                        });
//                      },
//                      controlAffinity: ListTileControlAffinity
//                          .leading, //  <-- leading Checkbox
//                    ),
//                    CheckboxListTile(
//                      title: Text(
//                          AppLocalizations.of(context)
//                              .ReportScreen_CheckboxAchesText,
//                          style: TextStyle(
//                              fontWeight: FontWeight.bold, fontSize: 20)),
//                      value: _achesPainsChecked,
//                      onChanged: (value) {
//                        setState(() {
//                          _achesPainsChecked = value;
//                        });
//                      },
//                      controlAffinity: ListTileControlAffinity
//                          .leading, //  <-- leading Checkbox
//                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 15.0),
                    ),
                    Text(
                      AppLocalizations.of(context)
                          .ReportScreen_CellphoneNumberPromptText,
                      style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 25,
                          color: Colors.red),
                    ),
                    TextFormField(
                      enabled: _enabled,
                      keyboardType: TextInputType.number,
                      inputFormatters: <TextInputFormatter>[
                        WhitelistingTextInputFormatter.digitsOnly
                      ],
                      decoration: InputDecoration(
                        icon: Icon(Icons.phone),
                        labelText:
                            AppLocalizations.of(context).cellphonenumberText,
                      ),
                      validator: (value) {
                        if (value.isEmpty) {
                          return AppLocalizations.of(context)
                              .cellphoneFieldValidator_ShouldNotBeEmptyText;
                        } else if (value.length < 11) {
                          return AppLocalizations.of(context)
                              .cellphoneFieldValidator_TooShortText;
                        } else if (value.length > 16) {
                          return AppLocalizations.of(context)
                              .cellphoneFieldValidator_TooLongText;
                        } else
                          return null;
                      },
                      onSaved: (String value) {
                        //Hier werden die Daten an das Backend gesendet und dort weiter-
                        //-bearbeitet
                        String phoneNumberE164 = PhoneNumber(value,
                                Localizations.localeOf(context).countryCode)
                            .normalize();
                        // vielleicht hier noch den String im TextFeld mit dem normalisierten ersetzen?
                        print(phoneNumberE164);
                      },
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 16.0),
                      child: RaisedButton(
                        onPressed: () {
                          // Validate returns true if the form is valid, or false
                          // otherwise.
                          _locale = Localizations.localeOf(context).languageCode;
                          if (_formKey.currentState.validate() && _enabled) {
                            _enabled = !_enabled;
                            // If the form is valid, display a Snackbar.
                            Scaffold.of(context).showSnackBar(SnackBar(
                                content: Text(AppLocalizations.of(context)
                                    .ReportScreen_SendingReportText)));
                            _formKey.currentState.save();
                            printInputs();
                          }
                        },
                        child: Text(AppLocalizations.of(context).SendButtonText),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  void printInputs() {
    print("_feverChecked:" + _feverChecked.toString());
    print("_tirednessChecked:" + _tirednessChecked.toString());
    print("_dryCoughChecked:" + _dryCoughChecked.toString());
    print("_achesPainsChecked:" + _achesPainsChecked.toString());
    print("medicalState:" + medicalState.toString());
  }
}
