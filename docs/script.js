async function main() {
    const response = await fetch("stats.json");
    const stats = await response.json();

    console.log(stats)
}

main();