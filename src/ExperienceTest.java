
public class ExperienceTest {

	private float percentage;
	
	public ExperienceTest(int level, int oldXP, int currentXP) {
		Player horst = new Player("Horst");
		horst.setLvl(level);
		horst.setoldXP(oldXP);
		while (level == horst.getLvl()) {
			horst.setXp(horst.getLvl());
		}
		percentage = (float) (currentXP - oldXP) / (float) (horst.getoldXP() - oldXP);
	}

	public float getPercentage() {
		return percentage * 100;
	}
	
	public static void main(String[] args) {
		System.out.println((new ExperienceTest(12, 3000, 3200).getPercentage()));
	}
}
