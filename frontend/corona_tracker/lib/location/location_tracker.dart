import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:location/location.dart';

class LocationTracker {
  LocationTrackerListener listener;
  Location location = new Location();
  LocationData currentLocation;

  LocationTracker(LocationTrackerListener listener) {
    this.listener = listener;
    location.onLocationChanged().listen((LocationData currentLocation) {
      this.currentLocation = currentLocation;
      if(listener != null) {
        listener.onLocationHasChanged(this.currentLocation);
      }
    });
  }

}

abstract class LocationTrackerListener {
  void onLocationHasChanged(LocationData locationData);
}



// Example-widget to visualize position
class LocationWidget extends StatefulWidget {
  @override
  _LocationWidgetState createState() => _LocationWidgetState();
}

class _LocationWidgetState extends State<LocationWidget> with LocationTrackerListener {
  LocationTracker locationTracker;
  LocationData currentLocation;

  @override
  void initState() {
    super.initState();
    locationTracker = new LocationTracker(this);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: <Widget>[
          currentLocation == null
              ? CircularProgressIndicator()
              : Text("Location:" + currentLocation.latitude.toString() + " " +
              currentLocation.longitude.toString()),
        ],
      ),
    );
  }

  @override
  void onLocationHasChanged(LocationData locationData) {
    setState(() {
      this.currentLocation = locationData;
    });
    print("location found: " + currentLocation.latitude.toString() + "," + currentLocation.longitude.toString());
  }
}