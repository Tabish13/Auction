package Bot.zoho;

public class Test {

	public static void main(String[] args) {
		Zohodb db = new Zohodb();
		try {
			db.readData("9");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
