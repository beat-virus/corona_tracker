library openapi.api;

import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart';

part 'api_client.dart';
part 'api_helper.dart';
part 'api_exception.dart';
part 'auth/authentication.dart';
part 'auth/api_key_auth.dart';
part 'auth/oauth.dart';
part 'auth/http_basic_auth.dart';

part 'api/default_api.dart';

part 'model/friend.dart';
part 'model/health_data_set.dart';
part 'model/medical_state_enum.dart';
part 'model/position.dart';
part 'model/report_trips.dart';
part 'model/temp_measurement.dart';
part 'model/user.dart';


ApiClient defaultApiClient = ApiClient();
