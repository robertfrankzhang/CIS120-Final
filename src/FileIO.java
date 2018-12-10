import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class FileIO {
	
	public static Map<Integer,String> readFile() throws IOException{
		Reader r = makeReader("scores.txt");
		if (r != null) {
			BufferedReader reader = new BufferedReader(r);
			if (!reader.ready()){
	    		throw new IOException();
	    	}
			String line;
			Map<Integer,String> scores = new TreeMap<Integer,String>();
			
	    	while ((line = reader.readLine())!=null) {
				String[] sections = line.split("\\*");
				scores.put(Integer.valueOf(sections[0]), sections[1]);
	    	}
	    	return scores;
		}else {
			Map<Integer,String> scores = new TreeMap<Integer,String>();
			return scores;
		}
		
	}
	
	private static Reader makeReader(String filename)  {
		Reader r = null;
		try {
    		r = new FileReader(filename);
    		
    	}catch (FileNotFoundException e){
    		System.out.println("FileNotFound");
    	} 
		return r;
	}
	
	public static void writeFile(int score) {
		String filename = "scores.txt";
		try {
			FileWriter fileWriter = new FileWriter(filename,true);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write(score+"*"+getTimestamp()+"\n");
			writer.flush();
			writer.close();
		}catch (IOException e) {
			System.out.println("IO");
		}
	}
	
	private static String getTimestamp(){
		return new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
	}
	
	public static String parseTimestamp(String timestamp) {
		String[] sections = timestamp.split("\\.");
		Map<String,String> monthMap = new TreeMap<String,String>();
		monthMap.put("01", "Jan");
		monthMap.put("02", "Feb");
		monthMap.put("03", "Mar");
		monthMap.put("04", "Apr");
		monthMap.put("05", "May");
		monthMap.put("06", "Jun");
		monthMap.put("07", "July");
		monthMap.put("08", "Aug");
		monthMap.put("09", "Sept");
		monthMap.put("10", "Oct");
		monthMap.put("11", "Nov");
		monthMap.put("12", "Dec");
		String newString = sections[3]+":"+sections[4]+" "+monthMap.get(sections[1])+" "+sections[2]+" "+sections[0];
		return newString;
	}
}
