# 2006Scape Singleplayer Server

This is the game server for the 2006Scape single-player project. It provides the
world simulation, networking, player persistence, NPCs, skills, quests,
minigames, trading, and configurable bot populations used by the matching Java
client. It includes a desktop control panel for starting, stopping, configuring,
and monitoring the server.

The server listens on port `43594` by default. Player and hiscore data can be
stored in the embedded SQLite database at `data/server.db`, so an external
database server is not needed.

**Revision 443 migration:** This branch defaults to a 443 cache path, login, and
update handshake. The static region packet now uses the 443 layout and XTEA
keys from `cache/xteas.json`. The matching client loads 443 JS5 assets when
started with `run.bat`; packet and interface behavior still needs graphical
validation.

## Requirements

- Windows (the included build and run scripts are batch files)
- JDK 1.8.0\_101 (Java SE Development Kit 8u101) - [Oracle Java SE 8 Archive Downloads](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
- Make sure to set `java`, `javac`on `PATH` in Enviroment Variables
- The Client is `2006sp client`
- About 1 GB of free RAM

No dependency download or package manager is required. All Java libraries are
included in `lib/`:

- Apache Commons Compress 1.0
- Joda-Time 2.3
- SQLite JDBC 3.53.4.0
- SLF4J API and no-operation provider 2.0.17
- `javac++.jar`, a bundled project runtime dependency

The revision 443 JS5 cache and map XTEA keys are in `cache/`. Server content
data is in `data/`.

## Build

From File Explorer, double-click `Build.bat`. From Command Prompt, run:

```bat
cd /d "location of server"
Build.bat
```

The script compiles every Java file under `src/main/java`, uses all JARs in
`lib/` as the classpath, and creates:

```text
dist/server.jar
```

The dependencies remain in `lib/`; keep that directory next to `dist/` when
running the server.

For a build that exits without waiting for a keypress, run `Build.bat --no-pause`.
This is useful for terminals and automation; it returns a nonzero exit code when
compilation or packaging fails.


## Run

Start the compiled server control panel with:

```bat
cd /d "location of server"
Run.bat
```

When the control panel opens:

1. Review the connection and gameplay settings if needed.
2. Click **Start Server** and wait for the status to show that it is online.
3. Start the matching client with its `run.bat` file.
4. Log in with the username and password you want to use. Local player data is
   created and saved by the server.

To diagnose a click that does nothing, enable **Debug Mode** in the launcher
before starting the server. Click packet details and interaction handling
messages will appear in the server terminal with the `[packet-debug]` prefix.
The same trace is saved to `qa-output/gameplay-trace.log`.

Inventory clicks also include an `[item-debug]` marker and a structured outcome.
`received` confirms the packet decoded, `rejected` explains validation failures
such as a stale slot or unmapped widget, and `unhandled` identifies an item option
that reached the server but has no gameplay handler. Each line includes the item
ID and name, option, slot, interface, player position, and rejection reason.
Administrators can alternatively enter `::debug` in-game to toggle these
diagnostics for only their player; the terminal prints an `[interaction-debug]`
confirmation when the toggle changes.

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
- `cache/` contains the revision 443 JS5 cache.
- `cache/xteas.json` contains the validated map XTEA keys.

The client defaults to `127.0.0.1:43594`. Leave LAN support disabled for a
local-only game. If you enable LAN connections, ensure your firewall and network
settings allow the selected server port.

## Project layout

```text
src/main/java/       Server, networking, gameplay, bots, and control-panel source
lib/                 Bundled runtime dependencies
config/              Editable server configuration
cache/               Revision 443 JS5 game cache
data/                Content, settings, saves, logs, and SQLite data
Build.bat             Compiles and packages the server
Run.bat               Opens the server control panel
dist/server.jar       Generated executable JAR
```

## Third-party notices

### RS Mod pathfinder

Parts of the route-reach semantics and large-entity clipping approach in this
repository are adapted from the RS Mod pathfinder project.

Copyright (c) 2020 RS Mod

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION
OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN
CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
