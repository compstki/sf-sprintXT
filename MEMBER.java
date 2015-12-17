// member.
// author ( 0 5 1 |) 3 5 |< 

import javax.swing.JOptionPane;    

public class MEMBER
{
    private String fName;
    private String sName;
    private float weight;
    private float height;
    private String memberID;
    private char category;
    private float bmi;

    public MEMBER()
    {
        // initialise instance variables
        fName = "jim";
        sName = "mac";
        weight = 0.0f;
        height = 0.0f;
        memberID = "";
        category = 0;
        bmi = 0.0f;
    }

    // file handling store details from file
    public void readMemberDetails(String dataItems)
    {
        // unpack string of row data into fields
        String[] rowItems = dataItems.split(",");
        // store each data item as instance property
        fName = rowItems[0];
        sName = rowItems[1];
        weight = Float.parseFloat(rowItems[2]);
        height = Float.parseFloat(rowItems[3]);
        memberID = rowItems[4];
        category = (rowItems[5]).charAt(0);
        //calculate ans store the bmi, adjust cm to m
        bmi = weight/(height*height/10000);
    }

    public String writeDetails()
    {
        // join up data into a string to output as a row
        // use "," to separate csv columns
        String memberData = "";
        memberData = memberData.concat(memberID);
        memberData = memberData.concat(",");
        memberData = memberData.concat(fName);
        memberData = memberData.concat(",");
        memberData = memberData.concat(sName);
        memberData = memberData.concat(",");
        memberData = memberData.concat(Float.toString(bmi));
        memberData = memberData.concat(",");
        memberData = memberData.concat(Character.toString(category));
        return memberData;
    }

    public float getBMI() 
    {
        return bmi;
    }

    public String getID() 
    {
        return memberID;
    }

    public char getMemberType() 
    {
        return category;
    }

    public String getName() 
    {
        return fName + " " + sName;
    }

    public void displayDetails()
    {
        // output basic details
        System.out.print("Member: " + fName + " " + sName);
        System.out.print(",  BMI is ");
        System.out.printf("%.2f", bmi);
        System.out.println();
    }

}
