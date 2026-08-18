# More Detailed Keep Inventory

A more flexible keep inventory mod that lets you precisely control which players keep their items and experience on death.

## Features

- Precisely control which players have keep inventory enabled via commands, without modifying the global `gamerule keepInventory`
- Preserves inventory items, equipment, experience, and score on death
- Data is persisted to disk, survives server restarts

## Commands

All commands require game master permission (`gamemaster` level).

| Command | Description |
|---------|-------------|
| `/keepInventory add <targets>` | Add players to the keep inventory list |
| `/keepInventory remove <targets>` | Remove players from the keep inventory list |
| `/keepInventory list` | Show all players currently in the keep inventory list |

Examples:

```
/keepInventory add @s          # Add yourself to the list
/keepInventory add @a          # Add all players to the list
/keepInventory add Player123   # Add a specific player to the list
/keepInventory remove @s       # Remove yourself from the list
/keepInventory list            # Show the list
```

## Requirements

- Minecraft **1.21.5** (26.2)
- Fabric Loader **>= 0.19.3**
- Fabric API
- Java **>= 25**

## Download

Download the latest version from [GitHub Releases](https://github.com/Lesungend/more-detailed-keep-inventory/releases), or build from source:

```bash
git clone https://github.com/Lesungend/more-detailed-keep-inventory.git
cd more-detailed-keep-inventory
./gradlew build
```

The built JAR is located at `build/libs/more-detailed-keep-inventory-0.0.1.jar`.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/)
2. Download [Fabric API](https://modrinth.com/mod/fabric-api)
3. Place this mod and Fabric API into the `mods` folder
4. Launch the game

## Data Storage

Player data is stored in the `more-detailed-keep-inventory.json` file in the game directory.

## License

MIT License — Attribution required