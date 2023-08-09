import 'package:flutter/material.dart';
import 'package:home_widget/home_widget.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();

  HomeWidget.registerBackgroundCallback(backgroundCallback);
  runApp(MyApp());
}

// Called when Doing Background Work initiated from Widget
Future<void> backgroundCallback(Uri? uri) async {
  print("onbackgroundcallback $uri");
  if (uri?.host == 'updatecounter') {
    int? data = 0;
    await HomeWidget.getWidgetData<int>('_counter', defaultValue: 0)
        .then((value) {
      data = value ?? 0;
    });
    await HomeWidget.saveWidgetData<int>('_counter', data);
    await HomeWidget.updateWidget(
        name: 'AppWidgetProvider', iOSName: 'AppWidgetProvider');
  }
  var data = uri?.host.split(',');
  if (data?[0] == 'onnumberbtnclick') {
    String? numberData = "";
    if (data?[1] == '-') {
      await HomeWidget.getWidgetData<String>('_number', defaultValue: "")
          .then((value) {
        print("num:$value");
        numberData = value?.substring(0, value.length - 1);
        print("value:$value");
      });
      await HomeWidget.saveWidgetData<String>('_number', numberData);
      await HomeWidget.updateWidget(
          name: 'AppWidgetProvider', iOSName: 'AppWidgetProvider');
    } else if (data?[1] == 'c') {
      final result = await HomeWidget.getWidgetData<String>('_number', defaultValue: "");
      if (result?.isNotEmpty??false) {
        // numberData = "";
        numberData = result?.substring(0, result.length - 1)??'';
      }
      final saveResult = await HomeWidget.saveWidgetData<String>('_number', numberData);
      if(saveResult!=null){
        await HomeWidget.updateWidget(
            name: 'AppWidgetProvider', iOSName: 'AppWidgetProvider');
      }
    } else {
      await HomeWidget.getWidgetData<String>('_number', defaultValue: "")
          .then((value) {
        print("num:$value");
        numberData = '$value${data?[1]}';
        print("value:$value");
      });
      await HomeWidget.saveWidgetData<String>('_number', numberData);
      await HomeWidget.updateWidget(
          name: 'AppWidgetProvider', iOSName: 'AppWidgetProvider');
    }
  }
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      color: Colors.transparent.withOpacity(0.5),
      routes: <String, WidgetBuilder>{
        '/BottomsheetWidget': (BuildContext context) => BottomsheetWidget(),
      },
      title: 'Flutter Demo',
      theme: ThemeData(
        canvasColor: Colors.transparent.withOpacity(0.2),
        bottomSheetTheme: BottomSheetThemeData(
            backgroundColor: Colors.transparent.withOpacity(0.5)),
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class BottomsheetWidget extends StatefulWidget {
  const BottomsheetWidget({super.key});

  @override
  State<BottomsheetWidget> createState() => _BottomsheetWidgetState();
}

class _BottomsheetWidgetState extends State<BottomsheetWidget> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _modalBottomSheetMenu();
  }

  @override
  Widget build(BuildContext context) {
    return const Opacity(
      opacity: 0.3,
    );
  }

  void _modalBottomSheetMenu() {
    WidgetsBinding.instance.addPostFrameCallback((_) async {
      await showModalBottomSheet(
          barrierColor: Colors.transparent.withOpacity(0.2),
          context: context,
          builder: (builder) {
            return Container(
              height: 350.0,
              color: Colors.transparent,
              //could change this to Color(0xFF737373),
              //so you don't have to change MaterialApp canvasColor
              child: Container(
                  decoration: const BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.only(
                          topLeft: Radius.circular(10.0),
                          topRight: Radius.circular(10.0))),
                  child: const Center(
                    child: Text("This is a modal sheet"),
                  )),
            );
          });
    });
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _counter = '0';

  @override
  void initState() {
    super.initState();
    HomeWidget.widgetClicked.listen((Uri? uri) => loadData());
    loadData(); // This will load data from widget every time app is opened
  }

  void loadData() async {
    await HomeWidget.getWidgetData<String>('_number', defaultValue: "")
        .then((value) {
      _counter = value ?? "0";
    });
    setState(() {});
  }

  Future<void> updateAppWidget() async {
    await HomeWidget.saveWidgetData<String>('_number', _counter);
    await HomeWidget.updateWidget(
        name: 'AppWidgetProvider', iOSName: 'AppWidgetProvider');
  }

  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      //  _counter++;
    });
    updateAppWidget();
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Container(
          height: 100,
          width: double.infinity,
          child: Text(
            "N: $_counter",
            style: Theme.of(context)
                .textTheme
                .headline4
                ?.copyWith(color: Colors.black),
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
