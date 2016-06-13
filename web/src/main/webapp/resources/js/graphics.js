var tid;
var map, pie, piePath, pieArc;

$(function() {
    init();
    startUpdating();
});

function startUpdating() {
    if (tid) {
        clearInterval(tid);
        resetMap();
        resetPieChart();
    }
    tid = setInterval(update, 1000);
}

function abortTimer() {
    clearInterval(tid);
    $("input[id*='jsonData']").val(null);
}

function init() {
    drawMap();
    drawPie();
}

function drawMap() {
    map = new Datamap({
        element: document.getElementById("map"),
        scope: 'world',
        geographyConfig: {
            borderColor: '#BBBBBB',
            popupTemplate: function(geography, data) {
                if (data) {
                    return '<div class="hoverinfo">' + geography.properties.name +
                           '<br/>AveragePrice: ' +  data.avgPrice.toFixed(2) +
                           '<br/>Tours bought: ' + data.toursBought +
                           '</div>'
                } else {
                    return '<div class="hoverinfo">' + geography.properties.name + '</div>'
                }
            },
            highlightOnHover: false
        },
        setProjection: function(element) {
            var projection = d3.geo.conicConformalEurope()
                .scale(780);
            var path = d3.geo.path()
                .projection(projection);

            return {path: path, projection: projection};
        },
        fills: {
            defaultFill: "#FFE6CC",
            "0": "#FFE6CC",
            "1": "#FFFF1F",
            "2": "#CCFF66",
            "3": "#7FFF66",
            "4": "#4CFF29",
            "5": "#CC9999",
            "6": "#CC99B3",
            "7": "#CB99CC",
            "8": "#B671B7",
            "9": "#F500F5",
            "10": "#F5007A",
            "11": "#FF0000"
        }
    });
    resetMap();
}

function update() {
    var modelData = $("input[id*='jsonData']").val();
    if (modelData) {
        var fillingData = prepareFilling(modelData);
        // update map
        map.updateChoropleth(fillingData);
        // update pie chart
        updatePieChart(fillingData);
    }
}

function updatePieChart(fillingData) {
    var list = mapToList(fillingData);
    piePath.data(pie(list));
    piePath.transition().duration(750).attrTween("d", arcTween);
}

function mapToList(fillingData) {
    var list = [], item;
    for (var element in fillingData) {
        item = {};
        item.name = fillingData[element].name;
        item.toursBought = fillingData[element].toursBought;
        list.push(item);
    }
    return list;
}

function prepareFilling(modelData) {
    var obj = $.parseJSON(modelData);
    var result = {};
    $.each(obj, function(country, params) {
        result[params["isoCode"]] = { "fillKey": params["fillKey"], "avgPrice": params["averagePrice"], "toursBought": params["toursBought"], "name": params["country"] }
    });
    return result;
}

function resetMap() {
    map.updateChoropleth(getEmptyMap());
}

function resetPieChart() {
    updatePieChart(getEmptyMap());
}

function getEmptyMap() {
    return {
        ALB: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        AND: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        AUT: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        BLR: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        BEL: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        BIH: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        BGR: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        HRV: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        CYP: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        CZE: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        DNK: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        EST: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        FIN: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        FRA: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        DEU: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        GRC: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        HUN: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        ISL: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        ITA: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        LVA: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        LTU: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        LUX: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        MLT: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        MCO: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        MNE: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        NLD: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        NOR: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        POL: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        PRT: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        IRL: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        MKD: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        SRB: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        ESP: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        SWE: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        CHE: { fillKey: 0, avgPrice: 0.0, toursBought: 0 },
        GBR: { fillKey: 0, avgPrice: 0.0, toursBought: 0 }
    };
}

function drawPie() {
    var width = 500;
    var height = 350;
    var radius = Math.min(width, height) / 2;
    var color = d3.scale.category20b();

    var initialMap = getEmptyMap();
    var fillingData = mapToList(initialMap);

    pie = d3.layout.pie()
        .value(function(d) { return d["toursBought"]; })
        .sort(function(a, b) { return d3.descending(a.toursBought, b.toursBought); });

    pieArc = d3.svg.arc()
        .outerRadius(radius)
        .innerRadius(radius - 50);

    var svg = d3.select("#pieChart")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + (width / 2) + "," + (height / 2) + ")");

    var tip = d3.tip()
        .attr('class', 'd3-tip')
        .offset([0, 0])
        .html(function(d) {
            return d.data.name + ": <span style='color:orangered'>" + d.data.toursBought + "</span>";
        });

    svg.call(tip);

    piePath = svg.datum(fillingData).selectAll("path")
        .data(pie)
        .enter().append("path")
        .attr("d", pieArc)
        .attr("fill", function(d, i) { return color(i); })
        .each(function(d) { this._current = d; })
        .on('mouseover', tip.show)
        .on('mouseout', tip.hide);
}

function arcTween(a) {
    var i = d3.interpolate(this._current, a);
    this._current = i(0);
    return function(t) {
        return pieArc(i(t));
    };
}