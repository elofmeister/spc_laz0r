
public class ExperienceTest {

	private float percentage;
	
	public ExperienceTest(int level, int oldXP, int currentXP) {
		Player horst = new Player("Horst");
		horst.load("Horst", currentXP, oldXP, level, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		if (horst.getLvl() < 100) {
			while (level == horst.getLvl()) {
				horst.setXp(horst.getLvl());
			}
			percentage = (float) (currentXP - oldXP) / (float) (horst.getoldXP() - oldXP);
		}		
	}

	public float getPercentage() {
		return percentage * 100;
	}
}
