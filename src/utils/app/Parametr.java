package utils.app;

import java.util.ArrayList;

public class Parametr {
	private String key;
	private ArrayList<String> params = new ArrayList<String>();
	
	Parametr(String key){
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void addParam(String param) {
		params.add(param);
	}
	
	public int getParamCount(){
		return params.size();
	}
	
	public String getParam(int index) {
		return params.get(index);
	}
	
	@Override
	public String toString() {
		return String.format("key=%s, params=%s ", this.key, params.toString());
		
	}
}
