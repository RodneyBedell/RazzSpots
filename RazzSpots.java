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

	public RazzSpots()
	{
		this.list = new ArrayList<String>();
		this.openSpots = new ArrayList<Integer>();
	}

	public static void main (String[] args)
	{
		RazzSpots razz = new RazzSpots();
		razz.initiate();
		razz.print();
		razz.fill();
		razz.print();
	}

	// print the current razz list
	public void print ()
	{
		for (String slot : list)
		{
			System.out.println(slot);
		}
	}

	// initiate the razz list with a specified number of spots, adding in fusco spots
	public void initiate ()
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
		int fusco = scanner.nextInt();
		for (int i=0;i<fusco;i++)
		{
			list.add((spots + i + 1) + ". fusco");
		}
	}

	// fill the spots in the razz
	public void fill ()
	{
		System.out.println("Filling any spots?");
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

		// figure out the called spots (specific spots claimed by participants)

		String calledSpotsString = "";
		// the list(string) of called spots. will be a String comprised of numbers and spaces

		String callerQuantity = "";
		// the name of the participant, + the number of additional spots
		// should look like "Rodney Bedell 5"

		String callerName = "";
		// the name of the participant

		int extraSpots = 0;
		// number of additional spots, to be filled at random

		// break the input into calledSpotsString and callerQuantity
		for(int i = 0; i<input.length(); i++)
		{
			// breaks if the loop arrives at the participant's name
			if (Character.isLetter(input.charAt(i)))
			{
				callerQuantity = input.substring(i);
				break;
			}
			// adds any leading numbers to the list(string) of called spots
			else
			{
				calledSpotsString = calledSpotsString + input.charAt(i);
			}
		}
		// converts the string of called spots into an int[]
		String[] calls = calledSpotsString.split("\\s+");

		int[] calledSpotsINT = new int[calls.length];
		for (int i = 0; i<calls.length; i++)
		{
			calledSpotsINT[i] = Integer.parseInt(calls[i]);
		}



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

}