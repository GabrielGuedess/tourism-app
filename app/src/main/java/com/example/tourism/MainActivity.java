package com.example.tourism;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.animation.CameraAnimationsUtils;
import com.mapbox.maps.plugin.animation.Cancelable;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private LocationComponentPlugin location;

    private LocationManager locationManager;

    private View bottomSheet;

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);

        mapView.getMapboxMap().loadStyleUri(Style.DARK);

        bottomSheet = findViewById(R.id.bottomSheet);
        BottomSheetBehavior.from(bottomSheet).setPeekHeight(86);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);

            return;
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60, 1, locationListenerGPS);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        final Cancelable cancelable = CameraAnimationsUtils.getCamera(mapView).easeTo(
                new CameraOptions.Builder()
                        .center(Point.fromLngLat(lastKnownLocation.getLongitude(), lastKnownLocation.getLatitude()))
                        .zoom(12.0)
                        .pitch(40.0)
                        .build(),
                new MapAnimationOptions.Builder()
                        .duration(5000)
                        .build()
        );

        List<List<Point>> pointsBosqueMaia = List.of(List.of(
                Point.fromLngLat(-46.53339359444723, -23.45451046687174),
                Point.fromLngLat(-46.528378467719534, -23.459765977969326),
                Point.fromLngLat(-46.52800721404722, -23.4602204967362),
                Point.fromLngLat(-46.527405755245184, -23.46187570705507),
                Point.fromLngLat(-46.526984734083726, -23.46186782514974),
                Point.fromLngLat(-46.526469197967714, -23.461710186956537),
                Point.fromLngLat(-46.52598803092653, -23.462261919809933),
                Point.fromLngLat(-46.52578181647971, -23.462190982857976),
                Point.fromLngLat(-46.52634031393907, -23.461387028072707),
                Point.fromLngLat(-46.52663245107118, -23.461063868396465),
                Point.fromLngLat(-46.52642623662507, -23.46031508074256),
                Point.fromLngLat(-46.52776663052626, -23.45973181162684),
                Point.fromLngLat(-46.52876333368417, -23.45757211706254),
                Point.fromLngLat(-46.52905993456258, -23.457071457304593),
                Point.fromLngLat(-46.52903672593365, -23.456915325888787),
                Point.fromLngLat(-46.52878143101299, -23.45663854701533),
                Point.fromLngLat(-46.52884332069027, -23.456454027444792),
                Point.fromLngLat(-46.528990308780465, -23.45630499219665),
                Point.fromLngLat(-46.529841291850545, -23.456077890575614),
                Point.fromLngLat(-46.52991091773748, -23.455914661045327),
                Point.fromLngLat(-46.53018168507808, -23.455687558752956),
                Point.fromLngLat(-46.529980543625044, -23.455141092262878),
                Point.fromLngLat(-46.53018168507808, -23.455048831462477),
                Point.fromLngLat(-46.53018168507808, -23.454644302581258),
                Point.fromLngLat(-46.532858413642145, -23.45417589917072),
                Point.fromLngLat(-46.533260696547586, -23.45419719027008),
                Point.fromLngLat(-46.53339994832268, -23.45435332490092),
                Point.fromLngLat(-46.53339994832268, -23.45447397426068)
        ));

        List<List<Point>> pointsShoppingInternacional = List.of(List.of(
                Point.fromLngLat(-46.55115547756111, -23.486541012137025),
                Point.fromLngLat(-46.55133879657643, -23.48747219989312),
                Point.fromLngLat(-46.55104442854226, -23.48752523190659),
                Point.fromLngLat(-46.55075302591645, -23.487576450774377),
                Point.fromLngLat(-46.550162187850646, -23.487684655040198),
                Point.fromLngLat(-46.55024507619052, -23.487851368261403),
                Point.fromLngLat(-46.55020823876548, -23.488005388899808),
                Point.fromLngLat(-46.55021363046188, -23.488175716975917),
                Point.fromLngLat(-46.5500578236865, -23.4881841874126),
                Point.fromLngLat(-46.55006876404704, -23.48827074578901),
                Point.fromLngLat(-46.549921443225514, -23.48836073757809),
                Point.fromLngLat(-46.54994581598575, -23.488504069250695),
                Point.fromLngLat(-46.54990274523166, -23.48867309514509),
                Point.fromLngLat(-46.54977954289808, -23.48878856745594),
                Point.fromLngLat(-46.549789334124256, -23.48882079618886),
                Point.fromLngLat(-46.54962284220454, -23.488927563668057),
                Point.fromLngLat(-46.54957293320149, -23.489343934086833),
                Point.fromLngLat(-46.54949198870919, -23.489462485766282),
                Point.fromLngLat(-46.54949836247058, -23.489574640401436),
                Point.fromLngLat(-46.549459207767825, -23.4896348488644),
                Point.fromLngLat(-46.54949032207591, -23.489652093341647),
                Point.fromLngLat(-46.549343748131264, -23.489929682099856),
                Point.fromLngLat(-46.54912107848122, -23.489809748946286),
                Point.fromLngLat(-46.548943330197005, -23.49006861838471),
                Point.fromLngLat(-46.548213027910066, -23.489660657858096),
                Point.fromLngLat(-46.54827403605833, -23.489566577385574),
                Point.fromLngLat(-46.54807813779547, -23.48942952704482),
                Point.fromLngLat(-46.548083158804786, -23.48904505443373),
                Point.fromLngLat(-46.54815330494674, -23.488950265730622),
                Point.fromLngLat(-46.548166692210486, -23.488640987912987),
                Point.fromLngLat(-46.547958695005576, -23.48862505252474),
                Point.fromLngLat(-46.54797401318481, -23.48820716978284),
                Point.fromLngLat(-46.54826991907157, -23.48808215917444),
                Point.fromLngLat(-46.54829089771357, -23.48780085598146),
                Point.fromLngLat(-46.549504140141636, -23.48718759552628),
                Point.fromLngLat(-46.54952350759514, -23.487104959294),
                Point.fromLngLat(-46.54953769574524, -23.48709338607899),
                Point.fromLngLat(-46.54953217743352, -23.48707437953134),
                Point.fromLngLat(-46.549615903214615, -23.48700951773882),
                Point.fromLngLat(-46.54970128550395, -23.487089663024282),
                Point.fromLngLat(-46.5496826081843, -23.486826597423857),
                Point.fromLngLat(-46.550094257321064, -23.48675744446423),
                Point.fromLngLat(-46.55009889089055, -23.48669794880476),
                Point.fromLngLat(-46.55115547756111, -23.486541012137025)
        ));

        List<List<Point>> pointsIgreja = List.of(List.of(
                Point.fromLngLat(-46.531480992636546, -23.470180858442603),
                Point.fromLngLat(-46.53079350477421, -23.470100834592174),
                Point.fromLngLat(-46.53082219014665, -23.47015665719054),
                Point.fromLngLat(-46.5308583586106, -23.470209734189986),
                Point.fromLngLat(-46.53093368877646, -23.47031726098885),
                Point.fromLngLat(-46.53096107341156, -23.470356022651146),
                Point.fromLngLat(-46.53126694723207, -23.470332259666662),
                Point.fromLngLat(-46.531253108657864, -23.470363087121),
                Point.fromLngLat(-46.53140137909409, -23.470373967397705),
                Point.fromLngLat(-46.53146266420748, -23.47034132656485),
                Point.fromLngLat(-46.531480992636546, -23.470180858442603)
        ));

        List<List<Point>> pointsZoologico = List.of(List.of(
                Point.fromLngLat(-46.554646932206, -23.44388000760243),
                Point.fromLngLat(-46.55392906536042, -23.444071824764464),
                Point.fromLngLat(-46.55369842307189, -23.44327858480645),
                Point.fromLngLat(-46.55290552713683, -23.443735664067333),
                Point.fromLngLat(-46.55260773826963, -23.443331094654745),
                Point.fromLngLat(-46.55221086099522, -23.442798958765124),
                Point.fromLngLat(-46.55095915218564, -23.44326244255008),
                Point.fromLngLat(-46.550777181712505, -23.443355868914054),
                Point.fromLngLat(-46.55076982045111, -23.443346204840523),
                Point.fromLngLat(-46.55071287259702, -23.44338070425205),
                Point.fromLngLat(-46.55052225286147, -23.443079777119507),
                Point.fromLngLat(-46.5504777500951, -23.44310539843606),
                Point.fromLngLat(-46.55066621347058, -23.443380072388237),
                Point.fromLngLat(-46.55059237988041, -23.44342219010001),
                Point.fromLngLat(-46.55056623885872, -23.44342356809929),
                Point.fromLngLat(-46.550513256892714, -23.4434457164091),
                Point.fromLngLat(-46.55045924970423, -23.44333354184485),
                Point.fromLngLat(-46.55051036323647, -23.443310066113213),
                Point.fromLngLat(-46.55049465018874, -23.44328364310062),
                Point.fromLngLat(-46.550463055458664, -23.443305217749767),
                Point.fromLngLat(-46.55042602406289, -23.443240372341364),
                Point.fromLngLat(-46.55046052119863, -23.4432170746706),
                Point.fromLngLat(-46.550367952606706, -23.443084137148702),
                Point.fromLngLat(-46.55043307431235, -23.44304962817194),
                Point.fromLngLat(-46.55037716007538, -23.442960220770015),
                Point.fromLngLat(-46.550665185421295, -23.442798268440782),
                Point.fromLngLat(-46.55070035968951, -23.442843560014488),
                Point.fromLngLat(-46.550742555790606, -23.442817987023957),
                Point.fromLngLat(-46.55083252763947, -23.442951584561385),
                Point.fromLngLat(-46.55083004173659, -23.442971058804332),
                Point.fromLngLat(-46.550877935515786, -23.44291792507076),
                Point.fromLngLat(-46.55164051838963, -23.44240625337563),
                Point.fromLngLat(-46.552049666567825, -23.442124859870514),
                Point.fromLngLat(-46.55242051172715, -23.441891386448674),
                Point.fromLngLat(-46.5528465228262, -23.44132579385007),
                Point.fromLngLat(-46.55292741307028, -23.44134662621731),
                Point.fromLngLat(-46.55306350562256, -23.441303134525455),
                Point.fromLngLat(-46.553068979008515, -23.44126009224486),
                Point.fromLngLat(-46.55346148984448, -23.44112365265041),
                Point.fromLngLat(-46.55353016606128, -23.441146948632237),
                Point.fromLngLat(-46.5535614835328, -23.44129116979694),
                Point.fromLngLat(-46.55356627891331, -23.441347143966652),
                Point.fromLngLat(-46.55364319725105, -23.441546371777598),
                Point.fromLngLat(-46.55373404395934, -23.441611422032665),
                Point.fromLngLat(-46.553848191596174, -23.441641231085583),
                Point.fromLngLat(-46.55390314365815, -23.441661456067038),
                Point.fromLngLat(-46.55450531070426, -23.441843322706845),
                Point.fromLngLat(-46.55436904598017, -23.442221350964125),
                Point.fromLngLat(-46.55430091361886, -23.44242633849012),
                Point.fromLngLat(-46.55430166836481, -23.442535221587505),
                Point.fromLngLat(-46.55437902914849, -23.44258021110295),
                Point.fromLngLat(-46.55445215625332, -23.44267166042343),
                Point.fromLngLat(-46.55495404196644, -23.443263244218365),
                Point.fromLngLat(-46.55532655894331, -23.443712100248575),
                Point.fromLngLat(-46.5546802696515, -23.443873489957),
                Point.fromLngLat(-46.554646932206, -23.44388000760243)
        ));

        List<List<Point>> pointsShoppingMaia = List.of(List.of(
                Point.fromLngLat(-46.54025254908035, -23.442644909617357),
                Point.fromLngLat(-46.540892151247334, -23.443123170516387),
                Point.fromLngLat(-46.5401557730475, -23.444448533375805),
                Point.fromLngLat(-46.539128595814645, -23.443910644207833),
                Point.fromLngLat(-46.53908169274382, -23.44383318798758),
                Point.fromLngLat(-46.539077002437324, -23.443727723929896),
                Point.fromLngLat(-46.539288066252084, -23.443441603066645),
                Point.fromLngLat(-46.539541342830205, -23.443075835882468),
                Point.fromLngLat(-46.53980400002226, -23.44286498139934),
                Point.fromLngLat(-46.5400377681361, -23.442735915371557),
                Point.fromLngLat(-46.54025254908035, -23.442644909617357)
        ));

        List<List<Point>> pointsLagoDosPatos = List.of(List.of(
                Point.fromLngLat(-46.563805979637266, -23.45147177084337),
                Point.fromLngLat(-46.56259178508839, -23.451902745595234),
                Point.fromLngLat(-46.56250081283049, -23.45193063373673),
                Point.fromLngLat(-46.562400573432654, -23.4519393932285),
                Point.fromLngLat(-46.562023600476806, -23.45190969999409),
                Point.fromLngLat(-46.561925095517665, -23.451899673699078),
                Point.fromLngLat(-46.561798789137725, -23.451910901459563),
                Point.fromLngLat(-46.56172688560798, -23.451939733194138),
                Point.fromLngLat(-46.56160161282821, -23.4520101490876),
                Point.fromLngLat(-46.56155534519365, -23.452120998430118),
                Point.fromLngLat(-46.56156004682924, -23.45220634294678),
                Point.fromLngLat(-46.56156004682924, -23.45230972204456),
                Point.fromLngLat(-46.56161639084024, -23.45239242526398),
                Point.fromLngLat(-46.56164900482305, -23.452443599003622),
                Point.fromLngLat(-46.561714053794134, -23.452482020360563),
                Point.fromLngLat(-46.561886842096584, -23.45255093962372),
                Point.fromLngLat(-46.562044605329305, -23.45260607500839),
                Point.fromLngLat(-46.56216856215477, -23.45266810228928),
                Point.fromLngLat(-46.56232632538752, -23.452733575498286),
                Point.fromLngLat(-46.56260485135758, -23.4528467763095),
                Point.fromLngLat(-46.563021234864266, -23.45301958966094),
                Point.fromLngLat(-46.56325036717769, -23.453121710003202),
                Point.fromLngLat(-46.56385911767366, -23.453392019956368),
                Point.fromLngLat(-46.56450493672935, -23.453653828446434),
                Point.fromLngLat(-46.564977560867476, -23.45259326072336),
                Point.fromLngLat(-46.56481075234785, -23.45235521629641),
                Point.fromLngLat(-46.564479452094304, -23.452008775873338),
                Point.fromLngLat(-46.56412266720571, -23.451730347634438),
                Point.fromLngLat(-46.563805979637266, -23.45147177084337)
        ));

        List<List<Point>> pointsParqueJulioFracalanza = List.of(List.of(
                Point.fromLngLat(-46.544970483561286, -23.48056401406079),
                Point.fromLngLat(-46.545590782465695, -23.481501641415946),
                Point.fromLngLat(-46.54663385492424, -23.48101534738329),
                Point.fromLngLat(-46.547067022371664, -23.480341527234373),
                Point.fromLngLat(-46.546640785638175, -23.48013174971382),
                Point.fromLngLat(-46.54622494279744, -23.480049110780413),
                Point.fromLngLat(-46.545569990323585, -23.480303384005673),
                Point.fromLngLat(-46.544970483561286, -23.48056401406079)
        ));

        List<List<Point>> pointsAdamastor = List.of(List.of(
                Point.fromLngLat(-46.518624845666636, -23.469281763184185),
                Point.fromLngLat(-46.51832436850398, -23.46923947230742),
                Point.fromLngLat(-46.51825123649601, -23.469695921396493),
                Point.fromLngLat(-46.51839750051195, -23.469713421010965),
                Point.fromLngLat(-46.518384781902, -23.469768836439755),
                Point.fromLngLat(-46.51890783474025, -23.469846126342034),
                Point.fromLngLat(-46.518901475434745, -23.469884042126537),
                Point.fromLngLat(-46.518279853368654, -23.46978925264534),
                Point.fromLngLat(-46.518241697538826, -23.470003622606768),
                Point.fromLngLat(-46.5190906647596, -23.470126119571916),
                Point.fromLngLat(-46.519125640936664, -23.469900083416974),
                Point.fromLngLat(-46.51931165060881, -23.469921957901136),
                Point.fromLngLat(-46.51940386053221, -23.46941009403676),
                Point.fromLngLat(-46.51865346253928, -23.469297804547836),
                Point.fromLngLat(-46.51861689653472, -23.46952530003672),
                Point.fromLngLat(-46.51858827966214, -23.469520925126844),
                Point.fromLngLat(-46.518624845666636, -23.469281763184185)
        ));





        AnnotationPlugin annotationAPI = AnnotationPluginImplKt.getAnnotations((MapPluginProviderDelegate) mapView);

        PolygonAnnotationManager polygonAnnotationManager = PolygonAnnotationManagerKt.createPolygonAnnotationManager(annotationAPI, mapView);

        PolygonAnnotationOptions polygonAnnotationOptionsBosque = new PolygonAnnotationOptions()
                .withPoints(pointsBosqueMaia)
                .withFillColor("#FFFFFF")
                .withFillOpacity(0.4);


        PolygonAnnotationOptions polygonAnnotationOptionsInter = new PolygonAnnotationOptions()
                .withPoints(pointsShoppingInternacional)
                .withFillColor("#FFFFFF")
                .withFillOpacity(0.4);

        PolygonAnnotationOptions polygonAnnotationOptionsIgreja = new PolygonAnnotationOptions()
                .withPoints(pointsIgreja)
                .withFillColor("#FFFFFF")
                .withFillOpacity(0.4);

        PolygonAnnotationOptions polygonAnnotationOptionsZoologico = new PolygonAnnotationOptions()
                .withPoints(pointsZoologico)
                .withFillColor("#FFFFFF")
                .withFillOpacity(0.4);

        PolygonAnnotationOptions polygonAnnotationOptionsMaia = new PolygonAnnotationOptions()
                .withPoints(pointsShoppingMaia)
                .withFillColor("#FFFFFF")
                .withFillOpacity(0.4);

        PolygonAnnotationOptions polygonAnnotationOptionsLago = new PolygonAnnotationOptions()
                .withPoints(pointsLagoDosPatos)
                .withFillColor("#FFFFFF")
                .withFillOpacity(0.4);

        PolygonAnnotationOptions polygonAnnotationOptionsJulio = new PolygonAnnotationOptions()
                .withPoints(pointsParqueJulioFracalanza)
                .withFillColor("#FFFFFF")
                .withFillOpacity(0.4);

        PolygonAnnotationOptions polygonAnnotationOptionsAdamastor = new PolygonAnnotationOptions()
                .withPoints(pointsAdamastor)
                .withFillColor("#FFFFFF")
                .withFillOpacity(0.4);


        CircleAnnotationManager circleAnnotationManager = CircleAnnotationManagerKt.createCircleAnnotationManager(annotationAPI, mapView);

        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationAPI, mapView);

        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.529687773412746, -23.457351838840353))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));

        PointAnnotationOptions pointAnnotationOptionsShopping = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.549118601134445, -23.4879523668775))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));

        PointAnnotationOptions pointAnnotationOptionsIgreja = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.53104863144423, -23.470117607763537))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));

        PointAnnotationOptions pointAnnotationOptionsZoologica = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.55328043165855,
                        -23.442689862086453))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));

        PointAnnotationOptions pointAnnotationOptionsShoppingMaia = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.540031569708674,
                        -23.443413274698784))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));

        PointAnnotationOptions pointAnnotationOptionsLagoDosPatos = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.56339968861684,
                        -23.45235282995284423668775))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));

        PointAnnotationOptions pointAnnotationOptionsJulio = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.54599142460762,
                        -23.480656207654988
                ))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));

        PointAnnotationOptions pointAnnotationOptionsAdamastor = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.51885886375584,
                        -23.46960231735447))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));


        pointAnnotationManager.create(pointAnnotationOptions);
        pointAnnotationManager.create(pointAnnotationOptionsShopping);
        pointAnnotationManager.create(pointAnnotationOptionsIgreja);
        pointAnnotationManager.create(pointAnnotationOptionsZoologica);
        pointAnnotationManager.create(pointAnnotationOptionsShoppingMaia);
        pointAnnotationManager.create(pointAnnotationOptionsLagoDosPatos);
        pointAnnotationManager.create(pointAnnotationOptionsJulio);
        pointAnnotationManager.create(pointAnnotationOptionsAdamastor);

        CircleAnnotationOptions circleAnnotationOptions = new CircleAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(lastKnownLocation.getLongitude(), lastKnownLocation.getLatitude(), 17))
                .withCircleRadius(8.0)
                .withCircleColor("#7C3AED")
                .withCircleStrokeWidth(2.0)
                .withCircleStrokeColor("#ffffff");

        polygonAnnotationManager.create(polygonAnnotationOptionsBosque);
        polygonAnnotationManager.create(polygonAnnotationOptionsInter);
        polygonAnnotationManager.create(polygonAnnotationOptionsIgreja);
        polygonAnnotationManager.create(polygonAnnotationOptionsZoologico);
        polygonAnnotationManager.create(polygonAnnotationOptionsMaia);
        polygonAnnotationManager.create(polygonAnnotationOptionsLago);
        polygonAnnotationManager.create(polygonAnnotationOptionsJulio);
        polygonAnnotationManager.create(polygonAnnotationOptionsAdamastor);
        circleAnnotationManager.create(circleAnnotationOptions);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}