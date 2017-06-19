import java.io.BufferedReader;
import java.io.FileReader;
import java.io.LineNumberReader;

public class CSV {
	private String[][] items;
	private int lines;
	private int columns;
	
	CSV(String file) {
		String line = "";
		String delimiter = ",";
		BufferedReader br = null;
		try {
			LineNumberReader lnr = new LineNumberReader(new FileReader(file));
			line = lnr.readLine();
			columns = line.length() - line.replace(",", "").length() + 1;
			lnr.skip(Long.MAX_VALUE);
			lines = lnr.getLineNumber() + 1;
			if(lines == 25)lines = 1;
			lnr.close();
			items = new String[lines][columns];
			br = new BufferedReader(new FileReader(file));
			int lineCnt = 0;
			while ((line = br.readLine()) != null) {
				items[lineCnt++] = line.split(delimiter);				
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	public int[][] getIntegerArray() {
		int[][] retval = new int[lines][columns];
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				retval[i][j] = Integer.parseInt(items[i][j]);
			}
		}
		return retval;
	}
}
