package net.azisaba.rcitemloggingintegration.integration;

import me.yic.xconomy.api.event.PlayerAccountEvent;
import net.azisaba.rcitemlogging.RcItemLogging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class XConomyListener implements Listener {
    private Logger logger;

    public XConomyListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerAccountEvent(PlayerAccountEvent e) {
        logger.info(String.format("AccountName: %s, balance: %s, amount: %s, isAdd: %b, method: %s, uuid: %s, reason: %s",
                e.getaccountname(),
                e.getbalance().toString(),
                e.getamount().toString(),
                e.getisadd(),
                e.getmethod(),
                e.getUniqueId(),
                e.getreason()
        ));
        RcItemLogging.getApi().put(
                "xconomy_player_money",
                "#none",
                e.getaccountname(),
                String.format("%s by %s (%s)", e.getreason(), e.getmethod(), e.getisadd() ? "add" : "non-add"),
                e.getUniqueId()
        );
    }
}
