package xyz.semoteo.ready.mod;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.darkmagician6.eventapi.EventManager;

import xyz.semoteo.ready.Ready;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.KeyDownEvent;
import xyz.semoteo.ready.font.FontManager;
import xyz.semoteo.ready.mod.impl.combat.Aura;
import xyz.semoteo.ready.mod.impl.combat.AutoPot;
import xyz.semoteo.ready.mod.impl.combat.AutoSoup;
import xyz.semoteo.ready.mod.impl.combat.Criticals;
import xyz.semoteo.ready.mod.impl.combat.FastBow;
import xyz.semoteo.ready.mod.impl.combat.NoKnockback;
import xyz.semoteo.ready.mod.impl.movement.Fly;
import xyz.semoteo.ready.mod.impl.movement.Inventory;
import xyz.semoteo.ready.mod.impl.movement.Jesus;
import xyz.semoteo.ready.mod.impl.movement.NoSlowdown;
import xyz.semoteo.ready.mod.impl.movement.Scaffold;
import xyz.semoteo.ready.mod.impl.movement.Speed;
import xyz.semoteo.ready.mod.impl.movement.Sprint;
import xyz.semoteo.ready.mod.impl.movement.Step;
import xyz.semoteo.ready.mod.impl.none.nobob;
import xyz.semoteo.ready.mod.impl.player.AutoArmor;
import xyz.semoteo.ready.mod.impl.player.FastUse;
import xyz.semoteo.ready.mod.impl.player.Sneak;
import xyz.semoteo.ready.mod.impl.render.Hud;
import xyz.semoteo.ready.mod.impl.render.NameTags;
import xyz.semoteo.ready.mod.impl.render.ShowShell;
import xyz.semoteo.ready.utils.FileUtils;

public class ModManager {

	private static List<Mod> registeredModules = new ArrayList<>();
	private static File file;
	public static void setup() {
		file = FileUtils.getConfigFile("mods");
		registerModule(new NoKnockback());
		registerModule(new NoSlowdown());
	    registerModule(new Sprint());
	    registerModule(new Hud());
	    registerModule(new Criticals());
	    registerModule(new Aura());
	    registerModule(new Jesus());
	    registerModule(new FastBow());
	    registerModule(new FastUse());
	    registerModule(new Inventory());
	    registerModule(new AutoSoup());
	    registerModule(new Speed());
	    registerModule(new AutoPot());
	    registerModule(new nobob());
	    registerModule(new NameTags());
	    registerModule(new Fly());
	    registerModule(new Step());
	    registerModule(new Scaffold());
	    registerModule(new ShowShell());
	    registerModule(new AutoArmor());
	    registerModule(new Sneak());
	    loadModules(); 
	    saveModules();
	    EventManager.register(new ModManager());
	}
	
	 @EventListener
	  public void onKey(KeyDownEvent event) {
		 
		 
		 
	 }
	  
	  public static void saveModules() {
	    List<String> save = new ArrayList<>();
	    save.add("# MODNAME:ENABLED:SHOWN");
	    for (Mod mod : registeredModules)
	      save.add(String.valueOf(mod.name) + ':' + mod.enabled + ':' + mod.shown + ':' + mod.key); 
	    FileUtils.write(file, save, true);
	  }
	  
	  public static void loadModules() {
	    List<String> save = FileUtils.read(file);
	    for (String str : save) {
	      if (str.startsWith("#"))
	        continue; 
	      String[] split = str.split(":");
	      Mod mod = getMod(split[0]);
	      if (mod.enabled != Boolean.parseBoolean(split[1])) {
	        mod.toggle(); 
	      }
	      mod.shown = Boolean.parseBoolean(split[2]);
	      mod.key = Integer.parseInt(split[3]);
	      Random rand = new Random();
	      for (Mod modteo : getModules()) {
	        float r = (float)((rand.nextFloat() / 2.0F) + 0.5D);
	        float g = (float)((rand.nextFloat() / 2.0F) + 0.5D);
	        float b = (float)((rand.nextFloat() / 2.0F) + 0.5D);
	        modteo.Color = (new Color(r, g, b)).getRGB();
	      } 
	    } 
	  }
	  
	  public static Mod getMod(String name) {
	    for (Mod mod : getModules()) {
	      if (mod.name.equalsIgnoreCase(name))
	        return mod; 
	      byte b;
	      int i;
	      String[] arrayOfString;
	      for (i = (arrayOfString = mod.alias).length, b = 0; b < i; ) {
	        String kisaltma = arrayOfString[b];
	        if (kisaltma.equalsIgnoreCase(name))
	          return mod; 
	        b++;
	      } 
	    } 
	    return null;
	  }
	  
	  public static void registerModule(Mod mod) {
	    registeredModules.add(mod);
	  }
	  
	  public static List<Mod> getModules() {
	    registeredModules.sort(new Comparator<Mod>() {
	          public int compare(Mod m1, Mod m2) {
	            float s1 = Ready.fm.semosaksofon_t.getStringWidth(m1.name);
	            float s2 = Ready.fm.semosaksofon_t.getStringWidth(m2.name);
	            return Float.compare(s2, s1);
	          }
	        });
	    return registeredModules;
	  }
	}
