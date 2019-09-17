var p1Button = document.querySelector("#p1");
var p2Button = document.querySelector("#p2");
var resetButton = document.querySelector("#reset");
var numInput = document.querySelector("input");

var p1ScoreDisplay = document.querySelector("#p1Score");
var p2ScoreDisplay = document.querySelector("#p2Score");
var scoreDisplay = document.querySelector("p");
var maxScoreDisplay = document.querySelector("#maxScore");
var maxScore = Number(maxScoreDisplay.textContent);

var score1 = 0;
var score2 = 0;

scoreDisplay.addEventListener("mouseover", function() {
    scoreDisplay.classList.toggle("highlight");
});

scoreDisplay.addEventListener("mouseout", function() {
    scoreDisplay.classList.toggle("highlight");
});

numInput.addEventListener("change", function() {
    maxScore = numInput.value;
    maxScoreDisplay.textContent = maxScore;
    resetGame();
});

p1Button.addEventListener("click", function() {
    if (score1 < maxScore && score2 < maxScore) {
        p1ScoreDisplay.textContent = ++score1;

        if (score1 == maxScore) {
            p1ScoreDisplay.classList.add("green");
        }
    }
});

p2Button.addEventListener("click", function() {
    if (score1 < maxScore && score2 < maxScore) {
        p2ScoreDisplay.textContent = ++score2;

        if (score2 == maxScore) {
            p2ScoreDisplay.classList.add("green");
        }
    }
});

reset.addEventListener("click", resetGame);

function resetGame() {
    score1 = 0;
    score2 = 0;
    p1ScoreDisplay.textContent = 0;
    p2ScoreDisplay.textContent = 0;
    p1ScoreDisplay.classList.remove("green");
    p2ScoreDisplay.classList.remove("green");
}