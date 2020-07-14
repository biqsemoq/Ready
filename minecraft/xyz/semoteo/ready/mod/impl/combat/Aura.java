package xyz.semoteo.ready.mod.impl.combat;

import io.netty.util.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Rotations;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.Location;
import xyz.semoteo.ready.Ready;
import xyz.semoteo.ready.eventapi.Event;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.eventapi.events.TickEvent;
import xyz.semoteo.ready.friend.FriendManager;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;
import xyz.semoteo.ready.setting.Setting;
import xyz.semoteo.ready.utils.MathUtils;
import xyz.semoteo.ready.utils.RandomUtils;
import xyz.semoteo.ready.utils.RotationUtils;
import xyz.semoteo.ready.utils.TeoTimer;

public class Aura extends Mod {
	private PlayerMotionEvent pre;
  private Setting<Boolean> block = new Setting("Block", Boolean.valueOf(true));
  
  private Setting<Float> range = new Setting("Range", Float.valueOf(4.5F));
  
  private Setting<Boolean> players = new Setting("Players", Boolean.valueOf(true));
  
  private Setting<Boolean> dura = new Setting("Dura", Boolean.valueOf(false));
  private Setting<Boolean> mobs = new Setting("Mobs", Boolean.valueOf(false));
  private boolean teoteo;
  private int tickz;
  private boolean yarrakTeo;
  private Entity targetteo;
  public EntityLivingBase target;
  private int heygoodlookin;
  public Aura() {
    super("Aura", new String[] { "aura", "ka" });
    this.timer = new TeoTimer();
    this.lookTimer = new TeoTimer();
    this.changetime = new TeoTimer();
    this.mode = new Setting("Mode", Auramode.SWITCH);
    this.category = Category.Combat;

  }

  public static List<EntityLivingBase> targetList = new ArrayList<>();
  private TeoTimer timer;
  private TeoTimer changetime;
  private TeoTimer changeTimer;
  private TeoTimer lookTimer;
  private boolean critattack;
  private int semoteonunaq;
  private int index;
  private boolean finishcrit;
  private boolean swapped;
  private int xx;
  private Setting<Auramode> mode;
  @EventListener
  private void UpdateEvent(PlayerMotionEvent event) {
    switch ((Auramode)this.mode.value) {
      case SINGLE:
        if (event.state == Event.State.BEFORE) {
          double CLOSEST_DIST = 3131.0D;
          this.target = null;
          for (Object o : this.mc.theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
              EntityLivingBase ent = (EntityLivingBase)o;
              if (check(ent) && 
                ent.getDistanceToEntity((Entity)this.mc.thePlayer) < CLOSEST_DIST) {
                CLOSEST_DIST = ent.getDistanceToEntity((Entity)ent);
                this.target = ent;
              } 
            } 
          } 
          if (this.target != null) {
            float[] SEMOTEONUN_HASMETLI_YARRAGI = 
              RotationUtils.getRotations((Entity)this.target);
            event.setRotationYaw(SEMOTEONUN_HASMETLI_YARRAGI[0]);
            event.setRotationPitch(SEMOTEONUN_HASMETLI_YARRAGI[1]);
          } 
          break;
        } 
        if (event.state == Event.State.AFTER && 
          this.target != null) {
        	if(mc.thePlayer.onGround) {
        mc.thePlayer.motionY = 0.42;
        	}
        	
          if (this.mc.thePlayer.ticksExisted % 4 == 0) {
            boolean blocking = this.mc.thePlayer.isBlocking();
            Criticals.cancel = true;
            this.mc.thePlayer.swingItem();
            this.mc.thePlayer.sendQueue
              .addToSendQueue((Packet)new C02PacketUseEntity((Entity)this.target, 
                  C02PacketUseEntity.Action.ATTACK));
           
          } 
          if (((Boolean)this.block.value).booleanValue() && 
            this.mc.thePlayer.getHeldItem() != null && 
            this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
            if (this.mc.thePlayer.moveForward == 0.0F && 
              this.mc.thePlayer.moveStrafing == 0.0F) {
              this.mc.playerController.sendUseItem((EntityPlayer)this.mc.thePlayer, 
                  (World)this.mc.theWorld, this.mc.thePlayer.getHeldItem());
              break;
            } 
            this.mc.thePlayer.setItemInUse(
                this.mc.thePlayer.getHeldItem(), 1337);
          } 
        } 
        break;
   
      case CROW:
          if (event.state == Event.State.BEFORE) {
            double CLOSEST_DIST = 3131.0D;
            this.target = null;
            for (Object o : this.mc.theWorld.loadedEntityList) {
              if (o instanceof EntityLivingBase) {
                EntityLivingBase ent = (EntityLivingBase)o;
                ent.ticks--;
                if (check(ent) && 
                  ent.getDistanceToEntity((Entity)this.mc.thePlayer) < CLOSEST_DIST) {
                  CLOSEST_DIST = ent.getDistanceToEntity((Entity)ent);
                  this.target = ent;
                } 
              } 
            } 
         
            if (this.target != null) {
              float[] SEMOTEONUN_HASMETLI_YARRAGI = 
                RotationUtils.getRotations((Entity)this.target);
              event.setRotationYaw(SEMOTEONUN_HASMETLI_YARRAGI[0]);
              event.setRotationPitch(SEMOTEONUN_HASMETLI_YARRAGI[1]);
              if (((Boolean)this.block.value).booleanValue() && 
                      this.mc.thePlayer.getHeldItem() != null && 
                      this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
                        this.mc.playerController.sendUseItem((EntityPlayer)this.mc.thePlayer, 
                            (World)this.mc.theWorld, this.mc.thePlayer.getHeldItem());
                        break;
                      } 
            } 
            break;
          } 
          
          if (event.state == Event.State.AFTER && 
            this.target != null) {
              Criticals.cancel = true;
   if(timer.delay(300)) {
    	swap(9, mc.thePlayer.inventory.currentItem);
    	 attackcrow(target, false);
   
   
    	 swap(9, mc.thePlayer.inventory.currentItem);
    	 attackcrow(target, true);
     timer.reset();
   }
              
          }
  
          break;
          
      case SWITCH:
        if (event.state == Event.State.BEFORE) {
        	  if ((this.block.value) &&   this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
      		       range.value += 1;
           	for (Object o : mc.theWorld.loadedEntityList) {
           		
           		if (o instanceof EntityLivingBase) {
           			final EntityLivingBase ent = (EntityLivingBase)o;
           			if (check(ent)) {
           					
           						mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
           		    	break;
           			}
           			
           		}
           	}
           	range.value -= 1; 
           	}
        	getDeathList();
          if (this.changetime.delay(300.0F) || this.target == null) {
            this.target = indexteo();
            this.changetime.reset();
          } 
          if (this.target != null && targetList.size() > 0) {
            float[] rotations = RotationUtils.getRotations((Entity)this.target);
            event.setRotationYaw(rotations[0]);
            event.setRotationPitch(rotations[1]);
    
    
            if (this.mc.thePlayer.onGround) 
              if (this.yarrakTeo) {
            	  event.ground = false;
                event.setY(event.getY() + 0.07D);
                event.send = true;
              } if(!yarrakTeo) {
                event.send = true;
                event.ground = false;
              }  
            if (this.mc.thePlayer.fallDistance > 0.0F && 
              this.mc.thePlayer.fallDistance < 0.66D)
             
            	event.ground = true; 
            this.yarrakTeo = !this.yarrakTeo;
          } 
          break;
        } 
        if (event.state == Event.State.AFTER && 
          targetList.size() > 0 && this.yarrakTeo && check(this.target)) {
          Criticals.cancel = true;
         
              attack(this.target);
        
         
        	
        
          

          } 
        
        break;
      case HURTTIME: {
    	    if(event.state == Event.State.BEFORE) {
    		  double CLOSEST_DIST = 3131.0D;
              this.target = null;
              for (Object o : this.mc.theWorld.loadedEntityList) {
                if (o instanceof EntityLivingBase) {
                  EntityLivingBase ent = (EntityLivingBase)o;
                
                  if (check(ent) && 
                    ent.getDistanceToEntity((Entity)this.mc.thePlayer) < CLOSEST_DIST) {
                    CLOSEST_DIST = ent.getDistanceToEntity((Entity)ent);
                    this.target = ent;
                  } 
                } 
              } 
            
    	    if(target != null && targetList.size() > 0 ) {
    	    	float[] rotations = RotationUtils.getRotations(target);
    	    	 event.setRotationYaw(rotations[0]);
    	         event.setRotationPitch(rotations[1]);
    	            if ((this.block.value) &&   this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
    	        		mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
    	                    }
    	        	   }
    	    } 
    	   }
    	     if (event.state == Event.State.AFTER && targetList.size() > 0 && check(this.target) && this.target != null) {
              Criticals.cancel = true;
    		  if(mc.thePlayer.isBlocking()) {
    			  this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)); 
    		  }
    		
    		  if (target.hurtTime == 0 && timer.delay(330)) {
    	
    		
    		
    			  if(this.dura.value) {
    				  swap(9, mc.thePlayer.inventory.currentItem);
    				  attackteo(target, false,false);
    				  attackteo(target, true,false);
    				  swap(9, mc.thePlayer.inventory.currentItem);
    		          attackteo(target, false,false);
    				  attackteo(target, true,false);
    				  } else {
    				  attackteo(target, true,false);
    			  }
    	        if(mc.thePlayer.isBlocking()) {
    	        	mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
    	        }
    	        timer.reset();
              }
    			
    	
    		
    		 
    	  break;
    	  } 
     

      case TICK:
    	  teo: {
        if (event.state == Event.State.BEFORE) {	
          getDeathList();
          if (this.changetime.delay(400.0F) || this.target == null) {
            this.target = indexteo();
            this.changetime.reset();
          } 
          if (this.target != null && targetList.size() > 0) {
        	  if ((this.block.value) &&   this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
        		  mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                        }       
            float[] rotations = RotationUtils.getRotations(this.target);
            event.setRotationYaw(rotations[0]);
            event.setRotationPitch(rotations[1]);
        
          }
      
        } 
      
      if (event.state == Event.State.AFTER &&  targetList.size() > 0 && check(this.target) && this.target != null && AutoPot.AuraTimer.delay(50)) {
       
          Criticals.cancel = true;
    boolean blocking = mc.thePlayer.isBlocking(); 
   
    
  if(this.target.ticks <= 41) {
            	
             swap(9, this.mc.thePlayer.inventory.currentItem);
             attackt(target, false);
             attackt(target, true);
             swap(9, mc.thePlayer.inventory.currentItem); 
             if(blocking) {
        		 mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,BlockPos.ORIGIN, EnumFacing.DOWN));
        	 } 
             attackt(target, false);
             attackt(target, true);
            	 
             if(blocking) {
              	mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
              	 }   
          
             AutoPot.doNextTick = true;
                     

      
        }
this.target.ticks = 51;
      }
             
      }     
        break;
      case SEMOTIME:
    	  if (event.state == Event.State.BEFORE) {	
              getDeathList();
              if (this.changetime.delay(400.0F) || this.target == null) {
                this.target = indexteo();
                this.changetime.reset();
              } 
              if (this.target != null && targetList.size() > 0) {
            	  if ((this.block.value) &&   this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
                		mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                            }       
                float[] rotations = RotationUtils.getRotations(this.target);
                event.setRotationYaw(rotations[0]);
                event.setRotationPitch(rotations[1]);
                 int preCps = cps;
                if (boktýr.delay(1000/cps)) {
                	if (cps == 2) {
                		going=1;
                	} else if (cps == 20) {
                		going=-1;
                	}
          
                	
                	cps+=going;
                	
                	hit = true;
                	
                	 event.ground = false;
                	 event.send = true;
                	
                	boktýr.reset();
                } else {
                	hit = false;
                }
                
               if (boktýr.delay((1000/preCps)-60)) {
            	   
                	 event.y+=0.07;
                	 event.ground = false;
                	 
                 }
                
              }
            
              break;
            
            } 
    	  if (event.state == Event.State.AFTER && 
    	          targetList.size() > 0 && check(this.target) && this.target != null && AutoPot.AuraTimer.delay(50)) {
    	          Criticals.cancel = true;
    	    boolean blocking = mc.thePlayer.isBlocking(); 
    	    if(blocking) {
    			 mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,BlockPos.ORIGIN, EnumFacing.DOWN));
    		 }       
    	    
    	    
    	    if (hit) {
    	    	if (cps == 2) {
    	    	swap(9, this.mc.thePlayer.inventory.currentItem);
                attackteo(target, false,false);
                attackteo(target, true,false);
                swap(9, mc.thePlayer.inventory.currentItem); 
                attackteo(target, false,false);
                attackteo(target, true,false);
    	    	   
    	    	   } else {
    	                attack(target);
    	    }
    	    }
    	    if(blocking) {
   			 mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
   		 }   
    	  }
    	  break;
    }
  }
  
  private int going;
  private boolean hit;
  private TeoTimer boktýr = new TeoTimer();
  private int cps = 2;
  
  private void attack(EntityLivingBase entity) {
    boolean blockteo = this.mc.thePlayer.isBlocking();
  
	   if(blockteo) {
	     	  this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	       }
   
    this.mc.thePlayer.swingItem();
    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(
          (Entity)entity, C02PacketUseEntity.Action.ATTACK));
      if(blockteo) {
    	  mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
      }
  }
  private void attackcrow(EntityLivingBase entity, boolean crit) {
	    Criticals.cancel = true;
	    boolean blocking = this.mc.thePlayer.isBlocking();
	  
	    if (crit) {
	    	   double BAKMA_KODLARA_aNANI_SIKERIM = this.mc.thePlayer.posY;
	    	      this.mc.thePlayer.sendQueue
	        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
	            this.mc.thePlayer.posX, 
	            BAKMA_KODLARA_aNANI_SIKERIM + 0.0625D, 
	            this.mc.thePlayer.posZ, false));
	      this.mc.thePlayer.sendQueue
	        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
	            this.mc.thePlayer.posX, 
	            BAKMA_KODLARA_aNANI_SIKERIM, 
	            this.mc.thePlayer.posZ, false));
	      this.mc.thePlayer.sendQueue
	        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
	            this.mc.thePlayer.posX, 
	            BAKMA_KODLARA_aNANI_SIKERIM + 1.1E-5D, 
	            this.mc.thePlayer.posZ, false));
	      this.mc.thePlayer.sendQueue
	        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
	            this.mc.thePlayer.posX, 
	            BAKMA_KODLARA_aNANI_SIKERIM, 
	            this.mc.thePlayer.posZ, false));
	    	 
	    } else {
	      this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(true));
	    } 
	    this.mc.thePlayer.swingItem();
	    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(
	          (Entity)entity, C02PacketUseEntity.Action.ATTACK));
	 
	  }
  private void attackteo(EntityLivingBase entity, boolean crit,boolean unblockreblock) {
    Criticals.cancel = true;
    if(mc.thePlayer.isBlocking()) {
		 mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,BlockPos.ORIGIN, EnumFacing.DOWN));
	 }     
    boolean blocking = this.mc.thePlayer.isBlocking();
       if (crit) {
    	   double BAKMA_KODLARA_aNANI_SIKERIM = this.mc.thePlayer.posY;
    	      this.mc.thePlayer.sendQueue
        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
            this.mc.thePlayer.posX, 
            BAKMA_KODLARA_aNANI_SIKERIM + 0.0625D, 
            this.mc.thePlayer.posZ, false));
      this.mc.thePlayer.sendQueue
        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
            this.mc.thePlayer.posX, 
            BAKMA_KODLARA_aNANI_SIKERIM, 
            this.mc.thePlayer.posZ, false));
      this.mc.thePlayer.sendQueue
        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
            this.mc.thePlayer.posX, 
            BAKMA_KODLARA_aNANI_SIKERIM + 1.1E-5D, 
            this.mc.thePlayer.posZ, false));
      this.mc.thePlayer.sendQueue
        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
            this.mc.thePlayer.posX, 
            BAKMA_KODLARA_aNANI_SIKERIM, 
            this.mc.thePlayer.posZ, false));
    	 
    }  else {
    	   this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    }
      
    this.mc.thePlayer.swingItem();
    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(
          (Entity)entity, C02PacketUseEntity.Action.ATTACK));
   
  }
  private void attackt(EntityLivingBase entity, boolean crit) {
	    Criticals.cancel = true;
	  
	       if (crit) {
	    	   double BAKMA_KODLARA_aNANI_SIKERIM = this.mc.thePlayer.posY;
	    	      this.mc.thePlayer.sendQueue
	        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
	            this.mc.thePlayer.posX, 
	            BAKMA_KODLARA_aNANI_SIKERIM + 0.0625D, 
	            this.mc.thePlayer.posZ, false));
	      this.mc.thePlayer.sendQueue
	        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
	            this.mc.thePlayer.posX, 
	            BAKMA_KODLARA_aNANI_SIKERIM, 
	            this.mc.thePlayer.posZ, false));
	      this.mc.thePlayer.sendQueue
	        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
	            this.mc.thePlayer.posX, 
	            BAKMA_KODLARA_aNANI_SIKERIM + 1.1E-5D, 
	            this.mc.thePlayer.posZ, false));
	      this.mc.thePlayer.sendQueue
	        .addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(
	            this.mc.thePlayer.posX, 
	            BAKMA_KODLARA_aNANI_SIKERIM, 
	            this.mc.thePlayer.posZ, false));
	    	 
	    }  else {
	    	   this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	    }
	      
	    this.mc.thePlayer.swingItem();
	    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(
	          (Entity)entity, C02PacketUseEntity.Action.ATTACK));
	   
	  }
  private void attackwithErÝk(EntityLivingBase entity) {
	    Criticals.cancel = true;
	    this.mc.thePlayer.swingItem();
	    this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(
	          (Entity)entity, C02PacketUseEntity.Action.ATTACK));
	 
	  }
  
  protected void swap(int slot, int hotbarNum) {
    this.mc.playerController.windowClick(
        this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, 
        (EntityPlayer)this.mc.thePlayer);

  }
 
  boolean check(EntityLivingBase entity) {
    if (entity == this.mc.thePlayer || entity == null)
      return false; 
    if (!(entity instanceof EntityLivingBase))
      return false; 
    
    if (entity instanceof EntityPlayer) {
    	final EntityPlayer player = (EntityPlayer)entity;
    	if (FriendManager.isFriend(player.getName())) {
    		return false;
    	}
    }
    
    if (entity instanceof EntityPlayer && !((Boolean)this.players.value).booleanValue())
      return false; 
    if(entity instanceof EntityMob || entity instanceof EntityAnimal && !this.mobs.value.booleanValue()) {
    	return false;
    }
    if (entity.getDistanceToEntity((Entity)this.mc.thePlayer) > ((Float)this.range.value).floatValue())
      return false; 
    if (!this.mc.thePlayer.canEntityBeSeen((Entity)entity) && 
      entity.getDistanceToEntity((Entity)this.mc.thePlayer) > 2.0F)
      return false;
  
    return (entity.isEntityAlive()&& entity instanceof EntityPlayer);
  }
  
  private void index() {}
  
  public void getDeathList() {
    targetList.clear();
    for (Object o : this.mc.theWorld.loadedEntityList) {
      if (o instanceof EntityLivingBase) {
        EntityLivingBase entityLivingBase = (EntityLivingBase)o;
    	

        entityLivingBase.ticks--;
   
    	     if (check(entityLivingBase)) {
          targetList.add(entityLivingBase); 
      } 
    } 
    }
    Collections.sort(targetList, new Comparator<EntityLivingBase>() {
          public int compare(EntityLivingBase t1, EntityLivingBase t2) {
            float yaw1 = RotationUtils.getRotations((Entity)t1)[0];
            float yaw2 = RotationUtils.getRotations((Entity)t2)[0];
            return Double.compare(yaw1, yaw2);
          }
        });
  }
  
  private EntityLivingBase indexteo() {
    if (targetList.isEmpty()) {
      this.index = 0;
      return null;
    } 
    this.index++;
    if (this.index >= targetList.size())
      this.index = 0; 
    return targetList.get(this.index);
  }
  
  private enum Auramode {
    SWITCH, HURTTIME, SEMOTIME, TICK, ANGRYTEO, CROW, SINGLE, MEANTEO;
  }
}
