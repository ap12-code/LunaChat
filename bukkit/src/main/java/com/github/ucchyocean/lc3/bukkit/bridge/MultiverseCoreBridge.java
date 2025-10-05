/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bukkit.bridge;

import org.bukkit.plugin.Plugin;
import org.mvplugins.multiverse.core.MultiverseCoreApi;
import org.mvplugins.multiverse.core.world.MultiverseWorld;
import org.mvplugins.multiverse.external.vavr.control.Option;


/**
 * MultiverseCore連携クラス
 * @author ucchy
 */
public class MultiverseCoreBridge {

    /** MultiverseCore API クラス */
    private final MultiverseCoreApi api;

    /** コンストラクタは使用不可 */
    private MultiverseCoreBridge(MultiverseCoreApi api) {
        this.api = api;
    }

    /**
     * MultiverseCore-apiをロードする
     * @param plugin MultiverseCoreのプラグインインスタンス
     */
    public static MultiverseCoreBridge load(Plugin plugin) {
        if (plugin == null) return null;
        if (!MultiverseCoreApi.isLoaded()) return null;

        return new MultiverseCoreBridge(MultiverseCoreApi.get());
    }

    /**
     * 指定されたワールドのエイリアス名を取得する
     * @param worldName ワールド名
     * @return エイリアス名、取得できない場合はnullが返される
     */
    public String getWorldAlias(String worldName) {

        Option<MultiverseWorld> worlds = api.getWorldManager().getWorld(worldName);
        if ( worlds.isDefined() ) {
            if (!worlds.get().getAlias().isEmpty()) {
                return worlds.get().getAlias();
            } else {
                return worlds.get().getName();
            }
        } else {
            return null;
        }
    }

}
