# ❤️ Damage Indicators ![GitHub Release](https://img.shields.io/github/v/release/Lightre/damage-indicators?color=%23fb5d62) ![Modrinth Downloads](https://img.shields.io/modrinth/dt/damageindicators?color=%23fb5d62)

**Damage Indicators** displays clear, real-time damage indicators above players and mobs, making it easy to see hits in PvP and PvE.

![Damage Indicators Banner](https://cdn.modrinth.com/data/cached_images/95be6fc1b4fbf85158bc2c60d35f8abd1a14c5d7_0.webp)

## ✨ Features

- Visual damage indicators
- Optimized for PvP and PvE
- Minimalist and performance-friendly
- Customizable via `.yml` configuration

![---](https://i.imgur.com/LJD65XI.png)

## ⚙️ Configuration

Customize the indicators via the config file:


<details>
<summary>config.yml</summary>

```yaml
# -------------------------------------------------- #
#               DamageIndicators Config              #
# -------------------------------------------------- #

# Master switch for the plugin.
# If set to false, no indicators will be shown.
# You can also change this setting with the `/di toggle` command. (default: true)
enabled: true

# Text to appear at the beginning of the damage indicator.
# You can use color codes with '&'. (default: '&c❤ ')
indicator-prefix: '&c❤ '

# Time the damage indicator stays on screen (in secs)
# You have to use decimal (double) values (default: 2.0)
indicator-duration-seconds: 2.0

# World Blacklist
# List the names of the worlds where you want to disable damage indicators.
disabled-worlds:
# - "world_lobby"
# - "towny"
```

</details>



![---](https://i.imgur.com/LJD65XI.png)

## 🛠️ Installation

1. Download the plugin `.jar` file.
2. Place it into your server's `plugins` folder.
3. Restart the server or use `/reload`.
4. Customize `plugins/DamageIndicators/config.yml`.

![---](https://i.imgur.com/LJD65XI.png)

## 🎯 Usage

* Damage indicators for players and mobs are automatically active.
* Config changes are adjustable via `.yml`, some require reload.
* Optimized for both PvP and PvE servers.

![---](https://i.imgur.com/LJD65XI.png)

## ⚡ Commands

| Command      | Description                                                     |
|--------------| --------------------------------------------------------------- |
| `/di help`   | Displays information about the plugin.                          |
| `/di reload` | Reloads the plugin configuration without restarting the server. |
| `/di toggle` | Toggle damage indicators on/off.                                |

![---](https://i.imgur.com/LJD65XI.png)

## 😎 Preview

![Preview](https://cdn.modrinth.com/data/8B6f2zti/images/6d49ef3b329a021aa9113cf5f60494ffa32099e0.png)

![---](https://i.imgur.com/LJD65XI.png)

<p align="center">
  — Made by Lightre
</p>