package com.sooki;

import java.util.ArrayList;
import java.util.Random;

import com.sooki.environment.BoardState;
import com.sooki.environment.Environment;
import com.sooki.memory.LimitedMemory;
import com.sooki.utility.Memory;

import jade.core.Agent;
import jade.core.behaviours.*;

public class HelloAgent extends Agent {
	int numberScored;
	int n=16;
	Memory memory;
	Random generator;
	LimitedMemory primary;
	LimitedMemory secondary;
	protected void setup() {
		addBehaviour(new myBehaviour(this));
		Environment.initialise();
	//	memory = new Memory();
		primary = new LimitedMemory();
		secondary = new LimitedMemory();
		generator = new Random();
		generator.setSeed(0);
	}

	class myBehaviour extends SimpleBehaviour {
		Agent cur;
		public myBehaviour(Agent a) {
			super(a);
			this.cur = a;
		
		}

		public BoardState percept() {

			BoardState b = Environment.getBoardState("test");
			return b;

		}

		public BoardState passToken() {
			return null;

		}

		public boolean isTokenObtained() {
			return true;
		}
		
		public boolean makeMove(int a, int b) {
			int re[] = Environment.seeCard(a, b);
			if (re[0] == re[1]) {
				primary.remove(a, re[0]);
				memory.erase(a, b);
				return true;
			} else {
				System.out.println("");
				System.out.println("The value was" + re[0] + "," + re[1]);
				memory.put(a, b, re[0], re[1]);
				return false;
			}
		}
		
		public boolean passTheToken() {
			return true;
		}
		
		
		public ArrayList<Integer> possibleMoves(BoardState b)
		{
			ArrayList<Integer> possible = new ArrayList<Integer>();
			for(int i=0;i<n;i++)
			{
				if(b.board[i] == -1)
				{
					possible.add(i);
				}
				else{
				}
			}
			return possible;
		}
		
		
		public int [] successMovesAvailable(ArrayList<Integer>  possible)
		{
			int memArray [] = memory.getMemory();
			int moves[] = new int [2];
			moves[0] = -1;
			moves[1] = -1;
			for(int i=0;i<n;i++)
			{
				if(memArray[i]!=-1)
				{
					int x = memArray[i];
					for (int j=i+1;j<n;j++)
					{
						if(memArray[j] == x)
						{
							if(possible.contains(i) && possible.contains(j))
							{
								moves[0] = i;
								moves[1] = j;
								return moves;
							}
						
							memory.erase(moves[0], moves[1]);
						}
					}
				}
			}
			return moves;
			
		}
		
		public void printPossible(ArrayList<Integer>  possible ) {
			for(Integer q : possible)
			{
				System.out.print(q + " ");
			}
		}
		public int [] generateRandom(ArrayList<Integer>  possible)
		{
			
			    int moves[] = new int[2];
			    moves[0] = -1;
				moves[1] = -1;
			    if(possible.size() > 1)
			    {
				int ran = generator.nextInt(possible.size());
				moves[0] = possible.get(ran);
				possible.remove(ran);
				ran = generator.nextInt(possible.size());
				moves[1] = possible.get(ran);
				possible.remove(ran);
				return moves;
			    }
			    return moves;
						
			
		}

		public void action() {

		
			if (isTokenObtained()) {
				BoardState  b = percept();
				ArrayList<Integer>  possible =  possibleMoves(b);
				b.printBoardState();
				printPossible(possible);
				int moves[] = successMovesAvailable(possible);
				if(moves[0] == -1 || moves[1] == -1)
				{
					moves = generateRandom(possible);
				//	System.out.println("No success move was found");
				}	
				
				if(moves[0] == -1 || moves[1] == -1)
				{
					System.out.println("game ended no possible moves");
				}	
				else {
					System.out.println("The move made " + moves[0] + " "+ moves[1]);
					makeMove(moves[0] ,moves[1]);
					System.out.println(memory);
				}
				
				passTheToken();
			}
			block(2000);
			
		}

		private boolean finished = false;

		public boolean done() {
			return finished;
		}

	} // ----------- End myBehaviour

}// end class myAgent
