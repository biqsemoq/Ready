package xyz.semoteo.ready.cmds;

import xyz.semoteo.ready.Ready;
import xyz.semoteo.ready.friend.FriendManager;

public class FriendADD extends Commands {
	  public FriendADD() {
	    super(new String[] { "friend" }, "f");
	  }
	  
	  public String RunCmd(final String[] text) {
	    if (text[1].toLowerCase().equals("remove")) {
	      FriendManager.removeFriend(text[2]);
	    Ready.LOG("friend removed");
	    } else if (text[1].toLowerCase().equals("add")) {
	      String alias = text[2];
	      if (text.length >= 4)
	        alias = text[3]; 
	      FriendManager.addFriend(text[2], alias);
	      Ready.LOG("friend added");
	    } else {
	     
	    } 
	    return "";
	  }
	}
