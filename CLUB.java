import javax.swing.JOptionPane;
import java.io.*;               // for general file handling
import java.time.*;
public class CLUB
{
    // array of MEMBER objects
    private MEMBER memberList[];
    // number of members calculated after reading file
    int noOfMembers;

    // CLASSes to open, create, read/write, close files
    FILEREADCSV bmiFile;        // to read file from storage
    FILEWRITECSV resultFile;    // to write data to storage

    public CLUB()  throws IOException
    {
        // create file handler objects
        bmiFile = new FILEREADCSV();
        resultFile = new FILEWRITECSV() ;
    }

    // top level algorithm
    public void processMembers()  throws IOException
    {
        setUpMemberList();  // read data from file, create MEMBERs, store data
        countNotOKbmi();    // decide if more MEMBERs are above or below ideal BMI
        hiBMI();            // report who has highest BMI
        calcAverageBMI();   // calculate the average BMI (total/number of members)
        searchMember();     // find who has a given membership number
        saveNewMembers();   // create file of new members only
    }

    public void setUpMemberList() throws IOException
    {
        // First user message
        System.out.println("ScotFit Club: Membership BMI update\n");
        System.out.println("** Preparing to read data file.");

        // read file, fetch data as String array containing the rows
        String[] dataRows = bmiFile.readCSVtable();
        // calculate the number of member rows, skip headings
        noOfMembers = dataRows.length - 1;

        // update user with number of rows with membership details
        System.out.println("** " + noOfMembers + " rows read.\n\n");

        // prepare array for members
        memberList = new MEMBER[noOfMembers];
        // create member objects and copy data from source
        for  (int i = 0; i < noOfMembers; i++) {
            memberList[i] = new MEMBER();
            // adjust to skip headings
            memberList[i].readMemberDetails(dataRows[i+1]);
        }
    }

    public void hiBMI()
    {
        // track the position, start at first position
        int hiBMIposition = 0;
        // loop for each item : member
        for (int i = 1; i < noOfMembers; i++)
        {
            // decide if current item: member matches target: bmi
            if (memberList[i].getBMI() > memberList[hiBMIposition].getBMI() )
            {
                // update position of best item
                hiBMIposition = i;
            }
        }
        // display the result, hiBMIposition is the location of the best value
        System.out.println("Person with highest BMI : ");
        System.out.print(memberList[hiBMIposition].getName() + ", " );
        System.out.println(memberList[hiBMIposition].getBMI());
        // A blank line to separate this report from others.
        System.out.println();
    }

    public void calcAverageBMI()
    {
        // initialise using a float, bmi is a float
        float total = 0.0f;
        for (int i = 0; i < noOfMembers; i++)
        {
            // update total
            total = total + memberList[i].getBMI();
        }
        // search report
        System.out.print("\n Average BMI is :  ");
        // calculate and display the average
        System.out.println(total/noOfMembers + "\n");
    }

    public void searchMember()
    {
        // use validation function to enter suitable ID
        String targetID = enterValidID();
        // track position and found status
        boolean memberFound = false;
        int targetPosition = 0;
        // loop for each item : member
        while ((targetPosition < noOfMembers) && (!memberFound))
        {
            // decide if current item: member matches target: bmi
            if (memberList[targetPosition].getID().equals(targetID) )
            {
                // update found status, record position
                memberFound = true;
            }
            else
            {
                targetPosition++;
            }
        }
        // search report
        System.out.println("\nSearch result : ");
        if (memberFound)
        {
            System.out.print(targetID + " : ");
            System.out.println(memberList[targetPosition].getName());
        }
        else
        {
            System.out.println(targetID + " not found");
        }
        // A blank line to separate this report from others.
        System.out.println();
    }

    public String enterValidID()
    {
        String userValue = "";
        boolean validData = false;
        while (!validData)
        {
            validData = true;
            userValue = JOptionPane.showInputDialog("Enter data");
            if (userValue.length() != 5)
            {
                validData = false;
            }

            if (!userValue.startsWith("SF"))
            {
                validData = false;
            }
        }
        return userValue;
    }

    public void countNotOKbmi()
    {
        System.out.print("A report of members within ideal BMI : ");
        System.out.println(Year.now().getValue() + "\n");
        // start the count
        int countOver = 0;
        int countUnder = 0;
        // loop for each item : member
        for (int i = 0; i < noOfMembers; i++)
        {
            // decide if current item: member matches target: bmi
            if (memberList[i].getBMI() < 18.5)
            {
                // add 1 to count: for OK bmi
                countUnder = countUnder + 1;
            }
            else if (memberList[i].getBMI() > 25)
            {
                countOver = countOver + 1;
            }
        }

        if (countOver > countUnder)
        {
            System.out.println("\n More members are above BMI ideal " + countOver + " (" + countUnder + ")");
        }
        else if (countOver < countUnder)
        {
            System.out.println("\n More members are below BMI ideal " + countUnder + " (" + countOver + ")");
        }
        else
        {
            System.out.println("\n Same number of members above and below BMI ideal "  + countOver);
        }
        // A blank line to separate this report from others.
        System.out.println();
    }

    public void saveNewMembers() throws IOException
    {
        String fileContent = "";
        int count = 0;
        for (int i = 0; i < noOfMembers; i++) 
        {
            if(memberList[i].getMemberType() == 'N' )
            {
                count = count + 1;
                if (count>1) 
                {
                    fileContent = fileContent.concat("\n");
                }
                fileContent = fileContent.concat(memberList[i].writeDetails());
            }
        }

        // *send for writing to file as a string containing all data
        System.out.println("** Preparing to write new members file.");
        resultFile.writeCSVtable(fileContent);
        System.out.println("** File written and closed.");
    }

    public static void main(String[] args)  throws IOException
    {
        CLUB myClub = new CLUB();
        myClub.processMembers();
    }
}
