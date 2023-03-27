import java.util.concurrent.*;

public class Bridge implements oneVehicule {
// This is our critical section semaphore
private static Semaphore midBridge = new Semaphore(3,true);
// This array was created to keep track of the number of cars in the semaphore (since we do not have this information from the semaphore by itself)
private static int[] track_midBridge= new int []{0,0};
// Semaphores for the right and left sides of the bridge
private static Semaphore rightBridge = new Semaphore(3, true);
private static Semaphore leftBridge= new Semaphore(3, true);
// This array was created to keep track of the number of cars in each direction semaphore
private static int[] track_dirBridge = new int []{0,0};
//This array was created to keep track of the total number of vehicules from both sides
private static int[] track_Vehicule = new int[]{0,0};

public int currDir =-1; // Setting our initial direction to a flag value
public int depIndex = 0 ;  // This is the departure index for cars that have crossed the bridge


@Override
public void arriveBridge(int vehicule_id,int dir){

  try{

    if(dir == 1)
      System.out.printf("Car %d has arrived from the left\n", vehicule_id );
    else 
      System.out.printf("Car %d has arrived from the right\n", vehicule_id );

    track_midBridge[dir] =  track_midBridge[dir] + 1;

    midBridge.acquire();
      


      // Setting bridge direction for first car crossing
      if ( currDir ==-1 ){

          if (dir == 1) { leftBridge.acquire(1); currDir = 1;}
          else {rightBridge.acquire(1); currDir = 0;}
      }
      
      else if (track_Vehicule[1-dir]>0 || currDir == 1-dir){

        midBridge.release(1);
        track_midBridge[dir] =  track_midBridge[dir] - 1;

        if(dir == 1){
          track_dirBridge[1] = track_dirBridge[1] +1;
          while(currDir == 0){
            rightBridge.acquire(1);
            }
          }
        else{
          track_dirBridge[0] = track_dirBridge[0]+1;
          while(currDir == 1){
          leftBridge.acquire(1);
          }
         }
      track_dirBridge[dir] = track_dirBridge[dir] - 1;
        }
      track_Vehicule[dir] = track_Vehicule[dir] + 1;
  }catch (Exception msg){
    msg.printStackTrace();
   }
 }

@Override
public void crossBridge(int time_to_cross, int dir, int vehicule_id){

  try{
      if (dir == 1)
      System.out.printf("Car %d is crossing from the left...\n", vehicule_id);
      else
      System.out.printf("Car %d is crossing from the right...\n", vehicule_id);
           
      TimeUnit.SECONDS.sleep(time_to_cross);

  }catch(Exception msg){
    msg.printStackTrace();
  }
}

@Override
public void exitBridge(int vehicule_id,int dir){
  try {
      if (dir == 1)
        System.out.printf( "Car %d coming from the left has left with departure index %d.\n ",vehicule_id,++depIndex);
      
      else 
        System.out.printf("Car %d coming from the right has left with departure index %d.\n ",vehicule_id,++depIndex);

      if (--track_Vehicule[dir] == 0) {
        // This is the direction swithing code to ensure fairness after a vehicule's departure
        if (track_dirBridge[1 - currDir] > 0 && track_dirBridge[currDir] == 0) {
          currDir = 1 - dir;
            if (dir == 0) {
              if (track_dirBridge[1] > 3) rightBridge.release(3);
              else rightBridge.release(track_dirBridge[1]);
            } 
            else {
              if (track_dirBridge[0] > 3)  leftBridge.release(3);
              else leftBridge.release(track_dirBridge[0]);
            }
        }
        else {
          if (track_dirBridge[1 - currDir] > 0 && track_dirBridge[currDir] == 0) {
            currDir = 1 - dir;
            if (track_midBridge[1 - dir] > 3)  midBridge.release(3);
            else midBridge.release(track_midBridge[1 - dir]);

          } 
          else if (track_dirBridge[dir] > 0) {
            if (dir == 0) {
              if (track_dirBridge[0] > 3) leftBridge.release(3);
              else leftBridge.release(track_dirBridge[0]);

            }
             else {
              if (track_dirBridge[1] > 3) rightBridge.release(3);
              else  rightBridge.release(track_dirBridge[1]);
            }
          } else if (track_midBridge[dir] > 0) {
            if (track_midBridge[dir] > 3)   midBridge.release(3);
            else midBridge.release(track_midBridge[dir]);
          }
        }
      }

      if (depIndex == 20 ) 
        System.out.printf("\nThe cars have crossed\n");
    } catch (Exception msg) {
      msg.printStackTrace();
    }
}
}