package tictactoe;

import java.util.Random;

public class engine {
	int height;
	int width; //identical to height
	int[][] board; //board
	int[][] brain_off; //offense table(for debug mode)
	int[][] brain_def; //defense table(for debug mode)
	//engine vars(for debug mode)
	int brain_off_max;
	int brain_def_max;
	int flag;
	int temp_i;
	int temp_j;
	//engine config
	int brain_off_offset; 
	int brain_def_offset;
	int brain_def_offset_off;
	int brain_def_offset_ctrl;
	int which_player;
	int brain_middle_switch;
	
	
	
	
	public int get_brain_off_max(){
		return brain_off_max;
		
		
	}
	
	public int get_brain_def_max(){
		return brain_def_max;
		
		
	}
	
	
	//constructor in java
	public engine(
			int height,
			int width, //identical to height
			int[][] board, //board
			//engine config
			int brain_off_offset, 
			int brain_def_offset, 
			int brain_def_offset_off, 
			int brain_def_offset_ctrl, 
			int which_player, //which player side is engine running
			int brain_middle_switch
			){
		this.height = height;
		this.width = width;
		this.board = board;
		this.brain_off_offset = brain_off_offset;
		this.brain_def_offset = brain_def_offset;
		this.brain_def_offset_off = brain_def_offset_off;
		this.brain_def_offset_ctrl = brain_def_offset_ctrl;
		this.which_player = which_player;
		this.brain_middle_switch = brain_middle_switch;
		
		
	}
	
	
	
	/*
	checkcondition properties:
	-return 1 => player1 wins
	-return 2 => player2 wins
	-return 3 => its a tie!
	-return 0 => nothing happens
	
	backported from my old homework.c
	*/
	public int checkcondition(int[][] arr, int row, int col) {
		int i, j, bak, bad, p1_exists, p2_exists, notatie;
		//row
		for (i = 0; i < row; i++) {
			bak = arr[i][0];
			bad = 0;
			for (j = 1; j < col; j++) {
				if (arr[i][j] != bak) {
					bad = 1;
					break; //not it

				}
			}
			if ((bad==0) && (bak != 0)) return bak;
		}
		//col
		for (i = 0; i < row; i++) {
			bak = arr[0][i];
			bad = 0;
			for (j = 1; j < col; j++) {
				if (arr[j][i] != bak) {
					bad = 1;
					break; //not it

				}
			}
			if ((bad==0) && (bak != 0)) return bak;
		}
		//diagonal
		bak = arr[0][0];
		bad = 0;
		for (i = 1; i < row; i++) {
			if (arr[i][i] != bak) {
				bad = 1;
				break;
			}
		}
		if ((bad==0) && (bak != 0)) return bak;

		bak = arr[0][row - 1];
		bad = 0;
		for (i = 1; i < row; i++) {
			if (arr[i][row - i - 1] != bak) {
				bad = 1;
				break;
			}
		}
		if ((bad==0) && (bak != 0)) return bak;

		//final sequence
		notatie = 0;
		for (i = 0; i<row; i++) {
			p1_exists = 0;
			p2_exists = 0;
			for (j = 0; j<col; j++) {
				if (arr[i][j] == 1) p1_exists++;
				else if (arr[i][j] == 2) p2_exists++;



			}
			if ((p1_exists!=0&&p2_exists==0) || (p1_exists==0&&p2_exists!=0) || (p1_exists==0&&p2_exists==0)) {
				notatie++; //not a tie!
				break;
			}
		}
		for (i = 0; i<row; i++) {
			p1_exists = 0;
			p2_exists = 0;
			for (j = 0; j<col; j++) {
				if (arr[j][i] == 1) p1_exists++;
				else if (arr[j][i] == 2) p2_exists++;



			}
			if ((p1_exists!=0&&p2_exists==0) || (p1_exists==0&&p2_exists!=0) || (p1_exists==0&&p2_exists==0)) {
				notatie++; //not a tie!
				break;
			}
		}
		p1_exists = 0;
		p2_exists = 0;
		for (i = 0; i<row; i++) {
			if (arr[i][i] == 1) p1_exists++;
			else if (arr[i][i] == 2) p2_exists++;


		}
		if ((p1_exists!=0&&p2_exists==0) || (p1_exists==0&&p2_exists!=0) || (p1_exists==0&&p2_exists==0)) notatie++; //not a tie!

		p1_exists = 0;
		p2_exists = 0;
		for (i = 0; i<row; i++) {
			if (arr[i][row - i - 1] == 1) p1_exists++;
			else if (arr[i][row - i - 1] == 2) p2_exists++;


		}
		if ((p1_exists!=0&&p2_exists==0) || (p1_exists==0&&p2_exists!=0) || (p1_exists==0&&p2_exists==0)) notatie++; //not a tie!


		if (notatie==0) return 3; //its a tie!

		return 0;
	}
	
	
	
	
	public void mainengine(){
		
		
		Random generator = new Random();
		//which player
		int other_player = 20154078;
		switch(which_player){
		case 1:
			other_player = 2; break;
		case 2:
			other_player = 1; break;
		default:
			//error
			return;
		
		}
		
		brain_off = new int[height][width];; //offense table
		brain_def = new int[height][width];; //defense table
		
		
		//input to brain
		//col
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				if (board[i][j] == which_player) {
					for(int k=0; k<width;k++) brain_off[i][k]+=brain_off_offset;
				} else if (board[i][j] == other_player) {
					for(int k=0; k<width;k++) {
						brain_def[i][k]+=brain_def_offset;
						brain_off[i][k]-=brain_def_offset_off;
					}
				}
			}
		}
		//row
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				if (board[j][i] == which_player) {
					for(int k=0; k<width;k++) brain_off[k][i]+=brain_off_offset;
				} else if (board[j][i] == other_player) {
					for(int k=0; k<width;k++) {
						brain_def[k][i]+=brain_def_offset;
						brain_off[k][i]-=brain_def_offset_off;
					}
				}
			}
		}
		//diagonal
		for(int i=0; i<height; i++){
			if (board[i][i] == which_player) {
				for(int k=0; k<width;k++) brain_off[k][k]+=brain_off_offset;
			} else if (board[i][i] == other_player) {
				for(int k=0; k<width;k++) {
					brain_def[k][k]+=brain_def_offset;
					brain_off[k][k]-=brain_def_offset_off;
				}
			}
		}
		//reverse-diagonal
		for(int i=0; i<height; i++){
			if (board[i][height - i - 1] == which_player) {
				for(int k=0; k<width;k++) brain_off[k][height - k - 1]+=brain_off_offset;
			} else if (board[i][height - i - 1] == other_player) {
				for(int k=0; k<width;k++) {
					brain_def[k][height - k - 1]+=brain_def_offset;
					brain_off[k][height - k - 1]-=brain_def_offset_off;
				}
			}
		}
		

		//controlled defense - height as default
		int total = 0;
		//col
		for(int i=0; i<height; i++){
			total = 0;
			for(int j=0; j<width;j++){
				if(board[i][j] == other_player){
					total++;
				}
			}
			if(total+1 == height){
				for(int k=0; k<width;k++) brain_def[i][k]+=brain_def_offset_ctrl; break;
			}
		}
		//row
		for(int i=0; i<height; i++){
			total = 0;
			for(int j=0; j<width;j++){
				if(board[j][i] == other_player){
					total++;
				}
			}
			if(total+1 == height){
				for(int k=0; k<width;k++) brain_def[k][i]+=brain_def_offset_ctrl; break;
			}
		}
		//diagonal
		total = 0;
		for(int i=0; i<height; i++){
			if(board[i][i] == other_player){
				total++;
			}
		}
		if(total+1 == height){
			for(int k=0; k<width;k++) brain_def[k][k]+=brain_def_offset_ctrl;
		}
		//reverse-diagonal
		total = 0;
		for(int i=0; i<height; i++){
			if(board[i][height - i - 1] == other_player){
				total++;
			}
		}
		if(total+1 == height){
			for(int k=0; k<width;k++) brain_def[k][height - k - 1]+=brain_def_offset_ctrl;
		}

		//remove already marked
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				if(board[i][j] != 0){
					brain_off[i][j] = height * -1;
					brain_def[i][j] = height * -1;
				}
			}
		}
		
		//engine vars
		brain_off_max = 0;
		brain_def_max = 0;
		temp_i = 0; temp_j = 0;
		flag = 0;
		
		
		//find min-max(might be the place to compare stuff like board[i][j] instead of brain[i][j])
		brain_off_max = 0;
		brain_def_max = 0;
		for(int i=0; i<height; i++){
			for(int j=0; j<width;j++){
				if(brain_off[i][j]>brain_off_max) brain_off_max = brain_off[i][j];
				if(brain_def[i][j]>brain_def_max) brain_def_max = brain_def[i][j];
			}
		}
		//decision system
		//check whether it should attack or block
		if(brain_def_max > brain_off_max){
			//then block
			
			//random
			temp_i = 0; temp_j = 0;
			flag = 0;
			for(int i=0; i<height; i++){
				for(int j=0; j<width;j++){
					if(brain_def[i][j] == brain_def_max){
						if(generator.nextInt(height*width)==0){
							board[i][j] = which_player;
							flag = 1;
							break;
							
						}
						temp_i = i;
						temp_j = j;
					}
				}
				if(flag == 1) break;
			}
			if(flag != 1){
				board[temp_i][temp_j] = which_player;
			}
			
			
		}else if(brain_def_max == brain_off_max && brain_middle_switch!=0){ //if enabled, defense is stronger
			//then block
			
			//random
			temp_i = 0; temp_j = 0;
			flag = 0;
			for(int i=0; i<height; i++){
				for(int j=0; j<width;j++){
					if(brain_def[i][j] == brain_def_max){
						if(generator.nextInt(height*width)==0){
							board[i][j] = which_player;
							flag = 1;
							break;
							
						}
						temp_i = i;
						temp_j = j;
					}
				}
				if(flag == 1) break;
			}
			if(flag != 1){
				board[temp_i][temp_j] = which_player;
			}
			
			
			
			
		}
		else{
			//then attack
			
			//random
			temp_i = 0; temp_j = 0;
			flag = 0;
			for(int i=0; i<height; i++){
				for(int j=0; j<width;j++){
					if(brain_off[i][j] == brain_off_max){
						if(generator.nextInt(height*width)==0){
							board[i][j] = which_player;
							flag = 1;
							break;
							
						}
						temp_i = i;
						temp_j = j;
					}
				}
				if(flag == 1) break;
			}
			if(flag != 1){
				board[temp_i][temp_j] = which_player;
			}
			
			
		}



		
	} //end of engine
}
