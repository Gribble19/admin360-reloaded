package com.battleasya.admin360.listener;

import com.battleasya.admin360.entities.Review;
import com.battleasya.admin360.handler.Config;
import com.battleasya.admin360.handler.Permission;
import com.battleasya.admin360.entities.Admin;
import com.battleasya.admin360.entities.Request;
import com.battleasya.admin360.entities.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinLeaveEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        if (User.hasPermission(player, Permission.ATTEND_TICKET, false)){
            Admin.addAdmin(player.getUniqueId());
            if (Config.message_pending_on_join) {
                User.messagePlayer(player,Config.pending_tickets
                        .replace("<PENDING>", String.valueOf(Request.getPndLstSize())));
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){

        UUID playerID = event.getPlayer().getUniqueId();

        Request.removeAdmin(playerID);

        Admin.removeAdmin(playerID);

        Review.removePlayer(playerID);

        Request.removePlayer(playerID);

    }

}
