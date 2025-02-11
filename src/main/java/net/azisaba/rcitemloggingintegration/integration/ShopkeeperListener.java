package net.azisaba.rcitemloggingintegration.integration;

import com.nisovin.shopkeepers.api.events.ShopkeeperTradeCompletedEvent;
import com.nisovin.shopkeepers.api.events.ShopkeeperTradeEvent;
import com.nisovin.shopkeepers.api.shopkeeper.TradingRecipe;
import com.nisovin.shopkeepers.api.util.UnmodifiableItemStack;
import net.azisaba.rcitemlogging.RcItemLogging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;
import java.util.logging.Logger;

public class ShopkeeperListener implements Listener {
    public static final String SHOPKEEPER_TRADE = "shopkeeper_trade";
    public static final String PREFIX_SHOPKEEPER_ENTITY = "shopkeeper:";

    private final Logger logger;

    public ShopkeeperListener(@NotNull Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTradeCompleted(ShopkeeperTradeCompletedEvent event) {
        ShopkeeperTradeEvent tradeEvent = event.getCompletedTrade();
        TradingRecipe recipe = tradeEvent.getTradingRecipe();
        Player player = tradeEvent.getPlayer();

        // Add log line
        RcItemLogging.getApi().put(
                SHOPKEEPER_TRADE,
                PREFIX_SHOPKEEPER_ENTITY + tradeEvent.getShopkeeper().getName(),
                player.getName(),
                getMessage(recipe),
                player.getUniqueId()
        );
    }

    private static String getMessage(TradingRecipe recipe) {
        StringJoiner sj = new StringJoiner(" ");

        // Require items
        sj.add(itemToString(recipe.getItem1(), OperatorType.MINUS));
        if(recipe.getItem2() != null) {
            sj.add("&").add(itemToString(recipe.getItem2(), OperatorType.MINUS));
        }

        sj.add("->");

        // Result item
        sj.add(itemToString(recipe.getResultItem(), OperatorType.PLUS));
        return sj.toString();
    }

    public static String itemToString(UnmodifiableItemStack itemStack, OperatorType operator) {
        return itemStack.getType() + " " + operator.getSymbol() + itemStack.getAmount();
    }

    public static enum OperatorType {
        EQUAL("="),
        MINUS("-"),
        PLUS("+");
        private final String symbol;

        OperatorType(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
