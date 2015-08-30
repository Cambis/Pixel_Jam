package game.model;

/**
 * A rule is chosen randomly at the start of each game, to win a game the
 * player(s) must satisfy that rule.
 *
 * @author bryerscame
 *
 *         NO_RULE: 'Nuff said
 *         TIME_ALIVE: Bullet has to stay alive x amount of time
 *         BOUNCES: Bullet must hit n amount of walls
 *         DISTANCE: Bullet must travel >= x distance
 *         TROPHY: Bullet must gain n trophies
 *         TIME_TO_FINISH: Bullet lasts x time TIMER: Player has x time to win
 */
public enum RuleType {

	NO_RULE, TIME_ALIVE, BOUNCES, DISTANCE, TROPHY, TIME_TO_FINISH;

	// Value that bullet has to satisfy
	private int value = 0;

	public final static int NO_RULE_MIN = 0;
	public final static int NO_RULE_MAX = 0;

	public final static int TIME_ALIVE_MIN = 5;
	public final static int TIME_ALIVE_MAX = 15;

	public final static int BOUNCES_MIN = 3;
	public final static int BOUNCES_MAX = 10;

	public final static int DISTANCE_MIN = 50;
	public final static int DISTANCE_MAX = 200;

	public final static int TROPHY_MIN = 1;
	public final static int TROPHY_MAX = 5;

	public final static int FINISH_TIME_MIN = 5;
	public final static int FINISH_TIME_MAX = 10;

	public void setValue(int value) {
		this.value = value;
	}

	public void randomiseValue() {
		int dif = 0;
		switch (this) {
		case NO_RULE:
			value = NO_RULE_MIN;
			break;
		case TIME_ALIVE:
			dif = TIME_ALIVE_MAX - TIME_ALIVE_MIN;
			value = (int) (Math.random() * dif) + TIME_ALIVE_MIN;
			break;
		case BOUNCES:
			dif = BOUNCES_MAX - BOUNCES_MIN;
			value = (int) (Math.random() * dif) + BOUNCES_MIN;
			break;
		case DISTANCE:
			dif = DISTANCE_MAX - DISTANCE_MIN;
			value = (int) (Math.random() * dif) + DISTANCE_MIN;
			break;
		case TROPHY:
			dif = TROPHY_MAX - TROPHY_MIN;
			value = (int) (Math.random() * dif) + TROPHY_MIN;
			break;
		case TIME_TO_FINISH:
			dif = TROPHY_MAX - TROPHY_MIN;
			value = (int) (Math.random() * dif) + FINISH_TIME_MIN;
			break;
		default:
			break;
		}
	}

	public final int getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		switch (this) {
		case NO_RULE:
			return "NO RULE";
		case TIME_ALIVE:
			return "Bullet has to be alive for: " + value
					+ " before hitting target";
		case BOUNCES:
			return "Bounces before target: " + value;
		case DISTANCE:
			return "Distance to travel before target: " + value;
		case TROPHY:
			return "Trophies to get before target: " + value;
		case TIME_TO_FINISH:
			return "Bullets last for: " + value + " seconds";
		default:
			return "ERROR";
		}
	}
}
