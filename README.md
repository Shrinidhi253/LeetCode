# LeetCode

This repo contains my solutions to leetcode problems, which are organised and tracked using git workflows.

## Stats and Topics solved
**Programming languages used:** Java

## How this repo documents progress:

### 1. Issues
Every leetcode problem is opened as a Github issue containing the:
- problem statement
- examples of inputs/outputs
- constraints
- link to the original leetcode question

### 2. Branches and PRs
Every solution is added from it's own branch following the convention: `questionNum-problem-title`. <br>

The corresponding PR description includes screenshots of the submission results (number of test cases passed, percentile the solution beats, memory, etc)

### 3. Afterthoughts & PR comments
When revisiting a problem later, if there are any improvements or alternative approaches I can think of, they are added as comment/ code review. <br>

For smaller afterthoughts, just a pseudocode or a description of the improvement is commented. If the change is very significant, the issue is reopened.

### 4. Solutions improving over time
Each **solution** is in a class named using the convention: `ProblemTitle_ProblemNumber`.

If a solution has been improved over time, each version is preserved as a method within the class using the naming convention `problemMethodSolution1`, `problemMethodSolution2` ... and so on to **document the full thinking process** as much as possible.

### 5. GitHub Actions
The repository also uses GitHub automated workflows to automatically update the statistics of the questions solved.

When an issue for a problem is closed: <br>
1. GitHub actions run a workflow job
2. This job gets the topic and difficulty of the problem from the issue
3. It updates the `stats.json` file with the count of how many different topics have been solved and in what difficulty.
4. These stats are then visualised using GitHub pages. The link to this page is given in [Stats and topics solved](#stats-and-topics-solved)



## Repo/ File structure
```
└── .github         #contains the workflow scripts
    ├── scripts/ build_stats.py
    └── workflows/ update_stats.yml  

└── docs            #code for "GitHub Pages" for the topic stats
    ├── index.html
    ├── script.js
    ├── stats.json
    └── styles.css

└── src             #contains the solutions
    ├── easy        
    ├── hard        
    ├── helper      #helper classes part of the problem
    └── medium
```