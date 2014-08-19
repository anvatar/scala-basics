package impatient.chapter05;

public class JavaCar {
	private final String manufacturer;
	private final String modelName;
	private final int modelYear;
	private String registeredNum;

	public JavaCar(String manufacturer, String modelName, int modelYear, String registeredNum) {
		this.manufacturer = manufacturer;
		this.modelName = modelName;
		this.modelYear = modelYear;
		this.registeredNum = registeredNum;
	}

	public JavaCar(String manufacturer, String modelName, int modelYear) {
		this(manufacturer, modelName, modelYear, "");
	}

	public JavaCar(String manufacturer, String modelName, String registeredNum) {
		this(manufacturer, modelName, -1, registeredNum);
	}

	public JavaCar(String manufacturer, String modelName) {
		this(manufacturer, modelName, -1);
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModelName() {
		return modelName;
	}

	public int getModelYear() {
		return modelYear;
	}

	public String getRegisteredNum() {
		return registeredNum;
	}

	public void setRegisteredNum(String registeredNum) {
		this.registeredNum = registeredNum;
	}
}
