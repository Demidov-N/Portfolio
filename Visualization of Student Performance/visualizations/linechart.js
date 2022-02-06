/* I used this website as reference https://www.d3-graph-gallery.com/graph/line_several_group.html
also this site https://www.d3-graph-gallery.com/graph/bubblemap_buttonControl.html 
https://bl.ocks.org/hettlage/db6dbba1ebec634adc695c30138dd355 for color lenged*/

function removePercent(data) {
    data = data.substr(0, data.length-1);
    return data
}

function makeLineChart() {
    const margin = {top: 100, right: 50, bottom: 100, left: 117},
        width = 1000 - margin.left - margin.right,
        height = 700 - margin.top - margin.bottom;


    var svg = d3.select("#linechart")
        .append("svg")
        .attr("width", width + margin.left + margin.right + 250)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`);

    var colorLegendG = svg.append('g')
                            .attr("transform", `translate(${width+50}, -60)`);

    var data = store.filtered
    //Find average attendance for each school per year
    var avgdata = d3.rollups(data, v => d3.mean(v, d=>d.Attendance), d=>d.School, d=>d.Year)
    //Find average attendance of all schools per year
    var dataOverall = d3.rollups(data, v => d3.mean(v, d=>d.Attendance), d=>d.Year)
    dataOverall = dataOverall.sort(function (a, b) { return  a[0] - b[0];})
    var avgdataOverall = ["overall", dataOverall]

    var oneYearSchools = avgdata.filter(e => e[1].length === 1);
    var mapKey = new Map();
    for (let i = 0; i < avgdata.length; i++) {
        mapKey.set(avgdata[i][0], "a"+i)
    }

    function getKey(schoolname) {
        return mapKey.get(schoolname)
    }

    //color scheme
    var color = d3.scaleOrdinal(["#6e40aa","#803eb0","#873eb1","#a43db3","#c13dae","#ef4494","#ff4d80","#ff596a","#ff6a54","#ff7d42",
        "#fd9334","#ecaa2e","#d9c131","#c6d73c","#b5ea51","#9af357","#78f659","#59f664","#3bf277",
        "#28ea8d","#1ddea4","#19cebb","#1dbbcd","#26a6db","#3390e1","#437be0","#5267d7","#6055c8",
        "#6a47b7","#6e40aa","#1ac6c4"])

    function toggleLine(event) {
        svg.selectAll(`.lines.${getKey(event.path[0].innerHTML)}`)
            .data(oneYearSchools)
            .attr("stroke-width", function() {
                if (d3.select(this).attr("stroke-width") === '1.5') {
                    return 0;
                }
                else {
                    return 1.5;
                }
            })
        }

    function toggleCircle(event) {
        svg.selectAll(`.circles.${getKey(event.path[0].innerHTML)}`)
            .data(oneYearSchools)
            .attr("r", function() {
                if (d3.select(this).attr("r") === '3') {
                    return 0;
                }
                else {
                    return 3;
                }
            })
        }

    // Add X axis
    var x = d3.scaleLinear()
        .domain(d3.extent(data, function(d) { return d.Year; }))
        .range([0, width]);
    svg.append("g")
        .attr("transform", `translate(0, ${height})`)
        .call(d3.axisBottom(x).ticks(5).tickFormat(d3.format("d")))
        .style("font-size", "15px")
    svg.append('text')
        .attr('x',width/2)
        .attr('y',height + 50)
        .attr('font-size','20px')
        .attr('text-anchor','middle')
        .attr('fill', 'black')
        .text("Year");

    // Add Y axis
    var y = d3.scaleLinear()
        .domain([0, d3.max(data, function(d) { return +d.Attendance; })])
        .range([height, 0]);
    svg.append("g")
        .call(d3.axisLeft(y))
        .style("font-size", "14px")
    svg.append('text')
        .attr('x', 0 - 260)
        .attr('y', height / 2 - 300)
        .attr('font-size','20px')
        .attr('text-anchor','middle')
        .attr("transform", "rotate(-90)")
        .text("Average Attendance Rate (%)");

    //Title
    svg.append('text')
        .attr('x', width / 2)
        .attr('y', -50)
        .attr('font-size', '30px')
        .attr('font-weight', 'bold')
        .attr('text-anchor', 'middle')
        .text('Average Attendance Rate of Each School')

    var textbox = d3.select("#linechart").append("box")
        .attr("class", "box")
        .style("opacity", 0);

    // Draw circles
    var circles = svg.selectAll(".circle")
        .data(oneYearSchools)
        .enter()
        .append("circle")
        .attr("class", function(d){ return "circles" + " " + getKey(d[0]); })
        .attr("cx", function (d) { return x(d[1][0][0]); } )
        .attr("cy", function (d) { return y(d[1][0][1]); } )
        .attr("r", 3)
        .style("fill", function(d){ return color(d[0]) })
        .on("mouseover", function(d,i) {
            circles.transition().style('opacity', 0.2)
            lines.transition().style('opacity', 0.2)
            averageLine.transition().style('opacity', 0.2)
            d3.select(this).transition()
                .style("opacity", 1);
            textbox.transition()
                .duration(50)
                .style("opacity", 1);
            textbox.html(i[0])
                .style("left", (d.pageX - 200) + "px")
                .style("top", (d.pageY - 180) + "px")
                .style("fill", "black");
        })
        .on("mouseout", function(d) {
            circles.transition().style('opacity', 1)
            lines.transition().style('opacity', 1)
            averageLine.transition().style('opacity', 0.8)
            textbox.transition()
                .duration(50)
                .style("opacity", 0);
        })

    // Draw lines
    var lines = svg.selectAll(".line")
        .data(avgdata)
        .join("path")
        .attr("class", function(d){ return "lines" + " "+ getKey(d[0]); })
        .attr("fill", "none")
        .attr("stroke", function(d){ return color(d[0]) })
        .attr("stroke-width", 1.5)
        .attr("d", function(d){
            return d3.line()
            .x(function(d) { return x(d[0]); })
            .y(function(d) { return y(d[1]); })
            (d[1])
        })
        .on("mouseover", function(d,i) {
            lines.transition().style('opacity', 0.2)
            circles.transition().style('opacity', 0.2)
            averageLine.transition().style('opacity', 0.2)
            d3.select(this).transition()
                .style("opacity", 1);
            textbox.transition()
                .duration(100)
                .style("opacity", 1);
            textbox.html(i[0])
                .style("left", (d.pageX - 200) + "px")
                .style("top", (d.pageY - 180) + "px")
                .style("fill", "black");
        })
        .on("mouseout", function(d) {
            lines.transition().style('opacity', 1)
            circles.transition().style('opacity', 1)
            averageLine.transition().style('opacity', 0.8)
            textbox.transition()
                .duration(100)
                .style("opacity", 0);
        })

    // Draw average line
    var averageLine = svg.selectAll(".line")
    .data([avgdataOverall])
    .join("path")
    .attr("class", "overall")
    .attr("fill", "none")
    .attr("stroke", "black")
    .attr("stroke-width", 4)
    .style("stroke-dasharray", ("7, 7"))
    .style("opacity", 0.8)
    .attr("d", function(d){
        return d3.line()
        .x(function(d) { return x(d[0]); })
        .y(function(d) { return y(d[1]); })
        (d[1])
    })

    // Add average line text
    svg.append('text')
        .attr('x', 0)
        .attr('y', -20)
        .attr('font-size','12px')
        .attr('text-anchor','left')
        .text("black dotted line = average of all schools");

    const colorLegend = d3.legendColor()
        .scale(color)
        .shape('rect')
        .shapeWidth(10)
        .shapeHeight(10)
        .on('cellclick', function(d) {
            toggleLine(d)
            toggleCircle(d)
            let legendCell = d3.select(this);
            if (this.style.opacity === '0.2') {
                legendCell.style("opacity", 1);    
            }
            else {
                legendCell.style("opacity", 0.2)
            };
        });
    colorLegendG.call(colorLegend);
}
