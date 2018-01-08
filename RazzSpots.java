/******************************************************
 *  Compilation:  javac RazzSpots.java
 *  Execution:    java RazzSpots
 *
 *  Prints all the numbered spots for a Razz, and then fills them to user specification
 * 
 *  % java RazzSpots
 *  How may spots in the Razz? 
 *  % 5
 *  
 *  1. 
 *  2. 
 *  3. 
 *  4. 
 *  5. 
 *	Any Fusco spots?
 *	%2
 *	
 *  1. 
 *  2. 
 *  3. 
 *  4. 
 *  5. 
 *	6. Fusco
 *	7. Fusco
 *
 ******************************************************/

import java.util.*;

public class RazzSpots {

	ArrayList<String> list;
	// RazzSpots are comprised of a numbered list of spots, claimed by participants

	ArrayList<Integer> openSpots;
	// the list of open spots that have not been claimed

	int fuscoSpots;
	// the number of spots given to fusco

	public RazzSpots()
	{
		this.list = new ArrayList<String>();
		this.openSpots = new ArrayList<Integer>();
		this.fuscoSpots = 0;
	}

	public static void main(String[] args)
	{
		RazzSpots razz = new RazzSpots();
		razz.initiate();
		razz.print();
		System.out.println("Filling any spots?");
		while(razz.openSpots.size() > 0)
		{
			razz.fill();
		}
	}

	// print the current razz list
	public void print()
	{
		for (String spot : list)
		{
			System.out.println(spot);
		}
	}

	// initiate the razz list with a specified number of spots, adding in fusco spots
	public void initiate()
	{
		System.out.println("How may spots in the Razz?");
		Scanner scanner = new Scanner(System.in);
		int spots = scanner.nextInt();

		for (int i=0;i<spots;i++)
		{
			list.add((i+1) + ".");
			openSpots.add(i+1);
		}
		System.out.println("How many fusco spots?");
	 	fuscoSpots = scanner.nextInt();
		for (int i=0;i<fuscoSpots;i++)
		{
			list.add((spots + i + 1) + ". fusco");
		}
	}

	// fill the spots in the razz
	public void fill()
	{

		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		/**
			input should be any called spots, followed by the participant, potentially followed by number of spots
			example
				1 2 3 Rodney Bedell
			results in
				1. Rodney Bedell
				2. Rodney Bedell
				3. Rodney Bedell
				4.
			example
				1 2 3 Rodney Bedell 5
			results in
				1. Rodney Bedell
				2. Rodney Bedell
				3. Rodney Bedell
				and 2 extra spots filled at random
			example
				Rodney Bedell 5
			results in
				5 open spots filled at random

		*/

		String[] pieces = input.split("\\s+");
		/**
			 the input is broken up into all the called spots, the parts of the name, and the total spots
			 example
			 	1 2 3 Rodney Bedell 6
			 results in
			 	1
			 	2
			 	3
			 	Rodney
			 	Bedell
			 	6
		*/
		ArrayList<Integer> calledSpots = new ArrayList<Integer>();
		String callerName = "";
		int extraSpots = 0;
 
		// iterate through the pieces, and store the inputs where they belong
		for (String piece : pieces)
		{
			if (piece.matches("-?\\d+(\\.\\d+)?"))
			{
				calledSpots.add(Integer.parseInt(piece));
			}
			else
			{
				callerName += piece;
				callerName += " ";
			}
		}
		// callerName is now the caller's full name, with an additional space at the end: "Rodney Bedell "
		// calledSpots now contains all the called spots, but also might have the total number of spots as the last item
		// if calledSpots does have the total number at the end (String[] pieces ends with a number), it must be fixed

		if (pieces[pieces.length - 1].matches("-?\\d+(\\.\\d+)?"))
		{
			extraSpots += calledSpots.get(calledSpots.size() -1);
			calledSpots.remove(calledSpots.size()-1);
			extraSpots -= calledSpots.size();
		}

		// make sure the input is acceptable
		if (calledSpots.size() + extraSpots > this.openSpots.size())
		{
			System.out.println("I'm sorry, there aren't enough open spots. Please try that again");
			return;
		}
		/** TO DO: 
			check for spots that have already been claimed
			check for someone taking too many spots
		*/

		// figure out the called spots (specific spots claimed by participants)
		for (Integer spot : calledSpots)
		{
			String newSpot = this.list.get(spot - 1);
			newSpot += callerName;
			this.list.set(spot - 1, newSpot);
			this.openSpots.remove(spot);
		}

		// fill the extraSpots, chosen at random
		for (int i = 0; i < extraSpots; i++)
		{
			Integer spot = openSpots.get((int)(Math.random() * openSpots.size()));
			String newSpot = this.list.get(spot - 1);
			newSpot += callerName;
			this.list.set(spot - 1, newSpot);
			this.openSpots.remove(spot);
		}

		this.print();

		if (this.openSpots.size() == 0)
		{
			System.out.println("Congrats! To the Queue!");
		}
		else
		{
			System.out.println("Filling any more?");
		}
	}




/**

		String calledSpotsString = "";
		// the list(string) of called spots. will be a String comprised of numbers and spaces

		String calledQuantity = "";
		// the name of the participant, + the number of additional spots
		// should look like "Rodney Bedell 5"

		String callerName = "";
		// the name of the participant

		int extraSpots = 0;
		// number of additional spots, to be filled at random

		// break the input into calledSpotsString and calledQuantity
		for(int i = 0; i<input.length(); i++)
		{
			// breaks if the loop arrives at the participant's name
			if (Character.isLetter(input.charAt(i)))
			{
				calledQuantity = input.substring(i);

				String[] pieces = calledQuantity.split("\\s+");
				// in most cases, 'pieces' will contain a First name, Last name, and number of spots
				// occaisonally people will have more than two parts to their name  
				for (int i = 0; i < pieces.length - 1; i++)
				{
					callerName.concat(pieces[i]);
				}
				extraSpots += Integer.parseInt(pieces[pieces.length -1]);
				break;
			}
			// adds any leading numbers to the list(string) of called spots
			// also decrements the number of additional random spots
			else
			{
				calledSpotsString = calledSpotsString + input.charAt(i);
				extraSpots --;
			}
		}
		// converts the string of called spots into an int[]
		String[] calls = calledSpotsString.split("\\s+");

		int[] calledSpotsINT = new int[calls.length];
		for (int i = 0; i<calls.length; i++)
		{
			calledSpotsINT[i] = Integer.parseInt(calls[i]);
		}
		this.callSpots(calledSpotsINT, callerName);
	}
	// give a person the spots they call, also remove those from the open spots
	public void callSpots(int[] spots, String callerName)
	{
		


		for (int calledSpot : spots)
		{
			this.list.get(calledSpot - 1).concat(callerName);
			this.openSpots.remove((Integer)calledSpot);
		}
	}
	*/


}