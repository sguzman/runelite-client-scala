/*
 * Copyright (c) 2018, Magic fTail
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.xper;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.NpcUtil;
import net.runelite.client.game.npcoverlay.NpcOverlayService;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.npchighlight.NpcIndicatorsConfig;
import net.runelite.client.ui.components.colorpicker.ColorPickerManager;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(name = "AAAAA - XPer Script", description = "Activate Script", tags = {
        "script", "clicker"
}, enabledByDefault = false)
@Slf4j
public class XPerPlugin extends Plugin {
    private static final Logger logger = LoggerFactory.getLogger(XPerOverlay.class);

    // ID of monsters
    private static final Set<Integer> MONSTERS = ImmutableSet.of(1274, 1275);

    // Current list of monsters, sorted by distance to player
    private NPCComposition[] monsters = new NPCComposition[0];

    @Inject
    private Client client;

    @Inject
    private XPerConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private XPerOverlay overlay;

    @Inject
    private ClientThread clientThread;

    @Inject
    private NpcOverlayService npcOverlayService;

    @Inject
    private NpcUtil npcUtil;

    @Inject
    private ConfigManager configManager;

    @Inject
    private ColorPickerManager colorPickerManager;

    @Provides
    public XPerConfig provideConfig(final ConfigManager configManager) {
        return configManager.getConfig(XPerConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        log.info("XPer Plugin is on :)");
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("XPer Plugin is off :(");
        overlayManager.remove(overlay);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        if (event.getGroup().equals(XPerConfig.GROUP)) {
            // ...
        }
    }

    // Subscribe to when an NPC spawns. Store monster in monsters array and sort
    // array by distance to player
    @Subscribe
    public void onNpcSpawned(NpcSpawned npcSpawned) {
        // Log that an NPC spawned
        logger.info("An NPC spawned.");
        // Get the NPC
        NPC npc = npcSpawned.getNpc();

        // Get the NPC composition
        NPCComposition npcComposition = npc.getComposition();
    }
}