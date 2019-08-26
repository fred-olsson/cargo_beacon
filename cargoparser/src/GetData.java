import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileReader;

public class GetData
{

    // Get the raw hex-data from file
    public static byte[] getRawHexData(String path) throws Exception
    {
        // Create FileReader stream and send in to BufferedReader
        FileReader dataDumpFile = new FileReader(path);
        BufferedReader reader = new BufferedReader(dataDumpFile);

        // Store all the data from the file
        String rawFileData = "";
        String line = reader.readLine();
        while (line != null)
        {
            rawFileData += line + System.getProperty("line.separator");
            line = reader.readLine();
        }

        // Get the hex-data from the file
        int hexPos = rawFileData.indexOf("Block");
        String hexDump = rawFileData.substring(hexPos);
        String[] hexDumpLines = hexDump.split(System.getProperty("line.separator"));
        String rawHexData = "";
        for( String item : hexDumpLines)
        {
            String[] split = item.split("\\s+");
            rawHexData += split[5];
        }

        byte[] byteArray = toByteArray(rawHexData);
        return byteArray;
    }

    public static byte[] toByteArray(String s)
    {
        return DatatypeConverter.parseHexBinary(s);
    }
}