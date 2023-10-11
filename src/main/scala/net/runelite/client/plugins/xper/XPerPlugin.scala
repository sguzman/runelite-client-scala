package net.runelite.client.plugins.xper;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.npcoverlay.NpcOverlayService;
import net.runelite.client.game.NpcUtil;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.xper.XPerConfig;
import net.runelite.client.plugins.xper.XPerOverlay;
import net.runelite.client.ui.components.colorpicker.ColorPickerManager;
import net.runelite.client.ui.overlay.OverlayManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PluginDescriptor(
  name = "A - Scala - XPer Script",
  description = "Activate Script",
  tags = Array("xper", "script"),
  enabledByDefault = false
)
@Slf4j
class XPerPlugin extends Plugin {
    val log: Logger = LoggerFactory.getLogger(classOf[XPerPlugin])

    // ID of monsters
    val monsters: Set[Int] = Set(1274, 1275)

    // Current list of monsters, sorted by distance
    var currentMonsters: List[NPCComposition] = List()

    @Inject
    private var client: Client = null

    @Inject
    private var config: XPerConfig = null

    @Inject
    private var overlay: XPerOverlay = null

    @Inject
    private var overlayManager: OverlayManager = null

    @Inject
    private var npcOverlayService: NpcOverlayService = null

    @Inject
    private var npcUtil: NpcUtil = null

    @Inject
    private var colorPickerManager: ColorPickerManager = null

    override def startUp(): Unit = {
        log.info("XPerPlugin started!")
        overlayManager.add(overlay)
    }

    override def shutDown(): Unit = {
        log.info("XPerPlugin stopped!")
        overlayManager.remove(overlay)
    }

    @Subscribe
    def onNpcSpawned(event: NpcSpawned): Unit = {
        val npc: NPC = event.getNpc
        val npcId: Int = npc.getId
        if (monsters.contains(npcId)) {
            val npcComposition: NPCComposition = npc.getTransformedComposition
            currentMonsters = npcComposition :: currentMonsters
        }
    }
}
