<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Markers Map</title>
    <link rel="stylesheet" href="https://openlayers.org/en/v4.1.0/css/ol.css" type="text/css">
    <script src="https://openlayers.org/en/v4.1.0/build/ol.js"></script>
    <link rel="stylesheet" href="https://openlayers.org/en/v3.19.1/css/ol.css" type="text/css">
    <style>
        .body-background {
            background-image: url("images/background.jpg");
            color: #7b68ee;
            text-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
        }
        .map {
            border: 2px solid #7b68ee;
            height: 515px;
            width: 100%;
        }
        .text {
            text-align:  center;
            color: #7b68ee;
        }
    </style>
    <script src="https://openlayers.org/en/v4.1.0/build/ol.js"></script>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
</head>
<h1 class="text">ADDING AND DELETING MARKERS</h1>
<body class="body-background">
<div id="map" class="map"></div>
<form class="form-inline">
    <label>Marker type</label>
    <select id="type">
        <option value="Point">Point</option>
        <option value="LineString">LineString</option>
        <option value="Polygon">Polygon</option>
        <option value="None">Delete(ctrl)</option>
    </select>
</form>
<script>

    var rastr = new ol.layer.Tile({
        source: new ol.source.OSM()
    });

    var map = new ol.Map({
        layers: [rastr],
        target: 'map',
        view: new ol.View({
            center: [-35720000, 6720000],
            zoom: 7
        })
    });

    var features = new ol.Collection();

    var svector = new ol.source.Vector({
        wrapX: false, features: features
    });

    var foverlay = new ol.layer.Vector({
        source: svector,
        style: new ol.style.Style({
            fill: new ol.style.Fill({
                color: 'rgba(0, 0, 0, 0.1)'
            }),
            stroke: new ol.style.Stroke({
                color: '#ff0000',
                width: 2
            }),
            image: new ol.style.Circle({
                radius: 5,
                fill: new ol.style.Fill({
                    color: '#ff0000'
                })
            })
        })
    });

    foverlay.setMap(map);


    var featureID = 0;

    var typeSelect = document.getElementById('type');

    var draw;

    function addInteraction() {

        var value = typeSelect.value;
        if (value !== 'None') {

            draw = new ol.interaction.Draw({
                features: features,
                type: (typeSelect.value)
            });

            draw.on('drawend', function (event) {
                var feature = event.feature;
                featureID = featureID + 1;  //dobavlyaem id k nashim elementam
                console.log(featureID); // smotrim id nashego elementa v consoli
                feature.setProperties({
                    'id': featureID
                });

                $.ajax({
                    url: '/Insert',
                    data: {
                        wktCoordinates: new ol.format.WKT().writeFeature(feature)
                    },
                    type: 'post'
                });
            });
            map.addInteraction(draw);
        }
    }

    var modify = new ol.interaction.Modify({
        features: features
    });

    map.addInteraction(modify);

    modify.on('modifyend', function (event) {
        event.features.forEach(function (elem) {
            $.ajax({
                url: '/Update',
                data: {
                    idToUpdate: elem.getProperties().id,
                    coordinatesToUpdate: new ol.format.WKT().writeFeature(elem)
                },
                type: 'post'
            });
        })
    });

    var select = new ol.interaction.Select;

    select.on('select', function (event) {
        if (event.selected.length > 0) {
            var properties = event.selected[0].getProperties();

            $.ajax({
                url: '/Delete',
                data: {
                    idToDelete: properties.id
                },
                type: 'post'
            });

            var features = svector.getFeatures();

            for (el in features) {
                var featureProperties = features[el].getProperties();

                var featureId = featureProperties.id;

                if (featureId == properties.id) {
                    svector.removeFeature(features[el]);
                }
            }

            select.getFeatures().clear();
        }
    });

    typeSelect.onchange = function() {
        map.removeInteraction(draw);
        addInteraction();
    };

    addInteraction();

    $(document).keydown(function (e) {                             // ydalyaem elementi na Ctrl(f-ya select)
        if (e.keyCode == 17 && typeSelect.value == "None") {       // tolko pri vibrannom "None", on zhe Delete(ctrl)
            map.removeInteraction(modify);
            map.addInteraction(select);
        }
    });

    $(document).keyup(function (e) {
        if (e.keyCode == 17 && typeSelect.value == "None") {
            map.addInteraction(modify);
            map.removeInteraction(select);
        }
    });

    $.ajax({
        url: '/Select',
        data: {
        },
        success: function (allFeatures) {
//            if (jQuery.isEmptyObject(allFeatures)) {
//                $.ajax({
//                    url: '/Select',
//                    data: {
//                    },
//                    success: function (responseText) {
//                        featureID = parseInt(responseText - 1);
//                    }
//                });
//            }

            var format = new ol.format.WKT();

            for (var id in allFeatures) {
                var feature = format.readFeature(allFeatures[id.toString()]);

                feature.setProperties({
                    'id': id.toString()
                });

                svector.addFeature(feature);

                featureID = parseInt(id);
            }

        },
        type: 'get'
    });

</script>
</body>
</html>