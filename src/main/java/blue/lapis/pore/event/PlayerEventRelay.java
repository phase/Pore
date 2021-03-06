/*
 * Pore
 * Copyright (c) 2014-2015, Lapis <https://github.com/LapisBlue>
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package blue.lapis.pore.event;

import blue.lapis.pore.converter.ActionConverter;
import blue.lapis.pore.converter.ItemStackConverter;
import blue.lapis.pore.converter.type.GameModeConverter;
import blue.lapis.pore.converter.vector.LocationConverter;
import blue.lapis.pore.impl.PoreWorld;
import blue.lapis.pore.impl.block.PoreBlock;
import blue.lapis.pore.impl.entity.PoreEntity;
import blue.lapis.pore.impl.entity.PoreItem;
import blue.lapis.pore.impl.entity.PorePlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.entity.EntityInteractBlockEvent;
import org.spongepowered.api.event.entity.living.player.PlayerChangeGameModeEvent;
import org.spongepowered.api.event.entity.living.player.PlayerChangeWorldEvent;
import org.spongepowered.api.event.entity.living.player.PlayerChatEvent;
import org.spongepowered.api.event.entity.living.player.PlayerDeathEvent;
import org.spongepowered.api.event.entity.living.player.PlayerDropItemEvent;
import org.spongepowered.api.event.entity.living.player.PlayerInteractBlockEvent;
import org.spongepowered.api.event.entity.living.player.PlayerInteractEntityEvent;
import org.spongepowered.api.event.entity.living.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.living.player.PlayerMoveEvent;
import org.spongepowered.api.event.entity.living.player.PlayerPickUpItemEvent;
import org.spongepowered.api.event.entity.living.player.PlayerQuitEvent;
import org.spongepowered.api.util.event.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PlayerEventRelay {

    @Subscribe
    public void onPlayerChangeGamemode(final PlayerChangeGameModeEvent event) {
        Bukkit.getPluginManager().callEvent(
                new PlayerGameModeChangeEvent(
                        PorePlayer.of(event.getPlayer()),
                        GameModeConverter.of(event.getNewGameMode())
                )
        );
    }

    @Subscribe
    public void onPlayerChangeWorld(final PlayerChangeWorldEvent event) {
        Bukkit.getPluginManager().callEvent(
                new PlayerChangedWorldEvent(
                        PorePlayer.of(event.getPlayer()),
                        PoreWorld.of(event.getFromWorld())
                )
        );
    }

    @Subscribe
    public void onPlayerChat(final PlayerChatEvent event) {
        Set<Player> players = new HashSet<Player>(PoreWorld.of(event.getPlayer().getWorld()).getPlayers());
        Bukkit.getPluginManager().callEvent(
                new org.bukkit.event.player.AsyncPlayerChatEvent(
                        false, //TODO: determine if this needs to be changed
                        PorePlayer.of(event.getPlayer()),
                        event.getMessage().toLegacy(),
                        players //TODO: players should only include recipients
                ) {
                    @Override
                    public void setCancelled(boolean cancelled) {
                        super.setCancelled(cancelled);
                        //TODO: find a way to cancel the event in Sponge
                    }
                }
        );
        //TODO: possibly call PlayerChatEvent as well (not sure if Bukkit handles that itself)
    }

    @Subscribe
    public void onPlayerDeath(final PlayerDeathEvent event) {
        Bukkit.getPluginManager().callEvent(
                new org.bukkit.event.entity.PlayerDeathEvent(
                        PorePlayer.of(event.getPlayer()),
                        new ArrayList<ItemStack>(), //TODO: item drops
                        0, //TODO: exp drop
                        0, //TODO: new player xp
                        0, //TODO: new total player xp
                        0, //TODO: new player level
                        event.getDeathMessage().toLegacy()
                )
        );
    }

    @Subscribe
    public void onPlayerDropItem(final PlayerDropItemEvent event) {
        Bukkit.getPluginManager().callEvent(
                new org.bukkit.event.player.PlayerDropItemEvent(
                        PorePlayer.of(event.getPlayer()),
                        null
                        //TODO: Sponge returns a Collection<ItemStack> but Bukkit accepts an Item entity O_o
                ) {
                    @Override
                    public void setCancelled(boolean cancelled) {
                        super.setCancelled(cancelled);
                        event.setCancelled(cancelled);
                    }
                }
        );
    }

    @Subscribe
    public void onEntityInteractBlock(final EntityInteractBlockEvent event) {
        if (event instanceof PlayerInteractBlockEvent) {
            final PlayerInteractBlockEvent pEvent = (PlayerInteractBlockEvent) event;
            Bukkit.getPluginManager().callEvent(
                    new org.bukkit.event.player.PlayerInteractEvent(
                            PorePlayer.of(pEvent.getPlayer()),
                            ActionConverter.of(pEvent.getInteractionType(), event.getBlock()),
                            ItemStackConverter.of(pEvent.getPlayer().getItemInHand().get()),
                            PoreBlock.of(event.getBlock()),
                            null //TODO: clicked face
                    ) {
                        @Override
                        public void setCancelled(boolean cancelled) {
                            super.setCancelled(cancelled);
                            //TODO: find a way to cancel event in Sponge
                        }
                    }
            );
        } else {
            Bukkit.getPluginManager().callEvent(
                    new org.bukkit.event.entity.EntityInteractEvent(
                            PoreEntity.of(event.getEntity()),
                            PoreBlock.of(event.getBlock())
                    ) {
                        @Override
                        public void setCancelled(boolean cancelled) {
                            super.setCancelled(cancelled);
                            //TODO: find a way to cancel event in Sponge
                        }
                    }
            );
        }
    }

    @Subscribe
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
        Bukkit.getPluginManager().callEvent(
                new org.bukkit.event.player.PlayerInteractEntityEvent(
                        PorePlayer.of(event.getPlayer()),
                        PoreEntity.of(event.getTargetEntity())
                ) {
                    @Override
                    public void setCancelled(boolean cancelled) {
                        super.setCancelled(cancelled);
                        //TODO: find a way to cancel event in Sponge
                    }
                }
        );
    }

    @Subscribe
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Bukkit.getPluginManager().callEvent(
                new org.bukkit.event.player.PlayerJoinEvent(
                        PorePlayer.of(event.getPlayer()),
                        event.getJoinMessage().toLegacy()
                )
        );
    }

    @Subscribe
    public void onPlayerMove(final PlayerMoveEvent event) {
        Bukkit.getPluginManager().callEvent(
                new org.bukkit.event.player.PlayerMoveEvent(
                        PorePlayer.of(event.getPlayer()),
                        LocationConverter.of(event.getOldLocation()),
                        LocationConverter.of(event.getNewLocation())
                ) {
                    @Override
                    public void setCancelled(boolean cancelled) {
                        super.setCancelled(cancelled);
                        event.setCancelled(cancelled);
                    }
                }
        );
    }

    @Subscribe
    public void onPlayerPickUpItem(final PlayerPickUpItemEvent event) {
        for (Entity drop : event
                .getItems()) { //TODO: possibly rewrite depending on how Sponge implements the event
            if (drop instanceof org.spongepowered.api.entity.Item) {
                Bukkit.getPluginManager().callEvent(
                        new PlayerPickupItemEvent(
                                PorePlayer.of(event.getPlayer()),
                                PoreItem.of((org.spongepowered.api.entity.Item) drop),
                                0 //TODO: remaining item count
                        ) {
                            @Override
                            public void setCancelled(boolean cancelled) {
                                super.setCancelled(cancelled);
                                event.setCancelled(cancelled);
                            }
                        }
                );
            } else {
                //TODO: maybe something, not sure what though
            }
        }
    }

    @Subscribe
    public void onPlayerQuit(final PlayerQuitEvent event) {
        Bukkit.getPluginManager().callEvent(
                new org.bukkit.event.player.PlayerQuitEvent(
                        PorePlayer.of(event.getPlayer()),
                        event.getQuitMessage().toLegacy()
                )
        );
    }

}
