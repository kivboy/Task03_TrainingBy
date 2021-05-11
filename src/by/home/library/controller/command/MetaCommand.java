package by.home.library.controller.command;

public class MetaCommand {
	public static final int CODE_SUCCESS = 200;
	public static final int CODE_ERROR_SYNTAX = 300;
	public static final int CODE_ERROR_NODATA = 400;
	public static final int CODE_ERROR_INTERNAL = 500;

	public static final String RESPONSE_CODE_DELIMETER = " ";
	public static final String RESPONSE_INFO_DELIMETER = ";";
	public static final String REQUEST_COMMAND_DELIMETER = " ";
	public static final String REQUEST_PARAM_DELIMETER = ";";
	public static final String KEY_VALUE_DELIMETER = "=";

	public static String getValue(String field, String tokens, String separator) {

		String result = null;

		String[] kvPairs = tokens.split(separator);

		for (String kvPair : kvPairs) {
			String[] kv = kvPair.split(KEY_VALUE_DELIMETER);
			String key = kv[0];
			
			// если нашли требуемый ключ возвращаем значение или null
			if (key.equals(field)) {
				if (kv.length == 2) {
					result = kv[1];
				}					
				break;
			}
		}

		return result;
	}		
	
	public static int countWords(String str, String pattern)
	{	    
	    return str.split(pattern, -1).length;
	}
}
