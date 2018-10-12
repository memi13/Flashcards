package enums;

public enum LeitnerNumbers 
{
	Fach1(1),
	Fach2(2),
	Fach3(10),
	Fach4(30),
	Fach5(90),;
	private final int id;
	LeitnerNumbers(int id) { this.id = id; }
    public int getValue() { return id; }
}
