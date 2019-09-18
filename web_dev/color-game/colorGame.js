var colors;
var correctColor;
var bgColor = window.getComputedStyle(document.body).getPropertyValue("background-color");

var squares = document.querySelectorAll(".square");
var mainHeaderDisplay = document.getElementById("mainHeader");
var colorDisplay = document.getElementById("colorDisplay");
var msgDisplay = document.getElementById("msg");

var modeButton = document.getElementById("mode");
var resetButton = document.getElementById("reset");
var difficultyButtons = document.querySelectorAll(".difficulty");
var currentDifficultyButton = document.querySelector(".selected");

var difficulties = {
    easy: 3,
    medium: 6,
    hard: 9
};

var numSquares = difficulties.medium;

init();

function init() {
    initButtons();
    startGame();
}

// Also serves to reset game
function startGame() {
    colors = generateRandomColorsArray(numSquares);
    correctColor = pickColor();
    colorDisplay.textContent = modeButton.textContent.toLowerCase() === "hex" ? correctColor : toHex(correctColor);
    msgDisplay.textContent = "";
    mainHeaderDisplay.style.backgroundColor = "steelblue";
    resetButton.textContent = "New Colors";
    initSquares();
}

// Add event listeners to difficulty buttons and reset button
function initButtons() {
    for (let i = 0; i < difficultyButtons.length; i++) {
        difficultyButtons[i].addEventListener("click", function () {
            let difficulty = this.textContent.toLowerCase();

            if (difficulties[difficulty] !== numSquares) {
                numSquares = difficulties[difficulty];

                currentDifficultyButton.classList.remove("selected");
                this.classList.add("selected");
                currentDifficultyButton = this;

                startGame();
            }
        });
    }

    modeButton.addEventListener("click", function() {
        let currModeText = this.textContent.toLowerCase();

        colorDisplay.textContent = currModeText === "hex" ? toHex(correctColor): correctColor;
        this.textContent = currModeText === "hex" ? "rgb" : "hex";
    });

    resetButton.addEventListener("click", startGame);
}

// Color the squares, add listeners, and hide unused squares
function initSquares() {
    for (let i = 0; i < numSquares; i++) {
        // Display the squares
        squares[i].style.display = "block";

        // Add colors to squares
        squares[i].style.backgroundColor = colors[i];

        // Add click listeners to squares
        squares[i].addEventListener("click", function () {
            let currColor = this.style.backgroundColor;

            if (currColor === correctColor) {
                msgDisplay.textContent = "Correct";
                mainHeaderDisplay.style.backgroundColor = currColor;
                resetButton.textContent = "Play Again?";
                changeAllColors(currColor);
            } else {
                this.style.backgroundColor = bgColor;
                msgDisplay.textContent = "Try Again";
            }
        });
    }

    // Hide unused squares
    for (let i = numSquares; i < squares.length; i++) {
        squares[i].style.display = "none";
    }
}

// Change rgb string to hex
// Assumes format rgb(r, g, b)
function toHex(rgb) {
    let strs = rgb.split(",");

    let r = strs[0];
    let g = strs[1];
    let b = strs[2];

    r = r.substring(r.indexOf("(")+1);
    g = g.substring(1);
    b = b.substring(1, b.length-1);

    r = rgbToHex(r);
    g = rgbToHex(g);
    b = rgbToHex(b);

    return "#" + r + g + b;
}

function rgbToHex(rgb) {
    let hex = Number(rgb).toString(16);
    return hex.length < 2 ? "0" + hex : hex;
}

// Change background colors of h1 and all squares
function changeAllColors(color) {

    for (let i = 0; i < squares.length; i++) {
        squares[i].style.backgroundColor = color;
    }
}

// Pick a random element from colors array
function pickColor() {
    return colors[Math.floor(Math.random() * colors.length)];
}

// Generate an array of length num and fill with random colors
function generateRandomColorsArray(num) {
    let arr = [];

    for (let i = 0; i < num; i++) {
        let randomColor = generateRandomColor();

        while (randomColor === bgColor) {
            randomColor = generateRandomColor();
        }

        arr.push(randomColor);
    }

    return arr;
}

// Generate a string representing a color in format rgb(r, g, b)
function generateRandomColor() {
    let r = Math.floor(Math.random() * 256);
    let g = Math.floor(Math.random() * 256);
    let b = Math.floor(Math.random() * 256);

    return "rgb(" + r + ", " + g + ", " + b + ")";
}