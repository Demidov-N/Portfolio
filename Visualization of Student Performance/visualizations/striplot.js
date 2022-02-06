//Data


// Represents the global variable for the data defined
let store = {};

/**
 * Gets the column from the table
 * @param matrix the table
 * @param col the string name of the column
 * @returns {*[]} the column of the matrix or table
 */
function getCol(matrix, col) {
	const column = [];
	for (var i = 0; i < matrix.length; i++) {
		column.push(matrix[i][col]);
	}
	return column; // return column data..
}

/**
 * Loads the data into the program and executes all the functions after that
 * @returns {Promise<{}>} the loaded data with the required transformations
 */
function loadData() {
	return Promise.all([
		d3.csv("visualizations/AttendanceCompiled.csv")])
		.then((res) => {
			store.data = res[0]
			store.dimensions = {
				width: 1000,
				height: 800,
				margin: {
					top: 100,
					bottom: 100,
					left: 80,
					right: 50
				}
			};
			// Transform the numerical data
			store.data.forEach((d) => {
				d["Attendance"] = d["Attendance"].substring(0, d["Attendance"].length - 1)
				d["Year"] = Number(d["Year"])
			})
			return store
		})
}

/**
 * Creates configuration for the striplot
 * @param store the global variale to add the configurations to
 * @returns {*} the updated global variable
 */
function makeConfig(store) {
	store.dimensions = {
		width: 1000,
		height: 800,
		margin: {
			top: 100,
			bottom: 100,
			left: 80,
			right: 50
		}
	}
	return store
}

/**
 * Adds a single variable to the final based on the recommendation and attendance. Adds to newly existing one if already
 * exist or create a new one.
 * @param final list of recommendation-attendance relationships of students, every instance of the list has a unique
 * 				recommendation and attendance
 * @param other the list of students itself
 * @returns {*} the final aggregated list
 */
function findSameRec(final, other) {
	var found = false;
	for (var i = 0; i < final.length; i++) {
		var fin = final[i];
		if (fin.rec === other["Recommendation"] && fin.score === other["Attendance"]) {
			fin.students.push(other)
			fin.total += 1
			found = true
			break
		}
	}
	if (!found) {
		const added = {
			rec: other["Recommendation"], // Recommendation
			score: other["Attendance"], // Attendance
			total: 1, // Amount of students in this grouping
			students: [other] // list of all the students in the goruping
		}
		final.push(added)
	}
	return final
}


/**
 * Aggregates the students by their recommendation and attendance
 * @param data the student data
 * @returns {*[]} the aggregated list of students based on attendance and recommendation
 */
function aggregateData(data) {
	var final = []
	data.forEach((o) => {
		findSameRec(final, o)
	})
	final = final.sort((a, b) => (- a.total + b.total))
	return final
}

/**
 * Creates a new plot with the new scales and values based on the presented data and the year range.
 * @param store the stored data
 * @param yearRange the year range which is going to be presented
 */
function makePlot(store, yearRange) {

	// Create a strip plot
	const svg = d3.select("#strip")
		.append('svg')
		.attr('width', store.dimensions.width)
		.attr('height', store.dimensions.height)
		.attr("style",
			"circle {fill:  #999999, opacity: 20%")






	// Make Container
	const container = svg.append('g')
		.attr('transform', `translate(${store.dimensions.margin.left},${store.dimensions.margin.top})`)
	containerWidth = store.dimensions.width - store.dimensions.margin.left - store.dimensions.margin.right
	containerHeight = store.dimensions.height - store.dimensions.margin.top - store.dimensions.margin.bottom


	// On hover tooltip. Initial it is hidden. It is moved and shown only when I hover on bars
	var tooltip = d3.select('#strip').append("box")
		.attr("class", "box")
		.style("opacity", 0);

	// Make Scales
	const recommendations = ["No Recommendation", "Pre-Calculus", "Regular Calculus", "AP Calculus AB", "AP Calculus BC"]

	const yScale = d3.scaleLinear()
		.domain([0, 100])
		.range([containerHeight, 0])

	const xScale = d3.scaleBand()
		.domain(recommendations)
		.range([0, containerWidth])
		.padding(1)

	const colorScale = d3.scaleOrdinal()
		.domain(recommendations)
		.range(['#a1e1e0', '#9ed6a3', '#fbe488', '#fea781', '#df7988'])

	// CircleScale is a global variable to remove the bias of circle sizes when image is rescaled

	// X Axis and Label
	const xAxis = d3.axisBottom(xScale)

	const xAxisGroup = container.append('g')
		.attr("transform", `translate(0, ${containerHeight})`)
		.call(xAxis)
		.selectAll("text")
		.attr("font-size", "15px")

	container.append('text')
		.attr('x', containerWidth / 2)
		.attr('y', containerHeight + 50)
		.attr('font-size', '20px')
		.attr('text-anchor', 'middle')
		.attr('fill', 'black')
		.text('Recommendations')

	//Y Axis and Label
	const yAxis = d3.axisLeft(yScale)
	const yAxisGroup = container.append('g')
		.call(yAxis)
		.selectAll("text")
		.attr('font-size', '15px')

	// Y Label
	container.append('text')
		.attr('x', -containerHeight / 2)
		.attr('y', -50)
		.attr('font-size', '20px')
		.attr('text-anchor', 'middle')
		.attr('transform', 'rotate(-90)')
		.attr('fill', 'black')
		.text('Attendance %')

	// Create a brush
	const brush = container.call(
		d3.brush()
			.on("start brush", (br) => {
				store.brushed = true
				updateCircles(br.selection, filterSelected(br.selection, xScale, yScale))
			})
			.on("end", (br) => {
				if (br.selection == null) {
					store.brushed = false
					updateCircles(br.selection, filterSelected(br.selection, xScale, yScale))
				}
				d3.select("#linechart").select("svg").remove()
				d3.select("#stackedline").select("svg").remove()
				makeLineChart()
				makeStackedLine()
			})
			.extent( [ [0,-50], [containerWidth,containerHeight]]))

	//Add data
	const agg_d = store.agg
	// Background Circles
	const bg = container.append("g").selectAll('circle')
		.data(agg_d)
		.join('circle')
		.attr('fill', "lightgray")
		.attr('cx', (d) => xScale(d.rec))
		.attr('cy', (d) => yScale(d.score))
		.attr('r', (d) => store.circleScale(d.total))
		.attr('stroke', 'gray')
		.attr('stroke-width', '1')
		.attr('opacity', '25%')

	// Visible Circles
	store.circles = container.append("g").selectAll('circle')
		.data(agg_d)
		.join('circle')
		.attr('fill', (d) => colorScale(d.rec))
		.attr('cx', (d) => xScale(d.rec))
		.attr('cy', (d) => yScale(d.score))
		.attr('r', (d) => store.circleScale(d.total))
		.attr('stroke', 'black')
		.attr('stroke-width', '0')
		.attr('opacity', '70%')
		.on("mouseover", (event, data) => {
			tooltip.style("opacity", 1)
			tooltip.html("Attendance: " + data.score + '%<br>Student Count: ' + data.total)
				.style("left", (event.pageX - 220) + "px")
				.style("top", (event.pageY - 200) + "px")
				.style("fill", "black");

			var f = store.filtered
			updateCircles(event, data.students)
			store.filtered = f
		})
		.on("mouseout", (event) => {
			updateCircles(null, store.filtered)
			tooltip.style('opacity', 0)
		})

	if (yearRange === null) {
		//Title
		container.append('text')
			.attr('x', containerWidth / 2)
			.attr('y', -50)
			.attr('font-size', '30px')
			.attr('font-weight', 'bold')
			.attr('text-anchor', 'middle')
			.attr('fill', 'black')
			.text('Attendance by Recommendation')
	}
	else if (yearRange[0] != yearRange[1]) {
		//Title
		container.append('text')
			.attr('x', containerWidth / 2)
			.attr('y', -50)
			.attr('font-size', '30px')
			.attr('font-weight', 'bold')
			.attr('text-anchor', 'middle')
			.attr('fill', 'black')
			.text('Attendance by Recommendation (' + yearRange[0] + "\-" + yearRange[1] + ")")
	} else {
		//Title
		container.append('text')
			.attr('x', containerWidth / 2)
			.attr('y', -50)
			.attr('font-size', '30px')
			.attr('font-weight', 'bold')
			.attr('text-anchor', 'middle')
			.attr('fill', 'black')
			.text('Attendance by Recommendation ' + `(${yearRange[0]})`)
	}


}

function updateCircles(selection, filtered) {
	store.filtered = filtered
	var aggregated = aggregateData(store.filtered)
	let circles = store.circles

	circles
		.attr("r", (d) => {
			if (hasGroup(d, aggregated))
				return store.circleScale(d.total)
			else
				return 0
		})
		.attr("stroke-width", (d) => {
			if (hasGroup(d, aggregated) && (selection != null || store.brushed))
				return 2
			else
				return 0
		})


}

function hasGroup(a, list) {
	let total = false;
	list.forEach((d) => {
		if (d.rec === a.rec && d.score === a.score) {
			total = true
		}
	})

	return total;
}

function filterSelected(brush_coords, xScale, yScale) {
	if (brush_coords != null) {
		var x0 = brush_coords[0][0],
			x1 = brush_coords[1][0],
			y0 = brush_coords[0][1],
			y1 = brush_coords[1][1];


		let filtered = []
		store.data.forEach((d) => {
			var cx = xScale(d["Recommendation"]),
				cy = yScale(d["Attendance"])
			if (y0 <= cy && y1 >= cy
				&& x0 <= cx && x1 >= cx) {
				filtered.push(d)
			}
		})
		return filtered
	} else
		return store.data
}


// Executing command
loadData().then((data) => {
	store = data
	store = makeConfig(store)
	store.agg = aggregateData(store.data)
	store.circleScale = d3.scalePow()
		.domain([0, d3.max(getCol(aggregateData(store.data), "total"))])
		.range([3, 40])
	makePlot(store, null)
	store.filtered = store.data

	makeLineChart()
	makeStackedLine()
})