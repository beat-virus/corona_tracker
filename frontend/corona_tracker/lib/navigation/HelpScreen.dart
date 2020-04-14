import 'package:flutter/material.dart';

class HelpScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Hilfe'),
        ),
        body:
        Column(
          children: <Widget>[
            Container(
              margin: const EdgeInsets.only(
                  top: 10.0, left: 10.0, right: 10.0, bottom: 0.0),
              child:
              Text('Eine Pandemie kann viele Fragen aufwerfen. Um Ihnen Antworten auf mögliche Bedenken'
                  ' zu geben, haben wir einige der offiziellen Gesundheitsinformationen des Robert Koch'
                  ' Instituts auf infektionsschutz.de erfasst.',
                  style:
                  TextStyle(
                      fontStyle: FontStyle.italic,
                      fontSize: 22,
                      color: Colors.blueGrey[500]
                  )
              )
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 5.0),
            ),
            Container(
                child: Expanded(
                    child: ListView.builder(
                      itemBuilder: (BuildContext context, int index) =>
                          EntryItem(data[index]),
                      itemCount: data.length,
                    ),
                )
            )
          ],
        )
    );
  }
}

// One entry in the multilevel list displayed by this app.
class Entry {
  Entry(this.title, [this.children = const <SubEntry>[]]);

  final String title;
  final List<SubEntry> children;
}

class SubEntry {
  SubEntry(this.title, this.description);

  final String title;
  final String description;
}

// The entire multilevel list displayed by this app.
final List<Entry> data = <Entry>[
  Entry('Allgemein', <SubEntry>[
    SubEntry(
      'Wie kann man sich vor einer Ansteckung schützen?',
          'Durch Anpassung des Verhaltens – möglichst viel zu Hause bleiben, Fachleute sprechen auch'
          ' von "sozialer Distanzierung" – können Sie dazu beitragen, dass sich das Virus langsamer'
          ' verbreitet.'
          ' Damit leisten Sie Ihren Beitrag, Menschenleben zu retten und auch ältere und Menschen mit'
          ' Vorerkrankungen zu schützen, die von dem Coronavirus stärker betroffen sind als alle anderen.'
          ' Darüber hinaus bleibt das Beachten einer guten Hygiene (Richtig Händewaschen, Husten und'
          ' Niesen) weiterhin von großer Bedeutung.'
    ),
  ]),
  Entry('Hygiene', <SubEntry>[
    SubEntry(
      'Wie wasche ich meine Hände richtig?',
        'Schmutz und auch Krankheitskeime abwaschen – das klingt einfach. Richtiges Händewaschen'
    ' erfordert aber ein sorgfältiges Vorgehen. Häufig werden die Hände beispielsweise nicht'
        ' ausreichend lange eingeseift und insbesondere Handrücken, Daumen und Fingerspitzen'
    ' vernachlässigt.'
    ' Gründliches Händewaschen gelingt in fünf Schritten:\n\n'
    ' 1. Halten Sie die Hände zunächst unter fließendes Wasser. Die Temperatur können Sie so'
        ' wählen, dass sie angenehm ist.\n\n'
    ' 2. Seifen Sie dann die Hände gründlich ein – sowohl Handinnenflächen als auch Handrücken,'
        ' Fingerspitzen, Fingerzwischenräume und Daumen. Denken Sie auch an die Fingernägel.'
    ' Hygienischer als Seifenstücke sind Flüssigseifen, besonders in öffentlichen Waschräumen.\n\n'
    ' 3. Reiben Sie die Seife an allen Stellen sanft ein. Gründliches Händewaschen dauert 20 bis 30'
    ' Sekunden.\n\n'
    ' 4. Danach die Hände unter fließendem Wasser abspülen. Verwenden Sie in öffentlichen Toiletten'
         'zum Schließen des Wasserhahns ein Einweghandtuch oder Ihren Ellenbogen.\n\n'
    ' 5. Trocknen Sie anschließend die Hände sorgfältig ab, auch in den Fingerzwischenräumen. In'
    ' öffentlichen Toiletten eignen sich hierfür am besten Einmalhandtücher. Zu Hause sollte jeder'
    ' sein persönliches Handtuch benutzen.'
    ),
    SubEntry(
        'Was ist wichtig beim husten oder niesen?',
        ' Sich beim Husten oder Niesen die Hand vor den Mund zu halten, wird oft für höflich gehalten. Aus'
    ' gesundheitlicher Sicht aber ist dies keine sinnvolle Maßnahme:'
        ' Dabei gelangen Krankheitserreger an die Hände und können anschließend über gemeinsam'
    ' benutzte Gegenstände oder beim Hände schütteln an andere weitergereicht werden.'
    ' Um keine Krankheitserreger weiterzuverbreiten und andere vor Ansteckung zu schützen, sollten'
        ' Erkrankte die Regeln der sogenannten Husten-Etiquette beachten, die auch beim Niesen gilt:\n\n'
    ' • Halten Sie beim Husten oder Niesen mindestens einen Meter Abstand von anderen'
    ' Personen und drehen Sie sich weg.\n\n'
    ' • Niesen oder husten Sie am besten in ein Einwegtaschentuch. Verwenden Sie dies nur'
    ' einmal und entsorgen es anschließend in einem Mülleimer mit Deckel. Wird ein'
        ' Stofftaschentuch benutzt, sollte dies anschließend bei 60°C gewaschen werden.\n\n'
        ' • Und immer gilt: Nach dem Naseputzen, Niesen oder Husten gründlich die Hände'
    ' waschen!\n\n'
        ' • Ist kein Taschentuch griffbereit, sollten Sie sich beim Husten und Niesen die Armbeuge vor'
    ' Mund und Nase halten und ebenfalls sich dabei von anderen Personen abwenden.'
    )
  ]),
  Entry('Soziale Distanzierung', <SubEntry>[
    SubEntry(
      'Defintion',
      'Auch räumliche Trennung oder physische Distanzierung genannt, ist eine Reihe von'
          'nicht-pharmazeutischen Maßnahmen zur Infektionskontrolle, die die'
          ' Ausbreitung einer ansteckenden Krankheit stoppen oder verlangsamen sollen.'
          'Die Maßnahmen bezwecken, den Abstand zwischen Menschen zu vergrößern'
          ' und so die Anzahl von Infektionen etwa durch Tröpfcheninfektionen'
          ' zu verringern.',
    ),
    SubEntry(
        'Wie passe ich mein privates und Familienleben an? ',
        '• Bleiben Sie, so oft es geht, zu Hause. Schränken Sie insbesondere die persönlichen'
        'Begegnungen mit älteren, hochbetagten oder chronisch kranken Menschen zu deren'
    'Schutz ein. Nutzen Sie stattdessen vermehrt die Kommunikation per Telefon, E-Mail,'
        'Chats, etc.'
    'Beachten Sie Besuchsregelungen für Krankenhäuser und sonstige Pflegeeinrichtungen.'
    '• Lüften Sie alle Aufenthaltsräume regelmäßig und vermeiden Sie Berührungen wie z. B.'
    'Händeschütteln oder Umarmungen.\n\n'
    '• Wenn eine Person in Ihrem Haushalt erkrankt ist, sorgen Sie nach Möglichkeit für eine'
    'räumliche Trennung und genügend Abstand zu den übrigen Haushaltsmitgliedern.\n\n'
    '• Kaufen Sie nicht zu Stoßzeiten ein, sondern dann, wenn die Geschäfte weniger voll sind'
    'oder nutzen Sie Abhol- und Lieferservices.\n\n'
        '• Helfen Sie denen, die Hilfe benötigen! Versorgen Sie ältere, hochbetagte, chronisch kranke'
        'Angehörige oder Nachbarn und alleinstehende und hilfsbedürftige Menschen mit'
    'Lebensmitteln und Dingen des täglichen Bedarfs.'
    ),
    SubEntry(
        'Wie passe ich das an meine Arbeit an?',
          '• Arbeiten Sie – in Abstimmung mit dem Arbeitgeber – wenn möglich, von zu Hause aus.'
          'Halten Sie Treffen klein und kurz und in einem gut belüfteten Raum ab. Halten Sie einen'
          'Abstand von 1 bis 2 Metern zu anderen Menschen und verzichten Sie auf persönliche'
          'Berührungen.\n\n'
          '• Nehmen Sie Ihre Mahlzeiten möglichst allein (z. B. im Büro) ein.\n\n'
          '• Bleiben Sie zu Hause, wenn Sie krank sind und kurieren Sie sich aus!'
    )
  ]),
  Entry('Quarantäne', <SubEntry>[
    SubEntry(
        'Wer muss in Quarantäne?',
        'In Quarantäne muss, wer ein hohes Risiko hat, sich angesteckt zu haben. Dies ist der Fall,\n\n'
        '• wenn man innerhalb der letzten 14 Tage engen Kontakt zu einem laborbestätigten COVID-19-'
    'Patienten hatte.\n\n'
        'Ein wirklich enger Kontakt bedeutet, dass man mindestens 15 Minuten mit dem Erkrankten'
        'gesprochen hat, bzw. angehustet oder angeniest worden ist, während dieser ansteckend'
    'gewesen ist\n\n'
    '• wenn man sich innerhalb der letzten 14 Tage in einem internationalen Risikogebiet bzw.'
    'besonders betroffenen Gebieten in Deutschland aufgehalten hat\n\n'
        '• immer, wenn das Gesundheitsamt dies anordnet'
        'Nicht in Quarantäne muss, wer ein geringeres Risiko hat, sich angesteckt zu haben. Dies ist der'
        'Fall, wenn man\n\n'
        '• innerhalb der letzten 14 Tage im gleichen Raum mit einer Person mit einer festgestellten'
    'COVID-19-Erkrankung war, ohne einen engen Kontakt\n\n'
    '• in einem Gebiet mit steigenden Fallzahlen von COVID-19 war\n\n'
    'Wer mit Menschen mit Vorerkrankungen arbeitet, z. B. im Krankenhaus oder in der Altenpflege,'
        'sollte aber in jedem Fall seinen Betriebsarzt informieren.'
    ),
    SubEntry(
        'Sollte bei einer Atemwegserkrankung ärztlicher Rat'
        'eingeholt und ein Test veranlasst werden, auch wenn'
        'die Krankheitszeichen nur leicht sind (Husten, Niesen,'
        'Halsschmerzen etc.)?',
        'Ja, wenn:\n\n'
        '• man in den letzten zwei Wochen Kontakt hatte zu einem Erkrankten, bei dem im Labor'
        'eine COVID-19 Diagnose gestellt wurde,\n\n'
        '• man in einem Gebiet war, in dem es bereits zu vielen COVID-19 Erkrankungen gekommen ist\n\n'
        '• eine Vorerkrankung besteht oder die Atemwegserkrankung schlimmer wird (Atemnot,'
        'hohes Fieber etc.)\n\n'
        '• wenn man bei der Arbeit oder ehrenamtlichen Tätigkeit mit Menschen in Kontakt kommt,'
            'die ein hohes Risiko für schwere Erkrankungen haben (z.B. im Krankenhaus oder der'
        'Altenpflege)\n\n'
        'Schon bevor das Testergebnis vorliegt, sollte man sich selbst isolieren, d. h. zu Hause bleiben,'
        'alle engen Kontakte unter 2 Metern meiden, gute Händehygiene einhalten und bei Kontakt zu'
        'anderen (falls vorhanden) einen Mund-Nasenschutz tragen.'
    ),
    SubEntry(
        'Ist ein Test auf das Coronavirus bei Personen ohne'
        'jedes Krankheitszeichen sinnvoll?',
        'Eine Laboruntersuchung sollte nur durchgeführt werden, wenn Krankheitszeichen vorliegen, um'
        'deren Ursache abzuklären.'
        'Bei einer gesunden Person sagt ein negatives Testergebnis auf das neuartige Coronavirus nichts'
        'darüber aus, ob diese Person nicht doch noch krank werden kann.'
        'Zudem würden damit die Laborkapazitäten unnötig belastet.\n\n'
        'Weitere Informationen finden Sie auf infektionsschutz.de'
    ),
  ])
];

// Displays one Entry. If the entry has children then it's displayed
// with an ExpansionTile.
class EntryItem extends StatelessWidget {
  const EntryItem(this.entry);

  final Entry entry;

  Widget _buildTiles(Entry root) {
    return ExpansionTile(
        key: PageStorageKey<Entry>(root),
        title:
          Text(
              root.title,
              style:
              TextStyle(
//                            fontStyle: FontStyle.italic,
                  fontSize: 20
              )
          ),
        children: List.generate(root.children.length, (i) {
          return ExpansionTile(
            key: PageStorageKey<SubEntry>(root.children[i]),
            title:
              Text(
                  root.children[i].title,
                  style:
                  TextStyle(
//                            fontStyle: FontStyle.italic,
                      fontSize: 20
                  )
              ),
            children: <Widget>[
              new Container(
                child: Text(root.children[i].description,
                    style:
                        TextStyle(
//                            fontStyle: FontStyle.italic,
                            fontSize: 20
                        )),
                margin: const EdgeInsets.only(
                    top: 10.0, left: 10.0, right: 10.0, bottom: 10.0),
                decoration: BoxDecoration(
                  color: Colors.blueGrey[100],
                ),
              )
            ],
          );
        }));

//
//        ExpansionTile(
//          key: PageStorageKey<SubEntry>(root.subEntry),
//          title: Text(root.subEntry.title),
//          children: <Widget> [
//
//          ]
//        )
    //],
    //);
  }

  @override
  Widget build(BuildContext context) {
    return _buildTiles(entry);
  }
}
