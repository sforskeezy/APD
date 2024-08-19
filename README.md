# AdvancedPermissionDumper for Spigot 1.8

A powerful plugin that allows you to dump all permissions from a specified plugin or all plugins on your server into a YAML file. This plugin is designed to work with any plugin installed on your server.

**Developed by Sforskeezy**

## Commands

- `/apd <plugin>` - Dumps all permissions of the specified plugin to a YAML file.
- `/apd ALL` - Dumps all permissions of all plugins on the server to a YAML file.

## Permissions

- `apd.use` - Allows the use of the `/apd` command.

## Features

- Dumps actual permissions, including wildcard permissions like `plugin.*`.
- Automatically organizes and formats permissions in the output YAML file.
- Supports tab completion for all plugin names.

## Usage

Simply use the `/apd <plugin>` command to generate a `permissions-dump.yml` file in the plugin's data folder, containing all the permissions of the specified plugin. If you use `/apd ALL`, permissions from all installed plugins will be dumped.

## Example

```bash
/apd WorldEdit
