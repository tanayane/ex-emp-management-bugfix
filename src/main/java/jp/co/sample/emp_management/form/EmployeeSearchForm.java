package jp.co.sample.emp_management.form;

/**
 * 名前の曖昧検索を行うためのフォーム.
 * 
 * @author ayane.tanaka
 *
 */
public class EmployeeSearchForm {

	/**	 検索する名前*/
	private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	@Override
	public String toString() {
		return "SearchName [searchName=" + searchName + "]";
	}
	
}
