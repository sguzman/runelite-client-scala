/*
 * Copyright (c) 2017, Aria <aria@ar1as.space>
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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.awt.Color;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Singleton;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.GameObject;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.ItemLayer;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.Node;
import net.runelite.api.NullObjectID;
import net.runelite.api.ObjectID;
import net.runelite.api.Player;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.TileItem;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

@Singleton
public class XPerOverlay extends Overlay {
    private static final int MAX_DISTANCE = 2400;
    private static final Color RED = new Color(221, 44, 0);
    private static final Color BLUE = new Color(0, 162, 255);

    // Use loggers to log that we found a yew tree
    private static final Logger logger = LoggerFactory.getLogger(XPerOverlay.class);

    // Set containing all the yew tree game object ids
    private static final Set<Integer> MONSTERS = ImmutableSet.of(
            1274, 1275);

    // Use class ModelOutlineRenderer to render the outline of the object

    private final Client client;
    private final XPerPlugin plugin;
    private final XPerConfig config;

    @Inject
    private XPerOverlay(Client client, XPerPlugin plugin, XPerConfig config) {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    // Function predicate to check if the object is a monster
    private static boolean isMonster(NPC npc) {
        // Get id of the object
        int id = npc.getComposition().getId();
        return MONSTERS.contains(id);
    }

    // Retrieve monsters from client as a list
    private static List<NPC> getMonsters(Graphics2D graphics, Client client) {
        // Get all the npcs in the scene
        List<NPC> npcs = client.getNpcs();

        // Null check
        if (npcs == null) {
            // Return empty list
            return Arrays.asList();
        }

        // Filter out the monsters
        return npcs
                .stream()
                // Is not null
                .filter(npc -> npc != null)
                // Composition is not null
                .filter(npc -> npc.getComposition() != null)
                .filter(XPerOverlay::isMonster)
                .collect(Collectors.toList());
    }

    private void renderMonsters(Graphics2D graphics) {
        // Get all the monsters in the scene
        List<NPC> npcs = getMonsters(graphics, client);

        for (NPC npc : npcs) {
            // Get id of the object
            int id = npc.getComposition().getId();
            OverlayUtil.renderActorOverlay(graphics, npc, id + "", RED);

            // Render the outline of the monster
            Shape objectClickbox = npc.getConvexHull();
        }
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (this.config.activate()) {
            this.renderMonsters(graphics);
        }

        return null;
    }
}