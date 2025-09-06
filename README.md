# Hands-on-Lab.Eclipse-MAT-Lab

This project lets you quickly generate Java exception and memory dump scenarios for [Eclipse Memory Analyzer Tool (MAT)](https://help.eclipse.org/latest/index.jsp?topic=/org.eclipse.mat.ui.help/welcome.html).
No complex setup required-just run and start analyzing. 

## Features 
- Ready-to-use Docker image,  no local build required
- Supports Jib build to create a runnable image
- Automatically triggers multiple exception/memory leak scenarios
- Generates `.dump` files ready for MAT analysis

## Project Structure Introduction 
### Package Structure 
```
Hands-on-Lab.MAT-Eclipse/
│
├─ src/
│   ├─ main/
│   │   ├─ java/
│   │   │   └─ com/
│   │   │       └─handson/
│   │   │           └─ matdemo/
│   │   │               ├─ Main.java          # Entry point, runs all or selected scenarios
│   │   │               ├─ scenario/
│   │   │               │   ├─ Scenario1.java  # e.g., heap leak
│   │   │               │   ├─ Scenario2.java  # e.g., deadlock
│   │   │               │   └─ Scenario3.java  # e.g., exception triggers
│   │   │               └─ util/
│   │   │                   └─ DumpUtils.java  # Helper for generating .dump files
│   │   └─ resources/
│   │       └─ config.properties                # Optional scenario configs
│   └─ test/
│       └─ java/...
│
├─ dumps/                  # Generated .dump files
│   ├─ scenario-1/
│   │   └─ heapleak.dump
│   ├─ scenario-2/
│   │   └─ deadlock.dump
│   └─ scenario-3/
│       └─ exception.dump
│
├─ pom.xml                 # Maven project + Jib configuration
└─ README.md
```

### Java Package Design 
Base package: com.handson.matdemo
**`Main.java`**
- Runs all or selected scenarios
- Calls each `DumpScenario` implementation

**scenario package**
- Each scenario implements a common interface
```java
public interface DumpScenario {
    void run(String outputDir); 
}
```
- Each scenario output is its `.dump` to its own folder in `dumps/`

**util package**
- `DumpUtils.java` handles:
1. Creating output directories
2. Generating `.dump` files
3. Naming files (e.g., timestamps, scenario names)

### `.dump` File Organization 

**Separate by scenario**
```
dumps/scenario-1/heapleak.dump
dumps/scenario-2/deadlock.dump
dumps/scenario-3/exception.dump
```

**Optional: include timestamp in filename to avoid overwriting**
```
dumps/scenario-1/heapleak-2025-09-06T14-30.dump
```
- Clear hierarchy makes it **MAT-friendly** and easy to use in hands-on tutorials

### Execution Flow 





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

