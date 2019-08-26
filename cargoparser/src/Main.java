import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main
{
    static byte[] rawData;
    static List<Package> packList = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        String fileDestination = userInput("Enter filepath: ");
        rawData = GetData.getRawHexData(fileDestination);

        for (int i = 0; i < rawData.length; i++)
        {
            if (rawData[i] == (byte) 0xAA)
            {
                RegPack r_pack = new RegPack();
                r_pack.type = rawData[i] & 0xFF;
                r_pack.batt = rawData[i + 1] & 0xFF;
                r_pack.humid = rawData[i + 2] & 0xFF;
                r_pack.temp = rawData[i + 3];
                try
                {
                    r_pack.seconds = shiftSeconds(i + 4);
                }
                catch (IndexOutOfBoundsException e)
                {
                    System.err.println("IndexOutOfBoundsException: " + e);
                }
                packList.add(r_pack);
                i += 7;
            }
            else if (rawData[i] == (byte) 0xAB)
            {
                AccPack a_pack = new AccPack();
                a_pack.type = rawData[i] & 0xFF;
                a_pack.acc_x = (rawData[i + 1] * 186) / 1000;
                a_pack.acc_y = (rawData[i + 2] * 186) / 1000;
                a_pack.acc_z = (rawData[i + 3] * 186) / 1000;
                try
                {
                    a_pack.seconds = shiftSeconds(i + 4);
                }
                catch (IndexOutOfBoundsException e)
                {
                    System.err.println("IndexOutOfBoundsException: " + e);
                }
                packList.add(a_pack);
                i += 7;
            }
        }

        // Cast the package as the correct subpackage and print out the contents
        for (Package item : packList)
        {
            if (item instanceof RegPack)
            {
                RegPack rp = (RegPack) item;
                System.out.println("Type: RegPackage " + rp.type);
                System.out.println("Batt: " + rp.batt);
                System.out.println("Humid: " + rp.humid);
                System.out.println("Temp: " + rp.temp);
                System.out.println("Seconds: " + rp.seconds);
                System.out.println();
            }
            else if (item instanceof AccPack)
            {
                AccPack ap = (AccPack) item;
                System.out.println("Type: AccPackage " + ap.type);
                System.out.println("Acc X: " + ap.acc_x);
                System.out.println("Acc Y: " + ap.acc_y);
                System.out.println("Acc Z: " + ap.acc_z);
                System.out.println("Seconds: " + ap.seconds);
                System.out.println();
            }
        }
    }

    public static String userInput(String msg)
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(msg);
        String input = inputScanner.next();
        return input;
    }

    public static long shiftSeconds(int i)
    {
        long sec  = rawData[i] & 0xFF;
        sec |= (rawData[i + 1] << 8) & 0xFFFF;
        sec |= (rawData[i + 2] << 16) & 0xFFFFFF;
        sec |= (rawData[i + 3] << 24) & 0xFFFFFFFF;
        return sec;
    }
}