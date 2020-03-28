import 'dart:async';
import 'dart:convert';
import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:numberpicker/numberpicker.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:openapi/api.dart';



class BodyTemperatureScreen extends StatefulWidget {

  @override
  _BodyTemperatureScreenState createState() => _BodyTemperatureScreenState();

}

class _BodyTemperatureScreenState extends State<BodyTemperatureScreen> {

  var api_instance = DefaultApi();
  var tempMeasurement = TempMeasurement();// TempMeasurement |

  double _currentDoubleValue = 36.0;
  NumberPicker decimalNumberPicker;

  //TODO funktioniert nicht
  Future<double> _saveBodyTemperature() async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setDouble('bodyTemperature', _currentDoubleValue);
    _currentDoubleValue = prefs.getDouble('bodyTemperature');
    return prefs.getDouble('bodyTemperature');
  }

 // https://camposha.info/flutter-numberpicker-pick-integer-and-decimals/

  void _initializeNumberPickers() async {

    decimalNumberPicker = new NumberPicker.decimal(
      initialValue: _currentDoubleValue,
      minValue: 36,
      maxValue: 43,
      decimalPlaces: 1,
      onChanged: (value) => setState(() {
        _currentDoubleValue = value;
      }),
    );
  }

  Future<Double> createBodyTempMeasurement(double value) async {
    final prefs = await SharedPreferences.getInstance();
    String userId = prefs.getString('UserToken');
    assert(userId!= null);

    tempMeasurement.time = DateTime.now();
    tempMeasurement.userId = userId;
    tempMeasurement.value = (value * 100).toInt();

    try {
      var result = api_instance.createBodyTempMeasurement(tempMeasurement);
      print(result);
      print('SUCCESS!!!!!!!!!!');
    } catch (e) {
      print(
          "Exception when calling DefaultApi->createBodyTempMeasurement: $e\n");
    }
  }

  @override
  Widget build(BuildContext context) {
    _initializeNumberPickers();
    return Scaffold(
          body: Center(
              child: ListView(
                //shrinkWrap: true,
                  padding: EdgeInsets.all(20.0),
                  children: [
                    Container(
                        child:
                        Text(
                          'Bitte auswählen :',
                          style: TextStyle(
                            fontSize: 30,

                          ),
                        )
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 15.0),
                    ),
                    Container(
                      child:
                      Column(
                        children: <Widget>[
                          Column(
                            children: <Widget>[
                              decimalNumberPicker,
                            ]
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 15.0),
                          ),
                          Column(
                            children: <Widget>[
                              Text(
                                  "Körpertemperatur: ",
                                   style: TextStyle(
                                   fontWeight: FontWeight.bold,
                                   fontSize: 34,
                                   color: Colors.blueGrey,
                                   ),
                              ),
                            ],
                          ),
                          Column(
                            children: <Widget>[
                              Text(
                                "$_currentDoubleValue°C",
                                style: TextStyle(
                                  fontWeight: FontWeight.bold,
                                  fontSize: 34,
                                  color: Colors.blueGrey,
                                ),
                              ),

                            ],
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 15.0),
                          ),
                          RaisedButton(
                            child:
                            Text(
                                'Speichern',
                              style: TextStyle(
                                  fontSize: 25,
                              )
                            ),
                            onPressed: () {
                              createBodyTempMeasurement(_currentDoubleValue);
                            },
                            color: Theme.of(context).primaryColor,
                            textColor: Colors.white,
                            /*onPressed: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (BuildContext context) => StatusScreen(),
                  ))*/
                          )
                        ],
                      ),
                    ),
                  ]
              )
          )
    );
  }
}
