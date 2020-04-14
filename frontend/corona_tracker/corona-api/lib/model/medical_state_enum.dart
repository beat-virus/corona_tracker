part of openapi.api;

class MedicalStateEnum {
  /// The underlying value of this enum member.
  final String value;

  const MedicalStateEnum._internal(this.value);

  static const MedicalStateEnum uNKNOWN_ = const MedicalStateEnum._internal("UNKNOWN");
  static const MedicalStateEnum iNFECTED_ = const MedicalStateEnum._internal("INFECTED");
  static const MedicalStateEnum tREATMENT_ = const MedicalStateEnum._internal("TREATMENT");
  static const MedicalStateEnum cURED_ = const MedicalStateEnum._internal("CURED");

  static MedicalStateEnum fromJson(String value) {
    return new MedicalStateEnumTypeTransformer().decode(value);
  }
}

class MedicalStateEnumTypeTransformer {

  dynamic encode(MedicalStateEnum data) {
    return data.value;
  }

  MedicalStateEnum decode(dynamic data) {
    switch (data) {
      case "UNKNOWN": return MedicalStateEnum.uNKNOWN_;
      case "INFECTED": return MedicalStateEnum.iNFECTED_;
      case "TREATMENT": return MedicalStateEnum.tREATMENT_;
      case "CURED": return MedicalStateEnum.cURED_;
      default: throw('Unknown enum value to decode: $data');
    }
  }
}

