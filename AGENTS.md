# Repository guide

This is a Java 8 game server. Run commands from the repository root because the
server loads `cache/`, `config/`, and `data/` by relative path. See `README.md`
for player setup and the desktop launcher.

## Find the relevant code

- `src/main/java/com/rs2/Server.java`: startup and game loop.
- `src/main/java/com/rs2/launcher/`: control panel and its settings UI.
- `src/main/java/com/rs2/net/packet/handler/`: incoming client actions;
  `src/main/java/com/rs2/net/packet/PacketSender.java`: outgoing packets.
- `src/main/java/com/rs2/model/quest/impl/`: quest scripts;
  `src/main/java/com/rs2/model/skill/`: skills.
- `src/main/java/com/rs2/model/interaction/`, `objects/`, `npc/`, `combat/`,
  and `gameplay/`: world interactions and game systems.
- `src/main/java/com/rs2/bot/`: bot behavior.
- `data/content/`, `data/npcs/`, and `data/world/`: bundled game definitions.

Search within the relevant package first. Several Java files are very large, so
find symbols or line ranges before reading a whole file. `data/launcher/sprites/`
contains thousands of PNG assets and is excluded from default ripgrep searches
by `.rgignore`. Use `rg --no-ignore` when searching sprite filenames.

For quick navigation, use `rg --files src/main/java/com/rs2` to find source
filenames, then `rg -n 'symbol' src/main/java/com/rs2/<relevant package>` to
locate code. Search `data/content/`, `data/npcs/`, or `data/world/` separately
for game definitions.

## Verify changes

- `Build.bat --no-pause` compiles all sources and packages `dist/server.jar`.
  A nonzero exit code means failure. `Build.bat` without the flag remains
  interactive for double-click use.
- `powershell -NoProfile -ExecutionPolicy Bypass -File .\Smoke.ps1` builds and
  runs isolated definition, chat, and server startup smoke checks.
- The project uses bundled JARs in `lib/` and has no package manager. Use JDK 8
  for the build.

Keep runtime saves, logs, and local configuration out of source changes unless
the task specifically requires them. The smoke script uses a temporary working
directory so it does not change normal game data.
