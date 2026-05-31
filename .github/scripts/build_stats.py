#AIM: This python script will retrieve all issues for the runner machine in the github actions
#The runner machine can only check the code in the repo after checkout. It needs to get
#info about the issues in the repo via the github API
#After getting the issues, the script writes the issue stats to the stats.json file

import requests
import json
import os

GITHUB_TOKEN = os.environ["GITHUB_TOKEN"] #read the secret token generated automatically by github for the API request. Used personal access token for testing...
REPO_OWNER = "Shrinidhi253"
REPO = "LeetCode"

"""
This method fetches all the closed issues in the repository from the Github API
"""
def fetch_closed_issues(page):
    #the max number of issues u can fetch for a page using the API is 100. If there are more than 100 issues, these will be spread across multiple pages
    #the "page" parameter is for the page that we want to fetch
    url = f"https://api.github.com/repos/{REPO_OWNER}/{REPO}/issues?state=closed&per_page=100&page={page}"

    reponse = requests.get(
        url = url,
        headers = {
            "Authorization": f"Bearer {GITHUB_TOKEN}",
            "Accept": "application/vnd.github+json" #to speicify the format of the response
        }
    )

    return reponse.json()

"""
The following method categorises all closed issues by topics and returns a dictionary with the topic
as key and number of easy, medium, hard questions solved in this topic as value
"""
def categorise_closed_issues(all_closed_issues):
    stats = {} #stores stats in the format: {topic : {easy : int, medium : int, hard : int}}

    for issue in all_closed_issues:
        difficulty, topics = get_labels(issue)

        for topic in topics: #increment count of questions solved for a difficulty level in this topic
            if topic not in stats:
                stats[topic] = {"easy" : 0, "medium" : 0, "hard" : 0}
            if difficulty:
                stats[topic][difficulty] += 1

    return stats

"""
This is a helper method for categorise_closed_issues.
It separates and returns the difficulty label and the topic labels
in the format: (difficulty, list of all topics)
"""
def get_labels(issue): 
    difficulty = ""
    topics = []

    for label in issue.get("labels", []):
        label_name = label["name"].lower()

        if label_name in ["easy", "medium", "hard"]:
            difficulty = label_name
        else:
            topics.append(label_name)

    return (difficulty, topics)


"""
This method writes the stats to the "stats.json" file
"""
def write_to_json(stats):
    filepath = "docs/stats.json"
    os.makedirs("docs", exist_ok=True) #create the docs directory if it doesn't exist

    with open(filepath, "w") as file:
        json.dump(stats, file, indent = 4)
    
def main():
    all_closed_issues = []
    #fetch all issues in page 1
    page = 1
    issues = fetch_closed_issues(page)
    
    #as long as there are non-null issues in a page, keep adding closed issues to the list of all closed issues
    while issues:
        all_closed_issues.extend(issues)
        page += 1 #check the next page for more issues
        issues = fetch_closed_issues(page)

    stats = categorise_closed_issues(all_closed_issues)
    write_to_json(stats)

if __name__ == "__main__":
    main()