package game.model;

/**
 * A rule is chosen randomly at the start of each game, to win a game the
 * player(s) must satisfy that rule.
 *
 * @author bryerscame
 *
 *         TIME_ALIVE: 	Bullet is alive for x amount of time
 *         BOUNCES:		Bullet must hit n amount of walls
 *         DISTANCE:	Bullet must travel >= x distance
 *         TROPHY:	 	Bullet must gain n trophies
 */
public enum RuleType {

	TIME_ALIVE, BOUNCES, DISTANCE, TROPHY;

	// Value that bullet has to satisfy
	private int value;

	public void setValue(int value) {
		this.value = value;
	}

	public final int getValue() {
		return this.value;
	}
}
