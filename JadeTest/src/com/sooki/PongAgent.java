package com.sooki;


import java.util.Random;




import com.sooki.utility.Helper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class PongAgent extends Agent {
	int numberScored;
	int n=16;
	ACLMessage msg ;
	Random generator;
	protected void setup() {
		addBehaviour(new myBehaviour(this));
		
		msg = new ACLMessage(ACLMessage.INFORM);
		AID r = new AID("test",AID.ISLOCALNAME);
		msg.setContent("your turn");
		msg.addReceiver(r);
			
	
	}

	class myBehaviour extends SimpleBehaviour {
		Agent cur;
		public myBehaviour(Agent a) {
			super(a);
			this.cur = a;
		
		}
		
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage recieved = blockingReceive();
			if(recieved != null)
			{
				System.out.println(cur.getLocalName() + " like sucks turn");  
				Helper.delay(2000);
				send(msg);
                
               
              
			}
			
		}

		
		private boolean finished = false;

		public boolean done() {
			return finished;
		}

		

	} // ----------- End myBehaviour

}// end class myAgent
