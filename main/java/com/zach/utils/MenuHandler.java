package com.zach.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MenuHandler {
    private ConcurrentHashMap<UUID, Menu> openMenus = new ConcurrentHashMap<>();

    public void openMenu(Player player, Menu menu) {
        openMenus.put(player.getUniqueId(), menu);
        menu.open(player);
    }

    public void closeMenu(Player player) {
        if (!openedMenu(player)) return;

        openMenus.remove(player.getUniqueId());
        player.closeInventory();
    }

    public boolean openedMenu(Player player) {
        if (!openMenus.isEmpty())  {
            return openMenus.keySet().contains(player.getUniqueId());
        }
        return false;
    }

    public Menu getMenu(Player player) {
        return openMenus.get(player.getUniqueId());
    }

    public void closeAll() {
        for (UUID uuid : openMenus.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            closeMenu(player);
            player.closeInventory();
        }
    }
    public Listener getListeners() {
        return new Listener() {


            @EventHandler
            public void onInventoryClick(InventoryClickEvent e) {
                if (e.getClickedInventory() == null) return;

                Player player = (Player) e.getWhoClicked();

                if (!openedMenu(player)) return;

                Menu menu = openMenus.get(player.getUniqueId());


                if (e.getClickedInventory().equals(e.getView().getTopInventory())) {

                    menu.performClick(menu, e);


                }


            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent e) {
                Player player = (Player) e.getPlayer();
                if (openedMenu((Player) e.getPlayer())) closeMenu(player);
            }

            @EventHandler
            public void onPlayerLeave(PlayerQuitEvent e) {
                if (openedMenu((Player) e.getPlayer())) closeMenu(e.getPlayer());
            }

            @EventHandler
            public void onPlayerDeath(PlayerDeathEvent e) {
                if (openedMenu(e.getEntity())) closeMenu(e.getEntity());
            }
        };
    }

}
