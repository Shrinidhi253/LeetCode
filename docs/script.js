async function main() {
    const response = await fetch("stats.json");
    const stats = await response.json();

    displayDifficultySummary(stats["difficultySummary"]);

    console.log(stats)
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

main();