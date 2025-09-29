<div align="center">

<a href="https://modrinth.com/plugin/damageindicators/" target="_blank" title="Damage Indicators Modrinth"><img width="160px" alt="damage-indicators icon" src="https://cdn.modrinth.com/data/cached_images/c8294d913d9edb23a9825500449c47236e5c5d55.png"></a>

<a name="readme-top"></a>

# ❤️ Damage Indicators

**Damage Indicators** displays clear, real-time damage indicators above players and mobs, making it easy to see hits in PvP and PvE.

![Minecraft Version][minecraft_version_img]
![Downloads][downloads_img]
[![Releases][releases_img]][releases_url]
[![License][repo_license_img]][repo_license_url]

<a href="https://cdn.modrinth.com/data/cached_images/95be6fc1b4fbf85158bc2c60d35f8abd1a14c5d7_0.webp" target="_blank" title="Damage Indicators Preview"><img width="95%" alt="plugin banner" src="https://cdn.modrinth.com/data/cached_images/95be6fc1b4fbf85158bc2c60d35f8abd1a14c5d7_0.webp"></a>

</div>

## ✨ Features

- Visual damage indicators
- Optimized for PvP and PvE
- Minimalist and performance-friendly
- Customizable via `.yml` configuration

---

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

# Special prefix that pops up when a crit lands. (default: '&e✯ ')
critical-indicator-prefix: '&e✯ '

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

---

## 🛠️ Installation

1. Download the plugin `.jar` file.
2. Place it into your server's `plugins` folder.
3. Restart the server or use `/reload`.
4. Customize `plugins/DamageIndicators/config.yml`.

---

## 🎯 Usage

* Damage indicators for players and mobs are automatically active.
* Config changes are adjustable via `.yml`, some require reload.
* Optimized for both PvP and PvE servers.

---

## ⚡ Commands

| Command      | Description                                                     |
|--------------| --------------------------------------------------------------- |
| `/di help`   | Displays information about the plugin.                          |
| `/di reload` | Reloads the plugin configuration without restarting the server. |
| `/di toggle` | Toggle damage indicators on/off.                                |

---

## 😎 Preview

![Preview](https://cdn.modrinth.com/data/8B6f2zti/images/6d49ef3b329a021aa9113cf5f60494ffa32099e0.png)

---

<p align="center">
  — Made by Lightre
</p>

[downloads_img]: https://img.shields.io/modrinth/dt/damageindicators?color=default
[java_url]: https://openjdk.org/projects/jdk/21/
[releases_img]: https://img.shields.io/github/v/release/Lightre/damage-indicators?color=%23fb5d62
[releases_url]: https://github.com/Lightre/damage-indicators/releases
[repo_license_img]: https://img.shields.io/badge/license-ARR-yellow.svg
[repo_license_url]: https://github.com/Lightre/damage-indicators/blob/main/LICENSE
[minecraft_version_img]: https://img.shields.io/badge/minecraft-1.21x-green.svg