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
        this.svgChart = d3.select("#chart") //get the chart div class
                          .append("svg") //create <svg></svg> element within the div class
                          .attr("width", this.WIDTH + this.margins.left + this.margins.right) //set chart area width, height
                          .attr("height", this.HEIGHT + this.margins.top + this.margins.bottom)
                          .append("g")
                          .attr("transform", `translate(${this.margins.left}, ${this.margins.top})`);
    }

    createScales() {
        this.xScale = d3.scaleBand() //for discrete data use scaleBand
                        .domain(this.topicsData.map(data => data.topic)) //domain is the list of topics
                        .range([0, this.WIDTH]) //span across the whole width
                        .padding(0.2); //space between the bars

        this.yScale = d3.scaleLinear() //for continuous numeric values, use scaleLinear
                        .domain([0, d3.max(this.topicsData, data => data.total)]) //min and max values in our data
                        .range([this.HEIGHT, 0]); //span across the whole height
    }

    drawAxes() {
        //Draw the ticks and texts for the x axis
        this.svgChart.append("g") //Create a group element for the x axis labels
                     .attr("transform", `translate(0, ${this.HEIGHT})`) //position HEIGHT pixels down on the chart
                     .call(d3.axisBottom(this.xScale)); //main method to display the axes

        //Draw the ticks and texts for the y axis
        this.svgChart.append("g")
                     .call(d3.axisLeft(this.yScale) //draw the y axis on the left side
                             .tickFormat(d3.format("d"))); //ensures that the y labels are only integers (otherwise it is 0.1, 0.2 ... and num questions cannot be decimals)
    }
}

main();