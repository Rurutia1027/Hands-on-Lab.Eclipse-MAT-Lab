# Hands-on-Lab.Eclipse-MAT-Lab

This project lets you quickly generate Java exception and memory dump scenarios for [Eclipse Memory Analyzer Tool (MAT)](https://help.eclipse.org/latest/index.jsp?topic=/org.eclipse.mat.ui.help/welcome.html).
No complex setup required-just run and start analyzing. 

## Features 
- Ready-to-use Docker image,  no local build required
- Supports Jib build to create a runnable image
- Automatically triggers multiple exception/memory leak scenarios
- Generates `.dump` files ready for MAT analysis

## Quick Start 

### Run with Docker
```bash
docker run --rm -v $(pwd)/dumps:/app/dumps nanachi1027/mat-hands-on:latest
```
- After running, `.dump` files for different scenarios will appear in `./dumps`

### Build and Run with Jib 
```bash
./mvnw compile jib:dockerBuild -Dimage=mat-hands-on:latest
docker run --rm -v $(pwd)/dumps:/app/dumps mat-hands-on:latest
```

### Load Dumps into MAT 
- Open MAT
- Go to `File` -> `Open Heap Dump`
- Select a generated `.dump` file 
- Star analyzing exceptions and memory issues

## Example Scenarios 
- Heap memory leak simulation
- Multi-thread deadlock simulation
- Common Java exception triggers

> Each run overwrites previous dumps. Check the dumps directory for the latest files.

## Learn More About MAT 
For a full, detailed introduction to MAT's features and analysis techniques, check out my Medium blog: **[Medium: Hands-on MAT Deep Dive]()**

This article explains MAT's capabilities in depth and shows how to analyze different types of Java heap dumps effectively. 

## Target Users 
- Java developers and testers
- Engineers learning MAT hands-on
- Anyone wanting practical exception and memory analysis examples 

