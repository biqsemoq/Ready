package xyz.semoteo.ready.friend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xyz.semoteo.ready.utils.FileUtils;

public class FriendManager {
	  public static List<Friend> friendList = new ArrayList<>();
	  
	  private static File file;
	  
	  public static void start() {
	  file = FileUtils.getConfigFile("friendteo");
	    load();
	    save();
	  }
	  
	  public static void load() {
	    List<String> strings = FileUtils.read(file);
	   friendList.clear();
	    for (String str : strings) {
	      if (!str.contains("::"))
	        continue; 
	      String[] split = str.split("::");
	     friendList.add(new Friend(split[0], split[1]));
	    } 
	  }
	  
	  public String replace(String text) {
	    for (Friend friend : this.friendList)
	      text = text.replaceAll(friend.getName(), friend.getAlias()); 
	    return text;
	  }
	  
	  public static void removeFriend(String name) {
	    Friend friend = getFriend(name);
	   friendList.remove(friend);
	  }
	  
	  public static void addFriend(String name, String alias) {
	    if (!isFriend(name)) {
	      Friend friend = new Friend(name, alias);
	      friendList.add(friend);
	      save();
	    } 
	  }
	  
	  public static void save() {
	    List<String> strings = new ArrayList<>();
	    for (Friend friend : friendList)
	      strings.add(friend.getName() + "::" + friend.getAlias()); 
	    FileUtils.write(file, strings, true);
	  }
	  
	  public static boolean isFriend(String name) {
	    for (Friend fr :friendList) {
	      if (fr.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()))
	        return true; 
	    } 
	    return false;
	  }
	  
	  public static Friend getFriend(String name) {
	    for (Friend f : friendList) {
	      if (f.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()) || f
	        .getAlias().toLowerCase().equalsIgnoreCase(name.toLowerCase()))
	        return f; 
	    } 
	    return null;
	  }
	}