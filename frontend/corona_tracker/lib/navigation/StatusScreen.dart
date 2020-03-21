import 'package:corona_tracker/location/location_tracker.dart';
import 'package:flutter/material.dart';

class StatusScreen extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Status'),
      ),
      body: Center(
        child: Center(
          child: LocationWidget(),
        ),
      ),
    );
  }
}