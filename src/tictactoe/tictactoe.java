package tictactoe;

import java.io.IOException;
import java.util.*;

public class tictactoe {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		int height = 5;
		int width = height;
		
		int player_option = -1;
		
		int board[][] = new int[height][width]; //board

		
		
		Random generator = new Random();
		
		//engine config
		int brain_off_offset = 2;
		int brain_def_offset = 1;
		int brain_def_offset_off = 2;
		int brain_def_offset_ctrl = 1;
		int brain_middle_switch = 1;
		
		
		//test here! - ++(cpu), +=2(player 2)
		/*board[1][1]+=2;
		board[0][0]++;
		board[1][0]+=2;
		/*board[1][2]++;
		board[2][0]+=2;
		
		
		//
		board[0][0]++;
		board[2][2]+=2;
		board[0][1]++;
		board[2][1]+=2;
		*/
		/*
		board[0][1]++;
		board[1][1]+=2;
		board[2][1]++;
		board[0][0]+=2;
		board[2][2]++;
		board[1][0]+=2;
		*/
		/*
		board[1][1]++;
		board[1][0]+=2;
		board[2][2]++;
		board[0][2]+=2;
		*/
		
		//board[0][1]++;
		//board[1][1]+=2;
		//board[2][1]++;
		//board[0][0]+=2;
		//board[2][2]++;
		//board[1][0]+=2;
		
		engine computer = new engine(height, width, board, brain_off_offset, brain_def_offset, brain_def_offset_off, brain_def_offset_ctrl, 1, brain_middle_switch);
		engine computer2 = new engine(height, width, board, brain_off_offset, brain_def_offset, brain_def_offset_off, brain_def_offset_ctrl, 2, brain_middle_switch);

		
		Scanner test = new Scanner(System.in);
		int done = 0;
		while(done == 0){
			for(int i = 0; i<10; i++) System.out.println(); //cls
			System.out.printf("select option:\n"
					+ "1.player vs cpu\n"
					+ "2.player vs player\n"
					+ "3.cpu vs cpu\n");
			switch(test.nextInt()){
			case 1:
				if(generator.nextInt(2)==0){
				player_option = 1; //player vs cpu
				System.out.println("player goes first");
				Thread.sleep(1000);
				}else{player_option = 0;
				System.out.println("cpu goes first");
				Thread.sleep(1000);
				} //cpu vs player
				done++;
				break;
			case 2:
				player_option = 2;
				done++;
				break;
			case 3:
				player_option = 3;
				done++;
				break;
			default:
				System.out.println("try again");
				Thread.sleep(1000);
			}
		}
		
		int inputy = -1, inputx = -1;
		int turn = 0;
		while(true){
			for(int i = 0; i<100; i++) System.out.println(); //cls
			
			//print board
			//player 1 is O the computer
			 //player 2 is X the user
			 
			for(int i=0; i<height; i++){
				for(int j=0; j<width;j++){
					switch(board[i][j]){
					case 1: System.out.printf("O "); break;
					case 2: System.out.printf("X "); break;
					default: System.out.printf(". ");
					}
				}
				if(i==0){
					switch(player_option){
					case 0:
						System.out.printf("\tcpu is O"); break;
					case 1:
						System.out.printf("\tplayer is O"); break;
					case 2:
						System.out.printf("\tplayer 1 is O"); break;
					case 3:
						System.out.printf("\tcpu 1 is O"); break;
					
					}
				}
				if(i==1){
					switch(player_option){
					case 0:
						System.out.printf("\tplayer is X"); break;
					case 1:
						System.out.printf("\tcpu is X"); break;
					case 2:
						System.out.printf("\tplayer 2 is X"); break;
					case 3:
						System.out.printf("\tcpu 2 is X"); break;
					
					}
				}
				System.out.println();
			}
			
			switch(computer.checkcondition(board, height, width)){
			case 1: //p1 wins
				switch(player_option){
				case 0:  //cpu vs p
					System.out.println("cpu wins!");
					return;
				case 1: //p vs cpu
					System.out.println("player wins!");
					return;
				case 2: //p1 vs p2
					System.out.println("player 1 wins!");
					return;
				case 3: //cpu1 vs cpu2
					System.out.println("cpu 1 wins!");
					return;
				
				
				}
				break;
			case 2: //p2 wins
				switch(player_option){
				case 0:  //cpu vs p
					System.out.println("player wins!");
					return;
				case 1: //p vs cpu
					System.out.println("cpu wins!");
					return;
				case 2: //p1 vs p2
					System.out.println("player 2 wins!");
					return;
				case 3: //cpu1 vs cpu2
					System.out.println("cpu 2 wins!");
					return;
				
				
				}
				break;
			case 3: //tie
				System.out.println("its a tie!");
				return;
			
			
			}
			
			switch(player_option){
			case 0:
				if(turn%2==0){
					System.out.println("cpu is thinking...");
					computer.mainengine();
					Thread.sleep(1000);
				}else{
					done = 0;
					while(done==0){
						System.out.println("player input row col:");
						inputy = test.nextInt();
						inputx = test.nextInt();
						if((inputx>height||inputx<1)||(inputy>height||inputy<1)){
							System.out.println("what?");
							
						}else if(board[inputy-1][inputx-1]!=0){
							System.out.println("you can't go there!");
							
						}
						else{
							done++;
							
						}
							
					}
					board[inputy-1][inputx-1]=2;
				}
				break;
			case 1:
				if(turn%2==1){
					System.out.println("cpu is thinking...");
					computer2.mainengine();
					Thread.sleep(1000);
				}else{
					done = 0;
					while(done==0){
						System.out.println("player input row col:");
						inputy = test.nextInt();
						inputx = test.nextInt();
						if((inputx>height||inputx<1)||(inputy>height||inputy<1)){
							System.out.println("what?");
							
						}else if(board[inputy-1][inputx-1]!=0){
							System.out.println("you can't go there!");
							
						}
						else{
							done++;
							
						}
							
					}
					board[inputy-1][inputx-1]=1;
				}
				break;
			case 2:
				if(turn%2==0){
					done = 0;
					while(done==0){
						System.out.println("player 1 input row col:");
						inputy = test.nextInt();
						inputx = test.nextInt();
						if((inputx>height||inputx<1)||(inputy>height||inputy<1)){
							System.out.println("what?");
							
						}else if(board[inputy-1][inputx-1]!=0){
							System.out.println("you can't go there!");
							
						}
						else{
							done++;
							
						}
							
					}
					board[inputy-1][inputx-1]=1;
				}else{
					done = 0;
					while(done==0){
						System.out.println("player 2 input row col:");
						inputy = test.nextInt();
						inputx = test.nextInt();
						if((inputx>height||inputx<1)||(inputy>height||inputy<1)){
							System.out.println("what?");
							
						}else if(board[inputy-1][inputx-1]!=0){
							System.out.println("you can't go there!");
							
						}
						else{
							done++;
							
						}
							
					}
					board[inputy-1][inputx-1]=2;
				}
				break;
			case 3:
				if(turn%2==1){
					System.out.println("cpu 1 is thinking...");
					computer.mainengine();
					Thread.sleep(1000);
				}else{
					System.out.println("cpu 2 is thinking...");
					computer2.mainengine();
					Thread.sleep(1000);
				}
				break;
			}
			
			
			
			turn++;
			
		}
		
		
		
		/*
		while(true){
			for(int i = 0; i<10; i++) System.out.println(); //cls
		computer.mainengine();
		computer2.mainengine();
		
		
		
		//print board
		//player 1 is O the computer
		 //player 2 is X the user
		 
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				switch(board[i][j]){
				case 1: System.out.printf("O "); break;
				case 2: System.out.printf("X "); break;
				default: System.out.printf(". ");
				}
			}
			System.out.println();
		}

		System.out.printf("=========================================\n");
		//debug brain print board
		
		System.out.printf("off\n");
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				System.out.printf("%d ", computer.brain_off[i][j]);
			}
			System.out.println();
		}
		System.out.printf("off2\n");
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				System.out.printf("%d ", computer2.brain_off[i][j]);
			}
			System.out.println();
		}

		System.out.printf("=========================================\n");
		
		System.out.printf("def\n");
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				System.out.printf("%d ", computer.brain_def[i][j]);
			}
			System.out.println();
		}
		System.out.printf("def2\n");
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				System.out.printf("%d ", computer2.brain_def[i][j]);
			}
			System.out.println();
		}

		System.out.printf("=========================================\n");
		System.out.printf("off def\n");
		System.out.printf("%d %d\n", computer.get_brain_off_max(), computer.get_brain_def_max());
		
		System.out.printf("off def2\n");
		System.out.printf("%d %d\n", computer2.get_brain_off_max(), computer2.get_brain_def_max());
		
		
		System.in.read();
		test.nextLine();
		}*/
		
		
		
		

	} //end of main

}
