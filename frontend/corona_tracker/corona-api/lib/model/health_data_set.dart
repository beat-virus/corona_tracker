part of openapi.api;

class HealthDataSet {
  
  String userId = null;
  
  MedicalStateEnum medicalState = null;
  //enum medicalStateEnum {  UNKNOWN,  INFECTED,  TREATMENT,  CURED,  };{
  /* in IS0 8601 format. eg. `1963-11-22T18:30:00Z` */
  DateTime time = null;
  HealthDataSet();

  @override
  String toString() {
    return 'HealthDataSet[userId=$userId, medicalState=$medicalState, time=$time, ]';
  }

  HealthDataSet.fromJson(Map<String, dynamic> json) {
    if (json == null) return;
    userId = json['userId'];
    medicalState = (json['medicalState'] == null) ?
      null :
      MedicalStateEnum.fromJson(json['medicalState']);
    time = (json['time'] == null) ?
      null :
      DateTime.parse(json['time']);
  }

  Map<String, dynamic> toJson() {
    Map <String, dynamic> json = {};
    if (userId != null)
      json['userId'] = userId;
    if (medicalState != null)
      json['medicalState'] = medicalState;
    if (time != null)
      json['time'] = time == null ? null : time.toUtc().toIso8601String();
    return json;
  }

  static List<HealthDataSet> listFromJson(List<dynamic> json) {
    return json == null ? List<HealthDataSet>() : json.map((value) => HealthDataSet.fromJson(value)).toList();
  }

  static Map<String, HealthDataSet> mapFromJson(Map<String, dynamic> json) {
    var map = Map<String, HealthDataSet>();
    if (json != null && json.isNotEmpty) {
      json.forEach((String key, dynamic value) => map[key] = HealthDataSet.fromJson(value));
    }
    return map;
  }

  // maps a json object with a list of HealthDataSet-objects as value to a dart map
  static Map<String, List<HealthDataSet>> mapListFromJson(Map<String, dynamic> json) {
    var map = Map<String, List<HealthDataSet>>();
     if (json != null && json.isNotEmpty) {
       json.forEach((String key, dynamic value) {
         map[key] = HealthDataSet.listFromJson(value);
       });
     }
     return map;
  }
}

