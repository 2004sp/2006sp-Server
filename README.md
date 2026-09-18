# 2006Scape Singleplayer Server

This is the game server for the 2006Scape single-player project. It provides the
world simulation, networking, player persistence, NPCs, skills, quests,
minigames, trading, and configurable bot populations used by the matching Java
client. It includes a desktop control panel for starting, stopping, configuring,
and monitoring the server.

The server listens on port `43594` by default. Player and hiscore data can be
stored in the embedded SQLite database at `data/server.db`, so an external
database server is not needed.

## Requirements

- Windows (the included build and run scripts are batch files)
- A Java Development Kit (JDK), preferably **JDK 8**
- `java`, `javac`, and `jar` available on `PATH`
- The matching `2006sp client` project to connect and play
- About 1 GB of free memory for the default server JVM settings

No dependency download or package manager is required. All Java libraries are
included in `lib/`:

- Apache Commons Compress 1.0
- Joda-Time 2.3
- SQLite JDBC 3.53.4.0
- SLF4J API and no-operation provider 2.0.17
- `javac++.jar`, a bundled project runtime dependency

The revision 377 cache and the server's content data are also included in the
`cache/` and `data/` directories.

## Build

From File Explorer, double-click `Build.bat`. From Command Prompt, run:

```bat
cd /d "C:\Users\Callum\Downloads\New folder\2006sp-Server"
Build.bat
```

The script compiles every Java file under `src/main/java`, uses all JARs in
`lib/` as the classpath, and creates:

```text
dist/server.jar
```

The dependencies remain in `lib/`; keep that directory next to `dist/` when
running the server.

## Run

Start the compiled server control panel with:

```bat
cd /d "C:\Users\Callum\Downloads\New folder\2006sp-Server"
Run.bat
```

When the control panel opens:

1. Review the connection and gameplay settings if needed.
2. Click **Start Server** and wait for the status to show that it is online.
3. Start the matching client with its `run.bat` file.
4. Log in with the username and password you want to use. Local player data is
   created and saved by the server.

Keep the project directory structure intact. `Run.bat` uses the project root as
the working directory so the relative paths to `config/`, `data/`, `cache/`, and
`lib/` resolve correctly.

## Configuration and data

- `config/server.cfg` controls membership, XP rates, bots, shops, drops,
  gameplay options, LAN access, and other server behaviour.
- `data/settings.dat` stores settings managed by the control panel.
- `data/server.db` is the generated SQLite player/hiscore database.
- `data/characters/` contains generated character data when file-based storage
  is used.
- `cache/` contains the revision 377 cache required by the server.

The client defaults to `127.0.0.1:43594`. Leave LAN support disabled for a
local-only game. If you enable LAN connections, ensure your firewall and network
settings allow the selected server port.

## Project layout

```text
src/main/java/       Server, networking, gameplay, bots, and control-panel source
lib/                 Bundled runtime dependencies
config/              Editable server configuration
cache/               Revision 377 game cache
data/                Content, settings, saves, logs, and SQLite data
Build.bat             Compiles and packages the server
Run.bat               Opens the server control panel
dist/server.jar       Generated executable JAR
```
