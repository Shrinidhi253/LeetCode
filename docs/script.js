async function main() {
    const response = await fetch("stats.json");
    const stats = await response.json();

    displayDifficultySummary(stats["difficultySummary"]);
    displayChart(stats["topics"])
}

function displayDifficultySummary(difficultySummary) {
    const difficultSummaryClass = document.getElementById("difficultySummary");
    var total = 0;

    for (const difficulty in difficultySummary) {
        //Create the elements
        var difficultyCard = document.createElement("div");
        var difficultyLabel = document.createElement("label");
        var difficultyCount = document.createElement("label");

        //Set class names
        difficultyCard.className = "difficultyCard";
        difficultyLabel.className = difficulty + "Label";
        difficultyCount.className = "difficultyCount";

        //Set texts
        difficultyLabel.textContent = difficulty.toUpperCase();
        difficultyCount.textContent = difficultySummary[difficulty];

        //Add the cards to the div class
        difficultyCard.appendChild(difficultyLabel);
        difficultyCard.appendChild(difficultyCount);
        difficultSummaryClass.appendChild(difficultyCard);

        //count total questions solved
        total += difficultySummary[difficulty];
    }

    //Create a card for total questions
    var totalCard = document.createElement("div");
    var totalLabel = document.createElement("label");
    var totalCount = document.createElement("label");

    totalCard.className = "difficultyCard";
    totalLabel.className = "totalLabel";
    totalCount.className = "totalCount";

    totalLabel.textContent = "TOTAL";
    totalCount.textContent = total;

    totalCard.appendChild(totalLabel);
    totalCard.appendChild(totalCount);
    difficultSummaryClass.appendChild(totalCard);
}

function flattenTopics(topics) {
    var flattenedTopics = []
    for (const topic in topics) {
        var tempDict = {"topic" : topic};

        for (const difficulty in topics[topic]) {
            tempDict[difficulty] = topics[topic][difficulty];
        }

        tempDict["total"] = tempDict["easy"] + tempDict["medium"] + tempDict["hard"];
        flattenedTopics.push(tempDict);
    }

    return flattenedTopics;
}

function displayChart(topics) {
    const topicData = flattenTopics(topics);
    const chart = new Chart(topicData, 800, 400);
    chart.displayChart();
}

class Chart {
    WIDTH;
    HEIGHT;
    svgChart;
    topicsData;
    margins;
    xScale; yScale;
    DYNAMIC_WIDTH; //for making the chart scrollable
    BAR_WIDTH = 15;
    BAR_PADDING = 0.2;

    constructor(topicsData, chartWidth, chartHeight) {
        this.topicsData = topicsData;
        this.WIDTH = chartWidth;
        this.HEIGHT = chartHeight;
    }

    displayChart() {
        this.setMargins();
        this.createSvgChartArea();
        this.createScales();
        this.drawAxes();
        this.drawBars();
    }

    setMargins() {
        this.margins = {
            top : 20, //temp values for margins for testing purposes. TODO change it later if needed
            right : 20,
            bottom : 100,
            left : 40
        }
    }

    createSvgChartArea() {
        this.DYNAMIC_WIDTH = this.topicsData.length * (this.BAR_WIDTH / (1 - this.BAR_PADDING));

        this.svgChart = d3.select("#chart") //get the chart div class
                          .append("svg") //create <svg></svg> element within the div class
                          .attr("width", this.DYNAMIC_WIDTH + this.margins.left + this.margins.right) //set chart area width, height
                          .attr("height", this.HEIGHT + this.margins.top + this.margins.bottom)
                          .append("g")
                          .attr("transform", `translate(${this.margins.left}, ${this.margins.top})`);
    }

    createScales() {
        this.xScale = d3.scaleBand() //for discrete data use scaleBand
                        .domain(this.topicsData.map(data => data.topic)) //domain is the list of topics
                        .range([0, this.DYNAMIC_WIDTH]) //span across the whole width
                        .padding(this.BAR_PADDING); //space between the bars

        this.yScale = d3.scaleLinear() //for continuous numeric values, use scaleLinear
                        .domain([0, d3.max(this.topicsData, data => data.total)]) //min and max values in our data
                        .range([this.HEIGHT, 0]); //span across the whole height
    }

    drawAxes() {
        //Draw the ticks and texts for the x axis
        this.svgChart.append("g") //Create a group element for the x axis labels
                     .attr("transform", `translate(0, ${this.HEIGHT})`) //position HEIGHT pixels down on the chart
                     .call(d3.axisBottom(this.xScale)) //main method to display the axes
        
        this.svgChart.selectAll("text") //Get all the x labels
                     .attr("transform", "rotate(-60)")
                     .attr("dx", "-0.9em")   // shift left
                     .attr("dy", "0.15em")
                     .style("text-anchor", "end"); //align the text to the end 
                     

        //Draw the ticks and texts for the y axis
        this.svgChart.append("g")
                     .call(d3.axisLeft(this.yScale) //draw the y axis on the left side
                             .tickFormat(d3.format("d")) //ensures that the y labels are only integers (otherwise it is 0.1, 0.2 ... and num questions cannot be decimals)
                             .ticks(d3.max(this.topicsData, data => data.total))); 
    }

    drawBars() {
        const DIFFICULTIES = ["easy", "medium", "hard"];
        const COLOURS = {"easy" : "#3e984aff", "medium" : "#dbae1dff", "hard" : "#a92d17ff"};

        let xScale = this.xScale;
        let yScale = this.yScale;

        //Create a bar group for each topic
        const groups = this.svgChart.selectAll("g.bar-group") //selects all <g> elements with class bar-group
                                    .data(this.topicsData) //to bind the topc data to the selected group of bars
                                    .enter() //gives placeholder slots for not created elements/ bars
                                    .append("g") //create a group
                                    .attr("class", "bar-group") //sets class of the group to "bar-group"
                                    .attr("transform", d => `translate(${this.xScale(d.topic)},0)`); //positions the bar at the x position at the bottom 

        //Create the stacked bars for each group
        groups.each(function(data) {
            let offset = 0;

            DIFFICULTIES.forEach(difficulty => {
                const count = data[difficulty];

                if (count == 0) {
                    offset += count;
                    return; //no need to draw bars if the there are no questions solved for this difficulty
                }

                d3.select(this).append("rect")
                               .attr("x", 0)
                               .attr("y", yScale(count + offset)) //the scale is used to map our values to graph appropriate coordinates
                               .attr("width", xScale.bandwidth())
                               .attr("height", yScale(0) - yScale(count))
                               .attr("fill", COLOURS[difficulty]);

                offset += count
            })
        })
    }
}

main();