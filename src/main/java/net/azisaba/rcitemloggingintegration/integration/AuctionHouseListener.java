package net.azisaba.rcitemloggingintegration.integration;

import com.spawnchunk.auctionhouse.events.PurchaseItemEvent;
import net.azisaba.rcitemlogging.RcItemLogging;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class AuctionHouseListener implements Listener {
    public static final String AH_BUY_ITEM = "auctionhouse_buy_item";

    @EventHandler
    public void onPlayerBuyItem(PurchaseItemEvent event) {
        RcItemLogging.getApi().put(
                AH_BUY_ITEM,
                getNameFromOfflinePlayer(event.getSeller(), event.getSeller_UUID()),
                getNameFromOfflinePlayer(event.getBuyer(), event.getBuyer_UUID()),
                "Bought " + LegacyComponentSerializer.legacyAmpersand().serialize(event.getItem().displayName()) + " x " + event.getItem().getAmount(),
                UUID.fromString(event.getBuyer_UUID()),
                UUID.fromString(event.getSeller_UUID())
        );
    }

    private String getNameFromOfflinePlayer(OfflinePlayer targetPlayer, String fallback) {
        if(targetPlayer == null) {
            return fallback;
        }
        return targetPlayer.getName();
    }
}
