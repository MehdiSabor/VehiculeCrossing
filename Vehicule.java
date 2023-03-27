import java.util.concurrent.*;

class Vehicule extends Thread {

int vehicule_id, dir, time_to_cross;
oneVehicule instance;
// Constructor

public Vehicule(int vehicule_id, int dir,int time_to_cross,oneVehicule instance ){
  this.vehicule_id =vehicule_id;
  this.dir=dir;
  this.time_to_cross=time_to_cross;
  this.instance = instance;
}

@Override
public void run (){
  try{
    instance.arriveBridge(vehicule_id, dir);
    TimeUnit.SECONDS.sleep(5); // This sets a delay between the arrival of the cars and their crossing
    instance.crossBridge(time_to_cross, dir, vehicule_id);
    instance.exitBridge(vehicule_id, dir);
    
  }catch(Exception msg){
    msg.printStackTrace();
  }
}
  
// getters and setters
public int getVehicule_id() {
	return vehicule_id;
}

public void setVehicule_id(int vehicule_id) {
	this.vehicule_id = vehicule_id;
}

public int getDir() {
	return dir;
}

public void setDir(int dir) {
	this.dir = dir;
}

public int getTime_to_cross() {
	return time_to_cross;
}

public void setTime_to_cross(int time_to_cross) {
	this.time_to_cross = time_to_cross;
}

public oneVehicule getInstance() {
	return instance;
}

public void setInstance(oneVehicule instance) {
	this.instance = instance;
}
}