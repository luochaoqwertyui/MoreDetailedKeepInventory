# More Detailed Keep Inventory

一个更灵活的死亡不掉落模组，允许你精确控制哪些玩家死亡时保留物品和经验。

## 功能

- 通过指令精确控制哪些玩家启用死亡不掉落，无需修改全局 `gamerule keepInventory`
- 玩家死亡时保留背包物品、装备、经验和分数
- 数据持久化存储，重启后不丢失

## 指令

所有指令需要游戏管理员权限（`gamemaster` 级别）。

| 指令 | 说明 |
|------|------|
| `/keepInventory add <目标>` | 将指定玩家添加到死亡不掉落列表 |
| `/keepInventory remove <目标>` | 将指定玩家从死亡不掉落列表中移除 |
| `/keepInventory list` | 查看当前已启用死亡不掉落的玩家列表 |

示例：

```
/keepInventory add @s          # 将自己添加到列表
/keepInventory add @a          # 将所有玩家添加到列表
/keepInventory add Player123   # 将指定玩家添加到列表
/keepInventory remove @s       # 将自己从列表移除
/keepInventory list            # 查看列表
```

## 安装要求

- Minecraft **1.21.5** (26.2)
- Fabric Loader **>= 0.19.3**
- Fabric API
- Java **>= 25**

## 安装方法

1. 安装 [Fabric Loader](https://fabricmc.net/use/)
2. 下载 [Fabric API](https://modrinth.com/mod/fabric-api)
3. 将本模组和 Fabric API 放入 `mods` 文件夹
4. 启动游戏

## 数据存储

玩家数据存储在游戏目录下的 `more-detailed-keep-inventory.json` 文件中。

## 许可证

CC0-1.0