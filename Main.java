/* CSC 3351, Fall 2022, Assignment Semaphores Implementation */

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main{
  public static void main(String[] args) throws InterruptedException{
    int carnum=0, vehicule_id , dir, time_to_cross, c=0;
    final Bridge Bridge = new Bridge();
    Vehicule[] vehicules = new Vehicule[20];
 
  // This is our vehicule generation code (directions are also assigned randomly)

    for (int i = 0; i <20 ; i++) {
        vehicule_id = i + 1;
        dir = (int) (Math.random() * 2);
        time_to_cross = 5;
        vehicules[i] = new Vehicule(vehicule_id, dir, time_to_cross, Bridge);
      }
   System.out.printf("\n******************************************************\n");
System.out.printf("Bridge crossing simulation\n");
System.out.printf("Please select the scenario you which to try:\n");
System.out.printf("(1) 5 : DELAY(10) : 5 : DELAY(10) : 5 : DELAY(10) : 5\n");
System.out.printf("(2) 10 : DELAY(10) : 10\n");
System.out.printf("(3) 20\n");    System.out.printf("\n******************************************************\n");
System.out.printf("Input a number corresponding to the scenario: ");
       
    Scanner sc = new Scanner(System.in);
    c = sc.nextInt();
// This is our switch case for the 3 scenarios
      switch (c){
        case 1:
          // 5 cars arrive at once with a delay of 10 between each batch
         for (Vehicule i : vehicules) {
          if (i != null) {
            if (carnum % 5 == 0 && carnum != 0 ) {
              TimeUnit.SECONDS.sleep(10);
            }
            carnum++;
            i.start();
          }
      } break;
        case 2:
          // 10 cars arrive at once with a delay of 10 between each batch
          for (Vehicule i : vehicules) {
              if (i != null) {
                if (carnum != 0 && carnum % 10 == 0 ) {
                  TimeUnit.SECONDS.sleep(10);
                }
                carnum++;
                i.start();
              }
          }break;
        case 3: 
          // 20 cars arrive at once
        for (Vehicule i : vehicules) i.start();
      break;
        default: System.out.printf("Wrong Input! Please re-run the program");
        }
      for (Vehicule i : vehicules) {
      if ( i != null && i.isAlive()) {
        i.join();
      }
    }

  }

}

/*
Asmae Mouradi
Badr Azzouzi
Fatima El Kabir
Mohamed El Mehdi Sabor
*/