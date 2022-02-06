// used https://www.d3-graph-gallery.com/graph/stackedarea_basic.html for insipration and some code

// setting the margins


function makeStackedLine() {
  let margin = store.dimensions.margin,
  width = store.dimensions.width - margin.left - margin.right,
    height = store.dimensions.height - margin.top - margin.bottom;

  // making the carriage for my precious baby
  var stackedarea = d3
    .select("#stackedline")
    .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

  // load data
  data = store.filtered

  data.forEach(function (d) {
    d.school = d["School"].trim();
    d.attendance = +d["Attendance"].split("%")[0];
    d.recommendation = d.Recommendation;
    d.year = +d.Year;
  });


  // then stack it
  // writing these manually because the order is important
  const groupNames = ["No Recommendation", "Pre-Calculus", "Regular Calculus", "AP Calculus AB", "AP Calculus BC"]

  // first group the data so that it forms an array for each value of the x axis

  // const sumstat = d3.flatGroup(data, d => d.year);
  // console.log(sumstat)

  var sumstat = Array.from(d3.rollup(data.sort((a, b) => d3.ascending(a.year, b.year)), v => v.length, d => d.year, d => d.recommendation)).reduce((accumlator, [yearKey, innerMap]) => {
    // create a 'row' with a Date property
    let row = { "year": yearKey }
    // add further properties to the 'row' based on existence of keys in the inner Map per point (1)
    groupNames.map(col => row[col] = innerMap.has(col) ? innerMap.get(col) : 0);
    // store and return the accumulated result
    accumlator.push(row);
    return accumlator;
  }, []);
  ;




  sumstat.splice(4, 0, { "year": 2020, "AP Calculus AB": 0, "AP Calculus BC": 0, "No Recommendation": 0, "Pre-Calculus": 0, "Regular Calculus": 0 })



  //console.log(keys)
  // again this is from https://www.d3-graph-gallery.com/graph/stackedarea_basic.html
  const stackedData = d3.stack()
    .keys(groupNames)
    .value(function (d, key) {
      return d[key]
    })(sumstat)



  //making the x axis
  const x = d3.scaleLinear()
    .domain(d3.extent(data, d => d.year))
    .rangeRound([0, width])
  const xAxis = stackedarea
    .append("g")
    .attr("transform", "translate(0," + height + ")")
    .call(d3.axisBottom(x).ticks(5).tickFormat(d3.format("d")))
    .style("font-size", "14px")

  stackedarea.append('text')
    .attr('class', '.axis')
    .attr('x', width / 2)
    .attr('y', height + 40)
    .attr('font-size', '20px')
    .attr('text-anchor', 'middle')
    .text("Year");

  // the max y value, for the domain
  const yMax = d3.greatest(d3.rollup(data, v => v.length, d => d.year), (a, b) => d3.ascending(a[1], b[1]))

  // making the y axis
  const y = d3.scaleLinear()
    .domain([0, yMax[1]])
    .range([height, 0])
    .nice();
  const yAxis = stackedarea.append("g")
    .call(d3.axisLeft(y))
    .style("font-size", "13px")

  // y axis title
  stackedarea.append('text')
    .attr('class', '.axis')
    .attr('x', -40)
    .attr('y', height / 2 + 5)
    .attr('font-size', '20px')
    .attr('text-anchor', 'middle')
    .attr("transform", `rotate(270, ${-40}, ${height / 2})`)
    .text("Number of Recommendations");



  // color palette
  const color = d3.scaleOrdinal()
    .domain(groupNames)
    // note that #fea781 repeats – this is intentional as AP Calculus AB and IB Math SL are equivalent
    .range(['#a1e1e0', '#9ed6a3', '#fbe488', '#fea781', '#df7988'])

  // title
  stackedarea.append('text')
    .attr('class', '.title')
    .attr('x', width / 2)
    .attr('y', -50)
    .attr('font-size', '30px')
    .attr('font-weight', 'bold')
    .attr('text-anchor', 'middle')
    .text('Number of Recommendations by Year by Recommendation')

  // a clip path, from https://www.d3-graph-gallery.com/graph/area_brushZoom.html
  // Add a clipPath: everything out of this area won't be drawn.
  const clip = stackedarea.append("defs").append("clipPath")
    .attr("id", "clip")
    .append("rect")
    .attr("width", width)
    .attr("height", height)
    .attr("x", 0)
    .attr("y", 0);


  var brush = d3.brushX()
    .extent([[0, 0], [width, height]])
    .on("end", updateCharts)




  /* 
  how the brush works
  1) count the number of points selected, this will be the number of ticks on the x-axis
  2) zoom in/ render only between the largest and smallest point
  3) redraw the other two charts with only that data

  on double click
  reset to the original charts
  */

  // function that generates an array of year values as integers
  // inspired by https://stackoverflow.com/questions/3895478/does-javascript-have-a-method-like-range-to-generate-a-range-within-the-supp
  function generateYears(startValue, endValue) {
    return [...Array(endValue - startValue + 1).keys()].map(i => i + startValue);
  }


  // based off of https://observablehq.com/@d3/brush-snapping-transitions
  // modified to work on integers instead of dates for the year value, otherwise works the same
  function updateCharts(event) {
    const selection = event.selection;

    if (selection == null) {
      store.filtered = store.data
      store.agg = aggregateData(store.filtered)
      d3.select("#linechart").select("svg").remove()
      d3.select("#strip").select("svg").remove()
      d3.select("#stackedline").select("svg").remove()
      makeLineChart()
      makePlot(store, null)
      makeStackedLine()
      return;
    }


    if (!event.sourceEvent || !selection) return;

    // round to the nearest integer, and convert it to a value on the x-axis with scaleLinear
    const [x0, x1] = selection.map(d => Math.round(x.invert(d)));
    // store the years that are covered as an array, for filtering
    let yearArray = generateYears(x0, x1)
    // move the brush so that it visibly covers the selected years
    // this if statement makes sure the brush doesnt move beyond the lowest or highest tick of the x-axis

    // if a user selects the entire chart, they'd normally be unable to click and drag any other area. this solves that by resetting the brush when it happens
    if (x0 === d3.min(data, d => d.year) && x1 === d3.max(data, d => d.year)) {
      d3.select(this).call(brush.move, null)
      return;
    }

    d3.select(this).transition().call(brush.move, x0 === d3.min(data, d => d.year) ? [x0, x1 + .25].map(x)
      : x1 === d3.max(data, d => d.year) ? [x0 - .25, x1].map(x) : [x0 - .25, x1 + .25].map(x))



    // filters out all data that doesnt have a year in the yearArray
    filteredData = data.filter(d => yearArray.indexOf(d.year) > -1)

    store.filtered = filteredData
    store.agg = aggregateData(store.filtered)
    d3.select("#linechart").select("svg").remove()
    d3.select("#strip").select("svg").remove()
    makeLineChart()
    makePlot(store, [x0, x1])

  }


  // the thing that makes the area
  const area = d3.area()
    .x(function (d) {
      return x(d.data.year);
    })
    .y0(function (d) {
      return y(d[0]);
    })
    .y1(function (d) {
      return y(d[1]);
    })


  // Show the areas
  stackedarea
    .selectAll(".layers")
    .data(stackedData)
    .join("path")
    .attr("class", ".area")
    .attr("fill", d => color(d[0]))
    .attr("d", area)




  // add the brush
  stackedarea
    .append("g")
    .attr("class", "brush")
    .call(brush);

    // add a legend

    stackedarea.selectAll("legend_squares")
  .data(groupNames)
  .enter()
  .append("rect")
    .attr("x", 620)
    .attr("y", function(d,i){ return 30 + i*25}) 
    .attr("width", 14)
    .attr("height", 14)
    .style("fill", function(d){ return color(d)})

    stackedarea.selectAll("legend")
    .data(groupNames)
    .enter()
    .append("text")
    .attr("x", 640)
    .attr("y", function(d,i){ return 38 + i*25}) 
    .style("fill", "black")
    .text(function(d){ return d})
    .attr("text-anchor", "left")
    .style("font-size", "14px")
    .style("alignment-baseline", "middle")




}



