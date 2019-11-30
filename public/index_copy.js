var margin = {top: 100, right: 100, bottom: 100, left: 100}
    , width = 1200 - margin.left - margin.right // Use the window's width
    , height = 650 - margin.top - margin.bottom; // Use the window's height

var tip = d3.tip()
  .attr("class", "d3-tip")
  .offset([-8, 0])
  .html(function(d) {
      return d.file2.file_name.substr(0, d.file2.file_name.length - 5) + '<br>'
          + d.file1.file_name.substr(0, d.file1.file_name.length - 5) + '<br>'
          + 'Plagiarism: ' + d.file1.similarity_percent + '%'
          + '<br>' + 'Click me for details';
  });
function addSVG() {
    svg = d3.select("#heatmap_div").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom * 2)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
        .style("text-align", "center");

    return svg;
}

function generate_heatmap(svg) {
    d3.json("output/output.json", function (error, data) {
        var fileNames = new Set();
        data.forEach(function (d) {
            fileNames.add(d.file1.file_name.substr(0, d.file1.file_name.length - 5));
            fileNames.add(d.file2.file_name.substr(0, d.file2.file_name.length - 5));
        });
        var xaxis = Array.from(fileNames);
        xaxis.sort();

        var padding = 0.02;
        // Build X scales and axis:
        var x = d3.scaleBand()
            .range([0, width - margin.left / 2])
            .domain(xaxis)
            .padding(padding);
        svg.append("g")
            .attr("transform", "translate(0," + (height - margin.bottom) + ")")
            .call(d3.axisBottom(x))
            .selectAll("text")
            .style("text-anchor", "end")
            .attr("dx", "2em")
            .attr("dy", "1.8em")
            .attr("transform", "rotate(-25)");

        // Build X scales and axis:
        var y = d3.scaleBand()
            .range([height - margin.bottom, 0])
            .domain(xaxis)
            .padding(padding);
        svg.append("g")
            .call(d3.axisLeft(y))
;

        // Build color scale
        var minimum = d3.min(data, function (d) {
                return d.file1.similarity_percent;
            });
        var maximum = d3.max(data, function (d) {
                    return d.file1.similarity_percent;
            });
        var color_mossi_polo = d3.scaleLinear()
            .range(["#77bea7", "#1a2a25"])
            .domain([minimum, maximum]);

        svg.selectAll()
            .data(data, function (d) {
                if (d.file1.file_name.localeCompare(d.file2.file_name) === 1) {
                    var temp = d.file1;
                    d.file1 = d.file2;
                    d.file2 = temp;
                }
                return d.file1.file_name.substr(0, d.file1.file_name.length - 5)
                    + ':' + d.file2.file_name.substr(0, d.file2.file_name.length - 5);
            })
            .enter()
            .append("a")
            .attr("xlink:href", "http://moss.stanford.edu/results/956166494/")
            .append("rect")
            .attr("x", function (d) {
                return x(d.file1.file_name.substr(0, d.file1.file_name.length - 5))
            })
            .attr("y", function (d) {
                return y(d.file2.file_name.substr(0, d.file2.file_name.length - 5))
            })
            .attr("width", x.bandwidth())
            .attr("height", y.bandwidth())
            .style("fill", function (d) {
                return color_mossi_polo(d.file1.similarity_percent)
            })
            .on("mouseover", tip.show)
            .on("mouseout", tip.hide);

        // Add non mossi-polo plagiarised version
        d3.json("output/output_plagiarised.json", function (error, ds) {
            svg.selectAll()
                .data(ds, function (d) {
                    if (d.file1.file_name.localeCompare(d.file2.file_name) === 1) {
                        var temp = d.file1;
                        d.file1 = d.file2;
                        d.file2 = temp;
                    }
                    // console.log(d.file2.file_name +':'+ d.file1.file_name);
                    return d.file2.file_name.substr(0, d.file2.file_name.length - 5)
                        + ':' + d.file1.file_name.substr(0, d.file1.file_name.length - 5);
                })
                .enter()
                .append("a")
                .attr("xlink:href", "http://moss.stanford.edu/results/956166494/")
                .append("rect")
                .attr("x", function (d) {
                    return x(d.file2.file_name.substr(0, d.file2.file_name.length - 5))
                })
                .attr("y", function (d) {
                    return y(d.file1.file_name.substr(0, d.file1.file_name.length - 5))
                })
                .attr("width", x.bandwidth())
                .attr("height", y.bandwidth())
                .style("fill", function (d) {
                    return color_mossi_polo(d.file1.similarity_percent)
                })
                .on("mouseover", tip.show)
                .on("mouseout", tip.hide);
        });
    addLegend(svg, minimum + '%', '' + maximum + '%');
    });
}

function handleMouseOver() {
    console.log("hello");
}

function addLegend(svg, min, max) {
    // Create the svg:defs element and the main gradient definition.
    var svgDefs = svg.append('defs');

    var mainGradient = svgDefs.append('linearGradient')
        .attr('id', 'mainGradient');

    // Create the stops of the main gradient. Each stop will be assigned
    // a class to style the stop using CSS.
    mainGradient.append('stop')
        .attr('stop-color', '#77bea7')
        .attr('offset', '0');

    mainGradient.append('stop')
        .attr('stop-color', '#1a2a25')
        .attr('offset', '1');

    // Use the gradient to set the shape fill, via CSS.
    svg.append('rect')
        .attr('x', width / 4 - margin.left / 2)
        .attr('y', height - margin.bottom / 4)
        .attr('width', width / 2)
        .attr('height', 15)
        .attr('fill', 'url(#mainGradient)');

    svg.append("text")
        .attr("x", width / 4 - margin.left / 2)
        .attr("y", height - margin.bottom / 4 - 5)
        .style("font-size", "14px")
        .text(min);
    svg.append("text")
        .attr("x", width / 4 - margin.left / 2 + width / 2 - 25)
        .attr("y", height - margin.bottom / 4 - 5)
        .style("font-size", "14px")
        .text(max);

}

var svg = addSVG();
svg.call(tip);
generate_heatmap(svg);